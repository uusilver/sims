package com.xwtech.mss.mssFtp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.xwtech.framework.pub.web.BaseFrameworkApplication;
import com.xwtech.mss.pub.tools.CommonOperation;

/**
 * <p>Title:OA从物资系统(mss)ftp下载附件，以及上传文件的操作 </p>
 * 
 * @author gu_daping
 */

public class MssFtpOperation {

	private static final Log log = LogFactory.getLog(MssFtpOperation.class);

	//南移ftp用户名
    private static String mssFtpUserName = "xwsx";
    //南移ftp密码
    private static String mssFtpPassWord = "xwsx";
    //南移ftp地址
    private static String mssFtpIp = "10.33.32.87";

    //从Mpms上传附件到Mpms的目录
    private static String ANNEX_FROM_MPMS_DIR = "xwsx/annex_from_mss";
    
    //OA上传xml文件到MpmsFtp的目录
    private static String XML_FROM_OA_DIR = "xwsx/xml_from_oa";

    //OA上传附件到MpmsFtp的目录
    private static String ANNEX_FROM_OA_DIR = "xwsx/annex_from_oa";
    
    //ftp客户端
    public FTPClient ftpClient = null;
    
    /**
     * 构造函数，当目录不存在的时候，创建文件夹
     */
    public MssFtpOperation() throws IOException{
    	log.info("create MpmsFtpOperation...............");
    	ftpClient = new FTPClient();
    	connectToMpmsFtpServer();
    	//判断指定路径是否存在，不存在就新建目录
    	checkPathExist("xwsx");
    	checkPathExist(XML_FROM_OA_DIR);
    	checkPathExist(ANNEX_FROM_OA_DIR);
    	checkPathExist(ANNEX_FROM_MPMS_DIR);
    	closeConnect();
    }

    /**
     * 判断能否连上服务器
     * @return flag:true:连上；false:连不上
     */
    public boolean ifConnectToServer(){
    	boolean flag = false;
    	
    	connectToMpmsFtpServer();
    	if(ftpClient.isConnected()){
    		closeConnect();
    		flag = true;
    	}
    	
    	return flag;
    }
    
