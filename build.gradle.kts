plugins {
	id("java")
	id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "me.proton.initsync"
version = "1.0.0"

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

repositories {
	mavenCentral()
	maven("https://papermc.io/repo/repository/maven-public/")
	maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
	compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
	compileOnly("me.clip:placeholderapi:2.11.2")
	compileOnly("net.luckperms:api:5.4")
}

tasks {
	processResources {
		expand(
			 "version" to version
		)
	}
	
	shadowJar {
		archiveFileName.set("MiniJoin.jar")
		duplicatesStrategy = DuplicatesStrategy.EXCLUDE
	}
	
	build { dependsOn(shadowJar) }
}