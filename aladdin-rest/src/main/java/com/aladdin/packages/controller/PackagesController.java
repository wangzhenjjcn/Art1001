package com.aladdin.packages.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aladdin.base.BaseController;
import com.aladdin.base.BaseVo2;
import com.aladdin.base.ErrorEnum;
import com.aladdin.base.Pager;
import com.aladdin.packages.entity.PackUpgVo;
import com.aladdin.packages.entity.PackVo;
import com.aladdin.packages.entity.PackageUpgrade;
import com.aladdin.packages.entity.PackageZone;
import com.aladdin.packages.entity.Packages;
import com.aladdin.packages.service.PackageUpgradeService;
import com.aladdin.packages.service.PackagesService;
import com.aladdin.series.entity.Series;
import com.aladdin.series.service.SeriesService;
import com.aladdin.utils.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.StringUtil;

@Controller("PackageController")
@RequestMapping("/api/v1/packages")
public class PackagesController extends BaseController{
	
	@Autowired
	private PackagesService packagesService;
	
	@Autowired
	private PackageUpgradeService packageUpgradeService;
	
	@Autowired
	private SeriesService seriesService;

	
	/**
	 * 显示套餐列表，分页查询
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value ="")
	public ResponseEntity<?> list(@RequestParam(value="style",required=false) String style,
			@RequestParam(value="area",required=false) String area,
			@RequestParam(value="unitprice",required=false) String unitprice,
			@RequestParam(value="keyword",required=false) String keyword,
			@RequestParam(value="pageNum",required=false,defaultValue="1") String pageNum,
			@RequestParam(value="pageSize",required=false,defaultValue="12") String pageSize
			) {
		
		return manage(style,area,unitprice,keyword,pageNum,pageSize);
		
	}

	public ResponseEntity<?> manage(
			@RequestParam(value="style",required=false) String style,
			@RequestParam(value="area",required=false) String area,
			@RequestParam(value="unitprice",required=false) String unitprice,//格式如 100-200
			@RequestParam(value="keyword",required=false) String keyword,
			@RequestParam(value="pageNum",required=false,defaultValue="1") String pageNum,
			@RequestParam(value="pageSize",required=false,defaultValue="12") String pageSize
			) {
		
		ResponseEntity<?> responseEntity=buildResponseEntity(ErrorEnum.SERVER_ERROR);
		
		Packages packages=new Packages();
		
		if (StringUtils.isNotEmpty(style)) {
			packages.setStyle(style);
		}
		
		if (StringUtils.isNotEmpty(area)) {
			packages.setArea(area);
		}

		Integer minprice=0;
		Integer maxprice=Integer.MAX_VALUE;
		
		if (StringUtils.isNotEmpty(unitprice)) {
			if(unitprice.contains("-")){
				String[] parr=unitprice.split("-");
				if(StringUtils.isNotEmpty(parr[0])){
					minprice=Integer.parseInt(parr[0]);
				}
				if(StringUtils.isNotEmpty(parr[1])){
					maxprice=Integer.parseInt(parr[1]);
				}
			}else{
				minprice=Integer.parseInt(unitprice);
			}
			
		}
		
		Pager<Packages> pager=packagesService.selectPageListByCondition(packages, keyword, Integer.parseInt(pageNum), Integer.parseInt(pageSize),minprice,maxprice);
		if(pager!=null){
			responseEntity=buildResponseEntity(ErrorEnum.SUCCESS, pager);
		}
		return responseEntity;
		
	}

	/**
	 * 显示我的套餐(全查)
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/my")
	public ResponseEntity<?> my(@RequestParam(value="style",required=false) String style,
			@RequestParam(value="area",required=false) String area,
			@RequestParam(value="unitprice",required=false) String unitprice,
			@RequestParam(value="keyword",required=false,defaultValue="") String keyword,
			HttpServletRequest req, HttpServletResponse res) {
		
		ErrorEnum errorEnum=ErrorEnum.SERVER_ERROR;
		BaseVo2 baseVo2=new BaseVo2();
		
		Packages packages=new Packages();
		String userId=getUserId(req);
		if(StringUtil.isEmpty(userId)){
			errorEnum=ErrorEnum.NO_AUT_ERROR;
		}else{
			packages.setCreatedBy(userId);
			
			if (StringUtils.isNotEmpty(style)) {
				packages.setStyle(style);
			}		

			Integer minprice=0;
			Integer maxprice=Integer.MAX_VALUE;
			
			if(StringUtils.isNotEmpty(area)){
				packages.setArea(area);
			}
			
			if (StringUtils.isNotEmpty(unitprice)) {
				if(unitprice.contains("-")){
					String[] parr=unitprice.split("-");
					if(StringUtils.isNotEmpty(parr[0])){
						minprice=Integer.parseInt(parr[0]);
					}
					if(StringUtils.isNotEmpty(parr[1])){
						maxprice=Integer.parseInt(parr[1]);
					}
				}else{
					minprice=Integer.parseInt(unitprice);
				}
				
			}
			
			List<Packages> list=packagesService.selectByCondition(packages, keyword,minprice,maxprice);
			if(list!=null){
				errorEnum=ErrorEnum.SUCCESS;
				baseVo2.setData(list);
			}			
		}

		ResponseEntity<?> responseEntity=buildResponseEntity(errorEnum,baseVo2);
		
		return responseEntity;

	}


	/**
	 * 套餐详情
	 * 
	 * @param id
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "info")
	public ResponseEntity<?> view(String id, HttpServletRequest req,
			HttpServletResponse res) {
		
		ErrorEnum errorEnum=ErrorEnum.SERVER_ERROR;
		
		PackUpgVo packUpgVo=new PackUpgVo();
		
		Packages packages=packagesService.selectByPrimaryKey(id);
		if(packages==null){
			errorEnum=ErrorEnum.NOT_EXIST;
		}else{
			
			packUpgVo.setPackages(packages);
			
			PackageUpgrade pampckug=new PackageUpgrade();
			pampckug.setPackageName1(packages.getName());
			
			List<PackageUpgrade> upgradelist=packageUpgradeService.selectByCondition(pampckug);
			if(upgradelist!=null && upgradelist.size()>0){
				List<LinkedHashMap<String, Object>> upglist=new ArrayList<LinkedHashMap<String, Object>>();
				for(PackageUpgrade upg:upgradelist){
					LinkedHashMap<String, Object> lm=new LinkedHashMap<String,Object>();
					String name2=upg.getPackageName2();
					String zone=upg.getZone();
					Double differents=upg.getDifferences();
					if(name2!=null){
						lm.put("package", name2);
					}
					if(zone!=null){
						lm.put("zone", zone);
					}
					if(differents!=null){
						lm.put("differences", differents);
					}
					if(lm!=null){
						upglist.add(lm);
					}
				}
				
				if(upglist!=null){
					String jsonlist=JSONArray.toJSONString(upglist);
					System.out.println(jsonlist);
					String base64Jsonstr=StringUtils.base64Encode(jsonlist);
					packUpgVo.setUpgradesJson(base64Jsonstr);
				}
			}
			
			errorEnum=ErrorEnum.SUCCESS;
		}
		
		ResponseEntity<?> responseEntity=buildResponseEntity(errorEnum, packUpgVo);
		return responseEntity;
		
	}

	/**
	 * 编辑套餐信息(获取编辑页数据信息)
	 * @param id
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/toEditPage")
	public ResponseEntity<?> editor(String id, 
			@RequestParam(value="zones",required=false) String zonesJsonStr,
			HttpServletRequest req,HttpServletResponse res) {
		
		ErrorEnum errorEnum=ErrorEnum.SERVER_ERROR;
		PackVo packVo=new PackVo();
		
		Packages packages=packagesService.selectByPrimaryKey(id);
		if(packages==null){
			errorEnum=ErrorEnum.NOT_EXIST;
		}else{
			//套餐信息
			packVo.setPackages(packages);
			
			//通用规则信息
			List<Series> serieslist=seriesService.find(new HashMap<String,Object>());
			if(serieslist!=null){
				packVo.setSerieslist(serieslist);
			}
			
			//套餐区域信息
			if(StringUtils.isNotEmpty(zonesJsonStr)){
				//zonesJsonStr=new String(zonesJsonStr.getBytes("ISO8859-1"),"UTF-8");
				Map<String, String> zoneMap = new LinkedHashMap<String, String>();
				
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> list=(List<Map<String, Object>>) JSONArray.parse(zonesJsonStr);
				if(list!=null && list.size()>0){
					for(Map<String, Object> zmap:list){
						zoneMap.put((String) zmap.get("id"), (String)zmap.get("name"));
					}
				}
				if(zoneMap!=null){
					packVo.setZoneMap(zoneMap);
				}
			}
			errorEnum=ErrorEnum.SUCCESS;
		}
		
		ResponseEntity<?> responseEntity=buildResponseEntity(errorEnum, packVo);
		return responseEntity;
		
	}


	/**
	 * 创建套餐
	 * @param item
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/save")
	public ResponseEntity<?> create(
			@RequestParam(value="skp",required=false) String skp, 
			@RequestParam(value="json",required=false) String json, 
			@RequestParam(value="zones",required=false)String zones,
			HttpServletRequest req,HttpServletResponse res) {
		
		ErrorEnum errorEnum=ErrorEnum.SERVER_ERROR;
		
		List<PackageZone> zonelist=new ArrayList<PackageZone>();
		
		Packages packages=getByRequest(req);
		
		String userId=getUserId(req);
		
		if(StringUtils.isNotEmpty(userId)){
			if(StringUtils.isNotEmpty(json)){
				packages.setJson(json);
			}
			if(StringUtils.isNotEmpty(skp)){
				packages.setSkp(skp);
			}
			if(StringUtils.isNotEmpty(zones)){
				zonelist=JSONArray.parseArray(zones, PackageZone.class);
			}
			
			if(packagesService.insertPackagesInfo(packages, zonelist, userId)){
				errorEnum=ErrorEnum.SUCCESS;
			}			
		}else{
			errorEnum=ErrorEnum.NO_AUT_ERROR;
		}
		
		ResponseEntity<?> responseEntity=buildResponseEntity(errorEnum);
		return responseEntity;

	}


	/**
	 * 更新套餐信息
	 * @return
	 */
	@RequestMapping(value = "/update")
	public ResponseEntity<?> update(String id,
			@RequestParam(value="from",required=false) String from,
			@RequestParam(value="skp",required=false)String skp,
			@RequestParam(value="json",required=false)String json,
			HttpServletRequest req, HttpServletResponse res) {
		
		ErrorEnum errorEnum=ErrorEnum.SERVER_ERROR;
		
		Packages packages=getByRequest(req);
		
		String userId=getUserId(req);
		if(StringUtils.isNotEmpty(userId)){
			if(from !=null && "sketchup".equals(from)){
				Packages pck=packagesService.selectByPrimaryKey(id);
				if(pck!=null){
					pck.setJson(json);
					pck.setSkp(skp);
					packages=pck;
				}
			}
			
			if(packages!=null){
				if(packagesService.insertPackagesInfo(packages, null, userId)){
					errorEnum=ErrorEnum.SUCCESS;
				}
			}			
		}else{
			errorEnum=ErrorEnum.NO_AUT_ERROR;
		}
		

		ResponseEntity<?> responseEntity=buildResponseEntity(errorEnum);
		return responseEntity;

	}

