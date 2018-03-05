package com.aladdin.model.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aladdin.base.BaseController;
import com.aladdin.base.BaseVo;
import com.aladdin.base.BaseVo2;
import com.aladdin.base.BaseVo3;
import com.aladdin.base.ErrorEnum;
import com.aladdin.base.Pager;
import com.aladdin.catalog.TreeConstant;
import com.aladdin.catalog.TreeVO;
import com.aladdin.member.entity.Member;
import com.aladdin.model.ModelContants;
import com.aladdin.model.entity.Brand;
import com.aladdin.model.entity.Category;
import com.aladdin.model.entity.Goods;
import com.aladdin.model.entity.Model;
import com.aladdin.model.entity.ModelFunction;
import com.aladdin.model.entity.ModelMeta;
import com.aladdin.model.entity.Sku;
import com.aladdin.model.mapper.ModelFunctionMapper;
import com.aladdin.model.mapper.ModelMetaMapper;
import com.aladdin.model.service.BrandService;
import com.aladdin.model.service.CategoryService;
import com.aladdin.model.service.ModelService;
import com.aladdin.model.service.ProductService;
import com.aladdin.model.vo.ModelCategorySkpVo;
import com.aladdin.model.vo.ModelCategorySkpVo2;
import com.aladdin.model.vo.ModelCategorySkpVo3;
import com.aladdin.model.vo.ModelSkpVo;
import com.aladdin.utils.StringUtils;
import com.google.common.collect.Maps;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Api(value = "/api/models")
@RestController
@RequestMapping("/api/models")
public class ModelController extends BaseController {

	@Autowired
	private ModelService modelService;

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ModelMetaMapper modelMetaMapper;
	@Autowired
	private ModelFunctionMapper modelFunctionMapper;

	@ResponseBody
	@ApiOperation(value = "按条件查询模型列表", notes = "")
	@RequestMapping("/search")
	public ResponseEntity<?> list(HttpServletRequest request) {

		Map<String, Object> map = extractParams(request);
		try {
			assertHas(map, "state:1", "sortRule:desc", "pageNo:1", "pageSize:10", "queryType:public","level:level3");
		} catch (Exception e) {
			return buildResponseEntity(ErrorEnum.PARAM_ERROR);
		}

		Member member = getLoginUser(request);
		if ("public".equalsIgnoreCase(String.valueOf(map.get("queryType")))) {
			map.put("accessibility", 3);// 共享的模型
		} else {
			map.put("createdBy", member.getId());
		}
		if (map.get("state") != null) {
			map.put("state", map.get("state"));// 审核状态
		}
		if (map.get("modelfrom") != null) {
			Object from = map.get("modelfrom");
			if("designer".equals(from)){
				map.put("comefrom", ModelContants.comefrom_designer);// 模型来源：我的模型
			}
			if("product".equals(from)){
				map.put("comefrom", ModelContants.comefrom_product);// 模型来源：单品模型
			}
		}
		if(map.get("categoryId")!=null){
			String categoryId =  map.get("categoryId").toString();
			map.remove("categoryId");
			if("level1".equals(map.get("level"))){
				map.put("categoryId1", categoryId);
			}else 
			if("level2".equals(map.get("level"))){
				map.put("categoryId2", categoryId);
			}else{
				map.put("categoryId", categoryId);
			}
		}
		if(map.get("zone")!=null){
			map.put("function", map.get("zone"));
		}
		Pager<Model> pager = new Pager<>();
		if (map.get("pageNo") != null) {
			pager.setPageIndex(Integer.parseInt(map.get("pageNo").toString()));
		}
		if (map.get("pageSize") != null) {
			pager.setPageSize(Integer.parseInt(map.get("pageSize").toString()));
		}
		Pager<Model> resultPager = modelService.find(map, pager);
		return buildResponseEntity(ErrorEnum.SUCCESS, resultPager);
	}

