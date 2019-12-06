package br.com.leonardoferreira

import org.springframework.boot.WebApplicationType
import org.springframework.fu.kofu.application
import org.springframework.fu.kofu.webmvc.webMvc

val app = application(WebApplicationType.SERVLET) {
    beans {
        bean<MyService>()
    }
    webMvc {
        router {
            GET("/") {
                val myService = ref<MyService>()
                ok().body(myService.greeting())
            }
        }
        converters {
            string()
            jackson {
                indentOutput = true
            }
        }
    }
}

fun main() {
    app.run()
}

class MyService {
    fun greeting() = mapOf("ola" to "mundo")
}


data class Time(val id: String, val name: String) {
    init {
        ...
    }
}



