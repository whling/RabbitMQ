package com.whl.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.whl.rabbitmq.util.ConnectionUtil;

/**
 * 
 * description: 消息消费者，由服务器异步push到消费者
 * 
 * @author whling
 * @date 2017年7月4日 下午3:16:56
 *
 */
public class Consumer {
	private final static String QUEUE_NAME = "test_queue";

	public static void main(String[] argv) throws Exception {

		// 获取到连接以及mq通道
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();

		// 声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		// 定义队列的消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		// 监听队列，TRUE为自动模式，即消费者获取消息，队列就认为消息被消费
		channel.basicConsume(QUEUE_NAME, true, consumer);

		// 获取消息
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			// 如果没有消息则在此阻塞，有消息则可以获取消息，获取消息之后消息被清空
			String message = new String(delivery.getBody());
			System.out.println(" [x] Received '" + message + "'");
		}
	}
}
