package com.xwtech.framework.pub.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.xwtech.framework.pub.result.ResultConstants;
import com.xwtech.framework.pub.result.ResultInfo;

public class HttpUtils
{
  protected static final Logger log = Logger.getLogger(HttpUtils.class);

  public HttpUtils()
  {

  }

  public static String callRPCByGsmobile(String url)
  {
    log.info(url);

    PostMethod post = null;
    StringBuffer buf = new StringBuffer();
    BufferedReader br = null;
    try
    {
      post = new PostMethod(url);
      HttpClient httpclient = new HttpClient();

      httpclient.setTimeout(30000);
      GetMethod authget = new GetMethod(url.substring(0,url.indexOf("?")));
      httpclient.executeMethod(authget);
      authget.releaseConnection();

      int resCode = httpclient.executeMethod(post);
      // HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发

      // 301或者302
      if(resCode == HttpStatus.SC_MOVED_PERMANENTLY ||
          resCode == HttpStatus.SC_MOVED_TEMPORARILY)
      {
        // 从头中取出转向的地址
        Header locationHeader = post.getResponseHeader("location");
        String location = null;
        if(locationHeader != null)
        {
          location = locationHeader.getValue();
          log.info("The page was redirected to:" + location);
        }
        else
        {
          log.info("Location field value is null.");
        }

      }

      if(resCode == HttpStatus.SC_OK)
      {
        br = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream()));
        String tmpLineStr = "";
        while((tmpLineStr = br.readLine()) != null)
        {
          buf.append(tmpLineStr);
        }
      }
      else
      //  log.info(new ResultInfo(ResultConstants.IP_HTTPSTATUS_FAILURE));
      log.info(new Integer(ResultConstants.IP_HTTPSTATUS_FAILURE));

    }
    catch(HttpException e)
    {
      //log.info(new ResultInfo(ResultConstants.IP_RESULT_HTTPERROR));
    	log.info(new Integer(ResultConstants.IP_RESULT_HTTPERROR));
    }
    catch(Exception e)
    {
      //log.info(new ResultInfo(ResultConstants.IP_RESULT_ERROR));
      log.info(new Integer(ResultConstants.IP_RESULT_ERROR));
    }
    finally
    {
      try
      {
        if(br != null)
          br.close();
        if(post != null)
          post.releaseConnection();
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
    }
    String retstr = buf.toString();
    if(retstr.indexOf("retcode")<0) retstr="retcode="+retstr;
    log.info(retstr);
    return retstr;

  }

  //调用httpclient
  public static String callRPC(String url)
  {
    log.info(url);
    PostMethod post = null;
    StringBuffer buf = new StringBuffer();
    BufferedReader br = null;
    try
    {
      post = new PostMethod(url);
      HttpClient httpclient = new HttpClient();
      httpclient.setTimeout(600000);
      int resCode = httpclient.executeMethod(post);
      // HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发

      // 301或者302
      if(resCode == HttpStatus.SC_MOVED_PERMANENTLY ||
          resCode == HttpStatus.SC_MOVED_TEMPORARILY)
      {
        // 从头中取出转向的地址
        Header locationHeader = post.getResponseHeader("location");
        String location = null;
        if(locationHeader != null)
        {
          location = locationHeader.getValue();
          log.info("The page was redirected to:" + location);
        }
        else
        {
          log.info("Location field value is null.");
        }

      }

      if(resCode == HttpStatus.SC_OK)
      {
        br = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream()));
        String tmpLineStr = "";
        while((tmpLineStr = br.readLine()) != null)
        {
          buf.append(tmpLineStr);
        }
      }
      else
        log.info(new ResultInfo(ResultConstants.IP_HTTPSTATUS_FAILURE));

    }
    catch(HttpException e)
    {
      log.info(new ResultInfo(ResultConstants.IP_RESULT_HTTPERROR));
    }
    catch(Exception e)
    {
    	e.printStackTrace();
      log.info(new ResultInfo(ResultConstants.IP_RESULT_ERROR));
    }
    finally
    {
      try
      {
        if(br != null)
          br.close();
        if(post != null)
          post.releaseConnection();
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
    }
    log.info(buf.toString());
    return buf.toString();
  }

}
