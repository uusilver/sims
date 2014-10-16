package com.xwtech.framework.pub.tag;

public class LinkParam
{

  private String sqlid = "";

  private String sqlvalue = "";

  private String sqlfid = "";

  /**
   * @return 返回 sqlfid。
   */
  public String getSqlfid()
  {
    return sqlfid;
  }

  /**
   * @param sqlfid 要设置的 sqlfid。
   */
  public void setSqlfid(String sqlfid)
  {
    this.sqlfid = sqlfid;
  }

  /**
   * @return 返回 sqlid。
   */
  public String getSqlid()
  {
    return sqlid;
  }

  /**
   * @param sqlid 要设置的 sqlid。
   */
  public void setSqlid(String sqlid)
  {
    this.sqlid = sqlid;
  }

  /**
   * @return 返回 sqlvalue。
   */
  public String getSqlvalue()
  {
    return sqlvalue;
  }

  /**
   * @param sqlvalue 要设置的 sqlvalue。
   */
  public void setSqlvalue(String sqlvalue)
  {
    this.sqlvalue = sqlvalue;
  }

  public LinkParam()
  {
  }
}
