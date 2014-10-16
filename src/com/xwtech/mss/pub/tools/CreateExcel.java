package com.xwtech.mss.pub.tools;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.log4j.Logger;

import com.xwtech.framework.pub.dao.BaseDao;
import com.xwtech.mss.pub.constants.SpmsConstants;

/**
 * 根据ExcelBean生成excel文件
 * @author gdp
 */
public class CreateExcel
{

	protected static final Logger log = Logger.getLogger(CreateExcel.class);

	/**
	 * 构造方法
	 */
	public CreateExcel()
	{
	}

	/**
	 * 设置标题样式
	 * @return
	 */
	public static WritableCellFormat getTitle(String strAlign)
	{
		WritableFont font = new WritableFont(WritableFont.TIMES, 9, WritableFont.BOLD);
		try
		{
			font.setColour(Colour.BLACK);//黑色字体
		}
		catch (WriteException e1)
		{
			e1.printStackTrace();
		}
		WritableCellFormat format = new WritableCellFormat(font);
		try
		{
			if (strAlign.equals("LEFT"))
			{
				format.setAlignment(jxl.format.Alignment.LEFT);
			}
			else if (strAlign.equals("RIGHT"))
			{
				format.setAlignment(jxl.format.Alignment.RIGHT);
			}
			else
			{
				format.setAlignment(jxl.format.Alignment.CENTRE);
			}

			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		}
		catch (WriteException e)
		{
			e.printStackTrace();
		}
		return format;
	}

	/**
	 * 设置其他单元格样式
	 * @return
	 */
	public static WritableCellFormat getCell(String strAlign)
	{//12号字体,上下左右居中,带黑色边框
		WritableFont font = new WritableFont(WritableFont.TIMES, 9);
		WritableCellFormat format = new WritableCellFormat(font);
		try
		{
			if (strAlign.equals("LEFT"))
			{
				format.setAlignment(jxl.format.Alignment.LEFT);
			}
			else if (strAlign.equals("RIGHT"))
			{
				format.setAlignment(jxl.format.Alignment.RIGHT);
			}
			else
			{
				format.setAlignment(jxl.format.Alignment.CENTRE);
			}
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		}
		catch (WriteException e)
		{
			e.printStackTrace();
		}
		return format;
	}

	/**
	 * 将excel文件写入指定路径的文件，文件名不带后缀
	 * @param xlsFile
	 * @param excelBean
	 * @return
	 * @throws Exception
	 */
	public static boolean expordExcelAndSaveToFile(String fileSavePath, String fileName, ExcelBean excelBean) throws Exception
	{

		File dirPath = null;
		dirPath = new File(fileSavePath); //生成文件所在文件夹
		if (!dirPath.exists())
			dirPath.mkdirs();
		fileName = fileName + ".xls";

		File xlsFile = new File(fileSavePath + fileName);

		WritableWorkbook wbook = null;

		try
		{
			//WritableWorkbook wbook = Workbook.createWorkbook(os); //建立EXCLE文件
			wbook = Workbook.createWorkbook(xlsFile);

			//设置EXCEL标题
			WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat wcfFC = new WritableCellFormat(wfont);
			wfont = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			wcfFC = new WritableCellFormat(wfont);

			int sheetResultNum = 30000;//每页纪录数
			int curSheet = 1;//当前sheet页
			int cellCount = 0;//记录条数，从0开始

			Label wlabel;

			while ((cellCount % sheetResultNum == 0) && (cellCount < excelBean.getContent().length))
			{
				WritableSheet sheet = wbook.createSheet("第" + curSheet + "页", curSheet);
				curSheet++;
				//给每页设置标题
				for (int titleCount = 0; titleCount < excelBean.getTitleContent().length; titleCount++)
				{
					for (int j = 0; j < excelBean.getTitleContent()[titleCount].size(); j++)
					{
						//并且加粗标题行
						/*	if(titleCount==0){
						 wlabel = new Label(j+5, titleCount,
						 (excelBean.getTitleContent()[titleCount]).get(j)==null?"":(String)(excelBean.getTitleContent()[titleCount]).get(j),wcfFC);
						 sheet.addCell(wlabel);
						 }else{*/

						String str = (excelBean.getTitleContent()[titleCount]).get(j) == null ? ""
								: (String) (excelBean.getTitleContent()[titleCount]).get(j);

						wlabel = new Label(j, titleCount, str);
						sheet.addCell(wlabel);
						//	}
					}
				}
				//给每页填充内容
				while (cellCount < excelBean.getContent().length)
				{
					for (int j = 0; j < excelBean.getContent()[cellCount].size(); j++)
					{
						String str = (excelBean.getContent()[cellCount]).get(j) == null ? "" : (String) (excelBean.getContent()[cellCount]).get(j);

						if (excelBean.getType().length > 0 && excelBean.getType()[j] == 1)
						{
							//如果当列格式被设置为保留小数点2位
							DecimalFormat nf = new DecimalFormat("0.00");
							str = nf.format((excelBean.getContent()[cellCount]).get(j));
						}

						wlabel = new Label(j, excelBean.getTitleContent().length + (cellCount % sheetResultNum), str);
						sheet.addCell(wlabel);
					}
					cellCount++;
					if (cellCount % sheetResultNum == 0)
					{
						break;
					}
				}
			}

			wbook.write(); //写入文件
		}
		catch (Exception ex)
		{
			log.error("导出Excel文件到" + fileSavePath + fileName + "出错！", ex);
		}
		finally
		{
			try
			{
				if (wbook != null)
				{
					wbook.close();
				}
			}
			catch (Exception e)
			{
				log.debug("关闭Excel工作簿错误！", e);
			}
		}
		return true;
	}

