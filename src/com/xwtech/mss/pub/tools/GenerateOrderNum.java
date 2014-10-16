package com.xwtech.mss.pub.tools;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xwtech.framework.pub.utils.DateUtils;
import com.xwtech.framework.pub.utils.JdbcUtils;
import com.xwtech.framework.pub.web.FrameworkApplication;

public class GenerateOrderNum {

	private HttpServletRequest request;
	
	public GenerateOrderNum(HttpServletRequest request){
		this.request = request;
	}

	private static String countDate = String.valueOf(FrameworkApplication.getCacheConstantsObjects("countDate"));

	private static String orderCount = String.valueOf(FrameworkApplication.getCacheConstantsObjects("orderCount"));
	
//	private static String  indentCount = String.valueOf(FrameworkApplication.getCacheConstantsObjects("indentCount"));

//	public synchronized static String getUniqueIndentNo() throws Exception{  
//		//根据当前时间设值
//		String curDate = DateUtils.getChar8();
//		if(!curDate.equals(countDate)){
//			countDate = curDate;
//			indentCount = "1";
//		}else{
//			indentCount = String.valueOf(new Long(indentCount).longValue() + 1) ;
//		}
//		//刷新缓存中的值
//		FrameworkApplication.refreshCacheByConstantsName("indentCount", null, indentCount);
//		FrameworkApplication.refreshCacheByConstantsName("countDate", null, countDate);
//		if(indentCount.length()<5){
//			int len = indentCount.length();
//			for(int i=0; i<5-len; i++){
//				indentCount = "0" + indentCount;
//			}
//		}
//		System.out.println(countDate + " indentCount : " + indentCount + "---------------------------");
//		return indentCount;
//	} 
//	
	public synchronized String getUniqueOrderNo() throws Exception{  
		//根据当前时间设值
		String curDate = DateUtils.getChar8();
		if(!curDate.equals(countDate)){
			countDate = curDate;
			orderCount = "1";
		}else{
			orderCount = String.valueOf(new Long(orderCount).longValue() + 1) ;
		}
		//刷新缓存中的值
		FrameworkApplication.refreshCacheByConstantsName("orderCount", null, orderCount);
		FrameworkApplication.refreshCacheByConstantsName("orderCountDate", null, countDate);
		if(orderCount.length()<4){
			int len = orderCount.length();
			for(int i=0; i<4-len; i++){
				orderCount = "0" + orderCount;
			}
		}
//		System.out.println(countDate + " orderCount: " + orderCount + "---------------------------");
		return "9" + orderCount;
	}  
}
