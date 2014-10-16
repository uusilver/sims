package com.xwtech.mss.pub.tools;

import java.io.UnsupportedEncodingException;

public class SplitString {
	String splitStr;

	int splitByte;

	String charsetName;
	
	public SplitString(String str, int bytes,String charsetName) {
		splitStr = str;
		splitByte = bytes;
		this.charsetName = charsetName.toUpperCase();
		System.out.println("The String is:′" + splitStr + "′;SplitBytes=" + splitByte+"; CharsetName="+charsetName);
	}

	public void SplitIt() throws UnsupportedEncodingException { 
		StringBuffer str = new StringBuffer();
		for (int i=0;i<splitByte ;i++ ){
			String s = splitStr.substring(i,i+1);
			if(splitStr.length()<splitByte){
				System.out.println("截断的长度超过被截断字符串！");
				return;
			}
			byte[] bytes = s.getBytes(charsetName);
			if(charsetName.equals("ISO8859-1")){
				str.append(s);
			}else{
				if((i == splitByte-1)&& bytes.length>1){
					break;
				}else if (bytes.length==1){ 
					str.append(s);
				} else { 
					str.append(s);
					splitByte--;
				}
			}
		}
		System.out.println("截取的字符串为：" + str.toString());
	}

	public static void main(String[] args){ 
		SplitString ss1 = new SplitString("我ABC的是FDS",6,"ISO8859-1"); 
		SplitString ss2 = new SplitString("我ABC的是FDS",6,"UTF-8"); 
		SplitString ss3 = new SplitString("我ABC的是FDS",6,"GBK"); 
		SplitString ss4 = new SplitString("我ABC的是FDS",6,"GB2312"); 
		try{
			ss1.SplitIt(); 
			ss2.SplitIt();
			ss3.SplitIt();
			ss4.SplitIt();
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}
}


