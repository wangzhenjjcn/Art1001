package com.aladdin.parts.service;

import java.util.List;

import com.aladdin.parts.entity.Parts;


public interface PartsService {

	List<Parts> findAll(int type);
	
}
