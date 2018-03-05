package com.aladdin.security.service;

import java.util.List;

import com.aladdin.security.entity.AppBean;

public interface AppService {
	
	public int createApp(AppBean app);
	
	public int remove(String appId);
	
	public boolean isExists(AppBean app);
	
	public AppBean findById(String appId);
	
	public List<AppBean> find(AppBean app);

	public int update(AppBean app);

}
