package br.com.leonardoferreira.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleListener.class);

    private final SimpleRepository simpleRepository;

    private final MeterRegistry meterRegistry;

    public SimpleListener(final SimpleRepository simpleRepository,
                          final MeterRegistry meterRegistry) {
        this.simpleRepository = simpleRepository;
        this.meterRegistry = meterRegistry;
    }

    @RabbitListener(
            concurrency = "5",
            bindings = {
                    @QueueBinding(
                            key = "",
                            exchange = @Exchange(
                                    name = "simple.exchange",
                                    type = "topic"
                            ),
                            value = @Queue(
                                    name = "simple.queue",
                                    durable = "true"
                            )
                    )
            }
    )
    public void simpleListener(final SimpleMessage simpleMessage) {
        LOGGER.info("M=simpleListener, simpleMessage={}", simpleMessage);
        simpleRepository.save(simpleMessage);

        meterRegistry.counter("simple.message.consumed")
                .increment();
    }

}
