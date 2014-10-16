package com.xwtech.framework.query.view;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Stack;

import com.xwtech.framework.query.bo.*;
import javax.servlet.http.HttpServletRequest;

public class StatView
{
  private static final String Empty_String = "";
  private static final String[] Empty_StringArray = new String[0];
  private static final String[][] Empty_StringArrayArray = new String[0][0];

  private StatInfo info; //数据信息
  private StatNode root; //根节点


  private QueryValue queryValue;
  private String conditionHTML;
  private int firstShow;

  /**
   * 输出钻取标题
   * @param node StatNode
   * @param out PrintWriter
   */
  private void outputDrillLabel(StatNode node, PrintWriter out)
  {
    String url = this.queryValue.drillURL;

    //不需要钻取
    if(url == null || url.length() == 0
      || node.isPlaceHolderStatQ())
    {
      out.print(QueryInfo.HTMLEncode(node.title));
      return;
    }

    StatDimMetaInfo[] dims = null;
    if(node.isDimTypeX())
      dims = this.info.xDim;
    else if(node.isDimTypeY())
      dims = this.info.yDim;

    int level = -1;
    ArrayList list = new ArrayList();
    StatNode cNode = node;
    while(cNode.dimType == node.dimType)
    {
      list.add(cNode);
      level ++;
      cNode = cNode.parent;
    }

    //需要钻取
    if(!dims[level].needDrill)
    {
      out.print(QueryInfo.HTMLEncode(node.title));
      return;
    }

    String pat = "";
    for(int i=0;i<=level;i++)
    {
      if(i > 0) pat += "&";
      pat += dims[i].fldName + "=" + ((StatNode)list.get(level - i)).key;
    }

    if(url.indexOf('?') == -1)
      url += "?" + pat;
    else
      url += "&" + pat;

    out.print("<span style='cursor:hand;' title='双击此处进行该条件下的钻取' ondblclick=\"javascript:frmQuery.action = '" + url + "';frmQuery.submit();\">" + QueryInfo.HTMLEncode(node.title) + "</span>");
  }

  /**
   * 对数据对应的树叶节点进行数据填充
   * @param pNode StatNode
   * @param data String[]
   * @param idx int
   */
  private void FillSingleTreeData(StatNode pNode, String[] data, int idx)
  {
    //子节点是统计节点,则赋值
    if(pNode.children != null && pNode.children.length>0 && pNode.children[0].isDimTypeQ())
    {
      StatNodeQ qNode = (StatNodeQ)pNode.children[0];
      for(int i=0;i<qNode.values.length;i++)
      {
        qNode.values[i] = data[idx+i];
      }
      return;
    }


    //非统计节点,继续进行深度遍历查找
    for (int i = 0; i < pNode.children.length; i++)
    {
      if (pNode.children[i].key.equals(data[idx]))
      {
        FillSingleTreeData(pNode.children[i], data, idx + 1);
        break;
      }
    }
  }

  /**
   * 开始对占位树进行数据填充
   */
  private void BuildDataTree()
  {
    for(int i=0;i<this.info.dataList.size();i++)
    {
      String[] data = (String[])this.info.dataList.get(i);

      //如果有X维度,则从根开始

      if(!this.info.isNoXDim())
        FillSingleTreeData(this.root, data, 0);
      //否则从占位X维度开始
      else
      {
        for(int j=0;j<this.root.children.length;j++)
        {
          FillSingleTreeData(this.root.children[j],data,0);
        }
      }
    }
  }

  /**
   * 取得同一维度的指定层次的父节点
   * @param pNode StatNode
   * @param level int
   * @return StatNode
   */
  private StatNode getParentBySpecifyLevel(StatNode pNode, int level)
  {
    ArrayList list = new ArrayList();

    //向下回溯取得所有父节点
    StatNode parent = pNode;
    while(parent != null && parent.dimType == pNode.dimType)
    {
      list.add(parent);
      parent = parent.parent;
    }

    return (StatNode)list.get(list.size()-level);
  }

  /**
   * 获取同维度的父级关系信息
   *
   * @param dimType StatNode
   * @param level int
   * @return StatDimRelationInfo
   */
  private StatDimRelationInfo getParentRelation(int dimType, int level)
  {
    StatDimRelationInfo[] rels = null;
    if(dimType == StatNode.DIM_TYPE_X)
      rels = info.relXDim;
    else if(dimType == StatNode.DIM_TYPE_Y)
      rels = info.relYDim;

    for(int i=0;i<rels.length;i++)
    {
      if(rels[i].childLevel == level)
        return rels[i];
    }

    return null;
  }

  /**
   * 获取有效的元素信息
   *
   * @param dimType StatNode
   * @param pNode int
   * @param pLevel int
   * @return String[]
   */
  private String[][] getValidChildMetaInfo(int dimType, StatNode pNode, int pLevel)
  {
    String[][] metas = Empty_StringArrayArray;
    StatDimRelationInfo rel = getParentRelation(dimType, pLevel + 1);

    String[][] meta = null;
    if(dimType == StatNode.DIM_TYPE_X)
      meta = info.xDim[pLevel].meta;
    else if(dimType == StatNode.DIM_TYPE_Y)
      meta = info.yDim[pLevel].meta;

    //和所有父级如果没有关系,则返回所有元数据信息
    if(rel == null)
    {
      metas = new String[meta.length][2];
      for(int i=0;i<metas.length;i++)
      {
        metas[i][0] = meta[i][0];
        metas[i][1] = meta[i][1];
      }
    }
    //否则到关系表中去找

    else
    {
      StatNode spNode = getParentBySpecifyLevel(pNode,rel.parentLevel);

      //从关系表中找合适的对应项

      ArrayList list = new ArrayList();
      for(int i=0;i<rel.keyMap.length;i++)
      {
        if(spNode.key.equals(rel.keyMap[i][0]))
        {
          for(int j=0;j<meta.length;j++)
          {
            if(rel.keyMap[i][1].equals(meta[j][0]))
            {
              list.add(rel.keyMap[i][1]);
              list.add(meta[j][1]);
              break;
            }
          }
        }
      }

      metas = new String[list.size()/2][2];
      for(int i=0;i<list.size();i+=2)
      {
        metas[i/2][0] = (String)list.get(i);
        metas[i/2][1] = (String)list.get(i+1);
      }
    }

    return metas;
  }

