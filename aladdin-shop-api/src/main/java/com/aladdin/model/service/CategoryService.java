package com.aladdin.model.service;

import java.util.List;

import com.aladdin.base.Pager;
import com.aladdin.model.entity.Category;
import com.aladdin.model.entity.Goods;

public interface CategoryService {

	List<Category> findByParentId(String parentId);
	
	Pager<Goods> findAll(Pager<Goods> pager,String name,String cid,String bid);
	
	Goods findGoodsById(String id);
	
	Category findCategoryById(String id);
	
}
