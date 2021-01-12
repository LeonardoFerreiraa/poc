package br.com.leonardoferreira.poc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.script.RedisScript
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration
import java.util.UUID


@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Configuration
class RateLimitCOnfiguration {

    @Bean
    fun rateLimiterScript(): RedisScript<Long> {
        val classPathResource = ClassPathResource("META-INF/scripts/rate-limiter.lua")
        return RedisScript.of(classPathResource, Long::class.java)
    }


    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<Any, Long> =
        RedisTemplate<Any, Long>().also {
            it.keySerializer = RedisSerializer.string()
            it.valueSerializer = RedisSerializer.json()
            it.setConnectionFactory(redisConnectionFactory)
        }

}

@RestController
@RequestMapping("/tps")
class MyController(
    private val redisTemplate: RedisTemplate<Any, Long>,
    private val rateLimiterScript: RedisScript<Long>
) {

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): Long =
        redisTemplate.execute(
            rateLimiterScript,
            listOf("ratelimiter:$id"),
            UUID.randomUUID(),
            System.nanoTime(),
            Duration.ofMinutes(1).toNanos(),
            10
        )

}
