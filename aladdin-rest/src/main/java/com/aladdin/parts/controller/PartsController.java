package com.aladdin.parts.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aladdin.base.BaseController;
import com.aladdin.base.BaseVo2;
import com.aladdin.base.ErrorEnum;
import com.aladdin.parts.entity.Parts;
import com.aladdin.parts.service.PartsService;

@Controller
@RequestMapping("/api/v1/parts")
public class PartsController extends BaseController {

	@Autowired
	private PartsService partsService;

	@RequestMapping(value = "/list")
	public ResponseEntity<?> list(HttpServletRequest request,
			@RequestParam(value = "type", defaultValue = "0", required = true) Integer type) {
		List<Parts> result = partsService.findAll(type);
		BaseVo2 vo = new BaseVo2();
		vo.setData(result);
		return buildResponseEntity(ErrorEnum.SUCCESS,vo);
	}
	
}
