package com.xwtech.framework.pub.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public abstract class MultiFileUploader {

	/**
	 * 获取上传文件列表
	 * 
	 * @param request
	 * @return
	 */
	protected List<MultipartFile> getFileList(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> fileList = new ArrayList<MultipartFile>();
		for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
			String key = (String) it.next();
			MultipartFile file = multipartRequest.getFile(key);
			if (file.getOriginalFilename().length() > 0) {
				fileList.add(file);
			}
		}
		return fileList;
	}

	/**
	 * 文件上传到服务器
	 * 
	 * @param file
	 * @param path
	 * @return
	 * @throws Exception
	 */
	protected String uploadFile(MultipartFile file,String path) throws Exception {
		try{
			if(validateFile(file)){
				String filePath = path + file.getOriginalFilename();
				//上传文件
		        FileUtils.write(file.getBytes(), filePath);
		        return filePath;
			}else{
				throw new Exception("上传文件不符合规范！");
			}
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * 文件上传到服务器
	 * 
	 * @param file
	 * @param path
	 * @param fileNm
	 * @return
	 * @throws Exception
	 */
	protected String uploadFile(MultipartFile file,String path,String fileNm) throws Exception {
		try{
			if(validateFile(file)){
				String filePath = path + fileNm;
				//上传文件
		        FileUtils.write(file.getBytes(), filePath);
		        return filePath;
			}else{
				throw new Exception("上传文件不符合规范！");
			}
		}catch(Exception e){
			throw e;
		}
	}

	public abstract boolean validateFile(MultipartFile file);
}
