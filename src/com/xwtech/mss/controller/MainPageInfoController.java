package com.xwtech.mss.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class MainPageInfoController extends MultiActionController{
	
	private static final Log log = LogFactory.getLog(MainPageInfoController.class);
	
	//Server status querier, return html
	public void queryServerInfo(HttpServletRequest request, HttpServletResponse response){
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.write("Server Info");
		
	}
	
	//Topology Information querier, return html
	public void queryTopologyInfo(HttpServletRequest request, HttpServletResponse response){
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.write("Topo Info");
	}
}
