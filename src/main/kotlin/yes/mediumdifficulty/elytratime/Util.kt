package yes.mediumdifficulty.elytratime

import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.world.World

object Util {
    fun formatTimePercent(item: ItemStack, format: String, timeFormat: String, world: World): String {
        val timeLeft = formatTime(Calculator.timeRemaining(item, world), timeFormat)
        val percent = (Calculator.fractionRemaining(item, world) * 100.0).toInt()

        return format
            .replace("[TIME]", timeLeft)
            .replace("[%]", percent.toString())
    }

    fun formatTime(time: Int, format: String): String {
        val minutes = time / 60
        val seconds = time % 60
        return format
            .replace("[M]", minutes.toString())
            .replace("[S]", seconds.toString().padStart(2, '0'))
    }

    fun shouldWarn(item: ItemStack, world: World): Boolean {
        if (!ElytraTime.config.alertThresholdEnabled) return false
        val secondsRemaining = Calculator.timeRemaining(item, world)
        return secondsRemaining <= ElytraTime.config.alertThresholdSeconds
    }

    fun getColor(percent: Int): Int {
        return when {
            percent > ElytraTime.config.yellowThreshold -> ElytraTime.config.greenColor
            percent > ElytraTime.config.redThreshold -> ElytraTime.config.yellowColor
            else -> ElytraTime.config.redColor
        }
    }

    fun findElytra(player: PlayerEntity): ItemStack? {
        val chestPlate = player.getEquippedStack(EquipmentSlot.CHEST)
        return chestPlate.takeIf { it.isOf(Items.ELYTRA) }
    }
}
