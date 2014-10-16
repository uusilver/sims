package com.xwtech.mss.mssFtp;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessageOperation{
	private static final Log log = LogFactory.getLog(MessageOperation.class);
	
	public int send(String destAddr, String content){
		MessageSendOperation msgSend = new MessageSendOperation(destAddr, content);
		try{
			Thread thread = new Thread(msgSend, "1");
			thread.start();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return msgSend.getReturnCode();
	}

	public static void main(String[] args) throws IOException{
    	try{
    		MessageOperation msg = new MessageOperation();
    		for(int i=0; i<2;i++){
    			log.info("-----------分割线开始："+i+"--------------");
    			msg.send("13914731267", String.valueOf(i));
    			msg.send("13914731267", String.valueOf(i+5));
    			log.info("-----------分割线结束："+i+"--------------");
    		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