	@ResponseBody
	@ApiOperation(value = "删除模型", notes = "删除模型")
	@RequestMapping(value = "/del/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String id, HttpServletRequest request,
			HttpServletResponse response) {
		Member user = getLoginUser(request);
		Model m = modelService.find(id, user.getId());
		if (m == null)
			buildResponseEntity(ErrorEnum.NO_AUT_ERROR);

		int i = modelService.delete(id);

		return buildResponseEntity(ErrorEnum.SUCCESS);
	}

	@ApiOperation(value = "查看单个模型信息", notes = "查询单个模型信息")
	@RequestMapping(value = "/get")
	public ResponseEntity<?> find(@RequestParam("id") String id, HttpServletRequest request,
			HttpServletResponse response) {

		int ind = id.indexOf(",");
		String[] ids;
		if (ind == -1) {
			ids = new String[] { id };
		} else {
			ids = id.split(",");
		}

		List<ModelSkpVo> skpvos = new ArrayList<ModelSkpVo>();

		for (String id_str : ids) {

			Model model = modelService.findSingleModelById(id_str);
			ModelSkpVo skpvo = new ModelSkpVo();

			if (model != null) {

				if (model.getBrand() != null) {
					Brand brand = brandService.findBrandById(model.getBrand().toString());
					if (brand != null) {
						skpvo.setBrand(brand.getBrand_name());
					} else {
						skpvo.setBrand("test");
					}
				}

				skpvo.setAuto_apply(model.getAutoApply()!=null&&model.getAutoApply()== 1);
				ModelCategorySkpVo vo = new ModelCategorySkpVo();
				ModelCategorySkpVo2 vo2 = new ModelCategorySkpVo2();
				ModelCategorySkpVo3 vo3 = new ModelCategorySkpVo3();
				if (model.getCategoryId() != null) {
					Category category3 = categoryService.findCategoryById(model.getCategoryId());
					if (category3!=null) {
						vo3.setGuid(category3.getGc_id());
						vo3.setName(category3.getGc_name());
						Category category2 = categoryService.findCategoryById(category3.getGc_parent_id());
						if (category2!=null) {
							vo2.setGuid(category2.getGc_id());
							vo2.setName(category2.getGc_name());
							vo2.setChildren(vo3);
							Category category = categoryService.findCategoryById(category2.getGc_parent_id());
							if (category!=null) {
								vo.setGuid(category.getGc_id());
								vo.setName(category.getGc_name());
								vo.setChildren(vo2);
								skpvo.setCategories_path(vo);
							}else{
//								skpvo.setCategories_path(vo2);
							}
						} else {
//							skpvo.setCategories_path(vo3);
						}
					}
				}
				skpvo.setGuid(model.getId());
				skpvo.setName(model.getName());
				skpvo.setThumb(model.getThumb());
				skpvo.setSkp(model.getSkp());
				skpvo.setProduct_mode(model.getModelnumber());
				skpvo.setUnit(model.getUnit());
				skpvo.setModel_type(model.getType());
				skpvo.setName(model.getName());
				skpvo.setPrice(model.getUnitprice()==null?"0":model.getUnitprice()+"");
				skpvo.setThumb(model.getThumb());
				skpvo.setUnit(model.getUnit());
				skpvo.setMaterial_type(model.getMaterial());

				List<ModelMeta> modelMetas = modelMetaMapper.selectMds(model.getId());
				Map<String, String> map = toMap(modelMetas);
				skpvo.setStage(map.get(ModelContants.stage));
				skpvo.setExport_type(map.get(ModelContants.exportType));
				skpvo.setInstall_expenses(map.get(ModelContants.installExpenses));
				skpvo.setUnitprice_man(map.get(ModelContants.unitpriceMan));
				skpvo.setUnitprice_material(map.get(ModelContants.unitpriceMaterial));
				skpvo.setWastage_rate(map.get(ModelContants.wastageRate));
				skpvo.setSpecification(map.get(ModelContants.modelSize)+""+map.get(ModelContants.modelMaterial));
				skpvo.setProject_category(map.get(ModelContants.pCategory));
				skpvo.setZone("未知");
				skpvos.add(skpvo);

			}
		}

		BaseVo2 bvo2 = new BaseVo2();
		bvo2.setData(skpvos);

		return buildResponseEntity(ErrorEnum.SUCCESS, bvo2);

	}

