package com.xwtech.framework.pub.utils;

import com.huawei.usdp.UsdpFactory;
import org.apache.log4j.Logger;

/**
 *
 * <p>Title: Framework </p>
 * <p>Description: Framework</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: xwtech.com</p>
 * @author not attributable
 * @version 1.0
 */
public class SoapUtils
{
  protected static final Logger log = Logger.getLogger(SoapUtils.class);

  /**
   * 默认构造函数
   */
  public SoapUtils()
  {
  }

  /**
   *
   * @param module String
   * @param operation String
   * @param objEvent Object
   * @param resultClass String
   * @return Object
   */
  public static Object fetchPageResultFromDb(String module,String operation,Object objEvent,String resultClass)
  {
    Object  value = null;
    try{
      value = Class.forName(resultClass).newInstance();
      log.info("实例化结果对象:"+value);
      Object objModule = UsdpFactory.class.getMethod("get"+module,null).invoke(null,null);
      log.info("soap查询的Module:"+objModule);
      value = objModule.getClass().getMethod(operation,new Class[]{objEvent.getClass()}).invoke(objModule,new Object[]{objEvent});
      log.info("soap查询的结果返回对象:"+value);
    }catch (Exception e){
      log.error("Soap查询结果错误",e);
    }
    return value;
  }

}
