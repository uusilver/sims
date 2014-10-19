package com.xwtech.mss.pub.po;

/**
 * ClientGroup entity. @author MyEclipse Persistence Tools
 */

public class ClientGroup implements java.io.Serializable {

	// Fields

	private Integer clientgroupid;
	private String clientgroupname;
	private String note;
	private String status;

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

}