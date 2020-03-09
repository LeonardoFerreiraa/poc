package br.com.leonardoferreira.jmx

import org.springframework.jmx.export.annotation.ManagedOperation
import org.springframework.jmx.export.annotation.ManagedOperationParameter
import org.springframework.jmx.export.annotation.ManagedOperationParameters
import org.springframework.jmx.export.annotation.ManagedResource
import org.springframework.stereotype.Component

@Component
@ManagedResource
class FirstJmx {

    @ManagedOperation(
        description = "returns a greeting"
    )
    fun greeting(): String =
        "hello world"

    @ManagedOperation(
        description = "returns a greeting by name"
    )
    @ManagedOperationParameters(
        ManagedOperationParameter(
            name = "name",
            description = "name to be greeted"
        )
    )
    fun greeting(name: String): String =
        "hello $name"

}
