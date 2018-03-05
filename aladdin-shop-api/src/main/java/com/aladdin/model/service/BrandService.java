package com.aladdin.model.service;

import java.util.List;

import com.aladdin.model.entity.Brand;

public interface BrandService {

	List<Brand> findAll();
	
	Brand findBrandById(String id);
	
}