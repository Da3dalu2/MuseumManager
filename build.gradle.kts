plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "5.2.0"
}
repositories {
    mavenCentral()
    jcenter()
}

val javaFXModules = listOf(
    "base",
    "controls",
    "fxml",
    "swing",
    "graphics"
)

val supportedPlatforms = listOf("linux", "mac", "win")

dependencies {

    // controlfx for JavaFx
    compile("org.controlsfx:controlsfx:11.0.2")

    // mysql connector
    compile("mysql:mysql-connector-java:8.0.21")
    
    // Logging with logback and slf4j 
  	compile("org.slf4j:slf4j-api:1.7.30")
    compile("ch.qos.logback:logback-classic:1.2.3")
	compile("ch.qos.logback:logback-core:1.2.3")

    for (platform in supportedPlatforms) {
        for (module in javaFXModules) {
            implementation("org.openjfx:javafx-$module:13:$platform")
        }
    }

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

application {
    mainClassName = "application.Launcher"
}
