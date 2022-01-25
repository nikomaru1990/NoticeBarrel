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
}

dependencies {
    implementation("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("co.aikar:acf-paper:0.5.0-SNAPSHOT")
    implementation("com.github.TechFortress:GriefPrevention:16.17.1")
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