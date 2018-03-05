package com.aladdin.render.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "computer_info")
public class ComputerInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;

	private String ip;

	private Integer cpuNum;// cpu使用量

	private Double cpuUser;
	
	private Double cpuSys;
	
	private Double cpuIdle;
	
	private Double cpuCombined;
	
	private Double cpuWait;
	
	private Long memoryTotal;
	
	private Double memoryUsedRate;
	
	private Double memoryFreeRate;

	private Integer pnum;

	@Column(name = "start_time")
	private Date startTime;

	@Column(name = "last_beat_time")
	private Date lastBeatTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPnum() {
		return pnum;
	}

	public void setPnum(Integer pnum) {
		this.pnum = pnum;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

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


	public Long getMemoryTotal() {
		return memoryTotal;
	}

	public void setMemoryTotal(Long memoryTotal) {
		this.memoryTotal = memoryTotal;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getLastBeatTime() {
		return lastBeatTime;
	}

	public void setLastBeatTime(Date lastBeatTime) {
		this.lastBeatTime = lastBeatTime;
	}

}
