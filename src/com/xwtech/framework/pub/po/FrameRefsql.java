package com.xwtech.framework.pub.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class FrameRefsql implements Serializable {

    /** identifier field */
    private Integer refsqlId;

    /** nullable persistent field */
    private String refName;

    /** nullable persistent field */
    private String sqlString;

    /** nullable persistent field */
    private String refTitle;

    /** full constructor */
    public FrameRefsql(Integer refsqlId, String refName, String sqlString, String refTitle) {
        this.refsqlId = refsqlId;
        this.refName = refName;
        this.sqlString = sqlString;
        this.refTitle = refTitle;
    }

    /** default constructor */
    public FrameRefsql() {
    }

    /** minimal constructor */
    public FrameRefsql(Integer refsqlId) {
        this.refsqlId = refsqlId;
    }

    public Integer getRefsqlId() {
        return this.refsqlId;
    }

    public void setRefsqlId(Integer refsqlId) {
        this.refsqlId = refsqlId;
    }

    public String getRefName() {
        return this.refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public String getSqlString() {
        return this.sqlString;
    }

    public void setSqlString(String sqlString) {
        this.sqlString = sqlString;
    }

    public String getRefTitle() {
        return this.refTitle;
    }

    public void setRefTitle(String refTitle) {
        this.refTitle = refTitle;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("refsqlId", getRefsqlId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof FrameRefsql) ) return false;
        FrameRefsql castOther = (FrameRefsql) other;
        return new EqualsBuilder()
            .append(this.getRefsqlId(), castOther.getRefsqlId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRefsqlId())
            .toHashCode();
    }

}
