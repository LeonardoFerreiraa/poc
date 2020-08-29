import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")

    api(project(":client-library"))
    
    testImplementation("io.rest-assured:rest-assured")
}

tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = false
}
