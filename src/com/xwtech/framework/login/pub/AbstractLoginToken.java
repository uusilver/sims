package com.xwtech.framework.login.pub;

import com.xwtech.framework.pub.po.FrameLogin;
import com.xwtech.framework.pub.po.FrameRole;
import com.xwtech.framework.pub.result.ResultInfos;
import com.xwtech.mss.pub.po.UserInfo;
import com.xwtech.mss.pub.po.Role;

/**
 *
 * <p>Title: Framework </p>
 * <p>Description: Framework</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: xwtech.com</p>
 * @author starxu
 * @version 1.0
 *
 * 登录令牌抽象基类 AbstractLoginToken
 * 保存登录信息login ,baseInfo和权限信息roles
 *
 */
abstract public class AbstractLoginToken
{
  private UserInfo sysUser;

  private FrameLogin frameLogin;

  private FrameRole[] frameRoles;

  private Object baseInfo;

  private Role[] roles;

  private String menuHtmlet;

  public FrameLogin getFrameLogin()
  {
    return frameLogin;
  }

  public void setFrameLogin(FrameLogin frameLogin)
  {
    this.frameLogin = frameLogin;
  }

  public UserInfo getSysUser()
  {
    return sysUser;
  }

  public void setSysUser(UserInfo SysUser)
  {
    this.sysUser = sysUser;
  }

  public Object getBaseInfo()
  {
    return baseInfo;
  }

  public void setBaseInfo(Object baseInfo)
  {
    this.baseInfo = baseInfo;
  }

  public FrameRole[] getFrameRoles()
  {
    return frameRoles;
  }

  public void setFrameRoles(FrameRole[] frameRoles)
  {
    this.frameRoles = frameRoles;
  }


  public Role[] getRoles()
  {
    return roles;
  }

  public void setRoles(Role[] roles)
  {
    this.roles = roles;
  }

  public void setMenuHtmlet(String menuHtmlet)
  {
    this.menuHtmlet = menuHtmlet;
  }

  public String getMenuHtmlet()
  {
    return this.menuHtmlet;
  }

  public abstract void load(UserInfo sysUser);

  public abstract void load(FrameLogin frameLogin);


}
