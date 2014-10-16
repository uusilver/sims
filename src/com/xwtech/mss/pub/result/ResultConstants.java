package com.xwtech.mss.pub.result;

public class ResultConstants{
  public static final int LOGIN_SUECCESS = 10000;  //登陆成功，欢迎您！
  public static final int LOGIN_NAME_AND_PWD_ERROR = 10003;  //登陆失败,用户名或密码不正确！
  public static final int NOT_ACCESS_ROLE = 10004;  //权限验证失败,您没有访问权限,请联系管理员！
  public static final int NOT_LOGIN_NOT_ACCESS_ROLE = 10005;  //您尚未登陆系统，没有权限进行此操作！
  public static final int USER_ADD_SUCCESS = 80001;  //用户信息添加成功！
  public static final int USER_ADD_FAILED = 80002;  //用户信息添加失败！
  public static final int USER_MOD_SUCCESS = 80003;  //用户信息修改成功！
  public static final int USER_MOD_FAILED = 80004;  //用户信息修改失败！
  public static final int USER_DEL_SUCCESS = 80005;  //用户信息删除成功！
  public static final int USER_DEL_HASREF = 80006;  //用户有关联信息，不能删除！
  
  public static final int USER_PWD_MOD_SUCCESS = 80013;  //用户密码修改成功！
  public static final int USER_PWD_MOD_FAILED = 80014;  //用户密码修改失败！
  public static final int USER_DEL_FAILED = 80015;  //用户信息删除失败！
 
  
  public static final int ROLEINFO_ADD_SUCCESS = 80041; 	//角色新增成功！
  public static final int ROLEINFO_ADD_FAILED = 80042; 		//角色新增失败！
  public static final int ROLEINFO_MOD_SUCCESS = 80043; 	//角色更新成功！
  public static final int ROLEINFO_MOD_FAILED = 80044; 		//角色更新失败！
  public static final int ROLEINFO_DEL_SUCCESS = 80045; 	//角色删除成功！
  public static final int ROLEINFO_DEL_FAILED = 80046; 		//角色删除失败！
  
  public static final int ADD_GOODS_TYPE_SUCCESS = 80020; 	//保存物品类别成功！
  public static final int ADD_GOODS_TYPE_FAILED = 80021;    //保存物品类别失败！
  public static final int DEL_GOODS_TYPE_SUCCESS = 80022; 	//删除物品类别成功！
  public static final int DEL_GOODS_TYPE_FAILED = 80023;    //删除物品类别失败！
  
  public static final int ADD_GOODS_INFO_SUCCESS = 80024; 	//保存物品信息成功！
  public static final int ADD_GOODS_INFO_FAILED = 80025;    //保存物品信息失败！
  public static final int DEL_GOODS_INFO_SUCCESS = 80026; 	//删除物品信息成功！
  public static final int DEL_GOODS_INFO_FAILED = 80027;    //删除物品信息失败！
  public static final int UPDATE_GOODS_INFO_SUCCESS = 80039;    //更新物品信息成功！
  public static final int UPDATE_GOODS_INFO_FAILED = 80040;    //更新物品信息失败！
  

  public static final int ADD_GOODS_IN_RECORD_SUCCESS = 80028; 	//保存物品入库记录成功！
  public static final int ADD_GOODS_IN_RECORD_FAILED = 80029;    //保存物品入库记录失败！
  public static final int ADD_GOODS_OUT_RECORD_SUCCESS = 80030; 	//保存物品出库记录成功！
  public static final int ADD_GOODS_OUT_RECORD_FAILED = 80031;    //保存物品出库记录失败！
  public static final int ADD_GOODS_BACK_RECORD_SUCCESS = 80032; 	//保存物品退库记录成功！
  public static final int ADD_GOODS_BACK_RECORD_FAILED = 80033;    //保存物品退库记录失败！
  
  public static final int GOODS_COUNT_NOT_ENOUGH = 80034; //物品库存量不足,出库失败！

  public static final int ADD_CLIENT_INFO_SUCCESS = 80035; 	//保存客户信息成功！
  public static final int ADD_CLIENT_INFO_FAILED = 80036;    //保存客户信息失败！
  public static final int DEL_CLIENT_INFO_SUCCESS = 80037; 	//删除客户信息成功！
  public static final int DEL_CLIENT_INFO_FAILED = 80038;    //删除客户信息失败！
  
  public static final int UPDATE_RECORD_STATE_SUCCESS = 80054;    //修改付款状态成功！
  public static final int UPDATE_RECORD_STATE_FAILED = 80055;    //修改付款状态失败！
  
  
  
  

  /**信息加载失败*/
  public static final int LOAD_ERROR = 1005;
  

}
