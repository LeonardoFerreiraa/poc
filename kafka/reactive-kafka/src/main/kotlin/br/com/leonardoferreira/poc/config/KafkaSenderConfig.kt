package br.com.leonardoferreira.poc.config

import br.com.leonardoferreira.ReceivedCustomer
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderOptions

@Configuration
class KafkaSenderConfig {

    @Bean
    fun receivedCustomerKafkaSender(): KafkaSender<String, ReceivedCustomer> {
        val senderOptions: SenderOptions<String, ReceivedCustomer> = SenderOptions.create<String, ReceivedCustomer>()
            .producerProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
            .producerProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
            .producerProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer::class.java)
            .producerProperty("schema.registry.url", "http://localhost:8081")
            .producerProperty("specific.avro.reader", true)

        return KafkaSender.create(senderOptions)
    }

}
