/*
 * �������� 发送到OA服务器
 */
package com.mdcl.jdom.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import org.apache.soap.rpc.Parameter;

import com.mdcl.jdom.util.*;

public class SendMessageoverWS {

	public String doSoapAction(File file, String staffno){
		StringBuffer retXML = new StringBuffer();
		String ret = "";
		FileInputStream fis = null;
    	DataInputStream in = null;
    	BufferedReader br = null;
    	try{
	    	fis = new FileInputStream(file);
	    	in = new DataInputStream(fis);
	    	br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
	    	String line = "";// 读取第一行
	        while((line = br.readLine()) != null) {// 如果 line 为空说明读完了
	        	retXML.append(line.replaceFirst("UTF-8", "GB2312")+"\n");// 添加换行符
	        }
	    	br.close();
	    	in.close();
	    	fis.close();
    	}catch(IOException ioe){
    		ioe.printStackTrace();
    	}finally{
    		try{
		    	br.close();
    			br = null;
		    	in.close();
		    	in = null;
		    	fis.close();
		    	fis = null;
	    	}catch(IOException ioe){
	    		ioe.printStackTrace();
	    	}
    	}
		
		Vector vector = new Vector();
		vector.addElement(new Parameter("xmlMessage", String.class, retXML.toString(), null));
		vector.addElement(new Parameter("staffno", String.class, staffno, null));
		ret = WebServiceAgent.doSOAPAction("urn:ebuyTrancefer", "doRecive", vector);
		return StringDecode(ret);
	}
	

	//把文字变成中文
	private String StringDecode(String s) {
		if(s!=null && !s.equals("")){
			StringBuffer stringbuffer = new StringBuffer();
				for (int i = 0; i < s.length(); i++) {
					char c = s.charAt(i);
					switch (c) {
						case 43: // '+'
							stringbuffer.append(' ');
							break;
						case 36: // '$'
							try {
								stringbuffer.append((char) Integer.parseInt(s.substring(i + 1, i + 5), 16));
							} catch (NumberFormatException numberformatexception) {
								System.out.println("字符串解码失败！");
								return null;
							}
							i += 4;
							break;
						default:
							stringbuffer.append(c);
							break;
					}
				}
			return stringbuffer.toString();
		}else{
			return "";
		}
	} 

	
	public String sendMessage(){
		String ret = "";
		Vector vector = new Vector();
		vector.addElement(new Parameter("str", String.class, "aaa", null));
		//vector.addElement(new Parameter("str2", String.class, "bbb", null));
		//vector.addElement();
		//String ret = WebServiceAgent.doSOAPAction("urn:HelloWorldService", "sayHelloTo", vector);
		ret = WebServiceAgent.doSOAPAction("urn:ebuyTrancefer", "doRecive", vector);
		if(ret!=null && ret.equals("OK")){
			System.out.println("OA URL:" + StringDecode(ret));
		}else{
			System.out.println("OA RETURN VALUE:" + StringDecode(ret));
		}
		return ret;
	}
}
