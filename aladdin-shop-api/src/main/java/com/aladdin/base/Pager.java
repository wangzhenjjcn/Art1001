package com.aladdin.base;

import java.io.Serializable;
import java.util.List;

public class Pager<T> extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer pageIndex;// 页码
	private Integer pageSize;// 每页显示记录数
	private Integer maxPages;// 总页数
	private Integer total;// 总记录数
	private Integer startIndex;
	private List<T> data;

	public Pager(Integer pageIndex, Integer pageSize, Integer total, List<T> data) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.startIndex=(pageIndex-1)*pageSize;
		this.total = total;
		this.maxPages = total == 0 ? 1 : (total + pageSize - 1) / pageSize;
		if(data!=null){
			this.data = data;
		}
	}

	public Pager(Integer pageIndex, Integer pageSize) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	public Pager() {
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		if (pageIndex == null) {
			pageIndex = 1;
		}
		this.pageIndex = pageIndex;
		if(pageSize!=null){
			this.startIndex = (pageIndex-1)*pageSize;
		}
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize == null) {
			pageSize = 20;
		}
		this.pageSize = pageSize;
		if(pageIndex!=null){
			this.startIndex = (pageIndex-1)*pageSize;
		}
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public Integer getMaxPages() {
		return maxPages;
	}

	public void setMaxPages(Integer maxPages) {
		this.maxPages = maxPages;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public void setErrorEnum(ErrorEnum errorEnum) {
		super.code =  errorEnum.getCode();
		super.msg = errorEnum.getMsg();
	}
	
}
