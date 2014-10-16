package com.xwtech.framework.pub.utils.simpleparser;

import java.util.*;
import org.apache.log4j.Logger;

/**
 * 1.参数支持的数据类型:null,Integer,Long,Float,Double,String,Boolean及相应的数组
 * 2.内部支持的数据类型:null,long,double,String,boolean,Object
 * 3.支持的关键字:true,false,null
 * 4.支持的内部变量:request,response(其值需作为参数传入)
 * 5.函数支持可变参数
 * <p>Title: 表达式解析</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author 邢正俊
 * @version 1.0
 */

public class SimpleParser
{
  protected static HashMap cache = new HashMap();
  protected static final Logger logger = Logger.getLogger(SimpleParser.class);

  /**
   * 从缓存中查找已编译得表达式
   * @param code String 表达式代码
   * @return Vector　已解析的单词列表
   */
  private static synchronized Vector getCacheVector(String code)
  {
    return (Vector)cache.get(code);
  }

  /**
   * 将已编译得表达式存放到缓存中
   * @param code String 表达式代码
   * @param vector Vector 已解析的单词列表
   */
  private static synchronized void setCacheVector(String code, Vector vector)
  {
    cache.put(code,vector);
  }

  /**
   * 编译表达式
   * @param code String 表达式代码
   * @return Vector 已解析的单词列表
   * @throws Exception
   */
  private static Vector Compile(String code)
    throws Exception
  {
    Vector words = getCacheVector(code);
    if(words == null)
    {
      try
      {
        words = new Vector();
        parseWords(words, code);
        setCacheVector(code,words);
      }
      catch(Exception ex)
      {
        String errinfo = "表达式编译错误:" + code;
        logger.error(errinfo,ex);
        throw new Exception(errinfo,ex);
      }
    }

    return words;
  }

  /**
   * 解析逻辑表达式并计算结果
   * @param code String 表达式代码
   * @param args HashMap 变量名-值对。
   * @return boolean 逻辑表达式结果
   * @throws Exception
   */
  public static final boolean execLogicExpression(String code, HashMap args)
    throws Exception
  {
    Vector words = Compile(code);

    //执行
    SimpleToken ret = null;
    try
    {
      ret = exec(words,args);
    }
    catch(Exception ex)
    {
      String errinfo = "逻辑表达式执行错误:" + code;
      logger.error(errinfo ,ex);
      throw new Exception(errinfo ,ex);
    }

    //没有返回值,则返回false
    if(ret == null)
    {
        String errinfo = "逻辑表达式没有执行结果:" + code;
        logger.error(errinfo);
        throw new Exception(errinfo);
    }
    //有布尔型值
    else if(!ret.isArray() && ret.getType() == ret.TK_TYPE_BOOLEAN)
      return ret.getBooleanValue();
    //其它非布尔型值
    else
    {
      String errinfo = "逻辑表达式期待布尔型返回值,非\"" + ret.getTypeName() + "\":" + code;
      logger.error(errinfo);
      throw new Exception(errinfo);
    }
  }

  /**
   * 解析表达式并计算结果
   * @param code 表达式代码
   * @param args 变量名-值对。
   * @return 计算的结果
   * @throws java.lang.Exception
   */
  public static final Object execExpression(String code, HashMap args) throws Exception
  {
    Vector words = Compile(code);
//    debugPrintWords(words);
    //执行
    SimpleToken ret = null;
    try
    {
      ret = exec(words,args);
    }
    catch(Exception ex)
    {
      String errinfo = "表达式执行错误:" + code;
      logger.error(errinfo ,ex);
      throw new Exception(errinfo, ex);
    }

    //没有返回值
    if(ret == null)
      return null;
    //数组
    else if(ret.isArray())
      return ret.getObjectValue();
    //布尔型
    else if(ret.isTypeBoolean())
      return new Boolean(ret.getBooleanValue());
    //整型
    else if(ret.isTypeLong())
      return new Long(ret.getLongValue());
    //浮点型
    else if(ret.isTypeDouble())
      return new Double(ret.getDoubleValue());
    //对象型,字符串型
    else if(ret.isTypeObject() || ret.isTypeString())
      return ret.getObjectValue();

    return null;
  }

