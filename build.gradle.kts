plugins {
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.jetbrains:annotations:23.0.0")
    implementation("org.fusesource.jansi:jansi:2.4.0")
}
