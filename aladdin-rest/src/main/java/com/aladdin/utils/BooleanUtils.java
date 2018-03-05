package com.aladdin.utils;

import java.util.Collection;
import java.util.Map;

public class BooleanUtils {
	public static final  String AND = "&&";
	public static final  String OR = "||";
	/**
	 * @param choice【1.and:当需要验证的参数都为空时返回true】【2.or 当需要验证的参数有1个为空时返回true】
	 * @param args 需要验证的对象
	 */
	public static boolean isEmpty(String choice,Object ...args){
		if(!isEmpty(args)){
			if(AND.equalsIgnoreCase(choice)){
				boolean flag = true;
				for(Object o:args){
					flag = flag&&isEmpty(o);
				}
				return flag;
			}
			if(OR.equalsIgnoreCase(choice)){
				boolean flag = false;
				for(Object o:args){
					flag = flag||isEmpty(o);
					if(flag){break;}
				}
				return flag;
			}
		}
		return false;
	}
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object o){
		if(o==null||"".equals(o.toString().trim())){return true;}
		if(o instanceof Collection){return ((Collection) o).isEmpty();}
		if(o instanceof Map){return ((Map) o).isEmpty();}
		if(o.getClass().isArray()){return ((Object[])o).length==0;}
		return false;
	}
//	public static void main(String[] args) {
//		System.out.println(isEmpty(OR, null,"dd",new String[]{},new ArrayList(),new HashMap()));
//	}
}
