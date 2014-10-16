package com.xwtech.framework.pub.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class FrameFieldCheck implements Serializable {

    /** identifier field */
    private Long fieldCheckId;

    /** persistent field */
    private String tableName;

    /** persistent field */
    private String fieldName;

    /** persistent field */
    private String checkValue;

    /** persistent field */
    private String checkDesc;

    /** persistent field */
    private String constantName;

    /** persistent field */
    private String checkState;

    /** full constructor */
    public FrameFieldCheck(Long fieldCheckId, String tableName, String fieldName, String checkValue, String checkDesc, String constantName, String checkState) {
        this.fieldCheckId = fieldCheckId;
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.checkValue = checkValue;
        this.checkDesc = checkDesc;
        this.constantName = constantName;
        this.checkState = checkState;
    }

    /** default constructor */
    public FrameFieldCheck() {
    }

    public Long getFieldCheckId() {
        return this.fieldCheckId;
    }

    public void setFieldCheckId(Long fieldCheckId) {
        this.fieldCheckId = fieldCheckId;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getCheckValue() {
        return this.checkValue;
    }

    public void setCheckValue(String checkValue) {
        this.checkValue = checkValue;
    }

    public String getCheckDesc() {
        return this.checkDesc;
    }

    public void setCheckDesc(String checkDesc) {
        this.checkDesc = checkDesc;
    }

    public String getConstantName() {
        return this.constantName;
    }

    public void setConstantName(String constantName) {
        this.constantName = constantName;
    }

    public String getCheckState() {
        return this.checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("fieldCheckId", getFieldCheckId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof FrameFieldCheck) ) return false;
        FrameFieldCheck castOther = (FrameFieldCheck) other;
        return new EqualsBuilder()
            .append(this.getFieldCheckId(), castOther.getFieldCheckId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getFieldCheckId())
            .toHashCode();
    }

}
