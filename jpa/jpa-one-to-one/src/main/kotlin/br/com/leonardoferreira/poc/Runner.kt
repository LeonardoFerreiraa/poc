package br.com.leonardoferreira.poc

import br.com.leonardoferreira.poc.domain.Customer
import br.com.leonardoferreira.poc.domain.CustomerAddress
import br.com.leonardoferreira.poc.repository.CustomerAddressRepository
import br.com.leonardoferreira.poc.repository.CustomerRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class Runner(
    private val customerRepository: CustomerRepository,
    private val customerAddressRepository: CustomerAddressRepository
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        (1..15)
            .map { System.currentTimeMillis() + it }
            .forEach { sharedId ->
                val customer = Customer(
                    id = sharedId,
                    name = "customer $sharedId",
                    email = "customer$sharedId@email.com"
                )

                customerRepository.save(customer)

                val customerAddress = CustomerAddress(
                    id = sharedId,
                    street = "street $sharedId",
                    city = "city $sharedId",
                    number = "$sharedId"
                )

                customerAddressRepository.save(customerAddress)
            }
    }

}