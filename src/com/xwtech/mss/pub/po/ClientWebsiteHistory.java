package com.xwtech.mss.pub.po;

import java.util.Date;

/**
 * ClientWebsiteHistory entity. @author MyEclipse Persistence Tools
 */

public class ClientWebsiteHistory implements java.io.Serializable {

	// Fields

	private Integer historyid;
	private Integer clientid;
	private String domainname;
	private String websiteip;
	private Date browsertime;
	private String status;

	// Constructors

	/** default constructor */
	public ClientWebsiteHistory() {
	}

	/** minimal constructor */
	public ClientWebsiteHistory(Integer clientid, String domainname,
			String websiteip, Date browsertime) {
		this.clientid = clientid;
		this.domainname = domainname;
		this.websiteip = websiteip;
		this.browsertime = browsertime;
	}

	/** full constructor */
	public ClientWebsiteHistory(Integer clientid, String domainname,
			String websiteip, Date browsertime, String status) {
		this.clientid = clientid;
		this.domainname = domainname;
		this.websiteip = websiteip;
		this.browsertime = browsertime;
		this.status = status;
	}

	// Property accessors

	public Integer getHistoryid() {
		return this.historyid;
	}

	public void setHistoryid(Integer historyid) {
		this.historyid = historyid;
	}

	public Integer getClientid() {
		return this.clientid;
	}

	public void setClientid(Integer clientid) {
		this.clientid = clientid;
	}

	public String getDomainname() {
		return this.domainname;
	}

	public void setDomainname(String domainname) {
		this.domainname = domainname;
	}

	public String getWebsiteip() {
		return this.websiteip;
	}

	public void setWebsiteip(String websiteip) {
		this.websiteip = websiteip;
	}

	public Date getBrowsertime() {
		return this.browsertime;
	}

	public void setBrowsertime(Date browsertime) {
		this.browsertime = browsertime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}