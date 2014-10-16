package com.xwtech.framework.login.controller;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.xwtech.framework.pub.po.FrameLogin;
import com.xwtech.framework.login.bo.LoginBO;
import com.xwtech.framework.pub.result.ResultInfos;
import com.xwtech.framework.pub.result.ResultConstants;
import com.xwtech.framework.pub.web.RequestNameConstants;


/**
 *
 * <p>Title: Framework </p>
 * <p>Description:Framework</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: xwtech.com</p>
 * @author starxu
 * @version 1.0
 *
 * 登录页面控制
 */
public class LoginController extends SimpleFormController implements InitializingBean
{

  protected static final Logger logger = Logger.getLogger(LoginController.class);

  private LoginBO loginBO;

  public void setLoginBO(LoginBO loginBO)
  {
    this.loginBO = loginBO;
  }

  public void afterPropertiesSet() throws Exception
  {
    if(loginBO == null)
      throw new ApplicationContextException("Must set loginBO property on " + getClass());
  }


  protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception
  {
    HashMap map = new HashMap();
    ResultInfos resultInfos = new ResultInfos();
    map.put(RequestNameConstants.RESULTINFOS,resultInfos);

    try
    {
      FrameLogin frameLogin = (FrameLogin)command;
      /**
       * 调用登录业务逻辑
       */
      loginBO.login(resultInfos,frameLogin,request);
      int resultCode = resultInfos.get(0).getResultCode();
      switch(resultCode)
      {
        case ResultConstants.LOGIN_SUECCESS: //登录成功
          resultInfos.setGotoUrl("/framework/jsp/main.jsp");
          break;
        case ResultConstants.LOGIN_IP_NOT_ACCESS: //登录失败,IP被限制!
          resultInfos.setGotoUrl("/framework/jsp/login.jsp");
          break;
        case ResultConstants.LOGIN_CHECK_CODE_ERROR: //登录失败,验证码不正确!
          resultInfos.setGotoUrl("/framework/jsp/login.jsp");
          break;
        case ResultConstants.LOGIN_NAME_AND_PWD_ERROR: //登录失败,登录名和密码不正确!
          resultInfos.setGotoUrl("/framework/jsp/login.jsp");
          break;
        default:
          logger.info("resultCode = " + resultCode + " not found!");
          break;
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return new ModelAndView("/framework/jsp/information.jsp", RequestNameConstants.INFORMATION, map);
  }

}
