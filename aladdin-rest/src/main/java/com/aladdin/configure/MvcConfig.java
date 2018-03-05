package com.aladdin.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.aladdin.policy.AccessTokenAuthPolicy;
import com.aladdin.policy.PermissionPolicy;
import com.aladdin.policy.SourcePolicy;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/aladdin/**")
	            .addResourceLocations("classpath:/aladdin/");
	}
	
	@Autowired
	private AccessTokenAuthPolicy accessTokenAuthPolicy;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(accessTokenAuthPolicy).addPathPatterns("/api/**");
		registry.addInterceptor(new SourcePolicy()).addPathPatterns("/api/**");
		registry.addInterceptor(new PermissionPolicy()).addPathPatterns("/api/**");
		super.addInterceptors(registry);
	}
}
