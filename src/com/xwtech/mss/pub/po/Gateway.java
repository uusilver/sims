package com.xwtech.mss.pub.po;

/**
 * Gateway entity. @author MyEclipse Persistence Tools
 */

public class Gateway implements java.io.Serializable {

	// Fields

	private Integer gatewayid;
	private String gatewayguid;
	private Integer gatewaystatus;
	private String rangename;
	private Integer authenticationtype;
	private String note;
	private String status;

	// Constructors

	/** default constructor */
	public Gateway() {
	}

	/** minimal constructor */
	public Gateway(String gatewayguid) {
		this.gatewayguid = gatewayguid;
	}

	/** full constructor */
	public Gateway(String gatewayguid, Integer gatewaystatus, String rangename,
			Integer authenticationtype, String note, String status) {
		this.gatewayguid = gatewayguid;
		this.gatewaystatus = gatewaystatus;
		this.rangename = rangename;
		this.authenticationtype = authenticationtype;
		this.note = note;
		this.status = status;
	}

	// Property accessors

	public Integer getGatewayid() {
		return this.gatewayid;
	}

	public void setGatewayid(Integer gatewayid) {
		this.gatewayid = gatewayid;
	}

	public String getGatewayguid() {
		return this.gatewayguid;
	}

	public void setGatewayguid(String gatewayguid) {
		this.gatewayguid = gatewayguid;
	}

	public Integer getGatewaystatus() {
		return this.gatewaystatus;
	}

	public void setGatewaystatus(Integer gatewaystatus) {
		this.gatewaystatus = gatewaystatus;
	}

	public String getRangename() {
		return this.rangename;
	}

	public void setRangename(String rangename) {
		this.rangename = rangename;
	}

	public Integer getAuthenticationtype() {
		return this.authenticationtype;
	}

	public void setAuthenticationtype(Integer authenticationtype) {
		this.authenticationtype = authenticationtype;
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

}