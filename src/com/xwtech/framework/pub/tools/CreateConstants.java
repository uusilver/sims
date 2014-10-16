package com.xwtech.framework.pub.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.xwtech.framework.pub.po.FrameFieldCheck;

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
public class CreateConstants
{
  protected static final Logger logger = Logger.getLogger(CreateConstants.class);

  //表的名称（手工写入）
  private String[] table_name ={};

  //存放路径即包名（手工写入）
  private String path = "com/xwtech/mpms/pub/constants";

  //工程源码路径（需要修改）
  private String projectPath = "F:/eclipse/workspace/mpms/src";

  //常量类文件名（如有需要，可以修改）





  private String fileName = "MpmsConstants";

  //常量定义
  private String prefix = "public static final";

  public CreateConstants()
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
   * 根据选择的表名读取常量定义表中的数据
   * @param table_name String[]
   * @throws SQLException
   * @return Connection
   */
  public ArrayList getConstantsValue(String[] table_name) throws SQLException
  {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    ArrayList result = new ArrayList();
    int table_number = table_name.length;
    StringBuffer hql = new StringBuffer();
    hql = hql.append("select * from mpms_frame_field_check");
    hql = hql.append(" where ");
    if(table_number == 1)
    {
      hql = hql.append("table_name in (?)");
    }
    else if(table_number > 1)
    {
      hql.append("table_name in (");
      for(int i = 0; i < table_number - 1; i++)
      {
        hql = hql.append("?,");
      }
      hql = hql.append("?)");
    }
    else
      hql.append(" 1=1 ");
    hql = hql.append(" and check_state = 'A'");
    hql = hql.append(" order by table_name,field_name,constant_name");
    try
    {
      con = this.getConnection();
      ps = con.prepareStatement(hql.toString());
      for(int i = 0; i < table_number; i++)
      {
        ps.setString(i + 1, table_name[i]);
      }
      rs = ps.executeQuery();
      while(rs.next())
      {
        FrameFieldCheck frameFieldCheck = new FrameFieldCheck();
        frameFieldCheck.setConstantName(rs.getString("constant_name"));
        frameFieldCheck.setCheckValue(rs.getString("check_value"));
        frameFieldCheck.setCheckDesc(rs.getString("check_desc"));
        if(rs.getString("constant_name").indexOf(".")<0)
        result.add(frameFieldCheck);
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
        logger.info("创建文件"+fileName+".java成功！");
      }
      catch(IOException e)
      {
        logger.info("创建文件"+fileName+".java失败！");
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
      lResult = this.getConstantsValue(table_name);
    }
    catch(SQLException ex1)
    {
      logger.info("从数据库中取数据时出错！");
    }
    if(lResult == null || lResult.size() == 0)
    {
      logger.info("没有此表名，请检查！");
    }
    else
    {
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
        FrameFieldCheck frameFieldCheck = (FrameFieldCheck)lResult.get(i);
        if(isInt(frameFieldCheck.getCheckValue()))
        {
          sb = sb.append("  " + prefix + " int "
               + frameFieldCheck.getConstantName() + " = "
               + frameFieldCheck.getCheckValue() + ";  //"
               + frameFieldCheck.getCheckDesc() + "\r\n");
        }
        else
        {
          sb = sb.append("  " + prefix + " String "
               + frameFieldCheck.getConstantName() + " = \""
               + frameFieldCheck.getCheckValue() + "\";  //"
               + frameFieldCheck.getCheckDesc() + "\r\n");
        }
      }
      sb = sb.append("}\r\n");
      try
      {
        FileOutputStream fileOut = new FileOutputStream (file);
        OutputStreamWriter out = new OutputStreamWriter(fileOut,"UTF-8");
        out.write(sb.toString());
        out.close();
        logger.info("写入文件Constants.java完成！");
      }
      catch(IOException ex)
      {
        logger.info("创建文件流失败！");
      }
    }
  }

  public static void main(String[] args)
  {
    CreateConstants createConstants = new CreateConstants();
    createConstants.writeConstantsFile();
  }

}
