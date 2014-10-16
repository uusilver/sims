package com.xwtech.framework.pub.commons;

import java.io.File;
import java.util.Map;

import javax.imageio.stream.FileImageInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

/**
 * @title: ImgAudioView.java
 * @description: 图片展示、声音播放View类，此类重写了render()方法
 * 
 * @author: dongyuese
 *
 */
public class ImgAudioView implements View
{

	public static final String FILE_PATH = "filePath";

	private String contentType = null;

	public ImgAudioView(String contentType)
	{
		this.contentType = contentType;
	}

	public String getContentType()
	{
		return this.contentType;
	}

	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String imgPath = (String) model.get(ImgAudioView.FILE_PATH);
		FileImageInputStream fiis = new FileImageInputStream(new File(imgPath));
		ServletOutputStream sos = response.getOutputStream();
		byte[] buff = new byte[1024];
		int i = 0;
		while ((i = fiis.read(buff)) != -1)
		{
			sos.write(buff, 0, i);
		}
		sos.flush();
		fiis.close();
	}

}
