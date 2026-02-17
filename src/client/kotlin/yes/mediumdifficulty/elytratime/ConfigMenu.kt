package yes.mediumdifficulty.elytratime

import me.shedaniel.clothconfig2.api.ConfigBuilder
import me.shedaniel.clothconfig2.api.Requirement
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import java.util.Optional

object ConfigMenu {
    fun build(parent: Screen?): Screen = ConfigBuilder.create().apply {
        setParentScreen(parent)
        setTitle(Text.translatable("title.elytratime.config"))
        setSavingRunnable { ElytraTime.config.saveToFile() }

        val general = getOrCreateCategory(Text.translatable("category.elytratime.general"))
        val eb = entryBuilder()

        val enableTooltipEntry = eb.startBooleanToggle(Text.translatable("option.elytratime.enable_tooltip"), ElytraTime.config.tooltipEnabled)
            .setSaveConsumer { ElytraTime.config.tooltipEnabled = it }
            .setTooltip(Text.translatable("tooltip.elytratime.enabled"))
            .setDefaultValue(true)
            .build()
        general.addEntry(enableTooltipEntry)

        general.addEntry(eb.startStrField(Text.translatable("option.elytratime.tooltip_format"), ClientTextUtils.getTooltipFormat())
            .setSaveConsumer { ElytraTime.config.tooltipFormat = it }
            .setTooltip(Text.translatable("tooltip.elytratime.tooltip_format"))
            .setDefaultValue(ClientTextUtils.getValueFromKey("value.elytratime.tooltip_format"))
            .setRequirement(Requirement.isTrue(enableTooltipEntry))
            .build())

        general.addEntry(eb.startStrField(Text.translatable("option.elytratime.time_format"), ClientTextUtils.getTimeFormat())
            .setSaveConsumer { ElytraTime.config.timeFormat = it }
            .setTooltip(Text.translatable("tooltip.elytratime.time_format"))
            .setDefaultValue(ClientTextUtils.getValueFromKey("value.elytratime.time_format"))
            .build())

        general.addEntry(eb.startStrField(Text.translatable("option.elytratime.time_report_format"), ClientTextUtils.getTimeReportFormat())
            .setSaveConsumer { ElytraTime.config.timeReportFormat = it }
            .setTooltip(Text.translatable("tooltip.elytratime.time_report_format"))
            .setDefaultValue(ClientTextUtils.getValueFromKey("value.elytratime.time_report_format"))
            .build())

        val enableAlertEntry = eb.startBooleanToggle(Text.translatable("option.elytratime.alert_enabled"), ElytraTime.config.alertThresholdEnabled)
            .setSaveConsumer { ElytraTime.config.alertThresholdEnabled = it }
            .setTooltip(Text.translatable("tooltip.elytratime.alert_enabled"))
            .setDefaultValue(true)
            .build()
        general.addEntry(enableAlertEntry)

        general.addEntry(eb.startIntField(Text.translatable("option.elytratime.alert_threshold_seconds"), ElytraTime.config.alertThresholdSeconds)
            .setSaveConsumer { ElytraTime.config.alertThresholdSeconds = it }
            .setTooltip(Text.translatable("tooltip.elytratime.alert_threshold_seconds"))
            .setDefaultValue(60)
            .setRequirement(Requirement.isTrue(enableAlertEntry))
            .build())

        general.addEntry(eb.startEnumSelector(Text.translatable("option.elytratime.alert_type"), Config.AlertType::class.java, ElytraTime.config.alertType)
            .setSaveConsumer { ElytraTime.config.alertType = it }
            .setTooltip(Text.translatable("tooltip.elytratime.alert_type"))
            .setDefaultValue(Config.AlertType.ACTION_BAR)
            .setEnumNameProvider { Text.translatable("option.elytratime.alert_type." + it.name.lowercase()) }
            .setRequirement(Requirement.isTrue(enableAlertEntry))
            .build())

        general.addEntry(eb.startIntSlider(Text.translatable("option.elytratime.yellow_threshold"), ElytraTime.config.yellowThreshold, 0, 100)
            .setSaveConsumer { ElytraTime.config.yellowThreshold = it }
            .setTooltip(Text.translatable("tooltip.elytratime.yellow_threshold"))
            .setDefaultValue(25)
            .build())

        general.addEntry(eb.startIntSlider(Text.translatable("option.elytratime.red_threshold"), ElytraTime.config.redThreshold, 0, 100)
            .setSaveConsumer { ElytraTime.config.redThreshold = it }
            .setTooltip(Text.translatable("tooltip.elytratime.red_threshold"))
            .setDefaultValue(10)
            .build())

        val colors = getOrCreateCategory(Text.translatable("category.elytratime.colors"))

        colors.addEntry(eb.startAlphaColorField(Text.translatable("option.elytratime.green_color"), ElytraTime.config.greenColor)
            .setSaveConsumer { ElytraTime.config.greenColor = it }
            .setTooltip(Text.translatable("tooltip.elytratime.green_color"))
            .setDefaultValue(0xFF55FF55.toInt())
            .build())

        colors.addEntry(eb.startAlphaColorField(Text.translatable("option.elytratime.yellow_color"), ElytraTime.config.yellowColor)
            .setSaveConsumer { ElytraTime.config.yellowColor = it }
            .setTooltip(Text.translatable("tooltip.elytratime.yellow_color"))
            .setDefaultValue(0xFFFFFF55.toInt())
            .build())

        colors.addEntry(eb.startAlphaColorField(Text.translatable("option.elytratime.red_color"), ElytraTime.config.redColor)
            .setSaveConsumer { ElytraTime.config.redColor = it }
            .setTooltip(Text.translatable("tooltip.elytratime.red_color"))
            .setDefaultValue(0xFFFF5555.toInt())
            .build())

        colors.addEntry(eb.startAlphaColorField(Text.translatable("option.elytratime.alert_color"), ElytraTime.config.alertColor)
            .setSaveConsumer { ElytraTime.config.alertColor = it }
            .setTooltip(Text.translatable("tooltip.elytratime.alert_color"))
            .setDefaultValue(0xFFFF5555.toInt())
            .build())

        val hud = getOrCreateCategory(Text.translatable("category.elytratime.hud"))
        
        hud.addEntry(eb.startBooleanToggle(Text.translatable("option.elytratime.hud_enabled"), ElytraTime.config.hudEnabled)
            .setSaveConsumer { ElytraTime.config.hudEnabled = it }
            .setTooltip(Text.translatable("tooltip.elytratime.hud_enabled"))
            .setDefaultValue(true)
            .build())

        hud.addEntry(eb.startFloatField(Text.translatable("option.elytratime.hud_scale"), ElytraTime.config.hudScale)
            .setSaveConsumer { ElytraTime.config.hudScale = it }
            .setTooltip(Text.translatable("tooltip.elytratime.hud_scale"))
            .setDefaultValue(1.0f)
            .setMin(0.1f)
            .setMax(10.0f)
            .build())

        hud.addEntry(eb.startIntField(Text.translatable("option.elytratime.hud_x"), ElytraTime.config.hudX)
            .setSaveConsumer { ElytraTime.config.hudX = it }
            .setErrorSupplier {
                ElytraTime.config.hudX = it
                Optional.empty()
            }
            .setTooltip(Text.translatable("tooltip.elytratime.hud_x"))
            .setDefaultValue(5)
            .build())

        hud.addEntry(eb.startIntField(Text.translatable("option.elytratime.hud_y"), ElytraTime.config.hudY)
            .setSaveConsumer { ElytraTime.config.hudY = it }
            .setErrorSupplier {
                ElytraTime.config.hudY = it
                Optional.empty()
            }
            .setTooltip(Text.translatable("tooltip.elytratime.hud_y"))
            .setDefaultValue(5)
            .build())
    }.build()
}
