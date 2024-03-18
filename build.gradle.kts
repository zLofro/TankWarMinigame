plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.papermc.paperweight.userdev") version "1.3.6"
    id("xyz.jpenilla.run-paper") version "1.0.6"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
}

group = "me.lofro"
version = "1.0.0-SNAPSHOT"
description = "Tank War mini-game for Minecraft 1.20.4 developed for Dedsafio Games."

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}


repositories {
    repositories {
        maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    }
    maven {
        url = uri("https://repo.aikar.co/content/groups/aikar/")
    }

    mavenCentral()
}

dependencies {
    // Paper.
    paperDevBundle("1.20.4-R0.1-SNAPSHOT")

    // Bukkit.
    implementation("net.kyori:adventure-platform-bukkit:4.3.2")

    // Paper NMS.
    implementation("co.aikar:acf-paper:0.5.1-SNAPSHOT")

    // ProjectLombok.
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    testCompileOnly("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.24")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
    shadowJar {
        relocate ("co.aikar.taskchain", "me.lofro.taskchain")
        relocate ("co.aikar.commands", "me.lofro.acf")
        relocate ("co.aikar.locales", "me.lofro.locales")
    }
}

bukkit {
    main = "me.lofro.TankWarMinigame"
    version = "1.0"
    apiVersion = "1.20"
    author = "Lofro"
    website = "https://github.com/zLofro"
}