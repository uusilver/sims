package com.xwtech.framework.pub.commons;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

/**
 * @title: HtmlView.java
 * @description: 异步请求View类，此类重写了render()方法
 * 
 * @author: dongyuese
 *
 */
public class HtmlView implements View
{

	public static final HtmlView instance = new HtmlView();

	public static final String HTML_RESULT = "htmlResult";

	private HtmlView()
	{
	}

	public String getContentType()
	{
		return "text/html; charset=UTF-8";
	}

	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String htmlResult = (String) model.get(HtmlView.HTML_RESULT);
		PrintWriter writer = response.getWriter();
		writer.write(htmlResult.toString());
	}

}