	//产生日报excel
	public static String createReportExcel(String type, String dayRptTableName, String date, String rptPath, String fileName, String sql_columns,
			String[] excel_columns, BaseDao dao, String field) throws Exception
	{
		int[] excelColumType = null;
		//根据业务 配置excel格式 0:默认,1:(0.00)带小数的数字
		if (type.equals(SpmsConstants.WEB_FORUM))
		{
			//web社区访问
			int[] columType =
			{ 0, 0, 0, 0, 1, 0 };
			excelColumType = columType;
		}
		else if (type.equals(SpmsConstants.WEB_PLATE))
		{
			//板块访问文件名

		}
		else if (type.equals(SpmsConstants.WEB_TOPIC))
		{
			//主题访问文件名

		}
		else if (type.equals(SpmsConstants.WEB_TOPIC_PLATE_INFO))
		{
			//web主题板块信息文件名

		}
		else if (type.equals(SpmsConstants.WEB_USER_ACC))
		{
			//web用户访问（行为）

		}
		else if (type.equals(SpmsConstants.WEB_USER_POINT))
		{
			//web用户积分

		}
		else if(type.equals(SpmsConstants.WAP_LANMU))
		{
			//wap 栏目
			field = "repDate";
		}
		else if(type.equals(SpmsConstants.WAP_OTHER))
		{
			//wap 其它统计数据
			field = "repDate";
		}

		//查询指定业务日报
		String hql = "select " + sql_columns + " from " + dayRptTableName + " dr where dr." + field + " =" + date;

		List resultList = new ArrayList();
		resultList = dao.find(hql);

		if (resultList.size() == 0)
		{
			log.info(dayRptTableName + "表中没有查到" + date + "日期的记录，无法产生日报，可能文件名日期与内容不符。");
			return null;
		}

		//文件标题
		Vector title = new Vector();

		for (int x = 0; x < excel_columns.length; x++)
		{
			title.add(excel_columns[x]);
		}

		Vector[] titleContent = new Vector[1];
		titleContent[0] = title;

		//文件内容 2维
		Vector[] contents = new Vector[resultList.size()];

		Object[] obj = null;
		for (int i = 0; i < resultList.size(); i++)
		{
			obj = (Object[]) resultList.get(i);

			//每行文件内容对象
			Vector content = new Vector();
			for (int j = 0; j < excel_columns.length; j++)
			{
				content.add(obj[j] == null ? "" : obj[j]);
			}
			//加入总结果集
			contents[i] = content;
		}

		ExcelBean excelBean = new ExcelBean();
		//excel内容
		excelBean.setContent(contents);
		//excel列
		excelBean.setTitleContent(titleContent);
		//列格式
		excelBean.setType(excelColumType);
		//向目录产出excel
		CreateExcel.expordExcelAndSaveToFileByType(rptPath, fileName, excelBean);

		return rptPath + fileName + SpmsConstants.EXCEL_POSTFIX;
	}

