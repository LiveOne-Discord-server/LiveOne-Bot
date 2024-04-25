package com.liveOne.github.botDS.configuration;

import dev.dejvokep.boostedyaml.YamlDocument;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MConfig {

    public static YamlDocument config;
    static {
        try {
          config = YamlDocument.create(new File("config.yml"), Objects.requireNonNull(MConfig.class.getClassLoader().getResourceAsStream("config.yml")));
        } catch (IOException e) {throw new RuntimeException(e);}
    }


    public static void saveConfiguration() {
        try {
            config.save();
        } catch (Exception e) {System.out.printf("Failed to save file %s", e.getCause());}}

}
