package com.xwtech.mss.pub.spring.scheduling;

import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.springframework.scheduling.quartz.CronTriggerBean;


/**
 * @author Administrator
 *
 */
public class ScheduleInfoAction {
	
	private static final Log log = LogFactory.getLog(ScheduleInfoAction.class);
	
	private Scheduler scheduler;
	
	private ScheduleInfoManager scheduleInfoManager;
	
	private StdSchedulerFactory stdSchedulerFactory;

	public void setScheduleInfoManager(ScheduleInfoManager scheduleInfoManager) {
		this.scheduleInfoManager = scheduleInfoManager;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void setStdSchedulerFactory(StdSchedulerFactory stdSchedulerFactory) {
		this.stdSchedulerFactory = stdSchedulerFactory;
	}

	/**
	 * 重新设置计划任务的触发时间
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public void reScheduleJob() throws SchedulerException, ParseException {

//		log.info("重新设置计划任务的触发时间");
//		
//		scheduler = stdSchedulerFactory.getScheduler();
//		
//        // 运行时可通过动态注入的scheduler得到trigger
//        CronTriggerBean trigger = (CronTriggerBean) scheduler.getTrigger("autoCloseAcct", Scheduler.DEFAULT_GROUP);
//        
//        CronTriggerBean trigger_sta = (CronTriggerBean) scheduler.getTrigger("autoCloseStatisticsTask", Scheduler.DEFAULT_GROUP);
//       
//       
//        //从数据库中获取时间格式
//        String dbCronExpression = scheduleInfoManager.getCronExpressionFromDB("C").getCronExpression();
//        
//        String dbCronExpression_sta = scheduleInfoManager.getCronExpressionFromDB("MN").getCronExpression();
//        
//        
//        //获取指定触发器的源时间格式
//        String originConExpression = trigger.getCronExpression();
//        
//        String originConExpression_sta = trigger_sta.getCronExpression();
//        
//        log.debug("originConExpression : "+originConExpression);
//        
//        log.debug("dbCronExpression : "+dbCronExpression);
//        
//        log.debug("originConExpression : "+originConExpression_sta);
//        
//        log.debug("dbCronExpression : "+dbCronExpression_sta);
//        
//	    // 判断从DB中取得的任务时间(dbCronExpression)和现在的quartz线程中的任务时间(originConExpression)是否相等
//	    // 如果相等，则表示用户并没有重新设定数据库中的任务时间，这种情况不需要重新rescheduleJob
//        if(!originConExpression.equalsIgnoreCase(dbCronExpression)&&!dbCronExpression.equals("")){
//        	
//        	//设置更新过后的时间
//            trigger.setCronExpression(dbCronExpression);
//            
//            //重新启动触发器
//            scheduler.rescheduleJob("autoCloseAcct", Scheduler.DEFAULT_GROUP, trigger);
//            
//        	log.info("出帐报表计划任务的触发时间已经更新");
//        }else
//        	log.info("出帐报表计划任务的触发时间未更新");
//        
//        if(!originConExpression_sta.equalsIgnoreCase(dbCronExpression_sta)&&!dbCronExpression_sta.equals("")){
//        	
//        	//设置更新过后的时间
//            trigger_sta.setCronExpression(dbCronExpression_sta);
//            
//            //重新启动触发器
//            scheduler.rescheduleJob("autoCloseStatisticsTask", Scheduler.DEFAULT_GROUP, trigger_sta);
//            
//        	log.info("关闭出帐报表计划任务的触发时间已经更新");
//        }else
//        	log.info("关闭出帐报表计划任务的触发时间未更新");
	}
}
