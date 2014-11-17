package com.tmind.mss.pub.po;

/**
 * Country entity. @author MyEclipse Persistence Tools
 */

public class Country implements java.io.Serializable {

	// Fields

	private Integer countryid;
	private String countryname;
	private String status;

	// Constructors

	/** default constructor */
	public Country() {
	}

	/** full constructor */
	public Country(String countryname, String status) {
		this.countryname = countryname;
		this.status = status;
	}

	// Property accessors

	public Integer getCountryid() {
		return this.countryid;
	}

	public void setCountryid(Integer countryid) {
		this.countryid = countryid;
	}

	public String getCountryname() {
		return this.countryname;
	}

	public void setCountryname(String countryname) {
		this.countryname = countryname;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}