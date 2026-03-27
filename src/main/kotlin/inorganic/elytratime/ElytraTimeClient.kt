package inorganic.elytratime

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements


import net.minecraft.client.Minecraft
import net.minecraft.client.KeyMapping


import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.world.item.Items
import net.minecraft.network.chat.Component


import org.lwjgl.glfw.GLFW

class ElytraTimeClient : ClientModInitializer {
    override fun onInitializeClient() {
        registerEvents()
        registerKeybindings()
        registerHud()

        ElytraTime.LOGGER.info("Initialised on client")
    }

    private fun registerKeybindings() {
        val category = KeyMapping.Category(net.minecraft.resources.Identifier.fromNamespaceAndPath("minecraft", "key.categories.misc"))

        val printTime = KeyMapping(
            "key.elytratime.show",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_F4,
            category
        )

        val openConfig = KeyMapping(
            "key.elytratime.config",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_O,
            category
        )

        var alertCooldown = 0
        var hasWarnedThisFlight = false
        ClientTickEvents.END_CLIENT_TICK.register { client ->
            val player = client.player ?: return@register
            val world = client.level ?: return@register

            if (alertCooldown > 0) alertCooldown--
            
            if (!player.isFallFlying()) {
                hasWarnedThisFlight = false
            }

            if (player.isFallFlying() && ElytraTime.config.alertThresholdEnabled) {
                val elytra = Util.findElytra(player)
                if (elytra != null && Util.shouldWarn(elytra, world)) {
                    val alertType = ElytraTime.config.alertType
                    val shouldWarn = when (alertType) {
                        Config.AlertType.CHAT -> !hasWarnedThisFlight
                        Config.AlertType.ACTION_BAR -> alertCooldown == 0
                        Config.AlertType.TITLE -> alertCooldown == 0
                    }

                    if (shouldWarn) {
                        val message = Component.literal("WARNING: Elytra durability low!")
                            .withColor(ElytraTime.config.alertColor)
                        when (alertType) {
                            Config.AlertType.CHAT -> player.sendSystemMessage(message)
                            Config.AlertType.ACTION_BAR -> client.gui.setOverlayMessage(message, true)
                            Config.AlertType.TITLE -> client.gui.setTitle(message)
                        }
                        alertCooldown = 60
                        hasWarnedThisFlight = true
                    }
                }
            }

            if (printTime.consumeClick()) {
                val elytra = Util.findElytra(player)
                if (elytra != null && ElytraTime.config.tooltipEnabled) {
                    val message = Util.formatTimePercent(elytra, ElytraTime.config.timeReportFormat, ElytraTime.config.timeFormat, world)
                    player.sendSystemMessage(Component.literal(message))
                }
            }

            if (openConfig.consumeClick()) {
                client.screen = ConfigMenu.build(client.screen)
            }
        }

        // Register keybindings using Fabric API
        KeyMappingHelper.registerKeyMapping(printTime)
        KeyMappingHelper.registerKeyMapping(openConfig)
    }

    private fun registerHud() {
        HudElementRegistry.attachElementBefore(
            VanillaHudElements.HOTBAR,
            net.minecraft.resources.Identifier.fromNamespaceAndPath("elytratime", "hud")
        ) { graphics, tickDelta ->
            HudRenderer.onHudRender(graphics, tickDelta)
        }
    }

    private fun registerEvents() {
        ItemTooltipCallback.EVENT.register { stack, context, tooltipFlag, lines ->
            if (!ElytraTime.config.tooltipEnabled) return@register
            val player = Minecraft.getInstance().player ?: return@register
            val world = Minecraft.getInstance().level ?: return@register

            val found = Util.findElytra(player)
            if (found != null && stack.`is`(Items.ELYTRA)) {
                val percent = (Calculator.fractionRemaining(found, world) * 100.0).toInt()
                val color = Util.getColor(percent)

                lines.add(
                    Component.literal(
                        Util.formatTimePercent(
                            found,
                            ClientTextUtils.getTimeReportFormat(),
                            ClientTextUtils.getTimeFormat(),
                            world
                        )
                    ).withColor(color)
                )
            } else if (stack.`is`(Items.ELYTRA)) {
                lines.add(
                    Component.translatable("message.elytratime.no_elytra").withColor(net.minecraft.ChatFormatting.RED.color ?: 0xFF5555)
                )
            }
        }
    }
}