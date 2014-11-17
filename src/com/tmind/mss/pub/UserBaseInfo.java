package com.tmind.mss.pub;

import com.tmind.mss.pub.po.UserInfo;

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
public class UserBaseInfo {
    private UserInfo sysUser;

   public void setSysUser(UserInfo sysUser)
   {
     this.sysUser = sysUser;
   }

   public UserInfo getSysUser()
   {
     return this.sysUser;
   }

}
