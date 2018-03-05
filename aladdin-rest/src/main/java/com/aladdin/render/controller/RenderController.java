package com.aladdin.render.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aladdin.base.BaseController;
import com.aladdin.base.BaseVo;
import com.aladdin.base.ErrorEnum;
import com.aladdin.oss.OssService;
import com.aladdin.properties.OssProperties;
import com.aladdin.render.entity.RenderTask;
import com.aladdin.render.service.RenderTaskService;
import com.aliyun.oss.model.PutObjectResult;
import com.google.common.collect.Maps;

import atg.taglib.json.util.Test;

@RestController
@RequestMapping("api/v1/render")
public class RenderController extends BaseController {
	
	private static final Integer state_created = 1;
	private static final Integer state_rendered = 2;
	@Autowired
	private RenderTaskService  renderTaskService;
	
	@Autowired
	private OssService ossService;
	
	@Autowired
	private OssProperties ossProperties;

	@RequestMapping(value="upload")
	public ResponseEntity<?> create(MultipartFile file){
		if(file!=null){
			String inputUrl = uploadfileGetTag(file);
			if(!"".equals(inputUrl)){
				RenderTask rt = new RenderTask();
				
				String orignFileName = file.getOriginalFilename();
				Date beginTime = new Date();
				
				rt.setBeginTime(beginTime);
				rt.setName(orignFileName);
				rt.setInputUrl(inputUrl);
				rt.setState(state_created);
				rt.setId(UUID.randomUUID().toString().replace("-", ""));
				
				int num = renderTaskService.create(rt);
				if(num>0){
					Map<String,Object> map = Maps.newHashMap();
					map.put("code",ErrorEnum.SUCCESS.getCode());
					map.put("msg","文件上传成功,正在进行渲染...");
					map.put("data",rt);
					return new ResponseEntity<>(map,HttpStatus.OK);
				}
			}
		}
		BaseVo vo = new BaseVo();
		vo.setCode(ErrorEnum.SERVER_ERROR.getCode());
		vo.setMsg("上传文件失败！");
		return new ResponseEntity<>(vo,HttpStatus.OK);
	}
	
	@RequestMapping("testMq")
	public String testMq(){
		RenderTask rt = new RenderTask();

		Date beginTime = new Date();
		
		rt.setBeginTime(beginTime);
		rt.setState(state_created);
		rt.setId(UUID.randomUUID().toString().replace("-", ""));
		
		int num = renderTaskService.create(rt);
		return "success"+num;
	}
	
	@RequestMapping(value="{id}")
	public ResponseEntity<?> find(
			@PathVariable(value="id") String id){
		RenderTask rt = null;
		Map<String,Object> map = new HashMap<>();
		if(!StringUtils.isEmpty(id)){
			 rt = renderTaskService.findById(id);
			 if(rt!=null){
				 map.put("code", ErrorEnum.SUCCESS.getCode());
				 map.put("msg","ok");
				 map.put("data",rt);
				 return new ResponseEntity<>(map,HttpStatus.OK);
			 }
		}
		BaseVo vo = new BaseVo();
		vo.setCode(ErrorEnum.NOT_EXIST.getCode());
		vo.setMsg("查无数据");
		return new ResponseEntity<>(vo,HttpStatus.OK);
	}
	@RequestMapping(value="over",method=RequestMethod.POST)
	public ResponseEntity<?> update(
			String id,
			String outputUrl
			){
		
		BaseVo vo = new BaseVo();
		
		if(id!=null&&!StringUtils.isEmpty(outputUrl)){
			
		  RenderTask rt = renderTaskService.findById(id);
		  if(rt!=null){
			  rt.setOutputUrl(outputUrl);
			  rt.setState(state_rendered);
			  rt.setEndTime(new Date());
			  int i = renderTaskService.update(rt);
			  if(i>0){
				  vo.setCode(ErrorEnum.SUCCESS.getCode());
				  vo.setMsg("更新渲染信息成功！");
				  return new ResponseEntity<>(vo,HttpStatus.OK);
			  }
		  }
		}
		  vo.setCode(ErrorEnum.SERVER_ERROR.getCode());
		  vo.setMsg("更新渲染信息失败！");
		  return new ResponseEntity<>(vo,HttpStatus.OK);
	}
	
	public String uploadfile(MultipartFile file) {
		
		try {
			String porfix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
			String fileName = UUID.randomUUID().toString() + "." + porfix;
			PutObjectResult result = ossService.upload(fileName, file.getInputStream());
			if (result.getETag() != null) {
				String url = ossProperties.getUrl() + "/" + fileName;
				return url;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String uploadfileGetTag(MultipartFile file) {
		
		try {
			String porfix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
			String fileName = UUID.randomUUID().toString() + "." + porfix;
			PutObjectResult result = ossService.upload(fileName, file.getInputStream());
			if (result.getETag() != null) {
				return fileName;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
