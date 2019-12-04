package br.com.leonardoferreira.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("kafka")
data class KafkaProperties(
    val topic: Topic
)

@ConstructorBinding
data class Topic(
    val createdUser: String
)
