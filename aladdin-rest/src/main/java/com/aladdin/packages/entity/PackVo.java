package com.aladdin.packages.entity;

import java.util.List;
import java.util.Map;

import com.aladdin.base.BaseVo;
import com.aladdin.series.entity.Series;

public class PackVo extends BaseVo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -593789019406760755L;

	private Packages packages;
	
	private List<Series> serieslist;
	
	private Map<String, String> zoneMap;

	public Packages getPackages() {
		return packages;
	}

	public void setPackages(Packages packages) {
		this.packages = packages;
	}

	public List<Series> getSerieslist() {
		return serieslist;
	}

	public void setSerieslist(List<Series> serieslist) {
		this.serieslist = serieslist;
	}

	public Map<String, String> getZoneMap() {
		return zoneMap;
	}

	public void setZoneMap(Map<String, String> zoneMap) {
		this.zoneMap = zoneMap;
	}
	
	
	
}
