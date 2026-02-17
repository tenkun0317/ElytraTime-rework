package yes.mediumdifficulty.elytratime

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.item.Items
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import org.lwjgl.glfw.GLFW

class ElytraTimeClient : ClientModInitializer {
    override fun onInitializeClient() {
        registerEvents()
        registerKeybindings()
        HudRenderCallback.EVENT.register(HudRenderer)

        ElytraTime.LOGGER.info("Initialised on client")
    }

    private fun registerKeybindings() {
        val category = KeyBinding.Category(Identifier.of("elytratime", "controls"))

        val printTime = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "key.elytratime.show",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F4,
                category
            )
        )

        val openConfig = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "key.elytratime.config",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                category
            )
        )

        var alertCooldown = 0
        var hasWarnedThisFlight = false
        ClientTickEvents.END_CLIENT_TICK.register { client ->
            val player = client.player ?: return@register
            val world = client.world ?: return@register

            if (alertCooldown > 0) alertCooldown--
            
            if (!player.isGliding) {
                hasWarnedThisFlight = false
            }

            if (player.isGliding && ElytraTime.config.alertThresholdEnabled) {
                val elytra = Util.findElytra(player)
                if (elytra != null && Util.shouldWarn(elytra, world)) {
                    val alertType = ElytraTime.config.alertType
                    val shouldWarn = when (alertType) {
                        Config.AlertType.CHAT -> !hasWarnedThisFlight
                        else -> alertCooldown <= 0
                    }

                    if (shouldWarn) {
                        val time = Util.formatTime(Calculator.timeRemaining(elytra, world), ClientTextUtils.getTimeFormat())
                        val color = ElytraTime.config.alertColor
                        
                        val message = ClientTextUtils.getValueFromKey("message.elytratime.warning")
                            .replace("§c", "") // Remove legacy codes if present
                            .replace("[TIME]", time)
                        val text = Text.literal(message).withColor(color)

                        when (alertType) {
                            Config.AlertType.CHAT -> {
                                player.sendMessage(text, false)
                                hasWarnedThisFlight = true
                            }
                            Config.AlertType.ACTION_BAR -> {
                                player.sendMessage(text, true)
                                alertCooldown = 40 // Alert every 2 seconds
                            }
                            Config.AlertType.TITLE -> {
                                client.inGameHud.setTitle(text)
                                client.inGameHud.setSubtitle(Text.translatable("message.elytratime.warning_subtitle"))
                                alertCooldown = 100 // Alert every 5 seconds for title
                            }
                        }
                    }
                } else {
                    // Reset if we are no longer in warning state (e.g. repaired or switched elytra)
                    hasWarnedThisFlight = false
                }
            }

            if (printTime.wasPressed()) {
                val found = Util.findElytra(player)

                if (found != null) {
                    val percent = (Calculator.fractionRemaining(found, client.world!!) * 100.0).toInt()
                    val color = Util.getColor(percent)
                    
                    player.sendMessage(
                        Text.literal(
                            Util.formatTimePercent(
                                found,
                                ClientTextUtils.getTimeReportFormat(),
                                ClientTextUtils.getTimeFormat(),
                                client.world!!
                            )
                        ).withColor(color), false
                    )
                } else {
                    player.sendMessage(
                        Text.translatable("message.elytratime.no_elytra").formatted(Formatting.RED),
                        false
                    )
                }
            }

            if (openConfig.wasPressed()) {
                client.setScreen(ConfigMenu.build(client.currentScreen))
            }
        }
    }

    private fun registerEvents() {
        ItemTooltipCallback.EVENT.register { itemStack, context, type, lines ->
            if (itemStack.isOf(Items.ELYTRA) && ElytraTime.config.tooltipEnabled) {
                val world = MinecraftClient.getInstance().world ?: return@register
                val percent = (Calculator.fractionRemaining(itemStack, world) * 100.0).toInt()
                val color = Util.getColor(percent)

                lines.add(
                    1, Text.literal(
                        Util.formatTimePercent(
                            itemStack,
                            ClientTextUtils.getTooltipFormat(),
                            ClientTextUtils.getTimeFormat(),
                            world
                        )
                    ).withColor(color)
                )
            }
        }
    }
}
