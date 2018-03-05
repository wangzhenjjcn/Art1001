package com.aladdin.member.service;

import com.aladdin.base.Pager;
import com.aladdin.member.entity.Member;

public interface MemberService {
	
	/**
	 * 条件查询(可用用户)
	 * @return
	 */
	Pager<Member> selectPagelistByCondition(Member member,Integer pageNum,Integer pageSize);
	
	
	/**
	 * 条件查询
	 * @param
	 * @return
	 */
	Member selectByCondition(Member member);
	
	/**
	 * 根据账号和密码查询
	 * @param account
	 * @param password
	 * @return
	 */
	Member find(String account,String password);
	
	/**
	 * 根据账号查询
	 * @param account
	 * @return
	 */
	Member find(String account);
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	Member findByKey(String id);
	
	/**
	 * 根据主键修改会员信息，0修改失败，1修改成功，2用户名或手机号已存在
	 * @param member
	 * @return
	 */
	Integer update(Member member);
	
	/**
	 * 修改密码
	 * @param id
	 * @param pwd
	 * @return
	 */
	boolean updatePwd(String id,String pwd);

	
	
}
