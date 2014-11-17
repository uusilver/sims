package com.tmind.mss.pub.po;

import java.util.Date;

/**
 * OperDNSServer entity. @author MyEclipse Persistence Tools
 */

public class OperDNSServer implements java.io.Serializable {

	// Fields

	private Integer dnsserverid;
	private String serverip;
	private Integer serverport;
	private Integer servertype;
	private String note;
	private String status;
	private Integer addWho;
	private Date addTime;
	private Integer editWho;
	private Date editTime;

	// Constructors

	/** default constructor */
	public OperDNSServer() {
	}

	/** full constructor */
	public OperDNSServer(String serverip, Integer serverport, Integer servertype,String note,String status) {
		this.serverip = serverip;
		this.serverport = serverport;
		this.servertype = servertype;
		this.note = note;
		this.status = status;
	}

	// Property accessors


	public String getStatus() {
		return this.status;
	}

	public Integer getDnsserverid() {
		return dnsserverid;
	}

	public void setDnsserverid(Integer dnsserverid) {
		this.dnsserverid = dnsserverid;
	}

	public String getServerip() {
		return serverip;
	}

	public void setServerip(String serverip) {
		this.serverip = serverip;
	}

	public Integer getServerport() {
		return serverport;
	}

	public void setServerport(Integer serverport) {
		this.serverport = serverport;
	}

	public Integer getServertype() {
		return servertype;
	}

	public void setServertype(Integer servertype) {
		this.servertype = servertype;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getAddWho() {
		return addWho;
	}

	public void setAddWho(Integer addWho) {
		this.addWho = addWho;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getEditWho() {
		return editWho;
	}

	public void setEditWho(Integer editWho) {
		this.editWho = editWho;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}