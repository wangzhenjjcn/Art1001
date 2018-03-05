package com.aladdin.series.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.aladdin.base.ErrorEnum;
import com.aladdin.base.Pager;
import com.aladdin.packages.entity.Packages;
import com.aladdin.packages.mapper.PackagesMapper;
import com.aladdin.series.SeriesConstant;
import com.aladdin.series.entity.Series;
import com.aladdin.series.mapper.SeriesMapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class SeriesServiceImpl implements SeriesService {
	
	@Autowired
	private SeriesMapper seriesMapper;
	
	@Autowired
	private PackagesMapper packagesMapper;

	@Override
	public Pager<Series> find(Map<String, Object> map, Integer pageIndex,Integer pageSize) {

		if( pageIndex==null){
			pageIndex=1;
		}
		if(pageSize==null){
			pageSize=20;
		}

		String name=(String)map.get("name");
		String createBy=(String)map.get("createBy");
		
		Series series=new Series();
		series.setStatus(SeriesConstant.stat_ok);
		series.setLatest(SeriesConstant.latest_ok);

		if(StringUtils.isEmpty(createBy)==false){
			series.setCreateBy(createBy);
		}
		if(StringUtils.isEmpty(name)==false){
			series.setName(name);
		}
		
		int count=count(series);
		List<Series> list=new ArrayList<Series>();
		if(count != 0 ){
			list=seriesMapper.selectListByCondition(series, (pageIndex-1)*pageSize, pageSize);
		}
		
		Pager<Series> pager=new Pager<Series>(pageIndex,pageSize,count,list);
		
		pager.setErrorEnum(ErrorEnum.SUCCESS);
		
		return pager;

	}
	
	public Integer count(Series series) {
		return seriesMapper.selectCountByCondition(series);
	}
	
	/**
	 * 添加规则
	 */
	@Override
	public Integer create(Series series){
		
		int res=0;
		String name=series.getName();
		String uuid=series.getId();
		if(StringUtils.isEmpty(uuid)){
			uuid=com.aladdin.utils.StringUtils.getUUID();
			series.setId(uuid);
		}
		series.setCreateTime(new Date());
		
		if(StringUtils.isEmpty(name)==false){
			
			Series ods=new Series();
			//ods.setStatus(SeriesConstant.stat_ok);
			ods.setName(name);
			ods.setLatest(SeriesConstant.latest_ok);
			
			Series oldseries=seriesMapper.selectOne(ods);
			if(oldseries!=null){
				series.setVersion(oldseries.getVersion()+1);
				
				//修改旧的通用规则信息
				Series oldser=new Series();
				oldser.setId(oldseries.getId());
				oldser.setLatest(SeriesConstant.latest_no);
				seriesMapper.updateByPrimaryKeySelective(oldser);
				
				//修改套餐中的通用规则引用
				Example example=new Example(Packages.class);
				example.createCriteria().andEqualTo("series", oldseries.getId());
				
				Packages packages=new Packages();
				packages.setSeries(uuid);
				
				packagesMapper.updateByExampleSelective(packages, example);
			}
			
			res=seriesMapper.insertSelective(series);
		}
		
		return res;
	}



	@Override
	public List<Series> find(Map<String, Object> map){
		Example example=new Example(Series.class);
		Criteria criteria=example.createCriteria();
		
		criteria.andEqualTo("status",SeriesConstant.stat_ok );
		criteria.andEqualTo("latest",SeriesConstant.latest_ok);	
		
		if(map!=null){
			String id=(String)map.get("id");
			String name=(String)map.get("name");
			String createBy=(String)map.get("createBy");
			

			if(StringUtils.isEmpty(id)==false){
				criteria.andEqualTo("id", id);
			}
			if(StringUtils.isEmpty(createBy)==false){
				criteria.andEqualTo("createBy", createBy);
			}
			if(StringUtils.isEmpty(name)==false){
				criteria.andLike("name", "%"+name+"%");
			}			
		}
		
		return seriesMapper.selectByExample(example);
		
	}

	@Override
	public Series find(String id) {
		return seriesMapper.selectByPrimaryKey(id);
	}

	@Override
	public Integer remove(String id) {
		Series series=new Series();
		series.setId(id);
		series.setStatus(SeriesConstant.stat_no);
		return seriesMapper.updateByPrimaryKeySelective(series);
	}

}
