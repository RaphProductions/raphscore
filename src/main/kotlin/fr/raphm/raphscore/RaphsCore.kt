package fr.raphm.raphscore

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object RaphsCore : ModInitializer {
    private val logger = LoggerFactory.getLogger("raphscore")

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello Fabric world!")
	}
}