package br.com.leonardoferreira.postgres.copy.service

import org.postgresql.copy.CopyManager
import org.postgresql.core.BaseConnection
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.sql.DataSource

@Service
class BulkContactService(
    private val dataSource: DataSource
) {

    fun bulkInsert(multipartFile: MultipartFile) {
        dataSource.connection.unwrap(BaseConnection::class.java).use { conn ->
            CopyManager(conn)
                .copyIn(
                    """
                    | COPY PUBLIC.CONTACT(NAME, EMAIL, STREET, AGE)
                    | FROM STDIN WITH DELIMITER ','
                    """.trimMargin()
                )
                .apply {
                    writeToCopy(multipartFile.bytes, 0, multipartFile.size.toInt())
                    endCopy()
                }
        }
    }

}
