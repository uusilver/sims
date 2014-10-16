package com.xwtech.framework.pub.utils;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * 进行宏替换的类
 * <p>Title: Framework </p>
 *
 * <p>Description: Framework</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: xwtech.com</p>
 *
 * @author 杨永清
 * @version 1.0
 */
public class MacroString
{
  protected static final Logger logger = Logger.getLogger(MacroString.class);

  private String macroBegin;
  private String macroEnd;
  private HashMap macroMap;
  private ArrayList splitStringList;

  class Macro
  {
    String macroName;
    String macroValue;
  }

  /**
   * 构造宏字符串
   * @param initString String
   * @param macroBegin String
   * @param macroEnd String
   */
  public MacroString(String initString,String macroBegin,String macroEnd)
  {
    this.macroBegin = macroBegin;
    this.macroEnd = macroEnd;
    int macroBeginLength=macroBegin.length();
    int macroEndLength=macroEnd.length();

    int positionBegin,positionEnd;
    String str = initString;
    splitStringList = new ArrayList();
    macroMap = new HashMap();
    while(true)
    {
      positionBegin = str.indexOf(macroBegin);
      if (positionBegin==-1)
        break;
      positionEnd = str.indexOf(macroEnd, positionBegin);
      if (positionEnd==-1)
        break;
      int tempPosition = str.lastIndexOf(macroBegin, positionEnd);
      if (tempPosition!=-1)
        positionBegin = tempPosition;
      splitStringList.add(str.substring(0, positionBegin));
      Macro macro = new Macro();
      macro.macroName = str.substring(positionBegin + macroBeginLength, positionEnd);
      if (!macroMap.containsKey(macro.macroName))
        macroMap.put(macro.macroName,macro);
      else
        macro = (Macro)macroMap.get(macro.macroName);
      splitStringList.add(macro);

      str=str.substring(positionEnd + macroEndLength);
    }
    splitStringList.add(str);
  }

  /**
   * 设置宏值
   * @param macroName String
   * @param macroValue String
   * @return boolean
   */
  public boolean setMacroValue(String macroName,String macroValue)
  {
    Object obj = macroMap.get(macroName);
    if (obj != null)
    {
      Macro macro = (Macro)obj;
      macro.macroValue = macroValue;
      return true;
    }
    else
      return false;
  }

  /**
   * 获取宏替换后的字符串
   * @param reverseNullMacro boolean
   * @return String
   */
  public String getString(boolean reverseNullMacro)
  {
    StringBuffer sb = new StringBuffer();
    Object obj = null;
    for(int i=0;i<splitStringList.size();i++)
    {
      obj = splitStringList.get(i);
      if (obj instanceof String)
        sb.append(obj);
      if (obj instanceof Macro)
      {
        Macro macro = (Macro)obj;
        if (macro.macroValue==null)
        {
          if (reverseNullMacro)
            sb.append(macroBegin).append(macro.macroName).append(macroEnd);
        }
        else
          sb.append(macro.macroValue);
      }
    }
    return sb.toString();
  }


  public static void main(String[] args)
  {
    MacroString m = new MacroString("yyq#{test}#ccm#{test}#","#{","}#");

    m.setMacroValue("test","love");
    logger.info(m.getString(false));
//    logger.info("asdf where  \nAnd  adfsf where  ".replaceFirst("[Ww][Hh][Ee][Rr][Ee][ \t]*$",""));

  }

  public HashMap getMacroMap()
  {
    return macroMap;
  }
}
