package com.tmind.mss.pub.po;

/**
 * ServerStatus entity. @author MyEclipse Persistence Tools
 */

public class ServerStatus implements java.io.Serializable {

	// Fields

	private Integer serverstatusid;
	private Integer serverid;
	private Integer runstatus;
	private Integer linkcount;
	private Integer delay;
	private Integer upflow;
	private Integer downflow;
	private Integer nodetype;
	private String status;

	// Constructors

	/** default constructor */
	public ServerStatus() {
	}

	/** full constructor */
	public ServerStatus(Integer serverid, Integer runstatus, Integer linkcount,
			Integer delay, Integer upflow, Integer downflow, Integer nodetype,
			String status) {
		this.serverid = serverid;
		this.runstatus = runstatus;
		this.linkcount = linkcount;
		this.delay = delay;
		this.upflow = upflow;
		this.downflow = downflow;
		this.nodetype = nodetype;
		this.status = status;
	}

	// Property accessors

	public Integer getServerstatusid() {
		return this.serverstatusid;
	}

	public void setServerstatusid(Integer serverstatusid) {
		this.serverstatusid = serverstatusid;
	}

	public Integer getServerid() {
		return this.serverid;
	}

	public void setServerid(Integer serverid) {
		this.serverid = serverid;
	}

	public Integer getRunstatus() {
		return this.runstatus;
	}

	public void setRunstatus(Integer runstatus) {
		this.runstatus = runstatus;
	}

	public Integer getLinkcount() {
		return this.linkcount;
	}

	public void setLinkcount(Integer linkcount) {
		this.linkcount = linkcount;
	}

	public Integer getDelay() {
		return this.delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	public Integer getUpflow() {
		return this.upflow;
	}

	public void setUpflow(Integer upflow) {
		this.upflow = upflow;
	}

	public Integer getDownflow() {
		return this.downflow;
	}

	public void setDownflow(Integer downflow) {
		this.downflow = downflow;
	}

	public Integer getNodetype() {
		return this.nodetype;
	}

	public void setNodetype(Integer nodetype) {
		this.nodetype = nodetype;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}