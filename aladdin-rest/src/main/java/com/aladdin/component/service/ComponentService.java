package com.aladdin.component.service;

import java.util.List;
import java.util.Map;

import com.aladdin.base.Pager;
import com.aladdin.component.entity.Component;

public interface ComponentService {

	public Pager<Component> findByPager(Map<String, Object> params);

	public List<Component> findPath(Component component);

	public Component find(String id);

	public List<Component> findChildren(Component component);
	
	public int save(Component com);

	public int update(Component item);

	public int remove(Component item);

	public List<Component> find(Map<String, Object> map);

}
