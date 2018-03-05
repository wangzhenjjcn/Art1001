package com.aladdin.render.bean;

public class Memory {

	private Long momoryTotal = 0L;

	private Double memoryUsedRate = 0D;

	private Double memoryFreeRate = 0D;

	public Long getMomoryTotal() {
		return momoryTotal;
	}

	public void setMomoryTotal(Long momoryTotal) {
		this.momoryTotal = momoryTotal;
	}

	public Double getMemoryUsedRate() {
		return memoryUsedRate;
	}

	public void setMemoryUsedRate(Double memoryUsedRate) {
		this.memoryUsedRate = memoryUsedRate;
	}

	public Double getMemoryFreeRate() {
		return memoryFreeRate;
	}

	public void setMemoryFreeRate(Double memoryFreeRate) {
		this.memoryFreeRate = memoryFreeRate;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "momoryTotal:" + momoryTotal + ";memoryUsedRate:" + memoryUsedRate + ";memoryFreeRate:" + memoryFreeRate;
	}

}