  /**
   * 词法分析
   * @param words 分析的词结果列表
   * @param code 表达式代码
   * @throws java.lang.Exception
   */
  private static final void parseWords(Vector words, String code)
    throws Exception
  {
    SimpleScanner ss = new SimpleScanner(code);

    char c;
    SimpleToken token = new SimpleToken(),preToken;

    while(!ss.isEnd())
    {
      c = ss.getNextChar();

      //空白
      if(c == ' ' || c == '\t' || c == '\r' || c == '\n')
      {
        ss.skipBlank();
        continue;
      }
      //数值
      else if((c >= '0' && c <= '9') || c == '.')
      {
        token.setValueToken(ss.scanNumber());

        words.add(token);
        token = new SimpleToken();
      }
      //字符串
      else if(c == '\'' || c == '\"')
      {
        token.setValueToken(ss.scanString());

        words.add(token);
        token = new SimpleToken();
      }
      //函数和变量
      else if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_')
      {
        String text = ss.scanToken();

        //关键字
        if(text.equals("null"))
        {
          token.setValueToken(null);
          words.add(token);
        }
        else if(text.equals("true"))
        {
          token.setValueToken(true);
          words.add(token);
        }
        else if(text.equals("false"))
        {
          token.setValueToken(false);
          words.add(token);
        }
        //特殊关键字处理
        else if(text.equals("request"))
        {
          Vector v = ss.scanRequest();
          for(int i=0;i<v.size();i++)
            words.add(v.get(i));
        }
        else if(text.equals("session"))
        {
          Vector v = ss.scanSession();
          for(int i=0;i<v.size();i++)
            words.add(v.get(i));
        }
        //函数
        else if(SimpleFunc.getFuncList().containsKey(text))
        {
          token.setFuncToken(text,((Integer)SimpleFunc.getFuncList().get(text)).intValue());
          words.add(token);
        }
        //变量
        else
        {
          token.setVarToken(text);
          words.add(token);
        }

        token = new SimpleToken();
      }
      //符号
      else
      {
        char c1;
        c = ss.seekNext();

        switch(c)
        {
          case '*':
            token.setSymbolToken(SimpleToken.SYMBOL_TYPE_ARITH_MUL);
            break;
          case '/':
            token.setSymbolToken(SimpleToken.SYMBOL_TYPE_ARITH_DIV);
            break;
          case '%':
            token.setSymbolToken(SimpleToken.SYMBOL_TYPE_ARITH_MOD);
            break;
          case '+':
            token.setSymbolToken(SimpleToken.SYMBOL_TYPE_ARITH_ADD);
            if(words.size() > 0)
            {
              preToken = (SimpleToken)words.get(words.size()-1);
              if(preToken.getType() == preToken.TK_TYPE_SYMBOL
                && preToken.getSymbolType() != preToken.SYMBOL_TYPE_RIGHT_PARENTHESIS)
                token.setArgsCount(1);
            }
            else
              token.setArgsCount(1);
            break;
          case '-':
            token.setSymbolToken(SimpleToken.SYMBOL_TYPE_ARITH_SUB);
            if(words.size() > 0)
            {
              preToken = (SimpleToken)words.get(words.size()-1);
              if(preToken.getType() == preToken.TK_TYPE_SYMBOL
                && preToken.getSymbolType() != preToken.SYMBOL_TYPE_RIGHT_PARENTHESIS)
                token.setArgsCount(1);
            }
            else
              token.setArgsCount(1);
            break;
          case '=':
            if(ss.seekNext() != '=')
              throw new Exception("不可识别的符号,位置:" + ss.getPos());

            token.setSymbolToken(SimpleToken.SYMBOL_TYPE_LOGIC_EQUAL);
            break;
          case '>':
            if(ss.getNextChar() == '=')
            {
              ss.seekNext();
              token.setSymbolToken(SimpleToken.SYMBOL_TYPE_LOGIC_MOREEQUAL);
            }
            else
              token.setSymbolToken(SimpleToken.SYMBOL_TYPE_LOGIC_MORE);
            break;
          case '<':
            if(ss.getNextChar() == '=')
            {
              ss.seekNext();
              token.setSymbolToken(SimpleToken.SYMBOL_TYPE_LOGIC_LESSEQUAL);
            }
            else
              token.setSymbolToken(SimpleToken.SYMBOL_TYPE_LOGIC_LESS);
            break;
          case '!':
            if(ss.getNextChar() == '=')
            {
              ss.seekNext();
              token.setSymbolToken(SimpleToken.SYMBOL_TYPE_LOGIC_NOTEQUAL);
            }
            else
              token.setSymbolToken(SimpleToken.SYMBOL_TYPE_LOGIC_NOT);
            break;
          case '~':
            token.setSymbolToken(SimpleToken.SYMBOL_TYPE_BIT_NOT);
            break;
          case '&':
            if(ss.getNextChar() == '&')
            {
              ss.seekNext();
              token.setSymbolToken(SimpleToken.SYMBOL_TYPE_LOGIC_AND);
            }
            else
              token.setSymbolToken(SimpleToken.SYMBOL_TYPE_BIT_AND);
            break;
          case '|':
            if(ss.getNextChar() == '|')
            {
              ss.seekNext();
              token.setSymbolToken(SimpleToken.SYMBOL_TYPE_LOGIC_OR);
            }
            else
              token.setSymbolToken(SimpleToken.SYMBOL_TYPE_BIT_OR);
            break;
          case '^':
            token.setSymbolToken(SimpleToken.SYMBOL_TYPE_BIT_XOR);
            break;
          case '(':
            token.setSymbolToken(SimpleToken.SYMBOL_TYPE_LEFT_PARENTHESIS);
            break;
          case ')':
            token.setSymbolToken(SimpleToken.SYMBOL_TYPE_RIGHT_PARENTHESIS);
            break;
          case ',':
            token.setSymbolToken(SimpleToken.SYMBOL_TYPE_COMMA);
            break;
          case '\t':
            break;
          case ' ':
            break;
          case '\r':
            break;
          case '\n':
            break;
          default:
            throw new Exception("无法识别的符号:\"" + c + "\",位置:" + ss.getPos());
        }

        if(token.getType() != token.TK_TYPE_NOUSE)
        {
          words.add(token);
          token = new SimpleToken();
        }
      }
    }
  }

