package br.com.leonardoferreira.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SimpleScheduler {

    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleScheduler.class);

    private final RabbitTemplate rabbitTemplate;

    private final MeterRegistry meterRegistry;

    public SimpleScheduler(final RabbitTemplate rabbitTemplate,
                           final MeterRegistry meterRegistry) {
        this.rabbitTemplate = rabbitTemplate;
        this.meterRegistry = meterRegistry;
    }

    @Scheduled(fixedDelay = 5000)
    public void addItemToQueue() {
        final SimpleMessage message = new SimpleMessage(UUID.randomUUID().toString());

        LOGGER.info("M=addItemToQueue, I=adding item to queue, simpleMessage={}", message);
        rabbitTemplate.convertAndSend(
                "simple.exchange",
                "",
                message
        );

        Counter.builder("simple.message.published")
                .register(meterRegistry)
                .increment();
    }

}
