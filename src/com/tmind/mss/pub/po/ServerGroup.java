package com.tmind.mss.pub.po;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ServerGroup entity. @author MyEclipse Persistence Tools
 */

public class ServerGroup implements java.io.Serializable {

	// Fields

	private Integer servergroupid;
	private String servergroupname;
	private String note;
	private String status;
	private Integer addWho;
	private Date addTime;
	private Integer editWho;
	private Date editTime;

	// Constructors

	/** default constructor */
	public ServerGroup() {
	}

	/** full constructor */
	public ServerGroup(String servergroupname, String note, String status) {
		this.servergroupname = servergroupname;
		this.note = note;
		this.status = status;
	}

	// Property accessors

	public Integer getServergroupid() {
		return this.servergroupid;
	}

	public void setServergroupid(Integer servergroupid) {
		this.servergroupid = servergroupid;
	}

	public String getServergroupname() {
		return this.servergroupname;
	}

	public void setServergroupname(String servergroupname) {
		this.servergroupname = servergroupname;
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