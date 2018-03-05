package com.aladdin;

import javax.swing.Spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.aladdin.properties.RocketMqProducerProperties;
import com.aladdin.util.SpringUtil;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;

@Configuration
@ComponentScan
@EnableAutoConfiguration 
@EnableConfigurationProperties({RocketMqProducerProperties.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);	
		
		DefaultMQProducer producer=(DefaultMQProducer) SpringUtil.getBean("defaultMQProducer");
		try {
			producer.start();
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
