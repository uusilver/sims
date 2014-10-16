package com.xwtech.mss.pub.spring.scheduling;

/**
 * 改类定义了任务的触发时间
 * @author 杨洋
 *
 */
public class ScheduleInfo {
	
	//时间表达式
	private String cronExpression = "";

	//获取时间表达式
	public String getCronExpression() {
		return cronExpression;
	}

	//设置时间表达式
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	

}
