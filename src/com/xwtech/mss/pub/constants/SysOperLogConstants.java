package com.xwtech.mss.pub.constants;

public class SysOperLogConstants 
{

	public SysOperLogConstants() 
	{
		// TODO Auto-generated constructor stub
	}

	//	doType字段 
	public static final Long insert=new Long(1);//新增
	public static final Long update=new Long(2);//修改
	public static final Long delete=new Long(3);//删除
	public static final Long discard=new Long(4);//作废

	//objType字段 
	public static final Long requestBuy=new Long(1);// 请购单 
	public static final Long requireBuy=new Long(2);// 需求采购单 
	public static final Long contractCheck=new Long(3);// 合同审批单
	public static final Long contract=new Long(4);// 合同
	public static final Long contractRecord=new Long(5);// 商务合同记帐单
	public static final Long feeOff=new Long(6);// 费用报销单
	public static final Long thingInfo=new Long(7);// 物品信息
	public static final Long factoryInfo=new Long(8);// 厂家信息
	public static final Long personInfo=new Long(9);// 人员信息
	public static final Long attrach=new Long(10);// 附件
		
	
	//各单据类型
	public static final String REQ_TYPE = "REQ";//请购单
	public static final String CONTR_TYPE = "CONTR";//合同审批单
	public static final String BUSS_TYPE = "BUSS";//商务合同审批单
	public static final String FEE_TYPE = "FEE";//费用报销单
	
	
}
