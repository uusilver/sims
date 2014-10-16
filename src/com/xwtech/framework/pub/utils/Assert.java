package com.xwtech.framework.pub.utils;

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
public class Assert
{

  /**
   * Assert that an object is not null.
   * <pre>
   * Assert.notNull(clazz, "The class must not be null");</pre>
   * @param object the object to check
   * @param message the exception message to use if the assertion fails
   * @throws IllegalArgumentException if the object is <code>null</code>
   */
  public static void notNull(Object object, String message)
  {
    if(object == null)
    {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Assert that an object is not null.
   * <pre>
   * Assert.notNull(clazz);</pre>
   * @param object the object to check
   * @throws IllegalArgumentException if the object is <code>null</code>
   */
  public static void notNull(Object object)
  {
    notNull(object, "[Assertion failed] - this argument is required; it cannot be null");
  }
}
