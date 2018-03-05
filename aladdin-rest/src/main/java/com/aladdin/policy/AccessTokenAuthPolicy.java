package com.aladdin.policy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.aladdin.member.entity.Member;
import com.aladdin.user.cache.UserCacheService;
import com.aladdin.user.common.OauthContants;

/**
 * 
 * 1:检测用户是否已经登录，用户登录是进行下面检测的前提。
 * 
 * @author shihan
 *
 */
@Component
public class AccessTokenAuthPolicy implements HandlerInterceptor {

	@Autowired
	UserCacheService userCacheService;

	private String[] excelUrl = {
			"/api/v1/oauth/token",
			"/error",
			"/api/v1/users/findpwd"
			
	};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getServletPath();
		if (hasUrl(url)) {
			return true;
		}
		
		if (request.getMethod().equals("OPTIONS")) {
			return true;
		}
		
		Object access_token = request.getParameter("access_token");
		if (access_token == null) {
			response.sendError(HttpStatus.UNAUTHORIZED.value());
			return false;
		}
		//User member = userCacheService.get(OauthContants.ACCESS_TOKEN + access_token.toString());
		Member member = userCacheService.get(OauthContants.ACCESS_TOKEN + access_token.toString());
		if (member == null) {
			response.sendError(HttpStatus.UNAUTHORIZED.value());
			return false;
		}
		return true;
	}
	
	private boolean hasUrl(final String url){
		for (int i = 0; i < excelUrl.length; i++) {
			if (url.equals(excelUrl[i])) {
				return true;
			}
		}
		return false;
	}
	

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
