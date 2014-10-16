package com.xwtech.mss.pub.tools;

import java.util.Vector;

public class ExcelBean {
	private String title;//标题����
	private String sheetName;//sheet名称
	private Vector[] titleContent;//标题内容
	private Vector[] content;//内容
	private int[] type;
	
	public int[] getType()
	{
		return type;
	}
	public void setType(int[] type)
	{
		this.type = type;
	}
	public Vector[] getContent() {
		return content;
	}
	public void setContent(Vector[] content) {
		this.content = content;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Vector[] getTitleContent() {
		return titleContent;
	}
	public void setTitleContent(Vector[] titleContent) {
		this.titleContent = titleContent;
	}
	
	
	

}
