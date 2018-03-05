package com.aladdin.render.service;

import java.util.List;

import com.aladdin.render.entity.RenderTask;

public interface RenderTaskService {
	
	public int create(RenderTask rt);
	
	public RenderTask findById(String id);
	
	public int update(RenderTask rt);
	
	public List<RenderTask> findNotRendered();
	
	
}
