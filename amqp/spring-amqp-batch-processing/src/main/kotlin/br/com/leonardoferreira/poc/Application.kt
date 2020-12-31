package br.com.leonardoferreira.poc

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.UUID

@EnableScheduling
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Configuration
class RabbitConfig {

    @Bean
    fun messageConverter(objectMapper: ObjectMapper): MessageConverter =
        Jackson2JsonMessageConverter(objectMapper)

    @Bean
    fun consumerBatchContainerFactory(
        connectionFactory: ConnectionFactory,
        messageConverter: MessageConverter
    ): SimpleRabbitListenerContainerFactory {
        val containerFactory = SimpleRabbitListenerContainerFactory()

        containerFactory.setConnectionFactory(connectionFactory)

        containerFactory.setBatchListener(true)
        containerFactory.setConsumerBatchEnabled(true)
        containerFactory.setBatchSize(2)
        containerFactory.setReceiveTimeout(Duration.ofSeconds(25).toMillis())
        containerFactory.setMessageConverter(messageConverter)

        return containerFactory
    }
    
    @Bean
    fun myQueue(): Queue =
        QueueBuilder.durable("my-queue")
            .build()

}

data class MyObject(val content: String)

@Component
class ScheduledProducer(
    val rabbitTemplate: RabbitTemplate
) {

    @Scheduled(fixedDelay = 1_000)
    fun produce() {
        val myObject = MyObject(UUID.randomUUID().toString())
        println("producer: $myObject")

        rabbitTemplate.convertAndSend(DirectExchange.DEFAULT.name, "my-queue", myObject)
    }

}

@Component
class SimpleListener {

    @RabbitListener(queues = ["my-queue"], containerFactory = "consumerBatchContainerFactory")
    fun listen(myObjects: List<MyObject>) {
        println("listener: $myObjects")
    }

}
