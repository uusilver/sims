package com.xwtech.framework.query;

import com.xwtech.framework.query.bo.IQuery;
import com.xwtech.framework.pub.utils.*;
/**
 * <p>Title: Framework</p>
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
public class QueryException extends RuntimeException
{
  private IQuery query;
  public QueryException(IQuery query,String message,Throwable ex)
  {
    super(message,ex);
    this.query = query;
  }

  public QueryException(IQuery query,String message)
  {
    super(message);
    this.query = query;
  }

  public String getMessage()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(super.getMessage());
    if (this.query != null)
    {
      sb.append(StringUtils.LINE_SEPARATOR).append("查询名：" + query.getQueryName());
      sb.append(StringUtils.LINE_SEPARATOR).append("查询文件：" + query.getConfigFileUrl().getPath());
    }
    return sb.toString();
  }
}
