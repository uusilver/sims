package com.xwtech.framework.pub.utils.simpleparser;

import java.util.HashMap;
import javax.servlet.ServletRequest;
import java.lang.reflect.Method;
import javax.servlet.http.HttpSession;

public class SimpleFunc
{
  private static HashMap funcList = new HashMap();

  static
  {
    funcList.put("~request_getParameter", new Integer(1));
    funcList.put("~request_getParameterValues", new Integer(1));
    funcList.put("~request_getAttribute", new Integer(2));
    funcList.put("~session_getAttribute", new Integer(2));

    funcList.put("isInStr", new Integer(2));
    funcList.put("isIn", new Integer(2));
    funcList.put("iif", new Integer(3));
    funcList.put("isNotEmpty", new Integer(2));
    funcList.put("toArrayString", new Integer(-1));
    funcList.put("toArrayInt", new Integer(-1));
  }

  /**
   * 取得函数信息列表
   * @return HashMap
   */
  public static HashMap getFuncList()
  {
    return funcList;
  }

  /**
   * 获取对象的方法
   * @param o Object
   * @param name String
   * @return Method
   */
  public static Method getMethod(Object o, String name)
    throws Exception
  {
    if(o == null)
      return null;

    if(name.length() > 1)
      return o.getClass().getMethod("get"
        + name.substring(0, 1).toUpperCase()
        + name.substring(1), new Class[0]);
    else
      return o.getClass().getMethod("get"
        + name.toUpperCase(), new Class[0]);
  }

  /**
   * 执行函数
   * @param op String
   * @param ops SimpleToken[]
   * @return SimpleToken
   * @throws Exception
   */
  public static SimpleToken execFunc(String op, SimpleToken[] ops, HashMap args)
    throws Exception
  {
    SimpleToken ret = new SimpleToken();

    if(op.equals("isNotEmpty"))
      return execIsNotEmpty(ops);
    else if(op.equals("toArrayString"))
      return execToArrayString(ops);
    else if(op.equals("toArrayInt"))
      return execToArrayInt(ops);
    else if(op.equals("iif"))
      return execIif(ops);
    else if(op.equals("isInStr"))
      return execIsInStr(ops);
    else if(op.equals("isIn"))
      return execIsIn(ops);
    else if(op.equals("~request_getParameter"))
      return execRequest_getParameter(ops, args);
    else if(op.equals("~request_getParameterValues"))
      return execRequest_getParameterValues(ops, args);
    else if(op.equals("~request_getAttribute"))
      return execRequest_getAttribute(ops, args);
    else if(op.equals("~session_getAttribute"))
      return execSession_getAttribute(ops, args);

    throw new Exception("函数\"" + op + "\"尚未支持");
  }

  /**
   * iif函数例程
   * Object iif(boolean expr, Object truepart, Object falsepart)
   * @param ops SimpleToken[]
   * @return SimpleToken
   */
  private static SimpleToken execIif(SimpleToken[] ops)
    throws Exception
  {
    if(!(ops[0].isTypeBoolean() && !ops[0].isArray()))
      throw new Exception("函数iif第一个参数期待布尔型值");

    if(ops[0].getBooleanValue())
      return ops[1];
    else
      return ops[2];
  }

  /**
   * request.getAttribute例程
   * Object request.getAttribute(String name).fieldName.fieldName...
   * @param ops SimpleToken[]
   * @param args HashMap
   * @return SimpleToken
   * @throws Exception
   */
  private static SimpleToken execRequest_getAttribute(SimpleToken[] ops, HashMap args)
    throws Exception
  {
    if(!(ops[0].isTypeString() && !ops[0].isArray()))
      throw new Exception("函数request.getAttribute第一个参数期待字符串型值");

    Object o = args.get("request");
    if(o == null)
      throw new Exception("期待request对象实例");
    if(!(o instanceof ServletRequest))
      throw new Exception("传入的request对象不是ServletRequest的实例");

    ServletRequest req = (ServletRequest)o;
    o = req.getAttribute(ops[0].toString());
    //没有属性
    if(o == null)
     return new SimpleToken(null);
    //仅返回该属性
    else if(ops[1].toString().length() == 0)
      return new SimpleToken(o);
    //查找该属性的嵌套字段
    else
    {
      Method method;
      String[] flds = ops[1].toString().split("\\.");
      for(int i = 0; i < flds.length; i++)
      {
        method = getMethod(o, flds[i]);
        o = method.invoke(o, new Object[0]);

        if(o == null)
          break;
      }

      return new SimpleToken(o);
    }
  }