  /**
   * 语法分析及运行
   *
   * @param words token列表
   * @param args HashMap
   * @return com.xwtech.framework.pub.utils.simpleparser.SimpleToken
   * @throws Exception
   */
  private static final SimpleToken exec(Vector words, HashMap args)
    throws Exception
  {
    SimpleToken token;
    Stack symstack = new Stack();
    Stack constack = new Stack();

    for(int i = 0; i < words.size(); i ++ )
    {
      token = (SimpleToken)words.get(i);

      switch(token.getType())
      {
        case SimpleToken.TK_TYPE_LONG:
        case SimpleToken.TK_TYPE_DOUBLE:
        case SimpleToken.TK_TYPE_STRING:
        case SimpleToken.TK_TYPE_VARIABLE:
        case SimpleToken.TK_TYPE_BOOLEAN:
        case SimpleToken.TK_TYPE_OBJECT:
          constack.push(token); //操作数直接入栈
          break;
        case SimpleToken.TK_TYPE_FUNCTION:
          //检查函数参数个数
          if(i >= words.size() - 2)
            throw new Exception("无效的函数调用:" + token.toString());
          else
          {
            SimpleToken o = (SimpleToken)words.get(i + 2);
            //实际无参数
            if(o.getSymbolType() == SimpleToken.SYMBOL_TYPE_RIGHT_PARENTHESIS)
            {
              //可变参数
              if(token.getArgsCount() == -1)
                token.setArgsCount(0);
              //期待有参数
              else if(token.getArgsCount() > 0)
                throw new Exception("函数" + token.toString() + "参数个数错误,期待" + token.getArgsCount() + "个参数");
            }
            //实际有参数
            else
            {
              //期待无参数
              if(token.getArgsCount() == 0)
                throw new Exception("函数" + token.toString() + "参数个数错误,期待无参数");
            }
          }
        case SimpleToken.TK_TYPE_SYMBOL:
          //如果符号栈为空,则函数符号直接入栈
          if(symstack.isEmpty())
            symstack.push(token);
          else
          {
            //如果为左括号,则直接入栈
            if(token.getSymbolType() == token.SYMBOL_TYPE_LEFT_PARENTHESIS)
              symstack.push(token);
            //如果为右括号,则向左进行规约
            else if(token.getSymbolType() == token.SYMBOL_TYPE_RIGHT_PARENTHESIS)
            {
              SimpleToken sym;
              sym = (SimpleToken)symstack.peek();
              //函数式
              if(sym.getSymbolType() == sym.SYMBOL_TYPE_COMMA
                 || (sym.getSymbolType() == sym.SYMBOL_TYPE_LEFT_PARENTHESIS
                     && symstack.size()>1
                     && ((SimpleToken)symstack.get(symstack.size()-2)).getType()==SimpleToken.TK_TYPE_FUNCTION))
                execFuncOP(symstack, constack, args);
              //普通操作符
              else
              {
                while(true)
                {
                  if(sym.getSymbolType() == sym.SYMBOL_TYPE_LEFT_PARENTHESIS)
                    break;

                  execOP(symstack, constack, args);
                  sym = (SimpleToken)symstack.peek();
                }

                if(sym.getSymbolType() == sym.SYMBOL_TYPE_LEFT_PARENTHESIS)
                  symstack.pop();
                else
                  throw new Exception("括号不匹配");
              }
            }
            //否则进行优先级比较
            else
            {
              SimpleToken sym;
              while(true)
              {
                sym = (SimpleToken)symstack.peek();

                //如果优先级高,则入栈
                if(token.getPriority() > sym.getPriority())
                {
                  symstack.push(token);
                  break;
                }
                //如果优先级相同或低,则执行栈里面的元素计算
                else
                {
                  //如果是逗号运算符，则直接入符号栈
                  if(sym.getSymbolType() == sym.SYMBOL_TYPE_COMMA)
                  {
                    symstack.push(sym);
                    break;
                  }
                  else
                    execOP(symstack, constack, args);
                }

                //如果符号栈为空,则入栈
                if(symstack.isEmpty())
                {
                  symstack.push(token);
                  break;
                }
              }
            }
          }
          break;
      }
    }

    //如果符号栈还有符号,则继续运算
    while(!symstack.isEmpty())
    {
      SimpleToken sym = (SimpleToken)symstack.peek();
      if(sym.getType() == SimpleToken.TK_TYPE_FUNCTION)
      {
        symstack.pop();
        SimpleToken[] ops = new SimpleToken[constack.size()];
        for(int i=0;i<ops.length ;i++)
          ops[ops.length - i - 1] = (SimpleToken)constack.pop();
        SimpleToken ret = SimpleFunc.execFunc(sym.toString(),ops,args);
        if(ret != null)
          constack.push(ret);
      }
      else if(sym.getType() == SimpleToken.TK_TYPE_SYMBOL)
        execOP(symstack, constack, args);
    }

    //如果常数栈有超过一个的元素,则出错
    if(constack.size() > 1)
      throw new Exception("缺少运算符");

    if(constack.size() == 1)
      return (SimpleToken)constack.pop();
    else
      return null;
  }

