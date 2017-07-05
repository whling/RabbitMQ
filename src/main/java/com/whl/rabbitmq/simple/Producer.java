package com.whl.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.whl.rabbitmq.util.ConnectionUtil;

/**
 * 
 * description: 消息的生产者
 * @author whling
 * @date 2017年7月5日 上午10:50:09
 *
 */
public class Producer {
	private final static String QUEUE_NAME = "test_queue";

	public static void main(String[] argv) throws Exception {
		// 获取到连接以及mq通道
		Connection connection = ConnectionUtil.getConnection();
		// 从连接中创建通道
		Channel channel = connection.createChannel();

		// 声明（创建）队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		

		for(int i=0;i<100;i++){
			// 消息内容
			String message = "Hello World!"+i;
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		}
		// 关闭通道和连接
		channel.close();
		connection.close();
	}
}
