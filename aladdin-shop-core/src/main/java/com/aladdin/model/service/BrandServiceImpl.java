package com.aladdin.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladdin.model.entity.Brand;
import com.aladdin.model.mapper.BrandMapper;

@Service("brandService")
public class BrandServiceImpl implements BrandService {
	
	@Autowired
	private BrandMapper brandMapper;

	@Override
	public List<Brand> findAll() {
		Brand brand = new Brand();
		brand.setIs_del(0);
		return brandMapper.select(brand);
	}

	@Override
	public Brand findBrandById(String id) {
		return brandMapper.selectByPrimaryKey(id);
	}
	
}
