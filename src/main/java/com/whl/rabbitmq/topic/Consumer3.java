package com.whl.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.whl.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.QueueingConsumer;

/**
 * 
 * description: 消息消费者，由服务器异步push到消费者 topic模式测试
 * 相同consumer3和consumer1实现业务功能相同（同时，绑定的routing规则也一样）， 
 * 监听同一个队列，使用work能者多劳，实现负载均衡
 * 
 * @author whling
 * @date 2017年7月4日 下午3:16:56
 *
 */
public class Consumer3 {
	private final static String QUEUE_NAME = "test_queue_topic_1";

	private final static String EXCHANGE_NAME = "test_exchange_topic";

	public static void main(String[] argv) throws Exception {

		// 获取到连接以及mq通道
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();

		// 声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		// 绑定队列到交换机
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "item.update");
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "item.delete");
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "item.*");

		// 同一时刻服务器只会发一条消息给消费者
		channel.basicQos(1);
		// 定义队列的消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		// 监听队列，手动返回完成
		channel.basicConsume(QUEUE_NAME, false, consumer);

		// 获取消息
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(" [x] Received '" + message + "'");
			Thread.sleep(10);

			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}
}
