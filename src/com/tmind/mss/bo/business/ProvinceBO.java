package com.tmind.mss.bo.business;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tmind.mss.pub.dao.business.ProvinceDAO;
import com.tmind.mss.pub.po.Province;

public class ProvinceBO {

	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(ProvinceBO.class);
	
	private ProvinceDAO provinceDAO;

	
	public void setProvinceDAO(ProvinceDAO provinceDAO) {
		this.provinceDAO = provinceDAO;
	}

	/**
	 * 保存或者更新省（州）信息
	 * @param transientInstance
	 */
	public void saveOrUpdate(Province transientInstance) {
		this.provinceDAO.save(transientInstance);
	}
	
	/**
	 * 根据服务器记录id查询省（州）信息
	 * @param id
	 * @return
	 */
	public Province findById(java.lang.Integer id) {
		return this.provinceDAO.findById(id);
	}
	
	/**
	 * 查询所有省（州）信息
	 * @param searchForm
	 * @param perPageCount
	 * @return
	 */
	public List<Province> queryAllProvinces(){
		return this.provinceDAO.queryAllProvinces();
	}
	
	/**
	 * 批量删除选中的服务器记录（逻辑删除）
	 * @param typeNumStr
	 * @return
	 */
	
	public List<Province> queryProvinceByCountryId(String countryId){
		return this.provinceDAO.queryProvinceByCountryId(countryId);
	}
//	public int delProvinces(String provinceNumStr) {
//		return this.provinceDAO.delServerInfo(provinceNumStr);
//	}
	
}
