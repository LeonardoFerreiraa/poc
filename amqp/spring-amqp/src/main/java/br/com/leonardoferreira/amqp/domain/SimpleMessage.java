package br.com.leonardoferreira.amqp.domain;

import br.com.leonardoferreira.amqp.config.RabbitConfig;
import br.com.leonardoferreira.amqp.config.TypeId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeId("br.com.leonardoferreira.amqp.domain.MessageSimple") // NAO FAZ DIFERENCA NA VERSAO DO SPRING-AMQP DESSE PROJETO
public class SimpleMessage {

    private String message;

}
