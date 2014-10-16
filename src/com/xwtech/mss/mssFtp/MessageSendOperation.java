package com.xwtech.mss.mssFtp;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.huawei.api.SMEntry;
import com.xwtech.framework.pub.web.FrameworkApplication;
//import com.xwtech.mss.bo.MpmsOrdersInfoBO;
//import com.xwtech.mss.pub.po.MpmsOrderRemindMsg;


public class MessageSendOperation implements Runnable{
	
	static String dbName = "10.33.13.246";

	private static final Log log = LogFactory.getLog(MessageSendOperation.class);

	static String name = "Customsms";
	
	static String pass = "sqlmsde@infoxeie2000";

	private String destAddr;

	private String content;

	private int returnCode;

	//短信代码
	private String serviceID = FrameworkApplication.frameworkProperties.getMsgServiceID();

	//企业代码
	private String sourceAddr = FrameworkApplication.frameworkProperties.getMsgSourceAddr();
	
	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public MessageSendOperation(String destAddr, String content){
		this.content = content==null?"":content.trim();
		this.destAddr = destAddr==null?"":destAddr.trim();
	}
	
	public void run() {
		int count = 0;
		try {
			try {
				/*
				 * System.out.println("-------------");
				 * SMEntry.init(RequestNameConstants.dbName,
				 * RequestNameConstants.name, RequestNameConstants.pass); Date
				 * atTime = new Date(); String sourceAddr = "07139"; int
				 * needStateReport = 0; String serviceID = "123456"; String feeType =
				 * "01"; String feeCode = "0"; int count =
				 * SMEntry.submitShortMessage(atTime, sourceAddr, destAddr, content,
				 * needStateReport, serviceID, feeType, feeCode);
				 * System.out.println("返回码为： " + count);
				 */
				log.info("<--------发送短信给用户:" + destAddr + ",内容为：" + content + "，stsrt...");
				SMEntry.init(dbName, name, pass);
				Date atTime = new Date();
//				String sourceAddr = "1068250940000";
				int needStateReport = 0;
//				String serviceID = "106825094";
				String feeType = "01";
				String feeCode = "0";
				count = SMEntry.submitShortMessage(atTime, sourceAddr,
						destAddr, content, needStateReport, serviceID, feeType,
						feeCode);
//				count = 1;
				if(count==0){
					log.error("短信发送失败，返回码为： " + count + "，end...-------->");
				}else{
					log.info("短信发送成功，返回码为： " + count + "，end...-------->");
				}
			} catch (Exception e) {
				log.error("短信发送异常，返回码为： " + e + "，end...-------->");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		setReturnCode(count);
	}
	
	public static void main(String[] args) throws IOException{
    	try{
    		for(int i=0; i<2;i++){
			  Thread t1=new Thread(new MessageSendOperation("13655175218", String.valueOf(i)));
			  t1.start();
			  Thread t2=new Thread(new MessageSendOperation("13914731267", String.valueOf(i+5)));
			  t2.start();
			  t1.run();
    		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}