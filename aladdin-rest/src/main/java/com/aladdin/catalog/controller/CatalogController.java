package com.aladdin.catalog.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mockito.cglib.reflect.MulticastDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladdin.base.BaseController;
import com.aladdin.base.BaseVo;
import com.aladdin.base.BaseVo2;
import com.aladdin.base.ErrorEnum;
import com.aladdin.base.Pager;
import com.aladdin.catalog.CatalogContans;
import com.aladdin.catalog.TreeConstant;
import com.aladdin.catalog.TreeVO;
import com.aladdin.catalog.entity.Catalog;
import com.aladdin.catalog.entity.CatalogVo;
import com.aladdin.catalog.entity.CatalogVo2;
import com.aladdin.catalog.service.CatalogService;
import com.aladdin.member.entity.Member;
import com.aladdin.model.ModelContants;
import com.aladdin.model.entity.Goods;
import com.aladdin.model.entity.Model;
import com.aladdin.model.entity.ModelFunction;
import com.aladdin.model.entity.ModelMeta;
import com.aladdin.model.service.ModelService;
import com.aladdin.utils.StringUtils;

/**
 * 型录
 * @author hyy
 * 2016年9月29日
 */
@Controller
@RequestMapping("/api/v1/catalogs")
public class CatalogController extends BaseController{
	
	@Autowired
	private CatalogService catalogService;
	
	@Autowired
	private ModelService modelService;
	
	/**
	 * 分页查询
	 * @param pageIndex
	 * @param pageSize
	 * @param createBy
	 * @param name
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "")
	public ResponseEntity<?> list(@RequestParam(value="pageIndex",defaultValue="1")Integer pageIndex,
			@RequestParam(value="pageSize",defaultValue="12")Integer pageSize,
			@RequestParam(value="createBy",required=false)String createBy,
			@RequestParam(value="type",required=false)String type,
			@RequestParam(value="name",required=false)String name
			) {
		
		ResponseEntity<?> responseEntity=buildResponseEntity(ErrorEnum.SERVER_ERROR, new BaseVo());
		
		Map<String, Object> map = new HashMap<String, Object>();

		if (StringUtils.isEmpty(name) == false) {
			map.put("name", name);
		}
		if (StringUtils.isEmpty(createBy) == false) {
			map.put("createBy", createBy);
		}
		if (StringUtils.isEmpty(type) == false) {
			map.put("type", type);
		}		
		
		Pager<Catalog> pager=catalogService.selectPageList(map, pageIndex, pageSize);
		
		responseEntity=new ResponseEntity<Pager>(pager, HttpStatus.OK);
		
		return responseEntity;

	}
	
	/**
	 * 查询列表（全查）
	 * @param pageIndex
	 * @param pageSize
	 * @param createBy
	 * @param name
	 * @return
	 */
	@RequestMapping(value ="/all")
	public ResponseEntity<?> list(
			@RequestParam(value="createBy",required=false)String createBy,
			@RequestParam(value="type",required=false)String type,
			@RequestParam(value="name",required=false)String name
			) {
		
		ResponseEntity<?> responseEntity=buildResponseEntity(ErrorEnum.SERVER_ERROR);
		
		Map<String, Object> map = new HashMap<String, Object>();

		if (StringUtils.isEmpty(name) == false) {
			map.put("name", name);
		}
		if (StringUtils.isEmpty(createBy) == false) {
			map.put("createBy", createBy);
		}
		if (StringUtils.isEmpty(type) == false) {
			map.put("type", type);
		}		
		
		List<Catalog> list=catalogService.selectList(map);
		if(list!=null){
			BaseVo2 baseVo2=new BaseVo2();
			baseVo2.setData(list);
			responseEntity=buildResponseEntity(ErrorEnum.SUCCESS, baseVo2);
		}
		
		return responseEntity;

	}
	
