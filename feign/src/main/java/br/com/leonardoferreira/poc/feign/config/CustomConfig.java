package br.com.leonardoferreira.poc.feign.config;

import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class CustomConfig {

    @Bean
    @Scope("prototype")
    public Feign.Builder builder(CustomContract customContract) {
        return Feign.builder()
                .contract(customContract)
                .invocationHandlerFactory(ReactorInvocationHandler::new);
    }

}