  /**
   * 遍历所有Y维的叶节点,然后生成统计节点
   * @param pNode StatNode
   */
  private void PrepareBuildStatTree(StatNode pNode)
  {
    //如果没有子节点,则生成统计节点

    if(pNode.children == null)
    {
      StatNodeQ qNode = new StatNodeQ(pNode);
      qNode.values = new String[this.info.statQ.length];

      pNode.children = new StatNode[1];
      pNode.children[0] = qNode;
    }
    //继续深度遍历
    else
    {
      for(int i=0;i<pNode.children.length;i++)
      {
        PrepareBuildStatTree(pNode.children[i]);
      }
    }
  }

  /**
   * 生成Y维度的上部树
   * @param node StatNode
   * @param pLevel int
   */
  private void BuildYHalfTree(StatNode node, int pLevel)
  {
    // 如果当前是Y维度的最后一层,则立即返回

    if(pLevel == info.yDim.length)
      return;

    //取得当前维度的有效元数据
    String[][] metas = getValidChildMetaInfo(StatNode.DIM_TYPE_Y, node, pLevel);

    //计算子节点数
    int m = metas.length;
    if(this.info.yDim[pLevel - 1].needStat)
      m++;
    node.children = new StatNode[m];

    //添加当前Y维度
    for(int i=0;i<metas.length;i++)
    {
      StatNodeY yNode = new StatNodeY(node);
      yNode.key = metas[i][0];
      yNode.title = metas[i][1];
      node.children[i] = yNode;

      //生成Y维的子树
      BuildYHalfTree(yNode,pLevel + 1);
    }

    //如果父级具有小计,则添加占位分支维
    if(this.info.yDim[pLevel - 1].needStat)
    {
      StatNodeY yNode = new StatNodeY(node);
      yNode.phType = StatNode.PH_TYPE_STATQ;
      yNode.key = StatNode.KEY_SUB_TOTAL;
      yNode.title = "小计";
      node.children[m-1] = yNode;
      node = yNode;

      //添加统计数据
      node.children = new StatNodeQ[1];
      StatNodeQ qNode = new StatNodeQ(node);
      qNode.values = new String[this.info.statQ.length];
      node.children[0] = qNode;
    }
  }

  /**
   * 遍历所有X维的叶节点,然后生成Y维度的上部树
   *
   * @param pNode StatNode
   */
  private void PrepareBuildYHalfTree(StatNode pNode)
  {
    //如果没有子节点,则开始生成Y上部树

    if(pNode.children == null)
    {
      //计算子节点数
      int m = info.yDim[0].meta.length;
      if(this.info.needYStat)
        m ++;

      //生成Y维第一级树
      pNode.children = new StatNodeY[m];
      for(int i=0;i<info.yDim[0].meta.length;i++)
      {
        StatNodeY yNode = new StatNodeY(pNode);
        yNode.key = info.yDim[0].meta[i][0];
        yNode.title = info.yDim[0].meta[i][1];
        pNode.children[i] = yNode;

        //生成X维的子树
        BuildYHalfTree(yNode,1);
      }

      //添加Y维的总计行
      if(this.info.needYStat)
      {
        StatNodeY yNode = new StatNodeY(pNode);
        yNode.phType = StatNode.PH_TYPE_STATQ;
        yNode.key = StatNode.KEY_SUM_TOTAL;
        yNode.title = "总计";
        pNode.children[m - 1] = yNode;
        pNode = yNode;

        //添加统计数据
        pNode.children = new StatNodeQ[1];
        StatNodeQ qNode = new StatNodeQ(pNode);
        qNode.values = new String[this.info.statQ.length];
        pNode.children[0] = qNode;
      }
    }
    else
    {
      //继续进行深度遍历
      for (int i = 0; i < pNode.children.length; i++)
      {
        PrepareBuildYHalfTree(pNode.children[i]);
      }
    }
  }

  /**
   * 生成X维度的下部树
   *
   * @param node StatNode
   * @param pLevel int
   */
  private void BuildXHalfTree(StatNode node, int pLevel)
  {
    // 如果当前是X维度的最后一层,则立即返回
    if(pLevel == info.xDim.length)
      return;

    //取得当前维度的有效元数据
    String[][] metas = getValidChildMetaInfo(StatNode.DIM_TYPE_X, node, pLevel);

    //添加当前X维度
    node.children = new StatNode[metas.length];
    for(int i=0;i<metas.length;i++)
    {
      StatNodeX xNode = new StatNodeX(node);
      xNode.key = metas[i][0];
      xNode.title = metas[i][1];
      node.children[i] = xNode;

      //生成X维的子树
      BuildXHalfTree(xNode,pLevel + 1);
    }
  }

