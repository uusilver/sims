package com.xwtech.mss.bo.system.login;

import com.xwtech.mss.pub.dao.system.UserInfoDAO;
import com.xwtech.mss.pub.po.UserInfo;
import com.xwtech.framework.pub.utils.Base64;
import com.xwtech.framework.pub.utils.SessionUtils;
import com.xwtech.framework.pub.result.ResultInfo;
import com.xwtech.framework.pub.result.ResultInfos;
import com.xwtech.framework.pub.web.SessionNameConstants;
import com.xwtech.framework.pub.result.ResultConstants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xwtech.mss.pub.po.FrameLoginLog;
import com.xwtech.framework.pub.utils.DateUtils;
import com.xwtech.mss.pub.dao.system.FrameLoginLogDAO;
import com.xwtech.mss.pub.UserLoginToken;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class UserLoginBO {

    private UserInfoDAO userInfoDAO;

    private FrameLoginLogDAO frameLoginLogDAO;


	public void setFrameLoginLogDAO(FrameLoginLogDAO frameLoginLogDAO) {
		this.frameLoginLogDAO = frameLoginLogDAO;
	}

	public void setUserInfoDAO(UserInfoDAO userInfoDAO) {
		this.userInfoDAO = userInfoDAO;
	}

	public UserLoginBO() {
    }

    /**
     * 根据用户名、密码和用户角色进行系统登陆校验
     * @param loginName String
     * @param loginPwd String
     * @param roleId Long
     * @return SysUser
     */
    public UserInfo queryFrameLoginByLoginNameAndLoginPwd(String loginName, String loginPwd){
        return userInfoDAO.queryFrameLoginByLoginNameAndLoginPwd(loginName,loginPwd);
    }

    public void login(ResultInfos resultInfos, UserInfo sysUser, HttpServletRequest request, HttpServletResponse response) throws Exception
  {

    //记录登录日志
    FrameLoginLog frameLoginLog = new FrameLoginLog();
    frameLoginLog.setLoginIp(request.getRemoteAddr());
    frameLoginLog.setLoginName(sysUser.getLoginName());
    frameLoginLog.setLoginPwd(String.valueOf(sysUser.getLoginPwd()));
    frameLoginLog.setLoginTime(DateUtils.getChar14());

    //检查登录名和密码是否正确
//    Long roleId = new Long(request.getParameter("roleId"));
    sysUser = userInfoDAO.queryFrameLoginByLoginNameAndLoginPwd(String.valueOf(sysUser.getLoginName()), sysUser.getLoginPwd());
    if(sysUser == null)
    {
      frameLoginLog.setLoginResultCode(String.valueOf(ResultConstants.LOGIN_NAME_AND_PWD_ERROR));
      frameLoginLogDAO.save(frameLoginLog);
      ResultInfo resultInfo = new ResultInfo(ResultConstants.LOGIN_NAME_AND_PWD_ERROR, null);
      resultInfos.add(resultInfo);
      return;
    }

    //加载登录用户的相关信息到登录令牌

    //添加系统cookie
//    String userId = Base64.encode(sysUser.getUserId().toString());
//    Cookie cookie = new Cookie(SessionNameConstants.MPMS_COOKIE,userId);
//    cookie.setMaxAge(SessionNameConstants.iCookieForCurrentPage);
//    cookie.setPath("/"); 
    //添加至response
//    response.addCookie(cookie);
    
    UserLoginToken loginToken = new UserLoginToken();
    loginToken.load(sysUser);
    if(loginToken.getRoles()==null){
    	frameLoginLog.setLoginResultCode(String.valueOf(ResultConstants.NOT_ACCESS_ROLE));
    	frameLoginLogDAO.save(frameLoginLog);
        ResultInfo resultInfo = new ResultInfo(ResultConstants.NOT_ACCESS_ROLE, null);
        resultInfos.add(resultInfo);
        return;
    }
    frameLoginLog.setLoginResultCode(String.valueOf(ResultConstants.LOGIN_SUECCESS));
    frameLoginLogDAO.save(frameLoginLog);
    ResultInfo resultInfo = new ResultInfo(ResultConstants.LOGIN_SUECCESS, null);
    resultInfos.add(resultInfo);
    //保存登录用户信息到http session
    SessionUtils.setObjectAttribute(request, SessionNameConstants.LOGIN_TOKEN, loginToken);
    //修改最后一次登录时间
//    sysUser.setLatestLoginTime(DateUtils.getChar14());
//    userInfoDAO.save(sysUser);
    
    return;

  }

    public void loginFromDms(UserInfo sysUser, HttpServletRequest request, HttpServletResponse response) throws Exception
  {

    //记录登录日志
    FrameLoginLog frameLoginLog = new FrameLoginLog();
    frameLoginLog.setLoginIp(request.getRemoteAddr());
    frameLoginLog.setLoginName(sysUser.getLoginName());
    frameLoginLog.setLoginPwd(String.valueOf(sysUser.getLoginPwd()));
    frameLoginLog.setLoginTime(DateUtils.getChar14());

    //检查登录名和密码是否正确
//    MssFrameLoginLogDAO.save(frameLoginLog);

    //加载登录用户的相关信息到登录令牌

    //添加系统cookie
    String userId = Base64.encode(sysUser.getUserId().toString());
    Cookie cookie = new Cookie(SessionNameConstants.MPMS_COOKIE,userId);
    cookie.setMaxAge(SessionNameConstants.iCookieForCurrentPage);
    cookie.setPath("/"); 
    //添加至response
    response.addCookie(cookie);
    
    UserLoginToken loginToken = new UserLoginToken();
    loginToken.load(sysUser);
    if(loginToken.getRoles()==null){
    	frameLoginLog.setLoginResultCode(String.valueOf(ResultConstants.NOT_ACCESS_ROLE));
    	frameLoginLogDAO.save(frameLoginLog);
        return;
    }
    frameLoginLog.setLoginResultCode(String.valueOf(ResultConstants.LOGIN_SUECCESS));
    frameLoginLogDAO.save(frameLoginLog);
    //保存登录用户信息到http session
    SessionUtils.setObjectAttribute(request, SessionNameConstants.LOGIN_TOKEN, loginToken);
    //修改最后一次登录时间
//    sysUser.setLatestLoginTime(DateUtils.getChar14());
//    userInfoDAO.save(sysUser);
    
    return;
  }

}
