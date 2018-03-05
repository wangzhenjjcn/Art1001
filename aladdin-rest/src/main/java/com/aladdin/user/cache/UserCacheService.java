package com.aladdin.user.cache;

import com.aladdin.member.entity.Member;

public interface UserCacheService {

	Member get(String key);

	void set(String key, Member member, int exp);

	boolean exists(String key);

	void del(String key);

}