  /**
   * 生成占位树
   */
  private void BuildPlaceHoldTree()
  {
    root = new StatNode();
    root.dimType = StatNode.DIM_TYPE_ROOT;

    //开始生成X维下部树
    if(info.xDim.length > 0)
    {
      //生成X维第一级树
      root.children = new StatNodeX[info.xDim[0].meta.length];
      for(int i=0;i<info.xDim[0].meta.length;i++)
      {
        StatNodeX xNode = new StatNodeX(root);
        xNode.key = info.xDim[0].meta[i][0];
        xNode.title = info.xDim[0].meta[i][1];
        root.children[i] = xNode;

        //生成X维的子树
        BuildXHalfTree(xNode,1);
      }
    }

    //遍历所有X维的叶节点,然后生成Y维
    PrepareBuildYHalfTree(root);

    //遍历所有Y维的叶节点,然后生成统计量节点
    PrepareBuildStatTree(root);
  }

  /**
   * 获得X维指定节点下面的叶节点
   * @param list ArrayList
   * @param pNode StatNode
   */
  private void findXLeaf(ArrayList list, StatNode pNode)
  {
    //为X维叶节点
    if(pNode.isDimTypeX() && !pNode.children[0].isDimTypeX())
    {
      list.add(pNode);
    }
    //继续进行遍历
    else
    {
      for(int i=0;i<pNode.children.length;i++)
      {
        findXLeaf(list, pNode.children[i]);
      }
    }
  }

  /**
   * 获得X维的所有叶节点列表
   * @return StatNodeX[]
   */
  private StatNodeX[] getXLeaves()
  {
    ArrayList list = new ArrayList();
    findXLeaf(list, this.root);

    StatNodeX[] xNodes = new StatNodeX[list.size()];
    for(int i=0;i<list.size();i++)
    {
      xNodes[i] = (StatNodeX)list.get(i);
    }

    return xNodes;
  }

  /**
   * 取得指Y维定节点的叶节点总数
   * @param node StatNode
   * @return int
   */
  private int getYLeavesCount(StatNode node)
  {
    if(node.children[0].isDimTypeQ())
      return 1;
    else
    {
      int n = 0;
      for(int i=0;i<node.children.length;i++)
      {
        n += getYLeavesCount(node.children[i]);
      }

      return n;
    }
  }

  /**
   * 取得指X维定节点的叶节点总数
   * @param node StatNode
   * @return int
   */
  private int getXLeavesCount(StatNode node)
  {
    if(node.children[0].isDimTypeY())
      return 1;
    else
    {
      int n = 0;
      for(int i=0;i<node.children.length;i++)
      {
        n += getXLeavesCount(node.children[i]);
      }

      return n;
    }
  }

  /**
   * 遍历输出Y维和数据列表
   *
   * @param pNodes StatNode[]
   * @param stack Stack
   * @param out PrintWriter
   */
  private void outputYDataTree(StatNode[] pNodes, Stack stack, PrintWriter out)
  {
    //到达统计量节点

    if(pNodes[0].children[0].isDimTypeQ())
    {
      out.println("<tr>");
      //输出所有Y维列
      for(int i=0;i<stack.size();i++)
      {
        StatNode node = (StatNode)stack.get(i);

        //输出该维列

        if(!node.bFlag)
        {
          out.print("\t<td");
          //非小计占位节点
          if(!node.isPlaceHolderStatQ())
          {
            //Y轴的第一维
            if (i == 0)
              out.print(" class=\"td_xy_channel\"");
            else
              out.print(" class=\"td_xy_download_setting\"");
          }
          //小计节点
          else
          {
            if(!node.key.equals(StatNode.KEY_SUM_TOTAL))
              out.print(" class=\"td_xy_area\"");
            else
              out.print(" class=\"td_xy_time\"");
          }

          int n = getYLeavesCount(node);
          if(n > 1)
            out.print(" rowspan=\"" + n + "\"");
          //如果是小计占位符,则计算列的跨度
          n = this.info.yDim.length - stack.size() + 1;
          if(n > 1)
            out.print(" colspan=\"" + n + "\"");
          out.print(">");
          outputDrillLabel(node, out);
          out.println("</td>");

          //设置维度已遍历标记
          node.bFlag = true;
        }
      }

      //输出所有统计量列
      for(int i=0;i<pNodes.length;i++)
      {
        StatNodeQ qNode = (StatNodeQ)pNodes[i].children[0];

        for(int j=0;j<qNode.values.length;j++)
        {
          out.print("\t<td");
          if(!qNode.parent.isPlaceHolderStatQ())
            out.print(" class=\"td_xy_data\" bgcolor=\"#eef7fd\"");
          else
          {
            if(!qNode.parent.key.equals(StatNode.KEY_SUM_TOTAL))
              out.print(" class=\"td_xy_data_all_1\" bgcolor=\"#eef7fd\"");
            else
              out.print(" class=\"td_xy_data_all\"");
          }
          out.print(">");
          if(qNode.values[j] != null)
            out.print(qNode.values[j]);
          out.println("</td>");
        }
      }

      //输出行统计统计量列

      if(this.info.needXStat)
      {
        //行统计

        double sum = 0;

        for(int i=0;i<this.info.statQ.length;i++)
        {
          sum = 0;
          if(!this.info.statQ[i].isTypeString())
          {
            for (int j = 0; j < pNodes.length; j++)
            {
              StatNodeQ qNode = (StatNodeQ)pNodes[j].children[0];

              if(this.info.statQ[i].isTypeLong())
                sum += this.info.statQ[i].parseLong(qNode.values[i]);
              else if(this.info.statQ[i].isTypeDouble())
                sum += this.info.statQ[i].parseDouble(qNode.values[i]);
            }
          }

          //输出
          out.print("\t<td");
          StatNode node = pNodes[0];
          if(!node.isPlaceHolderStatQ())
            out.print(" class=\"td_xy_data_all\" bgcolor=\"#eef7fd\"");
          else
          {
            if(!node.key.equals(StatNode.KEY_SUM_TOTAL))
              out.print(" class=\"td_xy_data_all_3\" bgcolor=\"#eef7fd\"");
            else
              out.print(" class=\"td_xy_data_all_2\"");
          }
          out.print("");
          out.print(">");
          if(this.info.statQ[i].isTypeString())
            out.print("&nbsp;");
          else
            out.print(this.info.statQ[i].formatValue(sum));
          out.println("</td>");
        }
      }

      out.println("</tr>");
    }
    //继续进行深度遍历
    else
    {
      StatNode[] cNodes = new StatNode[pNodes.length];

      for(int j=0;j<pNodes[0].children.length;j++)
      {
        stack.push(pNodes[0].children[j]);

        for (int i = 0; i < pNodes.length; i++)
        {
          cNodes[i] = pNodes[i].children[j];
        }

        outputYDataTree(cNodes, stack, out);

        stack.pop();
      }
    }
  }

