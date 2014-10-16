package com.xwtech.framework.pub.utils;

import java.beans.PropertyDescriptor;
import java.beans.BeanInfo;
import java.beans.Introspector;
import org.apache.log4j.Logger;
import java.lang.reflect.*;
import java.util.HashMap;

public class HibernateObjUtils
{
  protected static final Logger log = Logger.getLogger(HibernateObjUtils.class);
  private String hSql;
  private HashMap valueMap = new HashMap();

  public HibernateObjUtils(Object obj)
  {
    formatHSQL(obj);
  }

  //根据传输的数据不完整的对象组成hsql以便于执行
  private void formatHSQL(Object obj)
  {
    StringBuffer buf = new StringBuffer();
    String mainTableKeyStr = obj.toString();
    try{
      PropertyDescriptor[] origDescriptors = getPropertyDescriptors(obj.getClass());
      //获取主键关键字字段名称，以及字段赋值
      mainTableKeyStr = mainTableKeyStr.substring(mainTableKeyStr.indexOf("[") + 1, mainTableKeyStr.indexOf("]"));
      String mainTableKey = mainTableKeyStr.substring(0, mainTableKeyStr.indexOf("="));  //获取主表关键字字段名称

      String subTableKeyStr = "";
      String subTableKey = "";
      Object value = null;
      String name = "";
      String fieldType = "";
      for(int i = 0; i < origDescriptors.length; i++)
      {
        name = origDescriptors[i].getName();
        fieldType = origDescriptors[i].getPropertyType().toString();
        if(name.equals("class")||mainTableKey.equals(name)||fieldType.indexOf("java.util.Set")>=0)
          continue;
        value = obj.getClass().getMethod("get"+capitalizePropertyName(name),null).invoke(obj,null);
        //如果未定义值或者当前字段是关键字，则继续下一个检查

        if(value==null)
          continue;
        //如果是Integer、Long、Float、Double型且为数据类型的最大值，则将其该字段值更新成null
        //此举目的是为了解决界面上用户将价格等元素的值改成空元素的

        if ((fieldType.indexOf("java.lang.Integer")>=0&&((Integer)value).intValue() == Integer.MAX_VALUE)
            ||(fieldType.indexOf("java.lang.Long")>=0&&((Long)value).longValue()==Long.MAX_VALUE)
            ||(fieldType.indexOf("java.lang.Float")>=0&&((Float)value).floatValue()==Float.MAX_VALUE)
            ||(fieldType.indexOf("java.lang.Double")>=0&&((Double)value).doubleValue()==Double.MAX_VALUE))
        {
          buf.append(name + "=:" + name + ",");
          valueMap.put(name, null);
        }
        else if (fieldType.indexOf("java.lang.String")>=0||fieldType.indexOf("java.lang.Integer")>=0||fieldType.indexOf("java.lang.Long")>=0||fieldType.indexOf("java.lang.Float")>=0||fieldType.indexOf("java.lang.Double")>=0)
        {
          buf.append(name + "=:" + name + ",");
          valueMap.put(name, value);
        }
        else
        {
          //获取子对象的关键字
          subTableKeyStr = value.toString();
          subTableKeyStr = subTableKeyStr.substring(subTableKeyStr.indexOf("[") + 1, subTableKeyStr.indexOf("]"));
          subTableKey = subTableKeyStr.substring(0, subTableKeyStr.indexOf("="));
          Object subValue = value.getClass().getMethod("get" + capitalizePropertyName(subTableKey),null).invoke(value,null);
          //如果子对象的主键值为空，则将此对象值置为空 ，否则给对象置值
          if (subValue==null)
           buf.append(name + "=null,");
          else
          {
            buf.append(name + "=:" + name + ",");
            valueMap.put(name, subValue);
          }
        }
      }
    }catch(Exception e){
      log.error("根据对象"+mainTableKeyStr+"格式化HSQL时出错",e);
      e.printStackTrace();
      buf = new StringBuffer();
    }
    //组成hsql
    String queryConditionStr = buf.toString();
    if(queryConditionStr.length() > 0)
    {
      queryConditionStr = queryConditionStr.substring(0, queryConditionStr.length() - 1);
      queryConditionStr = "update " + obj.getClass().getName() + " set " + queryConditionStr + " where " + mainTableKeyStr;
    }
    setHSql(queryConditionStr);
  }
  //首字母转成大写
  private String capitalizePropertyName(String s) {
    if (s.length() == 0) {
      return s;
    }
    char chars[] = s.toCharArray();
    chars[0] = Character.toUpperCase(chars[0]);
    return new String(chars);
  }
  //获取bean下的属性
  private PropertyDescriptor[] getPropertyDescriptors(Class beanClass) throws Exception{
    BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
    PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
    return (descriptors);
  }
  public static void main(String[] args){
  }
  public String getHSql() {
    return hSql;
  }
  public void setHSql(String hSql) {
    this.hSql = hSql;
  }
  public java.util.HashMap getValueMap() {
    return valueMap;
  }
  public void setValueMap(java.util.HashMap valueMap) {
    this.valueMap = valueMap;
  }
}
