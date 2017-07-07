package com.whl.rabbitmq.pubsub;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.whl.rabbitmq.util.ConnectionUtil;

/**
 * 
 * description: 消息消费者，由服务器异步push到消费者 pub/sub模式测试 此模式类似于子网广播，所有订阅了交换机的队列都能拿到消息
 * 
 * @author whling
 * @date 2017年7月4日 下午3:16:56
 *
 */
public class Consumer1 {
	private final static String QUEUE_NAME = "test_queue_fanout_1";

	private final static String EXCHANGE_NAME = "test_exchange_fanout";

	public static void main(String[] argv) throws Exception {

		// 获取到连接以及mq通道
		Connection connection = ConnectionUtil.getConnection();
		final Channel channel = connection.createChannel();

		// 声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		// 绑定队列到交换机
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

		// 同一时刻服务器只会发一条消息给消费者
		channel.basicQos(1);

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		// 监听队列，手动返回完成
		channel.basicConsume(QUEUE_NAME, false, consumer);

		// 定义队列的消费者
		// QueueingConsumer consumer = new QueueingConsumer(channel);
		// // 监听队列，手动返回完成
		// channel.basicConsume(QUEUE_NAME, false, consumer);
		//
		// // 获取消息
		// while (true) {
		// QueueingConsumer.Delivery delivery = consumer.nextDelivery();
		// String message = new String(delivery.getBody());
		// System.out.println(" [x] Received '" + message + "'");
		// Thread.sleep(10);
		// // 返回读取消息成功标志
		// channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		// }
	}
}