  /**
   * 生成多叉树对应的文本
   *
   * @param out PrintWriter
   */
  private void buildTreeHTML(PrintWriter out)
  {
    //获得X维的所有叶节点列表
    StatNodeX[] xNodes = getXLeaves();

    //遍历输出Y维和数据列表
    Stack stack = new Stack();
    outputYDataTree(xNodes, stack, out);
  }

  /**
   * 取得X维的直接子结点

   * @param list ArrayList
   * @return ArrayList
   */
  private ArrayList getXSubChildren(ArrayList list)
  {
    StatNode node;
    ArrayList children = new ArrayList();

    //检查是否到X维最后的节点
    node = (StatNode)list.get(0);
    if(!node.children[0].isDimTypeX())
      return children;

    //获取子节点列表

    for(int i=0;i<list.size();i++)
    {
      node = (StatNode)list.get(i);
      for(int j=0;j<node.children.length;j++)
      {
        children.add(node.children[j]);
      }
    }

    return children;
  }

  /**
   * 输出X维标题和统计标题
   * @param out PrintWriter
   */
  private void outputXTitle(PrintWriter out)
  {
    int xIndex = 0;
    ArrayList list = new ArrayList(),tmpList;
    list.add(this.root);

    while(!this.info.isNoXDim())
    {
      //取得该维的所有子元素
      tmpList = getXSubChildren(list);
      if(tmpList.size() == 0)
        break;
      list = tmpList;

      //输出维标题
      out.println("<tr>");
      out.print("\t<td colspan=\"" + this.info.yDim.length + "\" class=\"td_xy_area\">");
      out.print(this.info.xDim[xIndex].title);
      out.println("</td>");
      for(int i=0;i<list.size();i++)
      {
        StatNodeX node = (StatNodeX)list.get(i);

        int n = getXLeavesCount(node) * this.info.statQ.length;
        out.print("\t<td");
        //如果为X轴的第一个维度
        if(xIndex == 0)
          out.print(" class=\"td_xy_time\"");
        else
          out.print(" class=\"td_xy_channel\"");

        if(n > 1)
          out.print(" colspan=\"" + n + "\"");
        out.print(">");
        outputDrillLabel(node, out);
        out.println("</td>");
      }

      //输出行总计标题
      if(xIndex == 0 && this.info.needXStat)
      {
        out.print("\t<td class=\"td_xy_time\"");
        if(this.info.statQ.length > 1)
          out.print(" colspan=\"" + this.info.statQ.length + "\"");
        if(this.info.xDim.length > 1)
          out.print(" rowspan=\"" + this.info.xDim.length + "\"");
        out.print(">");
        out.print("总计");
        out.println("</td>");
      }

      out.println("</tr>");

      xIndex++;
    }

    //输出统计行标题
    out.println("<tr>");
    //输出Y维标题
    for(int i=0;i<this.info.yDim.length;i++)
    {
      out.print("\t<td class=\"td_xy_area\">");
      out.print(this.info.yDim[i].title);
      out.println("</td>");
    }
    //输出统计行标题

    for(int i=0;i<list.size();i++)
    {
      for(int j=0;j<this.info.statQ.length;j++)
      {
        out.print("\t<td class=\"td_xy_download_setting\">");
        out.print(this.info.statQ[j].title);
        out.println("</td>");
      }
    }

    //输出行总计标题
    if(this.info.needXStat)
    {
      for(int i=0;i<this.info.statQ.length;i++)
      {
        out.print("\t<td class=\"td_xy_download_setting\">");
        out.print(this.info.statQ[i].title);
        out.println("</td>");
      }
    }

    out.println("</tr>");
  }

  /**
   * 生成表格部分HTML文本
   *
   * @param out PrintWriter
   */
  private void buildHTML(PrintWriter out)
  {
    out.println("<table id=\"statResult\"  border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#003DA2\" class=\"table_big\">");

    //输出X维标题

    outputXTitle(out);

    //生成多叉树对应的文本
    buildTreeHTML(out);

    out.println("</table>");
  }

