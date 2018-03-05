package com.aladdin.security.service;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladdin.security.entity.AppBean;
import com.aladdin.security.mapper.AppMapper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class AppServiceImpl implements AppService {
  
	@Autowired
	private AppMapper appMapper;
	
	@Override
	public int createApp(AppBean app) {
		if(app.getId()==null){
			app.setId(UUID.randomUUID().toString());
		}
		app.setStatus(1);
		return appMapper.insertSelective(app);
	}

	@Override
	public int remove(String appId) {
		AppBean app = appMapper.selectByPrimaryKey(appId);
		if(app==null){
			return 0;
		}
		app.setStatus(0);
		int i = appMapper.updateByPrimaryKey(app);
		return i;
	}

	@Override
	public boolean isExists(AppBean app) {
		Example ex = new Example(AppBean.class);
		Criteria c = ex.createCriteria();
		if(app==null){
			return false;
		}
		if(app.getName()!=null&&!"".equals(app.getName())){
			c.andEqualTo("name", app.getName());
		}
		if(app.getCode()!=null&&!"".equals(app.getCode())){
			c.andEqualTo("code", app.getCode());
		}
		if(app.getId()!=null&&!"".equals(app.getId())){
			c.andEqualTo("id", app.getId());
		}
		c.andEqualTo("status", 1);
	 	List<AppBean> apps = appMapper.selectByExample(ex);
		return !(apps==null||apps.isEmpty());
	}

	@Override
	public AppBean findById(String appId) {
		return appMapper.selectByPrimaryKey(appId);
	}

	@Override
	public int update(AppBean app) {
		int i= 0;
		if(app.getId()!=null&&!"".equals(app.getId().trim())){
			i = appMapper.updateByPrimaryKeySelective(app);
		}
		return i;
	}

	@Override
	public List<AppBean> find(AppBean app) {
		
		Example ex = new Example(AppBean.class);
		
		app.setStatus(1);
		
		return null;
	}

}
