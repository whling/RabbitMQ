package com.whl.rabbitmq.util;

import java.io.IOException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {

	public static Connection getConnection() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		 //设置服务地址
        factory.setHost("hadoop01");
        //端口
        factory.setPort(5672);
        //设置账号信息，用户名、密码、vhost
        factory.setVirtualHost("/whling");
        factory.setUsername("whling");
        factory.setPassword("whling");
        // 通过工程获取连接
        Connection connection = factory.newConnection();
        return connection;
	}

}
