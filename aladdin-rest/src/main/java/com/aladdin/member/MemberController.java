package com.aladdin.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.aladdin.base.BaseController;
import com.aladdin.base.BaseVo3;
import com.aladdin.base.ErrorEnum;
import com.aladdin.base.Pager;
import com.aladdin.member.entity.Member;
import com.aladdin.member.entity.MemberVo;
import com.aladdin.member.service.MemberService;
import com.aladdin.utils.StringUtils;

@Controller
@RequestMapping("/api/v1/member")
public class MemberController extends BaseController{
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/info")
	public ResponseEntity<?> getMemberInfo(HttpServletRequest req,HttpServletResponse res,
			@RequestParam(value="memberName")String memberName,
			//@RequestParam(value="memberPasswd",defaultValue="")String memberPasswd,
			@RequestParam(value="memberMobile",defaultValue="")String memberMobile){
		
		ErrorEnum errorEnum=ErrorEnum.SERVER_ERROR;
		
		MemberVo vo=new MemberVo();
		
		Member member=new Member();
		if(StringUtils.isNotEmpty(memberName)){
			member.setName(memberName);
		}
		if(StringUtils.isNotEmpty(memberMobile)){
			member.setMobile(memberMobile);
		}
		
		Member m=memberService.selectByCondition(member);
		
		if(m!=null){
			errorEnum=ErrorEnum.SUCCESS;
			try {
				vo=(MemberVo) po2vo(m, vo);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}else{
			errorEnum=ErrorEnum.NOT_EXIST;
		}
		
		return buildResponseEntity(errorEnum, vo);
		
	} 
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/pagelist",method=RequestMethod.POST)
	public ResponseEntity<?> list(
			@RequestParam(value="pageNum",defaultValue="1")Integer pageNum,
			@RequestParam(value="pageSize",defaultValue="20")Integer pageSize,
			HttpServletRequest request,HttpServletResponse response){
		ErrorEnum errorEnum=ErrorEnum.SERVER_ERROR;
		
		BaseVo3 baseVo3=new BaseVo3();
		
		Pager<Member> pager=memberService.selectPagelistByCondition(new Member(),pageNum,pageSize);
		List<Member> list=pager.getData();
		Map<String, Object> map=new HashMap<String,Object>();
		
		if(pager!=null){
			List<Map<String, Object>> maplist=new ArrayList<Map<String,Object>>();
			if(list.size()>0){
				for(Member member:list){
					Map<String,Object> maps=new HashMap<String,Object>();
					maps.put("memberId", member.getId());
					maps.put("memberName", member.getName());

					maps.put("memberMobile", member.getMobile());
					
					maplist.add(maps);
				}
			}
			if(maplist!=null){
				map.put("data", maplist);
			}
			map.put("total", pager.getTotal());
			map.put("pageNum", pageNum);
			map.put("pageSize", pageSize);
			map.put("maxPages", pager.getMaxPages()==0?1:pager.getMaxPages());
			baseVo3.setData(map);
			errorEnum=ErrorEnum.SUCCESS;
		
		}else{
			errorEnum=ErrorEnum.NOT_EXIST;
		}
		
		return buildResponseEntity(errorEnum, baseVo3);
	}

}
