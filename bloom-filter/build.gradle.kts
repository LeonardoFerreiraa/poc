plugins {
    kotlin("jvm") version "1.4.21"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))

    implementation("com.google.guava:guava:30.1-jre")
}
