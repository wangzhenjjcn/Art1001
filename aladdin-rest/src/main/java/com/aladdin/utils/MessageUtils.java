package com.aladdin.utils;

import java.util.HashMap;
import java.util.Map;


public class MessageUtils {
	
	public static final String PARAMS_ERROR = "300";
	public static final String SERVER_ERROR = "500";
	public static final String ACCESS_ERROR = "400";
	public static final String NO_AUT_ERROR = "403";//令牌错误
	public static final String SUCCESS = "200";
	
	public static Map<String, Object> showResultMessage(String code,String message,Object object){
		Map<String, Object> map=new HashMap<String, Object>();
		if(code!=null)
			map.put("code", code);
		if(message!=null)
			map.put("msg", message);
		if(object!=null)
			map.put("data", object);
		return map;
	}
	
	/**
	 * 令牌错误,403
	 * @return
	 */
	public static Map<String, Object> showErrorToken(){
		return showResultMessage(NO_AUT_ERROR, "令牌错误", null);
	}
	
	/**
	 * 操作成功 200
	 * @param msg
	 * @return
	 */
	public static Map<String, Object> showSuccess(String msg){
		return showResultMessage(SUCCESS, msg, null);
	}
	
	/**
	 * 操作成功 200
	 * @param msg
	 * @return
	 */
	public static Map<String, Object> showSuccess(String msg,Object obj){
		return showResultMessage(SUCCESS, msg, obj);
	}
	
	/**
	 * 操作失败 500
	 * @param msg
	 * @return
	 */
	public static Map<String, Object> showFail(String msg){
		return showResultMessage(SERVER_ERROR, msg, null);
	}
	
//	/**
//	 * 操作成功
//	 * @param msg
//	 * @return
//	 */
//	public static Map<String, Object> showSuccessMessage(String msg){
//		return showResultMessage(ContantsUtils.SUCCESS_CODE, msg, null);
//	}
//	
//	/**
//	 * 操作成功
//	 * @param msg
//	 * @return
//	 */
//	public static Map<String, Object> showSuccessMessage(String msg,Object object){
//		return showResultMessage(ContantsUtils.SUCCESS_CODE, msg, object);
//	}
//	
//	/**
//	 * 操作失败
//	 * @param msg
//	 * @return
//	 */
//	public static Map<String, Object> showErrorMessage(String msg){
//		return showResultMessage(ContantsUtils.ERROR_CODE, msg, null);
//	}
//	
//	/**
//	 * 操作失败
//	 * @param msg
//	 * @return
//	 */
//	public static Map<String, Object> showErrorMessage(String code,String msg){
//		return showResultMessage(code, msg, null);
//	}
//	
//	/**
//	 * 查询结果不存在
//	 * @param msg
//	 * @return
//	 */
//	public static Map<String, Object> showNotExitMessage(String msg){
//		return showResultMessage(ContantsUtils.NOT_EXIT_CODE, msg, null);
//	}
//	
//	/**
//	 * 未登陆
//	 * @param msg
//	 * @return
//	 */
//	public static Map<String, Object> showNotLoginMessage(){
//		return showResultMessage(ContantsUtils.NOT_LOGIN, "用户未登陆", null);
//	}
//	
//	/**
//	 * 会员不存在
//	 * @return
//	 */
//	public static Map<String, Object> showMemberNotExitMessage(){
//		return showResultMessage(ContantsUtils.MEMBER_NOTEXIT_CODE, "会员不存在", null);
//	}
//	
//	/**
//	 * 参数不合法
//	 * @return
//	 */
//	public static Map<String, Object> showErrorParamMessage(){
//		return showResultMessage(ContantsUtils.ERROR_PARAM_CODE, "参数不合法", null);
//	}
//	
//	/**
//	 * 非法操作
//	 * @return
//	 */
//	public static Map<String, Object> showErrorOperateMessage(){
//		return showResultMessage(ContantsUtils.ERROR_OPERT_CODE, "非法操作", null);
//	}
//	
//	/**
//	 * 判断操作是否成功（状态码 10000 表示成功，否则失败）
//	 * @param map
//	 * @return
//	 */
//	public static boolean isOk(Map<String, Object> map){
//		String code=(String)map.get("code");
//		if(!org.springframework.util.StringUtils.isEmpty(code) && code.equals("10000")){
//			return true;
//		}
//		return false;
//	}
	
	
}

