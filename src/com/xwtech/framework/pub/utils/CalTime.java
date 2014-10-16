package com.xwtech.framework.pub.utils;

/**
 * 计算时间值
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
public class CalTime
{
  private long beginTime;
  /**
   * 计时器开始
   */
  public void begin()
  {
    beginTime = System.currentTimeMillis();
  }

  /**
   * 计时器结束，获取时间
   * @return long
   */
  public long end()
  {
    return System.currentTimeMillis()-beginTime;
  }

  /**
   * 计时器结束，获取时间字符串
   * @return String
   */
  public String endString()
  {
    return String.valueOf(System.currentTimeMillis()-beginTime);
  }
}
