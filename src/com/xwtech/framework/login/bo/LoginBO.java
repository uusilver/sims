package com.xwtech.framework.login.bo;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.regexp.RE;

import com.xwtech.framework.login.pub.AbstractLoginToken;
import com.xwtech.framework.pub.po.FrameLogin;
import com.xwtech.framework.pub.po.FrameLoginLog;
import com.xwtech.framework.pub.dao.FrameLoginDAO;
import com.xwtech.framework.pub.dao.FrameLoginLogDAO;
import com.xwtech.framework.pub.web.FrameworkApplication;
import com.xwtech.framework.pub.utils.DateUtils;
import com.xwtech.framework.pub.utils.SessionUtils;
import com.xwtech.framework.pub.web.SessionNameConstants;
import com.xwtech.framework.pub.utils.RequestUtils;
import com.xwtech.framework.pub.result.ResultInfos;
import com.xwtech.framework.pub.result.ResultInfo;
import com.xwtech.framework.pub.result.ResultConstants;

/**
 *
 * <p>Title: Framework </p>
 * <p>Description:Framework</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: xwtech.com</p>
 * @author starxu
 * @version 1.0
 *
 * 登录业务处理
 */
public class LoginBO
{
  protected static final Logger logger = Logger.getLogger(LoginBO.class);

  private FrameLoginDAO frameLoginDAO;

  private FrameLoginLogDAO frameLoginLogDAO;

  private String notAccessIp;

  private AbstractLoginToken loginToken;

  public LoginBO()
  {

  }

  public void setFrameLoginDAO(FrameLoginDAO frameLoginDAO)
  {
    this.frameLoginDAO = frameLoginDAO;
  }

  public void setNotAccessIp(String notAccessIp)
  {
    this.notAccessIp = notAccessIp;
  }

  public void setLoginToken(AbstractLoginToken loginToken)
  {
    this.loginToken = loginToken;
  }

  public FrameLoginDAO getFrameLoginDAO()
  {
    return this.frameLoginDAO;
  }

  public FrameLoginLogDAO getFrameLoginLogDAO() {
	return frameLoginLogDAO;
}

public void setFrameLoginLogDAO(FrameLoginLogDAO frameLoginLogDAO) {
	this.frameLoginLogDAO = frameLoginLogDAO;
}

public String getNotAccessIp()
  {
    return this.notAccessIp;
  }

  public AbstractLoginToken getLoginToken()
  {
    return this.loginToken;
  }


  /**
   *
   * @param loginName String
   * @param loginPwd String
   * @param request HttpServletRequest
   * @throws Exception
   * @return int
   */
  public void login(ResultInfos resultInfos,String loginName,String loginPwd,HttpServletRequest request) throws Exception
  {
    FrameLogin frameLogin = new FrameLogin();
    frameLogin.setLoginName(loginName);
    frameLogin.setLoginPwd(loginPwd);
    login(resultInfos,frameLogin,request);
  }

  /**
   * 根据登录名和密码登录
   * @param loginName String
   * @param loginPwd String
   * @throws Exception
   * @return boolean
   */
  public void login(ResultInfos resultInfos,FrameLogin frameLogin,HttpServletRequest request) throws Exception
  {
    String checkCode = RequestUtils.getStringParameter(request,"checkCode",null);
       String loginIp = request.getRemoteAddr();

       //记录登录日志
       	FrameLoginLog frameLoginLog = new FrameLoginLog();
        frameLoginLog.setLoginName(frameLogin.getLoginName());
        frameLoginLog.setLoginPwd(frameLogin.getLoginPwd());
        frameLoginLog.setLoginIp(loginIp);
        frameLoginLog.setLoginTime(DateUtils.getChar14());

       //检查ip
       if(notAccessIp != null)
       {
         RE regexp = new RE(notAccessIp);
         if(regexp.match(loginIp) == true) //ip被限制



         {
           frameLoginLog.setLoginResultCode(new Long(ResultConstants.LOGIN_IP_NOT_ACCESS));
           frameLoginLogDAO.save(frameLoginLog);
           ResultInfo resultInfo = new ResultInfo(ResultConstants.LOGIN_IP_NOT_ACCESS,null);
           resultInfos.add(resultInfo);
           return;
         }
       }
       //检查验证码
       if(FrameworkApplication.frameworkProperties.getUseCheckCode() == true)//需要验证码验证
       {
         if( checkCode == null ||
             SessionUtils.getObjectAttribute(request,SessionNameConstants.CHECK_CODE) == null ||
             checkCode.compareTo((String)SessionUtils.getObjectAttribute(request,SessionNameConstants.CHECK_CODE))!=0)//验证失败
         {
           frameLoginLog.setLoginResultCode(new Long(ResultConstants.LOGIN_CHECK_CODE_ERROR));
           frameLoginLogDAO.save(frameLoginLog);
           ResultInfo resultInfo = new ResultInfo(ResultConstants.LOGIN_CHECK_CODE_ERROR,null);
           resultInfos.add(resultInfo);
           SessionUtils.removeObjectAttribute(request,SessionNameConstants.CHECK_CODE);
           return ;
         }
       }
       //检查登录名和密码是否正确



       frameLogin = frameLoginDAO.queryFrameLoginByLoginNameAndLoginPwd(frameLogin.getLoginName(), frameLogin.getLoginPwd());
       if(frameLogin == null)
       {
         frameLoginLog.setLoginResultCode(new Long(ResultConstants.LOGIN_NAME_AND_PWD_ERROR));
         frameLoginLogDAO.save(frameLoginLog);
         ResultInfo resultInfo = new ResultInfo(ResultConstants.LOGIN_NAME_AND_PWD_ERROR,null);
         resultInfos.add(resultInfo);
         return;
       }

       //加载登录用户的相关信息到登录令牌
       loginToken.load(frameLogin);
       frameLoginLog.setLoginResultCode(new Long(ResultConstants.LOGIN_SUECCESS));
       frameLoginLogDAO.save(frameLoginLog);
       ResultInfo resultInfo = new ResultInfo(ResultConstants.LOGIN_SUECCESS,null);
       resultInfos.add(resultInfo);
       //保存登录用户信息到http session
        SessionUtils.setObjectAttribute(request,SessionNameConstants.LOGIN_TOKEN, loginToken);
       //修改最后一次登录时间



       frameLogin.setLoginLastTime(DateUtils.getChar14());
       frameLoginDAO.saveFrameLogin(frameLogin);
       return ;

  }
}
