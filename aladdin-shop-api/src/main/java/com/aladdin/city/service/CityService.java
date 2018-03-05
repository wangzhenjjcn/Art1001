package com.aladdin.city.service;

import java.util.List;

import com.aladdin.city.entity.City;

public interface CityService {
	
	City findById(String id);
	
	List<City> findByParentId (String id);
	

}
