package yes.mediumdifficulty.elytratime

import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ElytraTime : ModInitializer {
    override fun onInitialize() {
        LOGGER.info("Initialised")
    }

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger("ElytraTime")
        @JvmField
        var config: Config = Config.fromFileOrDefault()
    }
}
