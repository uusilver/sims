package com.xwtech.framework.query.view;

import java.util.*;

/**
 *
 * <p>Title: 查询配置实体类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: xwtech.com</p>
 *
 * @author 邢正俊


 */
public class QueryInfo
{
  private String conditionFormName;//条件区域所在表单名称

  private boolean needrequest; //是否添加查询参数

  private static long currentSequenceid = System.currentTimeMillis();
  private String resultFormName;//查询列表所在表单名称



  private String[] hiddenColNames;//要隐藏列的域名称
  private String onTableLoad;//当行初始化展现时的处理方法



  private String tableId = null;//查询表格ID名



  private boolean canResize = false;//是否允许用户自己选择页面大小
  private int[] sizeList;//允许用户选择的自定义页面大小列表
  private int curPageSize;
  private boolean canJump = true;//是否允许用户任意跳转
  private String externScriptSrc = null;//外部脚本路径
  private String externScriptLanguage = "javascript";//外部脚本语言
  private String onRowDblClick;//onclick处理方法
  private QueryColExhibit[] colConfig;//列样式定义



  private ArrayList dataList;//行数据数组，每个元素为一个数组



  private String[] sumList;//统计列数组



  private int recordCount = -1; //记录总数,-1为没有记录总数
  private String conditionHTML;//条件区域HTML文本
  private boolean hasNextPage = false;//是否有下一页



  private int currentPageNo = 0;//当前页码
  private String[] colsTitle;//列标题数组



  private int pageSize = 0;//页面大小
  private int colsCount;//数据列总数
  private boolean needStat = false;//是否需要统计



  private boolean firstShow = false;//是否为不带数据的显示
  private int pageCount = -1;//页总数,-1表示没有计算总数

  private boolean needCond;
  private boolean needNavTop;
  private boolean needNavBottom;
  private QueryConfigExtraPart condTopExtra;
  private QueryConfigExtraPart condBottomExtra;
  private QueryConfigExtraPart pageBottomExtra;
  private QueryConfigExtraPart resultBottomExtra;
  private String querySortCol;
  private String querySortDir;
  private boolean fixRowsCount;//对于分页查询是否固定显示行总数

  private String resultFormMethod;
  private String resultFormAction;
  private String resultFormContent;
  private boolean needQueryButton;//是否需要查询按钮


  private String conditionFormStyle;//条件区域表单样式

  private boolean conditionFormCustom;

  private boolean needSelallBottom;//是否需要在底部显示全选框，默认为false

  private String checkBoxNameBottom; //底部全选框中将要选择的checkBox的名称


  public QueryInfo()
  {
    this.currentSequenceid++;
  }

  public String getConditionFormName()
  {
    return conditionFormName;
  }

  public long getCurrentSequenceid()
  {
    return currentSequenceid;
  }

  public String getResultFormName()
  {
    return resultFormName;
  }

  public String[] getHiddenColNames()
  {
    return hiddenColNames;
  }

  public String getOnTableLoad()
  {
    return onTableLoad;
  }

  public String getTableId()
  {
    return tableId;
  }

  public boolean isCanResize()
  {
    return canResize;
  }

  public int[] getSizeList()
  {
    return sizeList;
  }

  public int getCurPageSize()
  {
    return curPageSize;
  }

  public boolean isCanJump()
  {
    return canJump;
  }

  public String getExternScriptSrc()
  {
    return externScriptSrc;
  }

  public String getExternScriptLanguage()
  {
    return externScriptLanguage;
  }

  public String getOnRowDblClick()
  {
    return onRowDblClick;
  }

  public QueryColExhibit[] getColConfig()
  {
    return colConfig;
  }

  public ArrayList getDataList()
  {
    return dataList;
  }

  public String[] getSumList()
  {
    return sumList;
  }

  public int getRecordCount()
  {
    return recordCount;
  }

  public String getConditionHTML()
  {
    return conditionHTML;
  }

  public boolean isHasNextPage()
  {
    return hasNextPage;
  }

  public int getCurrentPageNo()
  {
    return currentPageNo;
  }

  public String[] getColsTitle()
  {
    return colsTitle;
  }

  public int getPageSize()
  {
    return pageSize;
  }

  public int getColsCount()
  {
    return colsCount;
  }

  public boolean isNeedStat()
  {

    return needStat;
  }

  public boolean isFirstShow()
  {

    return firstShow;
  }

  public int getPageCount()
  {
    return pageCount;
  }

  public boolean isNeedCond()
  {

    return needCond;
  }

