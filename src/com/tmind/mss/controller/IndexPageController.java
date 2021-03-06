package com.tmind.mss.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.tmind.mss.bo.business.ServerInfoBO;

public class IndexPageController extends MultiActionController{
	
	private static final Log log = LogFactory.getLog(MultiActionController.class);
	
	private ServerInfoBO serverInfoBO;

	public void setServerInfoBO(ServerInfoBO serverInfoBO) {
		this.serverInfoBO = serverInfoBO;
	}
	
	public void getServerInfoByCityName(HttpServletRequest request, HttpServletResponse response){
		 try {
			 response.setHeader("Content-type","text/html;charset=UTF-8");
			 PrintWriter writer = response.getWriter();
			 String cityName = request.getParameter("cityName").replaceAll("[\\[\\]]","").replace("\"", "");
			 List<ListOrderedMap> list = this.serverInfoBO.queryServerStatusInfo(cityName);
			 String activeServer = "0";
			 String busyServer = "0";
			 String diableServer = "0";
			 for(ListOrderedMap o : list){
				 //if 启用数小于5，全部繁忙
				 //大于5，按8:2来处理
				 //8:2 = busy - free
				 if(String.valueOf(o.get("status")).equals("启用")){
					 int serverNum = Integer.valueOf(String.valueOf(o.get("num"))).intValue();
					 if(serverNum<5){
						 busyServer = String.valueOf(serverNum);
					 }else{
						 busyServer = String.valueOf((int)Math.ceil((serverNum*(0.8))));
						 activeServer = String.valueOf(serverNum - Integer.valueOf(busyServer));
					 }
				 }
				 if(String.valueOf(o.get("status")).equals("备用")) diableServer = String.valueOf(o.get("num"));
				 
			 }
			 writer.write(activeServer+","+busyServer+","+diableServer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getServerOrgInfoByCityName(HttpServletRequest request, HttpServletResponse response){
		 try {
			 response.setHeader("Content-type","text/html;charset=UTF-8");
			 PrintWriter writer = response.getWriter();
			 String cityName = request.getParameter("cityName").replaceAll("[\\[\\]]","").replace("\"", "");
			 List<ListOrderedMap> list = this.serverInfoBO.queryServerOrgInfo(cityName);
			 Set<Integer> s = new HashSet<Integer>();
			 for(ListOrderedMap o:list){
				 s.add(Integer.valueOf(String.valueOf(o.get("GATEWAYID"))));
			 }
			 //GateWay Size 
			 int gateWaySize = s.size();
			 List<String> slist = new ArrayList<String>();
			 
			 for(Integer integer:s){
				 String str = "";
				 for(ListOrderedMap o:list){
					 if(integer.intValue()==Integer.valueOf(String.valueOf(o.get("GATEWAYID"))).intValue()){
						 str += String.valueOf(o.get("USERNAME"))+"<br/>";
					 }
				 }
				 slist.add(str);
			 }
			 String rs = "";
			 for(String strN:slist){
				 rs += strN+",";
			 }
			 String finalResult = gateWaySize+"@"+rs;
			 writer.write(finalResult);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void queryLog(HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Content-type","text/html;charset=UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			List<ListOrderedMap> list = this.serverInfoBO.queryLog();
			StringBuffer sb = new StringBuffer();
			for(ListOrderedMap o:list){
				sb.append(o.get("LOG")+"<br/>");
			}
			writer.write(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
