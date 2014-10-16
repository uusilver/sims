package com.xwtech.framework.query.bo;

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
public class Condition
{
  /**
   * 条件的标签
   */
  private String conditionLabel;
  /**
   * 条件的组件
   */
  private IHtmlet conditionHtmlet;

  /**
   * 返回条件的标签
   * @return String
   */
  public String getConditionLabel()
  {
    return conditionLabel;
  }

  /**
   * 返回条件的组件
   * @return IHtmlet
   */
  public IHtmlet getConditionHtmlet()
  {
    return conditionHtmlet;
  }

  /**
   * 设置条件的标签
   * @param conditionLabel String
   */
  public void setConditionLabel(String conditionLabel)
  {
    this.conditionLabel = conditionLabel;
  }

  /**
   * 设置条件的组件
   * @param conditionHtmlet IHtmlet
   */
  public void setConditionHtmlet(IHtmlet conditionHtmlet)
  {
    this.conditionHtmlet = conditionHtmlet;
  }
}