	private Map<String, String> toMap(List<ModelMeta> modelMetas) {

		Map<String, String> map = Maps.newHashMap();
		for (ModelMeta mm : modelMetas) {
			map.put(mm.getKey(), mm.getValue());
		}
		return map;
	}

/*	@ApiOperation(value = "编辑模型", notes = "编辑我的模型")
	@RequestMapping(value = "/edit/{id}")
	public ResponseEntity<?> editor(@PathVariable("id") String id, @RequestBody Model m, HttpServletRequest request,
			HttpServletResponse response) {

		Member user = getLoginUser(request);

		Model pm = modelService.find(id, user.getId());

		if (pm == null)
			return buildResponseEntity(ErrorEnum.NO_AUT_ERROR);

		m.setId(id);
		m.setModifiedBy(user.getId());

		int i = modelService.edit(m);
		if (i > 0) {
			return buildResponseEntity(ErrorEnum.SUCCESS);
		} else {
			return buildResponseEntity(ErrorEnum.SERVER_ERROR);
		}
	}*/
	
	@RequestMapping(value = "/edit/{id}")
	public ResponseEntity<?> edit(
			@PathVariable(value="id") String id,
			@RequestParam(value = "requestType", required = false) String requestType,
			
			@RequestParam(value = "goodsId", required = false) String goodsId,
			@RequestParam(value = "brandId", required = false) String brandId,
			@RequestParam(value = "skuId", required = false) String skuId,
			@RequestParam(value = "modelFunctions", required = false) String modelFunctions, // 区域
			@RequestParam(value = "accessibility", defaultValue = "3") Integer accessibility, // 3表示共享，0表示不共享

			@RequestParam(value = "style", required = false) String style, // 风格
			@RequestParam(value = "color", required = false) String color, // 色系
			@RequestParam(value = "type", required = false) String type, // 模型类型
			@RequestParam(value = "material", required = false) String material, // 材质类型
			@RequestParam(value = "category", required = false) String category, // 工程分类
			@RequestParam(value = "unitprice", required = false) String unitprice, // 单价
			@RequestParam(value = "autoApply", required = false) String autoApply, // 是否自适应

			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "length", required = false) String length,
			@RequestParam(value = "width", required = false) String width,
			@RequestParam(value = "height", required = false) String height,
			@RequestParam(value = "modelnumber", required = false) String modelnumber, // 型号
			@RequestParam(value = "weight", required = false) String weight,
			@RequestParam(value = "unit", required = false) String unit,
			@RequestParam(value = "description", required = false) String description, // 备注

			// @RequestParam(value="specification",required=false) String
			// specification,//规格
			@RequestParam(value = "modelSize", required = false) String modelSize, // 尺寸
			@RequestParam(value = "modelMaterial", required = false) String modelMaterial, // 材质
			@RequestParam(value = "stage", required = false) String stage, // 阶段
			@RequestParam(value = "unitpriceMan", required = false) String unitpriceMan, // 人工费
			@RequestParam(value = "unitpriceMaterial", required = false) String unitpriceMaterial, // 材料费
			@RequestParam(value = "transportExpenses", required = false) String transportExpenses, // 搬运费
			@RequestParam(value = "installExpenses", required = false) String installExpenses, // 安装费
			@RequestParam(value = "wastageRate", required = false) String wastageRate, // 损耗费
			@RequestParam(value = "exportType", required = false) String exportType, // 输出类型
			@RequestParam(value = "projectCategory", required = false) String projectCategory, // 项目类型

