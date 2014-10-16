package com.xwtech.mss.formBean;


public class BaseForm {

	// 当前页数
	private String currentPage;
	
	//角色标识
	private String roleId;
	//角色名称
	private String roleName;
	//角色描述
	private String roleDesc;
	//角色状态
	private String roleState;
	//查询或修改
	private String viewOrEdit;
	
	//社区模块
	//查询日报，若结果为day，则是查询日报；若结果为空，默认查询月报
	private String isRptDay;
	//若为month，则是查询月报；若为day，则是查询日报；若为hour，则查询时报，
	private String rptType;
	//月份
	private String statMonth;
	//日期
	private String statDate;
	//开始日期
	private String dateStart;
	//结束日期
	private String dateEnd;
	//开始月份
	private String monthStart;
	//结束月份
	private String monthEnd;
	//统计类别
	private String statType;
	//统计项目
	private String statItem;
	//板块代码
	private String plateCode;
	//板块名称
	private String plateName;
	//主题代码
	private String topicCode;
	//主题名称
	private String topicName;
	//用户手机号码
	private String mobileNum;
	//按最早或者最后访问时间查询
	private String selectType;
	//查询主题信息的入口：plate：版块；topic：主题
	private String selectFrom;
	//查询社区访问情况的类型：1：数字信息；2：文字信息
	private String type;
	//栏目编码
	private String columnCode;
	//栏目名称
	private String columnName;
	
	

	/**
	 * @return the mobileNum
	 */
	public String getMobileNum() {
		return mobileNum;
	}

	/**
	 * @param mobileNum the mobileNum to set
	 */
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	/**
	 * @return the topicCode
	 */
	public String getTopicCode() {
		return topicCode;
	}

	/**
	 * @param topicCode the topicCode to set
	 */
	public void setTopicCode(String topicCode) {
		this.topicCode = topicCode;
	}

	/**
	 * @return the topicName
	 */
	public String getTopicName() {
		return topicName;
	}

	/**
	 * @param topicName the topicName to set
	 */
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	/**
	 * @return the plateCode
	 */
	public String getPlateCode() {
		return plateCode;
	}

	/**
	 * @param plateCode the plateCode to set
	 */
	public void setPlateCode(String plateCode) {
		this.plateCode = plateCode;
	}

	/**
	 * @return the plateName
	 */
	public String getPlateName() {
		return plateName;
	}

	/**
	 * @param plateName the plateName to set
	 */
	public void setPlateName(String plateName) {
		this.plateName = plateName;
	}

	/**
	 * @return the isRptDay
	 */
	public String getIsRptDay() {
		return isRptDay;
	}

	/**
	 * @param isRptDay the isRptDay to set
	 */
	public void setIsRptDay(String isRptDay) {
		this.isRptDay = isRptDay;
	}


	public String getRptType() {
		return rptType;
	}

	public void setRptType(String rptType) {
		this.rptType = rptType;
	}

	/**
	 * @return the statMonth
	 */
	public String getStatMonth() {
		return statMonth;
	}

	/**
	 * @param statMonth the statMonth to set
	 */
	public void setStatMonth(String statMonth) {
		this.statMonth = statMonth;
	}

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the roleDesc
	 */
	public String getRoleDesc() {
		return roleDesc;
	}

	/**
	 * @param roleDesc the roleDesc to set
	 */
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	/**
	 * @return the viewOrEdit
	 */
	public String getViewOrEdit() {
		return viewOrEdit;
	}

	/**
	 * @param viewOrEdit the viewOrEdit to set
	 */
	public void setViewOrEdit(String viewOrEdit) {
		this.viewOrEdit = viewOrEdit;
	}

	/**
	 * @return the currentPage
	 */
	public String getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the roleState
	 */
	public String getRoleState() {
		return roleState;
	}

	/**
	 * @param roleState the roleState to set
	 */
	public void setRoleState(String roleState) {
		this.roleState = roleState;
	}

	/**
	 * @return the dateEnd
	 */
	public String getDateEnd() {
		return dateEnd;
	}

	/**
	 * @param dateEnd the dateEnd to set
	 */
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	/**
	 * @return the dateStart
	 */
	public String getDateStart() {
		return dateStart;
	}

	/**
	 * @param dateStart the dateStart to set
	 */
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getMonthEnd() {
		return monthEnd;
	}

	public void setMonthEnd(String monthEnd) {
		this.monthEnd = monthEnd;
	}

	public String getMonthStart() {
		return monthStart;
	}

	public void setMonthStart(String monthStart) {
		this.monthStart = monthStart;
	}

	/**
	 * @return the statItem
	 */
	public String getStatItem() {
		return statItem;
	}

	/**
	 * @param statItem the statItem to set
	 */
	public void setStatItem(String statItem) {
		this.statItem = statItem;
	}

	/**
	 * @return the statType
	 */
	public String getStatType() {
		return statType;
	}

	/**
	 * @param statType the statType to set
	 */
	public void setStatType(String statType) {
		this.statType = statType;
	}

	/**
	 * @return the statDate
	 */
	public String getStatDate() {
		return statDate;
	}

	/**
	 * @param statDate the statDate to set
	 */
	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		if(selectType==null || selectType.equals("")){
			this.selectType = "first";
		}else{
			this.selectType = selectType;
		}
	}

	public String getSelectFrom() {
		return selectFrom;
	}

	public void setSelectFrom(String selectFrom) {
		this.selectFrom = selectFrom;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

}
