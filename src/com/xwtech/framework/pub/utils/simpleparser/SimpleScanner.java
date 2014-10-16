package com.xwtech.framework.pub.utils.simpleparser;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.Vector;

public class SimpleScanner
{
  private String code; //代码
  private int pos; //指针位置

  /**
   * 构造函数
   * @param code String
   */
  public SimpleScanner(String code)
  {
    this.code = "\n" + code + "\n\n\n\0\0\0";
    this.pos = 0;
  }

  /**
   * 是否为代码结束
   * @return boolean
   */
  public boolean isEnd()
  {
    return this.code.charAt(this.pos) == '\0';
  }

  /**
   * 获取当前指针位置
   * @return int
   */
  public int getPos()
  {
    return this.pos;
  }

  /**
   * 获取下一个字符
   * @return char
   */
  public char getNextChar()
  {
    return this.code.charAt(this.pos + 1);
  }

  /**
   * 扫描上一个字符
   * @return char
   */
  public char seekPrev()
  {
    this.pos --;
    return this.code.charAt(this.pos);
  }

  /**
   * 扫描下一个字符
   * @return char
   */
  public char seekNext()
  {
    this.pos ++;
    return this.code.charAt(this.pos);
  }

  /**
   * 跳过空白字符
   */
  public void skipBlank()
  {
    while(!isEnd() && getNextChar() <= ' ')
    {
      seekNext();
    }
  }

  /**
   * 扫描整数或浮点数
   * @return Number
   * @throws Exception
   */
  public Number scanNumber()
    throws Exception
  {
    DecimalFormat df = new DecimalFormat();
    df.setGroupingUsed(false);
    ParsePosition pp = new ParsePosition(this.pos + 1);
    Number num = df.parse(this.code,pp);

    if(num == null)
      throw new Exception("无效的数值,位置:" + pp.getErrorIndex());

    if((num instanceof Integer) || (num instanceof Long))
    {
      if(this.code.substring(this.pos + 1, pp.getIndex()).indexOf('.') != -1)
        num = new Double(num.doubleValue());
    }

    this.pos = pp.getIndex() - 1;
    return num;
  }

  /**
   * 扫描字符串
   * @return String
   * @throws Exception
   */
  public String scanString()
    throws Exception
  {
    StringBuffer buf = new StringBuffer();

    char c,c1 = '\0';

    c = seekNext();
    int begin = getPos();

    while(!isEnd())
    {
      c1 = seekNext();

      if(c1 == c)
        break;

      if(c1 == '\\')
      {
        c1 = seekNext();
        switch(c1)
        {
          case '\\':
            c1 = '\\';
            break;
          case '\'':
            c1 = '\'';
            break;
          case '\"':
            c1 = '\"';
            break;
          case 'r':
            c1 = '\r';
            break;
          case 'n':
            c1 = '\n';
            break;
          case 't':
            c1 = '\t';
            break;
        }
      }

      buf.append(c1);
    }

    if(c != c1)
    {
      throw new Exception("未结束的字符串,起始位置:" + begin);
    }

    return buf.toString();
  }

