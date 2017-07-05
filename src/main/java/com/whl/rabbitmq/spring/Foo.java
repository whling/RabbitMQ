package com.whl.rabbitmq.spring;


public class Foo {

    //具体执行业务的方法
    public void listen(String fool) {
        System.out.println("消费者： " + fool);
    }
}