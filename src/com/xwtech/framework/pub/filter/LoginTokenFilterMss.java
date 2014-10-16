package com.xwtech.framework.pub.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.regexp.RE;
import org.springframework.web.filter.OncePerRequestFilter;

import com.xwtech.framework.login.pub.AbstractLoginToken;
import com.xwtech.framework.pub.utils.Base64;
import com.xwtech.framework.pub.utils.SessionUtils;
import com.xwtech.framework.pub.web.SessionNameConstants;
import com.xwtech.framework.pub.result.ResultInfo;
import com.xwtech.framework.pub.result.ResultInfos;
import com.xwtech.framework.pub.result.ResultConstants;
import com.xwtech.framework.pub.web.ContextBeanUtils;
import com.xwtech.framework.pub.web.RequestNameConstants;
import com.xwtech.framework.pub.web.FrameworkApplication;
//import com.xwtech.mss.bo.CUserInfoBO;
import com.xwtech.mss.bo.system.login.UserLoginBO;
import com.xwtech.mss.pub.UserBaseInfo;
import com.xwtech.mss.pub.po.FrameUrl;
import com.xwtech.mss.pub.po.UserInfo;

/**
 *
 * <p>Title: Framework </p>
 * <p>Description:Framework</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: xwtech.com</p>
 * @author starxu
 * @version 1.0
 *
 * 过滤url,ip,判断是否有权限能够被访问
 */
public class LoginTokenFilterMss extends OncePerRequestFilter
{
	  protected static final Logger logger = Logger.getLogger(LoginTokenFilterMss.class);

	  private String loginPageUrl;

	  public void setLoginPageUrl(String loginPageUrl)
	  {
	    this.loginPageUrl = loginPageUrl;
	  }


	  protected void doFilterInternal(
	      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
	  {

	    //正常流程登录
	    String currentURI = request.getRequestURI();
	    String compareUrl = currentURI.substring(currentURI.lastIndexOf("/")+1,currentURI.length());
	    String frontUrl = currentURI.substring(0,currentURI.lastIndexOf("/"));
	    AbstractLoginToken loginToken = (AbstractLoginToken)(request.getSession().getAttribute(SessionNameConstants.LOGIN_TOKEN));
	    if(loginToken == null) //登录令牌不存
	    {
	      boolean isNeedRedirectLoginPage = true; //是否要跳转到登录页面
	      //尚未登录系统
	      if( FrameworkApplication.frameworkProperties.getNotFilterUrls().contains( compareUrl))
	        {
	          isNeedRedirectLoginPage = false;
	        }
	      //如果在notFilterUrls 指定了路径，则也支持直接通过
	      if (FrameworkApplication.frameworkProperties.getNotFilterUrls().contains(frontUrl))
	        isNeedRedirectLoginPage = false;

	      //跳转到登录页
	      if(isNeedRedirectLoginPage == true)
	       {
	         HashMap map = new HashMap();
	         ResultInfos resultInfos = new ResultInfos();
	         resultInfos.setGotoUrl("/index.jsp");
//	         resultInfos.setIsRedirect(true);
	         resultInfos.add(new ResultInfo(ResultConstants.NOT_LOGIN_NOT_ACCESS_ROLE,null));
	         map.put(RequestNameConstants.RESULTINFOS,resultInfos);
	         map.put("flag","s");
	         request.setAttribute(RequestNameConstants.INFORMATION, map);
	         //由于请求页面所指向路径不一样，需要按级进行跳转
//	         int separatorLen = request.getRequestURI().split("/").length;
//	         if(separatorLen==4){
//	        	 request.getRequestDispatcher("/mss/jsp/information.jsp").forward(request,response);
//	         }else if(separatorLen==5){
//	        	 request.getRequestDispatcher("../../../mss/jsp/information.jsp").forward(request,response);
//	         }else if(separatorLen==6){
//	        	 request.getRequestDispatcher("../../../../mss/jsp/information.jsp").forward(request,response);
//	         }
	         request.getRequestDispatcher("/mss/jsp/information.jsp").forward(request,response);
	         return;
	       }
	    }
	    else //登录令牌存在
	    {
	       boolean isNeedRedirectNotRolePage = true;//是否要跳转到没有权限的页
	       //判断登录令牌权限
	       if(loginToken.getRoles()!=null){
	           for (int i = 0; i < loginToken.getRoles().length; i++) {
	               Iterator it = loginToken.getRoles()[i].getFrameUrls().iterator();
	               while (it.hasNext()) { //过滤url权限
	                   FrameUrl frameUrl = (FrameUrl) it.next();
	                   //正则表达式判断权附1�7
	                   RE regexp = new RE(frameUrl.getUrlValue());
	                   if (regexp.match(currentURI) ||
	                       FrameworkApplication.frameworkProperties.
	                       getNotFilterUrls().contains(compareUrl)) {
	                       isNeedRedirectNotRolePage = false;
	                       break;
	                   }
	               }
	           }
	       }
//	       if(loginToken.getRoles()!=null){
//	           for (int i = 0; i < loginToken.getRoles().length; i++) {
//	               Iterator it = loginToken.getRoles()[i].getDmsSysResources().iterator();
//	               while (it.hasNext()) { //过滤url权限
//	            	   DmsSysResource sysResource = (DmsSysResource) it.next();
//	                   //正则表达式判断权附1�7
	//
	//
//	                   RE regexp = new RE(sysResource.getResourceUrl());
//	                   if (regexp.match(currentURI) ||
//	                       FrameworkApplication.frameworkProperties.
//	                       getNotFilterUrls().contains(compareUrl)) {
//	                       isNeedRedirectNotRolePage = false;
//	                       break;
//	                   }
//	               }
//	           }
	//
//	       }
	       if(isNeedRedirectNotRolePage == true)
	       {
	         HashMap map = new HashMap();
	         ResultInfos resultInfos = new ResultInfos();
	         resultInfos.add(new ResultInfo(ResultConstants.NOT_ACCESS_ROLE,null));
	         map.put(RequestNameConstants.RESULTINFOS,resultInfos);
	         request.setAttribute(RequestNameConstants.INFORMATION, map);
	         request.getRequestDispatcher("information.jsp").forward(request,response);
	         return;
	       }
	    }

	    long processBeginTime = System.currentTimeMillis();
	    filterChain.doFilter(request, response);
	    long processEndTime = System.currentTimeMillis();
	    logger.debug("Process Time: "+(processEndTime-processBeginTime)+" millisecond");
	  }

	  //清除来自库存系统的用户cookie值
	  private void clearDmsCookie(HttpServletRequest request, HttpServletResponse response){
		  Cookie[] cookies = request.getCookies();
		//清除掉原有cookie
		for (int i = 0; cookies != null && i < cookies.length; i++)
		{
			 if (SessionNameConstants.DMS_COOKIE.equals(cookies[i].getName())){
				 cookies[i] = new Cookie(cookies[i].getName(), null);
				 cookies[i].setMaxAge(0);
				 cookies[i].setPath("/");
				 //添加至response
				 response.addCookie(cookies[i]);
				 break;
			 }
		}
	  }
	  
}
