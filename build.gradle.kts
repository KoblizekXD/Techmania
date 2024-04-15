plugins {
    id("fabric-loom") version "1.6-SNAPSHOT"
    java
    `maven-publish`
}

fun ext(name: String): String {
    return project.ext[name] as String
}

group = ext("maven_group")
version = ext("mod_version")

base {
    archivesName = ext("archives_base_name")
}

repositories {
    mavenCentral()
}

loom {
    mods {
        create(ext("archives_base_name")) {
            sourceSet("main")
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${ext("minecraft_version")}")
    mappings("net.fabricmc:yarn:${ext("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${ext("loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${ext("fabric_version")}")
}

tasks.withType<JavaCompile>().configureEach {
    this.options.release = 17
}

tasks.withType<Jar> {
    from("LICENSE") {
        rename { "${it}_${project.base.archivesName.get()}"}
    }
}

tasks.withType<ProcessResources> {
    inputs.property("version", project.version)

    filesMatching("fabric.mod.json") {
        expand(mapOf("version" to project.version))
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }

    repositories {

    }
}