package com.xwtech.mss.pub.po;

/**
 * Province entity. @author MyEclipse Persistence Tools
 */

public class Province implements java.io.Serializable {

	// Fields

	private Integer provinceid;
	private String provincename;
	private Integer countryid;
	private String status;

	// Constructors

	/** default constructor */
	public Province() {
	}

	/** full constructor */
	public Province(String provincename, Integer countryid, String status) {
		this.provincename = provincename;
		this.countryid = countryid;
		this.status = status;
	}

	// Property accessors

	public Integer getProvinceid() {
		return this.provinceid;
	}

	public void setProvinceid(Integer provinceid) {
		this.provinceid = provinceid;
	}

	public String getProvincename() {
		return this.provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

	public Integer getCountryid() {
		return this.countryid;
	}

	public void setCountryid(Integer countryid) {
		this.countryid = countryid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}