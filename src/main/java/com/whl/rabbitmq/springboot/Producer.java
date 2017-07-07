package com.whl.rabbitmq.springboot;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send() throws Exception {
		System.out.println("send:" + new Date());
		rabbitTemplate.convertAndSend("springboot-queue","whling");
	}
}
