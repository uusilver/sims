package com.xwtech.mss.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xwtech.mss.json.bean.CityJModel;
import com.xwtech.mss.json.bean.CountryJModel;
import com.xwtech.mss.json.bean.ProvinceJModel;
import com.xwtech.mss.json.bean.Root;
import com.xwtech.mss.json.bean.a_attrJModel;
import com.xwtech.mss.json.bean.dataJModel;
import com.xwtech.mss.json.bean.li_attrJModel;
import com.xwtech.mss.json.bean.stateJModel;

public class LocationController extends MultiActionController{
	
	private static final Log log = LogFactory.getLog(LocationController.class);

	public void queryLocationInfo(HttpServletRequest request, HttpServletResponse response){
		 try {
			 response.setHeader("Content-type","text/html;charset=UTF-8");
			 PrintWriter writer = response.getWriter();
			 writer.write(test4Json());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
	public void saveLocationInfo(HttpServletRequest request, HttpServletResponse response){
		String jsonString = request.getParameter("result");
		Gson gson = new Gson();
		List<Root> root = gson.fromJson(jsonString, new TypeToken<List<Root>>(){}.getType());
		log.info(root.get(0).getText());
		for(Root r:root){
			List<CountryJModel> countryList = r.getChildren();
			
		}
		response.setHeader("Content-type","text/html;charset=UTF-8");
		 PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 writer.write("success");
	}
	
	public static String test4Json(){
		CityJModel city1 = new CityJModel("3-1","南京");
		CityJModel city2 = new CityJModel("3-2","无锡");
		CityJModel city3 = new CityJModel("3-3","镇江");
		CityJModel cityModeArray[] = {city1, city2, city3};
		
		ProvinceJModel p1 = new ProvinceJModel("2-1","江苏省", Arrays.asList(cityModeArray));
		ProvinceJModel pModelArray[] = {p1};
		
		CountryJModel c1 = new CountryJModel("1-1","中国", Arrays.asList(pModelArray));
		CountryJModel cModelArray[] = {c1};
		
		Root root = new Root("0-0","地址信息",Arrays.asList(cModelArray));
		
		Gson gson = new Gson();
		String str = gson.toJson(root);
		log.info("Location Info:"+str);
		return str;
	}
	public static void main(String args[]){
		CityJModel city = new CityJModel("2012","nanjing");
		city.setChildren(new ArrayList<String>());
		
		a_attrJModel am = new a_attrJModel();
		am.setHref("#");
		am.setId("2012_icon");
		
		li_attrJModel li = new li_attrJModel();
		li.setId("2012");
		
		stateJModel sm = new stateJModel();
		sm.setLoaded("true");
		sm.setOpened("false");
		sm.setSelected("false");
		sm.setDisabled("false");
		
		dataJModel data = new dataJModel();
		data.setContent("");
		
		city.setA_attr(am);
		city.setLi_attr(li);
		city.setState(sm);
		city.setData(false);
		city.setType("default");
		city.setIcon("true");
		System.out.println(new Gson().toJson(city));
		
	}
	
}
