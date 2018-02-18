package br.com.leonardoferreira.pocamqp;

import br.com.leonardoferreira.pocamqp.service.Receiver;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public MessageListenerAdapter queueListener(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
}
