package com.tmind.mss.pub.po;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TransitServer entity. @author MyEclipse Persistence Tools
 */

public class TransitServer implements java.io.Serializable {

	// Fields

	private Integer serverid;
	private String serverip;
	private Integer countryid;
	private Integer provinceid;
	private Integer cityid;
	private Integer servertype;
	private Integer serverstatus;
	private Date invalidtime;
	private Integer limitation;
	private Integer regionid;
	private Date updatetime;
	private String note;
	private String status;
	private Integer addWho;
	private Date addTime;
	private Integer editWho;
	private Date editTime;

	// Constructors

	/** default constructor */
	public TransitServer() {
	}

	/** full constructor */
	public TransitServer(String serverip, Integer countryid,
			Integer provinceid, Integer cityid, Integer servertype,
			Integer serverstatus, Date invalidtime, Integer limitation,
			Integer regionid, Date updatetime, String note, String status) {
		this.serverip = serverip;
		this.countryid = countryid;
		this.provinceid = provinceid;
		this.cityid = cityid;
		this.servertype = servertype;
		this.serverstatus = serverstatus;
		this.invalidtime = invalidtime;
		this.limitation = limitation;
		this.regionid = regionid;
		this.updatetime = updatetime;
		this.note = note;
		this.status = status;
	}

	// Property accessors

	public Integer getServerid() {
		return this.serverid;
	}

	public void setServerid(Integer serverid) {
		this.serverid = serverid;
	}

	public String getServerip() {
		return this.serverip;
	}

	public void setServerip(String serverip) {
		this.serverip = serverip;
	}

	public Integer getCountryid() {
		return this.countryid;
	}

	public void setCountryid(Integer countryid) {
		this.countryid = countryid;
	}

	public Integer getProvinceid() {
		return this.provinceid;
	}

	public void setProvinceid(Integer provinceid) {
		this.provinceid = provinceid;
	}

	public Integer getCityid() {
		return this.cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public Integer getServertype() {
		return this.servertype;
	}

	public void setServertype(Integer servertype) {
		this.servertype = servertype;
	}

	public Integer getServerstatus() {
		return this.serverstatus;
	}

	public void setServerstatus(Integer serverstatus) {
		this.serverstatus = serverstatus;
	}

	public Date getInvalidtime() {
		return this.invalidtime;
	}

	public void setInvalidtime(Date invalidtime) {
		this.invalidtime = invalidtime;
	}

	public Integer getLimitation() {
		return this.limitation;
	}

	public void setLimitation(Integer limitation) {
		this.limitation = limitation;
	}

	public Integer getRegionid() {
		return this.regionid;
	}

	public void setRegionid(Integer regionid) {
		this.regionid = regionid;
	}

	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getAddWho() {
		return addWho;
	}

	public void setAddWho(Integer addWho) {
		this.addWho = addWho;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getEditWho() {
		return editWho;
	}

	public void setEditWho(Integer editWho) {
		this.editWho = editWho;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

}