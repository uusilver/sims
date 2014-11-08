package com.xwtech.mss.pub.po;

import java.util.Date;

/**
 * Client entity. @author MyEclipse Persistence Tools
 */

public class Client implements java.io.Serializable {

	// Fields

	private Integer clientid;
	private String keyid;
	private String username;
	private String password;
	private Integer modifypass;
	private Integer authenticationtype;
	private Integer disable;
	private Integer usertype;
	private String truename;
	private String telephone;
	private String mobilephone;
	private String firstLetter;
	private String note;
	private String status;
	private Integer addWho;
	private Date addTime;
	private Integer editWho;
	private Date editTime;

	// Constructors

	/** default constructor */
	public Client() {
	}

	/** minimal constructor */
	public Client(String keyid) {
		this.keyid = keyid;
	}

	/** full constructor */
	public Client(String keyid, String username, String password,
			Integer modifypass, Integer authenticationtype, Integer disable,
			Integer usertype, String truename,String telephone,String mobilephone,String firstLetter, String note, String status) {
		this.keyid = keyid;
		this.username = username;
		this.password = password;
		this.modifypass = modifypass;
		this.authenticationtype = authenticationtype;
		this.disable = disable;
		this.usertype = usertype;
		this.truename = truename;
		this.telephone = telephone;
		this.mobilephone = mobilephone;
		this.firstLetter = firstLetter;
		this.note = note;
		this.status = status;
	}

	// Property accessors

	public Integer getClientid() {
		return this.clientid;
	}

	public void setClientid(Integer clientid) {
		this.clientid = clientid;
	}

	public String getKeyid() {
		return this.keyid;
	}

	public void setKeyid(String keyid) {
		this.keyid = keyid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getModifypass() {
		return this.modifypass;
	}

	public void setModifypass(Integer modifypass) {
		this.modifypass = modifypass;
	}

	public Integer getAuthenticationtype() {
		return this.authenticationtype;
	}

	public void setAuthenticationtype(Integer authenticationtype) {
		this.authenticationtype = authenticationtype;
	}

	public Integer getDisable() {
		return this.disable;
	}

	public void setDisable(Integer disable) {
		this.disable = disable;
	}

	public Integer getUsertype() {
		return this.usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	public String getTruename() {
		return this.truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
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