package com.xwtech.framework.pub.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class FrameMenu implements Serializable {

    /** identifier field */
    private Integer menuId;

    /** persistent field */
    private String menuName;

    /** persistent field */
    private int menuSort;

    /** persistent field */
    private String menuState;

    /** persistent field */
    private Set frameSubmenus;

    /** full constructor */
    public FrameMenu(Integer menuId, String menuName, int menuSort, String menuState, Set frameSubmenus) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuSort = menuSort;
        this.menuState = menuState;
        this.frameSubmenus = frameSubmenus;
    }

    /** default constructor */
    public FrameMenu() {
    }

    public Integer getMenuId() {
        return this.menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuSort() {
        return this.menuSort;
    }

    public void setMenuSort(int menuSort) {
        this.menuSort = menuSort;
    }

    public String getMenuState() {
        return this.menuState;
    }

    public void setMenuState(String menuState) {
        this.menuState = menuState;
    }

    public Set getFrameSubmenus() {
        return this.frameSubmenus;
    }

    public void setFrameSubmenus(Set frameSubmenus) {
        this.frameSubmenus = frameSubmenus;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("menuId", getMenuId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof FrameMenu) ) return false;
        FrameMenu castOther = (FrameMenu) other;
        return new EqualsBuilder()
            .append(this.getMenuId(), castOther.getMenuId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getMenuId())
            .toHashCode();
    }

}
