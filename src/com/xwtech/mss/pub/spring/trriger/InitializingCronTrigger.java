package com.xwtech.mss.pub.spring.trriger;

import java.io.Serializable;
import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.quartz.CronTriggerBean;

import com.xwtech.mss.pub.spring.scheduling.ScheduleInfoManager;

/**
 * @author Administrator
 *
 */
public class InitializingCronTrigger extends CronTriggerBean implements Serializable {
	
	private static final Log log = LogFactory.getLog(InitializingCronTrigger.class);

	private ScheduleInfoManager scheduleInfoManager;

	
	/**
	 * 初始化定时任务的触发时间
	 * @param scheduleInfoManager
	 * @throws ParseException
	 */
//    public void initCronTrigger(ScheduleInfoManager scheduleInfoManager) throws ParseException{
//    	
//    	log.info("初始化定时任务的触发时间");
//    	
//        this.scheduleInfoManager = scheduleInfoManager;
//        
//        //查询关帐时间
//        String cronExpression = scheduleInfoManager.getCronExpressionFromDB("C").getCronExpression();
//        
//        //设置关帐时间
//        setCronExpression(cronExpression);
//    }
}
