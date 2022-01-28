plugins {
    id("java")
    id("eclipse")
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.0.1"
    kotlin("jvm") version "1.6.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "com.notice"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven ("https://papermc.io/repo/repository/maven-public/")
    maven ("https://oss.sonatype.org/content/groups/public/")
    maven ("https://repo.aikar.co/content/groups/aikar/")
    maven ("https://jitpack.io")
    maven ("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://repo.onarandombox.com/content/groups/public/")
    maven("https://repo.minebench.de/")
    maven("https://repo.fvdh.dev/releases")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
    compileOnly("org.maxgamer:QuickShop:6.0.0.0-BETA-20220126.084450-1")
    compileOnly("com.github.TechFortress:GriefPrevention:16.18-RC1")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.10-RC")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-api:1.5.0")
    implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:1.5.0")
    implementation("co.aikar:acf-paper:0.5.0-SNAPSHOT")
    implementation("net.kyori:adventure-text-minimessage:4.1.0-SNAPSHOT")
    implementation("org.spongepowered:configurate-hocon:4.1.2")
    implementation("org.spongepowered:configurate-extra-kotlin:4.1.2")
    compileOnly("dev.frankheijden.insights:Insights:6.10.0")

}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
        kotlinOptions.javaParameters = true
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    shadowJar {
        relocate("co.aikar.commands", "com.noticemc.noticebarrel.acf")
        relocate("co.aikar.locales", "com.noticemc.noticebarrel.acf.locales")
        archiveClassifier.set("")
    }
    build {
        dependsOn(shadowJar)
    }
}