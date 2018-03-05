package com.aladdin.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

import com.aladdin.city.service.CityService;
import com.aladdin.member.service.MemberService;
import com.aladdin.model.service.BrandService;
import com.aladdin.model.service.CategoryService;
import com.aladdin.model.service.ProductService;
import com.aladdin.order.service.OrderService;
import com.aladdin.render.service.RenderTaskService;


@Configuration  
public class RMIConfig {  
  
    @Autowired  
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CityService cityService;
    @Autowired
    private RenderTaskService renderTaskService;
    @Bean  
    public RmiServiceExporter initMemberService(){  
        RmiServiceExporter exporter=new RmiServiceExporter();  
        exporter.setServiceInterface(MemberService.class);  
        exporter.setServiceName("memberService");  
        exporter.setService(memberService);  
        return exporter;  
    }
    
    @Bean  
    public RmiServiceExporter initBrandService(){  
        RmiServiceExporter exporter=new RmiServiceExporter();  
        exporter.setServiceInterface(BrandService.class);  
        exporter.setServiceName("brandService");  
        exporter.setService(brandService);  
        return exporter;  
    }
    
    @Bean  
    public RmiServiceExporter initCategoryService(){  
        RmiServiceExporter exporter=new RmiServiceExporter();  
        exporter.setServiceInterface(CategoryService.class);  
        exporter.setServiceName("categoryService");  
        exporter.setService(categoryService);  
        return exporter;  
    }
    
    @Bean  
    public RmiServiceExporter initProductService(){  
        RmiServiceExporter exporter=new RmiServiceExporter();  
        exporter.setServiceInterface(ProductService.class);  
        exporter.setServiceName("productService");  
        exporter.setService(productService);  
        return exporter;  
    }
    @Bean  
    public RmiServiceExporter initOrderService(){  
    	RmiServiceExporter exporter=new RmiServiceExporter();  
    	exporter.setServiceInterface(OrderService.class);  
    	exporter.setServiceName("orderService");  
    	exporter.setService(orderService);  
    	return exporter;  
    }
    
    @Bean  
    public RmiServiceExporter initCityService(){  
        RmiServiceExporter exporter=new RmiServiceExporter();  
        exporter.setServiceInterface(CityService.class);  
        exporter.setServiceName("cityService");  
        exporter.setService(cityService);  
        return exporter;  
    }
    @Bean  
    public RmiServiceExporter initRenderTaskService(){  
    	RmiServiceExporter exporter=new RmiServiceExporter();  
    	exporter.setServiceInterface(RenderTaskService.class);  
    	exporter.setServiceName("renderTaskService");  
    	exporter.setService(renderTaskService);  
    	return exporter;  
    }
}  
