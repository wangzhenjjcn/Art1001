package com.aladdin.configure;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aladdin.properties.RocketMqProducerProperties;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

@Configuration
public class BeanConfig {
	
	@Autowired
	private RocketMqProducerProperties rocketMqProducerProperties;
	
	//创建消息生成者实例
	@Bean(name="defaultMQProducer")
	public DefaultMQProducer mqProducer(){
		
		DefaultMQProducer producer=new DefaultMQProducer(rocketMqProducerProperties.getProducerGroup());
		producer.setNamesrvAddr(rocketMqProducerProperties.getNameserver());
		producer.setInstanceName(rocketMqProducerProperties.getProducerInstance());
		
		System.out.println("生成producer");
		
		return producer;
	}

}
