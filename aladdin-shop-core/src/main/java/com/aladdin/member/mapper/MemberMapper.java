package com.aladdin.member.mapper;

import org.apache.ibatis.annotations.Param;

import com.aladdin.member.entity.Member;

import tk.mybatis.mapper.common.Mapper;

public interface MemberMapper extends Mapper<Member>{
	
	/**
	 * 用于验证登录
	 * @param memberName
	 * @param password
	 * @return
	 */
	Member selectByMemberinfo(@Param("memberName") String memberName,
			@Param("password") String password,
			@Param("isBuy")Integer isBuy);
	
}
