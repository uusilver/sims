package com.xwtech.mss.pub.po;

import java.math.BigDecimal;

/**
 * GoodsRecord entity. @author MyEclipse Persistence Tools
 */

public class GoodsRecord implements java.io.Serializable {

	// Fields

	private Long recordNum;
	private String goodsName;
	private Long goodsNum;
	private String goodsCode;
	private String goodsType;
	private String typeNum;
	private Long goodsCount;
	private Integer currentCount;
	private BigDecimal salePrice;
	private BigDecimal goodsProfit;
	private ClientInfo clientInfo;
	private String createTime;
	private String modifyTime;
	private Long recordType;
	private String recordState;
	private Long operator;
	private String userName;
	private String recordComm;
	private String clientConfirm;
	private String payTime;
	private BigDecimal finalPayPrice;
	private BigDecimal finalProfit;
	private Long fromRecordNum;
	private Long modifier;
	private String modifyUserName;

	// Constructors

	/** default constructor */
	public GoodsRecord() {
	}

	/** full constructor */
	public GoodsRecord(String goodsName, Long goodsNum,String goodsCode, String goodsType,
			String typeNum, Long goodsCount,Integer currentCount, BigDecimal salePrice,BigDecimal goodsProfit, ClientInfo clientInfo,
			String createTime,String modifyTime, Long recordType, String recordState,Long operator,String userName,String recordComm
			,String clientConfirm,String payTime,BigDecimal finalPayPrice,BigDecimal finalProfit,Long fromRecordNum,Long modifier,String modifyUserName) {
		this.goodsName = goodsName;
		this.goodsNum = goodsNum;
		this.goodsCode = goodsCode;
		this.goodsType = goodsType;
		this.typeNum = typeNum;
		this.goodsCount = goodsCount;
		this.currentCount = currentCount;
		this.salePrice = salePrice;
		this.goodsProfit = goodsProfit;
		this.clientInfo = clientInfo;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.recordType = recordType;
		this.recordState = recordState;
		this.operator = operator;
		this.userName = userName;
		this.recordComm = recordComm;
		this.clientConfirm = clientConfirm;
		this.payTime = payTime;
		this.finalPayPrice = finalPayPrice;
		this.finalProfit = finalProfit;
		this.fromRecordNum = fromRecordNum;
		this.modifier = modifier;
		this.modifyUserName = modifyUserName;
	}

	// Property accessors

	public Long getRecordNum() {
		return this.recordNum;
	}

	public void setRecordNum(Long recordNum) {
		this.recordNum = recordNum;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Long getGoodsNum() {
		return this.goodsNum;
	}

	public void setGoodsNum(Long goodsNum) {
		this.goodsNum = goodsNum;
	}

	public String getGoodsType() {
		return this.goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getTypeNum() {
		return this.typeNum;
	}

	public void setTypeNum(String typeNum) {
		this.typeNum = typeNum;
	}

	public Long getGoodsCount() {
		return this.goodsCount;
	}

	public void setGoodsCount(Long goodsCount) {
		this.goodsCount = goodsCount;
	}

	public Integer getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}

	public BigDecimal getSalePrice() {
		return this.salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getGoodsProfit() {
		return this.goodsProfit;
	}

	public void setGoodsProfit(BigDecimal goodsProfit) {
		this.goodsProfit = goodsProfit;
	}

	public ClientInfo getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}

	public String getCreateTime() {
		return this.createTime;
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

	public Long getRecordType() {
		return this.recordType;
	}

	public void setRecordType(Long recordType) {
		this.recordType = recordType;
	}

	public String getRecordState() {
		return this.recordState;
	}

	public void setRecordState(String recordState) {
		this.recordState = recordState;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRecordComm() {
		return recordComm;
	}

	public void setRecordComm(String recordComm) {
		this.recordComm = recordComm;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getClientConfirm() {
		return clientConfirm;
	}

	public void setClientConfirm(String clientConfirm) {
		this.clientConfirm = clientConfirm;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public BigDecimal getFinalPayPrice() {
		return finalPayPrice;
	}

	public void setFinalPayPrice(BigDecimal finalPayPrice) {
		this.finalPayPrice = finalPayPrice;
	}

	public BigDecimal getFinalProfit() {
		return finalProfit;
	}

	public void setFinalProfit(BigDecimal finalProfit) {
		this.finalProfit = finalProfit;
	}

	public Long getFromRecordNum() {
		return fromRecordNum;
	}

	public void setFromRecordNum(Long fromRecordNum) {
		this.fromRecordNum = fromRecordNum;
	}

	public Long getModifier() {
		return modifier;
	}

	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

}