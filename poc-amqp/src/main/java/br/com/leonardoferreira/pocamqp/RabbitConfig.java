package br.com.leonardoferreira.pocamqp;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by lferreira on 2/15/18
 */
//@Configuration
public class RabbitConfig {

//    @Bean
//    @Primary
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory factory = new CachingConnectionFactory();
//        factory.setAddresses("10.5.0.2:5672,10.5.0.3:5672");
//        factory.setUsername("guest");
//        factory.setPassword("guest");
//        factory.setVirtualHost("/");
//        return factory;
//    }

//    @Bean("secondConnectionFactory")
//    public ConnectionFactory secondConnectionFactory() {
//        CachingConnectionFactory factory = new CachingConnectionFactory();
//        factory.setUsername("guest");
//        factory.setPassword("guest");
//        factory.setVirtualHost("/second");
//        return factory;
//    }
}
