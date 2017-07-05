package com.whl.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.whl.rabbitmq.util.ConnectionUtil;
/**
 * 
 * description: 消息路由测试 routing(消息生产需要指定routing key:这个路由键没有通配符匹配，所以只能指定具体key)
 * @author whling
 * @date 2017年7月4日 下午7:22:00
 *
 */
public class Producer {
	private final static String EXCHANGE_NAME = "test_exchange_direct";

	public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // 消息内容
        String message = "id=1001的商品新增了";
        channel.basicPublish(EXCHANGE_NAME, "insert", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
