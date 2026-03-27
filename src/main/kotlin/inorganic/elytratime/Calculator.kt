package inorganic.elytratime

import net.minecraft.world.item.enchantment.EnchantmentHelper
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

object Calculator {
    fun timeRemaining(item: ItemStack, world: Level): Int {
        val multiplier = getUnbreakingMultiplier(item, world)
        return (item.maxDamage - item.damageValue) * multiplier - 1
    }

    fun fractionRemaining(item: ItemStack, world: Level): Float {
        val multiplier = getUnbreakingMultiplier(item, world)
        val totalTime = item.maxDamage * multiplier - 1
        if (totalTime <= 0) return 0f

        val remaining = (item.maxDamage - item.damageValue) * multiplier - 1
        return remaining.coerceAtLeast(0).toFloat() / totalTime.toFloat()
    }

    private fun getUnbreakingMultiplier(item: ItemStack, world: Level): Int {
        val registry = world.registryAccess().lookupOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT)
        val holder = registry.get(Enchantments.UNBREAKING).orElse(null)
        return if (holder != null) {
            EnchantmentHelper.getItemEnchantmentLevel(holder, item) + 1
        } else {
            1
        }
    }
}
