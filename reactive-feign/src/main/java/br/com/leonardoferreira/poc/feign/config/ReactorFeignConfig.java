package br.com.leonardoferreira.poc.feign.config;

import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ReactorFeignConfig {

    @Bean
    @Scope("prototype")
    public Feign.Builder builder(ReactorContract reactorContract) {
        return Feign.builder()
                .contract(reactorContract)
                .invocationHandlerFactory(ReactorInvocationHandler::new);
    }

}
