package com.tmind.framework.login.pub;

import java.util.Iterator;
import com.tmind.framework.pub.po.FrameRole;
import com.tmind.framework.pub.po.FrameLoginRole;
import com.tmind.framework.pub.po.FrameLogin;
import com.tmind.framework.login.pub.AbstractLoginToken;
import com.tmind.mss.pub.po.UserInfo;


/**
 *
 * <p>Title: Framework </p>
 * <p>Description:Framework</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: tmind.com</p>
 * @author starxu
 * @version 1.0
 *
 * 登录令牌例子
 */
public class ExampleLoginToken extends AbstractLoginToken
{
  public void load(FrameLogin frameLogin)
  {
    //设置登录信息
    this.setFrameLogin(frameLogin);

    //设置用户扩展信息
    setBaseInfo(null);

    //设置用户权限
    if(frameLogin.getFrameLoginRoles()!=null)
    {
      FrameRole[] frameRoles = new FrameRole[frameLogin.getFrameLoginRoles().size()];
      Iterator it = frameLogin.getFrameLoginRoles().iterator();
      int i = 0;
      while(it.hasNext())
      {
        FrameLoginRole frameLoginRole = (FrameLoginRole)it.next();
        frameRoles[i] = frameLoginRole.getFrameRole();
        i++;
      }
      setFrameRoles(frameRoles);
    }
  }

    public void load(UserInfo sysUser) {
    }
}
