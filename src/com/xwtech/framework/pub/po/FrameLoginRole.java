package com.xwtech.framework.pub.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class FrameLoginRole implements Serializable {

    /** identifier field */
    private Long loginRoleId;

    /** persistent field */
    private String loginRoleState;

    /** persistent field */
    private com.xwtech.framework.pub.po.FrameRole frameRole;

    /** persistent field */
    private com.xwtech.framework.pub.po.FrameLogin frameLogin;

    /** full constructor */
    public FrameLoginRole(Long loginRoleId, String loginRoleState, com.xwtech.framework.pub.po.FrameRole frameRole, com.xwtech.framework.pub.po.FrameLogin frameLogin) {
        this.loginRoleId = loginRoleId;
        this.loginRoleState = loginRoleState;
        this.frameRole = frameRole;
        this.frameLogin = frameLogin;
    }

    /** default constructor */
    public FrameLoginRole() {
    }

    public Long getLoginRoleId() {
        return this.loginRoleId;
    }

    public void setLoginRoleId(Long loginRoleId) {
        this.loginRoleId = loginRoleId;
    }

    public String getLoginRoleState() {
        return this.loginRoleState;
    }

    public void setLoginRoleState(String loginRoleState) {
        this.loginRoleState = loginRoleState;
    }

    public com.xwtech.framework.pub.po.FrameRole getFrameRole() {
        return this.frameRole;
    }

    public void setFrameRole(com.xwtech.framework.pub.po.FrameRole frameRole) {
        this.frameRole = frameRole;
    }

    public com.xwtech.framework.pub.po.FrameLogin getFrameLogin() {
        return this.frameLogin;
    }

    public void setFrameLogin(com.xwtech.framework.pub.po.FrameLogin frameLogin) {
        this.frameLogin = frameLogin;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("loginRoleId", getLoginRoleId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof FrameLoginRole) ) return false;
        FrameLoginRole castOther = (FrameLoginRole) other;
        return new EqualsBuilder()
            .append(this.getLoginRoleId(), castOther.getLoginRoleId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getLoginRoleId())
            .toHashCode();
    }

}
