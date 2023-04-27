import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.apache.tools.ant.filters.ReplaceTokens
import org.panteleyev.jpackage.ImageType

plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.panteleyev.jpackageplugin") version "1.5.2"

    //Java fx
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "dev.denux"
version = "0.1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
}

javafx {
    version = "19"
    modules = listOf("javafx.controls", "javafx.fxml")
}

repositories {
    mavenCentral()
}

val lombokVersion = "1.18.26"

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")

    //Logging
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("ch.qos.logback:logback-classic:1.4.7")

    //Code safety
    implementation("com.google.code.findbugs:jsr305:3.0.2")

    //Lombok's annotations (Utility annotations)
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

application {
    mainModule.set("dev.denux.flowcharts")
    mainClass.set("dev.denux.flowcharts.LaunchHelper")
    applicationName = "OpenFlowcharts"
}

tasks.jpackage {
    dependsOn(shadowJar)

    input = "$buildDir/libs"
    destination = "$buildDir/dist"

    appName = "OpenFlowcharts"
    appVersion = project.version.toString()
    vendor = "denux.dev"
    appDescription = "OpenFlowcharts is a free and open source flowchart editor."
    licenseFile = "LICENSE"

    mainJar = tasks.shadowJar.get().archiveFileName.get()
    mainClass = "dev.denux.flowcharts.LaunchHelper"

    javaOptions = listOf("-Dfile.encoding=UTF-8")

    linux {
        type = ImageType.DEFAULT
        linuxShortcut = true
    }

    windows {
        type = ImageType.MSI
        winDirChooser = true
        winMenu = true
        winMenuGroup = "OpenFlowcharts"
        winShortcut = true
        winShortcutPrompt = true
        winPerUserInstall = true
    }

    mac {
        type = ImageType.DMG
    }
}

val shadowJar: ShadowJar by tasks
val jar : Jar by tasks

shadowJar.archiveClassifier.set("withDependencies")

val sourcesForRelease = task<Copy>("sourcesForRelease") {
    from("src/main/java") {
        include("**/Constants.java")
        val tokens = mapOf(
            "version" to version.toString()
        )
        // Allow for setting null on some strings without breaking the source
        // for this, we have special tokens marked with "!@...@!" which are replaced to @...@
        filter { it.replace(Regex("\"!@|@!\""), "@") }
        // Then we can replace the @...@ with the respective values here
        filter<ReplaceTokens>(mapOf("tokens" to tokens))
    }
    into("build/filteredSrc")
    includeEmptyDirs = false
    outputs.upToDateWhen { false }
}

val generateJavaSources = task<SourceTask>("generateJavaSources") {
    val javaSources = sourceSets["main"].allJava.filter {
        it.name != "Constants.java"
    }.asFileTree

    source = javaSources + fileTree(sourcesForRelease.destinationDir)
    dependsOn(sourcesForRelease)
}


tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    source = generateJavaSources.source
    dependsOn(generateJavaSources)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
