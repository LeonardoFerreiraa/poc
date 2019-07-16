package br.com.leonardoferreira.postgres.copy.controller

import br.com.leonardoferreira.postgres.copy.extension.runWithStopWatch
import br.com.leonardoferreira.postgres.copy.service.BulkContactService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/contacts")
class ContactController(
    private val bulkContactService: BulkContactService
) {

    private val log = LoggerFactory.getLogger(ContactController::class.java)

    @PostMapping
    fun bulkInsert(
        @RequestParam("file") multipartFile: MultipartFile
    ): ResponseEntity<*> {
        runWithStopWatch {
            bulkContactService.bulkInsert(multipartFile)
        }
        
        return ResponseEntity<Void>(HttpStatus.CREATED)
    }

}
