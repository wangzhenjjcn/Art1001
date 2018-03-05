package com.aladdin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.aladdin.properties.OssProperties;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableConfigurationProperties({OssProperties.class})  
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
