package com.xwtech.framework.pub.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

//import sun.nio.ch.FileChannelImpl;

import com.xwtech.framework.pub.web.FrameworkApplication;
import com.xwtech.framework.pub.xmlContants.XmlContants;

/**
 * 对文件的操作
 * <p>Title: Framework </p>
 *
 * <p>Description: Framework</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: xwtech.com</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class FileUtils
{

	protected static final Logger log = Logger.getLogger(FileUtils.class);

	private static Document instants = DocumentHelper.createDocument();

	public static Properties property = null;

	//根节点名称
	private String rootName = XmlContants.ROOT;

	//根节点属性名
	private String attributeName = XmlContants.ELEMENTATTRIBUTE;

	//子节点名称
	private String childNodeName = XmlContants.CHILDELEMENTNAME;

	//内容节点名称－标题title
	private String property_title = XmlContants.PROPERTY_TITLE;

	//内容节点名称－标题对应的方法名称
	private String property_method = XmlContants.PROPERTY_METHOD;

	//内容节点名称－方法名对应的值
	private String property_value = XmlContants.PROPERTY_VALUE;

	//内容节点名称
	private String property_name = XmlContants.PROPERTY_NAME;

	//内容节点－属性名
	private String property_attribute_name = XmlContants.PROPERTY_ATTRIBUTE_NAME;

	//内容节点名称－属性值
	private String property_attribute_value = XmlContants.PROPERTY_ATTRIBUTE_VALUE;

	//根节点路径
	private String rootPath = XmlContants.ROOTPATH;

	//根节点属性路径
	private String rootAttributePath = XmlContants.ROOTATTRIBUTEPATH;

	//子节点属性路径
	private String elementPath = XmlContants.ELEMENTPATH;

	/**
	 * 构造方法
	 */
	public FileUtils()
	{
	}

	public static synchronized void createFile(String path, byte[] fileByte)
	{
		FileOutputStream fileStream = null;
		try
		{
			fileStream = new FileOutputStream(path);
			fileStream.write(fileByte);
			fileStream.close();
		}
		catch (FileNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * 读取用户号码的TXT文件
	 * 文件格式:
	 * 13800000000
	 * 13800000001
	 *
	 * @param txtFile File 文件名称
	 * @return List
	 * @throws IOException  IO异常
	 */
	public static List readNumTxtFile(File txtFile) throws IOException
	{
		List numList = new ArrayList();
		if (txtFile != null)
		{
			BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(txtFile)));
			String userNum = bufr.readLine();
			String tempNum = "0123456789";
			int j = -1;
			while (userNum != null)
			{
				if (!userNum.equalsIgnoreCase("") && userNum.length() <= 12)
				{
					for (int i = 0; i < userNum.length(); i++)
					{
						j = tempNum.indexOf(userNum.charAt(i));
						if (j < 0)
						{
							break;
						}
					}
					if (j >= 0)
					{
						numList.add(userNum);
					}
				}
				userNum = bufr.readLine();
			}
			return numList;
		}
		else
		{
			return null;
		}
	}

	/**
	 * 将页面上的内容导出到Excel文件中
	 * @param fileSavePath xls文件保存路径
	 * @param fileName 文件名称
	 * @param title 报表标题
	 * @param busSalle 营业厅名称
	 * @param time 报表生成时间
	 * @param valueList 报表内容列表 里面保存的是字符串数组
	 */
	public static void exportToExcel(String fileSavePath, String fileName, String title, String[] colName, String excelHeadName, String busSalle,
			String time, List valueList, String fTitle)
	{

		File dirPath = null;
		dirPath = new File(fileSavePath);
		if (!dirPath.exists())
			dirPath.mkdirs();
		fileName = fileName + ".xls";
		File xlsFile = new File(fileSavePath + fileName);
		WritableWorkbook book = null;
		String[] value = null;

		WritableCellFormat cellFormat = new WritableCellFormat();

		try
		{
			book = Workbook.createWorkbook(xlsFile);
			int sheetI = 0;
			WritableSheet sheet = book.createSheet("第" + (sheetI + 1) + "页", sheetI);
			cellFormat.setAlignment(jxl.format.Alignment.CENTRE);

			//创建标题栏
			if (title != null && !title.equals(""))
			{
				for (int i = 0; i < 8; i++)
				{
					sheet.addCell(new Label(i, 0, ""));
				}
				//合并单元格
				sheet.mergeCells(0, 0, 7, 0);

				//设置标题格式和值
				sheet.addCell(new Label(0, 0, title, cellFormat));
			}

			//创建操作人和操作时间
			if (busSalle != null && !busSalle.equals(""))
			{
				for (int j = 0; j < 8; j++)
				{
					if (j == 0)
					{
						sheet.addCell(new Label(j, 1, excelHeadName + "：" + busSalle));
						sheet.setColumnView(0, 30);
					}
					else if (j == 5)
						sheet.addCell(new Label(j, 1, "填表时间："));
					else if (j == 6)
						sheet.addCell(new Label(j, 1, time));
					else
						sheet.addCell(new Label(j, 1, ""));
				}
			}
			//创建标题
			for (int k = 0; k < 8; k++)
			{
				sheet.addCell(new Label(k, 2, ""));
			}

			//合并单元格
			sheet.mergeCells(2, 2, 3, 2);

			//设置标题名称
			sheet.addCell(new Label(2, 2, fTitle, cellFormat));

			for (int l = 0; l < 8; l++)
			{
				sheet.addCell(new Label(l, 3, ""));
			}

			//合并指定位置的单元格
			sheet.mergeCells(0, 2, 0, 3);
			sheet.mergeCells(1, 2, 1, 3);
			sheet.mergeCells(4, 2, 4, 3);
			sheet.mergeCells(5, 2, 5, 3);
			sheet.mergeCells(6, 2, 6, 3);
			sheet.mergeCells(7, 2, 7, 3);

			//设置标题名称
			for (int n = 0; n < colName.length; n++)
			{
				if (n == 2 || n == 3)
					sheet.addCell(new Label(n, 3, colName[n], cellFormat));
				else
					sheet.addCell(new Label(n, 2, colName[n], cellFormat));
			}

			//创建报表内容
			for (int m = 0; m < valueList.size(); m++)
			{
				value = (String[]) valueList.get(m);
				sheet.addCell(new Label(0, m + 4, value[0]));
				sheet.addCell(new Label(1, m + 4, value[1]));
				sheet.addCell(new Label(2, m + 4, value[2]));
				sheet.addCell(new Label(3, m + 4, value[3]));
				sheet.addCell(new Label(4, m + 4, value[4]));
				sheet.addCell(new Label(5, m + 4, value[5]));
				sheet.addCell(new Label(6, m + 4, value[6]));
				sheet.addCell(new Label(7, m + 4, value[7]));
			}
			book.write();
		}
		catch (Exception ex)
		{
			log.error("导出Excel文件出错！", ex);
		}
		finally
		{
			try
			{
				if (book != null)
				{
					book.close();
				}
			}
			catch (Exception e)
			{
				log.debug("关闭Excel工作簿错误！", e);
			}
		}
	}

	/**
	 * 将页面上的内容导出到Excel文件中,修改成通用
	 * 
	 * @author liuxiang
	 * @param fileSavePath
	 *            xls文件保存路径
	 * @param fileName
	 *            文件名称
	 * @param title
	 *            报表总标题
	 * @param colName
	 *            列标题名
	 * @param excelHeadName
	 *            信息名
	 * @param excelHeadValue
	 *            信息值
	 * @param time
	 *            报表生成时间
	 * @param valueList
	 *            报表内容列表 里面保存的是字符串数组
	 */
	public static void exportToExcelCommon(String fileSavePath, String fileName, String title, String[] colName, String excelHeadName,
			String excelHeadValue, String time, List valueList)
	{

		File dirPath = null;
		dirPath = new File(fileSavePath);
		if (!dirPath.exists())
			dirPath.mkdirs();
		fileName = fileName + ".xls";
		File xlsFile = new File(fileSavePath + fileName);
		WritableWorkbook book = null;
		String[] value = null;

		WritableCellFormat cellFormat = new WritableCellFormat();

		// 导出数据的总列数
		int colLen = colName.length;

		try
		{
			book = Workbook.createWorkbook(xlsFile);
			int sheetI = 0;
			WritableSheet sheet = book.createSheet("第" + (sheetI + 1) + "页", sheetI);
			cellFormat.setAlignment(jxl.format.Alignment.CENTRE);

			// 创建标题栏
			if (title != null && !title.equals(""))
			{
				for (int i = 0; i < colLen; i++)
				{
					sheet.addCell(new Label(i, 0, ""));
				}
				// 合并单元格
				sheet.mergeCells(0, 0, colLen - 1, 0);

				// 设置标题格式和值
				sheet.addCell(new Label(0, 0, title, cellFormat));
			}

			// 创建操作人和操作时间
			if (excelHeadValue != null && !excelHeadValue.equals(""))
			{
				sheet.addCell(new Label(0, 1, excelHeadName + "：" + excelHeadValue));
				sheet.setColumnView(0, 30);
				sheet.addCell(new Label(1, 1, "填表时间：" + time));
			}

			// 创建标题行
			for (int k = 0; k < colLen; k++)
			{
				sheet.addCell(new Label(k, 2, ""));
			}

			// 设置标题名称
			for (int n = 0; n < colName.length; n++)
			{
				sheet.addCell(new Label(n, 2, colName[n], cellFormat));
			}

			// 创建报表内容
			for (int m = 0; m < valueList.size(); m++)
			{
				value = (String[]) valueList.get(m);
				for (int x = 0; x < colLen; x++)
				{
					sheet.addCell(new Label(x, m + 3, value[x]));
				}
			}
			book.write();

		}
		catch (Exception ex)
		{
			log.error("导出Excel文件出错！", ex);
		}
		finally
		{
			try
			{
				if (book != null)
				{
					book.close();
				}
			}
			catch (Exception e)
			{
				log.debug("关闭Excel工作簿错误！", e);
			}
		}
	}

	/**
	 * 根据指定的po名称和Excel文件路径，将Excel文件包装成xml格式的文件
	 * @param xmlProperties xml属性文件，包括文件路径和文件名称
	 * @param fileName 新xml文件名
	 * @param newXPath 新xml文件存放路径
	 * @param xlsFile excel文件，包括文件名和路径
	 * @param mappingXPath excel列和po中方法映射的xml文件
	 * @author yangy
	 * @return
	 */
	public String createDataWithXls(String xmlProperties, String fileName, String newXPath, String xlsFile, String mappingXPath)
	{

		//保存xml文档返回值，0表示保存失败，1标识保存成功
		int returnValue = 0;

		String xmlfile = newXPath + fileName;

		//xls对象
		Workbook wb = null;

		//工作簿
		Sheet[] sheets = null;

		//单元格
		Cell cell = null;

		//行数
		int rows = 0;

		//列数
		int cols = 0;

		Object[] objs = null;

		HashMap tempMap = null;

		List resultList = new ArrayList();

		//初始化解析、创建xml文件所需的资源
		//		  initXmlSource(xmlProperties);

		//解析po类中的方法与xls文件中的标题对应的xml文件
		List mappingData = parseXml(mappingXPath);

		//获取指定的po类名称
		String className = (String) ((HashMap) mappingData.get(0)).get(attributeName);

		List rootAttributeList = new ArrayList();

		rootAttributeList.add(new Object[]
		{ attributeName, className });

		try
		{

			FileInputStream in = new FileInputStream(xlsFile);

			wb = Workbook.getWorkbook(in);

			sheets = wb.getSheets();

			cols = sheets[0].getColumns();

			rows = sheets[0].getRows();

			Document xmlDoc = createXmlFile(rootName, rootAttributeList);
			if (mappingData != null && !mappingData.isEmpty())
			{
				String key = "";
				List elementList = null;
				Object[] elementObj = null;
				for (int i = 3; i < rows; i++)
				{
					Element element = addElementToXml(xmlDoc, childNodeName);
					for (int j = 0; j < mappingData.size(); j++)
					{
						objs = new Object[2];
						tempMap = (HashMap) mappingData.get(j);

						//获取标题名称
						Set keySet = tempMap.keySet();
						Iterator iterator = keySet.iterator();
						while (iterator.hasNext())
						{
							key = (String) iterator.next();
						}
						//获取列数据
						if (!key.equals("") && !key.equals(attributeName))
						{
							elementList = new ArrayList();
							elementObj = new Object[2];
							cell = sheets[0].findCell(key);
							String methodName = (String) tempMap.get(key);
							Cell[] cells = sheets[0].getColumn(cell.getColumn());
							elementObj[0] = methodName;
							elementObj[1] = (cells.length - 1) < i ? "" : cells[i].getContents();
							elementList.add(elementObj);
							addElementContentToXml(element, elementList, false);
						}
					}
				}
				returnValue = saveXmlFile(fileName, newXPath, xmlDoc);

				if (returnValue == 1)
				{
					log.info("转换excel文件为xml文件成功！");
				}
				else
				{
					log.info("转换excel文件为xml文件失败！");
				}
			}
		}
		catch (FileNotFoundException e3)
		{
			e3.printStackTrace();
		}
		catch (BiffException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e2)
		{
			e2.printStackTrace();
		}
		return xmlfile;
	}

	/**
	 * 解析po-xml映射的xml文件
	 * @param mappingXPath
	 * @author yangy
	 */
	public List parseXml(String mappingXPath)
	{

		SAXReader reader = new SAXReader();

		Document xmlDoc = null;

		//类名
		String className = "";

		//标题名称
		String title = "";

		//对应的po方法名称
		String methodName = "";

		HashMap classMap = new HashMap();

		HashMap entryMap = null;

		List dataList = new ArrayList();

		int flag = 1;

		try
		{
			//创建xml数据对象
			xmlDoc = reader.read(new File(mappingXPath));

			className = xmlDoc.selectSingleNode(rootAttributePath).getText();

			//获取所有entry节点
			List list = xmlDoc.selectNodes(elementPath);

			Iterator it = list.iterator();

			classMap.put(attributeName, className);

			dataList.add(0, classMap);

			while (it.hasNext())
			{
				Element element = (Element) it.next();

				title = element.element(property_title).getText();

				methodName = element.element(property_method).getText();

				entryMap = new HashMap();

				entryMap.put(title, methodName);

				dataList.add(flag, entryMap);

				flag++;
			}

			log.info("解析完成");

		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	/**
	 * 获取Document单态实例
	 * @return
	 */
	public static Document getInstants()
	{

		if (instants == null)
		{
			instants = DocumentHelper.createDocument();
		}
		return instants;
	}

	/**
	 * 根据指定的文件名和根节点名称创建基本的xml文件
	 * @param xmlFileName
	 * @param root 根节点名称
	 * @param rootAttributeList 根节点属性列表，其中保存了Object数组形式的根节点属性的键值对
	 * @author yangy
	 * @return
	 */
	public Document createXmlFile(String root, List rootAttributeList)
	{

		Document xmlDoc = DocumentHelper.createDocument();

		Object[] obj = null;

		//添加根节点
		Element element = xmlDoc.addElement(root);

		if (rootAttributeList != null && !rootAttributeList.isEmpty())
		{
			for (int i = 0; i < rootAttributeList.size(); i++)
			{
				obj = (Object[]) rootAttributeList.get(i);
				element.addAttribute((String) obj[0], (String) obj[1]);
			}
		}
		return xmlDoc;
	}

	/**
	 * 根据指定的文件名和根节点名称创建基本的xml文件
	 * @param xmlFileName
	 * @param root 根节点名称
	 * @param rootAttributeList 根节点属性列表，其中保存了Object数组形式的根节点属性的键值对
	 * @author yangy
	 * @return
	 */
	public Document createSigleRooltXmlFile(String root)
	{

		Document xmlDoc = DocumentHelper.createDocument();

		//添加根节点
		xmlDoc.addElement(root);

		return xmlDoc;
	}

	/**
	 * 在指定的xml文件中添加指定的子节点
	 * @param xmlDoc xml文件对象
	 * @param elementName 要添加的元素（即子节点）的名称
	 * @author yangy
	 * @return
	 */
	public Element addElementToXml(Document xmlDoc, String elementName)
	{

		Element rootElement = xmlDoc.getRootElement();

		Element childElement = rootElement.addElement(elementName);

		return childElement;
	}

	/**
	 * 在指定的子节点中添加指定的子节点和相应的值
	 * @param xmlDoc xml文件对象
	 * @param element xml文件中某元素
	 * @param elementName 要添加的元素（即子节点）的名称
	 * @param contentList 该子节点下的子元素，该列表中的子元素以Object数组的形式存放
	 * 					即obj[0]: elementName,obj[1]:elementText
	 * @param isMappingFormat 是否创建映射文件格式的xml ：<node>AAA</node>,另一种节点格式为：<property name="" value=""/>
	 * @author yangy
	 * @return
	 */
	public void addElementContentToXml(Element element, List contentList, boolean isMappingFormat)
	{

		//		  Document newXmlDoc = xmlDoc;

		Element childElement = element;

		Object[] obj = null;

		//添加子节点
		for (int i = 0; i < contentList.size(); i++)
		{
			obj = (Object[]) contentList.get(i);
			if (isMappingFormat)
			{

				//添加子节点
				Element grandchildElement = grandchildElement = childElement.addElement((String) obj[0]);

				//设置子节点内容
				grandchildElement.setText((String) obj[1]);
			}
			else
			{

				Element grandchildElement = grandchildElement = childElement.addElement(property_name);

				grandchildElement.addAttribute(property_attribute_name, (String) obj[0]);

				grandchildElement.addAttribute(property_attribute_value, (String) obj[1]);
			}
		}
		//	      return newXmlDoc;
	}

	/**
	 * 保存xml文档的内容到指定的径物理目录下
	 * @param xmlFileName
	 * @param filePath
	 * @param xmlDoc
	 * @author yangy
	 * @return
	 */
	public int saveXmlFile(String xmlFileName, String filePath, Document xmlDoc)
	{

		//0标识保存失败，1标识保存成功
		int returnValue = 0;

		try
		{

			//格式化输出,类型IE浏览一样
			OutputFormat format = OutputFormat.createPrettyPrint();

			//设置编码格式
			format.setEncoding("GBK");

			//将document中的内容写入文件中 
			XMLWriter writer = new XMLWriter(new FileWriter(new File(filePath + xmlFileName)), format);

			writer.write(xmlDoc);

			writer.close();

			//保存成功，返回值1
			returnValue = 1;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return returnValue;
	}

	/**
	 * 初始化properties文件
	 * @author yangy
	 * @return
	 */
	public Properties initXmlProperty(String fileName)
	{

		property = new Properties();

		InputStream in = null;
		try
		{
			in = new BufferedInputStream(new FileInputStream(fileName));

			property.load(in);

		}
		catch (FileNotFoundException e)
		{

			e.printStackTrace();

		}
		catch (IOException e)
		{

			e.printStackTrace();
		}
		return property;
	}

	/**
	 * 初始化xml文件各节点名称
	 * @author yangy
	 * @param fileName
	 */
	public void initXmlSource(String fileName)
	{

		property = initXmlProperty(fileName);

		//根节点名称
		rootName = (String) property.get("root");

		//根节点属性名
		attributeName = (String) property.get("elementAttribute");

		//子节点名称
		childNodeName = (String) property.get("childElementName");

		//内容节点名称－标题title
		property_title = (String) property.get("property_title");

		//内容节点名称－标题对应的方法名称
		property_method = (String) property.get("property_method");

		//内容节点名称－方法名对应的值
		property_value = (String) property.get("property_value");

		//内容节点名称
		property_name = (String) property.get("property_name");
		;

		//内容节点－属性名
		property_attribute_name = (String) property.get("property_attribute_name");
		;

		//内容节点名称－属性值
		property_attribute_value = (String) property.get("property_attribute_value");
		;

		//根节点路径
		rootPath = (String) property.get("rootPath");

		//根节点属性路径
		rootAttributePath = (String) property.get("rootAttributePath");

		//子节点属性路径
		elementPath = (String) property.get("elementPath");
	}

	/**
	 * 文件操作：将文件从一个目录(oldFile所在的目录)拷贝到另外一个目录(newFile所在的目录)，如有同名文件，则用oldFile覆盖新目录的文件
	 * @param newFile 拷贝到新位置后的新文件
	 * @param oldFile 要拷贝的文件
	 * @return 操作成功标志
	 * @throws IOException
	 */
	public static boolean copyFile(File newFile, File oldFile) throws IOException
	{
		boolean successFlag = false;

		InputStream is = null;
		FileOutputStream fs = null;
		String filePath = newFile.getPath();
		try
		{
			/*	//如果文件夹不存在 则建立新文件夹
			 File file = new File(filePath.substring(0, filePath
			 .lastIndexOf(SpmsConstants.LIUNX_SEPARATRIX)));
			 if (!file.exists())
			 {
			 file.mkdirs();
			 }*/
			if (!newFile.exists())
			{
				newFile.createNewFile();
			}
			int bytesum = 0;
			int byteread = 0;
			if (oldFile.exists())
			{
				is = new FileInputStream(oldFile); //读入原文件
				fs = new FileOutputStream(newFile);//创建新文件
				byte[] buffer = new byte[1444];
				while ((byteread = is.read(buffer)) != -1)
				{
					bytesum += byteread; //字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				is.close();
				fs.close();
				successFlag = true;
			}
			else
			{
				log.info("原文件已被删除！");
			}
			//删除原文件
			oldFile.delete();

		}
		catch (IOException ex)
		{
			log.info("文件可能被其它程序占用，放弃移动文件");
			ex.printStackTrace();
		}
		finally
		{
			if (is != null)
			{
				is.close();
			}
			if (fs != null)
			{
				fs.close();
			}
		}

		return successFlag;
	}

	/*
	 * FileUtils.exportToExcel(fileSavePath, fileName, title, colName,numOrStr,mergeRow,_title, "", time, excelList,"");
	 
	 String realFileName = fileSavePath+fileName+".xls";
	 System.out.println("fileSavePath==:"+fileSavePath);
	 System.out.println("fileName===:"+fileName);
	 System.out.println("realFileName===:"+realFileName);
	 File sourceFile = new File(realFileName);
	 response.setContentType("application/x-msdownload");
	 response.setHeader("Content-Disposition","attachment;" + " filename="+new String(sourceFile.getName().getBytes("gb2312"), "ISO-8859-1"));
	 System.out.println("=================导出报表 path:"+fileSavePath);
	 Date d2 = new Date();
	 System.out.println("=================导出报表花费时间 path:"+(d2.getTime()-d1.getTime())/1000+"秒");
	 bis =new java.io.BufferedInputStream(new java.io.FileInputStream(realFileName));
	 os = response.getOutputStream();
	 byte[] buff = new byte[2048];
	 int bytesRead;
	 while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) 
	 {
	 os.write(buff,0,bytesRead);
	 }
	 os.flush();
	 */

	/*
	 * author:jzy
	 * date: 09-01-12
	 * titel:号码导出
	 * event: 
	 * request parameter: 1 sql(号码查询sql) 2 exportDir(导出目录) 3 fileName (文件名)
	 * operation: 
	 * 			  1 按sql查询得到结果集
	 * 			  3 按导出目录文件名创建文件
	 * 		      4 写文件
	 */
	public static String createFile(String sql, String exportDir, String fileName)
	{
		//根据sql得到查询结果
		List list_num = FrameworkApplication.baseJdbcDAO.queryForList(sql);
		//生产文件全路径
		String str_FileFullPath = exportDir + fileName;

		File dirPath = null;
		dirPath = new File(exportDir); // 生成文件所在文件夹
		if (!dirPath.exists())
			dirPath.mkdirs();

		//创建迭代器
		Iterator it = list_num.iterator();

		FileWriter fw = null;
		try
		{
			fw = new FileWriter(str_FileFullPath);

			while (it.hasNext())
			{
				fw.write(String.valueOf(((Map) it.next()).get("mobile_num")) + "\r\n");
			}
			fw.close();

		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return str_FileFullPath;
	}

	/**
	 * 文件锁
	 * @param fileIn
	 * @param fd
	 * @return
	 */
//	public static FileChannel getChannel(FileInputStream fileIn, FileDescriptor fd)
//	{
//		FileChannel channel = null;
//		synchronized (fileIn)
//		{
//			channel = FileChannelImpl.open(fd, true, true, fileIn);
//			return channel;
//		}
//	}
	
	
	/**
	 * 用来创建目录
	 * 
	 * @param dirName String, 目录名（或绝对路径文件名）

	 * @return bool, 是否创建成功
	 */
	public static boolean mkDir(String dirName)
	{
		File file = new File(dirName);
		if (file.exists()) // 判断文件或目录是否存在

		{
			if (file.canWrite() == false) // 判断文件或目录是否可写

			{
				return false;
			} else
			{
				return true;
			}
		} else
		// 如果不存在就创建目录
		{
			String path = null;
			// 确保windows系统下两种分割符兼容
			int firstSlash1 = dirName.indexOf("/");
			int finalSlash1 = dirName.lastIndexOf("/");

			int firstSlash2 = dirName.indexOf("\\");
			int finalSlash2 = dirName.lastIndexOf("\\");

			firstSlash1 = firstSlash1 < firstSlash2 ? firstSlash1 : firstSlash2;
			finalSlash1 = finalSlash1 > finalSlash2 ? finalSlash1 : finalSlash2;

			//if (firstSlash1 < 0 && finalSlash1 < 0)
			//{
			//	firstSlash1 = dirName.indexOf("\\");
			//	finalSlash1 = dirName.lastIndexOf("\\");
			//}

			if (finalSlash1 == 0) // 非法路径
			{
				return false;
			} else if (finalSlash1 == 1) // UNIX系统根目录

			{
				path = File.separator;
			} else if (firstSlash1 == finalSlash1) // 确保文件路径分隔符是路径的一部分
			{
				path = dirName.substring(0, finalSlash1 + 1);
			} else
			{
				path = dirName.substring(0, finalSlash1);
			}

			File dir = new File(path);
			return dir.mkdirs();
		}
	}
	
	
	/**
	 * 从文件中读取二进制字节流
	 * 
	 * @param fileName String, 要读取的文件名

	 * @return byte[], 读取的内容

	 */
	public static byte[] read(String fileName) throws Exception
	{
		if (fileName == null)
		{
			return null;
		}
		FileInputStream in = null;
		try
		{
			in = new FileInputStream(fileName);
			byte[] bytes = new byte[in.available()];
			in.read(bytes);
			return bytes;
		} catch (Exception e)
		{
			throw e;
		} finally
		{
			in.close();
		}
	}

	/**
	 * 从文件中读取第一行文本

	 * 
	 * @param fileName String, 文件名

	 * @return String, 文件第一行内容

	 */
	public static String readLine(String fileName) throws Exception
	{
		if (fileName == null)
		{
			return null;
		}
		BufferedReader in = null;
		try
		{
			in = new BufferedReader(new FileReader(fileName));
			String str = in.readLine();
			if (str != null)
			{
				str = str.trim();
			}
			return str;
		} catch (Exception e)
		{
			throw e;
		} finally
		{
			in.close();
		}
	}

	/**
	 * 从文件中读取文本到缓冲

	 * 
	 * @param fileName String, 文件名

	 * @return String, 所读文件内容

	 * @throws Exception
	 */
	public static String readTxt(String fileName) throws Exception
	{
		if (fileName == null)
		{
			return null;
		}
		BufferedReader in = null;
		String lineIn = null;
		StringBuffer buffer = new StringBuffer();
		try
		{
			in = new BufferedReader(new FileReader(fileName));
			while ((lineIn = in.readLine()) != null)
			{
				buffer.append(lineIn.trim());
				buffer.append("\r\n"); // 行号不可少

			}
			return buffer.toString();
		} catch (Exception e)
		{
			throw e;
		} finally
		{
			in.close();
		}
	}

	/**
	 * 把二进制字节流写入文件

	 * 
	 * @param bytes byte[], 要写出的字节数组
	 * @param fileName String, 文件名


	 */
	public static void write(byte[] bytes, String fileName) throws Exception
	{
		if (bytes == null || fileName == null)
		{
			return;
		}
		mkDir(fileName); // 写文件需要检查相关目录是否存在，没有的话需要创建

		FileOutputStream out = null;
		try
		{
			out = new FileOutputStream(fileName);
			out.write(bytes);
		} catch (Exception e)
		{
			throw e;
		} finally
		{
			out.close();
		}
	}
	
	
	/**
	 * 把字符串分割后写入文件

	 * 
	 * @param str
	 * @param regex
	 * @param fileName
	 * @throws Exception
	 */
	public static void write(String str, String regex, String fileName) throws Exception
	{
		if (str == null || fileName == null)
		{
			return;
		}
		mkDir(fileName); //写文件需要检查相关目录是否存在，没有的话需要创建

		BufferedWriter writer = null;
		try
		{
			writer = new BufferedWriter(new FileWriter(fileName));
			String[] lines = str.split(regex);
			if (lines != null && lines.length > 0)
			{
				for (int i = 0; i < lines.length; i++)
				{
					writer.write(lines[i] + "\n");
				}
			}
		} catch (Exception e)
		{
			throw e;
		} finally
		{
			writer.close();
		}
	}

	/**
	 * 写文件操作

	 * 
	 * @param str String, 要写出的内容
	 * @param fileName String, 文件名

	 * @param isAppend bool, 是否追加，如果值为false，则覆盖掉以前的内容
	 * @throws Exception
	 */
	private static void write(String str, String fileName, boolean isAppend) throws Exception
	{
		if (fileName == null || str == null)
		{
			return;
		}
		mkDir(fileName); // 写文件需要检查相关目录是否存在，没有的话需要创建

		BufferedWriter out = null;

		try
		{
			out = new BufferedWriter(new FileWriter(fileName, isAppend));
			out.write(str);
		} catch (Exception e)
		{
			throw e;
		} finally
		{
			out.close();
		}
	}

	/**
	 * 将字符串写出到文本文件，如果文件已存在，将覆盖原来的内容
	 * 
	 * @param str String, 要写出的内容
	 * @param fileName String, 文件名


	 * @throws Exception
	 */
	public static void writeTxt(String str, String fileName) throws Exception
	{
		write(str, fileName, false);
	}
	/**
	 * 测试Excel导出
	 * @param args
	 */
	//  public static void main (String[] args) throws IOException{
	//	  copyFile(new File("F:/test.txt"), new File("F:/文档/test.txt"));
	//	  String savePath = "E:/textXls/";
	//	  String filaName = "Statistics";
	//	  String title = "2007年3月营业厅礼品进、销、存报表";
	//	  String[] colName = {"礼品名称","上月结存","正常入库","客户退库","本月发出","本月退库","本月结存","备注"};
	//	  String time = "20070312";
	//	  String busSalle = "鼓楼区营业厅";
	//	  ArrayList list = new ArrayList();
	//	  String[] value = new String[8];
	//	  for(int j = 0;j<10;j++){
	//		  for(int i = 0 ; i < 8 ;i++ ){
	//			  if(i==0)
	//				  value[i] = "充值赠送苏果券"+i;
	//			  else
	//				  value[i] = i+"1";
	//	  	}
	//		  list.add(j,value);
	//	  }
	//	  exportToExcel(savePath,filaName,title,colName,busSalle,time,list);	  
	//	  log.debug("导出xls成功！");
	//  }
	//	  public static void main (String[] args){
	//		  FileUtils fu = new FileUtils();
	//		  fu.initXmlSource("");
	//		  
	//		  System.out.println(fu.rootName);
	//		  System.out.println(fu.attributeName);
	//		  System.out.println(fu.childNodeName);
	//		  System.out.println(fu.elementPath);
	//		  System.out.println(fu.property_attribute_name);
	//	  }
}
