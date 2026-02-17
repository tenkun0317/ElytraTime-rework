package inorganic.elytratime

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.render.RenderTickCounter
import net.minecraft.text.Text

object HudRenderer : HudRenderCallback {
    override fun onHudRender(drawContext: DrawContext, tickCounter: RenderTickCounter) {
        val client = MinecraftClient.getInstance()
        if (client.options.hudHidden || !ElytraTime.config.hudEnabled) return

        val player = client.player ?: return
        val world = client.world ?: return
        val elytra = Util.findElytra(player)

        val text: String
        var color: Int

        if (elytra != null) {
            text = Util.formatTimePercent(
                elytra,
                ClientTextUtils.getHudFormat(),
                ClientTextUtils.getTimeFormat(),
                world
            )
            val percent = (Calculator.fractionRemaining(elytra, world) * 100.0).toInt()
            color = Util.getColor(percent)

            if (Util.shouldWarn(elytra, world)) {
                val alpha = ((Math.sin(world.time.toDouble() / 5.0) + 1.0) / 2.0 * 155.0 + 100.0).toInt()
                color = (alpha shl 24) or (color and 0x00FFFFFF)
            }
        } else if (client.currentScreen?.title?.content == Text.translatable("title.elytratime.config").content) {
            // Preview mode when config screen is open
            text = "10m 00s (100%)"
            color = Util.getColor(100)
        } else {
            return
        }

        val scale = ElytraTime.config.hudScale
        val matrices = drawContext.getMatrices()
        matrices.pushMatrix()
        matrices.scale(scale, scale)

        val textWidth = client.textRenderer.getWidth(text)
        val x = when (ElytraTime.config.hudAlignment) {
            Config.Alignment.LEFT -> (ElytraTime.config.hudX / scale).toInt()
            Config.Alignment.CENTER -> (ElytraTime.config.hudX / scale - textWidth / 2).toInt()
            Config.Alignment.RIGHT -> (ElytraTime.config.hudX / scale - textWidth).toInt()
        }

        drawContext.drawTextWithShadow(
            client.textRenderer,
            Text.literal(text),
            x,
            (ElytraTime.config.hudY / scale).toInt(),
            color
        )

        matrices.popMatrix()
    }
}
