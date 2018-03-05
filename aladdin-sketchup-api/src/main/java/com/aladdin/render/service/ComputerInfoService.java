package com.aladdin.render.service;

import com.aladdin.render.entity.ComputerInfo;

public interface ComputerInfoService {
	
	public int create(ComputerInfo ci);
	
	public ComputerInfo findByIp(String ip); 

	public int update(ComputerInfo ci); 

}