			@RequestParam(value = "thumbId", required = false,defaultValue="") String thumbId,//thumburl
			@RequestParam(value = "skpId", required = false,defaultValue="") String skpId, //skpurl
			HttpServletRequest req,
			HttpServletResponse res) {

		ErrorEnum errorEnum = ErrorEnum.SERVER_ERROR;

		Member user = getLoginUser(req);

		Model model = /*new Model()*/modelService.findSingleModelById(id);
		
		if(model==null){
			BaseVo vo = new BaseVo();
			vo.setCode(ErrorEnum.NOT_EXIST.getCode());
			vo.setMsg("您要更新的模型不存在！");
			return new ResponseEntity<>(vo,HttpStatus.OK);
		}
		
		String specification = "";
		if(requestType==null){
			requestType = "s";
		}
		if ("s".equalsIgnoreCase(requestType)) {
			Goods goods = categoryService.findGoodsById(goodsId);
			if (skuId == null || "".equals(skuId.trim())) {
				throw new RuntimeException("未选择sku");
			}
			if (goods == null) {
				throw new RuntimeException("商品不存在");
			}
			model.setModelCode(skuId);
			model.setName(goods.getGoods_name());
			model.setAbbreviation(goods.getGoods_name());
			model.setBrand(goods.getBrand_id());
			model.setCategoryId(goods.getGc_id());
			//查询二级分类
			Category c3 = categoryService.findCategoryById(goods.getGc_id());
			if(c3!=null&&c3.getGc_parent_id()!=null){
				model.setCategoryId2(c3.getGc_parent_id());
				//查询一级分类
				Category c2 = categoryService.findCategoryById(c3.getGc_parent_id());
				if(c2!=null&&c2.getGc_parent_id()!=null){
					model.setCategoryId1(c2.getGc_parent_id());
				}
			}

			model.setComefrom(ModelContants.comefrom_product);
			specification = goods.getSpecName();

		} else if ("m".equalsIgnoreCase(requestType)) {
			model.setComefrom(ModelContants.comefrom_designer);

			if (StringUtils.isNotEmpty(name)) {
				model.setName(name);
			}
			if (StringUtils.isNotEmpty(length)) {
				model.setLength(new BigDecimal(length));
			}
			if (StringUtils.isNotEmpty(width)) {
				model.setWidth(new BigDecimal(width));
			}
			if (StringUtils.isNotEmpty(height)) {
				model.setHeight(new BigDecimal(height));
			}
			if (StringUtils.isNotEmpty(weight)) {
				model.setWeight(Integer.parseInt(weight));
			}
			if (StringUtils.isNotEmpty(unit)) {
				model.setUnit(unit);
			}
			if (StringUtils.isNotEmpty(description)) {
				model.setDescription(description);
			}
			if(StringUtils.isNotEmpty(brandId)){
				model.setBrand(brandId);
			}

		} else {
			throw new RuntimeException("requestType参数值不正确");
		}

		model.setAccessibility(accessibility);

		if (StringUtils.isNotEmpty(modelnumber)) {
			model.setModelnumber(modelnumber);
		}
		if (StringUtils.isNotEmpty(style)) {
			model.setStyle(style);
		}
		if (StringUtils.isNotEmpty(color)) {
			model.setColor(color);
		}
		if (StringUtils.isNotEmpty(type)) {
			model.setType(type);
		}
		if (StringUtils.isNotEmpty(material)) {
			model.setMaterial(material);
		}
		
//		if (StringUtils.isNotEmpty(category)) {
//			model.setCategoryId(category);
//		}
		if (StringUtils.isNotEmpty(unitprice)) {
			model.setUnitprice(Double.parseDouble(unitprice));
		}
		if (StringUtils.isNotEmpty(autoApply)) {
			model.setAutoApply(Integer.parseInt(autoApply));
		}
//		model.setCreatedBy(user.getId());
		model.setModifiedBy(user.getId());
		if (skpId != null) {
			model.setSkp(skpId);
		}
		if (thumbId != null) {
			model.setThumb(thumbId);
		}

		List<ModelMeta> metaDatas = new ArrayList<>();
		
		if(StringUtils.isNotEmpty(specification))
	        metaDatas.add(new ModelMeta("specification",specification));//规格
		if (StringUtils.isNotEmpty(category))
			metaDatas.add(new ModelMeta("projectCategory", category));
		if (StringUtils.isNotEmpty(modelSize))
			metaDatas.add(new ModelMeta("modelSize", modelSize));
		if (StringUtils.isNotEmpty(modelMaterial))
			metaDatas.add(new ModelMeta("modelMaterial", modelMaterial));
		if (StringUtils.isNotEmpty(stage))
			metaDatas.add(new ModelMeta("stage", stage));// 阶段
		if (StringUtils.isNotEmpty(unitpriceMan))
			metaDatas.add(new ModelMeta("unitpriceMan", unitpriceMan));// 人工费
		if (StringUtils.isNotEmpty(unitpriceMaterial))
			metaDatas.add(new ModelMeta("unitpriceMaterial", unitpriceMaterial));// 材料费
		if (StringUtils.isNotEmpty(transportExpenses))
			metaDatas.add(new ModelMeta("transportExpenses", transportExpenses));// 搬运费
		if (StringUtils.isNotEmpty(installExpenses))
			metaDatas.add(new ModelMeta("installExpenses", installExpenses));// 安装费
		if (StringUtils.isNotEmpty(wastageRate))
			metaDatas.add(new ModelMeta("wastageRate", wastageRate));// 损耗费
		if (StringUtils.isNotEmpty(exportType))
			metaDatas.add(new ModelMeta("exportType", exportType));// 输出类型

		if (metaDatas != null)
			model.setMetaDatas(metaDatas);

		if (StringUtils.isNotEmpty(modelFunctions))
			model.setMfs(modelFunctions);

		if (modelService.edit(model)) {
			errorEnum = ErrorEnum.SUCCESS;
		}

		return buildResponseEntity(errorEnum);

	}

	
	@RequestMapping("/info/{id}")
	public ResponseEntity<?> info(
			@PathVariable(value="id")String id){
		
		Map<String,Object> map = new HashMap<>();
		Model m = modelService.findSingleModelById(id);
		if(m==null){
			BaseVo vo = new BaseVo();
			vo.setCode(ErrorEnum.NOT_EXIST.getCode());
			vo.setMsg("查无数据!");
			return new ResponseEntity<>(vo,HttpStatus.OK);
		}
		List<ModelMeta> modelMetas = modelMetaMapper.selectMds(m.getId());
		if(modelMetas!=null){
			m.setMetaDatas(modelMetas);
		}
		List<ModelFunction> mfs = modelFunctionMapper.selectByModelId(m.getId());
		map.put("code", ErrorEnum.SUCCESS.getCode());
		map.put("model", m);
		if(mfs!=null){
			map.put("functions", mfs);
		}
		return new ResponseEntity<>(map,HttpStatus.OK);
	}
	
	/**
	 * 获取总页数
	 * 
	 * @return
	 */
	@RequestMapping("/totalPage")
	public ResponseEntity<?> getTotalPage(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			HttpServletRequest request) {

		Map<String, Object> map = extractParams(request);
		try {
			assertHas(map, "state:1", "pageSize:10", "queryType:public");
		} catch (Exception e) {
			return buildResponseEntity(ErrorEnum.PARAM_ERROR);
		}

		if ("public".equalsIgnoreCase(String.valueOf(map.get("queryType")))) {
			map.put("accessibility", 3);// 共享的模型
		} else {
			Member member = getLoginUser(request);
			map.put("createdBy", member.getId());
		}

		if (map.get("state") != null) {
			map.put("state", map.get("state"));// 审核状态
		}
		if (map.get("modelfrom") != null) {
			Object from = map.get("modelfrom");
			if("designer".equals(from)){
				map.put("comefrom", ModelContants.comefrom_designer);// 模型来源：我的模型
			}
			if("product".equals(from)){
				map.put("comefrom", ModelContants.comefrom_product);// 模型来源：单品模型
			}
		}
		if(map.get("categoryId")!=null){
			String categoryId =  map.get("categoryId").toString();
			if("level1".equals(map.get("level"))){
				map.put("categoryId1", categoryId);
			}else 
			if("level2".equals(map.get("level"))){
				map.put("categoryId2", categoryId);
			}else{
				map.put("categoryId", categoryId);
			}
		}
		if(map.get("zone")!=null){
			map.put("function", map.get("zone"));
		}
		int count = modelService.selectCount(map);
		BaseVo3 baseVo3 = new BaseVo3();
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("totalPage", count == 0 ? 1 : (count + pageSize - 1) / pageSize);
		baseVo3.setData(map2);
		return buildResponseEntity(ErrorEnum.SUCCESS, baseVo3);

	}

	/**
	 * 上传模型 accessibility=0表示上传我的模型，accessibility=3或不存在 表示上传单品模型
	 * 
	 * @return
	 */
	@RequestMapping(value = "/single/upload")
	public ResponseEntity<?> upload(@RequestParam(value = "goodsId", required = false) String goodsId,
			@RequestParam(value = "skuId", required = false) String skuId,
			@RequestParam(value = "brandId", required = false) String brandId,
			@RequestParam(value = "modelFunctions", required = false) String modelFunctions, // 区域
			@RequestParam(value = "accessibility", defaultValue = "3") Integer accessibility, // 3表示共享，0表示不共享

			@RequestParam(value = "style", required = false) String style, // 风格
			@RequestParam(value = "color", required = false) String color, // 色系
			@RequestParam(value = "type", required = false) String type, // 模型类型
			@RequestParam(value = "material", required = false) String material, // 材质类型
			@RequestParam(value = "category", required = false) String category, // 工程分类
			@RequestParam(value = "unitprice", required = false) String unitprice, // 单价
			@RequestParam(value = "autoApply", required = false) String autoApply, // 是否自适应

			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "length", required = false) String length,
			@RequestParam(value = "width", required = false) String width,
			@RequestParam(value = "height", required = false) String height,
			@RequestParam(value = "modelnumber", required = false) String modelnumber, // 型号
			@RequestParam(value = "weight", required = false) String weight,
			@RequestParam(value = "unit", required = false) String unit,
			@RequestParam(value = "description", required = false) String description, // 备注

			// @RequestParam(value="specification",required=false) String
			// specification,//规格
			@RequestParam(value = "modelSize", required = false) String modelSize, // 尺寸
			@RequestParam(value = "modelMaterial", required = false) String modelMaterial, // 材质
			@RequestParam(value = "stage", required = false) String stage, // 阶段
			@RequestParam(value = "unitpriceMan", required = false) String unitpriceMan, // 人工费
			@RequestParam(value = "unitpriceMaterial", required = false) String unitpriceMaterial, // 材料费
			@RequestParam(value = "transportExpenses", required = false) String transportExpenses, // 搬运费
			@RequestParam(value = "installExpenses", required = false) String installExpenses, // 安装费
			@RequestParam(value = "wastageRate", required = false) String wastageRate, // 损耗费
			@RequestParam(value = "exportType", required = false) String exportType, // 输出类型
			@RequestParam(value = "projectCategory", required = false) String projectCategory, // 项目类型

			@RequestParam(value = "thumbId", required = false,defaultValue="") String thumbId,//thumburl
			@RequestParam(value = "skpId", required = false,defaultValue="") String skpId, //skpurl
			@RequestParam(value = "requestType", required = false) String requestType,
			HttpServletRequest req,
			HttpServletResponse res) {

		ErrorEnum errorEnum = ErrorEnum.SERVER_ERROR;

		Member user = getLoginUser(req);

		Model model = new Model();
		
		String specification = "";
		
		if(requestType==null){
			requestType = "s";
		}

		if ("s".equalsIgnoreCase(requestType)) {
			Goods goods = categoryService.findGoodsById(goodsId);
			if (skuId == null || "".equals(skuId.trim())) {
				throw new RuntimeException("未选择sku");
			}
			if (goods == null) {
				throw new RuntimeException("商品不存在");
			}
			model.setModelCode(skuId);
			model.setName(goods.getGoods_name());
			model.setAbbreviation(goods.getGoods_name());
			model.setBrand(goods.getBrand_id());
			model.setCategoryId(goods.getGc_id());
			//查询二级分类
			Category c3 = categoryService.findCategoryById(goods.getGc_id());
			if(c3!=null&&c3.getGc_parent_id()!=null){
				model.setCategoryId2(c3.getGc_parent_id());
				//查询一级分类
				Category c2 = categoryService.findCategoryById(c3.getGc_parent_id());
				if(c2!=null&&c2.getGc_parent_id()!=null){
					model.setCategoryId1(c2.getGc_parent_id());
				}
			}

			model.setComefrom(ModelContants.comefrom_product);
			specification = goods.getSpecName();

		} else if ("m".equalsIgnoreCase(requestType)) {
			model.setComefrom(ModelContants.comefrom_designer);

			if (StringUtils.isNotEmpty(name)) {
				model.setName(name);
			}
			if (StringUtils.isNotEmpty(length)) {
				model.setLength(new BigDecimal(length));
			}
			if (StringUtils.isNotEmpty(width)) {
				model.setWidth(new BigDecimal(width));
			}
			if (StringUtils.isNotEmpty(height)) {
				model.setHeight(new BigDecimal(height));
			}
			if (StringUtils.isNotEmpty(weight)) {
				model.setWeight(Integer.parseInt(weight));
			}
			if (StringUtils.isNotEmpty(unit)) {
				model.setUnit(unit);
			}
			if (StringUtils.isNotEmpty(description)) {
				model.setDescription(description);
			}
			if (StringUtils.isNotEmpty(brandId)) {
				model.setDescription(brandId);
			}

		} else {
			throw new RuntimeException("requestType参数值不正确");
		}

		model.setAccessibility(accessibility);

		if (StringUtils.isNotEmpty(modelnumber)) {
			model.setModelnumber(modelnumber);
		}
		if (StringUtils.isNotEmpty(style)) {
			model.setStyle(style);
		}
		if (StringUtils.isNotEmpty(color)) {
			model.setColor(color);
		}
		if (StringUtils.isNotEmpty(type)) {
			model.setType(type);
		}
		if (StringUtils.isNotEmpty(material)) {
			model.setMaterial(material);
		}
//		if (StringUtils.isNotEmpty(category)) {
//			model.setCategoryId(category);
//		}
		if (StringUtils.isNotEmpty(unitprice)) {
			model.setUnitprice(Double.parseDouble(unitprice));
		}
		if (StringUtils.isNotEmpty(autoApply)) {
			model.setAutoApply(Integer.parseInt(autoApply));
		}
		model.setCreatedBy(user.getId());
		model.setModifiedBy(user.getId());
		if (skpId != null) {
			model.setSkp(skpId);
		}
		if (thumbId != null) {
			model.setThumb(thumbId);
		}

		List<ModelMeta> metaDatas = new ArrayList<>();
		
		if(StringUtils.isNotEmpty(specification))
	        metaDatas.add(new ModelMeta("specification",specification));//规格
		if (StringUtils.isNotEmpty(category))
			metaDatas.add(new ModelMeta("projectCategory", category));
		if (StringUtils.isNotEmpty(modelSize))
			metaDatas.add(new ModelMeta("modelSize", modelSize));
		if (StringUtils.isNotEmpty(modelMaterial))
			metaDatas.add(new ModelMeta("modelMaterial", modelMaterial));
		if (StringUtils.isNotEmpty(stage))
			metaDatas.add(new ModelMeta("stage", stage));// 阶段
		if (StringUtils.isNotEmpty(unitpriceMan))
			metaDatas.add(new ModelMeta("unitpriceMan", unitpriceMan));// 人工费
		if (StringUtils.isNotEmpty(unitpriceMaterial))
			metaDatas.add(new ModelMeta("unitpriceMaterial", unitpriceMaterial));// 材料费
		if (StringUtils.isNotEmpty(transportExpenses))
			metaDatas.add(new ModelMeta("transportExpenses", transportExpenses));// 搬运费
		if (StringUtils.isNotEmpty(installExpenses))
			metaDatas.add(new ModelMeta("installExpenses", installExpenses));// 安装费
		if (StringUtils.isNotEmpty(wastageRate))
			metaDatas.add(new ModelMeta("wastageRate", wastageRate));// 损耗费
		if (StringUtils.isNotEmpty(exportType))
			metaDatas.add(new ModelMeta("exportType", exportType));// 输出类型

		if (metaDatas != null)
			model.setMetaDatas(metaDatas);

		if (StringUtils.isNotEmpty(modelFunctions))
			model.setMfs(modelFunctions);

		if (modelService.saveProductModel(model)) {
			errorEnum = ErrorEnum.SUCCESS;
		}

		return buildResponseEntity(errorEnum);

	}

	@RequestMapping(value = "skus")
	public ResponseEntity<?> skus(@RequestParam(required = true) String goodId) {

		Goods good = categoryService.findGoodsById(goodId);
		if (good == null) {
			return buildResponseEntity(ErrorEnum.NOT_EXIST);
		}
		// 查询sku列表
		List<Sku> skus = productService.findSkusByGood(good);

		if (skus == null || skus.isEmpty()) {
			return buildResponseEntity(ErrorEnum.SUCCESS, null);
		}

		for (Sku sku : skus) {
			List<Model> models = modelService.findBySku(sku);
			if (models != null && !models.isEmpty()) {
				sku.setIs_exist(ModelContants.exist_yes);
			}
		}
		return new ResponseEntity<List<Sku>>(skus, HttpStatus.OK);
	}

	// 模型树
	@RequestMapping("tree")
	public List<TreeVO> tree(HttpServletRequest request, @RequestParam(required = true, defaultValue = "0") String id) {

		List<TreeVO> trees = new ArrayList<>();
		List<Category> categories = categoryService.findByParentId(id);
		if (categories != null) {
			for (Category c : categories) {
				TreeVO t = new TreeVO();
				t.setText(c.getGc_name());
				t.setId(c.getGc_id());
				t.setChildren(true);
				t.setIcon(TreeConstant.node_style);
				t.setA_attr("D");
				trees.add(t);
			}
		}
		List<Model> models = modelService.findByCategory(id);
		if (models != null) {
			for (Model m : models) {
				TreeVO vo = new TreeVO();
				vo.setId(m.getId());
				vo.setText(m.getName());
				vo.setChildren(false);
				vo.setIcon(TreeConstant.leaf_style);
				vo.setA_attr("F");
				trees.add(vo);
			}
		}

		return trees;
	}

	private Map<String, Object> extractParams(HttpServletRequest request, Object... requiredParams) {
		Map<String, Object> map = new HashMap<>();
		Map<String, String[]> params = request.getParameterMap();
		for (Entry<String, String[]> m : params.entrySet()) {

			String key = m.getKey();
			String[] values = m.getValue();

			if (values != null && values.length == 1) {
				map.put(key, values[0]);
			}
			if (values != null && values.length > 1) {
				map.put(key, values);
			}
		}
		return map;
	}

	public void assertHas(Map<String, Object> params, String... requiredParams) throws Exception {

		Assert.notNull(params);
		Assert.notNull(requiredParams);

		for (String key : requiredParams) {

			int num = key.indexOf(":");
			String paramName = "";

			if (num > 0) {
				paramName = key.split(":")[0];
			} else {
				paramName = key;
			}

			if (!params.containsKey(paramName)) {
				if (num > 0) {
					params.put(key.substring(0, num), key.substring(num + 1));// 设置默认值
				} else {
					throw new Exception("参数" + key + "为必填项！");
				}
			}
		}
	}

}
