package com.tmind.mss.controller;

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
import com.tmind.mss.bo.business.CityBO;
import com.tmind.mss.bo.business.CountryBO;
import com.tmind.mss.bo.business.ProvinceBO;
import com.tmind.mss.bo.business.RegionBO;
import com.tmind.mss.bo.system.operator.OperLogBO;
import com.tmind.mss.json.bean.CityJModel;
import com.tmind.mss.json.bean.CountryJModel;
import com.tmind.mss.json.bean.ProvinceJModel;
import com.tmind.mss.json.bean.Root;
import com.tmind.mss.json.bean.a_attrJModel;
import com.tmind.mss.json.bean.dataJModel;
import com.tmind.mss.json.bean.li_attrJModel;
import com.tmind.mss.json.bean.stateJModel;
import com.tmind.mss.pub.po.City;
import com.tmind.mss.pub.po.Country;
import com.tmind.mss.pub.po.Province;
import com.tmind.mss.pub.po.Region;

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
    
    private RegionBO regionBO;
    
    private OperLogBO operLogBO;
    
    

    

    public void setRegionBO(RegionBO regionBO) {
		this.regionBO = regionBO;
	}

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
	
	//delete
	public void deleteLocationInfo(HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Content-type","text/html;charset=UTF-8");
		 PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String id = request.getParameter("param");
		//String nd = id.substring(2, id.length()-2);
		String nd = id.replaceAll("[\\[\\]]","").replace("\"", "");
		if(nd.startsWith("j")){ //new node
			 writer.write("success");
		}else{
			String prefix = nd.split("-")[0];
			String realId = nd.split("-")[1];
			
			//3-city, 2-province, 1-country
			if(prefix.equalsIgnoreCase("3")){
				List<City> cityList = cityBO.queryAllCitys();
				for(City c:cityList){
					if(String.valueOf(c.getCityid()).equalsIgnoreCase(realId)){
						c.setStatus("D");
						cityBO.saveOrUpdate(c);
					}
				}
			}else if(prefix.equalsIgnoreCase("2")){
				List<Province> provinceList = provinceBO.queryAllProvinces();
				for(Province p:provinceList){
					if(String.valueOf(p.getProvinceid()).equalsIgnoreCase(realId)){
						p.setStatus("D");
						provinceBO.saveOrUpdate(p);
						List<City> cityList = cityBO.queryCityByProvinceId(String.valueOf(p.getProvinceid()));
						for(City c:cityList){
							c.setStatus("D");
							cityBO.saveOrUpdate(c);
						}
					}
				}
				
			}else if(prefix.equalsIgnoreCase("1")){
				List<Country> countryList = countryBO.queryAllCountries();
				for(Country c:countryList){
					if(String.valueOf(c.getCountryid()).equalsIgnoreCase(realId)){
						c.setStatus("D");
						countryBO.saveOrUpdate(c);
						List<Province> provinceList = provinceBO.queryProvinceByCountryId(String.valueOf(c.getCountryid()));
						for(Province p:provinceList){
							p.setStatus("D");
							provinceBO.saveOrUpdate(p);
							List<City> cityList = cityBO.queryCityByProvinceId(String.valueOf(p.getProvinceid()));
							for(City city:cityList){
								city.setStatus("D");
								cityBO.saveOrUpdate(city);
							}
						}
					}
				}
			}
			
			 writer.write("success");
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
				String coutryId = null;
				Country perCountry = new Country();
				//if starts with j means it is a new country
				if(c.getId().startsWith("j")){
					perCountry.setCountryname(c.getText());
					perCountry.setStatus("A");
					countryBO.saveOrUpdate(perCountry);
					List<Country> countryListTemp = countryBO.queryAllCountries();
					for(Country cTemp:countryListTemp){
						if(cTemp.getCountryname().equalsIgnoreCase(c.getText())){
							coutryId = String.valueOf(cTemp.getCountryid());
						}
					}
					
				}else{				//id like = "1-1", the first is the prefix, the second number which is after "-" is the real id
				//same for below
					perCountry.setCountryid(Integer.valueOf(c.getId().split("-")[1]));
					perCountry.setCountryname(c.getText());
					perCountry.setStatus("A");
					//TODO: Save into database
					countryBO.saveOrUpdate(perCountry);
					coutryId = c.getId().split("-")[1];
				}
				
				List<ProvinceJModel> provinceList = c.getChildren();
				//start to load province info
				for(ProvinceJModel p:provinceList){
					Province perProvince = new Province();
					String provinceId = null;
					if(p.getId().startsWith("j")){
						perProvince.setCountryid(Integer.valueOf(coutryId));
						perProvince.setProvincename(p.getText());
						perProvince.setStatus("A");
						provinceBO.saveOrUpdate(perProvince);
						List<Province> proTempList = provinceBO.queryAllProvinces();
						for(Province pTemp:proTempList){
							if(pTemp.getProvincename().equalsIgnoreCase(p.getText())){
								provinceId = String.valueOf(pTemp.getProvinceid());
							}
						}
						
					}else{
						perProvince.setCountryid(Integer.valueOf(coutryId));
						perProvince.setProvinceid(Integer.valueOf(p.getId().split("-")[1]));
						perProvince.setProvincename(p.getText());
						perProvince.setStatus("A");
						//TODO: Save into database
						provinceBO.saveOrUpdate(perProvince);
						provinceId = p.getId().split("-")[1];
					}
					
					List<CityJModel> cityList = p.getChildren();
					//start to load city info
					for(CityJModel city:cityList){
						City perCity = new City();
						if(city.getId().startsWith("j")){
							perCity.setProvinceid(Integer.valueOf(provinceId));
							perCity.setCityname(city.getText());
							perCity.setStatus("A");
							cityBO.saveOrUpdate(perCity);
						}else{
							perCity.setProvinceid(Integer.valueOf(provinceId));
							perCity.setCityid(Integer.valueOf(city.getId().split("-")[1]));
							perCity.setCityname(city.getText());
							perCity.setStatus("A");
							//TODO: Save into database
							cityBO.saveOrUpdate(perCity);
						}
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
			 Gson gson = new Gson();
			 if(provinceList!=null&&provinceList.size()>0){
				 result = gson.toJson(provinceList);
			 }else{
				 result = gson.toJson(null);
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
			 Gson gson = new Gson();
			 if(cityList!=null&&cityList.size()>0){
				 result = gson.toJson(cityList);
			 }else{
				 result = gson.toJson(null);
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
			 Gson gson = new Gson();
			 if(countryList!=null&&countryList.size()>0){
				 result = gson.toJson(countryList);
			 }else{
				 result = gson.toJson(null);
			 }
			 
			 writer.write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	} 
	
	//get Region list
	public void queryAllRegion(HttpServletRequest request, HttpServletResponse response){
		Gson gson = new Gson();
		List<Region> regionList = regionBO.queryAllRegions();
		response.setHeader("Content-type","text/html;charset=UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.write(gson.toJson(regionList));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//Update
	public void updateRegion(HttpServletRequest request, HttpServletResponse response){
		String proName = request.getParameter("obj");
		String nd = proName.replaceAll("[\\[\\]]","").replace("\"", "");
		String id = nd.split(":")[0];
		String name = nd.split(":")[1];
		List<Region> regionList = regionBO.queryAllRegions();
		String result = null;
		if(dulName(regionList,name)){
			result = "error1";
		}else{
			for(Region r:regionList){
				if(String.valueOf(r.getRegionid()).equalsIgnoreCase(id)){
					r.setRegionname(name);
					regionBO.saveOrUpdateRegion(r);
					result = "success";
				}
			}
		}
		
		response.setHeader("Content-type","text/html;charset=UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//save
		public void saveRegion(HttpServletRequest request, HttpServletResponse response){
			String proName = request.getParameter("obj");
			String nd = proName.replaceAll("[\\[\\]]","").replace("\"", "");
			List<Region> regionList = regionBO.queryAllRegions();
			String result = null;
			if(dulName(regionList,nd)){
				result = "error1";
			}else{
				Region region = new Region();
				region.setRegionname(nd);
				region.setStatus("A");
				regionBO.saveOrUpdateRegion(region);
				result = "success";
			}
			
			response.setHeader("Content-type","text/html;charset=UTF-8");
			try {
				PrintWriter writer = response.getWriter();
				writer.write(result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	//delete
		public void deleteRegion(HttpServletRequest request, HttpServletResponse response){
			String proName = request.getParameter("obj");
			String nd = proName.replaceAll("[\\[\\]]","").replace("\"", "");
			List<Region> regionList = regionBO.queryAllRegions();
			for(Region r:regionList){
				if(r.getRegionname().equals(nd)){
					r.setStatus("D");
					regionBO.saveOrUpdateRegion(r);
					break;
				}
			}
			response.setHeader("Content-type","text/html;charset=UTF-8");
			try {
				PrintWriter writer = response.getWriter();
				writer.write("success");
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
	
	private boolean dulName(List<Region> regionList, String name){
		boolean flag = false;
		for(Region r:regionList){
			if(r.getRegionname().equals(name)){
				flag = true;
				break;
			}
		}
		return flag;
	}
}
