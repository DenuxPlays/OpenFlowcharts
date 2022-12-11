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
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

javafx {
    version = "17"
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

jlink {
    imageZip.set(project.file("${buildDir}/distributions/app-${javafx.platform.classifier}.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = "app"
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}