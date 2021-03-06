package com.tmind.mss.pub.web;

import javax.servlet.http.HttpServletRequest;
import com.tmind.framework.login.pub.AbstractLoginToken;
import com.tmind.framework.pub.web.SessionNameConstants;
import com.tmind.framework.pub.utils.SessionUtils;
import com.tmind.mss.pub.UserBaseInfo;
import com.tmind.mss.pub.po.Role;
import com.tmind.mss.pub.po.UserInfo;
/**
 *
 * <p>Title:获取登录用户信息</p>
 *
 * <p>Description: 系统中从session中获取用户信息</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class SubSessionUtils {
    /**
     * 构造方法
     */
    public SubSessionUtils() {
    }
    /**
     * 获取登录用户信息
     * @param request HttpServletRequest
     * @return SysUser
     */
    public static UserInfo getSysUser(HttpServletRequest request) {
        AbstractLoginToken loginToken1 = (AbstractLoginToken) SessionUtils.
                                         getObjectAttribute(request,
                SessionNameConstants.LOGIN_TOKEN);
        UserBaseInfo userBaseInfo1 = (UserBaseInfo) loginToken1.getBaseInfo();

        return userBaseInfo1.getSysUser();
    }

    public static String getRoleName(HttpServletRequest request){
        AbstractLoginToken loginToken1 = (AbstractLoginToken) SessionUtils.
                                         getObjectAttribute(request,
                SessionNameConstants.LOGIN_TOKEN);
        Role[] role  = loginToken1.getRoles();
        return role[0].getRoleName();
    }

    
}
