package com.aladdin.packages.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladdin.base.Pager;
import com.aladdin.packages.PackagesContants;
import com.aladdin.packages.entity.PackageZone;
import com.aladdin.packages.entity.Packages;
import com.aladdin.packages.mapper.PackageZoneMapper;
import com.aladdin.packages.mapper.PackagesMapper;
import com.aladdin.series.entity.Series;
import com.aladdin.series.mapper.SeriesMapper;
import com.aladdin.utils.StringUtils;

@Service
public class PackagesServiceImpl implements PackagesService {
	
	@Autowired
	private PackagesMapper packagesMapper;
	
	@Autowired
	private PackageZoneMapper packageZoneMapper;
	
	@Autowired
	private SeriesMapper seriesMapper;

	@Override
	public boolean insertPackagesInfo(Packages packages, List<PackageZone> zonelist, String userId) {
		// TODO Auto-generated method stub
		boolean res=false;
		
		String id=StringUtils.getUUID();//套餐id
		
		String name=packages.getName();
		String series=packages.getSeries();
		
		if(StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(series)){
			Packages pam=new Packages();
			pam.setName(name);
			pam.setSeries(series);
			pam.setLatest(PackagesContants.latest_ok);
			
			Packages resPck=packagesMapper.selectOne(pam);
			if(resPck!=null){
				Packages pamPck=new Packages();
				pamPck.setId(resPck.getId());
				pamPck.setLatest(PackagesContants.latest_no);
				packagesMapper.updateByPrimaryKeySelective(pamPck);
			}
		}
		
		Integer i=packagesMapper.selectMaxVersionByName(name);
		if(i==null){
			i=0;
		}
		packages.setVersion(i+1);//版本号
		
		packages.setId(id);
		packages.setCreatedBy(userId);
		packages.setModifiedBy(userId);
		Date date=new Date();
		packages.setCreatedDate(date);
		packages.setModifiedDate(date);
		packages.setLatest(PackagesContants.latest_ok);
		packages.setStatus(PackagesContants.status_ok);
		
		if(packagesMapper.insertSelective(packages)==1){//保存套餐
			if(zonelist!=null && zonelist.size()>0){
				//保存套餐区域
				for(PackageZone zone:zonelist){
					if(zone.getZoneId()!=null && zone.getZoneName()!=null && zone.getImg()!=null){
						zone.setId(StringUtils.getUUID());
						zone.setCreatedBy(userId);
						zone.setModifiedBy(userId);
						zone.setCreatedDate(date);
						zone.setModifiedDate(date);
						zone.setPackageId(id);
						zone.setStatus(PackagesContants.status_ok);
						packageZoneMapper.insertSelective(zone);
					}
				}
			}
			res=true;
		}
		
		return res;
	}

	@Override
	public Packages selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		Packages packages=packagesMapper.selectByPrimaryKey(id);
		if(packages!=null){
			if(StringUtils.isNotEmpty(packages.getSeries())){
				Series series=seriesMapper.selectByPrimaryKey(packages.getSeries());
				if(series!=null && StringUtils.isNotEmpty(series.getJson())){
					//packages.setSeriesJson(JSON.toJSONString(series));
					packages.setSeriesJson(series.getJson());
				}
			}
		}
		return packages;
	}

	@Override
	public boolean deletePackagesByKey(Packages packages) {
		// TODO Auto-generated method stub
		boolean res=false;
		if(packages!=null){
			if(StringUtils.isNotEmpty(packages.getId())){
				packages.setModifiedDate(new Date());
				packages.setStatus(PackagesContants.status_no);
				int i=packagesMapper.updateByPrimaryKeySelective(packages);
				if(i==1){
					res=true;
				}
			}
		}
		return res;
		
	}

	@Override
	public List<Packages> selectByCondition(Packages packages, String keyword,Integer minprice,Integer maxprice) {
		// TODO Auto-generated method stub
		if(packages==null){
			packages=new Packages();
		}
		packages.setLatest(PackagesContants.latest_ok);
		packages.setStatus(PackagesContants.status_ok);
		
		List<Packages> list=packagesMapper.selectListByCondition(packages, keyword,null,null,minprice,maxprice);
		
		if(list!=null && list.size()>0){
			for(Packages pac:list){
				if(pac!=null && StringUtils.isNotEmpty(pac.getSeries())){
					Series series=seriesMapper.selectByPrimaryKey(pac.getSeries());
					if(series!=null && StringUtils.isNotEmpty(series.getJson())){
						//packages.setSeriesJson(JSON.toJSONString(series));
						pac.setSeriesJson(series.getJson());
					}
				}
			}
		}
	
		return list;
	}

	@Override
	public Pager<Packages> selectPageListByCondition(Packages packages, String keyword, Integer pageNum,
			Integer pageSize,Integer minprice,Integer maxprice) {
		// TODO Auto-generated method stub
		if(packages==null){
			packages=new Packages();
		}
		packages.setLatest(PackagesContants.latest_ok);
		packages.setStatus(PackagesContants.status_ok);
		
		List<Packages> list=new ArrayList<Packages>();
		Integer count=packagesMapper.selectCountByCondition(packages, keyword,minprice,maxprice);
		if(count==null){
			count=0;
		}
		if(count !=0){
			list=packagesMapper.selectListByCondition(packages, keyword, (pageNum-1)*pageSize, pageSize,minprice,maxprice);
			if(list!=null && list.size()>0){
				for(Packages pac:list){
					if(pac!=null && StringUtils.isNotEmpty(pac.getSeries())){
						Series series=seriesMapper.selectByPrimaryKey(pac.getSeries());
						if(series!=null && StringUtils.isNotEmpty(series.getJson())){
							//packages.setSeriesJson(JSON.toJSONString(series));
							pac.setSeriesJson(series.getJson());
						}
					}
				}
			}
		}
		Pager<Packages> pager=new Pager<>(pageNum, pageSize, count, list);
		
		return pager;
	}

}
