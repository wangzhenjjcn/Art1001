package com.aladdin.render;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aladdin.render.bean.Cpu;
import com.aladdin.render.bean.Memory;
import com.aladdin.render.entity.ComputerInfo;
import com.aladdin.render.service.ComputerInfoService;

@Component
public class Watcher {
	
	@Autowired
	private ComputerInfoService computerInfoService;
	@Value("${agent.netCardName}")
	private String netCardName;

	public int watch() {
		
		String ip = Top.ip(netCardName);
		Cpu cpu = Top.cpu();
		Memory memory = Top.memory();
		
		
		ComputerInfo computerInfo = computerInfoService.findByIp(ip);
		boolean hasData = false;
		int updatednum = 0;
		if(computerInfo==null){
			computerInfo = new ComputerInfo();
			computerInfo.setStartTime(new Date());
		}else{
			hasData=true;
		}
		computerInfo.setIp(ip);
		
		computerInfo.setPnum(Counter.getInstance().getNum());
		
		computerInfo.setCpuCombined(cpu.getCpuCombined());
		computerInfo.setCpuIdle(cpu.getCpuIdle());
		computerInfo.setCpuNum(cpu.getCpuNum());
		computerInfo.setCpuSys(cpu.getCpuSys());
		computerInfo.setCpuUser(cpu.getCpuUser());
		computerInfo.setCpuWait(cpu.getCpuWait());
		
		computerInfo.setMemoryTotal(memory.getMomoryTotal());
		computerInfo.setMemoryUsedRate(memory.getMemoryUsedRate());
		computerInfo.setMemoryFreeRate(memory.getMemoryFreeRate());
		
		computerInfo.setLastBeatTime(new Date());
		
		if(!hasData){
			updatednum = computerInfoService.create(computerInfo);
		}else{
			updatednum = computerInfoService.update(computerInfo);
		}
		if(updatednum==0){
			System.out.println("更新数据库失败！");
		}
		return updatednum;
	}
}
