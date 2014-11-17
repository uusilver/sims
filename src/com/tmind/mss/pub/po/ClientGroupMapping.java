package com.tmind.mss.pub.po;

/**
 * ClientGroupMapping entity. @author MyEclipse Persistence Tools
 */

public class ClientGroupMapping implements java.io.Serializable {

	// Fields

	private Integer linkid;
	private Integer clientid;
	private Integer clientgroupid;

	// Constructors

	/** default constructor */
	public ClientGroupMapping() {
	}

	/** full constructor */
	public ClientGroupMapping(Integer clientid, Integer clientgroupid) {
		this.clientid = clientid;
		this.clientgroupid = clientgroupid;
	}

	// Property accessors

	public Integer getLinkid() {
		return this.linkid;
	}

	public void setLinkid(Integer linkid) {
		this.linkid = linkid;
	}

	public Integer getClientid() {
		return this.clientid;
	}

	public void setClientid(Integer clientid) {
		this.clientid = clientid;
	}

	public Integer getClientgroupid() {
		return this.clientgroupid;
	}

	public void setClientgroupid(Integer clientgroupid) {
		this.clientgroupid = clientgroupid;
	}

}