    /**
     * 查找指定目录是否存在
     * @param String filePath 要查找的目录
     * @return boolean:存在:true，不存在:false
     * @throws IOException
     */
    private boolean checkPathExist(String filePath) throws IOException{
    	boolean existFlag = false;
    	try{
    		if(!ftpClient.changeWorkingDirectory(filePath)){
	    		ftpClient.makeDirectory(filePath);
	    	}
    		ftpClient.changeWorkingDirectory("/");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return existFlag;
    }

    /**
     * 查找指定目录是否存在
     * @param String filePath 要查找的目录
     * @return boolean:存在:true，不存在:false
     * @throws IOException
     */
    private boolean changeWorkingDirectory(String filePath) throws IOException{
    	boolean existFlag = false;
    	try{
    		String[] paths = filePath.split("/");
    		if(paths!=null && paths.length>0){
    			for(int i=0;i<paths.length;i++){
    				if(!ftpClient.changeWorkingDirectory(paths[i])){
    					existFlag = false;
    				}else{
    					existFlag = true;
    				}
    			}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return existFlag;
    }
    
    /**
     * 连接到mssftp服务器
     */
    private void connectToMpmsFtpServer() {
        if (!ftpClient.isConnected()) {
            int reply;
            try {
//                ftpClient=new FTPClient();
                ftpClient.connect(mssFtpIp);
                
                ftpClient.login(mssFtpUserName, mssFtpPassWord);
                reply = ftpClient.getReplyCode();

                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftpClient.disconnect();
                    log.error("FTP server refused connection.");
                }
                //每次新建连接时，将工作目录指向根目录
                if(ftpClient!=null && ftpClient.isConnected()){
            		ftpClient.changeWorkingDirectory("/");
                }
            } catch (Exception e) {
                log.error("登录ftp服务器【"+mssFtpIp+"】失败");
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭连接
     */
    private void closeConnect(){
        try{
            if(ftpClient!=null && ftpClient.isConnected()){
                ftpClient.logout();
                ftpClient.disconnect();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 转码[GBK ->  ISO-8859-1]
     * 不同的平台需要不同的转码
     * @param obj
     * @return
     */
    private String gbkToIso8859(Object obj){
        try{
            if(obj==null)
                return "";
            else
                return new String(obj.toString().getBytes("GBK"),"iso-8859-1");
        }catch(Exception e){
            return "";
        }
    }

    /**
     * 转码[ISO-8859-1 ->  GBK]
     * 不同的平台需要不同的转码
     * @param obj
     * @return
     */
    private String iso8859ToGbk(Object obj){
        try{
            if(obj==null)
                return "";
            else
                return new String(obj.toString().getBytes("iso-8859-1"),"GBK");
        }catch(Exception e){
            return "";
        }
    }
    
    /**
     * 设置传输文件的类型[文本文件或者二进制文件]
     * @param fileType--BINARY_FILE_TYPE、ASCII_FILE_TYPE 
     */
    private void setFileType(int fileType){
        try{
            ftpClient.setFileType(fileType);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 查找指定目录下面指定名称的文件是否存在
     * @param String filePath 要查找的目录
     * @param String fileName 要查找的文件名称
     * @return boolean:存在:true，不存在:false
     * @throws IOException
     */
    private boolean checkFileExist(String filePath, String fileName) throws IOException{
    	boolean existFlag = false;
		//跳转到指定的文件目录
		if(filePath!=null && !filePath.equals("")){
			if(filePath.indexOf("/")!=-1){
				int index = 0;
				while((index=filePath.indexOf("/")) != -1){
    				ftpClient.changeWorkingDirectory(filePath.substring(0,index));
    				filePath = filePath.substring(index+1,filePath.length());
				}
				if(!filePath.equals("")){
					ftpClient.changeWorkingDirectory(filePath);
				}
			}else{
				ftpClient.changeWorkingDirectory(filePath);
			}
		}
    	String[] fileNames = ftpClient.listNames();
    	if(fileNames!=null && fileNames.length>0){
	    	for(int i=0;i<fileNames.length;i++){
	    		if(fileNames[i]!=null && iso8859ToGbk(fileNames[i]).equals(fileName)){
	    			existFlag = true;
	    			break;
	    		}
	    	}
    	}
		ftpClient.changeWorkingDirectory("/");
    	return existFlag;
    }
    
    /**
     * 从mssFtp下载指定名称的文件到本地
     * @param String remoteFileName --服务器上的文件名(只需要文件名，比如"req_0823.doc")
     * @param String localFileName--本地文件名（包括完整的物理路径和文件名，比如"F:/ftpfile/req_0823.doc"，文件名可以自己定，可以不和服务器上的名字一致）
     */
    private boolean downloadFileByName(String remoteFilePath, String remoteFileName,String localFileName) throws IOException{
        boolean returnValue = false;
    	//下载文件
        BufferedOutputStream buffOut=null;
        try{
        	//连接mssFtp服务器
            connectToMpmsFtpServer();
        	File localFile = new File(localFileName.substring(0,localFileName.lastIndexOf("/")));
        	if(!localFile.exists()){
        		localFile.mkdirs();
        	}
        	if(!checkFileExist(remoteFilePath, remoteFileName)){
        		log.error("<----------- ERR : file  " + remoteFilePath + "/" + remoteFileName + " does not exist, download failed!----------->");
        		return false;
        	}
        	else{
        		//跳转到指定的文件目录
        		if(remoteFilePath!=null){
        			if(remoteFilePath.indexOf("/")!=-1){
        				int index = 0;
        				while((index=remoteFilePath.indexOf("/")) != -1){
	        				ftpClient.changeWorkingDirectory(remoteFilePath.substring(0,index));
	        				remoteFilePath = remoteFilePath.substring(index+1,remoteFilePath.length());
        				}
        				if(!remoteFilePath.equals("")){
        					ftpClient.changeWorkingDirectory(remoteFilePath);
        				}
        			}else{
        				ftpClient.changeWorkingDirectory(remoteFilePath);
        			}
        		}
	            //设置传输二进制文件
	            setFileType(FTP.BINARY_FILE_TYPE);
	            //获得服务器文件
	            buffOut=new BufferedOutputStream(new FileOutputStream(localFileName));
	            returnValue = ftpClient.retrieveFile(gbkToIso8859(remoteFileName), buffOut);
	            //输出操作结果信息
	            if(returnValue){
	            	log.info("<----------- INFO: download " + remoteFilePath + "/" + remoteFileName + " from mss ftp ： succeed! ----------->");
	            }else{
	            	log.error("<----------- ERR : download " + remoteFilePath + "/" + remoteFileName + " from mss ftp : failed! ----------->");
	            }
        	}
            //关闭连接
            closeConnect();
        }catch(Exception e){
            e.printStackTrace();
            returnValue = false;
            //输出操作结果信息
            log.error("<----------- ERR : download " + remoteFilePath + "/" + remoteFileName + " from mss ftp : failed! ----------->");
        }finally{
            try{
                if(buffOut!=null){
                    buffOut.close();
                }
                if(ftpClient.isConnected()){
                    closeConnect();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    	//若文件下载失败，且本地文件为0，则删除本地文件
    	File locFile = new File(localFileName);
    	if(returnValue==false && locFile.exists() && locFile.length()==0){
    		locFile.delete();
    	}
        return returnValue;
    }

    /**
     * 从mssFtp下载xml文件
     * @param remoteFileName
     * @param localFileName
     * @throws IOException
     */
    public boolean downloadXmllFileByName(String remoteFileName,String localFileName) throws IOException{
    	boolean flag = downloadFileByName(XML_FROM_OA_DIR, remoteFileName, localFileName);
    	if(flag){
    		flag = delXmlAtMpmsFtp(remoteFileName);
    	}
    	return flag;
    }

    /**
     * 从mssFtp下载所有xml文件
     * @param String filePath 本地文件存放路径，以"/"结尾
     * @throws IOException
     */
    public void downloadAllXmlFile(String filePath) throws IOException{
    	log.info("downloadAllXmlFile...............");
    	try{
			//建立连接
	    	connectToMpmsFtpServer();
	    	String[] files = null;
	    	if(changeWorkingDirectory(XML_FROM_OA_DIR)){
	    		files = ftpClient.listNames();
	    	}
            closeConnect();
            int downloadFileCount = 0;
	    	if(filePath!=null && filePath.length()>1 && files!=null && files.length>0){
	    		File file = new File((filePath.charAt(filePath.length()-1)=='/'||filePath.charAt(filePath.length()-1)=='\\')?filePath:filePath+"/");
	    		if(!file.exists()){
	    			file.mkdirs();
	    		}
	    		for(int i=0; i<files.length; i++){
	        		if(downloadXmllFileByName(files[i], filePath + files[i]) == false){
        				continue;
        			}else{
        				downloadFileCount ++;
        			}
	    		}
	    	}
            log.info("【----------- INFO: download all XML from mss ftp : " + downloadFileCount + " succeed! -----------】");
    	}catch(Exception e){
            e.printStackTrace();
            log.info("【----------- ERR : download all XML from mss ftp : failed! -----------】");
        }finally{
			try{
			    if(ftpClient.isConnected()){
		            closeConnect();
			    }
			}catch(Exception e){
			    e.printStackTrace();
			}
        }
    }
    
    /**
     * 从mssFtp下载附件
     * @param remoteFileName
     * @param localFileName
     * @throws IOException
     */
    public boolean downloadAnnexByName(String remoteFileName,String localFileName) throws IOException{
    	boolean flag = downloadFileByName(ANNEX_FROM_OA_DIR, remoteFileName, localFileName);
    	if(flag){
    		flag = delAnnexAtMpmsFtp(remoteFileName);
    	}
    	return flag;
    }
    
    //上传请购单附件到mssFtp
    public boolean uploadAnnexToMpmsFtp(File uploadFile, String fileName) throws IOException{
    	boolean returnValue = false;
    	//上传文件
        BufferedInputStream buffIn=null;
        try{
        	if(!uploadFile.exists()){
        		log.error("<----------- ERR : annex named " + fileName + " not exist, upload failed! ----------->");
        		return false;
        	}else{
        		//建立连接
	        	connectToMpmsFtpServer();
	            //设置传输二进制文件
	            setFileType(FTP.BINARY_FILE_TYPE);
	            //获得文件
	            buffIn=new BufferedInputStream(new FileInputStream(uploadFile));
	            //上传文件到ftp
	            returnValue = ftpClient.storeFile(gbkToIso8859(ANNEX_FROM_MPMS_DIR + "/" + fileName), buffIn);
	            //输出操作结果信息
	            if(returnValue){
	            	log.info("<----------- INFO: upload annex " + uploadFile.getAbsolutePath() + " to mss ftp : succeed! ----------->");
	            }else{
	            	log.error("<----------- ERR : upload annex " + uploadFile.getAbsolutePath() + " to mss ftp : failed! ----------->");
	            }
	            //关闭连接
	            closeConnect();
        	}
        }catch(Exception e){
            e.printStackTrace();
            returnValue = false;
            log.error("<----------- ERR : upload annex " + uploadFile.getAbsolutePath() + " to mss ftp : failed! ----------->");
        }finally{
			try{
			    if(buffIn!=null){
			        buffIn.close();
			    }
			    if(ftpClient.isConnected()){
		            closeConnect();
			    }
			}catch(Exception e){
			    e.printStackTrace();
			}
        }
        return returnValue;
    }

    /**
     * 删除服务器上文件
     * @param fileDir 文件路径
     * @param fileName 文件名称
     * @throws IOException
     */
    private boolean delFile(String fileDir, String fileName) throws IOException{
    	boolean returnValue = false;
    	try{
        	//连接mssFtp服务器
            connectToMpmsFtpServer();
    		//跳转到指定的文件目录
    		if(fileDir!=null){
    			if(fileDir.indexOf("/")!=-1){
    				int index = 0;
    				while((index=fileDir.indexOf("/")) != -1){
        				ftpClient.changeWorkingDirectory(fileDir.substring(0,index));
        				fileDir = fileDir.substring(index+1,fileDir.length());
    				}
    				if(!fileDir.equals("")){
    					ftpClient.changeWorkingDirectory(fileDir);
    				}
    			}else{
    				ftpClient.changeWorkingDirectory(fileDir);
    			}
    		}
            //设置传输二进制文件
            setFileType(FTP.BINARY_FILE_TYPE);
            //获得服务器文件
            returnValue = ftpClient.deleteFile(gbkToIso8859(fileName));
            //关闭连接
            closeConnect();
            //输出操作结果信息
            if(returnValue){
            	log.info("<----------- INFO: delete " + fileDir + "/" + fileName + " at mss ftp:succeed! ----------->");
            }else{
            	log.error("<----------- ERR : delete " + fileDir + "/" + fileName + " at mss ftp:failed! ----------->");
            }
        }catch(Exception e){
            e.printStackTrace();
            returnValue = false;
            //输出操作结果信息
        	log.error("<----------- ERR : delete " + fileDir + "/" + fileName + " at mss ftp:failed! ----------->");
        }finally{
            try{
                if(ftpClient.isConnected()){
                    closeConnect();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return returnValue;
    }
    
    /**
     * 删除mssFtp的xml文件
     * @param fileName 要删除的xml文件的名称
     * @throws IOException
     */
    public boolean delXmlAtMpmsFtp(String fileName) throws IOException{
    	return delFile(XML_FROM_OA_DIR, fileName);
    } 

    /**
     * 删除mssFtp的附件
     * @param fileName 要删除的附件的名称
     * @throws IOException
     */
    public boolean delAnnexAtMpmsFtp(String fileName) throws IOException{
    	return delFile(ANNEX_FROM_OA_DIR, fileName);
    } 
    
    /**
     * 取得OA新上传到mssftp的文件
     * 即统计出当前目录下的所有文件，当大于0的时候，说明有新上传的文件，因为旧的已经导入过的文件已经备份到web服务器
     */
    public void listenXmlOnFtp(HttpServletRequest request){
    	
    	try{
    		connectToMpmsFtpServer();
    		
    		ftpClient.changeWorkingDirectory(XML_FROM_OA_DIR);
    		
    		String[] fileNames = ftpClient.listNames();
    		
    		if(fileNames!=null){
    			for(int i=0; i<fileNames.length; i++){
    				String realPath = BaseFrameworkApplication.FrameworkWebAppRootPath;
    				realPath = realPath.charAt(realPath.length()-1)=='/'||realPath.charAt(realPath.length()-1)=='\\'?realPath:realPath+"/";
    				//若本地的临时目录不存在，则新建一个临时目录
    				File tempPath = new File(realPath + "/mss/jsp/download/temp/");
    				if(!tempPath.exists()){
    					tempPath.mkdir();
    				}
    				downloadXmllFileByName(fileNames[i], realPath + "/mss/jsp/download/temp/");
//    				MpmsXmlOperation.getInstance(request).parXmlFile(new File(realPath + "/mss/jsp/download/temp/"));
    			}
    		}
    		
    		closeConnect();
    		
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
//  public static void main(String[] args) throws IOException, InterruptedException{
//	TestThread testThread = new TestThread();
//	for(int i = 0 ; i <5 ; i++){
//		Thread thread = new Thread(testThread,"testThread-"+i);
//		thread.start();
//	}
//	  new MpmsFtpOperation().downloadAnnexByName("预算查询修改.doc", "E:/预算查询修改.doc");
//}
}

//class TestThread implements Runnable {
//
//	public String postfix = "";
//		
//	public void run(){
//		try {
//			MpmsFtpOperation ftpOperation = new MpmsFtpOperation();
//			System.out.println("【"+Thread.currentThread().getName()+"】-ftpClient : "+ftpOperation.ftpClient);
//			
//			ftpOperation.uploadAnnexToMpmsFtp(new File("E:\\testtest.txt"), "texttest"+ftpOperation.ftpClient+".txt");
//			ftpOperation.uploadAnnexToMpmsFtp(new File("E:\\testtest.xml"), "texttest"+ftpOperation.ftpClient+".xml");
//			System.out.println("upload done!");
//			System.out.println(" ");
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
//	}
//	
////	public void start(){
////		
////		synchronized(this){
////			postfix = (new CommonOperation()).createRandomNC(2);
////			this.yield();
////		}
////	
////		this.run();
////	}
//}
