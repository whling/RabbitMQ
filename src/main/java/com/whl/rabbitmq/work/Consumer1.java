package com.whl.rabbitmq.work;

import java.io.IOException;
import java.util.concurrent.SynchronousQueue;

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
 * description: 消息消费者，由服务器异步push到消费者
 * 	work模式测试，如果没有指定basicQos，则在此情况下是每个消费者消费消息是均衡的
 * 	如果采用fair dispatch公平分发策略，那么就是指定basicQos为1，那么此时消息必须要设置为手动确认模式,否则这个设置失效，用的还是round robin
 *  注：如果所有的工作者都处于繁忙状态，你的队列有可能被填充满。你可能会观察队列的使用情况，然后增加工作者，或者使用别的什么策略(round robin或者fair dispatch)。
 *  
 *  没有任何消息超时限制；只有当消费者挂掉时，RabbitMQ才会重新投递。即使处理一条消息会花费很长的时间。
 *  如果消息中间件挂了，那么最好先设置持久化队列，然后再设置持久化消息
 * @author whling
 * @date 2017年7月4日 下午3:16:56
 *
 */
public class Consumer1 {
	private final static String QUEUE_NAME = "test_queue_work";

	public static void main(String[] argv) throws Exception {

        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列(此队列是持久化队列，那么就可以使用持久化消息)
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        // 同一时刻服务器只会发一条消息给消费者，所以能者多劳
        channel.basicQos(1);
        
        Consumer consumer = new DefaultConsumer(channel){
        	@Override
        	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
        			throws IOException {
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		System.out.println(new String(body,"utf-8"));
        		//手动确认消息接收成功
//        		channel.basicAck(envelope.getDeliveryTag(), false);
        	}
        };
        
     // 监听队列，手动返回完成，手动模式
        channel.basicConsume(QUEUE_NAME, true, consumer);

       /* // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列，手动返回完成，手动模式
        channel.basicConsume(QUEUE_NAME, false, consumer);

        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            //休眠
            Thread.sleep(50);
            // 返回确认状态
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }*/
    }
}
