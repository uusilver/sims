package com.xwtech.framework.pub.utils.simpleparser;

public class SimpleSymbolFunc
{
  /**
   * 执行操作符
   * @param op SimpleToken
   * @param ops SimpleToken[]
   * @return SimpleToken
   * @throws Exception
   */
  public static final SimpleToken execOP(SimpleToken op, SimpleToken[] ops)
    throws Exception
  {
    switch(op.getSymbolType())
    {
      case SimpleToken.SYMBOL_TYPE_LOGIC_NOT://!
        return execLogicNot(ops);
      case SimpleToken.SYMBOL_TYPE_LOGIC_EQUAL://==
        return execLogicEqual(ops);
      case SimpleToken.SYMBOL_TYPE_LOGIC_MOREEQUAL://>=
        return execLogicMoreEqual(ops);
      case SimpleToken.SYMBOL_TYPE_LOGIC_LESSEQUAL://<=
        return execLogicLessEqual(ops);
      case SimpleToken.SYMBOL_TYPE_LOGIC_MORE://>
        return execLogicMore(ops);
      case SimpleToken.SYMBOL_TYPE_LOGIC_LESS://<
        return execLogicLess(ops);
      case SimpleToken.SYMBOL_TYPE_LOGIC_NOTEQUAL://!=
        return execLogicNotEqual(ops);
      case SimpleToken.SYMBOL_TYPE_LOGIC_AND://&&
        return execLogicAnd(ops);
      case SimpleToken.SYMBOL_TYPE_LOGIC_OR://||
        return execLogicOr(ops);
      case SimpleToken.SYMBOL_TYPE_ARITH_ADD://+
        return execArithAdd(ops);
      case SimpleToken.SYMBOL_TYPE_ARITH_MUL://*
        return execArithMul(ops);
      case SimpleToken.SYMBOL_TYPE_ARITH_DIV:///
        return execArithDiv(ops);
      case SimpleToken.SYMBOL_TYPE_ARITH_SUB://-
        return execArithSub(ops);
      case SimpleToken.SYMBOL_TYPE_ARITH_MOD://%
        return execArithMod(ops);
      case SimpleToken.SYMBOL_TYPE_BIT_NOT://~
        return execBitNot(ops);
      case SimpleToken.SYMBOL_TYPE_BIT_AND://&
        return execBitAnd(ops);
      case SimpleToken.SYMBOL_TYPE_BIT_XOR://^
        return execBitXor(ops);
      case SimpleToken.SYMBOL_TYPE_BIT_OR://|
        return execBitOr(ops);
    }

    throw new Exception("\"" + op.toString() + "\"运算符当前未实现");
  }

  /**
   * 逻辑非
   * @param ops SimpleToken[]
   * @return SimpleToken
   * @throws Exception
   */
  private static SimpleToken execLogicNot(SimpleToken[] ops)
    throws Exception
  {
    if(ops[0].isTypeBoolean())
      return new SimpleToken(!ops[0].getBooleanValue());
    else
      throw new Exception("\"!\"运算符未期待的参数类型:" + ops[0].getTypeName());
   }

  /**
   * 逻辑等于
   * @param ops SimpleToken[]
   * @return SimpleToken
   */
  private static SimpleToken execLogicEqual(SimpleToken[] ops)
    throws Exception
  {
    if(ops[0].isTypeObject() && ops[1].isTypeObject())
    {
      if(ops[0].isNull() || ops[1].isNull())
        return new SimpleToken(ops[0].isNull() == ops[1].isNull());
      else
        return new SimpleToken(ops[0].getObjectValue().equals(ops[1].getObjectValue()));
    }
    else if(ops[0].isTypeBoolean() && ops[1].isTypeBoolean())
      return new SimpleToken(ops[0].getBooleanValue() == ops[1].getBooleanValue());
    else if(ops[0].isTypeNumber() && ops[1].isTypeNumber())
    {
      if(ops[0].isTypeDouble() || ops[1].isTypeDouble())
        return new SimpleToken(ops[0].getDoubleValue() == ops[1].getDoubleValue());
      else
        return new SimpleToken(ops[0].getLongValue() == ops[1].getLongValue());
    }
    else if(ops[0].isTypeString() && ops[1].isTypeString())
    {
      return new SimpleToken(ops[0].getStringValue().equalsIgnoreCase(ops[1].getStringValue()));
    }
    else
      throw new Exception("\"==\"运算符未期待的参数类型:" + ops[0].getTypeName() + "," + ops[1].getTypeName());
  }

