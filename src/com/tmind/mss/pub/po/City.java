package com.tmind.mss.pub.po;

/**
 * City entity. @author MyEclipse Persistence Tools
 */

public class City implements java.io.Serializable {

	// Fields
	/*
	 * [{cityid:1,cityname:'nj',.....},{}]
	 */
	private Integer cityid;
	private String cityname;
	private Integer provinceid;
	private String status;

	// Constructors

	/** default constructor */
	public City() {
	}

	/** full constructor */
	public City(String cityname, Integer provinceid, String status) {
		this.cityname = cityname;
		this.provinceid = provinceid;
		this.status = status;
	}

	// Property accessors

	public Integer getCityid() {
		return this.cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public String getCityname() {
		return this.cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public Integer getProvinceid() {
		return this.provinceid;
	}

	public void setProvinceid(Integer provinceid) {
		this.provinceid = provinceid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}