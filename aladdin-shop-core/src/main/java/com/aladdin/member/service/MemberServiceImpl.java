package com.aladdin.member.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladdin.base.Pager;
import com.aladdin.member.entity.Member;
import com.aladdin.member.mapper.MemberMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("memberService")
public class MemberServiceImpl implements MemberService {
	
	public static final Byte is_del_ok=1;//删除
	
	public static final Byte is_del_no=0;//未删除
	
	@Autowired
	private MemberMapper memberMapper;
	

	@Override
	public Pager<Member> selectPagelistByCondition(Member member,Integer pageNum,Integer pageSize) {
		// TODO Auto-generated method stub
		if(member==null){
			member=new Member();
		}
		member.setIsDel(is_del_no);
		
		PageHelper.startPage(pageNum-1, pageSize, true);
		
		List<Member> list=memberMapper.select(member);
		
		Page<Member> page=(Page<Member>) list;
		
		Pager<Member> pager=new Pager<>(pageNum, pageSize);
		
		pager.setData(page.getResult());
		pager.setMaxPages(page.getPages());
		pager.setTotal((int) page.getTotal());
		
		return pager;
	}


	@Override
	public Member selectByCondition(Member member) {
		// TODO Auto-generated method stub
		member.setIsDel(is_del_no);
		return memberMapper.selectOne(member);
	}


	@Override
	public Member find(String account, String password) {
		// TODO Auto-generated method stub
		Member member = new Member();
		if(StringUtils.isNotEmpty(account)&&StringUtils.isNotEmpty(password)){			
			password=ecodeByMD5(password);
			member=memberMapper.selectByMemberinfo(account, password, null);
		}
		return member;
	}


	@Override
	public Member find(String account) {
		// TODO Auto-generated method stub
		Member member = new Member();
		if(StringUtils.isNotEmpty(account)){
			//member.setMemberName(account);
			member=memberMapper.selectByMemberinfo(account, null, null);
		}
		return member;
	}
	
	
	/**
     * 将指定的字符串用MD5加密
     * @param originstr 需要加密的字符串
     * @return 加密后的字符串
     */
    public String ecodeByMD5(String originstr){
        String result = null;
        char hexDigits[] = {//用来将字节转换成 16 进制表示的字符
             '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'}; 
        if(originstr != null){
            try {
                //返回实现指定摘要算法的 MessageDigest 对象
                MessageDigest md = MessageDigest.getInstance("MD5");
                //使用utf-8编码将originstr字符串编码并保存到source字节数组
                byte[] source = originstr.getBytes("utf-8");
                //使用指定的 byte 数组更新摘要
                md.update(source);
                //通过执行诸如填充之类的最终操作完成哈希计算，结果是一个128位的长整数
                byte[] tmp = md.digest();
                //用16进制数表示需要32位
                char[] str = new char[32];
                for(int i=0,j=0; i < 16; i++){
                    //j表示转换结果中对应的字符位置
                    //从第一个字节开始，对 MD5 的每一个字节
                    //转换成 16 进制字符
                    byte b = tmp[i];
                    //取字节中高 4 位的数字转换
                    //无符号右移运算符>>> ，它总是在左边补0
                    //0x代表它后面的是十六进制的数字. f转换成十进制就是15
                    str[j++] = hexDigits[b>>>4 & 0xf];
                    // 取字节中低 4 位的数字转换
                    str[j++] = hexDigits[b&0xf];
                }
                result = new String(str);//结果转换成字符串用于返回
            } catch (NoSuchAlgorithmException e) {
                //当请求特定的加密算法而它在该环境中不可用时抛出此异常
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                //不支持字符编码异常
                e.printStackTrace();
            }
        }
        return result;
    }


	@Override
	public Integer update(Member member) {
		// TODO Auto-generated method stub
		Member member2=new Member();
		String newName=member.getName();
		String newMobile=member.getMobile();
		
		if(StringUtils.isNotEmpty(newName)||StringUtils.isNotEmpty(newMobile)){
			if(StringUtils.isNotEmpty(newName)){
				member2.setName(newName);
			}
			if(StringUtils.isNotEmpty(newMobile)){
				member2.setMobile(newMobile);
			}	
			Member res1=memberMapper.selectOne(member2);
			if(res1!=null&&!(res1.getId().equals(member.getId()))){
				return 2;
			}
		}

		return memberMapper.updateByPrimaryKeySelective(member);

	}


	@Override
	public Member findByKey(String id) {
		// TODO Auto-generated method stub
		return memberMapper.selectByPrimaryKey(id);
	}


	@Override
	public boolean updatePwd(String id, String pwd) {
		// TODO Auto-generated method stub
		Member member=new Member();
		member.setId(id);
		member.setPassword(ecodeByMD5(pwd));
		if(memberMapper.updateByPrimaryKeySelective(member)==1){
			return true;
		}
		return false;
	}


}
