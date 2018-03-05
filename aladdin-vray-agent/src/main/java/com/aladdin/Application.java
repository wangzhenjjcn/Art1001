package com.aladdin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.aladdin.properties.OssProperties;
import com.aladdin.properties.SocketMqCousumerProperties;
import com.aladdin.properties.ZipProperties;
import com.aladdin.render.Watcher;
import com.aladdin.utils.SpringUtil;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableConfigurationProperties({
	ZipProperties.class,OssProperties.class,SocketMqCousumerProperties.class}) 
//@EnableScheduling
public class Application {
		
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		Watcher watcher = (Watcher)SpringUtil.getBean("watcher");
		watcher.watch();
	
		try {
			//启动消费者消息
			DefaultMQPushConsumer mqConsumer=(DefaultMQPushConsumer) SpringUtil.getBean("mqConsumer");
			mqConsumer.start();
			System.out.println("启动 mqconsumer");
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
