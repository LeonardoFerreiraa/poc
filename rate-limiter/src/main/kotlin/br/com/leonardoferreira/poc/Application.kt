package br.com.leonardoferreira.poc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript
import org.springframework.data.redis.core.script.RedisScript
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.scripting.support.ResourceScriptSource
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
    fun rateLimiterScriptV2(): RedisScript<List<*>> {
        val redisScript: DefaultRedisScript<List<*>> = DefaultRedisScript<List<*>>()
        redisScript.setScriptSource(ResourceScriptSource(ClassPathResource("META-INF/scripts/rate-limiterv2.lua")))
        redisScript.resultType = List::class.java
        return redisScript
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
@RequestMapping("/rate-limiter")
class MyController(
    private val redisTemplate: RedisTemplate<Any, Long>,
    private val rateLimiterScript: RedisScript<Long>,
    private val rateLimiterScriptV2: RedisScript<List<*>>
) {

    @GetMapping("/v1/{id}")
    fun get(@PathVariable id: String): RateLimitResponse {
        val result = redisTemplate.execute(
            rateLimiterScript,
            listOf("ratelimiter:$id"),
            UUID.randomUUID(),
            System.nanoTime(),
            Duration.ofSeconds(1).toNanos(),
            10
        )

        return RateLimitResponse(
            allowed = result > 0,
            tokensLeft = result - 1
        )
    }

    @GetMapping("/v2/{id}")
    fun getV2(@PathVariable id: String): Any {
        val result: List<*> = redisTemplate.execute(
            rateLimiterScriptV2,
            listOf(
                "rl:$id:tokens",
                "rl:$id:timestamp"
            ),
            10, // replenishRate -> How many requests per second do you want a user to be allowed to do?
            10, // burstCapacity ->  How much bursting do you want to allow?
            1 // requestedTokens -> How many tokens are requested per request?
        )

        return RateLimitResponse(
            allowed = (result[0] as Long) == 1L,
            tokensLeft = result[1] as Long
        )
    }

}

data class RateLimitResponse(
    val allowed: Boolean,
    val tokensLeft: Long
)
