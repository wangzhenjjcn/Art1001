package com.aladdin.security.service;

import com.aladdin.security.entity.UserBean;

public interface UserService {
	
	UserBean findById(String id);
	
	boolean isExists(UserBean user);
}
