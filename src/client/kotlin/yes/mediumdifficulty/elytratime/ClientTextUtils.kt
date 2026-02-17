package yes.mediumdifficulty.elytratime

import net.minecraft.util.Language

object ClientTextUtils {
    fun getTooltipFormat(): String =
        ElytraTime.config.tooltipFormat.takeIf { it.isNotEmpty() } ?: getValueFromKey("value.elytratime.tooltip_format")

    fun getTimeFormat(): String =
        ElytraTime.config.timeFormat.takeIf { it.isNotEmpty() } ?: getValueFromKey("value.elytratime.time_format")

    fun getTimeReportFormat(): String =
        ElytraTime.config.timeReportFormat.takeIf { it.isNotEmpty() } ?: getValueFromKey("value.elytratime.time_report_format")

    fun getValueFromKey(key: String): String =
        Language.getInstance().get(key)
}
