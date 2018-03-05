package com.aladdin.render.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aladdin.properties.RocketMqProducerProperties;
import com.aladdin.render.entity.RenderTask;
import com.aladdin.render.mapper.RenderTaskMapper;
import com.aladdin.render.service.RenderTaskService;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

@Service
public class RenderTaskServiceImpl implements RenderTaskService {
	
	@Autowired
	private RenderTaskMapper renderTaskMapper; 
	
	@Override
	public int create(RenderTask rt) {
		
		int i=0;		
		if (StringUtils.isNotEmpty(rt.getName()) 
				&& StringUtils.isNotEmpty(rt.getInputUrl()) 
				&& rt.getState() != null
				&& rt.getBeginTime() != null) {			
			i= renderTaskMapper.insertSelective(rt);
			if(i==1){//添加成功
				//发送消息
				String id=rt.getId();
				sendMqMessage(id);				
			}
		}
		return i;
	}
	
	private void sendMqMessage(String id){	
	
			try {
				//defaultMqProducer.start();
				Message msg = new Message(
						rocketMqProducerProperties.getTopic(),// topic
						null,// tag
						id,// key
						id.getBytes());// body
				SendResult sendResult = defaultMqProducer.send(msg);
				System.out.println(sendResult);
				//defaultMqProducer.shutdown();
			} catch (Exception e) {
				System.out.println("发布消息异常");
				e.printStackTrace();
			}

	}
	
	@Autowired
	private DefaultMQProducer defaultMqProducer;
	
	@Autowired
	private RocketMqProducerProperties rocketMqProducerProperties;

	@Override
	public RenderTask findById(String id) {
		RenderTask rt = null;
		if(id!=null){
			rt = renderTaskMapper.selectByPrimaryKey(id);
		}
		return rt;
	}

	@Override
	public int update(RenderTask rt) {
		
		if(rt!=null&&rt.getId()!=null){
			return renderTaskMapper.updateByPrimaryKeySelective(rt);
		}
		return 0;
	}

	@Override
	public List<RenderTask> findNotRendered() {
		
		RenderTask rt = new RenderTask();
		rt.setState(1);
		
		return renderTaskMapper.select(rt);
	}

}
