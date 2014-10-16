package com.xwtech.mss.ftp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * <p>Title:OA从物资系统(mpms)ftp下载附件，以及上传文件的操作 </p>
 * 
 * @author gu_daping
 */

public class FtpOperation {

	//mpmsftp用户名
    private static String username = "sgs";
    //mpmsftp密码
    private static String password = "sgs";
    //mpmsftp地址
    private static String ip = "10.33.32.87";
//  private static String ip = "192.168.16.83";

    //从Mpms上传附件到Mpms的目录
    private static String ANNEX_FROM_MPMS_DIR = "xwsx/annex_from_mpms";
    
    //OA上传xml文件到MpmsFtp的目录
    private static String XML_FROM_OA_DIR = "xwsx/xml_from_oa";

    //OA上传附件到MpmsFtp的目录
    private static String ANNEX_FROM_OA_DIR = "xwsx/annex_from_oa";
    
    //ftp客户端
    public FTPClient ftpClient = null;

    /**
     * 构造函数
     * @throws IOException
     */
    public FtpOperation() throws IOException{
    	try{
    		ftpClient = new FTPClient();
	    	connectServer();
	    	//判断指定路径是否存在，不存在就新建目录
	    	checkPathExist("xwsx");
	    	checkPathExist(ANNEX_FROM_MPMS_DIR);
	    	checkPathExist(XML_FROM_OA_DIR);
	    	checkPathExist(ANNEX_FROM_OA_DIR);
	    	closeConnect();
    	}catch(Exception ex){
    		ex.printStackTrace();
    		System.out.println("FtpOperation initiate err");
    		if(ftpClient!=null && ftpClient.isConnected()){
    			closeConnect();
    		}
    	}
    }
    
