package com.xwtech.mss.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class LocationController extends MultiActionController{
	
	private static final Log log = LogFactory.getLog(LocationController.class);

	public void queryLocationInfo(HttpServletRequest request, HttpServletResponse response){
		 try {
			 response.setHeader("Content-type","text/html;charset=UTF-8");
			 PrintWriter writer = response.getWriter();
			 writer.write("中国");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
}
