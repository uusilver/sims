package com.xwtech.mss.pub.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;


public class ConstructParamters {

	/**
	 * 根据传递的参数组织传递的参数组成的字符串
	 * @param paramStr 要组织的参数名称，用，隔开
	 * @param request
	 * @param multiRequest
	 * @return 参数组成的字符串
	 */
    public String strucQueryFilter(String paramStr, HttpServletRequest request, MultipartHttpServletRequest multiRequest){
        StringBuffer filter = new StringBuffer();
        
        if(paramStr!=null && !paramStr.equals("")){
        	String[] params = paramStr.split(",");
        	for(int i=0; i<params.length; i++){
        		String paramValue = "";
        		if(request!=null){
        			paramValue = request.getParameter(params[i]);
        		}else if(multiRequest!=null){
        			paramValue = multiRequest.getParameter(params[i]);
        		}
        		if(paramValue!=null && !paramValue.equals("")){
        			filter.append("&" + params[i] + "=" + paramValue);
        		}
        	}
        	
        }
        return filter.toString();
    }
    
    /**
     * 根据传递的参数组织传递的参数组成的查询formbean
     * @param paramStr
     * @param searFormName
     * @param request
     * @param multiRequest
     * @return Object(即传递过来的form)
     * @throws ClassNotFoundException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public Object strucReqInfoSearchForm(Object searchForm, String paramStr, HttpServletRequest request,
			MultipartHttpServletRequest multiRequest) throws ClassNotFoundException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// 通过反射机制加载类
		Class commClass = searchForm.getClass();
		// 依次获得传递的参数并set到class里面
		if (paramStr != null && !paramStr.equals("")) {
			String[] params = paramStr.split(",");
			for (int i = 0; i < params.length; i++) {
				if (params[i] != null && !params[i].equals("")) {
					String methodName = "set" + params[i].substring(0, 1).toUpperCase()
							+ params[i].substring(1, params[i].length());
					Method targetMethod = commClass.getMethod(methodName, new Class[] { String.class });
					// 取得传递的参数值
					String paramValue = "";
					if (request != null) {
						paramValue = request.getParameter(params[i]) == null ? "" : request.getParameter(params[i])
								.trim();
					} else if (multiRequest != null) {
						paramValue = multiRequest.getParameter(params[i]) == null ? "" : multiRequest.getParameter(
								params[i]).trim();
					}
					if (paramValue != null && !paramValue.equals("")) {
						targetMethod.invoke(searchForm, new Object[] { paramValue });
					}
				}
			}

		}
		return searchForm;
	}

  

    /**
     * 根据传递的参数组织传递的参数组成的查询formbean
     * @param paramStr
     * @param searFormName
     * @param request
     * @param multiRequest
     * @return Object(即传递过来的form)
     * @throws ClassNotFoundException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public Object structReqInfoSearchForm(Object searchForm, String paramStr, HttpServletRequest request, MultipartHttpServletRequest multiRequest) 
    	throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
        // 通过反射机制加载类
        Class commClass = searchForm.getClass();
        //依次获得传递的参数并set到class里面
        if(paramStr!=null && !paramStr.equals("")){
        	String[] params = paramStr.split(",");
        	for(int i=0; i<params.length; i++){
        		if(params[i]!=null && !params[i].equals("")){
	        		String methodName = "set" + params[i].substring(0,1).toUpperCase() + params[i].substring(1,params[i].length());
	                Method targetMethod = commClass.getMethod(methodName, new Class[]{String.class});
	                //取得传递的参数值
	                String paramValue = "";
	        		if(request!=null){
	        			paramValue = request.getParameter(params[i])==null?"":request.getParameter(params[i]).trim();
	        		}else if(multiRequest!=null){
	        			paramValue = multiRequest.getParameter(params[i])==null?"":multiRequest.getParameter(params[i]).trim();
	        		}
	        		if(paramValue!=null && !paramValue.equals("")){
	        			targetMethod.invoke(searchForm, new Object[]{paramValue});
	        		}
        		}
        	}
        	
        }
        return searchForm;
    }

}
