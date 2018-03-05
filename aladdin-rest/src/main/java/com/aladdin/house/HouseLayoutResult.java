package com.aladdin.house;

import java.util.List;

import com.aladdin.base.BaseVo;
import com.aladdin.house.entity.HouseInfo;


public class HouseLayoutResult  extends BaseVo {

	private static final long serialVersionUID = 1L;

	private String query;

	private int count;


	private List<HouseInfo> records;

	private int pageNo;

	private float[] area;
	
	private Integer state;//查询状态值
	
	public float[] getArea() {
		return area;
	}

	public void setArea(float[] area) {
		this.area = area;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<HouseInfo> getRecords() {
		return records;
	}

	public void setRecords(List<HouseInfo> records) {
		this.records = records;
	}


	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public HouseLayoutResult(String query, int count,List<HouseInfo> records, int pageNo,float[] area,Integer state) {
		super();
		this.query = query;
		this.count = count;
		this.records = records;
		this.pageNo = pageNo;
		this.area = area;
		this.state =state;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public HouseLayoutResult() {
		super();
	}

}
