package com.xwtech.mss.pub.po;

/**
 * ClientServerMapping entity. @author MyEclipse Persistence Tools
 */

public class ClientServerMapping implements java.io.Serializable {

	// Fields

	private Integer linkid;
	private Integer clientid;
	private Integer serverid;

	// Constructors

	/** default constructor */
	public ClientServerMapping() {
	}

	/** full constructor */
	public ClientServerMapping(Integer clientid, Integer serverid) {
		this.clientid = clientid;
		this.serverid = serverid;
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

	public Integer getServerid() {
		return this.serverid;
	}

	public void setServerid(Integer serverid) {
		this.serverid = serverid;
	}

}