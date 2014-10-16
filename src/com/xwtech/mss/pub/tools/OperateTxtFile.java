package com.xwtech.mss.pub.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.xwtech.framework.pub.utils.FileUtils;
import com.xwtech.framework.pub.web.BaseFrameworkApplication;
import com.xwtech.framework.pub.web.FrameworkApplication;
import com.xwtech.mss.formBean.BaseForm;

public class OperateTxtFile {

	/*
	 * author:jzy date: 09-01-12 titel:数据导出 event:
	 * /mss/jsp/dataOperationController.do?method=exportData (号码筛选后导出) request
	 * parameter: 1 sql(号码查询sql) 2 exportDir(导出目录) 3 fileName (文件名) 
	 * operation: 1 从request中得到参数 2 调用导出公共类 /按sql查询得到结果集 		3 输出文件
	 * 												\按导出目录文件名创建文件 
	 * 												 \写文件  			
	 */
	public static void exportData(HttpServletRequest request, HttpServletResponse response, String str_sql,
			String str_exportDir, String str_fileName) {
		// 附件路径前缀
		String prefixPath = FrameworkApplication.frameworkProperties.getPrefixPath();
		// 取得项目所在的绝对路径
		String realpath = BaseFrameworkApplication.FrameworkWebAppRootPath;

		// 创建导出文件 返回文件在上传目录下位置ex: /file/export/1.txt
		String strAbsolutePath = FileUtils.createFile(str_sql, realpath + prefixPath + str_exportDir, str_fileName);

		// 创建输出流
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {

			File sourceFile = new File(strAbsolutePath);
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment;" + " filename="
					+ new String(sourceFile.getName().getBytes("gb2312"), "ISO-8859-1"));

			bis = new java.io.BufferedInputStream(new java.io.FileInputStream(strAbsolutePath));
			os = response.getOutputStream();
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				os.write(buff, 0, bytesRead);
			}
			os.flush();
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null)
					bis.close();
				bis = null;
				if (os != null) {
					os.close();
					os = null;
					response.flushBuffer();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
