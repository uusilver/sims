package com.xwtech.framework.pub.utils.simpleparser;

public class SimpleToken
{
  //单词的基本类型
  public static final int TK_TYPE_NOUSE = 0; //未使用
  public static final int TK_TYPE_STRING = 1; //字符串
  public static final int TK_TYPE_LONG = 2; //整型
  public static final int TK_TYPE_DOUBLE = 3; //浮点型
  public static final int TK_TYPE_BOOLEAN = 4; //布尔型
  public static final int TK_TYPE_OBJECT = 5; //Object
  public static final int TK_TYPE_FUNCTION = 6; //函数
  public static final int TK_TYPE_SYMBOL = 7; //符号
  public static final int TK_TYPE_VARIABLE = 8; //变量

  public static final String[] tk_type_names = new String[]{"未使用","String","long","float","boolean",
                                               "Object","函数型","符号型","变量型"};

  //优先级定义
  public static final int PRI_FUNCTION = 160; //函数
  public static final int PRI_DOT = 150; //.
  public static final int PRI_LOGIC_NOT = 140; // !
  public static final int PRI_BIT_NOT = 140; // ~
  public static final int PRI_ARITH_MUL = 120; // *
  public static final int PRI_ARITH_DIV = 120; // /
  public static final int PRI_ARITH_MOD = 120; // %
  public static final int PRI_ARITH_ADD = 110; // +
  public static final int PRI_ARITH_SUB = 110; // -
  public static final int PRI_LOGIC_MOREEQUAL = 90; //	>=
  public static final int PRI_LOGIC_LESSEQUAL = 90; //	<=
  public static final int PRI_LOGIC_MORE = 90; //	>
  public static final int PRI_LOGIC_LESS = 90; //	<
  public static final int PRI_LOGIC_EQUAL = 80; // ==
  public static final int PRI_LOGIC_NOTEQUAL = 80; // !=
  public static final int PRI_BIT_AND = 70; //&
  public static final int PRI_BIT_XOR = 60; //^
  public static final int PRI_BIT_OR = 50; //|
  public static final int PRI_LOGIC_AND = 40; //&&
  public static final int PRI_LOGIC_OR = 30; //||
  public static final int PRI_COMMA = 1; //,

  //符号类型定义
  public static final int SYMBOL_TYPE_COMMA = 1;//,
  public static final int SYMBOL_TYPE_DOT = 2;//.
  public static final int SYMBOL_TYPE_LEFT_PARENTHESIS = 3;//(
  public static final int SYMBOL_TYPE_RIGHT_PARENTHESIS = 4;//)
  public static final int SYMBOL_TYPE_LOGIC_OR = 11;//||
  public static final int SYMBOL_TYPE_LOGIC_AND = 12;//&&
  public static final int SYMBOL_TYPE_LOGIC_NOT = 13;//!
  public static final int SYMBOL_TYPE_LOGIC_EQUAL = 14;//==
  public static final int SYMBOL_TYPE_LOGIC_NOTEQUAL = 15;//!=
  public static final int SYMBOL_TYPE_LOGIC_MOREEQUAL = 16;//>=
  public static final int SYMBOL_TYPE_LOGIC_MORE = 17;//>
  public static final int SYMBOL_TYPE_LOGIC_LESSEQUAL = 18;//<=
  public static final int SYMBOL_TYPE_LOGIC_LESS = 19;//<
  public static final int SYMBOL_TYPE_BIT_AND = 31;//&
  public static final int SYMBOL_TYPE_BIT_NOT = 32;//~,一元操作符
  public static final int SYMBOL_TYPE_BIT_XOR = 33;//^
  public static final int SYMBOL_TYPE_BIT_OR = 34;//|
  public static final int SYMBOL_TYPE_ARITH_MUL = 41;//*
  public static final int SYMBOL_TYPE_ARITH_DIV = 42;///
  public static final int SYMBOL_TYPE_ARITH_MOD = 43;//%
  public static final int SYMBOL_TYPE_ARITH_ADD = 44;//+，一(二)元操作符
  public static final int SYMBOL_TYPE_ARITH_SUB = 45;//-，一(二)元操作符

  public static int[] symPriList = new int[50];//symbol的优先级列表
  public static int[] symArgsList = new int[50];//symbol的参数个数列表
  public static String[] symTextList = new String[50];//symbol的表示文字

