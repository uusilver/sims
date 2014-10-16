package com.xwtech.framework.query.view;

/**
 *
 * <p>Title: 查询列表默认显示样式</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: xwtech.com</p>
 *
 * @author 邢正俊
 */
public class QueryDefaultView extends IQueryView
{
  /**
   * 取得条件区域标题文本,如果返回null,则不显示条件区域标题文本
   *
   * @return String
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected String getConditionTitle()
  {
    return "Search";
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputCheckBoxStyle()
  {
  }

  /**
   * outputConditionAreaEnd
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputConditionAreaEnd()
  {
    out.println("</table>");
  }

  /**
   * outputConditionAreaStart
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputConditionAreaStart()
  {
    out.println("<table border='0' align='center' cellpadding='0' cellspacing='0'>");
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputConditionStyle()
  {
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputConditionTitleStyle()
  {
  }

  /**
   * outputDataListAreaEnd
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputDataListAreaEnd()
  {
    out.println("</table>");
  }

  /**
   * outputDataListAreaStart
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputDataListAreaStart()
  {
    out.println("<table align='center' border='0' cellpadding='0' cellspacing='0' width='100%'>");
  }

  /**
   *
   * @param nCol int
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputDataListColStyle(int nCol)
  {
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputDataListHeadColStyle()
  {
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputDataListHeadRowStyle()
  {
  }

  /**
   *
   * @param nRow int
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputDataListRowStyle(int nRow)
  {
  }

  /**
   * outputFrameDataListAreaEnd
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputFrameDataListAreaEnd()
  {
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputFrameDataListAreaStart()
  {
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputLinkStyle()
  {
  }

  /**
   * outputNavBarAreaEnd
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputNavBarAreaEnd()
  {
  }

  /**
   * outputNavBarAreaStart
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputNavBarAreaStart()
  {
  }

  /**
   * outputNavButton
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputNavButton()
  {
  }

  /**
   * outputNavButtonAreaStyle
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputNavButtonAreaStyle()
  {
  }

  /**
   * outputNavJump
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputNavJump()
  {
  }

  /**
   * outputNavJumpAreaStyle
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputNavJumpAreaStyle()
  {
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputQueryButtonAreaStyle()
  {
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputQueryButtonStyle()
  {
  }

  /**
   * outputRecordStatInfo
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputRecordStatInfo()
  {
  }

  /**
   * outputRecordStatInfoAreaStyle
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputRecordStatInfoAreaStyle()
  {
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputResizeAreaStyle()
  {
  }

  /**
   *
   * @todo Implement this com.xwtech.framework.query.view.IQueryView method
   */
  protected void outputResizeListStyle()
  {
  }

  protected void outputQueryButton()
  {
    out.print("<input type='button' class='button_search' value='查询' onclick=\"javascript:querye_toquery('" + this.qInfo.getConditionFormName() + "');\">");
  }

  protected void outputDataListSumRowStyle()
  {
  }

  protected void outputDataListSumColStyle(int nCol)
  {
  }

  protected void outputDataListStatRowStyle()
  {
  }

  protected void outputDataListStatColStyle(int nCol)
  {
  }
}
