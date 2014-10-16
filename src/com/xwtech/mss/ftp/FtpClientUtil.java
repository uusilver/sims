package com.xwtech.mss.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.Arrays;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.xwtech.mss.pub.constants.SpmsConstants;
//import com.xwtech.mss.pub.dao.SpmsFileOperLogDAO;
//import com.xwtech.mss.pub.po.SpmsFileOperLog;
import com.xwtech.mss.pub.tools.CommonOperation;
import com.xwtech.mss.pub.tools.analyzeUtil;

/**
 * FTP文件服务器上传,下载,删除操作
 * @author heguanhua
 *
 */
public class FtpClientUtil
{
	private final static Logger _logger = Logger.getLogger(FtpClientUtil.class);

	private static FTPClient ftp;

	/**  
	 * 基于apache组织的commonNet开源组件实现ftp客户端<br>  
	 * 本程序运行依赖于一个config成员变量属性,其是一个属性(Properties)对象<br>  
	 * 系统要求这个config对象,必需有如下属性key:<br>  
	 * server(ftp服务器ip地址或域名)<br>  
	 * username(登录用户名)<br>  
	 * password(登录密码)<br>  
	 * 如下属性key是可选的:<br>  
	 * systemKey(ftp服务器的操作系统key,如window)<br>  
	 * serverLanguageCode(ftp服务器使用的语言,如zh)<br>  
	 * <br>  
	 * 本程序是线程安全,在每一个上传时都将创建ftp连接,上传结束后再关闭ftp连接<br>  
	 *  
	 * 例子:<br>  
	 * InputStream localIn = new FileInputStream("c:\\计算机.txt");<br>  
	 *  
	 * java.util.Properties config = new Properties();<br>  
	 * config.setProperty("server", "192.168.5.120");<br>  
	 * config.setProperty("username", "upload");<br>  
	 * config.setProperty("password", "upload");<br>  
	 *  
	 * FtpClientCommonNetImpl client = new FtpClientCommonNetImpl();<br>  
	 * client.setConfig(config);<br>  
	 * client.upload(localIn, "/aaa/计算机.txt", true);<br>  
	 *  
	 *  
	 * @author zhangdb  
	 *  
	 * 
	 * 连接到ftp服务器  
	 *  
	 * @param server  
	 * @param userName  
	 * @param password  
	 * @return  
	 * @throws FtpException  
	 */
//	public FTPClient connectFtpServer(String server, String userName, String password) throws Exception
//	{
//		//创建ftp客户端对象   
//		FTPClient ftp = new FTPClient();
//
//		//连接ftp服务器   
//		ftp.connect(server);
//		//登录   
//		ftp.login(userName, password);
//		//	_logger.info("@@@@@@@@@@@@@@@@@@@@@@"+server+" "+userName+" "+password);
//		//返回值   
//		int reply = ftp.getReplyCode();
//		if ((!FTPReply.isPositiveCompletion(reply)))
//		{
//			ftp.disconnect();
//			throw new Exception("登录ftp服务器失败,请检查server[" + server + "]、username[" + userName + "]、password[" + password + "]是否正确!");
//		}
//
//		return ftp;
//	}
//
//	/**  
//	 * 关闭连接  
//	 *  
//	 * @param ftp  
//	 */
//	public void disconnectFtpServer(FTPClient ftp) throws Exception
//	{
//		if (null != ftp && ftp.isConnected())
//		{
//			try
//			{
//				ftp.disconnect();
//			}
//			catch (IOException ioe)
//			{
//				ioe.printStackTrace();
//				throw new Exception();
//			}
//		}
//	}
//
//	/**  
//	 * 上传  
//	 */
//	public void upload(InputStream localIn, String remoteFilePath, String server, String userName, String password) throws Exception
//	{
//
//		FTPClient ftp = this.connectFtpServer(server, userName, password);
//
//		try
//		{
//			boolean result = ftp.storeFile(this.enCodingRemoteFilePath(remoteFilePath), localIn);
//			if (!result)
//			{
//				throw new Exception("文件上传失败!");
//			}
//		}
//		catch (Exception ex)
//		{
//			throw new Exception(ex);
//		}
//		finally
//		{
//			this.disconnectFtpServer(ftp);
//		}
//	}
//
//	/**  
//	 * 上传结束以后关闭输入流  
//	 *  
//	 * @param localIn  
//	 * @param remoteFilePath  
//	 * @param afterUploadCloseInputStream  
//	 * @throws FtpException  
//	 */
//	public void upload(InputStream localIn, String remoteFilePath, boolean afterUploadCloseInputStream, String server, String userName,
//			String password) throws Exception
//	{
//		try
//		{
//			//  上传   
//			this.upload(localIn, remoteFilePath, server, userName, password);
//		}
//		finally
//		{
//			if (afterUploadCloseInputStream)
//			{
//				if (localIn != null)
//				{
//					try
//					{
//						localIn.close();
//					}
//					catch (Exception ex)
//					{
//						throw new Exception(ex);
//					}
//				}
//			}
//		}
//	}
//
//	/**
//	 * 下载
//	 * @param ftp
//	 * @param remotePath
//	 * @param fileName
//	 * @param localPath
//	 * @return
//	 */
//	public boolean download(FTPClient ftp, String remotePath, String fileName, String localPath)
//	{
//		boolean success = false;
//
//		try
//		{
//			int reply;
//			reply = ftp.getReplyCode();
//			if (!FTPReply.isPositiveCompletion(reply))
//			{
//				ftp.disconnect();
//				return success;
//			}
//			/**
//			 ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录
//			 FTPFile[] fs = ftp.listFiles();
//			 for(FTPFile ff:fs){
//			 if(ff.getName().equals(fileName)){
//			 File localFile = new File(localPath+"/"+ff.getName());
//			 
//			 OutputStream is = new FileOutputStream(localFile); 
//			 ftp.retrieveFile(ff.getName(), is);
//			 is.close();
//			 }
//			 }
//			 
//			 */
//			fileName = gbkToIso8859((Object) fileName);
//			File localFile = new File(localPath);
//
//			OutputStream out = new FileOutputStream(localFile);
//			ftp.retrieveFile(fileName, out);
//			out.close();
//			ftp.logout();
//			success = true;
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//		finally
//		{
//			if (ftp.isConnected())
//			{
//				try
//				{
//					ftp.disconnect();
//				}
//				catch (IOException ioe)
//				{
//					ioe.printStackTrace();
//				}
//			}
//		}
//		return success;
//	}
//
//	/**
//	 * 删除文件
//	 * @param fu
//	 * @param url
//	 * @throws Exception
//	 */
//	public void delete_file(FTPClient ftp, String url) throws Exception
//	{
//		ftp.deleteFile(gbkToIso8859("/root/" + url));
//		int status = ftp.getReplyCode();
//		_logger.info("ftp delete info:" + ftp.getReplyString());
//		if (status == 250)
//		{
//			_logger.info("  成功删除FTP服务器中文件");
//
//		}
//	}
//
//	/**  
//	 * 远程文件路径编码(上传到ftp上的文件路径)  
//	 *  
//	 * @param remoteFilePath  
//	 * @return  
//	 * @throws UnsupportedEncodingException  
//	 */
//	protected String enCodingRemoteFilePath(String remoteFilePath) throws UnsupportedEncodingException
//	{
//		return gbkToIso8859(remoteFilePath);
//	}
//
//	/**
//	 *  文件采集
//	 * add by jzy 09-02-25
//	 * @param ip 
//	 * @param userName
//	 * @param password
//	 * @param pattern
//	 * @param tmpPath
//	 * @param errPath
//	 * @throws Exception
//	 */
//	public static void getLogFile(String type, String ip, String userName, String password, String pattern, String tmpPath, String errPath,
//			SpmsFileOperLogDAO spmsFileOperLogDAO) throws Exception
//	{
//		FtpClientUtil fu = null;
//		FTPClient ftp = null;
//		FTPFile[] files = null;
//		String remoteDir = null;
//
//		if (type.equals(SpmsConstants.WEB_APACHE))
//		{
//			remoteDir = SpmsConstants.WEB_APACHE_FTP_DIR;
//		}
//		else if (type.equals(SpmsConstants.WAP_APACHE))
//		{
//			remoteDir = SpmsConstants.WAP_APACHE_FTP_DIR;
//		}
//		else if (type.equals(SpmsConstants.WEB_FORUM)) //社区访问
//		{
//			remoteDir = SpmsConstants.WEB_FORUM_FTP_DIR;
//		}
//		else if (type.equals(SpmsConstants.WEB_PLATE)) //板块访问
//		{
//			remoteDir = SpmsConstants.WEB_PLATE_FTP_DIR;
//		}
//		else if (type.equals(SpmsConstants.WEB_TOPIC)) //主题访问
//		{
//			remoteDir = SpmsConstants.WEB_TOPIC_FTP_DIR;
//		}
//		else if (type.equals(SpmsConstants.WEB_TOPIC_PLATE_INFO)) //主题板块信息
//		{
//			remoteDir = SpmsConstants.WEB_TOPIC_PLATE_INFO_FTP_DIR;
//		}
//		else if (type.equals(SpmsConstants.WEB_USER_ACC)) ///web用户访问（行为）
//		{
//			remoteDir = SpmsConstants.WEB_USER_ACC_FTP_DIR;
//		}
//		else if (type.equals(SpmsConstants.WEB_USER_POINT)) ///web用户积分
//		{
//			remoteDir = SpmsConstants.WEB_USER_POINT_FTP_DIR;
//		}
//		else if (type.equals(SpmsConstants.WAP_LANMU))
//		{
//			//wap 栏目
//			remoteDir = SpmsConstants.WAP_LANMU_FTP_DIR;
//		}
//		else if (type.equals(SpmsConstants.WAP_OTHER))
//		{
//			//wap 其它统计数据
//			remoteDir = SpmsConstants.WAP_OTHER_FTP_DIR;
//		}
//
//		try
//		{
//			//获得ftp工具对象
//			fu = new FtpClientUtil();
//			//链接ftpServer
//			ftp = fu.connectFtpServer(ip, userName, password);
//
//			if (null != remoteDir)
//			{
//				if (!ftp.changeWorkingDirectory(remoteDir))
//				{
//					_logger.info("FTP 用户 " + userName + " 不能改变工作目录至" + remoteDir);
//					return;
//				}
//			}
//
//			//获得文件列表
//			files = ftp.listFiles();
//			//	_logger.info("###########################" +userName+" = "+files.length);
//
//			if (files.length == 0)
//			{
//				_logger.info("FTP 用户 " + userName + " 目录下没有采集文件");
//				return;
//			}
//			//****************************************************必须2开始的 后面还要测
//			for (int i = 0; i < files.length; i++)
//			{
//				//汉字转码
//				String name = iso8859ToGbk(files[i].getName());
//
//				//校验文件名
//				if (analyzeUtil.matches(name, pattern))
//				{
//					OutputStream out = null;
//					try
//					{
//						//文件名正确
//						_logger.info("fileName correct : " + name);
//
//						//本地文件
//						File localFile = new File(tmpPath + name);
//						//本地文件输入流
//						out = new FileOutputStream(localFile);
//						//下载
//						ftp.retrieveFile(name, out);
//
//						_logger.info("upload correct file to " + tmpPath + name);
//
//						//删除ftp上文件
//						_logger.info("del file " + files[i].getName() + " on ftp result:" + ftp.deleteFile(files[i].getName()));
//
//						//记录日志
//						spmsFileOperLogDAO.save(new SpmsFileOperLog(tmpPath, name, type, SpmsConstants.GET_COR_FILE, CommonOperation
//								.getSystemTime("dateTime"), SpmsConstants.GET_COR_FILE_REMARK));
//					}
//					catch (Exception e)
//					{
//						throw new Exception(e);
//					}
//					finally
//					{
//						//关闭流
//						if (null != out)
//						{
//							out.close();
//						}
//					}
//				}
//				else
//				{
//
//					//文件名错误 不做任何操作
//					/*_logger.info("fileName error : " + name);
//					 
//					 //本地文件
//					 File localFile = new File(errPath + name);
//					 //本地文件输入流
//					 OutputStream out = new FileOutputStream(localFile);
//					 //下载
//					 ftp.retrieveFile(name, out);
//					 //关闭流
//					 out.close();
//					 
//					 //删除ftp上文件
//					 ftp.deleteFile(files[i].getName());
//					 
//					 _logger.info("del file "+files[i].getName()+" on ftp result:"+ftp.deleteFile(files[i].getName()));
//					 
//					 //记录日志
//					 spmsFileOperLogDAO.save(new SpmsFileOperLog(tmpPath,name,type,SpmsConstants.GET_ERR_FILE,CommonOperation.getSystemTime("dateTime"),SpmsConstants.GET_ERR_FILE_REMARK));
//					 */
//				}
//
//			}
//
//		}
//		catch (ConnectException ce)
//		{
//			_logger.info("用户：" + userName + " 密码：" + password + " 登录ftp服务:" + ip + "失败!");
//			ce.printStackTrace();
//		}
//		catch (Exception e)
//		{
//			//System.out.println("##################");
//			e.printStackTrace();
//		}
//		finally
//		{
//			ftp.logout();
//			fu.disconnectFtpServer(ftp);
//		}
//		//关闭ftp链接
//		//fu.disconnectFtpServer(ftp);
//
//	}
//
//	/**
//
//	 *测试
//
//	 */
//	public static void main(String agrs[])
//	{
//		try
//		{
//			FtpClientUtil.getLogFile("4", "192.168.16.60", "spms", "spms", SpmsConstants.WEB_APACHE_FILE_NAME_REGULAR,
//					"E:\\spms_ftp\\local\\web_apache\\tmp\\", "E:\\spms_ftp\\local\\web_apache\\err\\", null);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//
//		/*FtpClientUtil fu = new FtpClientUtil();
//		 //  String url = Handup.getPropertie(Constants.PropertyKeys.FTP_SERVER_URL); 
//		 //  String username = Handup.getPropertie(Constants.PropertyKeys.FTP_USERNAME); 
//		 //  String password = Handup.getPropertie(Constants.PropertyKeys.FTP_PASSWORD); 
//
//		 try
//		 {
//		 ftp = fu.connectFtpServer("192.168.1.9", "root", "sinosoft");
//		 }
//		 catch (Exception e)
//		 {
//		 e.printStackTrace();
//		 }
//
//		 try
//		 {
//		 FTPFile[] files = null;
//		 FTPListParseEngine engine;
//		 ftp.changeWorkingDirectory("/");
//		 files = ftp.listFiles();
//
//		 _logger.info("Directory is " + ftp.printWorkingDirectory());
//
//		 _logger.info("文件名:" + files[0].getName());
//		 _logger.info("是否目录:" + files[0].isDirectory());
//		 _logger.info("文件长度:" + files[0].getSize());
//		 _logger.info("文件属性信息:" + files[0].getRawListing());
//		 _logger.info("***********************");
//
//		 //解决FTP文件路径中文编码问题
//		 String urls = gbkToIso8859("/root/博硕士论文/2008/10611/20208/测试上传_20080904525964/");
//
//		 ftp.changeWorkingDirectory(urls);
//		 files = ftp.listFiles();
//		 _logger.info("Directory is " + iso8859ToGbk(ftp.printWorkingDirectory()));
//
//		 for (int i = 0; i < files.length; i++)
//		 {
//		 String name = iso8859ToGbk(files[i].getName());
//		 _logger.info("文件名-" + i + ":" + name);
//		 _logger.info("是否目录-" + i + ":" + files[i].isDirectory());
//		 _logger.info("文件长度-" + i + ":" + files[i].getSize());
//		 _logger.info("文件创建时间信息-" + i + ":" + files[i].getTimestamp().getTime());
//		 if (name.equals("信息系统三部通讯录.xls"))
//		 {
//		 _logger.info("----------------OK-----------------");
//		 ftp.deleteFile(urls + files[i].getName());
//
//		 _logger
//		 .info("delete file:" + urls + files[i].getName() + "-----------------" + ftp.getReplyString() + "---"
//		 + ftp.getReplyCode());
//		 }
//		 if (name.equals("数据表初步设计.vsd"))
//		 {
//		 //源目录
//		 String fileName = urls + files[i].getName();
//		 //目标
//		 String localPath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 5.5\\webapps\\handup\\media\\博硕士论文\\2008\\10611\\20208\\测试上传_20080904525964";
//		 //  CreateFileUtil.createDir(localPath);
//		 localPath = localPath + "\\" + name;
//		 File localFile = new File(localPath);
//
//		 OutputStream out = new FileOutputStream(localFile);
//		 ftp.retrieveFile(fileName, out);
//
//		 _logger.info("code:" + ftp.getReplyString());
//		 out.close();
//		 ftp.logout();
//		 }
//
//		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//		 _logger.info(dateFormat.format(files[i].getTimestamp().getTime()));
//		 int idx = files[i].getRawListing().indexOf(" ");
//		 _logger.info(files[i].getRawListing().substring(0, idx--));
//		 }
//		 }
//		 catch (IOException e1)
//		 {
//		 e1.printStackTrace();
//		 }
//		 try
//		 {
//		 fu.disconnectFtpServer(ftp);
//		 }
//		 catch (Exception e)
//		 {
//		 e.printStackTrace();
//		 }*/
//	}
//
//	/**
//	 * 转码[GBK ->  ISO-8859-1]
//	 * 不同的平台需要不同的转码
//	 * @param obj
//	 * @return
//	 */
//	public static String gbkToIso8859(Object obj)
//	{
//		try
//		{
//			if (obj == null)
//				return "";
//			else
//				return new String(obj.toString().getBytes("GBK"), "iso-8859-1");
//		}
//		catch (Exception e)
//		{
//			return "";
//		}
//	}
//
//	/**
//	 * 转码[ISO-8859-1 ->  GBK]
//	 * 不同的平台需要不同的转码
//	 * @param obj
//	 * @return
//	 */
//	private static String iso8859ToGbk(Object obj)
//	{
//		try
//		{
//			if (obj == null)
//				return "";
//			else
//				return new String(obj.toString().getBytes("iso-8859-1"), "GBK");
//		}
//		catch (Exception e)
//		{
//			return "";
//		}
//	}
//
//	/**
//	 * 排序目录下文件（在目录下有多个文件时，按文件名时间顺序升序排序）
//	 * @param dirFile
//	 * @return
//	 */
//	public static File[] listSortedFiles(File dirFile)
//	{
//		assert dirFile.isDirectory();
//
//		File[] files = dirFile.listFiles();
//
//		FileWrapper[] fileWrappers = new FileWrapper[files.length];
//		for (int i = 0; i < files.length; i++)
//		{
//			fileWrappers[i] = new FileWrapper(files[i]);
//		}
//
//		Arrays.sort(fileWrappers);
//
//		File[] sortedFiles = new File[files.length];
//		for (int i = 0; i < files.length; i++)
//		{
//			sortedFiles[i] = fileWrappers[i].getFile();
//		}
//
//		return sortedFiles;
//	}
//}
//
//class FileWrapper implements Comparable
//{
//	/** File */
//	private File file;
//
//	public FileWrapper(File file)
//	{
//		this.file = file;
//	}
//
//	public int compareTo(Object obj)
//	{
//		assert obj instanceof FileWrapper;
//
//		FileWrapper castObj = (FileWrapper) obj;
//
//		if (this.file.getName().compareTo(castObj.getFile().getName()) > 0)
//		{
//			return 1;
//		}
//		else if (this.file.getName().compareTo(castObj.getFile().getName()) < 0)
//		{
//			return -1;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//
//	public File getFile()
//	{
//		return this.file;
//	}

}