	/**
	 * 获取子列表
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "/tree")
	@ResponseBody
	public List<TreeVO> getChilds(@RequestParam(value="pid",required=false)String pid){
		
		List<Catalog> list=catalogService.selectChilds(pid);

		List<TreeVO> trees = new ArrayList<TreeVO>();
		for (Catalog catalog : list) {
			TreeVO vo = new TreeVO();
			vo.setId(catalog.getId());
			vo.setText(catalog.getName());
			boolean chd=catalog.getType().intValue()==1?false:true;
			vo.setChildren(chd);		
			vo.setA_attr(catalog.getType()+"");
			if(chd){
				vo.setIcon(TreeConstant.node_style);
			}else{
				vo.setIcon(TreeConstant.leaf_style);
			}
			trees.add(vo);
		}
		return trees;
	}

	/**
	 * 显示多个型录信息，只显示产品信息
	 * @param ids 逗号分隔
	 * @return
	 */
	@RequestMapping(value ="/datas")
	public ResponseEntity<?> views(@RequestParam(value="ids",required=false)String ids
			) {
		
		ResponseEntity<?> responseEntity=buildResponseEntity(ErrorEnum.SERVER_ERROR, new BaseVo());
		
		if(StringUtils.isNotEmpty(ids)){
			List<CatalogVo2> list=new ArrayList<CatalogVo2>();
			String[] idarr=ids.split(",");
			if(idarr.length>0){
				for(String id:idarr){
					if(StringUtils.isNotEmpty(id)){
						Catalog catalog=catalogService.selectByKey(id);
						if(catalog!=null && catalog.getType()==CatalogContans.type_pro){
							CatalogVo2 vo = new CatalogVo2();
							vo.setId(catalog.getId());
							vo.setName(catalog.getName());
							vo.setOwner(catalog.getOwner());
							list.add(vo);
						}
					}
				}
			}
			BaseVo2 baseVo2=new BaseVo2();
			if(list!=null){
				baseVo2.setData(list);
			}
			responseEntity=buildResponseEntity(ErrorEnum.SUCCESS, baseVo2);
		}
		
		return responseEntity;
		
	}
	

	
	/**
	 * 查看型录详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/info")
	public ResponseEntity<?> view(@RequestParam("id") String id) {
		
		ResponseEntity<?> responseEntity=buildResponseEntity(ErrorEnum.SERVER_ERROR, new BaseVo());
		
		if(StringUtils.isNotEmpty(id)){
			Catalog catalog=catalogService.selectByKey(id);
			if(catalog!=null){
				CatalogVo catalogVo=new CatalogVo();
				catalogVo=(CatalogVo) po2vo(catalog, catalogVo);
				String modelId=catalog.getExtra();
				if(StringUtils.isNotEmpty(modelId) && catalog.getType()==1){
					Model model=modelService.selectByKey(modelId);
					if(model!=null){
						catalogVo.setModel(model);			
						
						List<ModelMeta> metalist=modelService.selectModelMetaListByModelId(modelId);
						Map<String, Object> metamap=new HashMap<String,Object>();
						if(metalist!=null && metalist.size()>0){
							for(ModelMeta meta:metalist){
								metamap.put(meta.getKey(), meta.getValue());
							}
						}
						if(metamap!=null){
							catalogVo.setModelMetaMap(metamap);
						}
						List<ModelFunction> functionlist=modelService.selectModelFunctionByModelId(modelId);
						if(functionlist!=null){
							catalogVo.setModelFunctionList(functionlist);
						}
						
					}
				}
				responseEntity=buildResponseEntity(ErrorEnum.SUCCESS, catalogVo);
			}
		}
		return responseEntity;

	}
	
	/**
	 * 添加型录
	 * @param name
	 * @param type
	 * @param modelId
	 * @param exportType
	 * @param parent
	 * @param owner
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/save")
	public ResponseEntity<?> create(@RequestParam(value="name")String name,
			@RequestParam(value="type",defaultValue="0")String type,//0表示目录，1表示产品
			@RequestParam(value="modelId",required=false)String modelId,
			@RequestParam(value="exportType",required=false)String exportType,
			@RequestParam(value="parent",required=false)String parent,
			//@RequestParam(value="owner",required=false)String owner,			
			HttpServletRequest req,HttpServletResponse res) {
		
		ResponseEntity<?> responseEntity=buildResponseEntity(ErrorEnum.SERVER_ERROR, new BaseVo());
		
		String userId=getUserId(req);
		if(StringUtils.isEmpty(userId)){
			responseEntity=buildResponseEntity(ErrorEnum.NO_AUT_ERROR);
		}else{
			
			byte tp=(byte) Integer.parseInt(type);
			Catalog catalog=new Catalog();
			catalog.setName(name);
			catalog.setType(tp);
			if(exportType!=null){
				catalog.setExportType(exportType);
			}
			if(parent!=null){
				catalog.setParent(parent);
			}
//			if(owner!=null){
//				catalog.setOwner(owner);
//			}
			catalog.setOwner(userId);
			catalog.setCreatedBy(userId);
			
			Model model=new Model();
			if(tp==CatalogContans.type_pro){//添加产品型录
				if(StringUtils.isNotEmpty(modelId)){
					model=modelService.selectByKey(modelId);
					if(model==null){
						model=getModelByReq(req);
					}
				}else{
					model=getModelByReq(req);
				}
				model.setCreatedBy(userId);
				model.setModifiedBy(userId);
				model.setName(name);
			}
			
			if(catalogService.saveInfo(model, catalog)){
				responseEntity=buildResponseEntity(ErrorEnum.SUCCESS, new BaseVo());
			}			
		}

		return responseEntity;

	}
	
	/**
	 * 提交修改信息
	 * @param id
	 * @param name
	 * @param modelId
	 * @param exportType
	 * @param owner
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "update")
	public ResponseEntity<?> update(
			@RequestParam(value="id")String id,
			@RequestParam(value="name")String name,
			@RequestParam(value="modelId",required=false)String modelId,
			@RequestParam(value="exportType",required=false)String exportType,
			@RequestParam(value="owner",required=false)String owner,
			HttpServletRequest req,
			HttpServletResponse res) {
		
		ResponseEntity<?> responseEntity=buildResponseEntity(ErrorEnum.PARAM_ERROR, new BaseVo());
		
		String userId=getUserId(req);
		
		if(StringUtils.isNotEmpty(userId)){
			Catalog catalog=catalogService.selectByKey(id);
			
			if(catalog!=null){
				
				catalog.setName(name);
				if(exportType!=null){
					catalog.setExportType(exportType);
				}
				if(owner!=null){
					catalog.setOwner(owner);
				}
				
				Model model=new Model();
				if(catalog.getType()==CatalogContans.type_pro){
					if(StringUtils.isNotEmpty(modelId)){
						model=modelService.selectByKey(modelId);
						if(model==null){
							model=getModelByReq(req);
						}
					}else{
						model=getModelByReq(req);
					}
					model.setCreatedBy(userId);
					model.setModifiedBy(userId);
					model.setName(name);
				}
				if(catalogService.updateInfo(model, catalog)){
					responseEntity=buildResponseEntity(ErrorEnum.SUCCESS, new BaseVo());
				}
			}			
		}else{
			responseEntity=buildResponseEntity(ErrorEnum.NO_AUT_ERROR);
		}
		

		return responseEntity;
	
	}
	
	/**
	 * 删除型录信息（逻辑删除）
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "del")
	public ResponseEntity<?> remove(@RequestParam(value="id") String id) {
		ErrorEnum errorEnum=ErrorEnum.PARAM_ERROR;
		Catalog catalog=catalogService.selectByKey(id);
		if(catalog==null){
			errorEnum=ErrorEnum.NOT_EXIST;
		}else{
			if(catalogService.deleteInfo(id)){
				errorEnum=ErrorEnum.SUCCESS;
			}			
		}
		ResponseEntity<?> responseEntity=buildResponseEntity(errorEnum, new BaseVo());
		return responseEntity;
	}
	
	/**
	 * 从request中获取模型信息，并保存
	 * @param req
	 * @return
	 */
	public Model getModelByReq(HttpServletRequest req){
		
		Model model = new Model();
		
		model.setComefrom(ModelContants.comefrom_designer);
		model.setCreatedBy(getUserId(req));
		model.setCreatedDate(new Date());
		
		List<ModelMeta> metaDatas = new ArrayList<>();
		
		model.setName(req.getParameter("name"));
		String brand = req.getParameter("brand");//品牌
		if (StringUtils.isNotEmpty(brand)) {
			model.setBrand(brand);
		}
		String unitprice = req.getParameter("unitprice");
		if (StringUtils.isNotEmpty(unitprice)
				&& StringUtils.isNumeric(unitprice)) {
			model.setUnitprice(Double.valueOf(unitprice));
		}
		String modelnumber=req.getParameter("modelnumber");
		if(StringUtils.isNotEmpty(modelnumber)){
			model.setModelnumber(req.getParameter("modelnumber"));//型号
		}
		
		String specification=req.getParameter("specification");//规格(model_meta)
		if(StringUtils.isNotEmpty(specification)){
			metaDatas.add(new ModelMeta("specification",specification));
		}
		String unit = req.getParameter("unit");
		if (StringUtils.isNotEmpty(unit)) {
			model.setUnit(unit);
		}	
		String unitpriceMan=req.getParameter("unitpriceMan");//人工费
		if(StringUtils.isNotEmpty(unitpriceMan)){
			metaDatas.add(new ModelMeta("unitpriceMan",unitpriceMan));
		}
		String unitpriceMaterial=req.getParameter("unitpriceMaterial");//材料费
		if(StringUtils.isNotEmpty(unitpriceMaterial)){
			metaDatas.add(new ModelMeta("unitpriceMaterial",unitpriceMaterial));
		}
		String transportExpenses=req.getParameter("transportExpenses");//搬运费
		if(StringUtils.isNotEmpty(transportExpenses)){
			metaDatas.add(new ModelMeta("transportExpenses",transportExpenses));
		}
		String installExpenses=req.getParameter("installExpenses");//安装费
		if(StringUtils.isNotEmpty(installExpenses)){
			metaDatas.add(new ModelMeta("installExpenses",installExpenses));
		}
		String wastageRate=req.getParameter("wastageRate");//损耗费
		if(StringUtils.isNotEmpty(wastageRate)){
			metaDatas.add(new ModelMeta("wastageRate",wastageRate));
		}
		String stage=req.getParameter("stage");//阶段
		if(StringUtils.isNotEmpty(stage)){
			metaDatas.add(new ModelMeta("stage",stage));
		}
		String category=req.getParameter("category");//工程
		if(StringUtils.isNotEmpty(category)){
			metaDatas.add(new ModelMeta("projectCategory",category));
		}
		String exportType=req.getParameter("exportType");//输出类型
		if(StringUtils.isNotEmpty(exportType)){
			metaDatas.add(new ModelMeta("exportType",exportType));
		}
		
		String modelFunctions=req.getParameter("modelFunctions");//区域
	
		if (metaDatas != null)
			model.setMetaDatas(metaDatas);

		if (StringUtils.isNotEmpty(modelFunctions))
			model.setMfs(modelFunctions);
		
		return model;
		
		//return modelService.saveProductModel1(model);
	}


}
