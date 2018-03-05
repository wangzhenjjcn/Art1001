package com.aladdin.security;

import java.util.ArrayList;  
import java.util.Collection;  
import java.util.List;  
import java.util.Set;  
  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.FilterInvocation;
  
public class MyInvocationSecurityMetadataSourceService implements  
        FilterInvocationSecurityMetadataSource {  
  
  
    @Override  
    public Collection<ConfigAttribute> getAttributes(Object object)  
            throws IllegalArgumentException {  
        String url = ((FilterInvocation) object).getRequestUrl();  
        int firstQuestionMarkIndex = url.indexOf("?");  
        if (firstQuestionMarkIndex != -1) {  
            url = url.substring(0, firstQuestionMarkIndex);  
        }  
        System.out.println("url:" + url);  
        List<ConfigAttribute> result = new ArrayList<ConfigAttribute>();  
        ConfigAttribute attribute = new SecurityConfig("ROLE_BASE");  
        result.add(attribute);  
        try {  
//            List<Perm> permList = permDao.findPermByUri(url);  
//            if (permList != null && permList.size() > 0) {  
//                for (Perm perm : permList) {  
//                    Set<Role> roles = perm.getRoles();  
//                    if (roles != null && roles.size() > 0) {  
//                        for (Role role : roles) {  
//                            ConfigAttribute conf = new SecurityConfig(  
//                                    role.getRolename());  
//                            result.add(conf);  
//                        }  
//                    }  
//                }  
//            }  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
  
    @Override  
    public Collection<ConfigAttribute> getAllConfigAttributes() {  
        // TODO Auto-generated method stub  
        return null;  
    }  
  
    @Override  
    public boolean supports(Class<?> clazz) {  
        // TODO Auto-generated method stub  
        return true;  
    }  
  
}  
