package yes.mediumdifficulty.elytratime

import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.ItemStack
import net.minecraft.registry.RegistryKeys
import net.minecraft.world.World

object Calculator {
    fun timeRemaining(item: ItemStack, world: World): Int {
        val multiplier = getUnbreakingMultiplier(item, world)
        return (item.maxDamage - item.damage) * multiplier - 1
    }

    fun fractionRemaining(item: ItemStack, world: World): Float {
        val multiplier = getUnbreakingMultiplier(item, world)
        val totalTime = item.maxDamage * multiplier - 1
        if (totalTime <= 0) return 0f

        val remaining = (item.maxDamage - item.damage) * multiplier - 1
        return remaining.coerceAtLeast(0).toFloat() / totalTime.toFloat()
    }

    private fun getUnbreakingMultiplier(item: ItemStack, world: World): Int {
        val registry = world.registryManager.getOrThrow(RegistryKeys.ENCHANTMENT)
        val enchantment = registry.get(Enchantments.UNBREAKING) ?: return 1
        return EnchantmentHelper.getLevel(registry.getEntry(enchantment), item) + 1
    }
}
