package com.xwtech.mss.pub.tools;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xwtech.mss.formBean.BaseForm;
import com.xwtech.mss.formBean.IGetFormByReq;

public class StructForm {
	private static final Log log = LogFactory.getLog(StructForm.class);

	/**
	 * @功能描述：根据页面表单元素和基础Form类进行封装
	 * @param request
	 * @param multiRequest
	 * @return 基础Form
	 * @throws ClassNotFoundException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public Object strucReqInfoSearchForm(HttpServletRequest request, MultipartHttpServletRequest multiRequest)
			throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		// 实例化基础Form。(如果以后涉及较多的Form元素，考虑采用不同的Form)
		BaseForm baseForm = new BaseForm();
		// 通过反射机制加载类
		Class baseFormClass = baseForm.getClass();

		Field[] field = baseFormClass.getDeclaredFields();
		String param = "";

		for (int i = 0; i < field.length; i++) {
			param = field[i].getName();
			if (param.indexOf("class$") > -1) {// 如果是得到的是class，则过滤
				continue;
			}
//			log.debug("Property " + i + " : " + param);

			if (param != null && !param.equals("")) {
				String methodName = "set" + param.substring(0, 1).toUpperCase() + param.substring(1, param.length());
				Method targetMethod = baseFormClass.getMethod(methodName, new Class[] { String.class });
				// 取得传递的参数值
				String paramValue = "";
				if (request != null) {
					paramValue = request.getParameter(param) == null ? "" : request.getParameter(param).trim();
				} else if (multiRequest != null) {
					paramValue = multiRequest.getParameter(param) == null ? "" : multiRequest.getParameter(param)
							.trim();
				}
				// 依次获得传递的参数并set到class里面
//				if (paramValue != null && !paramValue.equals("")) {
					targetMethod.invoke(baseForm, new Object[] { paramValue });
//				}
			}

		}

		return baseForm;
	}
	
/**
 * add by jzy 09-01-16
 * 根据上面strucReqInfoSearchForm方法修改, 根据request中传递的paramter填充form元素,from需要继承IGetFormByReq接口
 * form中成员变量的名称必须和表单元素名称相同
 * @param request
 * @param iGetFormByReq
 * @return
 * @throws ClassNotFoundException
 * @throws IllegalArgumentException
 * @throws IllegalAccessException
 * @throws InvocationTargetException
 * @throws NoSuchMethodException
 */
	public static IGetFormByReq strucReqInfoForm(HttpServletRequest request,IGetFormByReq iGetFormByReq)
	throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
	NoSuchMethodException {// 获得当前对象所属的类
		Class formClass = iGetFormByReq.getClass();
		
		
		// 得到类的字段信息
		Field[] field = formClass.getDeclaredFields();
		String param = "";
		
		for (int i = 0; i < field.length; i++)// 遍历form中的字段
		{
			param = field[i].getName();
			if (param.indexOf("class$") > -1)
			{// 如果是得到的是class，则过滤
				continue;
			}
			
			if (param != null && !param.equals(""))
			{
				// 拼写当前字段的set方法
				String methodName = "set" + param.substring(0, 1).toUpperCase()
						+ param.substring(1, param.length());
				// 获得当前字段set方法信息
				Method targetMethod = formClass.getMethod(methodName,
						new Class[] { String.class });
				
				String paramValue = "";
				if (request != null)
				{
					// 在map中寻找当前字段的值
					paramValue = request.getParameter(param) == null ? ""
							: String.valueOf(request.getParameter(param)).trim();
				}
				// 依次获得传递的参数并set到class里面
				if (paramValue != null && !paramValue.equals(""))
				{
					// 如果值存在 放到form中
					targetMethod.invoke(iGetFormByReq, new Object[] { paramValue });
				}
			}
		}
		return iGetFormByReq;
	}
}
