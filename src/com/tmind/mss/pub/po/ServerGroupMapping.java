package com.tmind.mss.pub.po;

/**
 * ServerGroupMapping entity. @author MyEclipse Persistence Tools
 */

public class ServerGroupMapping implements java.io.Serializable {

	// Fields

	private Integer linkid;
	private Integer serverid;
	private Integer servergroupid;

	// Constructors

	/** default constructor */
	public ServerGroupMapping() {
	}

	/** full constructor */
	public ServerGroupMapping(Integer serverid, Integer servergroupid) {
		this.serverid = serverid;
		this.servergroupid = servergroupid;
	}

	// Property accessors

	public Integer getLinkid() {
		return this.linkid;
	}

	public void setLinkid(Integer linkid) {
		this.linkid = linkid;
	}

	public Integer getServerid() {
		return this.serverid;
	}

	public void setServerid(Integer serverid) {
		this.serverid = serverid;
	}

	public Integer getServergroupid() {
		return this.servergroupid;
	}

	public void setServergroupid(Integer servergroupid) {
		this.servergroupid = servergroupid;
	}

}