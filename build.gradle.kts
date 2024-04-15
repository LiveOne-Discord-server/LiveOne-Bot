plugins {
    kotlin("jvm") version "1.9.23"
    id("com.github.johnrengelman.shadow") version "8.1.1" // ShadowJar
    application
}

group = "com.liveOne.github.botDS"
version = "0.0.1"

kotlin {
    jvmToolchain(19)
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://oss.sonatype.org/content/repositories/snapshots") // SonaType
}

dependencies {
    // Discord
    implementation("net.dv8tion:JDA:5.0.0-beta.22")
    implementation("ch.qos.logback:logback-classic:1.3.6")
    implementation("dev.dejvokep:boosted-yaml:1.3") // BoostedYaml
}

tasks {
    shadowJar {
        archiveBaseName.set("LiveOne-Bot")
        archiveVersion.set("$version")
        archiveClassifier.set("")
        relocate("dev.dejvokep.boostedyaml", "com.liveOne.libs.BoostedYaml") }

    application { mainClass = "com.liveOne.github.botDS.MainKt" }
}