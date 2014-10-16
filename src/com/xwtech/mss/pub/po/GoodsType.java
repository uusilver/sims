package com.xwtech.mss.pub.po;

import java.util.HashSet;
import java.util.Set;

/**
 * GoodsType entity. @author MyEclipse Persistence Tools
 */

public class GoodsType implements java.io.Serializable {

	// Fields

	private Long typeNum;
	private String typeName;
	private String typeShort;
	private GoodsType fatherType;
	private String STypeNum;
	private String createTime;
	private String modifyTime;
	private String typeState;
	private String typeComm;
	private Set sonTypes = new HashSet(0);

	// Constructors

	/** default constructor */
	public GoodsType() {
	}

	/** full constructor */
	public GoodsType(String typeName, String typeShort, GoodsType fatherType,String STypeNum,
			String createTime, String modifyTime, String typeState,
			String typeComm,Set sonTypes) {
		this.typeName = typeName;
		this.typeShort = typeShort;
		this.fatherType = fatherType;
		this.STypeNum = STypeNum;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.typeState = typeState;
		this.typeComm = typeComm;
		this.sonTypes = sonTypes;
	}

	// Property accessors

	public Long getTypeNum() {
		return this.typeNum;
	}

	public void setTypeNum(Long typeNum) {
		this.typeNum = typeNum;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeShort() {
		return this.typeShort;
	}

	public void setTypeShort(String typeShort) {
		this.typeShort = typeShort;
	}


	public GoodsType getFatherType() {
		return this.fatherType;
	}

	public void setFatherType(GoodsType fatherType) {
		this.fatherType = fatherType;
	}

	public String getSTypeNum() {
		return STypeNum;
	}

	public void setSTypeNum(String sTypeNum) {
		STypeNum = sTypeNum;
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

	public String getTypeState() {
		return this.typeState;
	}

	public void setTypeState(String typeState) {
		this.typeState = typeState;
	}

	public String getTypeComm() {
		return this.typeComm;
	}

	public void setTypeComm(String typeComm) {
		this.typeComm = typeComm;
	}

	public Set getSonTypes() {
		return this.sonTypes;
	}

	public void setSonTypes(Set sonTypes) {
		this.sonTypes = sonTypes;
	}

}