  /**
   * request.getParameter例程
   * String request.getParameter(String name)
   * @param ops SimpleToken[]
   * @param args HashMap
   * @return SimpleToken
   * @throws Exception
   */
  private static SimpleToken execRequest_getParameter(SimpleToken[] ops, HashMap args)
    throws Exception
  {
    if(!(ops[0].isTypeString() && !ops[0].isArray()))
      throw new Exception("函数request.getAttribute第一个参数期待字符串型值");

    Object o = args.get("request");
    if(o == null)
      throw new Exception("期待request对象实例");
    if(!(o instanceof ServletRequest))
      throw new Exception("传入的request对象不是ServletRequest的实例");

    ServletRequest req = (ServletRequest)o;
    return new SimpleToken(req.getParameter(ops[0].getStringValue()));
  }

  /**
   * request.getParameterValues例程
   * String[] request.getParameterValues(String name)
   * @param ops SimpleToken[]
   * @param args HashMap
   * @return SimpleToken
   * @throws Exception
   */
  private static SimpleToken execRequest_getParameterValues(SimpleToken[] ops, HashMap args)
    throws Exception
  {
    if(!(ops[0].isTypeString() && !ops[0].isArray()))
      throw new Exception("函数request.getParameterValues第一个参数期待字符串型值");

    Object o = args.get("request");
    if(o == null)
      throw new Exception("期待request对象实例");
    if(!(o instanceof ServletRequest))
      throw new Exception("传入的request对象不是ServletRequest的实例");

    ServletRequest req = (ServletRequest)o;
    return new SimpleToken(req.getParameterValues(ops[0].getStringValue()));
  }

  /**
   * session.getAttribute例程
   * Object session.getAttribute(String name).fieldName.fieldName...
   * @param ops SimpleToken[]
   * @param args HashMap
   * @return SimpleToken
   * @throws Exception
   */
  private static SimpleToken execSession_getAttribute(SimpleToken[] ops, HashMap args)
    throws Exception
  {
    if(!(ops[0].isTypeString() && !ops[0].isArray()))
      throw new Exception("函数session.getAttribute第一个参数期待字符串型值");

    Object o = args.get("session");
    if(o == null)
      throw new Exception("期待session对象实例");
    if(!(o instanceof HttpSession))
      throw new Exception("传入的session对象不是HttpSession的实例");

    HttpSession session = (HttpSession)o;
    o = session.getAttribute(ops[0].toString());
    //没有属性
    if(o == null)
     return new SimpleToken(null);
    //仅返回该属性
    else if(ops[1].toString().length() == 0)
      return new SimpleToken(o);
    //查找该属性的嵌套字段
    else
    {
      Method method;
      String[] flds = ops[1].toString().split("\\.");
      for(int i = 0; i < flds.length; i++)
      {
        method = getMethod(o, flds[i]);
        o = method.invoke(o, new Object[0]);

        if(o == null)
          break;
      }

      return new SimpleToken(o);
    }
  }

