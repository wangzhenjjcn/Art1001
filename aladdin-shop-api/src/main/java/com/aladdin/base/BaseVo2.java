package com.aladdin.base;

import java.util.List;

public class BaseVo2 extends BaseVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3004859769605194667L;
	
/*	public BaseVo2(ErrorEnum errorEnum){
		super(errorEnum);
	}*/
	
/*	public BaseVo2(){
	}*/
	
	private List<?> data;

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}
	
	
	
}
