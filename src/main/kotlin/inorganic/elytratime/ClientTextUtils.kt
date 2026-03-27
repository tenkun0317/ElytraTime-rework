package inorganic.elytratime

object ClientTextUtils {
    fun getTooltipFormat(): String =
        ElytraTime.config.tooltipFormat.takeIf { it.isNotEmpty() } ?: getValueFromKey("value.elytratime.tooltip_format")

    fun getHudFormat(): String {
        if (ElytraTime.config.useSameFormatForHudAndTooltip) {
            return getTooltipFormat()
        }
        return ElytraTime.config.hudFormat.takeIf { it.isNotEmpty() } ?: getValueFromKey("value.elytratime.tooltip_format")
    }

    fun getTimeFormat(): String =
        ElytraTime.config.timeFormat.takeIf { it.isNotEmpty() } ?: getValueFromKey("value.elytratime.time_format")

    fun getTimeReportFormat(): String =
        ElytraTime.config.timeReportFormat.takeIf { it.isNotEmpty() } ?: getValueFromKey("value.elytratime.time_report_format")

    fun getValueFromKey(key: String): String = key
}