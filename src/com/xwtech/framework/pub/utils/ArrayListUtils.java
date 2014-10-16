package com.xwtech.framework.pub.utils;

import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
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
public class ArrayListUtils
{
  protected static final Logger logger = Logger.getLogger(ArrayListUtils.class);

  public static ArrayList random(ArrayList arrryList)
{
  ArrayList newArrayList = new ArrayList();
  java.util.Random random = new java.util.Random();
  for(int i=arrryList.size();i>0;i--)
  {
    newArrayList.add(arrryList.remove(random.nextInt(i)));
  }
  return newArrayList;
}
public static void main(String[] args)
{
  ArrayList a = new ArrayList();
  for (int i=0;i<10;i++)
    a.add(String.valueOf(i));
  a = random(a);
  logger.info(a);
}
}
