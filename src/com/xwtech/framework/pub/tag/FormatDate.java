package com.xwtech.framework.pub.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.xwtech.framework.pub.utils.DateUtils;

public class FormatDate extends TagSupport {
	private String dateNumber; // 原始时间数字 12位

	public String getDateNumber() {
		return dateNumber;
	}

	public void setDateNumber(String dateNumber) {
		this.dateNumber = dateNumber;
	}

	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().print(getHtml((HttpServletRequest) pageContext.getRequest()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	/**
	 * 生成Html片段
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Html片段
	 */
	public String getHtml(HttpServletRequest request) {

		if (null == dateNumber || "".equals(dateNumber)) {
			return " - -  : : ";
		} else if (dateNumber.length() == 6) {
			return DateUtils.formatChar6(dateNumber);
		} else if (dateNumber.length() == 8) {
			return DateUtils.formatChar8(dateNumber);
		} else if (dateNumber.length() == 10) {
			return DateUtils.formatChar10(dateNumber);
		} else if (dateNumber.length() == 12) {
			return DateUtils.formatChar12(dateNumber);
		} else if (dateNumber.length() == 14) {
			return DateUtils.formatChar14(dateNumber);
		}

		return "";
	}
}
