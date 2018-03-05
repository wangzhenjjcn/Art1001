package com.aladdin.render.bean;

public class Cpu {
	
	private Integer cpuNum = 1;// cpu使用量

	private Double cpuUser = 0D;

	private Double cpuSys = 0D;

	private Double cpuIdle = 0D;

	private Double cpuCombined = 0D;

	private Double cpuWait = 0D;

	public Integer getCpuNum() {
		return cpuNum;
	}

	public void setCpuNum(Integer cpuNum) {
		this.cpuNum = cpuNum;
	}

	public Double getCpuUser() {
		return cpuUser;
	}

	public void setCpuUser(Double cpuUser) {
		this.cpuUser = cpuUser;
	}

	public Double getCpuSys() {
		return cpuSys;
	}

	public void setCpuSys(Double cpuSys) {
		this.cpuSys = cpuSys;
	}

	public Double getCpuIdle() {
		return cpuIdle;
	}

	public void setCpuIdle(Double cpuIdle) {
		this.cpuIdle = cpuIdle;
	}

	public Double getCpuCombined() {
		return cpuCombined;
	}

	public void setCpuCombined(Double cpuCombined) {
		this.cpuCombined = cpuCombined;
	}

	public Double getCpuWait() {
		return cpuWait;
	}

	public void setCpuWait(Double cpuWait) {
		this.cpuWait = cpuWait;
	}

	@Override
	public String toString() {
		return "cpuNum:"+cpuNum+
				";cpuUser:"+cpuUser
				+";cpuSys:"+cpuSys
				+";cpuIdle:"+cpuIdle
				+";cpuCombined:"+cpuCombined
				+";cpuWait:"+cpuWait;
	}
	
}
