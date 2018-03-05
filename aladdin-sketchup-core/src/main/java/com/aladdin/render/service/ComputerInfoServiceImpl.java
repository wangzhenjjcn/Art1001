package com.aladdin.render.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladdin.render.entity.ComputerInfo;
import com.aladdin.render.mapper.ComputerInfoMapper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class ComputerInfoServiceImpl implements ComputerInfoService {

	@Autowired
	private ComputerInfoMapper computerInfoMapper; 	
	
	@Override
	public int create(ComputerInfo ci) {
		
		if(ci!=null&&ci.getIp()!=null){
			if(ci.getStartTime()==null||ci.getLastBeatTime()==null){
				Date date = new Date();
				ci.setStartTime(date);
				ci.setLastBeatTime(date);
			}
		  return computerInfoMapper.insertSelective(ci);
		}
		return 0;
	}

	@Override
	public ComputerInfo findByIp(String ip) {
		
		ComputerInfo info = null;
		Example ex = new Example(ComputerInfo.class);
		Criteria cri = ex.createCriteria();
		if(ip!=null){
			cri.andEqualTo("ip",ip);
			ex.setOrderByClause("last_beat_time desc");
			List<ComputerInfo> infos = computerInfoMapper.selectByExample(ex);
			if(infos!=null&&infos.size()>=1){
				info = infos.get(0);
			}
		}
		return info;
	}

	@Override
	public int update(ComputerInfo ci) {
		
		if(ci!=null&&ci.getIp()!=null){
			ComputerInfo info = findByIp(ci.getIp());
			if(info!=null){
				ci.setId(info.getId());
				ci.setLastBeatTime(new Date());
				return computerInfoMapper.updateByPrimaryKeySelective(ci);
			}
		}
		return 0;
	}
	
}