  /**
   * 扫描token
   * @return String
   * @throws Exception
   */
  public String scanToken()
    throws Exception
  {
    StringBuffer buf = new StringBuffer();

    char c;

    //检查第一个字符
    c = getNextChar();
    if(!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_'))
      return null;

    while(!isEnd())
    {
      c = seekNext();

      if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_')
        buf.append(c);
      else
      {
        seekPrev();
        break;
      }
    }

    return buf.toString();
  }

  /**
   * 扫描request对象的方法
   * @return String
   * @throws Exception
   */
  public Vector scanRequest()
    throws Exception
  {
    Vector words = new Vector();
    SimpleToken token;

    //扫描点号
    char c = seekNext();

    //获取方法名
    String methodName = scanToken();

    //request获取参数方法
    if(methodName.equals("getParameter") || methodName.equals("getParameterValues"))
    {
      //添加复合函数token
      token = new SimpleToken();
      token.setFuncToken("~request_" + methodName,1);
      words.add(token);
      return words;
    }
    //request获取属性方法
    else if(methodName.equals("getAttribute"))
    {
      String attrName,fldName = "",text;

      //跳过空行
      skipBlank();
      //期待左括号
      if(seekNext() != '(')
        throw new Exception("期待左括号,位置:" + this.pos);
      //跳过空行
      skipBlank();
      //获取字符串参数
      if(getNextChar() != '\'' && getNextChar() != '\"')
        throw new Exception("期待字符串参数,位置:" + this.pos);
      attrName = scanString();
      //跳过空行
      skipBlank();
      //期待右括号
      if(seekNext() != ')')
        throw new Exception("期待右括号,位置:" + this.pos);
      //扫描字段
      while(getNextChar() == '.')
      {
        seekNext();
        //获取字段
        text = scanToken();
        if(text == null)
          throw new Exception("期待属性名,位置:" + this.pos);

        if(fldName.length() > 0)
          fldName += ".";
        fldName += text;
      }
      //转换成内部形式
      token = new SimpleToken();
      token.setFuncToken("~request_getAttribute",2);
      words.add(token);

      token = new SimpleToken();
      token.setSymbolToken(SimpleToken.SYMBOL_TYPE_LEFT_PARENTHESIS);
      words.add(token);

      token = new SimpleToken();
      token.setValueToken(attrName);
      words.add(token);

      token = new SimpleToken();
      token.setSymbolToken(SimpleToken.SYMBOL_TYPE_COMMA);
      words.add(token);

      token = new SimpleToken();
      token.setValueToken(fldName);
      words.add(token);

      token = new SimpleToken();
      token.setSymbolToken(SimpleToken.SYMBOL_TYPE_RIGHT_PARENTHESIS);
      words.add(token);

      return words;
    }
    else
      throw new Exception("未找到request的方法" + methodName + ",位置:" + this.pos);
  }

  /**
   * 扫描session对象的方法
   * @return String
   * @throws Exception
   */
  public Vector scanSession()
    throws Exception
  {
    Vector words = new Vector();
    SimpleToken token;

    //扫描点号
    char c = seekNext();

    //获取方法名
    String methodName = scanToken();

    //session获取属性方法
    if(methodName.equals("getAttribute"))
    {
      String attrName,fldName = "",text;

      //跳过空行
      skipBlank();
      //期待左括号
      if(seekNext() != '(')
        throw new Exception("期待左括号,位置:" + this.pos);
      //跳过空行
      skipBlank();
      //获取字符串参数
      if(getNextChar() != '\'' && getNextChar() != '\"')
        throw new Exception("期待字符串参数,位置:" + this.pos);
      attrName = scanString();
      //跳过空行
      skipBlank();
      //期待右括号
      if(seekNext() != ')')
        throw new Exception("期待右括号,位置:" + this.pos);
      //扫描字段
      while(getNextChar() == '.')
      {
        seekNext();
        //获取字段
        text = scanToken();
        if(text == null)
          throw new Exception("期待属性名,位置:" + this.pos);

        if(fldName.length() > 0)
          fldName += ".";
        fldName += text;
      }
      //转换成内部形式
      token = new SimpleToken();
      token.setFuncToken("~session_getAttribute",2);
      words.add(token);

      token = new SimpleToken();
      token.setSymbolToken(SimpleToken.SYMBOL_TYPE_LEFT_PARENTHESIS);
      words.add(token);

      token = new SimpleToken();
      token.setValueToken(attrName);
      words.add(token);

      token = new SimpleToken();
      token.setSymbolToken(SimpleToken.SYMBOL_TYPE_COMMA);
      words.add(token);

      token = new SimpleToken();
      token.setValueToken(fldName);
      words.add(token);

      token = new SimpleToken();
      token.setSymbolToken(SimpleToken.SYMBOL_TYPE_RIGHT_PARENTHESIS);
      words.add(token);

      return words;
    }
    else
      throw new Exception("未找到session的方法" + methodName + ",位置:" + this.pos);
  }
}
