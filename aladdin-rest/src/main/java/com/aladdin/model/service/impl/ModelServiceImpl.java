package com.aladdin.model.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladdin.base.Pager;
import com.aladdin.model.ModelContants;
import com.aladdin.model.entity.Model;
import com.aladdin.model.entity.ModelFunction;
import com.aladdin.model.entity.ModelMeta;
import com.aladdin.model.entity.Sku;
import com.aladdin.model.mapper.ModelFunctionMapper;
import com.aladdin.model.mapper.ModelMapper;
import com.aladdin.model.mapper.ModelMetaMapper;
import com.aladdin.model.service.ModelService;
import com.aladdin.utils.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("modelService")
public class ModelServiceImpl implements ModelService {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ModelFunctionMapper modelFunctionMapper;
	@Autowired
	private ModelMetaMapper modelMetaMapper;

	@Override
	public Pager<Model> find(Map<String, Object> paramsMap, Pager<Model> pager) {
		List<Model> list = modelMapper.find(paramsMap, pager);
		Integer totalNum = modelMapper.count(paramsMap);
		pager = new Pager<>(pager.getPageIndex(), pager.getPageSize(), totalNum, list);
		return pager;
	}

	@Override
	public int delete(String modelId) {
		// Model m = new Model();
		// m.setId(modelId);
		return modelMapper.deleteByPrimaryKey(modelId);
	}

	@Override
	public Model find(String modelId, String userId) {
		Model m = new Model();
		m.setId(modelId);
		m.setCreatedBy(userId);
		return modelMapper.selectOne(m);
	}

	@Override
	public Model findSingleModelById(String id) {
		return modelMapper.findSingleModelById(id);
	}

	@Override
	public int save(Model m) {
		// 设置一些默认值
		Date date = new Date();
		m.setCreatedDate(date);
		m.setModifiedDate(date);
		m.setComefrom(1);
		m.setState(1);
		m.setStatus(1);

		int i = modelMapper.insertSelective(m);// 插入

		if (i > 0) {// 插入模型信息成功后添加模型功能关联表
			String functionCodes = m.getMfs();
			if (functionCodes != null && !"".equals(functionCodes)) {
				String[] codeArr = functionCodes.split(",");
				for (String code : codeArr) {
					ModelFunction mf = new ModelFunction();
					mf.setFunction(code);
					mf.setModelId(m.getId());
					modelFunctionMapper.insert(mf);
				}
			}
			if (m.getMetaDatas() != null && m.getMetaDatas().size() > 0) {
				for (ModelMeta mm : m.getMetaDatas()) {
					mm.setObjectId(m.getId());
					modelMetaMapper.insertSelective(mm);
				}
			}
		}
		return i;
	}

	@Override
	public boolean edit(Model m) {
		boolean res = false;
		if (m != null) {
			Date date = new Date();
			m.setModifiedDate(date);

			int i = modelMapper.updateByPrimaryKeySelective(m);// 更新

			if (i > 0) {// 编辑模型信息成功后添加模型功能关联表
				String functionCodes = m.getMfs();
				if (functionCodes != null && !"".equals(functionCodes)) {
					String[] codeArr = functionCodes.split(",");
					for (String code : codeArr) {
						ModelFunction mf = new ModelFunction();
						mf.setFunction(code);
						mf.setModelId(m.getId());
						modelFunctionMapper.deleteByPrimaryKey(mf);
						modelFunctionMapper.insert(mf);
					}
				}

				if (m.getMetaDatas() != null && m.getMetaDatas().size() > 0) {
					for (ModelMeta mm : m.getMetaDatas()) {
						mm.setObjectId(m.getId());
						Example ex = new Example(ModelMeta.class);
						Criteria c = ex.createCriteria();
						c.andEqualTo("objectId", m.getId());
						c.andEqualTo("key", mm.getKey());
						modelMetaMapper.deleteByExample(ex);
						modelMetaMapper.insertSelective(mm);
					}
				}
				res = true;
			}
		}
		return res;
	}

	@Override
	public Model selectByKey(String id) {
		// TODO Auto-generated method stub
		if (StringUtils.isNotEmpty(id)) {
			return modelMapper.selectByPrimaryKey(id);
		}
		return null;
	}

