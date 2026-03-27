package inorganic.elytratime

import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level

object Util {
    fun formatTimePercent(item: ItemStack, format: String, timeFormat: String, world: Level): String {
        val timeLeft = formatTime(Calculator.timeRemaining(item, world), timeFormat)
        val percent = (Calculator.fractionRemaining(item, world) * 100.0).toInt()

        return format
            .replace("[TIME]", timeLeft)
            .replace("[%]", percent.toString())
    }

    fun formatTime(time: Int, format: String): String {
        if (ElytraTime.config.totalSecondsOnly) {
            val secondsStr = if (ElytraTime.config.padSeconds) time.toString().padStart(2, '0') else time.toString()
            return format
                .replace("[M]", "0")
                .replace("[S]", secondsStr)
        }

        val minutes = time / 60
        val seconds = time % 60
        val secondsStr = if (ElytraTime.config.padSeconds) seconds.toString().padStart(2, '0') else seconds.toString()

        return format
            .replace("[M]", minutes.toString())
            .replace("[S]", secondsStr)
    }

    fun shouldWarn(item: ItemStack, world: Level): Boolean =
        Calculator.timeRemaining(item, world) <= ElytraTime.config.alertThresholdSeconds

    fun getColor(percent: Int): Int = when {
        percent >= ElytraTime.config.yellowThreshold -> ElytraTime.config.greenColor
        percent >= ElytraTime.config.redThreshold -> ElytraTime.config.yellowColor
        else -> ElytraTime.config.redColor
    }

    fun findElytra(player: Player): ItemStack? {
        val chestPlate = player.getItemBySlot(EquipmentSlot.CHEST)
        return chestPlate.takeIf { it.`is`(Items.ELYTRA) }
    }
}