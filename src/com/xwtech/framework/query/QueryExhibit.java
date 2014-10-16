package com.xwtech.framework.query;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.jdom.Element;
import com.xwtech.framework.pub.utils.RequestUtils;
import com.xwtech.framework.query.bo.QueryValue;
import com.xwtech.framework.query.view.*;

/**
 *
 * <p>Title: 查询页面呈现控制</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: xwtech.com</p>
 *
 * @author 邢正俊
















 */
public class QueryExhibit
{
  //配置变量
  private String skinClass = "default";

  //其它内部变量
  private HttpServletRequest request = null; //Request对象

  private QueryInfo qinfo = null;//查询信息

  //外部调用
  private Element configRoot = null; //配置文件的resultview节点对象

  private boolean permitJump = true; //是否强制禁止跳转

  private boolean permitSort = true; //是否强制禁止排序

  private QueryValue queryValue;
  /**
   * 构造函数















   */
  public QueryExhibit(HttpServletRequest request)
  {
    this.request = request;
    this.qinfo = new QueryInfo();
  }

  /**
   * 读取页面呈现XML配置信息
   */
  private void readConfigInfo() throws Exception
  {
    Element ele = this.configRoot;
    String text;

    //<neednavtop>
    this.qinfo.setNeedNavTop(QueryExhibitUtil.getSingleChildValueNotEmpty(ele, "neednavtop", "false").equalsIgnoreCase("true"));
    //<neednavbottom>
    this.qinfo.setNeedNavBottom(QueryExhibitUtil.getSingleChildValueNotEmpty(ele, "neednavbottom", "false").equalsIgnoreCase("true"));
    //<needSelallbottom>
    this.qinfo.setNeedSelallBottom(QueryExhibitUtil.getSingleChildValueNotEmpty(ele, "needselallbotton", "false").equalsIgnoreCase("true"));
    String checkBoxNamebottom = QueryExhibitUtil.getSingleChildAttributeNotEmpty(ele, "needselallbotton", "name", null);
    this.qinfo.setCheckBoxNameBottom(checkBoxNamebottom);
    //<condtopextra>
    this.qinfo.setCondTopExtra(new QueryConfigExtraPart(QueryConfigExtraPart.AREA_TYPE_COND_TOP,ele.getChild("condtopextra")));
    //<condbottomextra>
    this.qinfo.setCondBottomExtra(new QueryConfigExtraPart(QueryConfigExtraPart.AREA_TYPE_COND_BOTTOM,ele.getChild("condbottomextra")));
    //<listbottomextra>
    this.qinfo.setResultBottomExtra(new QueryConfigExtraPart(QueryConfigExtraPart.AREA_TYPE_RESULT_BOTTOM,ele.getChild("resultbottomextra")));
    //<pagebottomextra>
    this.qinfo.setPageBottomExtra(new QueryConfigExtraPart(QueryConfigExtraPart.AREA_TYPE_LISTPAGE_BOTTOM,ele.getChild("pagebottomextra")));
    //<conditionform name="">name属性：条件区域所在表单名称















    text = QueryExhibitUtil.getSingleChildAttributeNotEmpty(ele, "conditionform", "name", null);
    if(text == null)
      text = "frm_querycondition_" + Long.toHexString(this.qinfo.getCurrentSequenceid());
    this.qinfo.setConditionFormName(text);

    this.qinfo.setNeedrequest(QueryExhibitUtil.getSingleChildAttributeNotEmpty(ele, "conditionform", "needrequest", "true").equals("true"));
    //<conditionform style="">
    this.qinfo.setConditionFormStyle(QueryExhibitUtil.getSingleChildAttributeNotEmpty(ele,"conditionform","style",null));
    //<conditionform custom="">
    this.qinfo.setConditionFormCustom(QueryExhibitUtil.getSingleChildAttributeNotEmpty(ele, "conditionform","custom", "false").equalsIgnoreCase("true"));
    //<resultform name="">name属性：查询列表所在表单名称















    this.qinfo.setResultFormName(QueryExhibitUtil.getSingleChildAttributeNotEmpty(ele, "resultform", "name", null));
    if (this.qinfo.isNeedrequest())
      this.qinfo.setResultFormAction(QueryExhibitUtil.getSingleChildAttributeNotEmpty(ele, "resultform", "action", QueryExhibitUtil.getRequesstURL(this.request)));
    else
      this.qinfo.setResultFormAction(QueryExhibitUtil.getSingleChildAttributeNotEmpty(ele, "resultform", "action", QueryExhibitUtil.getRequesstURLNoRequest(this.request)));
    //<resultform method="">结果列表表单提交方法
    this.qinfo.setResultFormMethod(QueryExhibitUtil.getSingleChildAttributeNotEmpty(ele, "resultform", "method", "POST"));
    //<resultform>其resultform内容
    this.qinfo.setResultFormContent(QueryExhibitUtil.getSingleChildValueNotEmpty(ele,"resultform",null));
    //<hiddencolname>要隐藏列的域名称
    text = QueryExhibitUtil.getSingleChildValueNotEmpty(ele, "hiddencolname", null);
    if(text == null || text.length() == 0)
      this.qinfo.setHiddenColNames(new String[0]);
    else
      this.qinfo.setHiddenColNames(text.split(","));
    //<needquerybutton>
    this.qinfo.setNeedQueryButton(!QueryExhibitUtil.getSingleChildValueNotEmpty(ele, "needquerybutton", "true").equalsIgnoreCase("false"));
    //<tableview>
    ele = ele.getChild("tableview");
    if(ele == null)
      throw new Exception("查询配置XML中没有<tableview>配置项");
    //onload,当列表进行初始化展现时的处理方法名称
    this.qinfo.setOnTableLoad(QueryExhibitUtil.getAttributeNotEmpty(ele, "onload", null));
    //查询表格的ID名称。















    this.qinfo.setTableId("query_table_" + Long.toHexString(this.qinfo.getCurrentSequenceid()));
    //<skinclass>查询样式类型
    this.skinClass = QueryExhibitUtil.getSingleChildValueNotEmpty(ele,"skinclass","");
    //<resize sizelist="">用户自己选择页面大小
    this.qinfo.setCanResize(QueryExhibitUtil.getSingleChildValueNotEmpty(ele, "resize", "false").equalsIgnoreCase("true"));
    text = QueryExhibitUtil.getSingleChildAttributeNotEmpty(ele, "resize", "sizelist", null);
    if(text == null || text.length() == 0)
      this.qinfo.setSizeList(new int[0]);
    else
    {
      String[] list = text.split(",");
      int[] arr = new int[list.length];
      for(int i=0;i<list.length;i++)
        arr[i] = Integer.parseInt(list[i]);
      this.qinfo.setSizeList(arr);
    }
    this.qinfo.setCurPageSize(RequestUtils.getIntParameter(this.request,"query_pagesize",0));
    //<navigate jump="true">是否允许用户任意跳转
    if(!this.permitJump)
      this.qinfo.setCanJump(false);
    else
      this.qinfo.setCanJump(!QueryExhibitUtil.getSingleChildAttributeNotEmpty(ele, "navigate", "jump", "true").equalsIgnoreCase("false"));
    //<fixrowscount>对于分页查询是否固定显示行总数
    this.qinfo.setFixRowsCount(!QueryExhibitUtil.getSingleChildAttributeNotEmpty(ele, "navigate", "fixrowscount", "true").equalsIgnoreCase("false"));
    //<externscript language="">外部脚本引用
    this.qinfo.setExternScriptSrc(QueryExhibitUtil.getSingleChildAttributeNotEmpty(ele, "externscript", "src", null));
    this.qinfo.setExternScriptLanguage(QueryExhibitUtil.getSingleChildAttributeNotEmpty(ele, "externscript", "language", "javascript"));
    //<trview>行视图定义















    ele = ele.getChild("trview");
    if(ele == null) return;
    //ondblclick处理方法
    this.qinfo.setOnRowDblClick(QueryExhibitUtil.getAttributeNotEmpty(ele, "ondblclick", null));
    //<tdview>列视图定义















    QueryColExhibit[] colConfig = new QueryColExhibit[this.qinfo.getColsCount() + 1];
    for(int i = 1; i <= this.qinfo.getColsCount(); i++)
      colConfig[i] = new QueryColExhibit();

    List list = ele.getChildren("tdview");
    for(int i = 0; i < list.size(); i++)
    {
      ele = (Element)list.get(i);
      //colnum：对应于记录集中的列序号
      int col = QueryExhibitUtil.getAttributeNotEmpty(ele, "colnum", 0);
      if(col < 1 || col > this.qinfo.getColsCount())
      {
        System.err.println("第" + i + "<tdview>中的colnum属性不是一个有效的列序号");
        continue;
      }
      QueryColExhibit colStyle = colConfig[col];
      //align：对齐属性















      colStyle.Align = QueryExhibitUtil.getAttributeNotEmpty(ele, "align", null);
      //width：列宽度
      colStyle.Width = QueryExhibitUtil.getAttributeNotEmpty(ele, "width", null);
      //sortable：是否排序















      colStyle.Sortable = QueryExhibitUtil.getAttributeNotEmpty(ele, "sortable", "false").equalsIgnoreCase("true");
      if(colStyle.Sortable && !this.permitSort)
        colStyle.Sortable = false;
      //<exhibit>具体展示定义
      ele = ele.getChild("exhibit");
      if(ele == null)
        continue;
      //<type>展示类型
      colStyle.ExhibitType = QueryExhibitUtil.getSingleChildValueNotEmpty(ele, "type", "raw");
      if(!colStyle.ExhibitType.equalsIgnoreCase("raw")
          && !colStyle.ExhibitType.equalsIgnoreCase("link")
          && !colStyle.ExhibitType.equalsIgnoreCase("checkbox"))
        colStyle.ExhibitType = "raw";
      //<raw>要展示的模板
      if(colStyle.ExhibitType.equalsIgnoreCase("raw"))
        colStyle.ExhibitRawTemplate = QueryExhibitUtil.getSingleChildValueNotEmpty(ele, "raw", null);
      //<link>
      else if(colStyle.ExhibitType.equalsIgnoreCase("link"))
      {
        //target属性：新页目标
        colStyle.ExhibitLinkTarget = QueryExhibitUtil.getSingleChildAttributeNotEmpty(ele, "link", "target", "_blank");
        if(colStyle.ExhibitLinkTarget == null || colStyle.ExhibitLinkTarget.length() == 0)
          colStyle.ExhibitLinkTarget = "_blank";
        //title属性：提示文本
        colStyle.ExhibitLinkTitle = QueryExhibitUtil.getSingleChildAttributeNotEmpty(ele, "link", "title", null);
        //href属性：链接内容
        colStyle.ExhibitLinkHref = QueryExhibitUtil.getSingleChildAttributeNotEmpty(ele, "link", "href", null);
        //onclick：处理方法















        colStyle.onExhibitLinkClick = QueryExhibitUtil.getSingleChildAttributeNotEmpty(ele, "link", "onclick", null);
        //link显示内容
        colStyle.ExhibitLinkText = QueryExhibitUtil.getSingleChildValueNotEmpty(ele, "link", null);
      }
      //<checkbox>
      else if(colStyle.ExhibitType.equalsIgnoreCase("checkbox"))
      {
        //name：复选框的名称















        colStyle.ExhibitCheckBoxName = QueryExhibitUtil.getSingleChildAttributeNotEmpty(ele, "checkbox", "name", null);
        if(colStyle.ExhibitCheckBoxName == null || colStyle.ExhibitCheckBoxName.length() == 0)
          colStyle.ExhibitCheckBoxName = "query_chkcol_" + col + "_" + Long.toHexString(this.qinfo.getCurrentSequenceid());
        //onclick：处理方法















        colStyle.onExhibitCheckBoxClick = QueryExhibitUtil.getSingleChildAttributeNotEmpty(ele, "checkbox", "onclick", null);
        //<checkall>
        Element e = ele.getChild("checkbox");
        if(e == null)
          continue;
        e = e.getChild("checkall");
        if(e != null)
        {
          //<type>全选模
          colStyle.ExhibitCheckAllType = QueryExhibitUtil.getSingleChildValueNotEmpty(e, "type", "all");
          //<raw onclick="">如果为“raw”模式，则自己定义处理方式






          if (colStyle.ExhibitCheckAllType.equalsIgnoreCase("all"))
          {
            if (this.qinfo.isNeedSelallBottom()==true) {
              colStyle.ExhibitCheckAllType = "noall";
            }
          }
          if(colStyle.ExhibitCheckAllType.equalsIgnoreCase("raw"))
            colStyle.onExhibitCheckAllRawClick = QueryExhibitUtil.getSingleChildAttributeNotEmpty(e, "raw", "onclick", null);
        }
        //<stat>页面列统计定义















        e = ele.getChild("checkbox").getChild("stat");
        if(e != null)
        {
          List statList = e.getChildren("col");
          ArrayList lstStatCols = new ArrayList();
          for(int j = 0; j < statList.size(); j++)
          {
            e = (Element)statList.get(j);
            int statCol = QueryExhibitUtil.getAttributeNotEmpty(e, "colnum", 0);
            if(statCol < 1 || statCol > this.qinfo.getColsCount())
              continue;

            QueryColStatExhibit qc = new QueryColStatExhibit();
            qc.ColNum = statCol;
            qc.StatMode = QueryExhibitUtil.getAttributeNotEmpty(e, "mode", "sum");
            lstStatCols.add(qc);
          }
          if(lstStatCols.size() > 0)
          {
            colStyle.StatCols = new QueryColStatExhibit[lstStatCols.size()];
            for(int j = 0; j < lstStatCols.size(); j++)
              colStyle.StatCols[j] = (QueryColStatExhibit)lstStatCols.get(j);
            //设置需要统计标志















            this.qinfo.setNeedStat(true);
          }
        }
      }
    }

    this.qinfo.setColConfig(colConfig);
  }

