plugins {
    id("java")
    id("java-library")
    alias(libs.plugins.shadow)
    id("maven-publish")
}

// store the version as a variable,
// as we use it several times
val fullVersion = "1.0.0"

// project settings
group = "me.kubbidev.spellcaster"
version = "1.0-SNAPSHOT"

base {
    archivesName.set("spellcaster")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    // include source in when publishing
    withSourcesJar()
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")

    // internal dependencies
    compileOnly("me.kubbidev.nexuspowered:nexuspowered:1.0-SNAPSHOT")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "spellcaster"

            from(components["java"])
            pom {
                name = "SpellCaster"
                description = "A spell casting Minecraft plugin. Create and cast unique spells with dynamic abilities and comprehensive entity statistics management."
                url = "https://kubbidev.com"

                licenses {
                    license {
                        name = "Apache-2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                    }
                }

                developers {
                    developer {
                        id = "kubbidev"
                        name = "kubbi"
                        url = "https://kubbidev.com"
                        email = "kubbidev@gmail.com"
                    }
                }

                issueManagement {
                    system = "Github"
                    url = "https://github.com/kubbidev/SpellCaster/issues"
                }
            }
        }
    }
}

// building task operations
tasks.processResources {
    filesMatching("plugin.yml") {
        expand("pluginVersion" to fullVersion)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.shadowJar {
    archiveFileName = "SpellCaster-${fullVersion}.jar"

    dependencies {
        include(dependency("me.kubbidev.spellcaster:.*"))
    }

    manifest {
        attributes["paperweight-mappings-namespace"] = "mojang"
    }
}

artifacts {
    archives(tasks.shadowJar)
}