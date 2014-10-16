package com.xwtech.mss.pub.po;

import java.util.HashSet;
import java.util.Set;

/**
 * ClientInfo entity. @author MyEclipse Persistence Tools
 */

public class ClientInfo implements java.io.Serializable {

	// Fields

	private Long clientNum;
	private String clientName;
	private String clientNick;
	private String clientTel;
	private String clientAddr;
	private String zipCode;
	private String eMail;
	private String clientState;
	private String createTime;
	private String modifyTime;
	private String clientComm;
	private String clientType;
	private String firstLetterName;
	private String firstLetterNick;
	private Set goodsRecords = new HashSet(0);

	// Constructors

	/** default constructor */
	public ClientInfo() {
	}

	/** full constructor */
	public ClientInfo(String clientName,String clientNick, String clientTel, String clientAddr,
			String zipCode,String eMail, String clientState,String createTime,String modifyTime,String clientComm,String clientType,Set goodsRecords
			,String firstLetterName,String firstLetterNick) {
		this.clientName = clientName;
		this.clientNick = clientNick;
		this.clientTel = clientTel;
		this.clientAddr = clientAddr;
		this.zipCode = zipCode;
		this.eMail = eMail;
		this.clientState = clientState;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.clientComm = clientComm;
		this.clientType = clientType;
		this.goodsRecords = goodsRecords;
		this.firstLetterName = firstLetterName;
		this.firstLetterNick = firstLetterNick;
	}

	// Property accessors

	public Long getClientNum() {
		return this.clientNum;
	}

	public void setClientNum(Long clientNum) {
		this.clientNum = clientNum;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientNick() {
		return clientNick;
	}

	public void setClientNick(String clientNick) {
		this.clientNick = clientNick;
	}

	public String getClientTel() {
		return this.clientTel;
	}

	public void setClientTel(String clientTel) {
		this.clientTel = clientTel;
	}

	public String getClientAddr() {
		return this.clientAddr;
	}

	public void setClientAddr(String clientAddr) {
		this.clientAddr = clientAddr;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getClientState() {
		return this.clientState;
	}

	public void setClientState(String clientState) {
		this.clientState = clientState;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getClientComm() {
		return clientComm;
	}

	public void setClientComm(String clientComm) {
		this.clientComm = clientComm;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public Set getGoodsRecords() {
		return goodsRecords;
	}

	public void setGoodsRecords(Set goodsRecords) {
		this.goodsRecords = goodsRecords;
	}

	public String getFirstLetterName() {
		return firstLetterName;
	}

	public void setFirstLetterName(String firstLetterName) {
		this.firstLetterName = firstLetterName;
	}

	public String getFirstLetterNick() {
		return firstLetterNick;
	}

	public void setFirstLetterNick(String firstLetterNick) {
		this.firstLetterNick = firstLetterNick;
	}

}