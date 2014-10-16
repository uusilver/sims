package com.xwtech.framework.pub.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class FrameRoleSubmenu implements Serializable {

    /** identifier field */
    private Long roleSubmenuId;

    /** persistent field */
    private String roleSubmenuState;

    /** persistent field */
    private com.xwtech.framework.pub.po.FrameRole frameRole;

    /** persistent field */
    private com.xwtech.framework.pub.po.FrameSubmenu frameSubmenu;

    /** full constructor */
    public FrameRoleSubmenu(Long roleSubmenuId, String roleSubmenuState, com.xwtech.framework.pub.po.FrameRole frameRole, com.xwtech.framework.pub.po.FrameSubmenu frameSubmenu) {
        this.roleSubmenuId = roleSubmenuId;
        this.roleSubmenuState = roleSubmenuState;
        this.frameRole = frameRole;
        this.frameSubmenu = frameSubmenu;
    }

    /** default constructor */
    public FrameRoleSubmenu() {
    }

    public Long getRoleSubmenuId() {
        return this.roleSubmenuId;
    }

    public void setRoleSubmenuId(Long roleSubmenuId) {
        this.roleSubmenuId = roleSubmenuId;
    }

    public String getRoleSubmenuState() {
        return this.roleSubmenuState;
    }

    public void setRoleSubmenuState(String roleSubmenuState) {
        this.roleSubmenuState = roleSubmenuState;
    }

    public com.xwtech.framework.pub.po.FrameRole getFrameRole() {
        return this.frameRole;
    }

    public void setFrameRole(com.xwtech.framework.pub.po.FrameRole frameRole) {
        this.frameRole = frameRole;
    }

    public com.xwtech.framework.pub.po.FrameSubmenu getFrameSubmenu() {
        return this.frameSubmenu;
    }

    public void setFrameSubmenu(com.xwtech.framework.pub.po.FrameSubmenu frameSubmenu) {
        this.frameSubmenu = frameSubmenu;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("roleSubmenuId", getRoleSubmenuId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof FrameRoleSubmenu) ) return false;
        FrameRoleSubmenu castOther = (FrameRoleSubmenu) other;
        return new EqualsBuilder()
            .append(this.getRoleSubmenuId(), castOther.getRoleSubmenuId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRoleSubmenuId())
            .toHashCode();
    }

}
