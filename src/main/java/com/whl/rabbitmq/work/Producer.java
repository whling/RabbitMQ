package com.whl.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.whl.rabbitmq.util.ConnectionUtil;

/**
 * 
 * description: 消息生产者，测试work模式下消息的消费情况
 * @author whling
 * @date 2017年7月5日 上午10:51:12
 *
 */
public class Producer {
	private final static String QUEUE_NAME = "test_queue_work";

	public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        for (int i = 0; i < 50; i++) {
            // 消息内容
            String message = "" + i;
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

//            Thread.sleep(i * 10);
        }

        channel.close();
        connection.close();
    }
}
