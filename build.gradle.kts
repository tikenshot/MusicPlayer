plugins {
    java
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "net.tmkspace"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        name = "simplevoicechat-api"
        url = uri("https://maven.maxhenkel.de/repository/public")
    }
    maven {
        name = "scarsz"
        url = uri("https://nexus.scarsz.me/content/groups/public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.5-R0.1-SNAPSHOT")

    // bStats dependency
    implementation("org.bstats:bstats-bukkit:3.1.0")

    // SimpleVoiceChat API
    implementation("de.maxhenkel.voicechat:voicechat-api:2.5.27")

    implementation("github.scarsz:configuralize:1.4.1") {
        exclude(module = "json-simple")
        exclude(module = "snakeyaml")
    }
}

tasks {
    // Task for starting paper server with plugin
    runServer {
        // Server version
        minecraftVersion("1.21.5")

        downloadPlugins {
            modrinth("simple-voice-chat", "bukkit-2.5.29")
        }
    }
}

val targetJavaVersion = 21
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"

    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible()) {
        options.release.set(targetJavaVersion)
    }
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}