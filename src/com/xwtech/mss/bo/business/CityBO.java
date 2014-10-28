package com.xwtech.mss.bo.business;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xwtech.mss.pub.dao.business.CityDAO;
import com.xwtech.mss.pub.po.City;

public class CityBO {

	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(CityBO.class);
	
	private CityDAO cityDAO;

	
	public void setCityDAO(CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}

	/**
	 * 保存或者更新城市信息
	 * @param transientInstance
	 */
	public void saveOrUpdate(City transientInstance) {
		this.cityDAO.save(transientInstance);
	}
	
	/**
	 * 根据服务器记录id查询城市信息
	 * @param id
	 * @return
	 */
	public City findById(java.lang.Integer id) {
		return this.cityDAO.findById(id);
	}
	
	/**
	 * 根据查询条件查询城市信息
	 * @return
	 */
	public List<City> queryAllCitys(){
		return this.cityDAO.queryAllCitys();
	}
	
	
	public List<City> queryCityByProvinceId(String provinceId){
		return this.cityDAO.queryCityByProvinceId(provinceId);
	}
	/**
	 * 批量删除选中的城市记录（逻辑删除）
	 * @param typeNumStr
	 * @return
	 */
//	public int delServerInfo(String ServerNumStr) {
//		return this.cityDAO.delCity(ServerNumStr);
//	}
}
