package com.aladdin.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aladdin.base.BaseController;
import com.aladdin.base.BaseVo;
import com.aladdin.base.BaseVo2;
import com.aladdin.base.ErrorEnum;
import com.aladdin.member.entity.Member;
import com.aladdin.member.service.MemberService;
import com.aladdin.user.cache.UserCacheService;
import com.aladdin.user.common.OauthContants;
import com.aladdin.user.entity.Menus;
import com.aladdin.user.entity.UserSwap;
import com.aladdin.user.mapper.MenusMapper;
import com.aladdin.user.mapper.UserSwapMapper;

@Controller
@RequestMapping("api/v1/")
public class UserController extends BaseController {

	//@Autowired
	//private UserService userService;
	@Autowired
	private MenusMapper menusMapper;
	@Autowired
	private MemberService memberService;
	@Autowired
	private UserSwapMapper userSwapMapper;
	@Autowired
	UserCacheService userCacheService;

	@RequestMapping(value = "/users/findpwd")
	public ResponseEntity<?> findPwd(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "account", defaultValue = "", required = true) String account) {
		//User user = userService.find(account);
		Member user=memberService.find(account);
		if (user == null) {
			return buildResponseEntity(ErrorEnum.NOT_ACCOUNT, new BaseVo());
		} else {
			// TODO 重置密码操作
			return buildResponseEntity(ErrorEnum.SUCCESS, new BaseVo());
		}
	}

	@RequestMapping(value = "/users")
	public ResponseEntity<?> update(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "truename", required = false) String truename,
			@RequestParam(value = "sex",required = false) String sex,
			@RequestParam(value = "birthday", required = false) Long birthday,
			@RequestParam(value = "avater", required = false) String avater,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "location", required = false) String location,
			@RequestParam(value = "locationIds", required = false) String locationIds
			
			) {
		
		Member user = getLoginUser(request);
		UserSwap uw = new UserSwap();
		uw.setUid(user.getId());
		user = memberService.findByKey(user.getId());
		if(StringUtils.isNotEmpty(avater)){
			uw.setHeaderImg(avater);
		}
		if(StringUtils.isNotEmpty(email)){
			user.setEmail(email);
		}
		if(StringUtils.isNotEmpty(mobile)){
			user.setMobile(mobile);
		}
		/*if(StringUtils.isNotEmpty(name)){
			user.setTruename(name);
		}*/
		if(StringUtils.isNotEmpty(truename)){
			user.setTruename(truename);
		}
		if (birthday != null) {
			//user.setBirthday(new Date(birthday));
			user.setMemberBirthday(birthday);
		}
		if(StringUtils.isNotEmpty(location)){
			uw.setLocation(location);
		}
		
		if(StringUtils.isNotEmpty(locationIds)){
			uw.setLocationIds(locationIds);
		}
		
		if("".equals(sex)){
			user.setSexCode(0);
		}else if(sex.equals("男")||"1".equals(sex)){
			user.setSexCode(1);
		}else if(sex.equals("女")||"2".equals(sex)){
			user.setSexCode(2);
		}
		
//		userService.update(user);
		int i=memberService.update(user);
		ErrorEnum errorEnum=ErrorEnum.SERVER_ERROR;
		if(i==1){
			userCacheService.set(OauthContants.ACCESS_TOKEN + request.getParameter("access_token"), user, OauthContants.EXPIRESIN);
			UserSwap us  = userSwapMapper.selectByPrimaryKey(user.getId());
			int uwflag = 0;
			if(us==null){
				uwflag = userSwapMapper.insertSelective(uw);
			}else{
				uwflag = userSwapMapper.updateByPrimaryKeySelective(uw);
			}
			if(uwflag==1){
				errorEnum=ErrorEnum.SUCCESS;
			}
		}else if(i==2){
			errorEnum=ErrorEnum.ACCOUNT_EXIST;
		}
		return buildResponseEntity(errorEnum, new BaseVo());
	}

	@RequestMapping(value = "/users/pwd")
	public ResponseEntity<?> pwd(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "oldpwd", defaultValue = "", required = true) String oldpwd,
			@RequestParam(value = "newpwd", defaultValue = "", required = true) String newpwd) {
		//User user = getLoginUser(request);
		Member user=getLoginUser(request);
		//user = userService.find(user.getMobile(), oldpwd);
		user=memberService.find(user.getMobile(), oldpwd);
		if (user != null) {
			//user.setPassword(Digests.entryptPassword(newpwd));

			if(memberService.updatePwd(user.getId(), newpwd)){
				return buildResponseEntity(ErrorEnum.SUCCESS, new BaseVo());
			}
			//userService.update(user);
			return buildResponseEntity(ErrorEnum.SERVER_ERROR, new BaseVo());
		} else {
			return buildResponseEntity(ErrorEnum.PASSWORD_ERROR, new BaseVo());
		}
	}

	@RequestMapping(value = "/menus")
	public ResponseEntity<?> menus(HttpServletRequest request, HttpServletResponse response) {
		//User user = getLoginUser(request);
		Member user=getLoginUser(request);
		BaseVo2 vo2 = new BaseVo2();
		
		vo2.setData(getMenus(0));
		
		return buildResponseEntity(ErrorEnum.SUCCESS, vo2);
	}
	
	public List<Menus> getMenus(int role){
		Menus menus = new Menus();
		menus.setStatus(0);
		return menusMapper.select(menus);
	}

}
