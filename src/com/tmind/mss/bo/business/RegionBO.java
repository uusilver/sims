package com.tmind.mss.bo.business;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tmind.mss.pub.dao.business.RegionDAO;
import com.tmind.mss.pub.po.Province;
import com.tmind.mss.pub.po.Region;

public class RegionBO {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(ProvinceBO.class);
	
	
	private RegionDAO regionDAO;

	public void setRegionDAO(RegionDAO regionDAO) {
		this.regionDAO = regionDAO;
	}
	
	public List<Region> queryAllRegions(){
		return this.regionDAO.findAll();
	}
	
	public void saveOrUpdateRegion(Region region){
		this.regionDAO.save(region);
	}
	
}