  /**
   * 设置数据列表
   * @param dataList ArrayList
   */
  public void setDataList(ArrayList dataList)
  {
    this.qinfo.setDataList(dataList);
  }

  /**
   * 设置统计列数据数组，没有则为null
   * @param sumList String[]
   */
  public void setSumList(String[] sumList)
  {
    this.qinfo.setSumList(sumList);
  }

  /**
   * 设置符合条件的总记录数，-1表示没有设置
   * @param recordCount int
   */
  public void setRecordCount(int recordCount)
  {
    this.qinfo.setRecordCount(recordCount);
  }

  /**
   * 设置条件区域的HTML代码
   * @param conditionHTML String
   */
  public void setConditionHTML(String conditionHTML)
  {
    this.qinfo.setConditionHTML(conditionHTML);
  }

  /**
   * 设置配置XML中的<resultview>节点
   * @param configRoot Element
   */
  public void setConfigRoot(Element configRoot)
  {
    this.configRoot = configRoot;
  }

  /**
   * 设置统计及查询的配置信息
   * @param queryValue QueryValue
   */
  public void setQueryValue(QueryValue queryValue)
  {
    this.queryValue = queryValue;
  }

  /**
   * 设置是否有下一页















   * @param hasNextPage boolean
   */
  public void setHasNextPage(boolean hasNextPage)
  {
    this.qinfo.setHasNextPage(hasNextPage);
  }

