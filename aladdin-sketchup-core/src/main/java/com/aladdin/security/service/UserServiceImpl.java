package com.aladdin.security.service;

import org.springframework.stereotype.Service;

import com.aladdin.security.entity.UserBean;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public UserBean findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExists(UserBean user) {
		// TODO Auto-generated method stub
		return false;
	}

}
