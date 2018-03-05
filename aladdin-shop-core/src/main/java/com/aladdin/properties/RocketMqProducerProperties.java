package com.aladdin.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(locations = "classpath:conf/rocketMq.properties")
public class RocketMqProducerProperties {
	
	private String producerGroup;
	
	private String nameserver;
	
	private String producerInstance;
	
	private String topic;
	
	

	public String getProducerGroup() {
		return producerGroup;
	}

	public void setProducerGroup(String producerGroup) {
		this.producerGroup = producerGroup;
	}

	public String getNameserver() {
		return nameserver;
	}

	public void setNameserver(String nameserver) {
		this.nameserver = nameserver;
	}

	public String getProducerInstance() {
		return producerInstance;
	}

	public void setProducerInstance(String producerInstance) {
		this.producerInstance = producerInstance;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	
	
	
}
