package com.xwtech.framework.pub.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class FrameLoginBak implements Serializable {

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

    /** full constructor */
    public FrameLoginBak(Long loginId, String loginName, String loginPwd, String loginLastTime, String loginState) {
        this.loginId = loginId;
        this.loginName = loginName;
        this.loginPwd = loginPwd;
        this.loginLastTime = loginLastTime;
        this.loginState = loginState;
    }

    /** default constructor */
    public FrameLoginBak() {
    }

    /** minimal constructor */
    public FrameLoginBak(Long loginId, String loginName, String loginPwd, String loginState) {
        this.loginId = loginId;
        this.loginName = loginName;
        this.loginPwd = loginPwd;
        this.loginState = loginState;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("loginId", getLoginId())
            .toString();
    }

}
