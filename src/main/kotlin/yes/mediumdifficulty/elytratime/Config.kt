package yes.mediumdifficulty.elytratime

import com.google.gson.GsonBuilder
import net.fabricmc.loader.api.FabricLoader
import java.io.File

class Config {
    enum class AlertType {
        CHAT,
        ACTION_BAR,
        TITLE
    }

    @JvmField var tooltipEnabled = true
    @JvmField var tooltipFormat = ""
    @JvmField var timeFormat = ""
    @JvmField var timeReportFormat = ""
    @JvmField var alertThresholdSeconds = 60
    @JvmField var alertThresholdEnabled = true
    @JvmField var alertType = AlertType.ACTION_BAR
    @JvmField var yellowThreshold = 25
    @JvmField var redThreshold = 10
    @JvmField var greenColor = 0xFF55FF55.toInt()
    @JvmField var yellowColor = 0xFFFFFF55.toInt()
    @JvmField var redColor = 0xFFFF5555.toInt()
    @JvmField var alertColor = 0xFFFF5555.toInt()
    @JvmField var hudEnabled = true
    @JvmField var hudScale = 1.0f
    @JvmField var hudX = 5
    @JvmField var hudY = 5

    fun saveToFile() {
        val file = FabricLoader.getInstance().configDir.resolve(CONFIG_FILE).toFile()

        try {
            if (file.createNewFile()) {
                ElytraTime.LOGGER.info("Created new config file")
            }
            file.writeText(GSON.toJson(this))
            ElytraTime.LOGGER.info("Saved config")
        } catch (e: Exception) {
            ElytraTime.LOGGER.error("Error occurred while saving config: ${e.message}")
        }
    }

    companion object {
        const val CONFIG_FILE = "elytratime.json"
        private val GSON = GsonBuilder().setPrettyPrinting().create()

        fun fromFileOrDefault(): Config {
            val configPath = FabricLoader.getInstance().configDir.resolve(CONFIG_FILE)
            val file = configPath.toFile()

            if (file.exists()) {
                try {
                    return GSON.fromJson(file.readText(), Config::class.java)
                } catch (e: Exception) {
                    ElytraTime.LOGGER.error("Error occurred while loading config: ${e.message}")
                }
            }
            return Config()
        }
    }
}
