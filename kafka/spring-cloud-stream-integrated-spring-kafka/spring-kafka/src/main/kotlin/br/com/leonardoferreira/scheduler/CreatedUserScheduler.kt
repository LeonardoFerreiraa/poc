package br.com.leonardoferreira.scheduler

import br.com.leonardoferreira.CreatedUser
import br.com.leonardoferreira.property.KafkaProperties
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.internals.RecordHeader
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.Random

@Component
class CreatedUserScheduler(
    private val kafkaProperties: KafkaProperties,
    private val kafkaTemplate: KafkaTemplate<String, CreatedUser>,
    private val random: Random = Random()
) {

    private val log = LoggerFactory.getLogger(CreatedUserScheduler::class.java)

    @Scheduled(fixedRate = 5_000)
    fun createUser() {
        val id = random.nextPositiveLong()

        val createdUser: CreatedUser = CreatedUser.newBuilder()
            .setId(id)
            .setName("user $id")
            .setEmail("user$id@email.com")
            .build()

        kafkaTemplate.send(buildProducerRecord(createdUser))
            .completable()
            .get()

        log.info("M=createUser, I=user created, id={}", id)
    }

    private fun buildProducerRecord(createdUser: CreatedUser): ProducerRecord<String, CreatedUser> =
        ProducerRecord(
            kafkaProperties.topic.createdUser,
            null,
            null,
            createdUser.id.toString(),
            createdUser,
            listOf(RecordHeader("x-user-origin", buildOrigin().toByteArray()))
        )

    private fun buildMessage(createdUser: CreatedUser): Message<CreatedUser> =
        MessageBuilder
            .withPayload(createdUser)
            .setHeader(KafkaHeaders.TOPIC, kafkaProperties.topic.createdUser)
            .setHeader(KafkaHeaders.MESSAGE_KEY, createdUser.id.toString())
            .setHeader("x-user-origin", buildOrigin())
            .build()

    private fun buildOrigin(): String =
        if (random.nextBoolean()) {
            "web"
        } else {
            "mobile"
        }

    private fun Random.nextPositiveLong(): Long =
        this.nextLong().let {
            if (it > 0)
                it
            else
                it * -1
        }
}
