package com.aladdin.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aladdin.properties.OssProperties;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;

@Configuration
public class BeanConfigure {
	
	@Autowired
	private OssProperties ossProperties;

	@Bean(name="ossClient")
    public OSSClient ossClient() {
		ClientConfiguration conf = new ClientConfiguration();
		conf.setSupportCname(true);
		OSSClient client = new OSSClient(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret(), conf);
		return client;
    }
}