	@Override
	public int selectCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer res = modelMapper.count(map);
		return res == null ? 0 : res;
	}

	@Override
	public boolean saveProductModel(Model m) {
		// TODO Auto-generated method stub
		boolean res = false;
		if (m != null) {
			Date date = new Date();
			m.setCreatedDate(date);
			m.setModifiedDate(date);
			String id = StringUtils.getUUID();// 模型id
			m.setId(id);
			m.setState(ModelContants.state_ok);
			m.setStatus(ModelContants.status_ok);

			if (modelMapper.insertSelective(m) == 1) {// 添加模型成功
				// 添加model-function
				String functionCodes = m.getMfs();
				if (functionCodes != null && !"".equals(functionCodes)) {
					String[] codeArr = functionCodes.split(",");
					for (String code : codeArr) {
						ModelFunction mf = new ModelFunction();
						mf.setFunction(code);
						mf.setModelId(id);
						// modelFunctionMapper.deleteByPrimaryKey(mf);
						modelFunctionMapper.insert(mf);
					}
				}
				// 添加meta-data
				if (m.getMetaDatas() != null && m.getMetaDatas().size() > 0) {
					for (ModelMeta mm : m.getMetaDatas()) {
						mm.setObjectId(id);
						modelMetaMapper.insertSelective(mm);
					}
				}
				res = true;
			}
		}
		return res;

	}

	@Override
	public String saveProductModel1(Model m) {
		// TODO Auto-generated method stub
		String res = "";
		if (m != null) {
			Date date = new Date();
			m.setCreatedDate(date);
			m.setModifiedDate(date);
			String id = StringUtils.getUUID();// 模型id
			m.setId(id);
			m.setState(ModelContants.state_ok);
			m.setStatus(ModelContants.status_ok);

			if (modelMapper.insertSelective(m) == 1) {// 添加模型成功
				// 添加model-function
				String functionCodes = m.getMfs();
				if (functionCodes != null && !"".equals(functionCodes)) {
					String[] codeArr = functionCodes.split(",");
					for (String code : codeArr) {
						ModelFunction mf = new ModelFunction();
						mf.setFunction(code);
						mf.setModelId(id);
						// modelFunctionMapper.deleteByPrimaryKey(mf);
						modelFunctionMapper.insert(mf);
					}
				}
				// 添加meta-data
				if (m.getMetaDatas() != null && m.getMetaDatas().size() > 0) {
					for (ModelMeta mm : m.getMetaDatas()) {
						mm.setObjectId(id);
						modelMetaMapper.insertSelective(mm);
					}
				}
				res = id;
			}
		}
		return res;

	}

	@Override
	public List<Model> selectGoodsModel() {
		// TODO Auto-generated method stub
		Model model = new Model();
		model.setComefrom(ModelContants.comefrom_product);
		return modelMapper.select(model);
	}

	// 查询给定skus对应的models
	@Override
	public List<Model> findBySku(Sku sku) {

		Example ex = new Example(Model.class);
		Criteria cri = ex.createCriteria();
		cri.andEqualTo("modelCode", sku.getGoodsSpecId());
		cri.andEqualTo("comefrom", ModelContants.comefrom_product);
		List<Model> models = modelMapper.selectByExample(ex);
		return models;
	}

	@Override
	public List<Model> findByCategory(String categoryId) {

		Example ex = new Example(Model.class);
		Criteria cri = ex.createCriteria();

		cri.andEqualTo("categoryId", categoryId);
		cri.andEqualTo("status", 1);
		cri.andEqualTo("comefrom", ModelContants.comefrom_product);

		List<Model> models = modelMapper.selectByExample(ex);
		return models;
	}

	@Override
	public ModelMeta selectModelMetaByCondition(ModelMeta modelMeta) {
		// TODO Auto-generated method stub
		if (modelMeta == null) {
			modelMeta = new ModelMeta();
		}
		return modelMetaMapper.selectOne(modelMeta);
	}

	@Override
	public List<ModelMeta> selectModelMetaListByModelId(String modelId) {
		// TODO Auto-generated method stub
		ModelMeta meta = new ModelMeta();
		meta.setObjectId(modelId);

		return modelMetaMapper.select(meta);
	}

	@Override
	public List<ModelFunction> selectModelFunctionByModelId(String modelId) {
		// TODO Auto-generated method stub
		return modelFunctionMapper.selectByModelId(modelId);
	}

}
