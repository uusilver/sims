package com.xwtech.framework.pub.tools;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.ArrayList;

/**
 *
 * <p>Title: Framework </p>
 * <p>Description:Framework</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: xwtech.com</p>
 * @author yug
 * @version 1.0
 *
 * 根据po生成DAO的文件





 */
public class CreateDAO
{
  //存放路径即包名（手工写入）


  protected static final Logger logger = Logger.getLogger(CreateDAO.class);


  private String path = "com/xwtech/cring/pub/dao";

  //工程源码路径（需要修改）
  private String projectPath = "c:/src";

  //需要引用的po的包路径 如：import com.xwtech.cring.pub.po
  private String poPackagePath = "com.xwtech.cring.pub.po";

  public CreateDAO()
  {
  }

  //创建java文件
  public void createFile(File file)
  {
    File parent = file.getParentFile();
    if(!parent.exists())
    {
      parent.mkdirs();
    }
    if(!file.exists())
    {
      try
      {
        file.createNewFile();
      }
      catch(IOException e)
      {
        logger.info("创建文件失败！");
      }
    }
  }

  //取得目录内文件名称





  public String[] getFileNames(String filePath)
  {
    if(null == filePath)
    {
      return null;
    }
    File objFile = new File(filePath.trim());
    if(false == objFile.exists())
    {
      return null;
    }
    String[] strAryFileName = objFile.list();
    ArrayList arr = new ArrayList();
    for(int i = 0; i < strAryFileName.length; i++)
    {
      int index = strAryFileName[i].lastIndexOf(".");
      String suffix = strAryFileName[i].substring(index);
      if(suffix.equals(".java"))
      {
        arr.add(strAryFileName[i].substring(0,index));
      }
    }
    String[] fileName = new String[arr.size()];
    for(int j=0;j<arr.size();j++){
      fileName[j] = (String)arr.get(j);
    }
    return fileName;
  }

  /**
   * 生成文件
   * @param
   * @throws
   * @return
   */
  public void writeDAOFile(String poName, String type)
  {
    //生成的文件名
    String fileName = "";
    //对象首字母小写的名称
    String poNameLower = "";
    String low = poName.substring(0, 1);
    String other = poName.substring(1);
    poNameLower = low.toLowerCase() + other;
    fileName = poName + "DAO";
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
    sb = sb.append("import java.util.List;");
    sb = sb.append("\r\n");
    sb = sb.append("import org.apache.log4j.Logger;");
    sb = sb.append("\r\n");
    sb = sb.append("import org.springframework.orm.hibernate3.support.HibernateDaoSupport;");
    sb = sb.append("\r\n");
    sb = sb.append("import org.springframework.orm.ObjectRetrievalFailureException;");
    sb = sb.append("\r\n");
    sb = sb.append("import "+this.poPackagePath+"." + poName + " ;");
    sb = sb.append("\r\n");
    sb = sb.append("\r\n");
    sb = sb.append("public class " + fileName + " extends HibernateDaoSupport");
    sb = sb.append("\r\n{\r\n");
    sb = sb.append("\r\n");
    sb = sb.append("  protected static final Logger logger = Logger.getLogger(" + fileName + ".class);");
    sb = sb.append("\r\n");
    sb = sb.append("\r\n");
    sb = sb.append("  public void save" + poName + "(" + poName + " " + poNameLower + ")");
    sb.append("\r\n  {\r\n");
    sb = sb.append("    getHibernateTemplate().saveOrUpdate(" + poNameLower + ");");
    sb.append("\r\n  }\r\n");
    sb.append("\r\n");
    sb = sb.append("  public " + poName + " get" + poName + "(Long id)");
    sb.append("\r\n  {\r\n");
    sb = sb.append("    " + poName + " " + poNameLower + " = (" + poName + ")getHibernateTemplate().get(" + poName + ".class, id);");
    sb.append("\r\n");
    sb = sb.append("    if(" + poNameLower + " == null)");
    sb.append("\r\n    {\r\n");
    sb.append("      logger.warn(\"uh oh, " + poName + " \" + id + \"' not found...\");");
    sb.append("\r\n");
    sb = sb.append("      throw new ObjectRetrievalFailureException(" + poName + ".class, id);");
    sb.append("\r\n    }\r\n");
    sb = sb.append("  return " + poNameLower + ";");
    sb.append("\r\n  }\r\n");
    sb.append("\r\n");
    sb = sb.append("  public List get" + poName + "s()");
    sb.append("\r\n  {\r\n");
    sb = sb.append("    return getHibernateTemplate().loadAll(" + poName + ".class);");
    sb.append("\r\n  }\r\n");
    sb.append("\r\n");
    sb = sb.append("  public void remove" + poName + "(Long id)");
    sb.append("\r\n  {\r\n");
    sb = sb.append("    getHibernateTemplate().delete(get" + poName + "(id));");
    sb.append("\r\n  }\r\n");
    sb = sb.append("\r\n}\r\n");
    if(type.equals("1"))
    {
      String filePathName = projectPath + "/" + path + "/" + fileName + ".java";
      File file = new File(filePathName);
      this.createFile(file);
      try
      {
        FileOutputStream fileOut = new FileOutputStream(file);
        OutputStreamWriter out = new OutputStreamWriter(fileOut, "UTF-8");
        out.write(sb.toString());
        out.close();
      }
      catch(IOException ex)
      {
        logger.info("");
      }
    }
    else if(type.equals("2"))
    {
      logger.info(sb.toString());
    }
    StringBuffer xmlSb = new StringBuffer();
    xmlSb.append("       <bean id=\"" + fileName.substring(0,1).toLowerCase()+fileName.substring(1,fileName.length()) + "\" class=\"" + path.replace('/','.') + "." + fileName + "\">");
    xmlSb.append("<property name=\"sessionFactory\" ref=\"sessionFactory\"/>");
    xmlSb.append("</bean>");
    logger.info(xmlSb.toString());
  }

  public static void main(String[] args)
  {
    CreateDAO createDAO = new CreateDAO();
    //po的名称(手工写入)
    String[] poNames =
        {"RingGroupRing"};
    //po文件存放路径(手工写入)  程序将到此目录下寻找po文件生成dao
    String path = "D:/NewPlatForm/Services/clportal/projectsrc/src/com/xwtech/cring/pub/po";
    String[] names = createDAO.getFileNames(path);
    //输出类型 1：通过文件名输出到文件 2：通过文件名输出到命令行 3：通过目录名输出到文件 4：通过目录名输出到命令行





    String type = "1";
    if(type.equals("3"))
    {
      for(int i = 0; i < names.length; i++)
      {
        if(names[i].length() > 0)
        {
          createDAO.writeDAOFile(names[i], "1");
        }
      }
    }
    else if(type.equals("4"))
    {
      for(int i = 0; i < names.length; i++)
      {
        if(names[i].length() > 0)
        {
          createDAO.writeDAOFile(names[i], "2");
        }
      }
    }
    else
    {
      for(int i = 0; i < poNames.length; i++)
      {
        if(poNames[i].length() > 0)
        {
          createDAO.writeDAOFile(poNames[i], type);
        }
      }
    }
  }
}
