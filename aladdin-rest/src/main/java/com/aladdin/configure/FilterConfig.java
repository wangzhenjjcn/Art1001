package com.aladdin.configure;

import javax.servlet.Filter;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aladdin.filter.MyCORSFilter;

@Configuration
public class FilterConfig {
	
	@Bean
	public Filter corsFilter(){
		return new MyCORSFilter();
	}
	
	@Bean
	public FilterRegistrationBean someFilterRegistration() {

	    FilterRegistrationBean registration = new FilterRegistrationBean();
	    registration.setFilter(corsFilter());
	    registration.addUrlPatterns("/api/*");
	    registration.setOrder(1);
	    return registration;
	} 
	
}
