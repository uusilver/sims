package com.xwtech.framework.pub.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class FrameSubmenu implements Serializable {

    /** identifier field */
    private Long submenuId;

    /** persistent field */
    private String submenuName;

    /** persistent field */
    private int submenuSort;

    /** persistent field */
    private String submenuState;

    /** persistent field */
    private com.xwtech.framework.pub.po.FrameUrl frameUrl;

    /** persistent field */
    private com.xwtech.framework.pub.po.FrameMenu frameMenu;

    /** persistent field */
    private Set frameRoleSubmenus;

    /** full constructor */
    public FrameSubmenu(Long submenuId, String submenuName, int submenuSort, String submenuState, com.xwtech.framework.pub.po.FrameUrl frameUrl, com.xwtech.framework.pub.po.FrameMenu frameMenu, Set frameRoleSubmenus) {
        this.submenuId = submenuId;
        this.submenuName = submenuName;
        this.submenuSort = submenuSort;
        this.submenuState = submenuState;
        this.frameUrl = frameUrl;
        this.frameMenu = frameMenu;
        this.frameRoleSubmenus = frameRoleSubmenus;
    }

    /** default constructor */
    public FrameSubmenu() {
    }

    public Long getSubmenuId() {
        return this.submenuId;
    }

    public void setSubmenuId(Long submenuId) {
        this.submenuId = submenuId;
    }

    public String getSubmenuName() {
        return this.submenuName;
    }

    public void setSubmenuName(String submenuName) {
        this.submenuName = submenuName;
    }

    public int getSubmenuSort() {
        return this.submenuSort;
    }

    public void setSubmenuSort(int submenuSort) {
        this.submenuSort = submenuSort;
    }

    public String getSubmenuState() {
        return this.submenuState;
    }

    public void setSubmenuState(String submenuState) {
        this.submenuState = submenuState;
    }

    public com.xwtech.framework.pub.po.FrameUrl getFrameUrl() {
        return this.frameUrl;
    }

    public void setFrameUrl(com.xwtech.framework.pub.po.FrameUrl frameUrl) {
        this.frameUrl = frameUrl;
    }

    public com.xwtech.framework.pub.po.FrameMenu getFrameMenu() {
        return this.frameMenu;
    }

    public void setFrameMenu(com.xwtech.framework.pub.po.FrameMenu frameMenu) {
        this.frameMenu = frameMenu;
    }

    public Set getFrameRoleSubmenus() {
        return this.frameRoleSubmenus;
    }

    public void setFrameRoleSubmenus(Set frameRoleSubmenus) {
        this.frameRoleSubmenus = frameRoleSubmenus;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("submenuId", getSubmenuId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof FrameSubmenu) ) return false;
        FrameSubmenu castOther = (FrameSubmenu) other;
        return new EqualsBuilder()
            .append(this.getSubmenuId(), castOther.getSubmenuId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getSubmenuId())
            .toHashCode();
    }

}