  static
  {
    //","
    symPriList[SYMBOL_TYPE_COMMA] = PRI_COMMA;
    symArgsList[SYMBOL_TYPE_COMMA] = 0;
    symTextList[SYMBOL_TYPE_COMMA] = ",";
    //"."
    symPriList[SYMBOL_TYPE_DOT] = PRI_DOT;
    symArgsList[SYMBOL_TYPE_DOT] = 2;
    symTextList[SYMBOL_TYPE_DOT] = ".";
    //"("
    symTextList[SYMBOL_TYPE_LEFT_PARENTHESIS] = "(";
    //")"
    symTextList[SYMBOL_TYPE_RIGHT_PARENTHESIS] = ")";
    //"||"
    symPriList[SYMBOL_TYPE_LOGIC_OR] = PRI_LOGIC_OR;
    symArgsList[SYMBOL_TYPE_LOGIC_OR] = 2;
    symTextList[SYMBOL_TYPE_LOGIC_OR] = "||";
    //"&&"
    symPriList[SYMBOL_TYPE_LOGIC_AND] = PRI_LOGIC_AND;
    symArgsList[SYMBOL_TYPE_LOGIC_AND] = 2;
    symTextList[SYMBOL_TYPE_LOGIC_AND] = "&&";
    //"!"
    symPriList[SYMBOL_TYPE_LOGIC_NOT] = PRI_LOGIC_NOT;
    symArgsList[SYMBOL_TYPE_LOGIC_NOT] = 1;
    symTextList[SYMBOL_TYPE_LOGIC_NOT] = "!";
    //"=="
    symPriList[SYMBOL_TYPE_LOGIC_EQUAL] = PRI_LOGIC_EQUAL;
    symArgsList[SYMBOL_TYPE_LOGIC_EQUAL] = 2;
    symTextList[SYMBOL_TYPE_LOGIC_EQUAL] = "==";
    //"!="
    symPriList[SYMBOL_TYPE_LOGIC_NOTEQUAL] = PRI_LOGIC_NOTEQUAL;
    symArgsList[SYMBOL_TYPE_LOGIC_NOTEQUAL] = 2;
    symTextList[SYMBOL_TYPE_LOGIC_NOTEQUAL] = "!=";
    //">="
    symPriList[SYMBOL_TYPE_LOGIC_MOREEQUAL] = PRI_LOGIC_MOREEQUAL;
    symArgsList[SYMBOL_TYPE_LOGIC_MOREEQUAL] = 2;
    symTextList[SYMBOL_TYPE_LOGIC_MOREEQUAL] = ">=";
    //">"
    symPriList[SYMBOL_TYPE_LOGIC_MORE] = PRI_LOGIC_MORE;
    symArgsList[SYMBOL_TYPE_LOGIC_MORE] = 2;
    symTextList[SYMBOL_TYPE_LOGIC_MORE] = ">";
    //"<="
    symPriList[SYMBOL_TYPE_LOGIC_LESSEQUAL] = PRI_LOGIC_LESSEQUAL;
    symArgsList[SYMBOL_TYPE_LOGIC_LESSEQUAL] = 2;
    symTextList[SYMBOL_TYPE_LOGIC_LESSEQUAL] = "<=";
    //"<"
    symPriList[SYMBOL_TYPE_LOGIC_LESS] = PRI_LOGIC_LESS;
    symArgsList[SYMBOL_TYPE_LOGIC_LESS] = 2;
    symTextList[SYMBOL_TYPE_LOGIC_LESS] = "<";
    //"&"
    symPriList[SYMBOL_TYPE_BIT_AND] = PRI_BIT_AND;
    symArgsList[SYMBOL_TYPE_BIT_AND] = 2;
    symTextList[SYMBOL_TYPE_BIT_AND] = "&";
    //"~"
    symPriList[SYMBOL_TYPE_BIT_NOT] = PRI_BIT_NOT;
    symArgsList[SYMBOL_TYPE_BIT_NOT] = 1;
    symTextList[SYMBOL_TYPE_BIT_NOT] = "~";
    //"^"
    symPriList[SYMBOL_TYPE_BIT_XOR] = PRI_BIT_XOR;
    symArgsList[SYMBOL_TYPE_BIT_XOR] = 2;
    symTextList[SYMBOL_TYPE_BIT_XOR] = "^";
    //"|"
    symPriList[SYMBOL_TYPE_BIT_OR] = PRI_BIT_OR;
    symArgsList[SYMBOL_TYPE_BIT_OR] = 2;
    symTextList[SYMBOL_TYPE_BIT_OR] = "|";
    //"*"
    symPriList[SYMBOL_TYPE_ARITH_MUL] = PRI_ARITH_MUL;
    symArgsList[SYMBOL_TYPE_ARITH_MUL] = 2;
    symTextList[SYMBOL_TYPE_ARITH_MUL] = "*";
    //"/"
    symPriList[SYMBOL_TYPE_ARITH_DIV] = PRI_ARITH_DIV;
    symArgsList[SYMBOL_TYPE_ARITH_DIV] = 2;
    symTextList[SYMBOL_TYPE_ARITH_DIV] = "/";
    //"%"
    symPriList[SYMBOL_TYPE_ARITH_MOD] = PRI_ARITH_MOD;
    symArgsList[SYMBOL_TYPE_ARITH_MOD] = 2;
    symTextList[SYMBOL_TYPE_ARITH_MOD] = "%";
    //"+"
    symPriList[SYMBOL_TYPE_ARITH_ADD] = PRI_ARITH_ADD;
    symArgsList[SYMBOL_TYPE_ARITH_ADD] = 2;
    symTextList[SYMBOL_TYPE_ARITH_ADD] = "+";
    //"-"
    symPriList[SYMBOL_TYPE_ARITH_SUB] = PRI_ARITH_SUB;
    symArgsList[SYMBOL_TYPE_ARITH_SUB] = 2;
    symTextList[SYMBOL_TYPE_ARITH_SUB] = "-";
  }