  /**
   * 逻辑大于等于
   * @param ops SimpleToken[]
   * @return SimpleToken
   * @throws Exception
   */
  private static SimpleToken execLogicMoreEqual(SimpleToken[] ops)
    throws Exception
  {
    if(ops[0].isTypeNumber() && ops[1].isTypeNumber())
    {
      if(ops[0].isTypeDouble() || ops[1].isTypeDouble())
        return new SimpleToken(ops[0].getDoubleValue() >= ops[1].getDoubleValue());
      else
        return new SimpleToken(ops[0].getLongValue() >= ops[1].getLongValue());
    }
    else if(ops[0].isTypeString() && ops[1].isTypeString())
    {
      return new SimpleToken(ops[0].getStringValue().compareToIgnoreCase(ops[1].getStringValue()) >= 0);
    }
    else
      throw new Exception("\">=\"运算符未期待的参数类型:" + ops[0].getTypeName() + "," + ops[1].getTypeName());
  }

  /**
   * 逻辑小于等于
   * @param ops SimpleToken[]
   * @return SimpleToken
   * @throws Exception
   */
  private static SimpleToken execLogicLessEqual(SimpleToken[] ops)
    throws Exception
  {
    if(ops[0].isTypeNumber() && ops[1].isTypeNumber())
    {
      if(ops[0].isTypeDouble() || ops[1].isTypeDouble())
        return new SimpleToken(ops[0].getDoubleValue() <= ops[1].getDoubleValue());
      else
        return new SimpleToken(ops[0].getLongValue() <= ops[1].getLongValue());
    }
    else if(ops[0].isTypeString() && ops[1].isTypeString())
    {
      return new SimpleToken(ops[0].getStringValue().compareToIgnoreCase(ops[1].getStringValue()) <= 0);
    }
    else
      throw new Exception("\"<=\"运算符未期待的参数类型:" + ops[0].getTypeName() + "," + ops[1].getTypeName());
  }

  /**
   * 逻辑大于
   * @param ops SimpleToken[]
   * @return SimpleToken
   * @throws Exception
   */
  private static SimpleToken execLogicMore(SimpleToken[] ops)
    throws Exception
  {
    if(ops[0].isTypeNumber() && ops[1].isTypeNumber())
    {
      if(ops[0].isTypeDouble() || ops[1].isTypeDouble())
        return new SimpleToken(ops[0].getDoubleValue() > ops[1].getDoubleValue());
      else
        return new SimpleToken(ops[0].getLongValue() > ops[1].getLongValue());
    }
    else if(ops[0].isTypeString() && ops[1].isTypeString())
    {
      return new SimpleToken(ops[0].getStringValue().compareToIgnoreCase(ops[1].getStringValue()) > 0);
    }
    else
      throw new Exception("\">\"运算符未期待的参数类型:" + ops[0].getTypeName() + "," + ops[1].getTypeName());
  }

  /**
   * 逻辑小于
   * @param ops SimpleToken[]
   * @return SimpleToken
   * @throws Exception
   */
  private static SimpleToken execLogicLess(SimpleToken[] ops)
    throws Exception
  {
    if(ops[0].isTypeNumber() && ops[1].isTypeNumber())
    {
      if(ops[0].isTypeDouble() || ops[1].isTypeDouble())
        return new SimpleToken(ops[0].getDoubleValue() < ops[1].getDoubleValue());
      else
        return new SimpleToken(ops[0].getLongValue() < ops[1].getLongValue());
    }
    else if(ops[0].isTypeString() && ops[1].isTypeString())
    {
      return new SimpleToken(ops[0].getStringValue().compareToIgnoreCase(ops[1].getStringValue()) < 0);
    }
    else
      throw new Exception("\"<\"运算符未期待的参数类型:" + ops[0].getTypeName() + "," + ops[1].getTypeName());
  }

