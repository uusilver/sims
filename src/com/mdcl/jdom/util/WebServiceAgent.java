/*
 * �������� 2006-3-15
 *
 * TODO�� �� Java �� ������ʽ �� ����ģ��
 */
package com.mdcl.jdom.util;

import java.net.URL;
import java.util.Vector;

import org.apache.soap.Constants;
import org.apache.soap.Fault;
import org.apache.soap.SOAPException;
import org.apache.soap.rpc.Call;
import org.apache.soap.rpc.Parameter;
import org.apache.soap.rpc.Response;

public class WebServiceAgent {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODOԶ���ɷ������

	}

	//把文字变成中文
	private static String StringDecode(String s) {
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

//	public static String HOST = GetFromFile.getInstance().getDZCGHOST();
//
//	public static String Port = GetFromFile.getInstance().getDZCGPORT();

	public static String doSOAPAction(String ServiceName, String MethodName,
			Vector params) {
		try {
			URL url = null;
			url = new URL("http://" + "10.32.153.51" + ":" + "930"
					+ "/soap/servlet/rpcrouter");
			System.out.println("url:" + url);
			// ����Call����
			Call call = new Call();
			call.setTargetObjectURI(ServiceName);
			call.setMethodName(MethodName);
			call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
			// Vector params = new Vector();
			// params.addElement(new Parameter("name", String.class, name,
			// null));
			call.setParams(params);

			Response resp = null;
			try {
				resp = call.invoke(url, "");
			} catch (SOAPException e) {
				System.out.println("get Excetpion!!");
				System.err.println("Caught SOAPException (" + e.getFaultCode()
						+ "): " + e.getMessage());
				return null;
			}

			// �����
			if (!resp.generatedFault()) {
				Parameter ret = resp.getReturnValue();
				Object value = ret.getValue();
				if(value!=null && ((String)value).equals("OK")){
					System.out.println("return value:" + value);
				}else{
					System.out.println("return value:" + StringDecode((String)value));
				}
//				System.out.println("return value:"+value);
				return value.toString();
			} else {
				Fault fault = resp.getFault();
				System.err.println("Generated fault: ");
				System.out.println(" Fault Code = " + fault.getFaultCode());
				System.out.println(" Fault String = " + fault.getFaultString());
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}