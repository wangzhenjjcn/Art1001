//package com.aladdin.user.vo;
//
//import org.springframework.beans.BeanUtils;
//
//import com.aladdin.base.ErrorEnum;
//import com.aladdin.user.entity.User;
//
//public class UserModelUtils {
//
//	public static UserVO po2vo(final User user, final ErrorEnum errorEnum) {
//		UserVO vo = null;
//		if (user != null) {
//			vo = new UserVO();
//			BeanUtils.copyProperties(user, vo);
//		}
//		return vo;
//	}
//
//}
