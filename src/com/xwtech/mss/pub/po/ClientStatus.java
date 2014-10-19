package com.xwtech.mss.pub.po;

/**
 * ClientStatus entity. @author MyEclipse Persistence Tools
 */

public class ClientStatus implements java.io.Serializable {

	// Fields

	private Integer statusid;
	private Integer clientid;
	private Integer clientstatus;
	private Integer onlinetimer;
	private String status;

	// Constructors

	/** default constructor */
	public ClientStatus() {
	}

	/** minimal constructor */
	public ClientStatus(Integer clientid, Integer clientstatus,
			Integer onlinetimer) {
		this.clientid = clientid;
		this.clientstatus = clientstatus;
		this.onlinetimer = onlinetimer;
	}

	/** full constructor */
	public ClientStatus(Integer clientid, Integer clientstatus,
			Integer onlinetimer, String status) {
		this.clientid = clientid;
		this.clientstatus = clientstatus;
		this.onlinetimer = onlinetimer;
		this.status = status;
	}

	// Property accessors

	public Integer getStatusid() {
		return this.statusid;
	}

	public void setStatusid(Integer statusid) {
		this.statusid = statusid;
	}

	public Integer getClientid() {
		return this.clientid;
	}

	public void setClientid(Integer clientid) {
		this.clientid = clientid;
	}

	public Integer getClientstatus() {
		return this.clientstatus;
	}

	public void setClientstatus(Integer clientstatus) {
		this.clientstatus = clientstatus;
	}

	public Integer getOnlinetimer() {
		return this.onlinetimer;
	}

	public void setOnlinetimer(Integer onlinetimer) {
		this.onlinetimer = onlinetimer;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}