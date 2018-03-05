package com.aladdin.catalog.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladdin.base.ErrorEnum;
import com.aladdin.base.Pager;
import com.aladdin.catalog.CatalogContans;
import com.aladdin.catalog.entity.Catalog;
import com.aladdin.catalog.mapper.CatalogMapper;
import com.aladdin.model.ModelContants;
import com.aladdin.model.entity.Model;
import com.aladdin.model.mapper.ModelMapper;
import com.aladdin.model.service.ModelService;
import com.aladdin.utils.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class CatalogServiceImpl implements CatalogService {
	
	
	@Autowired
	private CatalogMapper catalogMapper;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Catalog selectByKey(String id) {
		// TODO Auto-generated method stub
		return catalogMapper.selectByPrimaryKey(id);
	}

	@Override
	public Pager<Catalog> selectPageList(Map<String, Object> map, Integer pageIndex, Integer pageSize) {
		// TODO Auto-generated method stub
		if(pageIndex==null){
			pageIndex=1;
		}
		if(pageSize==null){
			pageSize=20;
		}
		Example example=new Example(Catalog.class);
		Criteria criteria=example.createCriteria();
		
		String createBy=(String)map.get("createBy");
		String name=(String)map.get("name");
		if(StringUtils.isNotEmpty(createBy)){
			criteria.andEqualTo("createdBy", createBy);
		}
		criteria.andEqualTo("status", CatalogContans.stat_ok);
		if(map.get("type")!=null){
			criteria.andEqualTo("type", map.get("type"));
		}
		if(StringUtils.isNotEmpty(name)){
			criteria.andLike("name", "%"+name+"%");
		}
		
		List<Catalog> list= catalogMapper.selectByExampleAndRowBounds(example, new RowBounds((pageIndex-1)*pageSize, pageSize));

		int count = catalogMapper.selectCountByExample(example);
		
		Pager<Catalog> pager= new Pager<>(pageIndex, pageSize, count, list);
		pager.setErrorEnum(ErrorEnum.SUCCESS);
		
		return pager;
	}
	

	@Override
	public List<Catalog> selectList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<Catalog> list=new ArrayList<Catalog>();
		
		Example example=new Example(Catalog.class);
		Criteria criteria=example.createCriteria();
		
		String createBy=(String)map.get("createBy");
		String name=(String)map.get("name");
		if(StringUtils.isNotEmpty(createBy)){
			criteria.andEqualTo("createdBy", createBy);
		}
		criteria.andEqualTo("status", CatalogContans.stat_ok);
		if(map.get("type")!=null){
			criteria.andEqualTo("type", map.get("type"));
		}
		if(StringUtils.isNotEmpty(name)){
			criteria.andLike("name", "%"+name+"%");
		}
		list=catalogMapper.selectByExample(example);
		
		return list;
	}
	
	@Autowired
	private ModelService modelService;

	@Override
	public boolean saveInfo(Model model, Catalog catalog) {
		// TODO Auto-generated method stub
		boolean res=false;
		
		Byte type=catalog.getType();
		if(type==null){
			type=CatalogContans.type_cat;
		}
		
		//当型录是产品时,先添加模型
		if(type == CatalogContans.type_pro){
			if(model!=null){
				model.setComefrom(ModelContants.comefrom_catalog);
				model.setCreatedDate(new Date());
				model.setModifiedDate(new Date());
				model.setSkp("");
				
				String modelId=modelService.saveProductModel1(model);
				if(StringUtils.isEmpty(modelId)){
					return false;//模型添加失败
				}
				catalog.setExtra(modelId);
			}
		}
		if(StringUtils.isEmpty(catalog.getId())){
			catalog.setId(StringUtils.getUUID());
		}
		if(StringUtils.isEmpty(catalog.getParent())){
			catalog.setParent(CatalogContans.def_parent);
		}
		if(StringUtils.isEmpty(catalog.getOwner())){
			catalog.setOwner(CatalogContans.def_owner);
		}
		catalog.setStatus(CatalogContans.stat_ok);
		catalog.setCreatedDate(new Date());
		
		if(catalogMapper.insertSelective(catalog)>0){
			res=true;
		}
		return res;
		
	}
	
	

	@Override
	public boolean updateInfo(Model model, Catalog catalog) {
		// TODO Auto-generated method stub
		boolean res=false;
		
		Byte type=catalog.getType();

		//当型录是产品时,先添加模型
		if(type == CatalogContans.type_pro){
			if(model!=null){
				
				//String modelId=model.getId();
				//添加模型
				//if(StringUtils.isEmpty(modelId)){
				//	modelId=StringUtils.getUUID();
				//	model.setId(modelId);
				//}
				model.setComefrom(ModelContants.comefrom_catalog);
				model.setCreatedDate(new Date());
				model.setModifiedDate(new Date());
				
				String modelId=modelService.saveProductModel1(model);
				if(StringUtils.isEmpty(modelId)){
					return false;//模型添加失败
				}
				catalog.setExtra(modelId);

			}
		}

		if(catalogMapper.updateByPrimaryKeySelective(catalog)>0){
			res=true;
		}
		return res;
	}

	@Override
	public boolean deleteInfo(String id) {
		// TODO Auto-generated method stub
		Catalog catalog=new Catalog();
		catalog.setId(id);
		catalog.setStatus(CatalogContans.stat_no);
		if(catalogMapper.updateByPrimaryKeySelective(catalog)>0){
			return true;
		}
		return false;
	}

	@Override
	public List<Catalog> selectChilds(String pid) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(pid)){
			pid=CatalogContans.def_parent;
		}
		Catalog catalog=new Catalog();
		catalog.setParent(pid);
		catalog.setStatus(CatalogContans.stat_ok);
		List<Catalog> list = catalogMapper.select(catalog);
		if(list==null || list.size()==0){
			/*Catalog cata = new Catalog();
			cata.setName("无");
			cata.setType(CatalogContans.type_pro);
			list.add(cata);*/
		}
		return list;

	}

    
}
