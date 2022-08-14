plugins {
    kotlin("jvm") version "1.7.10"
    id("java")
    id("application")
    id("idea")
    id("io.freefair.lombok") version "5.3.0"
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.springframework.boot") version "2.7.2"
}
apply(plugin = "io.spring.dependency-management")
apply(plugin = "kotlin-kapt")

val javaFXVersion = "18.0.2"
javafx {
    version = javaFXVersion
    modules = listOf("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("tdc.FXMain")

    println(gradle.gradleHomeDir)

    group = "TestDataCreatorByDB"
    version = "0.0.1-SNAPSHOT"
    description = """"""

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.isDeprecation = true
        options.release.set(11)
    }
    tasks.jar {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}

repositories {
//    maven(url = "https://nexus.gluonhq.com/nexus/content/repositories/releases/")
    mavenCentral()
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-test")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    implementation("org.openjfx:javafx-controls:18.0.2")
    implementation("org.openjfx:javafx-fxml:18.0.2")

    implementation("com.h2database:h2:2.1.214")
    implementation("javax.inject:javax.inject:1")
    implementation("mysql:mysql-connector-java:8.0.30")
    implementation("com.microsoft.sqlserver:mssql-jdbc:10.2.1.jre17")
    implementation("com.typesafe:config:1.4.2")
    implementation("org.apache.commons:commons-lang3")

    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("ch.qos.logback:logback-core:1.2.11")
    implementation("org.slf4j:jcl-over-slf4j:1.7.36")
    implementation("org.slf4j:jul-to-slf4j:1.7.36")
    implementation("org.slf4j:log4j-over-slf4j:1.7.36")

    implementation("org.thymeleaf:thymeleaf:3.0.15.RELEASE")

    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}