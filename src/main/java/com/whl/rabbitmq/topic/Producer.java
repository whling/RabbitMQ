package com.whl.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.whl.rabbitmq.util.ConnectionUtil;
/**
 * 
 * description: 消息路由测试topic(topic支持routing key通配符模式(#:匹配多个，*:匹配一个))
 * @author whling
 * @date 2017年7月4日 下午7:22:00
 *
 */
public class Producer {
	private final static String EXCHANGE_NAME = "test_exchange_topic";

	public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        // 消息内容
        String message = "id=1001";
        channel.basicPublish(EXCHANGE_NAME, "item.hello", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
