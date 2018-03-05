package com.aladdin.component.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladdin.base.BaseController;
import com.aladdin.base.BaseVo2;
import com.aladdin.base.ErrorEnum;
import com.aladdin.catalog.TreeConstant;
import com.aladdin.catalog.TreeVO;
import com.aladdin.catalog.entity.Catalog;
import com.aladdin.catalog.service.CatalogService;
import com.aladdin.component.ControllerHelper;
import com.aladdin.component.ControllerHelper.ParamsException;
import com.aladdin.component.entity.Component;
import com.aladdin.component.entity.ProductItem;
import com.aladdin.component.service.ComponentService;
import com.aladdin.component.vo.ComponentCollectionVo;
import com.aladdin.component.vo.ComponentSkpVo;
import com.aladdin.component.vo.ComponentSkpVo2;
import com.aladdin.component.vo.ComponentVo;
import com.aladdin.member.entity.Member;
import com.aladdin.model.ModelContants;
import com.aladdin.model.entity.Brand;
import com.aladdin.model.entity.Model;
import com.aladdin.model.entity.ModelMeta;
import com.aladdin.model.mapper.ModelMetaMapper;
import com.aladdin.model.service.BrandService;
import com.aladdin.model.service.ModelService;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/api/v1/components")
public class ComponentController extends BaseController {

	@Autowired
	private ComponentService componentService;
	@Autowired
	private CatalogService catalogService;
	@Autowired
	private ModelService modelService;
	@Autowired
	private ModelMetaMapper modelMetaMapper;
	@Autowired
	private BrandService brandService;

	@RequestMapping(value = "")
	public ResponseEntity<?> list(HttpServletRequest req, HttpServletResponse rep) {

		Map<String, Object> params = ControllerHelper.extractParams(req);
		try {
			ControllerHelper.assertHas(params, "parent:root");
		} catch (ParamsException e) {
			return buildResponseEntity(ErrorEnum.PARAM_ERROR);
		}
		List<Component> list = componentService.find(params);
		ComponentCollectionVo vo = new ComponentCollectionVo();
		vo.setComponents(list);
		return buildResponseEntity(ErrorEnum.SUCCESS, vo);
	}

	@RequestMapping(value = "info")
	public ResponseEntity<?> view(String id, HttpServletRequest req, HttpServletResponse res) {

		if (id == null || "".equals(id)) {
			return buildResponseEntity(ErrorEnum.PARAM_ERROR);
		}
		Component component = componentService.find(id);
		if (component != null) {
			// component.setItems(jsonToList(component.getExtra()));
			return buildResponseEntity(ErrorEnum.SUCCESS, (ComponentVo) po2vo(component, new ComponentVo()));
		} else {
			return buildResponseEntity(ErrorEnum.NOT_EXIST);
		}

	}

