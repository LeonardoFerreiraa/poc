package br.com.leonardoferreira.poc.service

import br.com.leonardoferreira.poc.domain.response.CustomerAddressResponse
import br.com.leonardoferreira.poc.domain.response.CustomerResponse
import br.com.leonardoferreira.poc.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
) {

    fun findAll(): List<CustomerResponse> =
        customerRepository.findAll()
            .map { customer ->
                val customerAddress = customer.customerAddress

                CustomerResponse(
                    id = customer.id,
                    name = customer.name,
                    email = customer.email,
                    createdAt = customer.createdAt,
                    updatedAt = customer.updatedAt,
                    address = CustomerAddressResponse(
                        street = customerAddress.street,
                        city = customerAddress.city,
                        number = customerAddress.number,
                        createdAt = customerAddress.createdAt,
                        updatedAt = customerAddress.updatedAt
                    )
                )
            }

}