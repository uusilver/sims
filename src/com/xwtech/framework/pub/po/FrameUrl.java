package com.xwtech.framework.pub.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class FrameUrl implements Serializable {

    /** identifier field */
    private Long urlId;

    /** persistent field */
    private String urlValue;

    /** persistent field */
    private String urlName;

    /** nullable persistent field */
    private String urlDesc;

    /** persistent field */
    private String urlState;

    /** persistent field */
    private com.xwtech.framework.pub.po.FrameRole frameRole;

    /** persistent field */
    private Set frameSubmenus;

    /** persistent field */
    private Set frameButtonUrls;

    /** full constructor */
    public FrameUrl(Long urlId, String urlValue, String urlName, String urlDesc, String urlState, com.xwtech.framework.pub.po.FrameRole frameRole, Set frameSubmenus, Set frameButtonUrls) {
        this.urlId = urlId;
        this.urlValue = urlValue;
        this.urlName = urlName;
        this.urlDesc = urlDesc;
        this.urlState = urlState;
        this.frameRole = frameRole;
        this.frameSubmenus = frameSubmenus;
        this.frameButtonUrls = frameButtonUrls;
    }

    /** default constructor */
    public FrameUrl() {
    }

    /** minimal constructor */
    public FrameUrl(Long urlId, String urlValue, String urlName, String urlState, com.xwtech.framework.pub.po.FrameRole frameRole, Set frameSubmenus, Set frameButtonUrls) {
        this.urlId = urlId;
        this.urlValue = urlValue;
        this.urlName = urlName;
        this.urlState = urlState;
        this.frameRole = frameRole;
        this.frameSubmenus = frameSubmenus;
        this.frameButtonUrls = frameButtonUrls;
    }

    public Long getUrlId() {
        return this.urlId;
    }

    public void setUrlId(Long urlId) {
        this.urlId = urlId;
    }

    public String getUrlValue() {
        return this.urlValue;
    }

    public void setUrlValue(String urlValue) {
        this.urlValue = urlValue;
    }

    public String getUrlName() {
        return this.urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getUrlDesc() {
        return this.urlDesc;
    }

    public void setUrlDesc(String urlDesc) {
        this.urlDesc = urlDesc;
    }

    public String getUrlState() {
        return this.urlState;
    }

    public void setUrlState(String urlState) {
        this.urlState = urlState;
    }

    public com.xwtech.framework.pub.po.FrameRole getFrameRole() {
        return this.frameRole;
    }

    public void setFrameRole(com.xwtech.framework.pub.po.FrameRole frameRole) {
        this.frameRole = frameRole;
    }

    public Set getFrameSubmenus() {
        return this.frameSubmenus;
    }

    public void setFrameSubmenus(Set frameSubmenus) {
        this.frameSubmenus = frameSubmenus;
    }

    public Set getFrameButtonUrls() {
        return this.frameButtonUrls;
    }

    public void setFrameButtonUrls(Set frameButtonUrls) {
        this.frameButtonUrls = frameButtonUrls;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("urlId", getUrlId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof FrameUrl) ) return false;
        FrameUrl castOther = (FrameUrl) other;
        return new EqualsBuilder()
            .append(this.getUrlId(), castOther.getUrlId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getUrlId())
            .toHashCode();
    }

}
