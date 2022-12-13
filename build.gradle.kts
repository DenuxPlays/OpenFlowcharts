import org.apache.tools.ant.filters.ReplaceTokens;

plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.beryx.jlink") version "2.25.0"
}

group = "dev.denux"
version = "1.0.0-alpha.1"

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

val lombokVersion = "1.18.24"

dependencies {
    implementation("org.controlsfx:controlsfx:11.1.2")
    implementation("net.synedra:validatorfx:0.4.0") {
        exclude(group = "org.openjfx")
    }

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.1")

    //Logging
    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation("ch.qos.logback:logback-classic:1.4.5")

    //Code safety
    implementation("com.google.code.findbugs:jsr305:3.0.2")

    //Lombok's annotations (Utility annotations)
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

application {
    mainModule.set("dev.denux")
    mainClass.set("dev.denux.Run")
}

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

jlink {
    imageZip.set(project.file("${buildDir}/distributions/app-${javafx.platform.classifier}.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = "app"
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    source = generateJavaSources.source
    dependsOn(generateJavaSources)
}

tasks.withType<Test> {
    useJUnitPlatform()
}