  /**
   * 生成Y维某个节点的小计值
   *
   * @param pNode StatNode
   * @param totals double[]
   */
  private void buildYSubTotalData(StatNode pNode, double[] totals)
  {
    //累加小计
    if(pNode.isDimTypeQ())
    {
      StatNodeQ qNode = (StatNodeQ)pNode;
      for(int i=0;i<qNode.values.length;i++)
      {
        if(!this.info.statQ[i].isTypeString())
        {
          if(this.info.statQ[i].isTypeDouble())
          {
            totals[i] += this.info.statQ[i].parseDouble(qNode.values[i]);
          }
          else if(this.info.statQ[i].isTypeLong())
          {
            totals[i] += this.info.statQ[i].parseLong(qNode.values[i]);
          }
        }
      }
    }
    //继续遍历
    else if(!pNode.isPlaceHolderStatQ())
    {
      for(int i=0;i<pNode.children.length;i++)
      {
        buildYSubTotalData(pNode.children[i], totals);
      }
    }
  }

  /**
   * 遍历Y维的所有节点,找到要小计的节点
   *
   * @param pNodes StatNode[]
   * @param nodes StatNode[]
   * @param pNode StatNode
   */
  private void findYSubTotalNode(StatNode[] pNodes, StatNode[] nodes, StatNode pNode)
  {
    //如果是小计节点,则计算该小计节点的小计值

    if(pNode.isDimTypeY() && pNode.isPlaceHolderStatQ())
    {
      double[] totals = new double[this.info.statQ.length];
      for(int i=0;i<pNodes.length;i++)
      {
        for(int j=0;j<totals.length;j++)
          totals[j] = 0;

        buildYSubTotalData(pNodes[i], totals);

        StatNodeQ qNode = (StatNodeQ)nodes[i].children[0];
        for(int j=0;j<totals.length;j++)
          qNode.values[j] = this.info.statQ[j].formatValue(totals[j]);
      }
    }
    //如果子节点是非统计节点,则继续进行枚举

    else if(!pNode.isDimTypeQ())
    {
      StatNode[] cNodes = new StatNode[nodes.length];
      for(int i=0;i<pNode.children.length;i++)
      {
        for(int j=0;j<nodes.length;j++)
        {
          cNodes[j] = nodes[j].children[i];
        }

        findYSubTotalNode(nodes, cNodes, cNodes[0]);
      }
    }
  }

  /**
   * 计算Y维的小计和总计数据
   */
  private void FillYSubTotalData()
  {
    //获得X维的所有叶节点列表
    StatNodeX[] xNodes = getXLeaves();

    //遍历Y维的所有节点

    findYSubTotalNode(xNodes, xNodes, xNodes[0]);
  }

  /**
   * 生成结果HTML文本
   *
   * @param out PrintWriter
   */
  private void getStatHTML(PrintWriter out)
  {
    //生成数据
    BuildData();

    //生成占位树

    BuildPlaceHoldTree();

    //对生成的占位树进行数据填充

    BuildDataTree();

    //进行Y维的小计统计
    FillYSubTotalData();

    //生成表格部分HTML文本
    buildHTML(out);
  }

  /**
   * 生成纬度之间的关系
   */
  private void buildRelation()
  {
    ArrayList raList = this.queryValue.groupRalationList;
    ArrayList xRelList = new ArrayList(),yRelList = new ArrayList();

    for(int i=0;i<raList.size();i++)
    {
      GroupRelation gr = (GroupRelation)raList.get(i);

      //找出相应的维度

      Group group1 = null,group2 = null;
      ArrayList grpList = this.queryValue.groupList;
      for(int j=0;j<grpList.size();j++)
      {
        Group group = (Group)grpList.get(j);
        if(group.groupName.equals(gr.getGroupName1()))
          group1 = group;
        else if(group.groupName.equals(gr.getGroupName2()))
          group2 = group;
      }

      //如果两个维度存在
      if(group1 != null && group2 != null)
      {
        //同一维度
        if(group1.position.charAt(0) == group2.position.charAt(0))
        {
          StatDimRelationInfo rel = new StatDimRelationInfo();
          rel.parentLevel = Integer.parseInt(group1.position.substring(1));
          rel.childLevel = Integer.parseInt(group2.position.substring(1));

          rel.keyMap = new String[gr.getRelation().size()][2];
          for(int j=0;j<rel.keyMap.length;j++)
          {
            String[] vals = (String[])gr.getRelation().get(j);
            rel.keyMap[j][0] = vals[0];
            rel.keyMap[j][1] = vals[1];
          }

          if(group1.position.charAt(0) == 'x')
            xRelList.add(rel);
          else if(group1.position.charAt(0) == 'y')
            yRelList.add(rel);
        }
      }
    }

    //生成最终形式

    this.info.relXDim = new StatDimRelationInfo[xRelList.size()];
    for(int i=0;i<xRelList.size();i++)
      this.info.relXDim[i] = (StatDimRelationInfo)xRelList.get(i);

    this.info.relYDim = new StatDimRelationInfo[yRelList.size()];
    for(int i=0;i<yRelList.size();i++)
      this.info.relYDim[i] = (StatDimRelationInfo)yRelList.get(i);
  }

