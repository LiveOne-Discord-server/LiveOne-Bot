package com.liveOne.github.botDS.configuration

import dev.dejvokep.boostedyaml.YamlDocument
import java.io.*

object MConfig {
    val config: YamlDocument = YamlDocument.create(File("config.yml"), javaClass.classLoader.getResourceAsStream("config.yml")!!)


    fun saveConfiguration() {
        try {
            config.save()
        } catch (e: Exception) {System.out.printf("Failed to save file %s", e.cause)} }
}

