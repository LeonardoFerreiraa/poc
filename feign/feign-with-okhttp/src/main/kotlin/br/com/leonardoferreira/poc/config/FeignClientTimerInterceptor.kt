package br.com.leonardoferreira.poc.config

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class FeignClientTimerInterceptor(
    private val registry: MeterRegistry
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val sample = Timer.start(registry)

        val outcome = try {
            ClientResponse.Success(chain.proceed(request))
        } catch (e: Exception) {
            ClientResponse.Error(e)
        }

        sample.stop(
            Timer.builder("http.client.requests")
                .tag("uri", request.url().toString())
                .tag("method", request.method())
                .tag("status", outcome.status)
                .register(registry)
        )

        return when (outcome) {
            is ClientResponse.Success -> outcome.response
            is ClientResponse.Error -> throw outcome.exception
        }
    }

    sealed class ClientResponse {

        abstract val status: String

        data class Success(val response: Response) : ClientResponse() {
            override val status: String
                get() = "${response.code()}"
        }

        data class Error(val exception: Exception) : ClientResponse() {
            override val status: String
                get() = if (exception is IOException)
                    "IO_ERROR"
                else
                    "CLIENT_ERROR"
        }

    }

}