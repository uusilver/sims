package com.xwtech.mss.pub.po;

import java.math.BigDecimal;

/**
 * GoodsInfo entity. @author MyEclipse Persistence Tools
 */

public class GoodsInfo implements java.io.Serializable {

	// Fields

	private Long goodsNum;
	private String goodsName;
	private String goodsCode;
	private String goodsType;
	private String typeName;
	private Long goodsCount;
	private Double goodsWeight;
	private BigDecimal goodsPrice;
	private BigDecimal wishPrice;
	private String createTime;
	private String modifyTime;
	private String goodsState;
	private String goodsComm;
	private String codeNum;
	private Integer version;

	// Constructors

	/** default constructor */
	public GoodsInfo() {
	}

	/** full constructor */
	public GoodsInfo(String goodsName, String goodsCode, String goodsType,String typeName,
			Long goodsCount,Double goodsWeight, BigDecimal goodsPrice,BigDecimal wishPrice, String createTime,
			String modifyTime, String goodsState,String goodsComm,String codeNum) {
		this.goodsName = goodsName;
		this.goodsCode = goodsCode;
		this.goodsType = goodsType;
		this.typeName = typeName;
		this.goodsCount = goodsCount;
		this.goodsWeight = goodsWeight;
		this.goodsPrice = goodsPrice;
		this.wishPrice = wishPrice;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.goodsState = goodsState;
		this.goodsComm = goodsComm;
		this.codeNum = codeNum;
	}

	// Property accessors

	public Long getGoodsNum() {
		return this.goodsNum;
	}

	public void setGoodsNum(Long goodsNum) {
		this.goodsNum = goodsNum;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsCode() {
		return this.goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsType() {
		return this.goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getGoodsCount() {
		return this.goodsCount;
	}

	public void setGoodsCount(Long goodsCount) {
		this.goodsCount = goodsCount;
	}

	public Double getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(Double goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	public BigDecimal getWishPrice() {
		return wishPrice;
	}

	public void setWishPrice(BigDecimal wishPrice) {
		this.wishPrice = wishPrice;
	}

	public BigDecimal getGoodsPrice() {
		return this.goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getGoodsState() {
		return this.goodsState;
	}

	public void setGoodsState(String goodsState) {
		this.goodsState = goodsState;
	}

	public String getGoodsComm() {
		return goodsComm;
	}

	public void setGoodsComm(String goodsComm) {
		this.goodsComm = goodsComm;
	}

	public String getCodeNum() {
		return codeNum;
	}

	public void setCodeNum(String codeNum) {
		this.codeNum = codeNum;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}