  /**
   * 逻辑不等于
   * @param ops SimpleToken[]
   * @return SimpleToken
   */
  private static SimpleToken execLogicNotEqual(SimpleToken[] ops)
    throws Exception
  {
    if(ops[0].isNull() || ops[1].isNull())
      return new SimpleToken(ops[0].isNull() != ops[1].isNull());

    if(ops[0].isTypeObject() && ops[1].isTypeObject())
        return new SimpleToken(!ops[0].getObjectValue().equals(ops[1].getObjectValue()));
    else if(ops[0].isTypeBoolean() && ops[1].isTypeBoolean())
      return new SimpleToken(ops[0].getBooleanValue() != ops[1].getBooleanValue());
    else if(ops[0].isTypeNumber() && ops[1].isTypeNumber())
    {
      if(ops[0].isTypeDouble() || ops[1].isTypeDouble())
        return new SimpleToken(ops[0].getDoubleValue() != ops[1].getDoubleValue());
      else
        return new SimpleToken(ops[0].getLongValue() != ops[1].getLongValue());
    }
    else if(ops[0].isTypeString() && ops[1].isTypeString())
    {
      return new SimpleToken(!ops[0].getStringValue().equalsIgnoreCase(ops[1].getStringValue()));
    }
    else
      throw new Exception("\"!=\"运算符未期待的参数类型:" + ops[0].getTypeName() + "," + ops[1].getTypeName());
  }

  /**
   * 逻辑与
   * @param ops SimpleToken[]
   * @return SimpleToken
   */
  private static SimpleToken execLogicAnd(SimpleToken[] ops)
    throws Exception
  {
    if(ops[0].isTypeBoolean() && ops[1].isTypeBoolean())
      return new SimpleToken(ops[0].getBooleanValue() && ops[1].getBooleanValue());
    else
      throw new Exception("\"&&\"运算符未期待的参数类型:" + ops[0].getTypeName() + "," + ops[1].getTypeName());
  }

  /**
   * 逻辑或
   * @param ops SimpleToken[]
   * @return SimpleToken
   */
  private static SimpleToken execLogicOr(SimpleToken[] ops)
    throws Exception
  {
    if(ops[0].isTypeBoolean() && ops[1].isTypeBoolean())
      return new SimpleToken(ops[0].getBooleanValue() || ops[1].getBooleanValue());
    else
      throw new Exception("\"||\"运算符未期待的参数类型:" + ops[0].getTypeName() + "," + ops[1].getTypeName());
  }

  /**
   * 算术加法
   * @param ops SimpleToken[]
   * @return SimpleToken
   */
  private static SimpleToken execArithAdd(SimpleToken[] ops)
    throws Exception
  {
    if(ops.length == 1)
      return ops[0];
    else
    {
      if(ops[0].isTypeNumber() && ops[1].isTypeNumber())
      {
        if(ops[0].isTypeDouble() || ops[1].isTypeDouble())
          return new SimpleToken(ops[0].getDoubleValue() + ops[1].getDoubleValue());
        else
          return new SimpleToken(ops[0].getLongValue() + ops[1].getLongValue());
      }
      else if(ops[0].isTypeString() || ops[1].isTypeString())
        return new SimpleToken(ops[0].getStringValue() + ops[1].getStringValue());
      else
        throw new Exception("\"+\"运算符未期待的参数类型:" + ops[0].getTypeName() + "," + ops[1].getTypeName());
    }
  }

  /**
   * 算术乘法
   * @param ops SimpleToken[]
   * @return SimpleToken
   * @throws Exception
   */
  private static SimpleToken execArithMul(SimpleToken[] ops)
    throws Exception
  {
    if(ops[0].isTypeNumber() && ops[1].isTypeNumber())
    {
      if(ops[0].isTypeDouble() || ops[1].isTypeDouble())
       return new SimpleToken(ops[0].getDoubleValue() * ops[1].getDoubleValue());
     else
       return new SimpleToken(ops[0].getLongValue() * ops[1].getLongValue());
    }
    else
      throw new Exception("\"*\"运算符未期待的参数类型:" + ops[0].getTypeName() + "," + ops[1].getTypeName());
  }