    /**
     * 连接到ftp服务器
     */
    private void connectServer() {
        if (!ftpClient.isConnected()) {
            int reply;
            try {
//                ftpClient=new FTPClient();
                ftpClient.connect(ip);
                
                ftpClient.login(username, password);
                reply = ftpClient.getReplyCode();

                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftpClient.disconnect();
                    System.err.println("FTP server refused connection.");
                }
                //每次新建连接时，将工作目录指向根目录
                if(ftpClient!=null && ftpClient.isConnected()){
            		ftpClient.changeWorkingDirectory("/");
                }
            } catch (Exception e) {
                System.err.println("登录ftp服务器【"+ip+"】失败");
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭连接
     */
    private void closeConnect(){
        try{
            if(ftpClient!=null){
                ftpClient.logout();
                ftpClient.disconnect();
            }
        }catch(Exception e){
            e.printStackTrace();
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
     * 转码[GBK ->  ISO-8859-1]
     * 不同的平台需要不同的转码
     * @param obj
     * @return
     */
    private static String gbkToIso8859(Object obj){
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
    private static String iso8859ToGbk(Object obj){
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
     * 从ftp下载指定名称的文件到本地
     * @param String remoteFileName --服务器上的文件名(只需要文件名，比如"req_0823.doc")
     * @param String localFileName--本地文件名（包括完整的物理路径和文件名，比如"F:/ftpfile/req_0823.doc"，文件名可以自己定，可以不和服务器上的名字一致）
     */
    public boolean downloadAnnexByName(String remoteFileName,String localFileName) throws IOException{
        boolean returnValue = false;
    	//下载文件
        BufferedOutputStream buffOut=null;
        try{
        	//连接ftp服务器
            connectServer();
        	//先判断本地文件夹是否存在
        	File localFile = new File(localFileName.substring(0,localFileName.lastIndexOf("/")));
        	if(!localFile.exists()){
        		System.out.println("<----------- ERR: local direction does not exist, download failed!----------->");
        		return false;
        	}else if(!checkFileExist(ANNEX_FROM_MPMS_DIR, remoteFileName)){
        		System.out.println("<----------- ERR: file named " + remoteFileName + " does not exist at " + ANNEX_FROM_MPMS_DIR + ", download failed!----------->");
        		return false;
        	}else{
        		//跳转到指定的文件目录
        		ftpClient.changeWorkingDirectory("xwsx");
        		ftpClient.changeWorkingDirectory("annex_from_mpms");
	            //设置传输二进制文件
	            setFileType(FTP.BINARY_FILE_TYPE);
	            //获得服务器文件
	            buffOut=new BufferedOutputStream(new FileOutputStream(localFileName));
	            returnValue = ftpClient.retrieveFile(gbkToIso8859(remoteFileName), buffOut);
	            //输出操作结果信息
	            if(returnValue){
	            	System.out.println("<----------- INFO: download " + remoteFileName + " from mpms ftp : succeed! ----------->");
	            }else{
	            	System.out.println("<----------- INFO:download " + remoteFileName + " from mpms ftp : failed! ----------->");
	            }
        	}
            //关闭连接
            closeConnect();
        }catch(Exception e){
            e.printStackTrace();
            returnValue = false;
            //输出操作结果信息
            System.out.println("<----------- INFO:download " + remoteFileName + " from mpms ftp : failed! ----------->");
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
        return returnValue;
    }

    /**
     * 上传文件ftp服务器
     * @param uploadFile 要上传的文件
     * @param filePath 上传文件到ftp的目录
     * @param fileName 上传的文件的名称
     * @throws IOException
     */
    private boolean uploadFile(File uploadFile,String filePath, String fileName) throws IOException{
    	boolean returnValue = false;
    	//上传文件
        BufferedInputStream buffIn=null;
        try{
        	if(!uploadFile.exists()){
        		System.out.println("<----------- ERR: " + fileName + " not exist, upload failed! ----------->");
        		return false;
        	}else{
	        	//建立连接
	        	connectServer();
	            //设置传输二进制文件
	            setFileType(FTP.BINARY_FILE_TYPE);
	            //获得文件
	            buffIn=new BufferedInputStream(new FileInputStream(uploadFile));
	            //上传文件到ftp
	            if(checkFileExist(filePath, fileName)){
            		ftpClient.deleteFile(filePath + "/" + fileName);
            	}
            	returnValue = ftpClient.storeFile(gbkToIso8859(filePath + "/" + fileName), buffIn);
	            //输出操作结果信息
	            if(returnValue){
	            	System.out.println("<----------- INFO: upload " + fileName + " to mpms ftp : succeed! ----------->");
	            }else{
	            	System.out.println("<----------- ERR: upload " + fileName + " to mpms ftp : failed! ----------->");
	            }
	            //关闭连接
	            closeConnect();
        	}
        }catch(Exception e){
            e.printStackTrace();
            returnValue = false;
            System.out.println("<----------- ERR: upload " + fileName + " to mpms ftp : failed! ----------->");
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
     * 查找指定目录下面指定名称的文件是否存在
     * @param String filePath 要查找的目录
     * @param String fileName 要查找的文件名称
     * @return boolean:存在:true，不存在:false
     * @throws IOException
     */
    private boolean checkFileExist(String filePath, String fileName) throws IOException{
    	boolean existFlag = false;
		//跳转到指定的文件目录
		if(filePath!=null){
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
     * 上传xml文件到mpms服务器
     * @param localXmlFile--要上传的文件
     * @param newFileName--上传文件的文件名
     */
    public boolean uploadXMLFile(File localXmlFile, String fileName) throws IOException{
        return uploadFile(localXmlFile, XML_FROM_OA_DIR, fileName);
    }

    /**
     * 上传xml文件到mpms服务器
     * @param localXmlFilePath--要上传的文件的地址
     * @param newFileName--上传文件的文件名
     */
    public boolean uploadXMLFile(String localXmlFilePath, String fileName) throws IOException{
    	return uploadFile(new File(localXmlFilePath), XML_FROM_OA_DIR, fileName);
    }

    /**
     * 上传附件到mpms服务器
     * @param localAnnex--要上传的文件
     * @param newFileName--上传文件的文件名
     */
    public boolean uploadAnnex(File localAnnex, String fileName) throws IOException{
        return uploadFile(localAnnex, ANNEX_FROM_OA_DIR, fileName);
    }

    /**
     * 上传附件到mpms服务器
     * @param localAnnexPath--要上传的文件的地址
     * @param newFileName--上传文件的文件名
     */
    public boolean uploadAnnex(String localAnnexPath, String fileName) throws IOException{
        return uploadFile(new File(localAnnexPath), ANNEX_FROM_OA_DIR, fileName);
    }
    
//    public static void main(String[] args) throws IOException, InterruptedException{
//    	TestThread testThread = new TestThread();
//    	for(int i = 0 ; i <5 ; i++){
//    		Thread thread = new Thread(testThread,"testThread-"+i);
//    		thread.start();
//    	}
//    	new FtpOperation().downloadAnnexByName("1200637073546X9EzjA3v.htm", "E:/1200637073546X9EzjA3v.htm");
//	}
//    public static void main(String[] args) throws IOException, InterruptedException{
//    	File uploadFile = new File("E:/dms_data/dms_statis.sql");
//    	FtpOperation ftpOperation = new FtpOperation(); 
//    	ftpOperation.uploadFile(uploadFile, "xwsx/xml_from_oa", "dms_statis.sql");
//    	}
}
//class TestThread implements Runnable {
//	
//	public String postfix = "";
//	
//	public void run(){
//		try {
//			FtpOperation ftpOperation = new FtpOperation();
//			System.out.println("【"+Thread.currentThread().getName()+"】-ftpClient : "+ftpOperation.ftpClient);
//			
//			ftpOperation.uploadAnnex("E:\\testtest.txt", "texttest"+ftpOperation.ftpClient+".txt");
//			ftpOperation.uploadXMLFile("E:\\testtest.xml", "texttest"+ftpOperation.ftpClient+".xml");
//			System.out.println("upload done!");
//			System.out.println(" ");
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
//	}
//
//	
//public void start(){
//	class TestThread implements Runnable {
//			
//		public void run(){
//			try {
//				FtpOperation ftpOperation = new FtpOperation();
//				System.out.println("【"+Thread.currentThread().getName()+"】-ftpClient : "+ftpOperation.ftpClient);
//		
//		ftpOperation.downloadAnnexByName("texttestorg.apache.commons.net.ftp.FTPClient@e86da0.xml", "E:/texttest"+ftpOperation.ftpClient+".xml");
//		ftpOperation.uploadXMLFile("E:\\texttest.xml", "texttest"+ftpOperation.ftpClient+".xml");
//		System.out.println("upload done!");
//		System.out.println(" ");
//			} catch (IOException e) {
//				e.printStackTrace();
//			} 
//		}
//	}
//}
