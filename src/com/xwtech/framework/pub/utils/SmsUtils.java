package com.xwtech.framework.pub.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class SmsUtils
{
  protected static final Logger log = Logger.getLogger(SmsUtils.class);

  public SmsUtils()
  {
  }

  public static HashMap getParam(String processParam){
    String[] arrParam = null;
    HashMap map = new HashMap();
    if (processParam!=null && processParam.length()!=0){
      arrParam = processParam.split(",");
      for(int i=0;i<arrParam.length;i++){
        int position = arrParam[i].indexOf("=");
        String paraName = arrParam[i].substring(0,position);
        String paraValue = arrParam[i].substring(position+1);
        map.put(paraName,paraValue);
      }
    }
    return map;
  }

  public static List cutString(String strParam,String strCut){
    ArrayList list = new ArrayList();
    String[] arrStr = strParam.split(strCut);
    for(int i=0;i<arrStr.length;i++){
      if (arrStr[i]!=null && arrStr[i].length()!=0)
        list.add(arrStr[i]);
    }
    return list;
  }

  /**
   * 替换下发短信中的宏定义
   * @param message String
   * @param map HashMap
   * @return String
   */
  public static String replaceMsgValue(String message, HashMap map)
  {
    MacroString macro = null;
    try {
      //返回信息中的宏替换
      macro = new MacroString(message,"{","}");
      Iterator iterator = macro.getMacroMap().keySet().iterator();
      while (iterator.hasNext()) {
        String paramName = (String)iterator.next();
        macro.setMacroValue(paramName,(String)map.get(paramName));
      }
    }
    catch (Exception ex) {
      log.error("下发短信时替换宏定义错误", ex);
    }
    return macro.getString(false);
  }

//  /**
//   * 根据铃音Code查询铃音信息,建立名值对
//   * @param ringBO RingBO
//   * @param ringCode String
//   * @return HashMap
//   */
//  public static HashMap queryRingByCode(RingBO ringBO,String ringCode)
//  {
//    HashMap ringMap = new HashMap();
//    try{
//      //订购铃音成功
//      List list = ringBO.queryRingsByRingCodeStr(ringCode);
//      Ring ring = null;
//      if(list != null && list.size() > 0)
//        ring = (Ring)list.get(0);
//      if(ring != null)
//      {
//        ringMap.put("toneName", ring.getRingName());
//        ringMap.put("price", String.valueOf(Double.parseDouble(ring.getRingPrice().toString()) / 100));
//        ringMap.put("toneValidDay", DateUtils.formatDate(ring.getUsefulLife(), "yyyyMMdd", "yyyy-M-d"));
//      }
//    } catch (Exception e) {
//      log.error("根据铃音Code查询铃音的详细信息时错误");
//    }
//    return ringMap;
//  }
//  /**
//   * 根据铃音组Code查询铃音组信息,建立名值对
//   * @param ringBO RingBO
//   * @param ringBoxCode String
//   * @return HashMap
//   */
//  public static HashMap queryRingBoxByRingBoxCode(RingBO ringBO,String ringBoxCode)
//  {
//    HashMap ringMap = new HashMap();
//    try{
//      //订购铃音成功
//      ToneBoxInfo toneBoxInfo = (ToneBoxInfo)ringBO.queryRingBoxByRingBoxCode(ringBoxCode);
//      if(toneBoxInfo!=null)
//      {
//        ringMap.put("feeType",toneBoxInfo.getFeeType());
//        ringMap.put("toneBoxName", toneBoxInfo.getToneBoxName());
//        ringMap.put("price", String.valueOf(Double.parseDouble(toneBoxInfo.getPrice()) / 100));
//        ringMap.put("toneValidDay", DateUtils.formatDate(toneBoxInfo.getValidDay(), "yyyy-MM-dd hh:mm:ss","yyyy-M-d"));
//      }
//    } catch (Exception e) {
//      log.error("根据铃音Code查询铃音的详细信息时错误");
//    }
//    return ringMap;
//  }

  public static void main(String[] args){
    List list = cutString("  a=1  b=2  "," ");
    System.out.println("list length is :"+list.size());
    for (int i=0;i<list.size();i++){
      System.out.println("list get["+(i+1)+"] is :("+list.get(i)+")");
    }
  }
}