  private int type; //基本单词类型,TK_TYPE_*
  private boolean bArray; //是否为数组类型
  private int priority; //函数优先级
  private int symbolType; //符号类型

  //对应的值
  private boolean booleanValue; //布尔值
  private long longValue; //整型,参数个数
  private double doubleValue; //浮点型
  private Object objectValue; //Object值,字符串值,null值,数组,函数名,变量名

  public SimpleToken()
  {
    this.type = TK_TYPE_NOUSE;
  }

  public SimpleToken(double value)
  {
    setValueToken(value);
  }

  public SimpleToken(long value)
  {
    setValueToken(value);
  }

  public SimpleToken(boolean value)
  {
    setValueToken(value);
  }

  public SimpleToken(String value)
  {
    setValueToken(value);
  }

  public SimpleToken(Object value)
  {
    setValueToken(value);
  }

  /**
   * 获取单词的基本类型
   * @return int
   */
  public int getType()
  {
    return this.type;
  }

  /**
   * 获取符号类型
   * @return int
   */
  public int getSymbolType()
  {
    return this.symbolType;
  }

  /**
   * 获取单词的基本类型名称
   * @return String
   */
  public String getTypeName()
  {
    //非数组
    if(!this.bArray)
      return this.tk_type_names[this.type];
    //数组
    else
      return this.tk_type_names[this.type] + "[]";
  }

  /**
   * 是否为数组类型
   * @return boolean
   */
  public boolean isArray()
  {
    return this.bArray;
  }

  /**
   * 是否为布尔型
   * @return boolean
   */
  public boolean isTypeBoolean()
  {
    return this.type == TK_TYPE_BOOLEAN;
  }

  /**
   * 是否为整型
   * @return boolean
   */
  public boolean isTypeLong()
  {
    return this.type == TK_TYPE_LONG;
  }

  /**
   * 是否为数值型
   * @return boolean
   */
  public boolean isTypeNumber()
  {
    return this.type == TK_TYPE_LONG || this.type == TK_TYPE_DOUBLE;
  }

  /**
   * 是否为字符串型
   * @return boolean
   */
  public boolean isTypeString()
  {
    return this.type == TK_TYPE_STRING;
  }

  /**
   * 是否为Object型
   * @return boolean
   */
  public boolean isTypeObject()
  {
    return this.type == TK_TYPE_OBJECT;
  }

  /**
   * 是否为浮点型
   * @return boolean
   */
  public boolean isTypeDouble()
  {
    return this.type == TK_TYPE_DOUBLE;
  }

  /**
   * 是否为null值
   * @return boolean
   */
  public boolean isNull()
  {
    if(this.type == TK_TYPE_OBJECT && this.objectValue == null)
      return true;
    else
      return false;
  }

  /**
   * 获取布尔型值
   * @return boolean
   */
  public boolean getBooleanValue()
  {
    if(this.type == TK_TYPE_BOOLEAN)
      return this.booleanValue;
    else if(this.type == TK_TYPE_LONG)
      return this.longValue == 0 ? false : true;
    else
      return false;
  }

  /**
   * 获取整型值
   * @return int
   */
  public long getLongValue()
  {
    if(this.type == TK_TYPE_LONG)
      return this.longValue;
    else if(this.type == TK_TYPE_DOUBLE)
      return (long)this.doubleValue;
    else
      return 0;
  }

  /**
   * 获取浮点型值
   * @return double
   */
  public double getDoubleValue()
  {
    if(this.type == TK_TYPE_DOUBLE)
      return this.doubleValue;
    else if(this.type == TK_TYPE_LONG)
      return this.longValue;
    else
      return 0;
  }

  /**
   * 获取对象型值
   * @return Object
   */
  public Object getObjectValue()
  {
    if(this.type == TK_TYPE_OBJECT || this.type == TK_TYPE_STRING)
      return this.objectValue;
    else if(this.type == TK_TYPE_LONG)
      return new Long(this.longValue);
    else if(this.type == TK_TYPE_DOUBLE)
      return new Double(this.doubleValue);
    else if(this.type == TK_TYPE_BOOLEAN)
      return new Boolean(this.booleanValue);
    else
      return null;
  }

