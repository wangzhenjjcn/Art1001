package com.aladdin.model.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aladdin.base.BaseController;
import com.aladdin.base.BaseVo2;
import com.aladdin.base.ErrorEnum;
import com.aladdin.model.entity.Brand;
import com.aladdin.model.service.BrandService;

@Controller
@RequestMapping("/api/v1/brands")
public class BrandController extends BaseController {

	@Autowired
	private BrandService brandService;
	
	@RequestMapping(value = "/list")
	public ResponseEntity<?> test(HttpServletRequest request) {
		List<Brand> result = brandService.findAll();
		BaseVo2 vo = new BaseVo2();
		vo.setData(result);
		return buildResponseEntity(ErrorEnum.SUCCESS,vo);
	}

}
