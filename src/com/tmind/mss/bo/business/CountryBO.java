package com.tmind.mss.bo.business;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tmind.mss.pub.dao.business.CountryDAO;
import com.tmind.mss.pub.po.Country;

public class CountryBO {

	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(CountryBO.class);
	
	private CountryDAO countryDAO;

	
	public void setCountryDAO(CountryDAO countryDAO) {
		this.countryDAO = countryDAO;
	}

	/**
	 * 保存或者更新国家信息
	 * @param transientInstance
	 */
	public void saveOrUpdate(Country transientInstance) {
		this.countryDAO.save(transientInstance);
	}
	
	/**
	 * 根据服务器记录id查询国家信息
	 * @param id
	 * @return
	 */
	public Country findById(java.lang.Integer id) {
		return this.countryDAO.findById(id);
	}
	
	/**
	 * 查询所有国家信息
	 * @return
	 */
	public List<Country> queryAllCountries(){
		return this.countryDAO.queryAllCountries();
	}
	
	/**
	 * 批量删除选中的国家记录（逻辑删除）
	 * @param typeNumStr
	 * @return
	 */
//	public int delCountries(String countryNumStr) {
//		return this.countryDAO.delCountries(countryNumStr);
//	}
}
