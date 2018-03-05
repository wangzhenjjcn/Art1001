package com.aladdin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

import com.aladdin.render.service.ComputerInfoService;


@Configuration  
public class RMIConfig {  
  
    @Autowired
    private ComputerInfoService computerInfoService;
    @Bean  
    public RmiServiceExporter initMemberService(){  
        RmiServiceExporter exporter=new RmiServiceExporter();  
        exporter.setServiceInterface(ComputerInfoService.class);  
        exporter.setServiceName("computerInfoService");  
        exporter.setService(computerInfoService);  
        return exporter;  
    }
  
}  
