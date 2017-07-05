package com.whl.rabbitmq.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.whl.rabbitmq.util.ConnectionUtil;

public class Producer {
	private final static String EXCHANGE_NAME = "test_exchange_fanout";

	public static void main(String[] argv) throws Exception {
		// 获取到连接以及mq通道
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		// 声明exchange
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		// 消息内容
		String message = "id=1001";
		//消息被发送到交换机上,此模式下routing key设置为空
		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");

		channel.close();
		connection.close();
	}
}
