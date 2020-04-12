package br.com.leonardoferreira.poc.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.kafka.transaction.ChainedKafkaTransactionManager;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TransactionalConfig {

    @Bean
    public JpaTransactionManager jpaTransactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    @Primary
    public ChainedKafkaTransactionManager<?, ?> transactionManager(
            final JpaTransactionManager jpaTransactionManager,
            final KafkaTransactionManager<?, ?> kafkaTransactionManager
    ) {
        return new ChainedKafkaTransactionManager<>(jpaTransactionManager, kafkaTransactionManager);
    }

}
