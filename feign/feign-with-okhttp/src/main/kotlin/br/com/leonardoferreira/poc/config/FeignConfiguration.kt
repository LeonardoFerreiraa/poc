package br.com.leonardoferreira.poc.config

import feign.Logger
import io.micrometer.core.instrument.MeterRegistry
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfiguration {

    @Bean
    fun logger(): Logger =
        FeignLogger()

    @Bean
    fun feignClientTimerInterceptor(registry: MeterRegistry): Interceptor =
        FeignClientTimerInterceptor(registry)

    @Autowired
    fun addInterceptor(
        builder: OkHttpClient.Builder,
        feignClientTimerInterceptor: Interceptor
    ) {
        builder.addInterceptor(feignClientTimerInterceptor)
    }

}