package com.xwtech.framework.pub.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class FrameRole implements Serializable {

    /** identifier field */
    private Long roleId;

    /** persistent field */
    private String roleName;

    /** nullable persistent field */
    private String roleDesc;

    /** persistent field */
    private String roleState;

    /** persistent field */
    private Set frameRoleSubmenus;

    /** persistent field */
    private Set frameUrls;

    /** persistent field */
    private Set frameLoginRoles;

    /** full constructor */
    public FrameRole(Long roleId, String roleName, String roleDesc, String roleState, Set frameRoleSubmenus, Set frameUrls, Set frameLoginRoles) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
        this.roleState = roleState;
        this.frameRoleSubmenus = frameRoleSubmenus;
        this.frameUrls = frameUrls;
        this.frameLoginRoles = frameLoginRoles;
    }

    /** default constructor */
    public FrameRole() {
    }

    /** minimal constructor */
    public FrameRole(Long roleId, String roleName, String roleState, Set frameRoleSubmenus, Set frameUrls, Set frameLoginRoles) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleState = roleState;
        this.frameRoleSubmenus = frameRoleSubmenus;
        this.frameUrls = frameUrls;
        this.frameLoginRoles = frameLoginRoles;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return this.roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getRoleState() {
        return this.roleState;
    }

    public void setRoleState(String roleState) {
        this.roleState = roleState;
    }

    public Set getFrameRoleSubmenus() {
        return this.frameRoleSubmenus;
    }

    public void setFrameRoleSubmenus(Set frameRoleSubmenus) {
        this.frameRoleSubmenus = frameRoleSubmenus;
    }

    public Set getFrameUrls() {
        return this.frameUrls;
    }

    public void setFrameUrls(Set frameUrls) {
        this.frameUrls = frameUrls;
    }

    public Set getFrameLoginRoles() {
        return this.frameLoginRoles;
    }

    public void setFrameLoginRoles(Set frameLoginRoles) {
        this.frameLoginRoles = frameLoginRoles;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("roleId", getRoleId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof FrameRole) ) return false;
        FrameRole castOther = (FrameRole) other;
        return new EqualsBuilder()
            .append(this.getRoleId(), castOther.getRoleId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRoleId())
            .toHashCode();
    }

}