	/**
	 * 删除套餐,逻辑删除
	 * @return
	 */
	@RequestMapping(value = "/del")
	public ResponseEntity<?> remove(String id,HttpServletRequest req) {
		
		ErrorEnum errorEnum=ErrorEnum.SERVER_ERROR;
		
		String userId=getUserId(req);
		if(StringUtils.isNotEmpty(userId)){
			if(packagesService.selectByPrimaryKey(id)==null){
				errorEnum=ErrorEnum.NOT_EXIST;
			}else{
				Packages packages=new Packages();
				packages.setModifiedBy(userId);
				packages.setId(id);
				if(packagesService.deletePackagesByKey(packages)){
					errorEnum=ErrorEnum.SUCCESS;
				}
			}			
		}else{
			errorEnum=ErrorEnum.NO_AUT_ERROR;
		}

		ResponseEntity<?> responseEntity=buildResponseEntity(errorEnum);
		return responseEntity;

	}
	
	
	public Packages getByRequest(HttpServletRequest request){
		
		Packages packages=new Packages();
		
		String id=request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			packages.setId(id);
		}
		String name=request.getParameter("name");
		if(StringUtil.isNotEmpty(name)){
			packages.setName(name);
		}
		String style=request.getParameter("style");
		if(StringUtil.isNotEmpty(style)){
			packages.setStyle(style);
		}
		String area=request.getParameter("area");
		if(StringUtil.isNotEmpty(area)){
			packages.setArea(area);
		}
		String unitprice=request.getParameter("unitprice");
		if(StringUtil.isNotEmpty(unitprice)){
			packages.setUnitprice(unitprice);
		}
		String json=request.getParameter("json");
		if(StringUtil.isNotEmpty(json)){
			packages.setJson(json);
		}
		String effect=request.getParameter("effect");
		if(StringUtil.isNotEmpty(effect)){
			packages.setEffect(effect);
		}
		String skp=request.getParameter("skp");
		if(StringUtil.isNotEmpty(skp)){
			packages.setSkp(skp);
		}
		String series=request.getParameter("series");
		if(StringUtil.isNotEmpty(series)){
			packages.setSeries(series);
		}

		return packages;
	}
}
