/**
 * 发送邮件示例
 */

package com.xwtech.mss.pub.tools;

import java.io.File;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.xwtech.framework.pub.web.FrameworkApplication;

public class MailOperation
{

	private static final Log log = LogFactory.getLog(MailOperation.class);

	//发件地址
	private String mailFrom = FrameworkApplication.frameworkProperties.getMailFrom();

	//private String mailFrom = "gaoran@nj.js.chinamobile.com";

	//发件地址邮件服务器
	private String mailFromHost = FrameworkApplication.frameworkProperties.getMailHost();

	//private String mailFromHost = "najmail.js.cmcc";

	//发件地址用户名
	private String userId = FrameworkApplication.frameworkProperties.getUserName();

	//private String userId = "gaoran";

	//发件地址密码
	private String password = FrameworkApplication.frameworkProperties.getPassword();

	//private String password = "gaoran12";

	//邮件发送开关
	private boolean mailSendSwitch = FrameworkApplication.frameworkProperties.getMailSendSwitch();

	//收件地址
	private String mailTo;

	public String getMailTo()
	{
		return mailTo;
	}

	public void setMailTo(String mailTo)
	{
		this.mailTo = mailTo;
	}

	public MailOperation()
	{
	}

	public MailOperation(String mailTo)
	{
		this.mailTo = mailTo;
	}

	private Transport transport;

