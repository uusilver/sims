package com.xwtech.framework.pub.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class FrameLogin implements Serializable {

    /** identifier field */
    private Long loginId;

    /** persistent field */
    private String loginName;

    /** persistent field */
    private String loginPwd;

    /** nullable persistent field */
    private String loginLastTime;

    /** persistent field */
    private String loginState;

    /** persistent field */
    private Set frameLoginRoles;

    /** full constructor */
    public FrameLogin(Long loginId, String loginName, String loginPwd, String loginLastTime, String loginState, Set frameLoginRoles) {
        this.loginId = loginId;
        this.loginName = loginName;
        this.loginPwd = loginPwd;
        this.loginLastTime = loginLastTime;
        this.loginState = loginState;
        this.frameLoginRoles = frameLoginRoles;
    }

    /** default constructor */
    public FrameLogin() {
    }

    /** minimal constructor */
    public FrameLogin(Long loginId, String loginName, String loginPwd, String loginState, Set frameLoginRoles) {
        this.loginId = loginId;
        this.loginName = loginName;
        this.loginPwd = loginPwd;
        this.loginState = loginState;
        this.frameLoginRoles = frameLoginRoles;
    }

    public Long getLoginId() {
        return this.loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPwd() {
        return this.loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getLoginLastTime() {
        return this.loginLastTime;
    }

    public void setLoginLastTime(String loginLastTime) {
        this.loginLastTime = loginLastTime;
    }

    public String getLoginState() {
        return this.loginState;
    }

    public void setLoginState(String loginState) {
        this.loginState = loginState;
    }

    public Set getFrameLoginRoles() {
        return this.frameLoginRoles;
    }

    public void setFrameLoginRoles(Set frameLoginRoles) {
        this.frameLoginRoles = frameLoginRoles;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("loginId", getLoginId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof FrameLogin) ) return false;
        FrameLogin castOther = (FrameLogin) other;
        return new EqualsBuilder()
            .append(this.getLoginId(), castOther.getLoginId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getLoginId())
            .toHashCode();
    }

}
