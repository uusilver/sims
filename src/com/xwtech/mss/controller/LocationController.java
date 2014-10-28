package com.xwtech.mss.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xwtech.mss.bo.business.CityBO;
import com.xwtech.mss.bo.business.CountryBO;
import com.xwtech.mss.bo.business.ProvinceBO;
import com.xwtech.mss.bo.system.operator.OperLogBO;
import com.xwtech.mss.json.bean.CityJModel;
import com.xwtech.mss.json.bean.CountryJModel;
import com.xwtech.mss.json.bean.ProvinceJModel;
import com.xwtech.mss.json.bean.Root;
import com.xwtech.mss.json.bean.a_attrJModel;
import com.xwtech.mss.json.bean.dataJModel;
import com.xwtech.mss.json.bean.li_attrJModel;
import com.xwtech.mss.json.bean.stateJModel;
import com.xwtech.mss.pub.po.City;
import com.xwtech.mss.pub.po.Country;
import com.xwtech.mss.pub.po.Province;

public class LocationController extends MultiActionController{
	
	private static final Log log = LogFactory.getLog(LocationController.class);
	
	
	/**

     * 注入回滚事务

     */
	private final static String ROOT_PREFIX="0";
	private final static String COUNTRY_PREFIX="1";
	private final static String PROVINCE_PREFIX="2";
	private final static String CITY_PREFIX="3";

    private TransactionTemplate transTemplate;

    

    private CountryBO countryBO;

    

    private ProvinceBO provinceBO;

    

    private CityBO cityBO;
    
    private OperLogBO operLogBO;
    
    

    

    public void setOperLogBO(OperLogBO operLogBO) {
		this.operLogBO = operLogBO;
	}

	public void setTransTemplate(TransactionTemplate transTemplate) {

            this.transTemplate = transTemplate;

    }

    public void setCountryBO(CountryBO countryBO) {

            this.countryBO = countryBO;

    }

    public void setProvinceBO(ProvinceBO provinceBO) {

            this.provinceBO = provinceBO;

    }

    public void setCityBO(CityBO cityBO) {

            this.cityBO = cityBO;

    }

	public void queryLocationInfo(HttpServletRequest request, HttpServletResponse response){
		 try {
			 response.setHeader("Content-type","text/html;charset=UTF-8");
			 PrintWriter writer = response.getWriter();
			 writer.write(queryLocationFromTable());
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
			//start to load country info
			for(CountryJModel c:countryList){
				Country perCountry = new Country();
				//id like = "1-1", the first is the prefix, the second number which is after "-" is the real id
				//same for below
				perCountry.setCountryid(Integer.valueOf(c.getId().split("-")[1]));
				perCountry.setCountryname(c.getText());
				perCountry.setStatus("A");
				//TODO: Save into database
				
				
				List<ProvinceJModel> provinceList = c.getChildren();
				//start to load province info
				for(ProvinceJModel p:provinceList){
					Province perProvince = new Province();
					perProvince.setCountryid(Integer.valueOf(c.getId().split("-")[1]));
					perProvince.setProvinceid(Integer.valueOf(p.getId().split("-")[1]));
					perProvince.setProvincename(p.getText());
					perProvince.setStatus("A");
					//TODO: Save into database
					
					
					List<CityJModel> cityList = p.getChildren();
					//start to load city info
					for(CityJModel city:cityList){
						City perCity = new City();
						perCity.setProvinceid(Integer.valueOf(p.getId().split("-")[1]));
						perCity.setCityid(Integer.valueOf(city.getId().split("-")[1]));
						perCity.setCityname(city.getText());
						perCity.setStatus("A");
						//TODO: Save into database
					}
				}
				
			}
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
	
	private String queryLocationFromTable(){
		List<Country> countryList = countryBO.queryAllCountries();
		List<Province> provinceList = provinceBO.queryAllProvinces();
		List<City> cityList = cityBO.queryAllCitys();
		
		//init object for GSON loading
		Root root = new Root();
		root.setId(ROOT_PREFIX+"-0");
		root.setText("地理信息");
		/////////////////////////////////////////////////////////////////
		List<CountryJModel> countryListJ = new ArrayList<CountryJModel>();
		
		
		
		for(Country country:countryList){
			CountryJModel countryJ = new CountryJModel();
			countryJ.setId(COUNTRY_PREFIX+"-"+country.getCountryid());
			countryJ.setText(country.getCountryname());
			List<ProvinceJModel> provinceListJ = new ArrayList<ProvinceJModel>();
				//start to loading province
				for(Province province:provinceList){
					if(province.getCountryid().toString().equalsIgnoreCase(country.getCountryid().toString())){
						ProvinceJModel provinceJ = new ProvinceJModel();
						provinceJ.setId(PROVINCE_PREFIX+"-"+province.getProvinceid());
						provinceJ.setText(province.getProvincename());
						List<CityJModel> cintyListJ = new ArrayList<CityJModel>();
							//start to load city
							for(City city:cityList){
								if(city.getProvinceid().toString().equalsIgnoreCase(province.getProvinceid().toString())){
									CityJModel cityJ = new CityJModel();
									cityJ.setId(CITY_PREFIX+"-"+city.getCityid());
									cityJ.setText(city.getCityname());
									cintyListJ.add(cityJ);
								}
							}
							
						provinceJ.setChildren(cintyListJ);
						provinceListJ.add(provinceJ);	
					}
				}
			countryJ.setChildren(provinceListJ);
			countryListJ.add(countryJ);
			//init provice Model List
			root.setChildren(countryListJ);
		}
		Gson gson = new Gson();
		String result = gson.toJson(root);
		log.info(result);
		return result;
	}
	
	/**
	 * 根据countryId查询省
	 * @param request
	 * @param response
	 */
	public void queryProvinceByCountryId(HttpServletRequest request, HttpServletResponse response){
		 try {
			 response.setHeader("Content-type","text/html;charset=UTF-8");
			 PrintWriter writer = response.getWriter();
			 String countryId = request.getParameter("countryId");
			 List<Province> provinceList = provinceBO.queryProvinceByCountryId(countryId);
			 String result = null;
			 if(provinceList.size()>0){
				 Gson gson = new Gson();
				 result = gson.toJson(provinceList);
			 }else{
				 result = "error";
			 }
			 
			 writer.write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	} 
	
	/**
	 * 根据provinceId查询城市
	 * @param request
	 * @param response
	 */
	public void queryCityByProvinceId(HttpServletRequest request, HttpServletResponse response){
		 try {
			 response.setHeader("Content-type","text/html;charset=UTF-8");
			 PrintWriter writer = response.getWriter();
			 String provinceId = request.getParameter("provinceId");
			 List<City> cityList = cityBO.queryCityByProvinceId(provinceId);
			 String result = null;
			 if(cityList.size()>0){
				 Gson gson = new Gson();
				 result = gson.toJson(cityList);
			 }else{
				 result = "error";
			 }
			 
			 writer.write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	} 
	

	/**
	 * 查询所有国家信息
	 * @param request
	 * @param response
	 */
	public void queryAllCountries(HttpServletRequest request, HttpServletResponse response){
		 try {
			 response.setHeader("Content-type","text/html;charset=UTF-8");
			 PrintWriter writer = response.getWriter();
			 List<Country> countryList = countryBO.queryAllCountries();
			 String result = null;
			 if(countryList.size()>0){
				 Gson gson = new Gson();
				 result = gson.toJson(countryList);
			 }else{
				 result = "error";
			 }
			 
			 writer.write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
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
