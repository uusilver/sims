package com.xwtech.mss.controller;

import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.validation.BindException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import org.springframework.web.servlet.ModelAndView;
import com.xwtech.framework.pub.web.RequestNameConstants;

import java.util.HashMap;

import com.xwtech.framework.pub.result.ResultInfos;
import com.xwtech.mss.bo.system.login.UserLoginBO;
import com.xwtech.mss.pub.po.UserInfo;
import com.xwtech.framework.pub.result.ResultConstants;
import com.xwtech.framework.pub.utils.RequestUtils;

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
public class UserLoginController extends SimpleFormController {

    private static final int iCookieTime = 60 * 60 * 24 * 30;

    private UserLoginBO userLoginBO;

	public void setUserLoginBO(UserLoginBO userLoginBO) {
		this.userLoginBO = userLoginBO;
	}


	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception{
        HashMap map = new HashMap();
        ResultInfos resultInfos = new ResultInfos();
        map.put(RequestNameConstants.RESULTINFOS, resultInfos);
        UserInfo sysUser = (UserInfo)command;
        String gotoUrl = "";
        try
        {
            String loginName = request.getParameter("loginName");
            String loginPwd = request.getParameter("loginPwd");
            
            sysUser.setLoginName(loginName);
            sysUser.setLoginPwd(loginPwd);

            userLoginBO.login(resultInfos, sysUser, request, response);
            int resultCode = resultInfos.get(0).getResultCode();
            switch(resultCode)
            {
            case ResultConstants.LOGIN_SUECCESS: //登录成功
            	gotoUrl = "/mss/jsp/main.jsp";
//                String sHavingCookie = RequestUtils.getStringParameter(request, "retainPwd");
//                String sSelectPage = RequestUtils.getStringParameter(request,"selectPage");
//                Cookie diyUserCookie = null;
                resultInfos.setGotoUrl(gotoUrl);
//                if(sHavingCookie != null)
//                {
//                    if(this.checkCookieHavingChar(loginPwd))
//                    {
//                        try
//                        {
//                            diyUserCookie = new Cookie(loginName, loginPwd);
//                            diyUserCookie.setMaxAge(iCookieTime);
//                            response.addCookie(diyUserCookie);
//                        }
//                        catch(Exception e)
//                        {
//                            logger.warn("client can't open cookie set");
//                        }
//                    }
//                    else
//                    {
//                        logger.warn("can't save cookie because pwd having lawless char");
//                    }
//                }
//                else
//                {
//                    javax.servlet.http.Cookie[] diyUserChookies = request.getCookies();
//                    if(diyUserChookies != null)
//                    {
//                        if(diyUserChookies.length > 1)
//                        {
//                            diyUserChookies[0].setMaxAge(0);
//                            response.addCookie(diyUserChookies[0]);
//                        }
//                    }
//                }
                break;
            case ResultConstants.LOGIN_NAME_AND_PWD_ERROR: //登录失败,登录名和密码不正确!
            	gotoUrl = "/index.jsp";
            	map.put("falg", "1");
                resultInfos.setGotoUrl(gotoUrl);
                break;
            case ResultConstants.NOT_ACCESS_ROLE: //登录失败,没有权限!
            	gotoUrl = "/index.jsp";
            	map.put("falg", "1");
                resultInfos.setGotoUrl(gotoUrl);
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
        resultInfos.setIsAlert(true);
        resultInfos.setIsRedirect(true);
        return new ModelAndView("/mss/jsp/information.jsp", RequestNameConstants.INFORMATION, map);
    }


    private boolean checkCookieHavingChar(String checkValue)
    {
            //过滤字符'[',']','(',')','=',',','"','/','?','@',':',';',' '};
            byte[] bCharArray =
            {91, 93, 40, 41, 61, 44, 34, 47, 63, 64, 58, 59, 32};
            byte[] bCheckVale = checkValue.getBytes();
            for(int i = 0; i < bCheckVale.length; i++)
            {
                for(int j = 0; j < bCharArray.length; j++)
                {
                    if(bCheckVale[i] == bCharArray[j])return false;
                }
            }
            return true;
        }

    }
