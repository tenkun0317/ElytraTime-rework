package inorganic.elytratime


import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.network.chat.Component

object HudRenderer {
    fun onHudRender(drawContext: GuiGraphicsExtractor, tickDelta: net.minecraft.client.DeltaTracker) {
        val client = Minecraft.getInstance()
        if (client.options.hideGui || !ElytraTime.config.hudEnabled) return

        val player = client.player ?: return
        val world = client.level ?: return
        val elytra = Util.findElytra(player)

        val text: String
        var color: Int

        if (elytra != null && (player.isFallFlying() || ElytraTime.config.hudAlwaysShow)) {
            text = Util.formatTimePercent(elytra, ClientTextUtils.getHudFormat(), ClientTextUtils.getTimeFormat(), world)
            color = Util.getColor((Calculator.fractionRemaining(elytra, world) * 100.0).toInt())

            if (Util.shouldWarn(elytra, world)) {
                val alpha = ((Math.sin(world.gameTime.toDouble() / 5.0) + 1.0) / 2.0 * 155.0 + 100.0).toInt()
                color = (alpha shl 24) or (color and 0x00FFFFFF)
            }
        } else if (client.screen?.title?.string == Component.translatable("title.elytratime.config").string) {
            // Preview mode when config screen is open
            text = "10m 00s (100%)"
            color = Util.getColor(100)
        } else {
            return
        }

        val scale = ElytraTime.config.hudScale
        drawContext.pose().pushMatrix()
        drawContext.pose().scale(scale, scale)

        val textWidth = client.font.width(text)
        val x = when (ElytraTime.config.hudAlignment) {
            Config.Alignment.LEFT -> (ElytraTime.config.hudX / scale).toInt()
            Config.Alignment.CENTER -> (ElytraTime.config.hudX / scale - textWidth / 2).toInt()
            Config.Alignment.RIGHT -> (ElytraTime.config.hudX / scale - textWidth).toInt()
        }

        drawContext.text(
            client.font,
            Component.literal(text),
            x,
            (ElytraTime.config.hudY / scale).toInt(),
            color
        )

        drawContext.pose().popMatrix()
    }
}