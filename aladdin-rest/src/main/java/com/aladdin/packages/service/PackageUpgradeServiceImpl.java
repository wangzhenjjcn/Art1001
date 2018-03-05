package com.aladdin.packages.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladdin.packages.PackagesContants;
import com.aladdin.packages.entity.PackageUpgrade;
import com.aladdin.packages.mapper.PackageUpgradeMapper;

@Service
public class PackageUpgradeServiceImpl implements PackageUpgradeService {
	
	@Autowired
	private PackageUpgradeMapper packageUpgradeMapper;

	@Override
	public List<PackageUpgrade> selectByCondition(PackageUpgrade packageUpgrade) {
		// TODO Auto-generated method stub
		
		if(packageUpgrade==null){
			packageUpgrade=new PackageUpgrade();
		}
		packageUpgrade.setStatus(PackagesContants.status_ok);
		
		List<PackageUpgrade> list=packageUpgradeMapper.select(packageUpgrade);
		
		return list;
	}

}
