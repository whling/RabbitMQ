package com.whl.rabbitmq.spring;

/**
 * 
 * description: 在使用spring amqp框架的时候，一个普通的java类变成了一个消费者
 * 	要求，一个类要有公有的方法，方法参数是String类型的用来接收收到的消息
 * @author whling
 * @date 2017年7月5日 上午11:01:28
 *
 */
public class Foo {

    //具体执行业务的方法
    public void listen(String fool) {
        System.out.println("消费者： " + fool);
    }
}