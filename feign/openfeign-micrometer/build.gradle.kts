import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"

    id("org.springframework.boot") version "2.4.1"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
}

java.sourceCompatibility = JavaVersion.VERSION_15

repositories {
    mavenCentral()
}


dependencies {
//    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    
//    implementation("io.micrometer:micrometer-registry-prometheus")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("io.github.openfeign:feign-micrometer:10.10.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2020.0.0")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "15"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