  public boolean isNeedNavTop()
  {
    return needNavTop;
  }

  public boolean isNeedNavBottom()
  {
    return needNavBottom;
  }

  public QueryConfigExtraPart getCondTopExtra()
  {
    return condTopExtra;
  }

  public QueryConfigExtraPart getCondBottomExtra()
  {
    return condBottomExtra;
  }

  public QueryConfigExtraPart getPageBottomExtra()
  {
    return pageBottomExtra;
  }

  public String getQuerySortCol()
  {

    return querySortCol;
  }

  public String getQuerySortDir()
  {

    return querySortDir;
  }

  public boolean isFixRowsCount()
  {
    return fixRowsCount;
  }

  public String getResultFormMethod()
  {
    return resultFormMethod;
  }

  public String getResultFormAction()
  {
    return resultFormAction;
  }

  public String getResultFormContent()
  {
    return resultFormContent;
  }

  public boolean isNeedQueryButton()
  {
    return needQueryButton;
  }

  public String getConditionFormStyle()
  {
    return conditionFormStyle;
  }

  public boolean isConditionFormCustom()
  {
    return conditionFormCustom;
  }

  public QueryConfigExtraPart getResultBottomExtra()
  {

    return resultBottomExtra;
  }

  public void setConditionFormName(String conditionFormName)
  {
    this.conditionFormName = conditionFormName;
  }

  public void setResultFormName(String resultFormName)
  {
    this.resultFormName = resultFormName;
  }

  public void setHiddenColNames(String[] hiddenColNames)
  {
    this.hiddenColNames = hiddenColNames;
  }

  public void setOnTableLoad(String onTableLoad)
  {
    this.onTableLoad = onTableLoad;
  }

  public void setTableId(String tableId)
  {
    this.tableId = tableId;
  }

  public void setCanResize(boolean canResize)
  {
    this.canResize = canResize;
  }

  public void setSizeList(int[] sizeList)
  {
    this.sizeList = sizeList;
  }

  public void setCurPageSize(int curPageSize)
  {
    this.curPageSize = curPageSize;
  }

  public void setCanJump(boolean canJump)
  {
    this.canJump = canJump;
  }

  public void setExternScriptSrc(String externScriptSrc)
  {
    this.externScriptSrc = externScriptSrc;
  }

  public void setExternScriptLanguage(String externScriptLanguage)
  {
    this.externScriptLanguage = externScriptLanguage;
  }

  public void setOnRowDblClick(String onRowDblClick)
  {
    this.onRowDblClick = onRowDblClick;
  }

  public void setColConfig(QueryColExhibit[] colConfig)
  {
    this.colConfig = colConfig;
  }

  public void setDataList(ArrayList dataList)
  {
    this.dataList = dataList;
  }

  public void setSumList(String[] sumList)
  {
    this.sumList = sumList;
  }

  public void setRecordCount(int recordCount)
  {
    this.recordCount = recordCount;
  }

  public void setConditionHTML(String conditionHTML)
  {
    this.conditionHTML = conditionHTML;
  }

  public void setHasNextPage(boolean hasNextPage)
  {
    this.hasNextPage = hasNextPage;
  }

  public void setCurrentPageNo(int currentPageNo)
  {
    this.currentPageNo = currentPageNo;
  }

  public void setColsTitle(String[] colsTitle)
  {
    this.colsTitle = colsTitle;
  }

  public void setPageSize(int pageSize)
  {
    this.pageSize = pageSize;
  }

  public void setColsCount(int colsCount)
  {
    this.colsCount = colsCount;
  }

  public void setNeedStat(boolean needStat)
  {

    this.needStat = needStat;
  }

  public void setFirstShow(boolean firstShow)
  {

    this.firstShow = firstShow;
  }

  public void setPageCount(int pageCount)
  {
    this.pageCount = pageCount;
  }

  public void setNeedCond(boolean needCond)
  {

    this.needCond = needCond;
  }

  public void setNeedNavTop(boolean needNavTop)
  {
    this.needNavTop = needNavTop;
  }

  public void setNeedNavBottom(boolean needNavBottom)
  {
    this.needNavBottom = needNavBottom;
  }

  public void setCondTopExtra(QueryConfigExtraPart condTopExtra)
  {
    this.condTopExtra = condTopExtra;
  }

  public void setCondBottomExtra(QueryConfigExtraPart condBottomExtra)
  {
    this.condBottomExtra = condBottomExtra;
  }

