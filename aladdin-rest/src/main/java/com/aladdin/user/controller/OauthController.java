package com.aladdin.user.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aladdin.base.BaseController;
import com.aladdin.base.BaseVo;
import com.aladdin.base.ErrorEnum;
import com.aladdin.member.entity.Member;
import com.aladdin.member.entity.MemberVo;
import com.aladdin.member.service.MemberService;
import com.aladdin.oss.OssService;
import com.aladdin.properties.OssProperties;
import com.aladdin.user.cache.UserCacheService;
import com.aladdin.user.common.OauthContants;
import com.aladdin.user.entity.UserSwap;
import com.aladdin.user.mapper.UserSwapMapper;
import com.aladdin.user.vo.FileVO;
import com.aladdin.user.vo.HouseFileVO;
import com.aliyun.oss.model.PutObjectResult;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("api/v1/")
public class OauthController extends BaseController {

	//@Autowired
	//private UserService userService;
	
	@Autowired
	private MemberService memberService;

	@Autowired
	UserCacheService userCacheService;
	
	@Autowired
	private UserSwapMapper userSwapMapper;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("oauth/token")
	@ResponseBody
	public HttpEntity accessToken(HttpServletRequest request,
			@RequestParam(value = "client_id", defaultValue = "") String client_id,
			@RequestParam(value = "client_secret", defaultValue = "") String client_secret,
			@RequestParam(value = "username", defaultValue = "") String username,
			@RequestParam(value = "password", defaultValue = "") String password,
			@RequestParam(value = "grant_type", defaultValue = "") String grant_type,
			@RequestParam(value = "refresh_token", defaultValue = "") String refresh_token,
			HttpServletResponse httpresponse) throws OAuthSystemException, OAuthProblemException {
		// 校验客户端Id是否正确
		if (!OauthContants.CLIENTID.equals(client_id)) {
			OAuthResponse oAuthResponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
					.setError(OAuthError.TokenResponse.INVALID_CLIENT).setErrorDescription("无效的客户端Id")
					.buildJSONMessage();
			return new ResponseEntity(oAuthResponse.getBody(), HttpStatus.valueOf(oAuthResponse.getResponseStatus()));
		}

		// 检查客户端安全KEY是否正确
		if (!OauthContants.CLIENTKEY.equals(client_secret)) {
			OAuthResponse response = OAuthResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
					.setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT).setErrorDescription("客户端安全KEY认证失败！")
					.buildJSONMessage();
			return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
		}


		Member user = null;