	@RequestMapping(value = "save")
	public ResponseEntity<?> create(@RequestParam(value = "name") String name,
			@RequestParam(value = "extra", defaultValue = "") String extra,
			@RequestParam(value = "parent", defaultValue = "root") String parent,
			@RequestParam(value = "owner", defaultValue = "aladdin") String owner,
			@RequestParam(value = "type", defaultValue = "0") Integer type,
			@RequestParam(value = "priority", defaultValue = "0") Long priority,
			@RequestParam(value = "builtin", defaultValue = "1") Integer builtin, HttpServletRequest req,
			HttpServletResponse res) {

		Member user = getLoginUser(req);

		Component component = new Component();

		component.setId(UUID.randomUUID().toString().replace("-", ""));
		component.setName(name);
		if (extra != null && !"".equals(extra.trim())) {
			extra = listToJson(jsonToList(extra));
		}
		component.setExtra(extra);
		component.setParent(parent);
		component.setOwner(owner);
		component.setType(type);
		component.setPriority(priority);
		component.setBuiltin(builtin);

		//component.setCreatedBy(user.getId());
		component.setCreatedBy(user.getId());
		component.setCreatedDate(new Date());
		component.setPriority(0);
		component.setBuiltin(1);
		component.setStatus(1);
		component.setModifiedBy(user.getId());
		component.setModifiedDate(new Date());


		int i = componentService.save(component);
		if (i > 0) {
			return buildResponseEntity(ErrorEnum.SUCCESS);
		} else {
			return buildResponseEntity(ErrorEnum.SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/del")
	public ResponseEntity<?> remove(String id, HttpServletRequest req, HttpServletResponse res) {
		Member user = getLoginUser(req);
		if (StringUtils.isNotEmpty(id)) {
			Component item = componentService.find(id);
			if (item != null) {
				item.setModifiedBy(user.getId());
				item.setModifiedDate(new Date());
				componentService.remove(item);
				return buildResponseEntity(ErrorEnum.SUCCESS);
			} else {
				return buildResponseEntity(ErrorEnum.SUCCESS);
			}
		} else {
			return buildResponseEntity(ErrorEnum.PARAM_ERROR);
		}
	}

	@RequestMapping(value = "update")
	public ResponseEntity<?> update(@RequestParam(value = "name") String name, @RequestParam(value = "id") String id,
			@RequestParam(value = "extra", defaultValue = "") String extra, HttpServletRequest req,
			HttpServletResponse res) {

		Member user = getLoginUser(req);

		if (id == null || "".equals(id)) {
			return buildResponseEntity(ErrorEnum.PARAM_ERROR);
		}
		Component component = componentService.find(id);
		if (component != null) {
			component.setName(name);
			if (extra != null && !"".equals(extra.trim())) {
				extra = listToJson(jsonToList(extra));
			}
			component.setExtra(extra);
			component.setModifiedBy(user.getId());
			component.setModifiedDate(new Date());
			int i = componentService.update(component);
			if (i > 0) {
				return buildResponseEntity(ErrorEnum.SUCCESS);
			} else {
				return buildResponseEntity(ErrorEnum.SERVER_ERROR);
			}
		} else {
			return buildResponseEntity(ErrorEnum.PARAM_ERROR);
		}
	}

	/**
	 * 显示多个配件的json数据
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/datas")
	public ResponseEntity<?> views(HttpServletRequest req, HttpServletResponse res) {
		// List<Component> list = new ArrayList<Component>();
		List<ComponentSkpVo> vos = new ArrayList<>();
		String id = req.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			String[] ids = id.split(",");
			if(ids.length>0){
				for (String idtr : ids) {
					if (StringUtils.isNotEmpty(idtr) && !"null".equalsIgnoreCase(idtr)) {
						Component component = componentService.find(idtr);
						if (component != null && component.getType() == 1) {
							ComponentSkpVo vo = new ComponentSkpVo();
							// list.add(component);
							vo.setId(component.getId());
							vo.setName(component.getName());
							String itemsJson = component.getExtra();
							if (itemsJson != null && !"".equals(itemsJson.trim())) {
								List<ProductItem> items = jsonToList(itemsJson);
								
								if(items!=null&&!items.isEmpty()){
									List<ComponentSkpVo2> vo2s = new ArrayList<>();
									for (ProductItem item : items) {
										
										ComponentSkpVo2 vo2 = new ComponentSkpVo2();
										vo2.setId(item.getCatalogId());
										vo2.setName(item.getCatalogName());
										vo2.setFormula(item.getFormula());
										vo2.setUnit(item.getUnit());
										vo2.setThumb(item.getThumb());
										vo2.setGuid(item.getGuid());
										vo2.setSkp(item.getSkp());
										vo2.setIsPackage(false);
										vo2.setType(item.getType());
										
										Model m = item.getModel();
										List<ModelMeta> metas = new ArrayList<>();
										if(m!=null){
											metas = modelMetaMapper.selectMds(m.getId());
										}else{
											m = new Model();
										}
										Map<String, String> metasMap = toMap(metas);
										if (m != null) {
											if (m.getBrand() != null) {
												Brand brand = brandService.findBrandById(m.getBrand().toString());
												if (brand != null) {
													vo2.setBrand(brand.getBrand_name());
												} else {
													vo2.setBrand("无");
												}
											}
											vo2.setProduct_mode(m.getModelnumber());
											vo2.setStage(metasMap.get(ModelContants.stage));
											vo2.setProject_category(metasMap.get(ModelContants.pCategory));
											vo2.setUnitprice_man(metasMap.get(ModelContants.unitpriceMan));
											vo2.setUnitprice_material(metasMap.get(ModelContants.unitpriceMaterial));
											vo2.setTransport_expenses(metasMap.get(ModelContants.transportExpenses));
											vo2.setInstall_expenses(metasMap.get(ModelContants.installExpenses));
											vo2.setWastage_rate(metasMap.get(ModelContants.wastageRate));
											String modelSize = metasMap.get(ModelContants.modelSize);
											String modelMaterial = metasMap.get(ModelContants.modelMaterial);
											vo2.setSpecification(modelSize==null?"":modelSize+" "+modelMaterial==null?"":modelMaterial);
											vo2.setExport_type(metasMap.get(ModelContants.exportType));
											vo2.setPrice(m.getUnitprice());
										}
										vo2s.add(vo2);
									}
									vo.setChildren(vo2s);
								}
							}
							vos.add(vo);
						}
					}
				}
			}
		}
		BaseVo2 baseVo2=new BaseVo2();
		if(vos!=null){
			baseVo2.setData(vos);
		}
		return buildResponseEntity(ErrorEnum.SUCCESS, baseVo2);
	}

	private Map<String, String> toMap(List<ModelMeta> modelMetas) {

		Map<String, String> map = Maps.newHashMap();
		for (ModelMeta mm : modelMetas) {
			map.put(mm.getKey(), mm.getValue());
		}
		return map;
	}

	@RequestMapping(value = "/tree")
	@ResponseBody
	public List<TreeVO> tree(HttpServletRequest req, HttpServletResponse res) {

		String id = req.getParameter("id");
		String keyword = req.getParameter("keyword");
		List<Component> components = new ArrayList<Component>();
		Set<String> scs = new HashSet<String>();
		if (StringUtils.isNotEmpty(keyword)) {
			// keyword = new String(keyword.getBytes("ISO8859-1"), "UTF-8");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("keyword", keyword);
			map.put("type", "1");

			List<Component> list = componentService.find(map);
			if (list != null && list.size() > 0) {
				for (Component component : list) {
					scs.add(component.getId());
					List<Component> path = componentService.findPath(component);
					if (path != null && path.size() > 0) {
						for (Component pct : path) {
							scs.add(pct.getId());
						}
					}
				}
			}
		}
		if (StringUtils.isEmpty(id) || "#".equals(id)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("parent", "root");
			components = componentService.find(map);
		} else {
			Component component = componentService.find(id);
			if (component != null) {
				components = componentService.findChildren(component);
			}
		}
		/*if (!(components != null && components.size() > 0)) {
			Component component = new Component();
			component.setName("无");
			component.setType(1);
			components.add(component);
		}*/
		if (components != null && components.size() > 0 && StringUtils.isNotEmpty(keyword)) {
			List<Component> list = new ArrayList<Component>();
			if (scs != null && scs.size() > 0) {
				for (Component component : components) {
					if (scs.contains(component.getId())) {
						list.add(component);
					}
				}
			}
			components = list;
		}
		List<TreeVO> trees = new ArrayList<TreeVO>();
		for (Component catalog : components) {
			TreeVO vo = new TreeVO();
			vo.setId(catalog.getId());
			vo.setText(catalog.getName());
			
			boolean chd=catalog.getType().intValue()==1?false:true;
			
			vo.setChildren(chd);
			
			if(chd){
				vo.setIcon(TreeConstant.node_style);
			}else{
				vo.setIcon(TreeConstant.leaf_style);
			}
			vo.setA_attr(catalog.getType() + "");
			trees.add(vo);
		}
		return trees;
		// ComponentCollectionVo vo = new ComponentCollectionVo();
		// vo.setComponents(components);
		// return buildResponseEntity(ErrorEnum.SUCCESS, vo);
	}

	public String listToJson(List<ProductItem> list) {
		JSONArray jsonArray = new JSONArray();
		try {
			if (list != null && list.size() > 0) {
				for (ProductItem productItem : list) {
					if (StringUtils.isNotEmpty(productItem.getCatalogId())
							&& StringUtils.isNotEmpty(productItem.getCatalogName())) {
						Catalog catalog = catalogService.selectByKey(productItem.getCatalogId());
						if (catalog != null && StringUtils.isNotEmpty(catalog.getExtra())) {
							Model model = modelService.selectByKey(catalog.getExtra());
							if (model != null) {
								productItem.setThumb(model.getThumb());
								productItem.setSkp(model.getSkp()==null?"":model.getSkp());
							}
						}
					}
					JSONObject jsonObject = new JSONObject(toJson(productItem));
					jsonArray.put(jsonObject);
				}
			}
		} catch (Exception e) {

		}
		return jsonArray.toString();
	}

	public String toJson(ProductItem item) {

		if (StringUtils.isNotEmpty(item.getCatalogId())) {
			JSONObject jsonObject = new JSONObject();
			try {
				Catalog catalog = catalogService.selectByKey(item.getCatalogId());
				if (catalog != null && StringUtils.isNotEmpty(catalog.getExtra())) {
					Model model = modelService.selectByKey(catalog.getExtra());
					item.setModel(model);
					if (model != null) {
						item.setGuid(model.getId());
						item.setType("accessory");
						item.setThumb(model.getThumb());
						item.setSkp(model.getSkp());
					}
				}
				jsonObject.put("catalogId", item.getCatalogId());
				jsonObject.put("catalogName", item.getCatalogName());
				jsonObject.put("formula", item.getFormula());
				jsonObject.put("unit", item.getUnit());
				jsonObject.put("thumb", item.getThumb());
				jsonObject.put("guid", item.getGuid());
				jsonObject.put("skp", item.getSkp());
				jsonObject.put("is_package", item.getIsPackage());
				jsonObject.put("type", item.getType());
			} catch (Exception e) {
				// LogUtil.log(this.getClass()).error(e.getMessage(),e);
			}
			return jsonObject.toString();
		}
		return "";
	}

	public ProductItem jsonToObj(String json) {
		ProductItem productItem = null;
		if (StringUtils.isNotEmpty(json)) {
			productItem = new ProductItem();
			try {
				JSONObject jsonObject = new JSONObject(json);
				productItem.setCatalogId(jsonObject.getString("catalogId"));
				productItem.setCatalogName(jsonObject.getString("catalogName"));
				productItem.setFormula(jsonObject.getString("formula"));
				productItem.setUnit(jsonObject.getString("unit"));
				productItem.setThumb(jsonObject.getString("thumb"));
				productItem.setGuid(jsonObject.getString("guid"));
				productItem.setSkp(jsonObject.getString("skp"));
				productItem.setIsPackage(jsonObject.getString("is_package"));
				productItem.setType(jsonObject.getString("type"));
				Catalog catalog = catalogService.selectByKey(productItem.getCatalogId());
				if (catalog != null && StringUtils.isNotEmpty(catalog.getExtra())) {
					Model model = modelService.selectByKey(catalog.getExtra());
					productItem.setModel(model);
				}
			} catch (Exception e) {
			}
			return productItem;
		}
		return productItem;
	}

	public List<ProductItem> jsonToList(String json) {
		List<ProductItem> productItems = new ArrayList<ProductItem>();
		if (StringUtils.isNotEmpty(json)) {
			try {
				JSONArray jsonArray = new JSONArray(json);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					ProductItem productItem = new ProductItem();
					productItem.setCatalogId(jsonObject.getString("catalogId"));
					productItem.setCatalogName(jsonObject.getString("catalogName"));
					productItem.setFormula(jsonObject.getString("formula"));
					productItem.setUnit(jsonObject.getString("unit"));
					productItem.setThumb(jsonObject.getString("thumb"));
					productItem.setGuid(jsonObject.getString("guid"));
					productItem.setSkp(jsonObject.getString("skp"));
					productItem.setIsPackage(jsonObject.getString("is_package"));
					productItem.setType(jsonObject.getString("type"));

					Catalog catalog = catalogService.selectByKey(productItem.getCatalogId());
					if (catalog != null && StringUtils.isNotEmpty(catalog.getExtra())) {
						Model model = modelService.selectByKey(catalog.getExtra());
						productItem.setModel(model);
					}
					productItems.add(productItem);
				}
			} catch (Exception e) {
				// LogUtil.log(ProductItem.class).error(e.getMessage(),e);
				e.printStackTrace();
			}
		}
		return productItems;
	}
}