  public void setPageBottomExtra(QueryConfigExtraPart pageBottomExtra)
  {
    this.pageBottomExtra = pageBottomExtra;
  }

  public void setQuerySortCol(String querySortCol)
  {

    this.querySortCol = querySortCol;
  }

  public void setQuerySortDir(String querySortDir)
  {

    this.querySortDir = querySortDir;
  }

  public void setFixRowsCount(boolean fixRowsCount)
  {
    this.fixRowsCount = fixRowsCount;
  }

  public void setResultFormMethod(String resultFormMethod)
  {
    this.resultFormMethod = resultFormMethod;
  }

  public void setResultFormAction(String resultFormAction)
  {
    this.resultFormAction = resultFormAction;
  }

  public void setResultFormContent(String resultFormContent)
  {
    this.resultFormContent = resultFormContent;
  }

  public void setNeedQueryButton(boolean needQueryButton)
  {
    this.needQueryButton = needQueryButton;
  }

  public void setConditionFormStyle(String conditionFormStyle)
  {
    this.conditionFormStyle = conditionFormStyle;
  }

  public void setConditionFormCustom(boolean conditionFormCustom)
  {
    this.conditionFormCustom = conditionFormCustom;
  }

  public void setResultBottomExtra(QueryConfigExtraPart resultBottomExtra)
  {

    this.resultBottomExtra = resultBottomExtra;
  }

  /**
   * 宏替换




   * 将'$$'=>'$','$[0-9]+'=>值,其它不变
   * @param src String 源字符串
   * @param values String[] 宏替换值




   * @return StringBuffer
   */
  public static StringBuffer MacroReplace(String src, String[] values, String[] sysArgs)
  {
    StringBuffer sb = new StringBuffer();
    if(src == null || src.length() == 0)
      return sb;

    char c;
    boolean isSys = false;

    int len = src.length(), idx;

    for(int i = 0; i < len; i++)
    {
      c = src.charAt(i);
      if(c != '$' || i == len - 1)
        sb.append(c);
      else
      {
        c = src.charAt(i + 1);
        if(c == '$')
        {
          sb.append(c);
          i++;
        }
        else if(c == '_' || (c >= '0' && c <= '9'))
        {
          isSys = (c == '_');
          if(!isSys)
            idx = c - '0';
          else
          {
            idx = 0;
            i++;
          }

          for(i += 2; i < len; i++)
          {
            c = src.charAt(i);
            if(c >= '0' && c <= '9')
              idx = idx * 10 + (c - '0');
            else
            {
              i--;
              break;
            }
          }

          if(!isSys)
            sb.append(values[idx - 1] != null ? values[idx - 1] : "");
          else
            sb.append(sysArgs[idx]);
        }
        else
          sb.append('$');
      }
    }

    return sb;
  }

  /**
   * 对源文本进行HTML编码,如果编码后字符串为空,则输出"&nbsp;"
   * @param src String
   * @return StringBuffer
   */
  public static StringBuffer HTMLEncodeNotEmpty(String src)
  {
    StringBuffer sb = HTMLEncode(src);
    if(sb.length() == 0)
      sb.append("&#32;");
    return sb;
  }

  /**
   * 对源文本进行HTML编码
   * @param src String
   * @return StringBuffer
   */
  public static StringBuffer HTMLEncode(String src)
  {
    StringBuffer sb = new StringBuffer();
    if(src == null || src.length() == 0)
      return sb;

    char c;
    for(int i = 0; i < src.length(); i++)
    {
      c = src.charAt(i);
      switch(c)
      {
        case ' ':
          sb.append("&#32;");
          break;
        case '\"':
          sb.append("&#34;");
          break;
        case '&':
          sb.append("&#38;");
          break;
        case '>':
          sb.append("&#62;");
          break;
        case '<':
          sb.append("&#60;");
          break;
        default:
          sb.append(c);
      }
    }
    return sb;
  }

  public boolean isNeedSelallBottom()
  {
    return needSelallBottom;
  }

  public void setNeedSelallBottom(boolean needSelallBottom)
  {
    this.needSelallBottom = needSelallBottom;
  }

  public void setCheckBoxNameBottom(String checkBoxNameBottom)
  {
    this.checkBoxNameBottom = checkBoxNameBottom;
  }

  public String getCheckBoxNameBottom()
  {
    return checkBoxNameBottom;
  }

  public boolean isNeedrequest() {
    return needrequest;
  }

  public void setNeedrequest(boolean needrequest) {
    this.needrequest = needrequest;
  }  
}