  /**
   * 获取字符串型值
   * @return String
   */
  public String getStringValue()
  {
    return toString();
  }

  /**
   * 根据值设置浮点型token
   * @param value double
   */
  public void setValueToken(double value)
  {
    this.bArray = false;
    this.type = TK_TYPE_DOUBLE;
    this.doubleValue = value;
  }

  /**
   * 根据值设置整型token
   * @param value long 整型值
   */
  public void setValueToken(long value)
  {
    this.bArray = false;
    this.type = TK_TYPE_LONG;
    this.longValue = value;
  }

  /**
   * 根据值设置字符串型token
   * @param value String
   */
  public void setValueToken(String value)
  {
    this.bArray = false;
    this.objectValue = value;

    if(value == null)
      this.type = TK_TYPE_OBJECT;
    else
      this.type = TK_TYPE_STRING;
  }

  /**
   * 根据值设置布尔型token
   * @param value boolean
   */
  public void setValueToken(boolean value)
  {
    this.bArray = false;
    this.type = TK_TYPE_BOOLEAN;
    this.booleanValue = value;
  }

  /**
   * 根据object值设置token
   * @param value Object
   */
  public void setValueToken(Object value)
  {
    //null
    if(value == null)
    {
      this.bArray = false;
      this.type = TK_TYPE_OBJECT;
      this.objectValue = null;
    }
    //数组或对象
    else
    {
      //元素类型
      Class classType = value.getClass();

      this.bArray = classType.isArray();
      if(this.bArray)
      {
        this.objectValue = value;
        classType = value.getClass().getComponentType();
      }

      //字符串型
      if(classType.equals(String.class))
      {
        if(!this.bArray)
          this.objectValue = value;
        this.type = TK_TYPE_STRING;
      }
      //整型
      else if(classType.equals(Long.class) || classType.equals(Integer.class))
      {
        if(!this.bArray)
          this.longValue = ((Number)value).longValue();
        this.type = TK_TYPE_LONG;
      }
      //浮点型
      else if(classType.equals(Double.class) || classType.equals(Float.class))
      {
        if(!this.bArray)
          this.doubleValue = ((Number)value).doubleValue();
        this.type = TK_TYPE_DOUBLE;
      }
      //布尔型
      else if(classType.equals(Boolean.class))
      {
        if(!this.bArray)
          this.booleanValue = ((Boolean)value).booleanValue();
        this.type = TK_TYPE_BOOLEAN;
      }
      //对象类型
      else
      {
        if(!this.bArray)
          this.objectValue = value;
        this.type = TK_TYPE_OBJECT;
      }
    }
  }

  /**
   * 设置函数token
   * @param name String 函数名
   * @param argsCount int 参数个数
   */
  public void setFuncToken(String name, int argsCount)
  {
    this.bArray = false;
    this.type = TK_TYPE_FUNCTION;
    this.objectValue = name;
    this.longValue = argsCount;
    this.priority = PRI_FUNCTION;
  }

  /**
   * 设置变量token
   * @param name String
   */
  public void setVarToken(String name)
  {
    this.bArray = false;
    this.type = TK_TYPE_VARIABLE;
    this.objectValue = name;
  }

  /**
   * 设置符号token
   * @param type int
   */
  public void setSymbolToken(int type)
  {
    this.bArray = false;
    this.type = TK_TYPE_SYMBOL;
    this.symbolType = type;
    this.objectValue = symTextList[type];
    this.longValue = symArgsList[type];
    this.priority = symPriList[type];
  }

  /**
   * 设置参数个数
   * @param argsCount int
   */
  public void setArgsCount(int argsCount)
  {
    this.longValue = argsCount;
  }

  /**
   * 获取参数个数
   * @return int
   */
  public int getArgsCount()
  {
    return (int)this.longValue;
  }

  /**
   * 获取优先级
   * @return int
   */
  public int getPriority()
  {
    return this.priority;
  }

  /**
   * 字符串描述
   * @return String
   */
  public String toString()
  {
    if(this.isArray())
      return "<array>";

    switch(this.type)
    {
      case TK_TYPE_BOOLEAN:
        return this.booleanValue?"true":"false";
      case TK_TYPE_DOUBLE:
        return String.valueOf(this.doubleValue);
      case TK_TYPE_FUNCTION:
        return this.objectValue.toString();
      case TK_TYPE_LONG:
        return String.valueOf(this.longValue);
      case TK_TYPE_OBJECT:
        return this.objectValue == null ? "null" : this.objectValue.toString();
      case TK_TYPE_STRING:
        return this.objectValue.toString();
      case TK_TYPE_SYMBOL:
        return this.objectValue.toString();
      case TK_TYPE_VARIABLE:
        return this.objectValue.toString();
    }

    return null;
  }
}
