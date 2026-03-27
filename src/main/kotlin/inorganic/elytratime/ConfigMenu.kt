package inorganic.elytratime

import dev.isxander.yacl3.api.*
import dev.isxander.yacl3.api.controller.*

import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import kotlin.reflect.KMutableProperty0

object ConfigMenu {
    fun build(parent: Screen?): Screen {
        val config = ElytraTime.config

        return YetAnotherConfigLib.createBuilder()
            .title(Component.translatable("title.elytratime.config"))
            .save { config.saveToFile() }

            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("category.elytratime.general"))
                .option(createBooleanOption(
                    "option.elytratime.enable_tooltip",
                    "tooltip.elytratime.enabled",
                    config::tooltipEnabled,
                    true
                ))
                .option(createBooleanOption(
                    "option.elytratime.hud_enabled",
                    "tooltip.elytratime.hud_enabled",
                    config::hudEnabled,
                    true
                ))
                .option(createBooleanOption(
                    "option.elytratime.hud_always_show",
                    "tooltip.elytratime.hud_always_show",
                    config::hudAlwaysShow,
                    false
                ))
                .option(createBooleanOption(
                    "option.elytratime.pad_seconds",
                    "tooltip.elytratime.pad_seconds",
                    config::padSeconds,
                    true
                ))
                .option(createBooleanOption(
                    "option.elytratime.total_seconds_only",
                    "tooltip.elytratime.total_seconds_only",
                    config::totalSecondsOnly,
                    false
                ))
                .option(createBooleanOption(
                    "option.elytratime.use_same_format",
                    "tooltip.elytratime.use_same_format",
                    config::useSameFormatForHudAndTooltip,
                    true
                ))
                .option(createStringOption(
                    "option.elytratime.time_format",
                    "tooltip.elytratime.time_format",
                    config::timeFormat,
                    "%02d:%02d"
                ))
                .option(createStringOption(
                    "option.elytratime.time_report_format",
                    "tooltip.elytratime.time_report_format",
                    config::timeReportFormat,
                    "Remaining: %s (%d%%)"
                ))
                .option(createStringOption(
                    "option.elytratime.tooltip_format",
                    "tooltip.elytratime.tooltip_format",
                    config::tooltipFormat,
                    "%s (%d%%)"
                ))
                .option(createStringOption(
                    "option.elytratime.hud_format",
                    "tooltip.elytratime.hud_format",
                    config::hudFormat,
                    "%s"
                ))
                .build()
            )

            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("category.elytratime.alerts"))
                .option(createBooleanOption(
                    "option.elytratime.alert_enabled",
                    "tooltip.elytratime.alert_enabled",
                    config::alertThresholdEnabled,
                    true
                ))
                .option(createIntegerFieldOption(
                    "option.elytratime.alert_threshold",
                    "tooltip.elytratime.alert_threshold",
                    config::alertThresholdSeconds,
                    60
                ))
                .option(createAlertTypeOption(
                    "option.elytratime.alert_type",
                    "tooltip.elytratime.alert_type",
                    config::alertType,
                    Config.AlertType.ACTION_BAR
                ))
                .option(createColorOption(
                    "option.elytratime.alert_color",
                    "tooltip.elytratime.alert_color",
                    config::alertColor,
                    0xFFFF5555.toInt()
                ))
                .build()
            )

            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("category.elytratime.colors"))
                .option(createIntegerFieldOption(
                    "option.elytratime.yellow_threshold",
                    "tooltip.elytratime.yellow_threshold",
                    config::yellowThreshold,
                    25
                ))
                .option(createIntegerFieldOption(
                    "option.elytratime.red_threshold",
                    "tooltip.elytratime.red_threshold",
                    config::redThreshold,
                    10
                ))
                .option(createColorOption(
                    "option.elytratime.green_color",
                    "tooltip.elytratime.green_color",
                    config::greenColor,
                    0xFF55FF55.toInt()
                ))
                .option(createColorOption(
                    "option.elytratime.yellow_color",
                    "tooltip.elytratime.yellow_color",
                    config::yellowColor,
                    0xFFFFFF55.toInt()
                ))
                .option(createColorOption(
                    "option.elytratime.red_color",
                    "tooltip.elytratime.red_color",
                    config::redColor,
                    0xFFFF5555.toInt()
                ))
                .build()
            )

            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("category.elytratime.hud"))
                .option(createAlignmentOption(
                    "option.elytratime.hud_alignment",
                    "tooltip.elytratime.hud_alignment",
                    config::hudAlignment,
                    Config.Alignment.LEFT
                ))
                .option(createIntegerFieldOption(
                    "option.elytratime.hud_x",
                    "tooltip.elytratime.hud_x",
                    config::hudX,
                    5
                ))
                .option(createIntegerFieldOption(
                    "option.elytratime.hud_y",
                    "tooltip.elytratime.hud_y",
                    config::hudY,
                    5
                ))
                .build()
            )

            .build()
            .generateScreen(parent)
    }

    private fun createBooleanOption(
        nameKey: String,
        descriptionKey: String,
        property: KMutableProperty0<Boolean>,
        defaultValue: Boolean
    ): Option<Boolean> {
        return Option.createBuilder<Boolean>()
            .name(Component.translatable(nameKey))
            .description(OptionDescription.of(Component.translatable(descriptionKey)))
            .binding(defaultValue, { property.get() }, { property.set(it) })
            .controller(TickBoxControllerBuilder::create)
            .build()
    }

    private fun createStringOption(
        nameKey: String,
        descriptionKey: String,
        property: KMutableProperty0<String>,
        defaultValue: String
    ): Option<String> {
        return Option.createBuilder<String>()
            .name(Component.translatable(nameKey))
            .description(OptionDescription.of(Component.translatable(descriptionKey)))
            .binding(defaultValue, { property.get() }, { property.set(it) })
            .controller(StringControllerBuilder::create)
            .build()
    }

    private fun createIntegerFieldOption(
        nameKey: String,
        descriptionKey: String,
        property: KMutableProperty0<Int>,
        defaultValue: Int
    ): Option<Int> {
        return Option.createBuilder<Int>()
            .name(Component.translatable(nameKey))
            .description(OptionDescription.of(Component.translatable(descriptionKey)))
            .binding(defaultValue, { property.get() }, { property.set(it) })
            .controller(IntegerFieldControllerBuilder::create)
            .build()
    }

    private fun createColorOption(
        nameKey: String,
        descriptionKey: String,
        property: KMutableProperty0<Int>,
        defaultValue: Int
    ): Option<String> {
        return Option.createBuilder<String>()
            .name(Component.translatable(nameKey))
            .description(OptionDescription.of(Component.translatable(descriptionKey)))
            .binding(
                String.format("#%08X", defaultValue),
                { String.format("#%08X", property.get()) },
                { value ->
                    try {
                        // Remove # if present and parse as hex
                        val hex = if (value.startsWith("#")) value.substring(1) else value
                        property.set(hex.toLong(16).toInt())
                    } catch (e: Exception) {
                        try {
                            // Try parsing as decimal integer
                            property.set(value.toInt())
                        } catch (e2: Exception) {
                            // If parsing fails, keep current value
                        }
                    }
                }
            )
            .controller(StringControllerBuilder::create)
            .build()
    }

    private fun createAlertTypeOption(
        nameKey: String,
        descriptionKey: String,
        property: KMutableProperty0<Config.AlertType>,
        defaultValue: Config.AlertType
    ): Option<Config.AlertType> {
        return Option.createBuilder<Config.AlertType>()
            .name(Component.translatable(nameKey))
            .description(OptionDescription.of(Component.translatable(descriptionKey)))
            .binding(defaultValue, { property.get() }, { property.set(it) })
            .controller { opt ->
                EnumControllerBuilder.create(opt)
                    .enumClass(Config.AlertType::class.java)
            }
            .build()
    }

    private fun createAlignmentOption(
        nameKey: String,
        descriptionKey: String,
        property: KMutableProperty0<Config.Alignment>,
        defaultValue: Config.Alignment
    ): Option<Config.Alignment> {
        return Option.createBuilder<Config.Alignment>()
            .name(Component.translatable(nameKey))
            .description(OptionDescription.of(Component.translatable(descriptionKey)))
            .binding(defaultValue, { property.get() }, { property.set(it) })
            .controller { opt ->
                EnumControllerBuilder.create(opt)
                    .enumClass(Config.Alignment::class.java)
            }
            .build()
    }
}