	/**
	 * 判断是否能够连接到邮件服务器
	 * 当邮件发送开关开启的时候，进行邮箱连接测试，反之，直接返回
	 */
	public boolean connetToMailHost() throws Exception
	{
		if (mailSendSwitch)
		{
			boolean canFlag = false;
			try
			{
				connectToServer();
				if (transport.isConnected())
				{
					log.info("连接邮箱服务器。。。。。。。。。。。成功！");
					canFlag = true;
					transport.close();
				}
				else
				{
					log.info("连接邮箱服务器。。。。。。。。。。。失败！");
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return canFlag;
			}
			return canFlag;
		}
		else
		{
			log.info("邮件发送开关关闭，不做连接测试！");
			return true;
		}
	}

	/**
	 * 连接服务器
	 * @param fileTitle 邮件主题
	 * @param fileContent 邮件内容
	 * @param filePath 附件地址
	 */
	private boolean connectToServer()
	{
		boolean flag = false;
		Properties props = new Properties();
		props.put("mail.smtp.host", mailFromHost); //存储发送邮件服务器的信息 
		props.put("mail.smtp.auth", "true"); //发送邮件需通过服务器验证 

		Session s = Session.getInstance(props, null);

		//根据属性新建一个邮件会话，null参数是一种Authenticator(验证程序) 对象 
		s.setDebug(true); //设置调试标志,要查看经过邮件服务器邮件命令，可以用该方法

		try
		{
			transport = s.getTransport("smtp");
			log.info("mailFromHost is " + mailFromHost);
			log.info("userId is " + userId);
			log.info("password is " + password);
			transport.connect(mailFromHost, userId, password); //登陆邮箱服务器的用户名和密码；
			if (transport.isConnected())
			{
				flag = true;
			}
			else
			{
				log.info("连接邮箱服务器失败！");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 断开连接
	 * @param fileTitle 邮件主题
	 * @param fileContent 邮件内容
	 * @param filePath 附件地址
	 */
	private void disconnectServer()
	{

		try
		{
			transport.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 发送邮件
	 * 当邮件发送开关开启的时候，发送邮件，反之，直接返回
	 * @param fileTitle 邮件主题
	 * @param fileContent 邮件内容
	 * @param filePath 附件地址
	 */
	public boolean send(String fileTitle, String fileContent, String[] filePath)
	{
		if (mailSendSwitch)
		{
			boolean flag = false;

			Properties props = new Properties();
			log.info("mailFromHost is : " + mailFromHost);
			props.put("mail.smtp.host", mailFromHost); //存储发送邮件服务器的信息 
			props.put("mail.smtp.auth", "true"); //发送邮件需通过服务器验证 

			Authenticator auth = new SMTPAuthenticator(this.userId, this.password);

			Session s = Session.getInstance(props, auth);

			//根据属性新建一个邮件会话，null参数是一种Authenticator(验证程序) 对象 
			s.setDebug(true); //设置调试标志,要查看经过邮件服务器邮件命令，可以用该方法
			Message message = new MimeMessage(s); //由邮件会话新建一个消息对象 

			//用于保存附件的集合
			Vector file = new Vector();
			//附件设值
			if (filePath != null && filePath.length > 0)
			{
				for (int i = 0; i < filePath.length; i++)
				{
					file.addElement(new File(filePath[i]));
				}
			}

			try
			{
				Address from = new InternetAddress(mailFrom); //发件人的邮件地址 
				message.setFrom(from); //设置发件人 
				//设置群发地址
				InternetAddress[] address =
				{ new InternetAddress(this.mailTo) };
				//	            for(int i = 0 ; i < address.length; i ++){
				//	            	log.info("address"+i+" is :"+address[i]);
				//	            }
				message.setRecipients(Message.RecipientType.TO, address); //设置收件人,并设置其接收类型为TO,还有3种预定义类型如下： 
				message.setSubject(fileTitle); //设置主题

				Multipart mp = new MimeMultipart();
				MimeBodyPart mbp = new MimeBodyPart();
				String filename = "";

				mbp.setContent(fileContent, "text/html;charset=gb2312"); //设置信件内容   
				mp.addBodyPart(mbp);

				if (!file.isEmpty())
				{//有附件   
					Enumeration efile = file.elements();
					while (efile.hasMoreElements())
					{
						mbp = new MimeBodyPart();
						filename = efile.nextElement().toString(); //选择出每一个附件名
						//	                    filename = filename.substring(0,filename.lastIndexOf("\\")+1)+"订单"+filename.substring(filename.lastIndexOf("\\")+1);
						FileDataSource fds = new FileDataSource(filename); //得到数据源
						mbp.setDataHandler(new DataHandler(fds)); //得到附件本身并至入BodyPart   
						mbp.setFileName(fds.getName()); //得到文件名同样至入BodyPart   
						mp.addBodyPart(mbp);
					}
					file.removeAllElements();
				}
				message.setContent(mp); //Multipart加入到信件   
				message.setSentDate(new Date()); //设置信件头的发送日期  
				message.saveChanges(); //存储邮件信息

				connectToServer();
				transport.sendMessage(message, message.getAllRecipients()); //发送邮件,其中第二个参数是所有已设好的收件人地址 
				disconnectServer();
				flag = true;
			}
			catch (Exception e)
			{
				flag = false;
				e.printStackTrace();
			}
			if (flag)
			{
				log.info("邮件发送。。。。。。。。。。。成功！");
			}
			else
			{
				log.info("邮件发送。。。。。。。。。。。失败！");
			}
			return flag;
		}
		else
		{
			log.info("邮件发送开关关闭，不发送邮件！");
			return true;
		}
	}

	//	public static void main(String args[]) {
	//		MailOperation frist = new MailOperation(); 
	//		frist.connectToServer();
	//	}

	private class SMTPAuthenticator extends Authenticator
	{

		String username;

		String password;

		public SMTPAuthenticator(String userName, String password)
		{
			//	    	super();
			this.username = userName;
			this.password = password;
		}

		public PasswordAuthentication getPasswordAuthentication()
		{
			return new PasswordAuthentication(username, password);
		}
	}

	/**
	 * add by jzy 09-03-02
	 * @param smtpHost
	 * @param userName
	 * @param password
	 * @param to
	 * @param form
	 * @param subject
	 * @param text
	 * @param strFile
	 * @param fileName
	 * @throws Exception
	 */
	public static boolean sendEmail(String smtpHost, String userName, String password, String to, String form, String subject, String text,
			String strFile) throws Exception
	{
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", "true");
		senderImpl.setJavaMailProperties(properties);

		senderImpl.setHost(smtpHost);
		senderImpl.setUsername(userName);
		senderImpl.setPassword(password);

		MimeMessage mailMessage = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true,"GB2312");

		messageHelper.setTo(to);
		messageHelper.setFrom(form);
		messageHelper.setSubject(subject);
		
		
		//text = javax.mail.internet.MimeUtility.encodeText(text,"GBK","utf-8");   
		messageHelper.setText("<html><head></head><body><h1>" + text + "</h1></body></html>", true);

		FileSystemResource file = new FileSystemResource(new File(strFile));
		messageHelper.addAttachment(file.getFilename(), file);

		senderImpl.send(mailMessage);
		log.info("完成从" + form + " 向 " + to + " 发送的邮件，邮件主题:" + subject + " 附件:" + strFile);
		
		return true;
	}

	public static void main(String args[]) throws Exception
	{
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

		//设定 Mail Server
		//        senderImpl.setHost("mail.xwtec.cn");
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", "true");
		senderImpl.setJavaMailProperties(properties);

		// senderImpl.setHost("smtp.e172.com");
		senderImpl.setHost("mail.xwtec.cn");
		//SMTP验证时，需要用户名和密码
		senderImpl.setUsername("jiangzy");
		senderImpl.setPassword("164711825");
		//senderImpl.
		//authentication

		MimeMessage mailMessage = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true);

		messageHelper.setTo("13951776391@139.com");
		messageHelper.setFrom("jiangzy@mail.xwtec.cn");
		messageHelper.setSubject("Test");
		messageHelper.setText("<html><head></head><body><h1>Hello! Spring!" + "</h1></body></html>", true);

		FileSystemResource file = new FileSystemResource(new File("E:\\spms_ftp\\local\\web_forum\\rep\\WEB_FORUM_20090227.xls"));
		messageHelper.addAttachment("WEB_FORUM_20090227.xls", file);

		senderImpl.send(mailMessage);

		System.out.println("OK");

	}
}