  /**
   * 执行函数式
   * @param symstack Stack
   * @param constack Stack
   * @param args HashMap
   * @throws Exception
   */
  private static void execFuncOP(Stack symstack, Stack constack, HashMap args)
    throws Exception
  {
    int pars = 0;
    SimpleToken sym, ret = null;

    while(!symstack.isEmpty())
    {
      sym = (SimpleToken) symstack.pop();
      if(sym.getSymbolType() == sym.SYMBOL_TYPE_COMMA)
        pars ++;
      else if(sym.getSymbolType() == sym.SYMBOL_TYPE_LEFT_PARENTHESIS)
        break;
    }

    //找到函数
    if(symstack.isEmpty())
      throw new Exception("期待函数");

    sym = (SimpleToken) symstack.pop();

    if(pars == 0)
    {
      if(sym.getArgsCount() == -1 || sym.getArgsCount() == 1)
        pars = 1;
      else if(sym.getArgsCount() > 1)
        throw new Exception("函数" + sym.toString() + "期待" + sym.getArgsCount() + "个参数");
    }
    else
    {
      pars ++;
      if(sym.getArgsCount() != -1 && sym.getArgsCount() != pars)
        throw new Exception("函数" + sym.toString() + "期待" + sym.getArgsCount() + "个参数");
    }

    //检查参数个数
    if(constack.size() < pars)
      throw new Exception("函数" + sym.toString() + "参数不足,期待" + pars + "个参数");

    //取出参数
    SimpleToken[] ops = new SimpleToken[pars];
    for(int i=0;i<pars;i++)
    {
      SimpleToken token = (SimpleToken)constack.pop();
      String key = token.toString();
      if(token.getType() == SimpleToken.TK_TYPE_VARIABLE)
      {
        //检查该变量是否存在
        if(!args.containsKey(key))
          throw new Exception("变量" + key + "未赋值");

        token = new SimpleToken();
        token.setValueToken(args.get(key));
      }

      ops[pars-i-1] = token;
    }

    //计算
    ret = SimpleFunc.execFunc(sym.toString(),ops,args);

    //结果进栈
    if(ret != null)
      constack.push(ret);
  }

