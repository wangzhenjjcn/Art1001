package com.aladdin.model.service;

import com.aladdin.model.entity.Model;
import com.aladdin.model.entity.ModelFunction;
import com.aladdin.model.entity.ModelMeta;
import com.aladdin.model.entity.Sku;

import java.util.List;
import java.util.Map;
import com.aladdin.base.Pager;


public interface ModelService {

	
	public Pager<Model> find(Map<String,Object> paramsMap,Pager<Model> pager);
	
	public Model find(String id,String userId);
	
	int delete(String modelId);
	/*
	 * 查询模型及其关联表信息
	 */
	public Model findSingleModelById(String id);
	
	public int save(Model m);
	
	public Model selectByKey(String id);

	public boolean edit(Model m);
	
	/**
	 * 添加商品模型
	 * @return
	 */
	public boolean saveProductModel(Model m);
	
	/**
	 * 添加模型，返回id
	 * @param m
	 * @return
	 */
	String saveProductModel1(Model m);
	
	public int selectCount(Map<String, Object> map);
	
	/**
	 * 获取所有单品模型
	 * @return
	 */
	public List<Model> selectGoodsModel();


	public List<Model> findBySku(Sku sku);

	
	public List<Model> findByCategory(String categoryId);

	
	/**
	 * 条件查询modelmetta
	 * 
	 */
	ModelMeta selectModelMetaByCondition(ModelMeta modelMeta);
	
	
	List<ModelMeta> selectModelMetaListByModelId(String modelId);
	
	
	List<ModelFunction> selectModelFunctionByModelId(String modelId);

	


}
