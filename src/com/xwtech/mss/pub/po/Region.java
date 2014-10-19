package com.xwtech.mss.pub.po;

/**
 * Region entity. @author MyEclipse Persistence Tools
 */

public class Region implements java.io.Serializable {

	// Fields

	private Integer regionid;
	private String regionname;
	private String status;

	// Constructors

	/** default constructor */
	public Region() {
	}

	/** full constructor */
	public Region(String regionname, String status) {
		this.regionname = regionname;
		this.status = status;
	}

	// Property accessors

	public Integer getRegionid() {
		return this.regionid;
	}

	public void setRegionid(Integer regionid) {
		this.regionid = regionid;
	}

	public String getRegionname() {
		return this.regionname;
	}

	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}