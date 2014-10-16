package com.xwtech.mss.pub.timer;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.xwtech.framework.pub.utils.HttpUtils;
import com.xwtech.framework.pub.web.FrameworkApplication;

public class HttpCallQuartzJob extends QuartzJobBean implements StatefulJob
{

	protected static final Logger log = Logger.getLogger(HttpCallQuartzJob.class);

	private int timeout;

	private String url;

	/************
	 * 注射
	 * @param timeOut	超时设置
	 */
	public void setTimeOut(int timeOut)
	{
		this.timeout = timeOut;
	}

	/************
	 * 注射
	 * @param url    需要执行的URL
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}

	/***********************
	 * 执行定时器
	 * @param	  ctx						ctx
	 * @exception JobExecutionException		执行异常s
	 */
	protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException
	{
		//log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+url);
		try
		{
			if (url.indexOf("http:") != 0)
//				url = FrameworkApplication.cringProperties.getUrlRootPath() + url;
			log.info(url);
			HttpUtils.callRPC(url);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		//log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+url);
	}

}
