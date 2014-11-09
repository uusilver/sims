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
  
  public static final int ADD_CLIENT_INFO_SUCCESS = 80035; 	//保存客户信息成功！
  public static final int ADD_CLIENT_INFO_FAILED = 80036;    //保存客户信息失败！
  public static final int DEL_CLIENT_INFO_SUCCESS = 80037; 	//删除客户信息成功！
  public static final int DEL_CLIENT_INFO_FAILED = 80038;    //删除客户信息失败！
  public static final int UPDATE_CLIENT_INFO_SUCCESS = 80039; 	//更新客户信息成功！
  public static final int UPDATE_CLIENT_INFO_FAILED = 80040;    //更新客户信息失败！

  /**信息加载失败*/
  public static final int LOAD_ERROR = 1005;


  public static final int ADD_MENU_INFO_SUCCESS = 20030; 	//保存菜单信息成功！
  public static final int ADD_MENU_INFO_FAILED = 20031;    //保存菜单信息失败！
  public static final int DEL_MENU_INFO_SUCCESS = 20032; 	//删除菜单信息成功！
  public static final int DEL_MENU_INFO_FAILED = 20033;    //删除菜单信息失败！
  public static final int UPDATE_MENU_INFO_SUCCESS = 20034; 	//更新菜单信息成功！
  public static final int UPDATE_MENU_INFO_FAILED = 20035;    //更新菜单信息失败！

  /**服务器信息*/
  public static final int ADD_SERVER_INFO_SUCCESS = 90001; 	//保存服务器信息成功！
  public static final int ADD_SERVER_INFO_FAILED = 90002;    //保存服务器信息失败！
  public static final int DEL_SERVER_INFO_SUCCESS = 90003; 	//删除服务器信息成功！
  public static final int DEL_SERVER_INFO_FAILED = 90004;    //删除服务器信息失败！
  public static final int UPDATE_SERVER_INFO_SUCCESS = 90005;    //更新服务器信息成功！
  public static final int UPDATE_SERVER_INFO_FAILED = 90006;    //更新服务器信息失败！

  /**服务器组信息*/
  public static final int ADD_SERVER_GROUP_SUCCESS = 90007; 	//保存服务器组信息成功！
  public static final int ADD_SERVER_GROUP_FAILED = 90008;    //保存服务器组信息失败！
  public static final int DEL_SERVER_GROUP_SUCCESS = 90009; 	//删除服务器组成功！
  public static final int DEL_SERVER_GROUP_FAILED = 90010;    //删除服务器组失败！
  public static final int UPDATE_SERVER_GROUP_SUCCESS = 90011;    //更新服务器组信息成功！
  public static final int UPDATE_SERVER_GROUP_FAILED = 90012;    //更新服务器组信息失败！

  /**国家信息*/
  public static final int ADD_COUNTRY_INFO_SUCCESS = 90013; 	//保存国家信息成功！
  public static final int ADD_COUNTRY_INFO_FAILED = 90014;    //保存国家信息失败！
  public static final int DEL_COUNTRY_INFO_SUCCESS = 90015; 	//删除国家信息成功！
  public static final int DEL_COUNTRY_INFO_FAILED = 90016;    //删除国家信息失败！
  public static final int UPDATE_COUNTRY_INFO_SUCCESS = 90017;    //更新国家信息成功！
  public static final int UPDATE_COUNTRY_INFO_FAILED = 90018;    //更新国家信息失败！

  /**省(州)信息*/
  public static final int ADD_PROVINCE_INFO_SUCCESS = 90019; 	//保存省(州)信息成功！
  public static final int ADD_PROVINCE_INFO_FAILED = 90020;    //保存省(州)信息失败！
  public static final int DEL_PROVINCE_INFO_SUCCESS = 90021; 	//删除省(州)信息成功！
  public static final int DEL_PROVINCE_INFO_FAILED = 90022;    //删除省(州)信息失败！
  public static final int UPDATE_PROVINCE_INFO_SUCCESS = 90023;    //更新省(州)信息成功！
  public static final int UPDATE_PROVINCE_INFO_FAILED = 90024;    //更新省(州)信息失败！

  /**城市信息*/
  public static final int ADD_CITY_INFO_SUCCESS = 90025; 	//保存城市信息成功！
  public static final int ADD_CITY_INFO_FAILED = 90026;    //保存城市信息失败！
  public static final int DEL_CITY_INFO_SUCCESS = 90027; 	//删除城市信息成功！
  public static final int DEL_CITY_INFO_FAILED = 90028;    //删除城市信息失败！
  public static final int UPDATE_CITY_INFO_SUCCESS = 90029;    //更新城市信息成功！
  public static final int UPDATE_CITY_INFO_FAILED = 90030;    //更新城市信息失败！

  /**地域信息*/
  public static final int ADD_REGION_INFO_SUCCESS = 90031; 	//保存地域信息成功！
  public static final int ADD_REGION_INFO_FAILED = 90032;    //保存地域信息失败！
  public static final int DEL_REGION_INFO_SUCCESS = 90033; 	//删除地域信息成功！
  public static final int DEL_REGION_INFO_FAILED = 90034;    //删除地域信息失败！
  public static final int UPDATE_REGION_INFO_SUCCESS = 90035;    //更新地域信息成功！
  public static final int UPDATE_REGION_INFO_FAILED = 90036;    //更新地域信息失败！

}
