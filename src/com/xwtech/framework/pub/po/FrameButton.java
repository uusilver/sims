package com.xwtech.framework.pub.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class FrameButton implements Serializable {

    /** identifier field */
    private Long buttonId;

    /** persistent field */
    private String buttonName;

    /** nullable persistent field */
    private String buttonAttr;

    /** nullable persistent field */
    private String buttonRegexp;

    /** persistent field */
    private String buttonState;

    /** persistent field */
    private Set frameButtonUrls;

    /** full constructor */
    public FrameButton(Long buttonId, String buttonName, String buttonAttr, String buttonRegexp, String buttonState, Set frameButtonUrls) {
        this.buttonId = buttonId;
        this.buttonName = buttonName;
        this.buttonAttr = buttonAttr;
        this.buttonRegexp = buttonRegexp;
        this.buttonState = buttonState;
        this.frameButtonUrls = frameButtonUrls;
    }

    /** default constructor */
    public FrameButton() {
    }

    /** minimal constructor */
    public FrameButton(Long buttonId, String buttonName, String buttonState, Set frameButtonUrls) {
        this.buttonId = buttonId;
        this.buttonName = buttonName;
        this.buttonState = buttonState;
        this.frameButtonUrls = frameButtonUrls;
    }

    public Long getButtonId() {
        return this.buttonId;
    }

    public void setButtonId(Long buttonId) {
        this.buttonId = buttonId;
    }

    public String getButtonName() {
        return this.buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonAttr() {
        return this.buttonAttr;
    }

    public void setButtonAttr(String buttonAttr) {
        this.buttonAttr = buttonAttr;
    }

    public String getButtonRegexp() {
        return this.buttonRegexp;
    }

    public void setButtonRegexp(String buttonRegexp) {
        this.buttonRegexp = buttonRegexp;
    }

    public String getButtonState() {
        return this.buttonState;
    }

    public void setButtonState(String buttonState) {
        this.buttonState = buttonState;
    }

    public Set getFrameButtonUrls() {
        return this.frameButtonUrls;
    }

    public void setFrameButtonUrls(Set frameButtonUrls) {
        this.frameButtonUrls = frameButtonUrls;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("buttonId", getButtonId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof FrameButton) ) return false;
        FrameButton castOther = (FrameButton) other;
        return new EqualsBuilder()
            .append(this.getButtonId(), castOther.getButtonId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getButtonId())
            .toHashCode();
    }

}
