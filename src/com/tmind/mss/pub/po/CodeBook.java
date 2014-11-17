package com.tmind.mss.pub.po;

/**
 * CodeBook entity. @author MyEclipse Persistence Tools
 */

public class CodeBook implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5331311168718932905L;

	// Fields
	private Integer codeid;
	private String value;
	private String text;
	private String tag;
	private String comment;
	private String status;

	// Constructors

	/** default constructor */
	public CodeBook() {
	}

	/** full constructor */
	public CodeBook(String value, String text, String tag,String comment,String status) {
		this.value = value;
		this.text = text;
		this.tag = tag;
		this.comment = comment;
		this.status = status;
	}

	// Property accessors

	

	public String getStatus() {
		return this.status;
	}

	public Integer getCodeid() {
		return codeid;
	}

	public void setCodeid(Integer codeid) {
		this.codeid = codeid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}