import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class CreateMigration : DefaultTask() {
    var migrationName: String? = null
        @Input get() = field?.replace(" ", "_")
        @Option(option = "migrationName", description = "Migration name") set(name) {
            field = name
        }

    private val migrationDir
        get() = "${super.getProject().projectDir.path}/src/main/resources/db/migration"

    @TaskAction
    fun execute() {
        val timestamp: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
        File("$migrationDir/V${timestamp}__$migrationName.sql").createNewFile()
    }

}

tasks.register<CreateMigration>("createMigration") {
    group = "flyway"
    description = "Create flyway migration with timestamp"
}
