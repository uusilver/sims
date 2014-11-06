package com.xwtech.mss.pub.po;

import java.util.Date;

/**
 * ClientGroup entity. @author MyEclipse Persistence Tools
 */

public class ClientGroup implements java.io.Serializable {

	// Fields

	private Integer clientgroupid;
	private String clientgroupname;
	private String note;
	private String status;
	private Integer addWho;
	private Date addTime;
	private Integer editWho;
	private Date editTime;

	// Constructors

	/** default constructor */
	public ClientGroup() {
	}

	/** full constructor */
	public ClientGroup(String clientgroupname, String note, String status) {
		this.clientgroupname = clientgroupname;
		this.note = note;
		this.status = status;
	}

	// Property accessors

	public Integer getClientgroupid() {
		return this.clientgroupid;
	}

	public void setClientgroupid(Integer clientgroupid) {
		this.clientgroupid = clientgroupid;
	}

	public String getClientgroupname() {
		return this.clientgroupname;
	}

	public void setClientgroupname(String clientgroupname) {
		this.clientgroupname = clientgroupname;
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

}