  /**
   * 生成内部数据
   */
  private void BuildData()
  {
    this.info = new StatInfo();
    int m,n,xCount,yCount;

    //总计控制
    this.info.needXStat = this.queryValue.needTotalX;
    this.info.needYStat = this.queryValue.needTotalY;

    //统计量
    m = this.queryValue.groupList.size();
    n = this.queryValue.title.length;
    StatMetaInfo[] statQ = new StatMetaInfo[n - m];
    for(int i=m;i<n;i++)
    {
      statQ[i - m] = new StatMetaInfo();
      statQ[i - m].title = this.queryValue.title[i];
      statQ[i - m].setStatQType(this.queryValue.dataType[i]);
      //查找浮点数的精度
      if(statQ[i - m].isTypeDouble())
      {
        statQ[i - m].format = "0.00";
        for(int j=0;j<this.queryValue.dataList.size();j++)
        {
          String[] vals = (String[])this.queryValue.dataList.get(j);
          if(vals[i] != null && vals[i].length() > 0)
          {
            int idx = vals[i].indexOf('.');
            if(idx == -1)
              statQ[i - m].format = "0";
            else
            {
              statQ[i - m].format = "0.";
              for(int k=0;k<idx;k++)
                statQ[i - m].format += "0";
            }
            break;
          }
        }
      }
    }

    info.statQ = statQ;

    //维度定义
    xCount = 0;
    yCount = 0;
    for(int i=0;i<this.queryValue.groupList.size();i++)
    {
      Group group = (Group)this.queryValue.groupList.get(i);
      if(group.position.indexOf('x') == 0)
        xCount ++;
      else if(group.position.indexOf('y') == 0)
        yCount ++;
    }

    //X维度定义
    StatDimMetaInfo[] xDim = null;
    if(xCount == 0)
    {
      xDim = new StatDimMetaInfo[1];

      xDim[0] = new StatDimMetaInfo();
      xDim[0].title = "@";
      xDim[0].meta = new String[1][2];
      xDim[0].meta[0] = new String[]{"@", "@"};

      info.setNoXDim();
    }
    else
    {
      xDim = new StatDimMetaInfo[xCount];

      for(int i=0;i<xCount;i++)
      {
        Group group = (Group)this.queryValue.groupList.get(i);

        xDim[i] = new StatDimMetaInfo();
        xDim[i].title = group.groupLabel;
        xDim[i].needDrill = group.needDrill;
        xDim[i].fldName = group.groupName;
        xDim[i].meta = new String[group.metadata.size()][2];

        for(int j=0;j<group.metadata.size();j++)
        {
          String[] vals = (String[])group.metadata.get(j);
          if(vals.length == 2)
            xDim[i].meta[j] = new String[]{vals[0],vals[1]};
          else
            xDim[i].meta[j] = new String[]{vals[0],vals[0]};
        }
      }
    }

    info.xDim = xDim;

    //Y维定义
    StatDimMetaInfo[] yDim = new StatDimMetaInfo[yCount];

    for(int i=xCount;i<xCount + yCount;i++)
    {
      Group group = (Group)this.queryValue.groupList.get(i);

      yDim[i - xCount] = new StatDimMetaInfo();
      yDim[i - xCount].title = group.groupLabel;
      yDim[i - xCount].needStat = group.needSubTotal;
      yDim[i - xCount].needDrill = group.needDrill;
      yDim[i - xCount].fldName = group.groupName;
      yDim[i - xCount].meta = new String[group.metadata.size()][2];

      for(int j=0;j<group.metadata.size();j++)
      {
        String[] vals = (String[])group.metadata.get(j);
        if(vals.length == 2)
          yDim[i - xCount].meta[j] = new String[]{vals[0],vals[1]};
        else
          yDim[i - xCount].meta[j] = new String[]{vals[0],vals[0]};
      }
    }

    info.yDim = yDim;

    //X、Y维度关系定义
    buildRelation();

    //数据
    info.dataList = this.queryValue.dataList;
  }

  /**
   * 获取正常提交的ＵＲＬ路径
   * @return String
   */
  private String getFormAction(HttpServletRequest request)
  {
    String url = request.getRequestURL().toString();
    String name = request.getParameter("name");
    if(name != null)
      url += "?name=" + name;
    return url;
  }