  /**
   * 计算符号栈顶端的符号
   * @param symstack Stack
   * @param constack Stack
   * @param args HashMap
   * @throws Exception
   */
  private static void execOP(Stack symstack, Stack constack, HashMap args)
    throws Exception
  {
    SimpleToken sym = (SimpleToken)symstack.pop(),ret = null;

    if(sym.getSymbolType() == SimpleToken.SYMBOL_TYPE_COMMA)
      return;

    //检查参数个数
    if(constack.size() < sym.getArgsCount())
      throw new Exception("参数不足");

    //取出参数
    SimpleToken[] ops = new SimpleToken[sym.getArgsCount()];
    for(int i=0;i<sym.getArgsCount();i++)
    {
      SimpleToken token = (SimpleToken)constack.pop();
      String key = token.toString();
      if(token.getType() == SimpleToken.TK_TYPE_VARIABLE)
      {
        //检查该变量是否存在
        if(!args.containsKey(key))
          throw new Exception("变量" + key + "未赋值");

        token = new SimpleToken();
        token.setValueToken(args.get(key));

        //如果是数组就出错
        if(token.isArray())
          throw new Exception("操作符中的变量类型不允许为数组类型");
      }

      ops[sym.getArgsCount()-i-1] = token;
    }

    //计算
    ret = SimpleSymbolFunc.execOP(sym, ops);

    //结果进栈
    if(ret != null)
      constack.push(ret);
  }

  private static void debugPrintWords(Vector words)
  {
    SimpleToken token;
    System.out.println("\n解析的单词:\n");

    for(int i=0;i<words.size();i++)
    {
       token = (SimpleToken)words.get(i);

       System.out.print(token.toString() + "{" + token.getTypeName() + "}  ");
    }
  }

  public static void main(String[] args)
  {
//    TestRequest req = new TestRequest();
//    TestSession req = new TestSession();
//    req.setParameter("month_begin","200502");
//    testClass tc = new testClass();
//    req.setAttribute("userToken",tc);

    HashMap vars = new HashMap();

//   vars.put("request",req);
//    vars.put("session",req);
    vars.put("uu",new String[]{"a","c'd","ef"});


    try
    {
      Object obj = null;
//      String expr = "isNotEmpty('cctv','abc' + 'def')";
      String expr = "isNotEmpty(iif(true,'beginDate','a'),'x'+iif(true,'beginDate','b')+'y')";

      System.err.println(System.currentTimeMillis());
      /*
             for(int i=0;i<1;i++)
             {
        Object b = SimpleParser.execExpression(expr,vars);
        System.err.println(b.toString());
             }
       */
      for(int i=0;i<3;i++)
      {
        vars.put("postid",new Integer(1000 + i));
        obj = execExpression(expr,vars);


        System.out.println("\n\"" + expr + "\"\n=\n" + obj.toString());

        System.err.println(System.currentTimeMillis());
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
}

class testClass
{
  public testClass getUserInfo()
  {
    testClass c = new testClass();
    return c;
  }

  public int getUserId()
  {
    return 800;
  }
}
