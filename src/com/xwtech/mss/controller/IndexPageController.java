package com.xwtech.mss.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.xwtech.mss.bo.business.ServerInfoBO;

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
			 List<Object[]> list = this.serverInfoBO.queryServerStatusInfo(cityName);
			 String activeServer = "0";
			 String busyServer = "0";
			 String diableServer = "0";
			 for(Object[] o : list){
				 //if 启用数小于5，全部繁忙
				 //大于5，按8:2来处理
				 //8:2 = busy - free
				 if(String.valueOf(o[1]).equals("启用")){
					 int serverNum = Integer.valueOf(String.valueOf(o[2])).intValue();
					 if(serverNum<5){
						 busyServer = String.valueOf(serverNum);
					 }else{
						 busyServer = String.valueOf(Math.ceil((serverNum*(0.8))));
						 activeServer = String.valueOf(serverNum - Integer.valueOf(busyServer));
					 }
				 }
				 if(String.valueOf(o[1]).equals("备用")) diableServer = String.valueOf(o[2]);
				 
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
			 List<Object[]> list = this.serverInfoBO.queryServerOrgInfo(cityName);
			 Set<Integer> s = new HashSet<Integer>();
			 for(Object[] o:list){
				 s.add(Integer.valueOf(String.valueOf(o[2])));
			 }
			 //GateWay Size 
			 int gateWaySize = s.size();
			 List<String> slist = new ArrayList<String>();
			 
			 for(Integer integer:s){
				 String str = null;
				 for(Object[] o:list){
					 if(integer.intValue()==Integer.valueOf(String.valueOf(o[2])).intValue()){
						 str = String.valueOf(o[1])+"<br/>";
					 }
				 }
				 slist.add(str);
			 }
			 String rs = null;
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
}
