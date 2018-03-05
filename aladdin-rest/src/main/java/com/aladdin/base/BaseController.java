package com.aladdin.base;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.aladdin.member.entity.Member;
import com.aladdin.user.cache.UserCacheService;
import com.aladdin.user.common.OauthContants;

@Controller
public class BaseController {

	@Autowired
	private UserCacheService userCacheService;

	public Member getLoginUser(HttpServletRequest request) {
		String access_token = request.getParameter("access_token");
		Member member = userCacheService.get(OauthContants.ACCESS_TOKEN + access_token.toString());
		
		return member;
	}
	
	//获取登录用户id
	public String getUserId(HttpServletRequest request){
		Member user=getLoginUser(request);
		if(user!=null){
			return user.getId();
		}
		return null;
	}

	protected ResponseEntity<?> buildResponseEntity(ErrorEnum errorEnum,BaseVo vo) {
		if(vo==null){
			vo=new BaseVo();
		}
		vo.code = errorEnum.getCode();
		vo.msg = errorEnum.getMsg();
		return new ResponseEntity<BaseVo>(vo, HttpStatus.OK);
	}
	protected ResponseEntity<?> buildResponseEntity(ErrorEnum errorEnum) {
		BaseVo vo = new BaseVo();
		return buildResponseEntity(errorEnum,vo);
	}

	public <T> T po2vo(final T source, final T target) {
		BeanUtils.copyProperties(source, target);
		return target;
	}
	
	
}
