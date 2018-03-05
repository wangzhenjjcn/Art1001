package com.aladdin.render;

public class Counter {
	//当前执行任务数量
	private volatile int num;
	
	private static Counter watcher = new Counter();
	
	private Counter(){
		
	}
	public synchronized int add(){
		return this.num++;
	}
	
	public synchronized int reduce(){
		if(num>0){
			return this.num--;
		}
		return 0;
	}
	
	public static Counter getInstance(){
		return watcher;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
}
