//package com.aladdin.user.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.aladdin.user.entity.User;
//import com.aladdin.user.mapper.UserMapper;
//import com.aladdin.utils.Digests;
//import com.aladdin.utils.RegexpUtils;
//
//@Service
//public class UserServiceImpl implements UserService {
//
//	@Autowired
//	private UserMapper userMapper;
//
//	@Override
//	public User find(String account,String password) {
//		User member =new User();
//		if (RegexpUtils.checkEmail(account)) {
//			member.setEmail(account);
//		}else if (RegexpUtils.checkMobile(account)) {
//			member.setMobile(account);
//		}else {
//			return null;
//		}
//		member.setPassword(Digests.entryptPassword(password));
//		return userMapper.selectOne(member);
//	}
//
//	@Override
//	public User find(String account) {
//		User member =new User();
//		if (RegexpUtils.checkEmail(account)) {
//			member.setEmail(account);
//		}else if (RegexpUtils.checkMobile(account)) {
//			member.setMobile(account);
//		}else {
//			return null;
//		}
//		return userMapper.selectOne(member);
//	}
//
//	@Override
//	public void update(User user) {
//		userMapper.updateByPrimaryKey(user);
//	}
//	
//	
//}
