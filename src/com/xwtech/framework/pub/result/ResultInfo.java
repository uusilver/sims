package com.xwtech.framework.pub.result;

import java.util.List;

import com.xwtech.framework.pub.web.FrameworkApplication;
import com.xwtech.framework.pub.utils.StringUtils;
import com.xwtech.mss.pub.po.FrameResult;

public class ResultInfo
{
  private int resultCode ;

  private String[] replace ;

  public ResultInfo(int resultCode)
  {
    this.resultCode = resultCode;
  }

  public ResultInfo(int resultCode, String[] replace)
  {
    this.resultCode = resultCode;
    this.replace = replace;
  }

  public void setResultCode(int resultCode)
  {
    this.resultCode = resultCode;
  }

  public int getResultCode()
  {
    return resultCode;
  }

  public void setReplace(String[] replace)
  {
    this.replace = replace;
  }

  public String[] getReplace()
  {
    return replace;
  }

  public FrameResult getFrameResult()
  {
	  FrameResult frameResult = new FrameResult();
    List list = FrameworkApplication.getCacheObjects("frameResult");
    for (int i=0;i<list.size();i++)
    {
      frameResult = (FrameResult)list.get(i);
      if(this.resultCode == frameResult.getResultCode().intValue())
         return frameResult;
    }
    return frameResult;
  }

  public String getResultInfo()
  {
    String resultInfo = getFrameResult().getResultInfo();
    if(this.replace !=null)
    {
      for(int i=0;i<this.replace.length;i++)
      {
        resultInfo = StringUtils.replace(resultInfo,"{"+i+"}",this.replace[i]);
      }
    }
    return resultInfo;
  }


}
