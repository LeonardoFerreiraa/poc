package br.com.leonardoferreira.poc.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class LoggerPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        log.info("beanName={}", beanName);

        if ("dataSource".equals(beanName)) {
            return LoggerAdvice.of(bean);
        }

        return bean;
    }

}
