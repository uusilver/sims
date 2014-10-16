package com.xwtech.mss.pub.spring.scheduling;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import com.xwtech.dms.bo.DmsTimeLimitBO;
//import com.xwtech.dms.pub.po.DmsTimeLimit;

public class ScheduleInfoManager {
	
	private static final Log log = LogFactory.getLog(ScheduleInfoManager.class);
	
//	private DmsTimeLimitBO dmsTimeLimitBO; 
	
	private ScheduleInfo scheduleInfo; 

//	public void setDmsTimeLimitBO(DmsTimeLimitBO dmsTimeLimitBO) {
//		this.dmsTimeLimitBO = dmsTimeLimitBO;
//	}

	public void setScheduleInfo(ScheduleInfo scheduleInfo) {
		this.scheduleInfo = scheduleInfo;
	}

	/**
	 * 查询关帐时间
	 * @return
	 */
//	public ScheduleInfo getCronExpressionFromDB (String timeType){
//		log.info("查询关帐时间");
//		String time = "";
//		List timeList = dmsTimeLimitBO.queryTimeLimit(timeType);
//		if(timeList!=null){
//			time = ((DmsTimeLimit)timeList.get(0)).getTime();
//			scheduleInfo.setCronExpression("0 0 "+time.trim()+" * * ?");
//		}
//		return scheduleInfo;
//	}
}