		if (grant_type.equals(GrantType.PASSWORD.toString())) {

			//user = userService.find(username, password);
			user = memberService.find(username, password);

			if (user == null) {
				OAuthResponse response = OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_GRANT).setErrorDescription("用户名密码错误")
						.buildJSONMessage();
				return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
			}

		} else if (grant_type.equals(GrantType.REFRESH_TOKEN.toString())) {

			if (!userCacheService.exists(OauthContants.REFRESH_TOKEN + refresh_token)) {
				OAuthResponse response = OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_GRANT).setErrorDescription("错误的refreshToken")
						.buildJSONMessage();
				return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
			}

			// 获取用户信息
			user = (Member) userCacheService.get(OauthContants.REFRESH_TOKEN + refresh_token);

			userCacheService.del(OauthContants.REFRESH_TOKEN + refresh_token);

		} else {
			OAuthResponse response = OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
					.setError(OAuthError.TokenResponse.INVALID_GRANT).buildJSONMessage();
			return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
		}

		// 生成访问令牌
		OAuthIssuerImpl authIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
		String accessToken = authIssuerImpl.accessToken();
		String refreshToken = authIssuerImpl.refreshToken();


		userCacheService.set(OauthContants.ACCESS_TOKEN + accessToken, user, OauthContants.EXPIRESIN);
		userCacheService.set(OauthContants.REFRESH_TOKEN + refreshToken, user, 0);

		// 生成OAuth响应
		OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK).setAccessToken(accessToken)
				.setRefreshToken(refreshToken).setExpiresIn(OauthContants.EXPIRESIN + "")
				.setParam("created_at", String.valueOf(System.currentTimeMillis())).buildJSONMessage();

		return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
	}
	
	

	@RequestMapping("users/me")
	public ResponseEntity<?> me(HttpServletRequest request) {
		Member member = getLoginUser(request);
		member = memberService.find(member.getMobile());
		List<UserSwap> uss = null;
		if(member!=null){
			UserSwap us = new UserSwap();
			us.setUid(member.getId());
			uss = userSwapMapper.select(us);
		}
		if(uss!=null&&!uss.isEmpty()){
			member.setAvater(uss.get(0).getHeaderImg());
			member.setLocation(uss.get(0).getLocation());
			member.setLocationIds(uss.get(0).getLocationIds());
		}
		return buildResponseEntity(ErrorEnum.SUCCESS, (MemberVo) po2vo(member, new MemberVo()));
	}

	@RequestMapping("users/logout")
	@ResponseBody
	public ResponseEntity<?> logout(HttpServletRequest request) {
		String access_token = request.getHeader("access_token");
		userCacheService.del(OauthContants.ACCESS_TOKEN + access_token);
		return buildResponseEntity(ErrorEnum.SUCCESS, new BaseVo());
	}

	/**
	 * 批量上传文件（fastdfs）
	 * 
	 * @param files
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/upload")
	public void uploadFiles(@RequestParam("files") MultipartFile[] files,HttpServletResponse response) throws IOException {
		String res = uploadfile(files);
		FileVO fileVO = new FileVO();
		fileVO.setUrl(res);
		fileVO.setCode(ErrorEnum.SUCCESS.getCode());
		fileVO.setMsg(ErrorEnum.SUCCESS.getMsg());
		String result  = JSONObject.fromObject(fileVO).toString();
		response.setContentType("text/html");
		response.getWriter().write(result);
		response.getWriter().close();
	}
	
	/**
	 * 批量上传文件（fastdfs）
	 * 
	 * @param files
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("upload_house")
	@ResponseBody
	public ResponseEntity<?> uploadFiles(@RequestParam("skp") MultipartFile skp,@RequestParam("thumbnail") MultipartFile png) throws IOException {
		HouseFileVO vo = new HouseFileVO();
		
		String porfix = skp.getOriginalFilename().substring(skp.getOriginalFilename().lastIndexOf(".") + 1);
		String fileName = UUID.randomUUID().toString() + "." + porfix;
		PutObjectResult result = ossService.upload(fileName, skp.getInputStream());
		if (result.getETag() != null) {
			String url = ossProperties.getUrl() + "/" + fileName;
			vo.setSkp(url);
		}
		
		porfix = png.getOriginalFilename().substring(png.getOriginalFilename().lastIndexOf(".") + 1);
		fileName = UUID.randomUUID().toString() + "." + porfix;
		result = ossService.upload(fileName, png.getInputStream());
		if (result.getETag() != null) {
			String url = ossProperties.getUrl() + "/" + fileName;
			vo.setPng(url);
		}
		return buildResponseEntity(ErrorEnum.SUCCESS, vo);
	}

	@Autowired
	private OssService ossService;

	@Autowired
	private OssProperties ossProperties;

	/**
	 * 批量上传文件
	 * 
	 * @param files
	 * @return
	 */
	public String uploadfile(MultipartFile[] files) {
		StringBuffer sb = new StringBuffer();

		for (MultipartFile file : files) {
			try {
				String porfix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
				String fileName = UUID.randomUUID().toString() + "." + porfix;
				PutObjectResult result = ossService.upload(fileName, file.getInputStream());
				if (result.getETag() != null) {
					String url = ossProperties.getUrl() + "/" + fileName;
					sb.append(url).append(",");
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String res = "";
		if (sb != null) {
			res = sb.toString();
			if (!StringUtils.isEmpty(res)) {
				res = res.substring(0, res.length() - 1);
			}
		}
		return res;
	}

}
