package com.aladdin.series.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aladdin.base.BaseController;
import com.aladdin.base.BaseVo;
import com.aladdin.base.BaseVo2;
import com.aladdin.base.ErrorEnum;
import com.aladdin.base.Pager;
import com.aladdin.series.entity.Series;
import com.aladdin.series.entity.SeriesVo;
import com.aladdin.series.service.SeriesService;

/**
 * 通用规则
 * 
 * @author hyy 2016年9月27日
 */
@Controller
@RequestMapping("/api/v1/series")
public class SeriesController extends BaseController {

	@Autowired
	private SeriesService seriesService;


	/**
	 * 获取规则列表(分页查询)
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "")
	@ResponseBody
	public ResponseEntity<?> list(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "pageIndex", defaultValue = "1", required = false) String pageIndex,
			@RequestParam(value = "pageSize", defaultValue = "20", required = false) String pageSize,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "createBy", required = false) String userId) {

		Map<String, Object> map = new HashMap<String, Object>();

		if (StringUtils.isEmpty(name) == false) {
			map.put("name", name);
		}
		if (StringUtils.isEmpty(userId) == false) {
			map.put("createBy", userId);
		}
		
		Pager<Series> pager=seriesService.find(map, Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
		
		@SuppressWarnings("rawtypes")
		ResponseEntity<?> responseEntity=new ResponseEntity<Pager>(pager, HttpStatus.OK);
		
		return responseEntity;

	}
	
	
	/**
	 * 获取规则列表(全查)
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/all")
	@ResponseBody
	public ResponseEntity<?> showlist(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "createBy", required = false) String userId) {
		
		ResponseEntity<?> responseEntity=buildResponseEntity(ErrorEnum.SERVER_ERROR);
		
		Map<String, Object> map = new HashMap<String, Object>();

		if (StringUtils.isEmpty(name) == false) {
			map.put("name", name);
		}
		if (StringUtils.isEmpty(userId) == false) {
			map.put("createBy", userId);
		}
		
		List<Series> list=seriesService.find(map);
		
		if(list!=null){
			//errorEnum=ErrorEnum.SUCCESS;
			BaseVo2 baseVo2=new BaseVo2();
			baseVo2.setData(list);
			
			responseEntity = buildResponseEntity(ErrorEnum.SUCCESS, baseVo2);
		}
		return responseEntity;

	}

	/**
	 * 根据id查询单个规则详情
	 * 
	 * @param id
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/info")
	@ResponseBody
	public ResponseEntity<?> editor(String id, HttpServletRequest req, HttpServletResponse res) {
		
		SeriesVo seriesVo=new SeriesVo();
		ResponseEntity<?> responseEntity=buildResponseEntity(ErrorEnum.SERVER_ERROR, seriesVo);
		Series series = seriesService.find(id);
		if(series!=null){
			BeanUtils.copyProperties(series, seriesVo);
			
			responseEntity=buildResponseEntity(ErrorEnum.SUCCESS, seriesVo);
		}
		
		return responseEntity;
	}

	/**
	 * 创建规则
	 * 
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public ResponseEntity<?> create(HttpServletRequest req, HttpServletResponse res) {
		
		SeriesVo seriesVo=new SeriesVo();
		ResponseEntity<?> responseEntity=buildResponseEntity(ErrorEnum.SERVER_ERROR, seriesVo);

		String name = req.getParameter("name");
		String userId = getUserId(req);
		if(StringUtils.isEmpty(userId)){
			responseEntity=buildResponseEntity(ErrorEnum.NO_AUT_ERROR);
		}else{
			if (StringUtils.isEmpty(name) == false && StringUtils.isEmpty(userId)==false) {
				Series series = getByRequest(req);
				series.setCreateBy(userId);
				String uuid = com.aladdin.utils.StringUtils.getUUID();
				series.setId(uuid);

				if (seriesService.create(series) > 0) {
					series = seriesService.find(uuid);
					BeanUtils.copyProperties(series, seriesVo);
					
					responseEntity=buildResponseEntity(ErrorEnum.SUCCESS, seriesVo);
				}
			}			
		}
		return responseEntity;
	}

	/**
	 * 修改规则（实际上实现添加功能）
	 * 
	 * @param id
	 * @param item
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public ResponseEntity<?> update(String id, HttpServletRequest req, HttpServletResponse res) {
		return create(req, res);
	}

	/**
	 * 删除规则（逻辑删除）
	 * 
	 * @param id
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public ResponseEntity<?> remove(String id, HttpServletRequest req, HttpServletResponse res) {
		
		ResponseEntity<?> responseEntity =  buildResponseEntity(ErrorEnum.SERVER_ERROR,new BaseVo());
		
		if(StringUtils.isEmpty(id)==false){
			if(seriesService.remove(id)>=0){
				responseEntity=buildResponseEntity(ErrorEnum.SUCCESS,new BaseVo());
			}
		}
		
		return responseEntity;
	}
	
	/**
	 * 从request中获取series信息
	 * 
	 * @param req
	 * @return
	 */
	public Series getByRequest(HttpServletRequest req) {
		Series series = new Series();
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String json = req.getParameter("json");
		if (StringUtils.isEmpty(id) == false) {
			series.setId(id);
		}
		if (StringUtils.isEmpty(name) == false) {
			series.setName(name);
		}
		if (StringUtils.isEmpty(json) == false) {
			series.setJson(json);
		}
		return series;
	}

}
