package com.aladdin.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(locations = "classpath:conf/socketMq.properties")
public class SocketMqCousumerProperties {
	
	private String nameserver;
	
	private String pushCustomerGroup;
	
	private String pushCustomerInstance;
	
	private String topic;

	public String getNameserver() {
		return nameserver;
	}

	public void setNameserver(String nameserver) {
		this.nameserver = nameserver;
	}


	public String getPushCustomerGroup() {
		return pushCustomerGroup;
	}

	public void setPushCustomerGroup(String pushCustomerGroup) {
		this.pushCustomerGroup = pushCustomerGroup;
	}

	public String getPushCustomerInstance() {
		return pushCustomerInstance;
	}

	public void setPushCustomerInstance(String pushCustomerInstance) {
		this.pushCustomerInstance = pushCustomerInstance;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	
	
	
	
}
