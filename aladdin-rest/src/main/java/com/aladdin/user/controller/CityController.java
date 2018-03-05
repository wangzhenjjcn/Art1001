package com.aladdin.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aladdin.base.BaseController;
import com.aladdin.base.BaseVo2;
import com.aladdin.base.ErrorEnum;
import com.aladdin.city.entity.City;
import com.aladdin.city.service.CityService;

@Controller
@RequestMapping("api/v1/")
public class CityController extends BaseController {

	@Autowired
	private CityService cityService;

	@RequestMapping("city/list/{cityId}")
	public ResponseEntity<?> list(@PathVariable("cityId") String cityId) {
		City city = cityService.findById(cityId);
		List<City> citys = new ArrayList<City>();
		if (city == null) {
			citys = cityService.findByParentId("0");
		}else{
			citys = cityService.findByParentId(city.getCode());
		}
		
		BaseVo2 vo2 = new BaseVo2();
		vo2.setData(citys);
		
		return buildResponseEntity(ErrorEnum.SUCCESS, vo2);
	}

}
