package com.tmind.mss.json.bean;

import java.util.List;

public class ProvinceJModel {
	public ProvinceJModel() {
		super();
	}
	private String id;
	private String text;
	private List<CityJModel> children;
	private String icon;
	private li_attrJModel li_attr;
	private a_attrJModel a_attr;
	private stateJModel state;
	private boolean data;
	private String type;
	
	
	public ProvinceJModel(String id, String text, List<CityJModel> children) {
		super();
		this.id = id;
		this.text = text;
		this.children = children;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<CityJModel> getChildren() {
		return children;
	}
	public void setChildren(List<CityJModel> children) {
		this.children = children;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public li_attrJModel getLi_attr() {
		return li_attr;
	}
	public void setLi_attr(li_attrJModel li_attr) {
		this.li_attr = li_attr;
	}
	public a_attrJModel getA_attr() {
		return a_attr;
	}
	public void setA_attr(a_attrJModel a_attr) {
		this.a_attr = a_attr;
	}
	public stateJModel getState() {
		return state;
	}
	public void setState(stateJModel state) {
		this.state = state;
	}
	
	
	
	public boolean isData() {
		return data;
	}
	public void setData(boolean data) {
		this.data = data;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
