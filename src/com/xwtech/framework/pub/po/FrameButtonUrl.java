package com.xwtech.framework.pub.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class FrameButtonUrl implements Serializable {

    /** identifier field */
    private Long buttonUrlId;

    /** persistent field */
    private String buttonUrlState;

    /** persistent field */
    private com.xwtech.framework.pub.po.FrameButton frameButton;

    /** persistent field */
    private com.xwtech.framework.pub.po.FrameUrl frameUrl;

    /** full constructor */
    public FrameButtonUrl(Long buttonUrlId, String buttonUrlState, com.xwtech.framework.pub.po.FrameButton frameButton, com.xwtech.framework.pub.po.FrameUrl frameUrl) {
        this.buttonUrlId = buttonUrlId;
        this.buttonUrlState = buttonUrlState;
        this.frameButton = frameButton;
        this.frameUrl = frameUrl;
    }

    /** default constructor */
    public FrameButtonUrl() {
    }

    public Long getButtonUrlId() {
        return this.buttonUrlId;
    }

    public void setButtonUrlId(Long buttonUrlId) {
        this.buttonUrlId = buttonUrlId;
    }

    public String getButtonUrlState() {
        return this.buttonUrlState;
    }

    public void setButtonUrlState(String buttonUrlState) {
        this.buttonUrlState = buttonUrlState;
    }

    public com.xwtech.framework.pub.po.FrameButton getFrameButton() {
        return this.frameButton;
    }

    public void setFrameButton(com.xwtech.framework.pub.po.FrameButton frameButton) {
        this.frameButton = frameButton;
    }

    public com.xwtech.framework.pub.po.FrameUrl getFrameUrl() {
        return this.frameUrl;
    }

    public void setFrameUrl(com.xwtech.framework.pub.po.FrameUrl frameUrl) {
        this.frameUrl = frameUrl;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("buttonUrlId", getButtonUrlId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof FrameButtonUrl) ) return false;
        FrameButtonUrl castOther = (FrameButtonUrl) other;
        return new EqualsBuilder()
            .append(this.getButtonUrlId(), castOther.getButtonUrlId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getButtonUrlId())
            .toHashCode();
    }

}
