package br.com.leonardoferreira.poc.repository

import br.com.leonardoferreira.poc.domain.CustomerAddress
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerAddressRepository : JpaRepository<CustomerAddress, Long>
