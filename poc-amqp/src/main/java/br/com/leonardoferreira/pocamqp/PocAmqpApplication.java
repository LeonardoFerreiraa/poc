package br.com.leonardoferreira.pocamqp;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PocAmqpApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocAmqpApplication.class, args);
	}

	public Queue queue() {
		return new Queue("TestQ", false);
	}

}
