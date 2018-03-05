package com.aladdin.parts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladdin.parts.entity.Parts;
import com.aladdin.parts.mapper.PartsMapper;

@Service
public class PartsServiceImpl implements PartsService {
	
	@SuppressWarnings("unused")
	private static final int delete = 1;
	private static final int normal = 0;
	
	
	@Autowired
	private PartsMapper partsMapper;

	@Override
	public List<Parts> findAll(int type) {
		Parts parts = new Parts();
		parts.setStatus(normal);
		if (type!=0) {
			parts.setCategory(type);
		}
		return partsMapper.select(parts);
	}

}
