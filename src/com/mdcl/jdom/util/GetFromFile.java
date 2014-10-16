/*
 * �������� 2004-3-23
 *
 * ���������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
package com.mdcl.jdom.util;

import java.util.PropertyResourceBundle;

/**
 * ���.properties�ļ���Ϣ.����hashtable����!
 * 
 * @author Fishman
 */
public class GetFromFile {


	private static String DZCGHOST;
	
	private static String OAHOST;

	private static String DZCGPORT;

	private static String OAPORT;
	
	private static GetFromFile _instance;

	private static String configFilePath = "dzcgConfig";

	private GetFromFile() {

	}

	public static synchronized GetFromFile getInstance() {
		if (_instance == null) {
			_instance = new GetFromFile();
			try {
				PropertyResourceBundle configBundle = (PropertyResourceBundle) PropertyResourceBundle
						.getBundle(configFilePath);
				DZCGHOST = configBundle.getString("DZCGHOST");
				DZCGPORT = configBundle.getString("DZCGPORT");
				
				setDZCGHOST(DZCGHOST);
				setDZCGPORT(DZCGPORT);
//				OAHOST = configBundle.getString("OAHOST");
//				OAPORT = configBundle.getString("OAPORT");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return _instance;
	}

	public static String getDZCGHOST() {
		return DZCGHOST;
	}

	public static void setDZCGHOST(String dzcghost) {
		DZCGHOST = dzcghost;
	}

	public static String getDZCGPORT() {
		return DZCGPORT;
	}

	public static void setDZCGPORT(String dzcgport) {
		DZCGPORT = dzcgport;
	}

	public static String getOAHOST() {
		return OAHOST;
	}

	public static void setOAHOST(String oahost) {
		OAHOST = oahost;
	}

	public static String getOAPORT() {
		return OAPORT;
	}

	public static void setOAPORT(String oaport) {
		OAPORT = oaport;
	}

	public static void main(String[] args){
		GetFromFile instance = GetFromFile.getInstance();
		
		System.out.println("Host :" + instance.getDZCGHOST());
		System.out.println("Port :" + instance.getDZCGPORT());
	}
}