  /**
   * 返回全页面HTML
   *
   * @return String
   * @param request HttpServletRequest
   */
  public String getHTML(HttpServletRequest request)
  {
    StringWriter writer = new StringWriter();
    PrintWriter out = new PrintWriter(writer);

    //输出头部
    out.println("<table width='100%'  border='0' align='center' cellpadding='0' cellspacing='0' class='table_big'>");
    out.println("  <tr>");
    out.println("    <td height='40' valign='top'><img src='/framework/image/title_r2_c2.jpg' width='983' height='36'></td>");
    out.println("  </tr>");
    out.println("</table>");

    out.println("<SCRIPT LANGUAGE='JavaScript'>");
    out.println("function hideAll()");
    out.println("{");
    out.println("	frmQuery.style.display = 'none';");
//    out.println("	document.all.statResult.style.display = 'none';");
    out.println("}");

    out.println("function showAll()");
    out.println("{");
    out.println("	frmQuery.style.display = '';");
//    out.println("	document.all.statResult.style.display = '';");
    out.println("}");
    out.println("</SCRIPT>");

    out.println("<table width='100%'  border='0' cellpadding='0' cellspacing='0' class='table_big'>");
    out.println("  <tr>");
    out.println("    <td><table  border='0' align='right' cellpadding='0' cellspacing='5'>");
    out.println("      <tr>");
    out.println("        <td><img src='/framework/image/title_r22_c14.jpg' width='24' height='24'></td>");
    out.println("        <td width='60' class='link_normal'><a href='#' onclick=\"javascript:showAll();\" class='link_normal'>全部打开</a></td>");
    out.println("        <td><img src='/framework/image/title_r22_c16.jpg' width='24' height='24'></td>");
    out.println("        <td width='60'><a href='#' onclick=\"javascript:hideAll();\" class='link_normal'>全部合拢</a></td>");
    out.println("      </tr>");
    out.println("    </table></td>");
    out.println("  </tr>");
    out.println("</table>");

    //输出条件区域
    out.println("<form style='margin:0px' name='frmQuery' method='POST' action='" + getFormAction(request) + "'");
    if(this.firstShow == 0)
      out.print(" style='display:none'");
    out.println(">");
    out.println("<input type='hidden' name='stat_firstShow' value='0'>");
    out.println("<table width='100%'  border='0' cellpadding='0' cellspacing='0' class='table_big'>");
    out.println("<tr><td>");
    if(this.conditionHTML != null)
      out.println(this.conditionHTML);
    out.println("</td></tr>");
    out.println("</table>");

    out.println("<table height='60' border='0' align='center' cellpadding='0' cellspacing='20'>");
    out.println("  <tr>");
    out.println("    <td align='center'>");
    out.println("      <div align='center'>");
    out.println("        <input type='submit' class='button_xy_normal' value=' 提 交 '>");
    out.println("    </div></td>");
    out.println("    <td align='center'><input type='reset' class='button_xy_normal' value=' 重 填 '></td>");
    out.println("  </tr>");
    out.println("</table>");
    out.println("<br>");

    out.println("</form>");

    //列表数据
    if(this.firstShow == 0)
    {
      boolean bContinue = true;

      //检查元数据是否为空
      for(int i=0;i<this.queryValue.groupList.size();i++)
      {
        Group group = (Group)this.queryValue.groupList.get(i);
        if(group.metadata.size() == 0)
        {
          out.println("<div align=center>没有可以展示的统计数据</div>");
          bContinue = false;
          break;
        }
      }

      //获取HTML
      if(bContinue)
        getStatHTML(out);
    }

    return writer.toString();
  }

  /**
   * 生成测试数据
   */
  private void BuildTestData_1()
  {
    info = new StatInfo();

    info.needXStat = true;
    info.needYStat = true;

    //统计定义
    StatMetaInfo[] statQ = new StatMetaInfo[2];
 /*   statQ[0] = new StatMetaInfo();
    statQ[0].title = "投资额";
    statQ[0].type = StatMetaInfo.STAT_TYPE_DOUBLE;
    statQ[0].format = "0.0";

    statQ[1] = new StatMetaInfo();
    statQ[1].title = "完成额";
    statQ[1].type = StatMetaInfo.STAT_TYPE_LONG;
    statQ[1].format = "0.00";
*/
    info.statQ = statQ;

    //X维定义

    StatDimMetaInfo[] xDim = new StatDimMetaInfo[2];

    xDim[0] = new StatDimMetaInfo();
    xDim[0].title = "建设单位";
    xDim[0].meta = new String[2][2];
    xDim[0].meta[0] = new String[]{"NJ.","南京电信"};
    xDim[0].meta[1] = new String[]{"SZ.","苏州电信"};

    xDim[1] = new StatDimMetaInfo();
    xDim[1].title = "月份";
    xDim[1].meta = new String[2][2];
    xDim[1].meta[0] = new String[]{"Jan.","一月"};
    xDim[1].meta[1] = new String[]{"Feb.","二月"};

    info.xDim = xDim;

 //   info.setNoXDim();

    //Y维定义

    StatDimMetaInfo[] yDim = new StatDimMetaInfo[3];

    yDim[0] = new StatDimMetaInfo();
    yDim[0].title = "管理级别";
    yDim[0].needStat = true;
    yDim[0].meta = new String[2][2];
    yDim[0].meta[0] = new String[]{"Prov.","省管"};
    yDim[0].meta[1] = new String[]{"City.","市管"};

    yDim[1] = new StatDimMetaInfo();
    yDim[1].title = "专业大类";
    yDim[1].needStat = true;
    yDim[1].meta = new String[2][2];
    yDim[1].meta[0] = new String[]{"Comm.","通信"};
    yDim[1].meta[1] = new String[]{"Ip.","IP"};

    yDim[2] = new StatDimMetaInfo();
    yDim[2].title = "专业细类";
    yDim[2].needStat = true;
    yDim[2].meta = new String[4][2];
    yDim[2].meta[0] = new String[]{"GL.","光缆"};
    yDim[2].meta[1] = new String[]{"DL.","电缆"};
    yDim[2].meta[2] = new String[]{"DG.","单工"};
    yDim[2].meta[3] = new String[]{"SG.","双工"};

    info.yDim = yDim;

    //X维度关系定义
    info.relXDim = new StatDimRelationInfo[0];

    //Y维度关系定义
    StatDimRelationInfo[] relYDim = new StatDimRelationInfo[1];

    relYDim[0] = new StatDimRelationInfo();
    relYDim[0].parentLevel = 2;
    relYDim[0].childLevel = 3;
    relYDim[0].keyMap = new String[4][2];
    relYDim[0].keyMap[0] = new String[]{"Comm.","GL."};
    relYDim[0].keyMap[1] = new String[]{"Comm.","DL."};
    relYDim[0].keyMap[2] = new String[]{"Ip.","DG."};
    relYDim[0].keyMap[3] = new String[]{"Ip.","SG."};

    info.relYDim = relYDim;

    //数据
    info.dataList = new ArrayList();

    for(int i=0;i<xDim[0].meta.length;i++)
    {
      for(int j=0;j<xDim[1].meta.length;j++)
      {
        for(int m=0;m<yDim[0].meta.length;m++)
        {
          for(int n=0;n<yDim[1].meta.length;n++)
          {
            for(int o=0;o<yDim[2].meta.length;o++)
            {
              info.dataList.add(new String[]{
                                xDim[0].meta[i][0],
                                xDim[1].meta[j][0],
                                yDim[0].meta[m][0],
                                yDim[1].meta[n][0],
                                yDim[2].meta[o][0],
                                String.valueOf((int)(Math.sin(m+n+o) * 10000)/10.0),
                                String.valueOf((int)(Math.cos(m+n+o) * 1000))});
            }
          }
        }
      }
    }

    /*
    for(int i=0;i<info.dataList.size();i++)
    {
      String[] a = (String[])info.dataList.get(i);
      for(int j=0;j<a.length;j++)
      {
        System.err.print(a[j] + ",");
      }
      System.err.println();
    }*/
  }


