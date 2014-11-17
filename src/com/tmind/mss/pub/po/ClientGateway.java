package com.tmind.mss.pub.po;

/**
 * ClientGateway entity. @author MyEclipse Persistence Tools
 */

public class ClientGateway implements java.io.Serializable {

	// Fields

	private Integer linkid;
	private Integer clientid;
	private Integer gatewayid;

	// Constructors

	/** default constructor */
	public ClientGateway() {
	}

	/** full constructor */
	public ClientGateway(Integer clientid, Integer gatewayid) {
		this.clientid = clientid;
		this.gatewayid = gatewayid;
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

	public Integer getGatewayid() {
		return this.gatewayid;
	}

	public void setGatewayid(Integer gatewayid) {
		this.gatewayid = gatewayid;
	}

}