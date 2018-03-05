package com.aladdin.configure;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aladdin.properties.SocketMqCousumerProperties;
import com.aladdin.render.entity.RenderTask;
import com.aladdin.render.service.RenderTaskService;
import com.aladdin.schedule.ScheduleTask;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;

@Configuration
public class BeanConfig {

	@Autowired
	private SocketMqCousumerProperties socketMqCousumerProperties;
	
	@Autowired
	private RenderTaskService renderTaskService;
	
	@Autowired
	private ScheduleTask scheduleTask;
	
	@Bean(name="mqConsumer")
	public DefaultMQPushConsumer defaultMQPushConsumer() {
		
		DefaultMQPushConsumer consumer=new DefaultMQPushConsumer(
				socketMqCousumerProperties.getPushCustomerGroup());	
		try {
			consumer.setNamesrvAddr(socketMqCousumerProperties.getNameserver());
			consumer.setInstanceName(socketMqCousumerProperties.getPushCustomerInstance());	
			//设置消息监听
			consumer.subscribe(socketMqCousumerProperties.getTopic(), "*");
			consumer.registerMessageListener(new MessageListenerConcurrently() {			
				@Override
				public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {			
					if(msgs!=null && msgs.size()>0){
						for(MessageExt msg:msgs){						
							//System.out.println(msg.getTopic()+"---"+msg.getTags()+"---"+new String(msg.getBody()));							
							String renderId=new String(msg.getBody());
							if(StringUtils.isNotEmpty(renderId)){
								RenderTask task=renderTaskService.findById(renderId);
								if(task!=null){
									//执行渲染
									System.out.println(task.getId());
									scheduleTask.renderfile(task);
								}
							}
							
						}
					}
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
			});
			
			System.out.println("生成pushConsumer");
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return consumer;		
	}
}