  /**
 * 生成测试数据
 */
private void BuildTestData()
{
  info = new StatInfo();

  info.needXStat = true;
  info.needYStat = true;

  //统计定义
  StatMetaInfo[] statQ = new StatMetaInfo[2];
 /* statQ[0] = new StatMetaInfo();
  statQ[0].title = "投资额";
  statQ[0].type = StatMetaInfo.STAT_TYPE_DOUBLE;
  statQ[0].format = "0.0";

  statQ[1] = new StatMetaInfo();
  statQ[1].title = "完成额";
  statQ[1].type = StatMetaInfo.STAT_TYPE_LONG;
  statQ[1].format = "0.00";
*/
  info.statQ = statQ;

  //X维定义

  StatDimMetaInfo[] xDim = new StatDimMetaInfo[2];

  xDim[0] = new StatDimMetaInfo();
  xDim[0].title = "建设单位";
  xDim[0].meta = new String[2][2];
  xDim[0].meta[0] = new String[]{"NJ.","南京电信"};
  xDim[0].meta[1] = new String[]{"SZ.","苏州电信"};

  xDim[1] = new StatDimMetaInfo();
  xDim[1].title = "月份";
  xDim[1].meta = new String[2][2];
  xDim[1].meta[0] = new String[]{"Jan.","一月"};
  xDim[1].meta[1] = new String[]{"Feb.","二月"};

  info.xDim = xDim;

//   info.setNoXDim();

  //Y维定义

  StatDimMetaInfo[] yDim = new StatDimMetaInfo[3];

  yDim[0] = new StatDimMetaInfo();
  yDim[0].title = "专业大类";
  yDim[0].needStat = true;
  yDim[0].meta = new String[2][2];
  yDim[0].meta[0] = new String[]{"Comm.","通信"};
  yDim[0].meta[1] = new String[]{"Ip.","IP"};

  yDim[1] = new StatDimMetaInfo();
  yDim[1].title = "管理级别";
  yDim[1].needStat = true;
  yDim[1].meta = new String[2][2];
  yDim[1].meta[0] = new String[]{"Prov.","省管"};
  yDim[1].meta[1] = new String[]{"City.","市管"};

  yDim[2] = new StatDimMetaInfo();
  yDim[2].title = "专业细类";
  yDim[2].needStat = true;
  yDim[2].meta = new String[4][2];
  yDim[2].meta[0] = new String[]{"GL.","光缆"};
  yDim[2].meta[1] = new String[]{"DL.","电缆"};
  yDim[2].meta[2] = new String[]{"DG.","单工"};
  yDim[2].meta[3] = new String[]{"SG.","双工"};

  info.yDim = yDim;

  //X维度关系定义
  info.relXDim = new StatDimRelationInfo[0];

  //Y维度关系定义
  StatDimRelationInfo[] relYDim = new StatDimRelationInfo[1];

  relYDim[0] = new StatDimRelationInfo();
  relYDim[0].parentLevel = 1;
  relYDim[0].childLevel = 3;
  relYDim[0].keyMap = new String[4][2];
  relYDim[0].keyMap[0] = new String[]{"Comm.","GL."};
  relYDim[0].keyMap[1] = new String[]{"Comm.","DL."};
  relYDim[0].keyMap[2] = new String[]{"Ip.","DG."};
  relYDim[0].keyMap[3] = new String[]{"Ip.","SG."};

  info.relYDim = relYDim;

  //数据
  info.dataList = new ArrayList();

  for(int i=0;i<xDim[0].meta.length;i++)
  {
    for(int j=0;j<xDim[1].meta.length;j++)
    {
      for(int m=0;m<yDim[0].meta.length;m++)
      {
        for(int n=0;n<yDim[1].meta.length;n++)
        {
          for(int o=0;o<yDim[2].meta.length;o++)
          {
            info.dataList.add(new String[]{
                              xDim[0].meta[i][0],
                              xDim[1].meta[j][0],
                              yDim[0].meta[m][0],
                              yDim[1].meta[n][0],
                              yDim[2].meta[o][0],
                              String.valueOf((int)(Math.sin(i+j+m+n+o) * 10000)/10.0),
                              String.valueOf((int)(Math.cos(i+j+m+n+o) * 1000))});
          }
        }
      }
    }
  }
}


  /**
   * 测试例程
   * @param args String[]
   */
  public static void main(String[] args)
  {
  }

  /**
   * 设置统计数据信息
   * @param queryValue QueryValue
   */
  public void setQueryValue(QueryValue queryValue)
  {
    this.queryValue = queryValue;
  }

  public void setConditionHTML(String conditionHTML)
  {
    this.conditionHTML = conditionHTML;
  }

  public void setFirstShow(int firstShow)
  {
    this.firstShow = firstShow;
  }
}