  /**
   * 算术除法
   * @param ops SimpleToken[]
   * @return SimpleToken
   * @throws Exception
   */
  private static SimpleToken execArithDiv(SimpleToken[] ops)
    throws Exception
  {
    if(ops[0].isTypeNumber() && ops[1].isTypeNumber())
    {
      if(ops[0].isTypeDouble() || ops[1].isTypeDouble())
       return new SimpleToken(ops[0].getDoubleValue() / ops[1].getDoubleValue());
     else
       return new SimpleToken(ops[0].getLongValue() / ops[1].getLongValue());
    }
    else
      throw new Exception("\"/\"运算符未期待的参数类型:" + ops[0].getTypeName() + "," + ops[1].getTypeName());
  }

  /**
   * 算术减法
   * @param ops SimpleToken[]
   * @return SimpleToken
   * @throws Exception
   */
  private static SimpleToken execArithSub(SimpleToken[] ops)
    throws Exception
  {
    if(ops.length == 1)
    {
      if(ops[0].isTypeNumber())
      {
        if(ops[0].isTypeDouble())
          return new SimpleToken(- ops[0].getDoubleValue());
        else
          return new SimpleToken(- ops[0].getLongValue());
      }
      else
        throw new Exception("\"-\"运算符未期待的参数类型:" + ops[0].getTypeName());
    }
    else
    {
      if(ops[0].isTypeNumber() && ops[1].isTypeNumber())
      {
        if(ops[0].isTypeDouble() || ops[1].isTypeDouble())
          return new SimpleToken(ops[0].getDoubleValue() - ops[1].getDoubleValue());
        else
          return new SimpleToken(ops[0].getLongValue() - ops[1].getLongValue());
      }
      else
        throw new Exception("\"-\"运算符未期待的参数类型:" + ops[0].getTypeName() + "," + ops[1].getTypeName());
    }
  }

  /**
   * 算术模
   * @param ops SimpleToken[]
   * @return SimpleToken
   * @throws Exception
   */
  private static SimpleToken execArithMod(SimpleToken[] ops)
    throws Exception
  {
    if(ops[0].isTypeNumber() && ops[1].isTypeNumber())
    {
       return new SimpleToken(ops[0].getLongValue() % ops[1].getLongValue());
    }
    else
      throw new Exception("\"%\"运算符未期待的参数类型:" + ops[0].getTypeName() + "," + ops[1].getTypeName());
  }

  /**
   * 位非
   * @param ops SimpleToken[]
   * @return SimpleToken
   * @throws Exception
   */
  private static SimpleToken execBitNot(SimpleToken[] ops)
    throws Exception
  {
    if(ops[0].isTypeNumber())
      return new SimpleToken(~ops[0].getLongValue());
    else
      throw new Exception("\"~\"运算符未期待的参数类型:" + ops[0].getTypeName());
  }

  /**
   * 位与
   * @param ops SimpleToken[]
   * @return SimpleToken
   * @throws Exception
   */
  private static SimpleToken execBitAnd(SimpleToken[] ops)
    throws Exception
  {
    if(ops[0].isTypeNumber() && ops[1].isTypeNumber())
      return new SimpleToken(ops[0].getLongValue() & ops[1].getLongValue());
    else
      throw new Exception("\"&\"运算符未期待的参数类型:" + ops[0].getTypeName() + "," + ops[1].getTypeName());
  }

  /**
   * 位异或
   * @param ops SimpleToken[]
   * @return SimpleToken
   * @throws Exception
   */
  private static SimpleToken execBitXor(SimpleToken[] ops)
    throws Exception
  {
    if(ops[0].isTypeNumber() && ops[1].isTypeNumber())
      return new SimpleToken(ops[0].getLongValue() ^ ops[1].getLongValue());
    else
      throw new Exception("\"^\"运算符未期待的参数类型:" + ops[0].getTypeName() + "," + ops[1].getTypeName());
  }

  /**
   * 位或
   * @param ops SimpleToken[]
   * @return SimpleToken
   * @throws Exception
   */
  private static SimpleToken execBitOr(SimpleToken[] ops)
    throws Exception
  {
    if(ops[0].isTypeNumber() && ops[1].isTypeNumber())
      return new SimpleToken(ops[0].getLongValue() | ops[1].getLongValue());
    else
      throw new Exception("\"|\"运算符未期待的参数类型:" + ops[0].getTypeName() + "," + ops[1].getTypeName());
  }
}
