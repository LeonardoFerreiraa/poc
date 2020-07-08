package br.com.leonardoferreira.poc.service

import br.com.leonardoferreira.ReceivedCustomer
import br.com.leonardoferreira.poc.domain.CustomerRequest
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderRecord
import java.util.UUID

@Service
class CustomerService(
    private val receivedCustomerKafkaSender: KafkaSender<String, ReceivedCustomer>
) {

    suspend fun create(customerRequest: CustomerRequest) {
        val receivedCustomer = ReceivedCustomer.newBuilder()
            .setId(UUID.randomUUID().toString().replace("-", ""))
            .setName(customerRequest.name)
            .setEmail(customerRequest.email)
            .build()

        val senderRecord: SenderRecord<String, ReceivedCustomer, String> = SenderRecord.create(
            ProducerRecord<String, ReceivedCustomer>("customer.received", receivedCustomer.id, receivedCustomer),
            receivedCustomer.id
        )

        receivedCustomerKafkaSender.send(Mono.just(senderRecord))
            .awaitSingle()
    }

}
