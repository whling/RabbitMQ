package com.whl.rabbitmq.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * description: springboot 整合 rabbitmq
 *   先运行启动类app,然后运行测试类springbootTest
 * @author whling
 * @date 2017年7月6日 下午7:31:16
 *
 */
@SpringBootApplication
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class,args);
	}
}
