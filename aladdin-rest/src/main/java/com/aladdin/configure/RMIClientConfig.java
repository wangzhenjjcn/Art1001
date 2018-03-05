package com.aladdin.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import com.aladdin.city.service.CityService;
import com.aladdin.member.service.MemberService;
import com.aladdin.model.service.BrandService;
import com.aladdin.model.service.CategoryService;
import com.aladdin.model.service.ProductService;
import com.aladdin.order.service.OrderService;
import com.aladdin.render.service.RenderTaskService;

@Configuration  
public class RMIClientConfig {
	
	@Value("${globe.rmihost}")
	private String rmihost;
	
	
    @Bean(name = "brandService")  
    public RmiProxyFactoryBean initBrandService() {  
        RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();  
        factoryBean.setServiceUrl("rmi://"+rmihost+":1099/brandService");  
        factoryBean.setServiceInterface(BrandService.class);  
        return factoryBean;  
    }
    
    @Bean(name = "categoryService")  
    public RmiProxyFactoryBean initCategoryService() {  
        RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();  
        factoryBean.setServiceUrl("rmi://"+rmihost+":1099/categoryService");  
        factoryBean.setServiceInterface(CategoryService.class);  
        return factoryBean;  
    }
    
    @Bean(name = "cityService")  
    public RmiProxyFactoryBean initCityService() {  
        RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();  
        factoryBean.setServiceUrl("rmi://"+rmihost+":1099/cityService");  
        factoryBean.setServiceInterface(CityService.class);  
        return factoryBean;  
    }
    
    @Bean(name = "productService")  
    public RmiProxyFactoryBean initProductService() {  
    	RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();  
    	factoryBean.setServiceUrl("rmi://"+rmihost+":1099/productService");  
    	factoryBean.setServiceInterface(ProductService.class);  
    	return factoryBean;  
    }
    @Bean(name = "orderService")  
    public RmiProxyFactoryBean initOrderService() {  
    	RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();  
    	factoryBean.setServiceUrl("rmi://"+rmihost+":1099/orderService");  
    	factoryBean.setServiceInterface(OrderService.class);  
    	return factoryBean;  
    }
    
   @Bean(name = "memberService")
    public RmiProxyFactoryBean initMemberService() {  
        RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();  
        factoryBean.setServiceUrl("rmi://"+rmihost+":1099/memberService");  
        factoryBean.setServiceInterface(MemberService.class);  
        return factoryBean;  
    }
   @Bean(name = "renderTaskService")
   public RmiProxyFactoryBean initRenderTaskService() {  
	   RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();  
	   factoryBean.setServiceUrl("rmi://"+rmihost+":1099/renderTaskService");  
	   factoryBean.setServiceInterface(RenderTaskService.class);  
	   return factoryBean;  
   }
   
}