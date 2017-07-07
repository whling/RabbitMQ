package com.whl.rabbitmq.springboot;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "springboot-queue")
public class Consumer {
	
	@RabbitHandler
	public void handler(String message) {
		System.out.println("receive: " + message);
	}
}