	public static boolean expordExcelAndSaveToFileByType(String fileSavePath, String fileName, ExcelBean excelBean) throws Exception
	{

		File dirPath = null;
		dirPath = new File(fileSavePath); //生成文件所在文件夹
		if (!dirPath.exists())
			dirPath.mkdirs();
		fileName = fileName + ".xls";

		File xlsFile = new File(fileSavePath + fileName);

		WritableWorkbook wbook = null;

		try
		{
			//WritableWorkbook wbook = Workbook.createWorkbook(os); //建立EXCLE文件
			wbook = Workbook.createWorkbook(xlsFile);

			//设置EXCEL标题
			WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat wcfFC = new WritableCellFormat(wfont);
			wfont = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			wcfFC = new WritableCellFormat(wfont);

			int sheetResultNum = 30000;//每页纪录数
			int curSheet = 1;//当前sheet页
			int cellCount = 0;//记录条数，从0开始

			Label wlabel;
			jxl.write.Number number;

			while ((cellCount % sheetResultNum == 0) && (cellCount < excelBean.getContent().length))
			{
				WritableSheet sheet = wbook.createSheet("第" + curSheet + "页", curSheet);
				curSheet++;
				//给每页设置标题
				for (int titleCount = 0; titleCount < excelBean.getTitleContent().length; titleCount++)
				{
					for (int j = 0; j < excelBean.getTitleContent()[titleCount].size(); j++)
					{
						//并且加粗标题行
						/*	if(titleCount==0){
						 wlabel = new Label(j+5, titleCount,
						 (excelBean.getTitleContent()[titleCount]).get(j)==null?"":(String)(excelBean.getTitleContent()[titleCount]).get(j),wcfFC);
						 sheet.addCell(wlabel);
						 }else{*/

						wlabel = new Label(j, titleCount, (excelBean.getTitleContent()[titleCount]).get(j) == null ? "" : (String) (excelBean
								.getTitleContent()[titleCount]).get(j));
						sheet.addCell(wlabel);

						//	}
					}
				}
				//给每页填充内容
				while (cellCount < excelBean.getContent().length)
				{
					for (int j = 0; j < excelBean.getContent()[cellCount].size(); j++)
					{
						if (((excelBean.getContent()[cellCount]).get(j)) instanceof Double)
						{
							String str = (excelBean.getContent()[cellCount]).get(j) == null ? "" : ((Double) (excelBean.getContent()[cellCount])
									.get(j)).toString();

							if (null!=excelBean.getType() && excelBean.getType().length > 0 && excelBean.getType()[j] == 1)
							{
								//如果当列格式被设置为保留小数点2位
								DecimalFormat nf = new DecimalFormat("0.00");
								str = nf.format((excelBean.getContent()[cellCount]).get(j));
							}

							wlabel = new Label(j, excelBean.getTitleContent().length + (cellCount % sheetResultNum), str);
							sheet.addCell(wlabel);
						}
						else if (((excelBean.getContent()[cellCount]).get(j)) instanceof Long)
						{
							String str = (excelBean.getContent()[cellCount]).get(j) == null ? "" : ((Long) (excelBean.getContent()[cellCount])
									.get(j)).toString();

							if (null!=excelBean.getType() && excelBean.getType().length > 0 && excelBean.getType()[j] == 1)
							{
								//如果当列格式被设置为保留小数点2位
								DecimalFormat nf = new DecimalFormat("0.00");
								str = nf.format((excelBean.getContent()[cellCount]).get(j));
							}

							wlabel = new Label(j, excelBean.getTitleContent().length + (cellCount % sheetResultNum), str);
							sheet.addCell(wlabel);
						}
						else if (((excelBean.getContent()[cellCount]).get(j)) instanceof String)
						{
							wlabel = new Label(j, excelBean.getTitleContent().length + (cellCount % sheetResultNum),
									(excelBean.getContent()[cellCount]).get(j) == null ? "" : (String) (excelBean.getContent()[cellCount]).get(j));
							sheet.addCell(wlabel);
						}
						else
						{
							log.error("导出excel 出现未处理类型：" + (excelBean.getContent()[cellCount]).get(j).getClass().getName());
						}
					}
					cellCount++;
					if (cellCount % sheetResultNum == 0)
					{
						break;
					}
				}
			}

			wbook.write(); //写入文件
		}
		catch (Exception ex)
		{
			log.error("导出Excel文件到" + fileSavePath + fileName + "出错！", ex);
		}
		finally
		{
			try
			{
				if (wbook != null)
				{
					wbook.close();
				}
			}
			catch (Exception e)
			{
				log.debug("关闭Excel工作簿错误！", e);
			}
		}
		return true;
	}

	public static void main(String args[]) throws Exception
	{
		String fileSavePath = "E:\\spms_ftp\\local\\web_apache\\rep\\";
		String fileName = "access_log.2007-12-12";

		Vector[] titleContent = new Vector[1];
		Vector[] content = new Vector[10];

		Vector tit = new Vector();
		tit.add("AREAID");
		tit.add("CDATE");
		tit.add("USERID");
		tit.add("MOBILE");
		tit.add("POINTVAL");
		tit.add("PRESTIGEVAL");
		tit.add("MONEYVAL");
		tit.add("MZONEVAL");
		titleContent[0] = tit;

		Vector con = new Vector();
		con.add("BBS_WEB");
		con.add("20090228");
		con.add("1");
		con.add("13951776391");
		con.add("123");
		con.add("100");
		con.add("15");
		con.add("15");
		for (int i = 0; i < content.length; i++)
		{
			content[i] = con;
		}

		ExcelBean excelBean = new ExcelBean();
		excelBean.setTitle("webApacheLog20071212");
		excelBean.setSheetName("webApacheLog20071212");
		excelBean.setContent(content);
		excelBean.setTitleContent(titleContent);
		expordExcelAndSaveToFile(fileSavePath, fileName, excelBean);
	}

}
