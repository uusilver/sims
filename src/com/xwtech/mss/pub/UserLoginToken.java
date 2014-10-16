package com.xwtech.mss.pub;

import com.xwtech.framework.login.pub.AbstractLoginToken;
import com.xwtech.framework.pub.dao.FrameLoginLogDAO;
import com.xwtech.framework.pub.po.FrameLogin;
import com.xwtech.framework.pub.po.FrameLoginLog;
import com.xwtech.framework.pub.result.ResultConstants;
import com.xwtech.framework.pub.result.ResultInfo;
import com.xwtech.framework.pub.result.ResultInfos;
import com.xwtech.framework.pub.web.FrameworkApplication;
import com.xwtech.mss.pub.dao.system.RoleDAO;
import com.xwtech.mss.pub.dao.system.UserInfoDAO;
import com.xwtech.mss.pub.po.Role;
import com.xwtech.mss.pub.po.UserInfo;
import com.xwtech.mss.pub.po.UserProperty;

import java.util.ArrayList;
import java.util.List;

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
public class UserLoginToken extends AbstractLoginToken
{
  private UserInfoDAO sysUserDAO = FrameworkApplication.dbCollection.getUserInfoDAO();
  
  private RoleDAO roleDAO= FrameworkApplication.dbCollection.getRoleDAO();

  private FrameLoginLogDAO frameLoginLogDAO;

  public void setFrameLoginLogDAO(FrameLoginLogDAO frameLoginLogDAO) {
	  this.frameLoginLogDAO = frameLoginLogDAO;
  }

public void load(UserInfo sysUser)
  {
    //记录登录日志
    FrameLoginLog frameLoginLog = new FrameLoginLog();
    //设置user登陆信息
//    this.setSysUser(sysUser);
    UserBaseInfo userBaseInfo = new UserBaseInfo();
    UserInfo sysUser_login = sysUserDAO.querySysUserByUserId(sysUser.getUserId());
    userBaseInfo.setSysUser(sysUser_login);
    //设置扩展信息
    this.setBaseInfo(userBaseInfo);
    //设置用户权限
    List userRoleList = sysUserDAO.querySysUserRoleRightsByUserId(sysUser.getUserId());
//    ArrayList userRoleList = new ArrayList();
//    userRoleList.add(sysUser_login.getRole());
    Role[] roles = new Role[userRoleList.size()];

    if(!userRoleList.isEmpty())
    {
      int i = 0;
      for(int j = 0 ; j<userRoleList.size() ; j++)
      {
    	  //Role role = roleDAO.findById((Long)userRoleList.get(j));
    	  Role role = (Role)userRoleList.get(j);
    	  roles[i] = role;
    	  i++;
      }
      this.setRoles(roles);
    }else{
        return;
    }
  }

    public void load(FrameLogin frameLogin) {
    }

}
