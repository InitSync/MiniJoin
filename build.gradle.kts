plugins {
	java
	id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "me.proton.initsync"
version = "1.0.0"

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

repositories {
	mavenLocal()
	maven("https://jitpack.io/")
	maven("https://papermc.io/repo/repository/maven-public/")
	maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
	mavenCentral()
}

dependencies {
	compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
	compileOnly("com.github.4drian3d:MiniPlaceholders:1.0.0")
	compileOnly("me.clip:placeholderapi:2.11.2")
	
	implementation("com.github.MegavexNetwork.scoreboard-library:v1_18_R2:-SNAPSHOT")
	implementation("com.github.MegavexNetwork.scoreboard-library:implementation:-SNAPSHOT")
}

tasks {
	processResources {
		expand(
			 "name" to rootProject.name,
			 "version" to version,
			 "author" to "InitSync"
		)
	}
	shadowJar {
		archiveFileName.set("PaperBoard.jar")
		duplicatesStrategy = DuplicatesStrategy.EXCLUDE
		
		relocate("net.megavex.scoreboardlibrary", "me.proton.initsync.minitab.libs.scoreboardlibrary")
	}
	build {
		dependsOn(shadowJar)
	}
}