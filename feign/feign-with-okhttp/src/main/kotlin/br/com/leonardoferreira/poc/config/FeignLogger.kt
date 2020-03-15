package br.com.leonardoferreira.poc.config

import feign.Logger
import feign.Request
import feign.Response
import feign.Util
import org.slf4j.LoggerFactory
import java.io.IOException
import java.nio.charset.Charset

class FeignLogger : Logger() {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun logRequest(configKey: String, logLevel: Level, request: Request) {
        log.info("configKey={}, method={}, url={}", configKey, request.httpMethod(), request.url())

        if (logLevel.ordinal >= Level.HEADERS.ordinal) {
            log.info("configKey={}, headers={}", configKey, request.headers())

            if (request.body() != null && logLevel.ordinal >= Level.FULL.ordinal) {
                log.info("configKey={}, body={}", configKey, String(request.body(), Charset.forName("UTF-8")))
            }
        }
    }

    override fun logRetry(configKey: String, logLevel: Level?) {
        log.info("I=retry, configKey={}", configKey)
    }

    override fun logAndRebufferResponse(
        configKey: String,
        logLevel: Level,
        response: Response,
        elapsedTime: Long
    ): Response {
        log.info(
            "configKey={}, status={}, elapsedTime={}",
            configKey, response.status(), elapsedTime
        )

        if (logLevel.ordinal >= Level.HEADERS.ordinal) {
            log.info(
                "configKey={}, headers={}",
                configKey,
                response.headers()
            )

            if (response.body() != null && !(response.status() == 204 || response.status() == 205)) {
                val bodyData = Util.toByteArray(response.body().asInputStream())
                if (logLevel.ordinal >= Level.FULL.ordinal && bodyData.isNotEmpty()) {
                    log.info(
                        "configKey={}, body={}",
                        configKey, Util.decodeOrDefault(bodyData, Util.UTF_8, "Binary data")
                    )
                }
                return response.toBuilder().body(bodyData).build()
            }
        }
        return response
    }

    override fun logIOException(
        configKey: String,
        logLevel: Level,
        ioe: IOException,
        elapsedTime: Long
    ): IOException {
        log.info(
            "configKey={}, elapsedTime={}, E={}, Err={}",
            configKey, elapsedTime, ioe.javaClass.simpleName, ioe.message
        )

        if (logLevel.ordinal >= Level.FULL.ordinal) {
            log.error("configKey={}", configKey, ioe)
        }

        return ioe
    }

    override fun log(configKey: String, format: String, vararg args: Any) {
        log.info(String.format(methodTag(configKey) + format, *args))
    }

}