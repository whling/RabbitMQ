package com.whl.rabbitmq.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.whl.rabbitmq.springboot.App;
import com.whl.rabbitmq.springboot.Producer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class SpringbootTest {
	@Autowired
	private Producer producer;

	@Test
	public void hello() throws Exception {
		producer.send();
	}
}
