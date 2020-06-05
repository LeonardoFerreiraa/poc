package br.com.leonardoferreira.poc

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("rabbitmq")
data class RabbitPropertyMap(
    val queues: Map<String, RabbitProperties>
)
