package com.xwtech.mss.pub.po;

/**
 * ServerGroup entity. @author MyEclipse Persistence Tools
 */

public class ServerGroup implements java.io.Serializable {

	// Fields

	private Integer servergroupid;
	private String servergroupname;
	private String note;
	private String status;

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

}