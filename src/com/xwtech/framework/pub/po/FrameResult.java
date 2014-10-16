package com.xwtech.framework.pub.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class FrameResult implements Serializable {

    /** identifier field */
    private Long resultId;

    /** persistent field */
    private Long resultCode;

    /** persistent field */
    private String resultInfo;

    /** persistent field */
    private int resultType;

    /** persistent field */
    private String resultState;

    /** persistent field */
    private String constantName;

    /** full constructor */
    public FrameResult(Long resultId, Long resultCode, String resultInfo, int resultType, String resultState, String constantName) {
        this.resultId = resultId;
        this.resultCode = resultCode;
        this.resultInfo = resultInfo;
        this.resultType = resultType;
        this.resultState = resultState;
        this.constantName = constantName;
    }

    /** default constructor */
    public FrameResult() {
    }

    public Long getResultId() {
        return this.resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public String getResultInfo() {
        return this.resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public int getResultType() {
        return this.resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
    }

    public String getResultState() {
        return this.resultState;
    }

    public void setResultState(String resultState) {
        this.resultState = resultState;
    }

    public String getConstantName() {
        return this.constantName;
    }

    public void setConstantName(String constantName) {
        this.constantName = constantName;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("resultId", getResultId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof FrameResult) ) return false;
        FrameResult castOther = (FrameResult) other;
        return new EqualsBuilder()
            .append(this.getResultId(), castOther.getResultId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getResultId())
            .toHashCode();
    }

	public Long getResultCode() {
		return resultCode;
	}

	public void setResultCode(Long resultCode) {
		this.resultCode = resultCode;
	}

}