  /**
   * 是否一个字符串出现在另一个字符串中
   * boolean isInStr(String string, String match)
   * @param ops SimpleToken[]
   * @return SimpleToken
   * @throws Exception
   */
  private static SimpleToken execIsInStr(SimpleToken[] ops)
    throws Exception
  {
    if(!ops[0].isArray() && ops[0].isTypeString()
      && !ops[1].isArray() && ops[1].isTypeString())
    {
      return new SimpleToken(ops[0].getStringValue().indexOf(ops[1].getStringValue()) >= 0);
    }

    throw new Exception("isInStr函数未期待的参数类型:" + ops[0].getTypeName() + "," + ops[1].getTypeName());
  }

  /**
   * 指定的对象是否出现在指定的数组中
   * boolean isIn(Object[] array, Object find)
   * @param ops SimpleToken[]
   * @return SimpleToken
   * @throws Exception
   */
  private static SimpleToken execIsIn(SimpleToken[] ops)
    throws Exception
  {
    if(ops[0].isNull())
      return new SimpleToken(false);

    if(!ops[0].isArray())
      throw new Exception("isIn第一个参数类型错误,期待数组型参数");

    Object[] array = (Object[])ops[0].getObjectValue();
    for (int i = 0; i < array.length; i++)
    {
      if (ops[1].isNull())
      {
        if(array[i] == null)
          return new SimpleToken(true);
      }
      else if (array[i] != null)
      {
        if (array[i].equals(ops[1].getObjectValue()))
          return new SimpleToken(true);
      }
    }

    return new SimpleToken(false);
  }

  private static SimpleToken execIsNotEmpty(SimpleToken[] ops)
      throws Exception
  {
    if(ops[0].isNull())
      return new SimpleToken("");
    else if(!ops[0].isArray() && ops[0].isTypeString() && ops[0].getStringValue().equals(""))
      return new SimpleToken("");
    else
      return ops[1];
  }

  private static SimpleToken execToArrayString(SimpleToken[] ops)
      throws Exception
  {
    //数组为null
    if(ops[0].isNull())
      return new SimpleToken("");

    //参数必须为字符串数组
    if(!ops[0].isArray() || !ops[0].isTypeString())
      throw new Exception("函数toArrayString第一个参数期待字符串数组,非" + ops[0].getTypeName());

    String sep = ",";
    //获取分隔符
    if(ops.length == 2)
    {
      if(!ops[1].isArray() && ops[1].isTypeString())
        sep = ops[1].getStringValue();
      else
        throw new Exception("函数toArrayString第二个参数期待字符串,非" + ops[1].getTypeName());
    }

    StringBuffer sb = new StringBuffer();
    String[] arr = (String[])ops[0].getObjectValue();
    for(int i=0;i<arr.length;i++)
    {
      if(arr[i] != null && arr[i].length() > 0)
      {
        if(sb.length() > 0)
          sb.append(sep);
        sb.append("'");
        sb.append(arr[i].replaceAll("\'","\'\'"));
        sb.append("'");
      }
    }

    return new SimpleToken(sb.toString());
  }

  private static SimpleToken execToArrayInt(SimpleToken[] ops)
      throws Exception
  {
    //数组为null
    if(ops[0].isNull())
      return new SimpleToken("");

    //参数必须为字符串数组
    if(!ops[0].isArray() || !ops[0].isTypeString())
      throw new Exception("函数toArrayInt第一个参数期待字符串数组,非" + ops[0].getTypeName());

    String sep = ",";
    //获取分隔符
    if(ops.length == 2)
    {
      if(!ops[1].isArray() && ops[1].isTypeString())
        sep = ops[1].getStringValue();
      else
        throw new Exception("函数toArrayInt第二个参数期待字符串,非" + ops[1].getTypeName());
    }

    StringBuffer sb = new StringBuffer();
    String[] arr = (String[])ops[0].getObjectValue();
    for(int i=0;i<arr.length;i++)
    {
      if(arr[i] != null && arr[i].length() > 0)
      {
        if(sb.length() > 0)
          sb.append(sep);
        sb.append(arr[i]);
      }
    }

    return new SimpleToken(sb.toString());
  }
}
