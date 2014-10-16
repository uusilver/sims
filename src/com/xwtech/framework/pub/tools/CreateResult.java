package com.xwtech.framework.pub.tools;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import com.xwtech.framework.pub.po.FrameResult;
import org.apache.log4j.Logger;

/**
 *
 * <p>Title: Framework </p>
 * <p>Description:Framework</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: xwtech.com</p>
 * @author yug
 * @version 1.0
 *
 * 生成常量类的文件
 */
public class CreateResult
{
  protected static final Logger logger = Logger.getLogger(CreateResult.class);
  
  //存放路径即包名
  private String path = "com/xwtech/mpms/pub/result";

  //工程源码路径（需要修改）
  private String projectPath = "F:/eclipse/workspace/mpms/src";

  //结果常量类文件名（如有需要，可以修改）
  private String fileName = "ResultConstants";

  //常量定义
  private String prefix = "public static final";

  public CreateResult()
  {
  }

  /**
   * 获得数据库的连接
   * @param
   * @throws
   * @return Connection
   */
  public Connection getConnection()
  {
    Connection connection = null;
    try
    {
      String url = "jdbc:oracle:thin:@192.168.4.23:1521:smnew";
      Class.forName("oracle.jdbc.driver.OracleDriver");
      connection = DriverManager.getConnection(url, "nj_cmpp", "njcmpp");
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return connection;
  }

  /**
   * 读取常量定义表中的数据
   * @param table_name String[]
   * @throws SQLException
   * @return Connection
   */
  public ArrayList getConstantsValue() throws SQLException
  {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    ArrayList result = new ArrayList();
    String sql = "select * from mpms_frame_result where result_state='A' order by result_code";
    try
    {
      con = this.getConnection();
      ps = con.prepareStatement(sql);
      rs = ps.executeQuery();
      while(rs.next())
      {
        FrameResult frameResult = new FrameResult();
        frameResult.setConstantName(rs.getString("CONSTANT_NAME"));
        frameResult.setResultCode(new Long(rs.getLong("RESULT_CODE")));
        frameResult.setResultInfo(rs.getString("RESULT_INFO"));
        result.add(frameResult);
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      if(rs != null)
      {
        rs.close();
      }
      if(ps != null)
      {
        ps.close();
      }
      if(con != null && !con.isClosed())
      {
        con.close();
      }

    }
    return result;
  }

  //创建java文件
  public void createFile(File file)
  {
    File parent = file.getParentFile();
    if(!parent.exists())
    {
      parent.mkdirs();
      logger.info("创建目录！");
    }
    if(!file.exists())
    {
      try
      {
        file.createNewFile();
        logger.info("创建文件" + fileName + ".java成功！");
      }
      catch(IOException e)
      {
        e.printStackTrace();
    	  logger.info("创建文件" + fileName + ".java失败！");
      }
    }
  }

  /**
   * 判断String的值是不是int型的
   * @param StringValue String
   * @throws
   * @return boolean
   */
  public boolean isInt(String StringValue)
  {
    boolean bResult = false;
    try
    {
      int intValue = Integer.parseInt(StringValue);
      bResult = true;
    }
    catch(Exception e)
    {
    	e.printStackTrace();
    	bResult = false;
    }
    return bResult;
  }

  /**
   * 根据查询出来的数据生成常量类文件
   * @param
   * @throws
   * @return
   */
  public void writeConstantsFile()
  {
    ArrayList lResult = null;
    try
    {
      lResult = this.getConstantsValue();
    }
    catch(SQLException ex1)
    {
    	ex1.printStackTrace();
    	logger.info("从数据库中取数据时出错！");
    }
    String filePathName = projectPath + "/" + path + "/" + fileName
                          + ".java";
    File file = new File(filePathName);
    this.createFile(file);
    StringBuffer sb = new StringBuffer();
    StringTokenizer st = new StringTokenizer(path, "/");
    int count = st.countTokens() - 2;
    String packageName = "";
    packageName = packageName + st.nextToken();
    for(int i = 0; i < count; i++)
    {
      packageName = packageName + "." + st.nextToken();
    }
    packageName = packageName + "." + st.nextToken();
    sb.append("package " + packageName + ";\r\n");
    sb = sb.append("\r\n");
    sb = sb.append("public class " + fileName);
    sb = sb.append("{\r\n");
    for(int i = 0; i < lResult.size(); i++)
    {
      FrameResult frameResult = (FrameResult)lResult.get(i);
      sb = sb.append("  " + prefix + " int "
           + frameResult.getConstantName() + " = "
           + frameResult.getResultCode() + ";  //"
           + frameResult.getResultInfo() + "\r\n");
    }
    sb = sb.append("}\r\n");
    try
    {
      FileOutputStream fileOut = new FileOutputStream(file);
      OutputStreamWriter out = new OutputStreamWriter(fileOut, "UTF-8");
      out.write(sb.toString());
      out.close();
      logger.info("写入文件ResultConstants.java完成！");
    }
    catch(IOException ex)
    {
    	ex.printStackTrace();
    	logger.info("创建文件流失败！");
    }
  }

  public static void main(String[] args)
  {
    CreateResult createResult = new CreateResult();
    createResult.writeConstantsFile();
  }

}
