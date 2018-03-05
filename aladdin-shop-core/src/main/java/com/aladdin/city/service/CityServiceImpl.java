package com.aladdin.city.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladdin.city.entity.City;
import com.aladdin.city.mapper.CityMapper;

@Service
public class CityServiceImpl implements CityService {
	
	@Autowired
	private CityMapper cityMapper;

	@Override
	public City findById(String id) {
		City city = new City();
		city.setCode(id);
		return cityMapper.selectOne(city);
	}

	@Override
	public List<City> findByParentId(String id) {
		City city = new City();
		city.setParentId(id);
		return cityMapper.select(city);
	}

}
