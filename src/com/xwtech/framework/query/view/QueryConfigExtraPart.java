package com.xwtech.framework.query.view;

import java.util.HashMap;

import org.jdom.Element;
import java.util.List;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class QueryConfigExtraPart
{
  public String partType = null;
  public String partHTML = null;
  public String partClass = null;
  public HashMap partParams = null;
  private int areaType = 0;

  public static final int AREA_TYPE_COND_TOP = 1;
  public static final int AREA_TYPE_COND_BOTTOM = 2;
  public static final int AREA_TYPE_LISTPAGE_BOTTOM = 3;
  public static final int AREA_TYPE_RESULT_BOTTOM = 4;

  protected static final Logger logger = Logger.getLogger(QueryConfigExtraPart.class);

  public QueryConfigExtraPart(int areaType , Element ele)
  {
    this.areaType = areaType;

    if(ele == null)
    {
      this.partType = "";
      this.partHTML = null;
    }
    else
    {
      this.partType = QueryExhibitUtil.getAttributeNotEmpty(ele,"type","");
      if(this.partType.equals("class"))
      {
        this.partClass = QueryExhibitUtil.getSingleChildValueNotEmpty(ele,"class",null);

        this.partParams = new HashMap();
        //<param name="ywtype">3</param>
        List list = ele.getChildren("param");
        for(int i=0;i<list.size();i++)
        {
          Element e = (Element)list.get(i);
          String name = QueryExhibitUtil.getAttributeNotEmpty(e,"name",null);
          if(name != null)
            this.partParams.put(name,QueryExhibitUtil.getValueNotEmpty(e,""));
        }
      }
      else
        this.partHTML = QueryExhibitUtil.getValueNotEmpty(ele,null);
    }
  }

  public void outputHTML(PrintWriter out, HttpServletRequest request)
  {
    if(this.partType.equals("class"))
    {
      if(this.partClass != null)
      {
        try
        {
          Class cls = Class.forName(this.partClass);
          Object obj = cls.newInstance();
          if(obj instanceof IQueryPartView)
          {
            IQueryPartView pv = (IQueryPartView)obj;
            out.println(pv.toHTML(this.areaType,request,this.partParams));
          }
        }
        catch(Exception ex)
        {
          logger.warn("调用" + this.partClass + "的IQueryPartView接口失败");
          ex.printStackTrace();
        }
      }
    }
    else
    {
      if(this.partHTML != null)
        out.println(this.partHTML);
    }
  }

  public String getPartClass()
  {
    return partClass;
  }

  public HashMap getPartParams()
  {
    return partParams;
  }

  public String getPartType()
  {
    return partType;
  }
}
