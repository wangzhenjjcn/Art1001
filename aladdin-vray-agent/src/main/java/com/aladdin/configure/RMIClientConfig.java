package com.aladdin.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import com.aladdin.render.service.ComputerInfoService;
import com.aladdin.render.service.RenderTaskService;

@Configuration
public class RMIClientConfig {
	
	@Value("${globe.rmihost}")
	private String rmihost;

    @Bean(name = "renderTaskService")
    public RmiProxyFactoryBean initRenderTaskService() {  
 	   RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();  
 	   factoryBean.setServiceUrl("rmi://"+rmihost+":1099/renderTaskService");  
 	   factoryBean.setServiceInterface(RenderTaskService.class);  
 	   return factoryBean;  
    }
    
    @Bean(name = "computerInfoService")
    public RmiProxyFactoryBean initComputerInfoService() {  
 	   RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();  
 	   factoryBean.setServiceUrl("rmi://"+rmihost+":1099/computerInfoService");  
 	   factoryBean.setServiceInterface(ComputerInfoService.class);  
 	   return factoryBean;  
    }
    
}