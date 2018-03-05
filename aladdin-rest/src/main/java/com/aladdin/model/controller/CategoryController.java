package com.aladdin.model.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aladdin.base.BaseController;
import com.aladdin.base.BaseVo2;
import com.aladdin.base.ErrorEnum;
import com.aladdin.model.entity.Category;
import com.aladdin.model.service.CategoryService;
import com.aladdin.model.vo.CategoryVO;

@Controller
@RequestMapping("/api/v1/categorys")
public class CategoryController extends BaseController {

	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = "/list")
	public ResponseEntity<?> list(HttpServletRequest request) {
		List<Category> result = categoryService.findByParentId("0");
		
		List<CategoryVO> caVos_1 = new ArrayList<>();
		
		for (Category category : result) {
			CategoryVO categoryVO = (CategoryVO) po2vo(category, new CategoryVO());
			List<Category> result2 = categoryService.findByParentId(categoryVO.getGc_id());
			
			List<CategoryVO> caVos_2 = new ArrayList<>();
			
			for (Category category2 : result2) {
				CategoryVO categoryVO2 = (CategoryVO) po2vo(category2, new CategoryVO());
				List<Category> result3 = categoryService.findByParentId(categoryVO2.getGc_id());
				
				
				List<CategoryVO> caVos_3 = new ArrayList<>();
				for (Category category3 : result3) {
					CategoryVO categoryVO3 = (CategoryVO) po2vo(category3, new CategoryVO());
					caVos_3.add(categoryVO3);
				}
				
				categoryVO2.setCategoryVOs(caVos_3);
				
				caVos_2.add(categoryVO2);
			}
			categoryVO.setCategoryVOs(caVos_2);
			caVos_1.add(categoryVO);
		}
		
		BaseVo2 vo = new BaseVo2();
		vo.setData(caVos_1);
		return buildResponseEntity(ErrorEnum.SUCCESS,vo);
	}

}