  /**
   * 设置当前页码
   * @param currentPageNo int
   */
  public void setCurrentPageNo(int currentPageNo)
  {
    this.qinfo.setCurrentPageNo(currentPageNo);
  }

  /**
   * 设置列标题















   * @param colsTitle String[]
   */
  public void setColsTitle(String[] colsTitle)
  {
    this.qinfo.setColsTitle(colsTitle);
    this.qinfo.setColsCount(colsTitle.length);
  }

  public void setPageSize(int pageSize)
  {
    this.qinfo.setPageSize(pageSize);
  }

  /**
   * 设置是否强制禁止跳转
   */
  public void setForceNoJump()
  {
    this.permitJump = false;
  }

  /**
   * 设置是否强制禁止排序
   */
  public void setForceNoSort()
  {
    this.permitSort = false;
  }

  /**
   * 生成HTML
   * @return String
   * @throws Exception
   */
  public String toHTML()
    throws Exception
  {
    //统计
    if(this.queryValue.groupList != null)
    {
      StatView statView = new StatView();
      statView.setQueryValue(this.queryValue);
      statView.setConditionHTML(this.qinfo.getConditionHTML());
      statView.setFirstShow(RequestUtils.getIntParameter(this.request,"stat_firstShow",1));
      return statView.getHTML(this.request);
    }

    //如果是首次展现,则初始化一个空数据列表
    if(this.qinfo.getDataList() == null)
    {
      this.qinfo.setFirstShow(true);
      this.qinfo.setDataList(new ArrayList());
    }

    //读取页面呈现配置
    readConfigInfo();

    //计算总页数














    if(this.qinfo.getRecordCount() >= 0)
    {
      if(this.qinfo.getRecordCount() > 0)
        this.qinfo.setPageCount((this.qinfo.getRecordCount() - 1) / this.qinfo.getPageSize() + 1);
      else
        this.qinfo.setPageCount(0);
    }

    //处理排序参数
    this.qinfo.setQuerySortCol(RequestUtils.getStringParameter(this.request, "query_sortcol",""));
    this.qinfo.setQuerySortDir(RequestUtils.getStringParameter(this.request,"query_sortdir",""));

    //进行全局参数调整
    if(this.qinfo.getPageSize() <= 0)
    {
      this.qinfo.setNeedNavTop(false);
      this.qinfo.setNeedNavBottom(false);
    }
    //如果需要统计且没有设置结果表单名,则设置默认结果表单名
    if(this.qinfo.isNeedStat() && this.qinfo.getResultFormName() == null)
      this.qinfo.setResultFormName("frm_querylist_" + Long.toHexString(this.qinfo.getCurrentSequenceid()));
    //todo:如果需要排序,则必须输出表单














    //如果没有条件区域的HTML文本,则设置不需要显示条件区域













    this.qinfo.setNeedCond(this.qinfo.getConditionHTML() != null);

    //获取视图
    IQueryView qv = null;
    if(this.skinClass.equals("user-top"))
      qv = new QueryUserTopView();
    else if(this.skinClass.equals("admin-default"))
      qv = new QueryAdminDefaultView();
    else if(this.skinClass.equals("admin-top"))
      qv = new QueryAdminTopView();
    else if(this.skinClass.equals("diy-top"))
      qv = new QueryDiyUserTopView();
    else if(this.skinClass.equals("diy-top-person"))
      qv = new QueryDiyUserPersonTopView();
    else if(this.skinClass.equals("diy-top-search"))
      qv = new QueryDiySearchView();
    else if(this.skinClass.equals("diy-top-iframe"))
      qv = new QueryDiyTopIframeView();
    else if(this.skinClass.equals("diy-personal-ring"))
      qv = new QueryDiyUserPersonalRingView();
    else if(this.skinClass.equals("group-top"))
      qv = new QueryGroupCalling();
    else if(this.skinClass.equals("group-top-measure"))
      qv = new QueryGroupMeasure();
    else if(this.skinClass.equals("diy-top-person-detail"))
      qv = new QueryDiyPersonDetail();
    else if(this.skinClass.equals("diy-top-person-iframe"))
      qv = new QueryDiyPersonIframe();
    else if(this.skinClass.equals("group-top-search"))
      qv = new QueryGroupSearch();
    else if(this.skinClass.equals("new-upload"))
      qv = new QueryNewUploadRingsView();
    else if(this.skinClass.equals("lt-type"))
      qv = new QueryDiyLTUserTopView();
    else if(this.skinClass.equals("new-indite"))
      qv = new QueryNewInditeRings();
    else if(this.skinClass.equals("recommend-indite"))
      qv = new QueryRecommendInditeRings();
    else
      qv = new QueryDefaultView();

    return qv.getHTML(this.qinfo,this.request,this.queryValue).toString();
  }
}
