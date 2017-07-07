package com.whl.rabbitmq.springboot;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
	/**
	 * springboot 自动配置
	 */
	@Autowired
	private ConnectionFactory connectionFactory;

	/**
	 * 配置管理
	 * 
	 * @return
	 */
	@Bean
	public RabbitAdmin RabbitAdmin() {
		return new RabbitAdmin(connectionFactory);
	}

	/**
	 * 声明队列
	 * 默认是自动声明
	 * @return
	 */
	@Bean
	public Queue helloQueue() {
		return new Queue("hello-queue");
	}

	/**
	 * 声明队列
	 * 
	 * @return
	 */
	@Bean
	public Queue helloQueueSpringboot() {
		return new Queue("springboot-queue");
	}
}