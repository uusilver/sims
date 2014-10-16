package com.xwtech.mss.pub.tools;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;




public class CreateGraphics {
	
	/**
	 * 
	 * @return
	 */
	private static PieDataset createDataset()
	{
	DefaultPieDataset defaultpiedataset = new DefaultPieDataset(); //注意是DefaultPieDataset！！
	defaultpiedataset.setValue("One", new Double(33.7D));
	defaultpiedataset.setValue("Two", new Double(19.3D));
	defaultpiedataset.setValue("Three", new Double(27.5D));
	defaultpiedataset.setValue("Four", new Double(17.5D));
	return defaultpiedataset;
	}
	
	/**
	 * 由ChartFactory 产生 JFreeChart 对象
	 */
	private static JFreeChart createChart(PieDataset piedataset)
	{
	JFreeChart jfreechart = ChartFactory.createPieChart3D("Pie Chart Demo", piedataset, true, true, false);
	PiePlot pieplot = (PiePlot)jfreechart.getPlot(); //通过JFreeChart 对象获得 plot：PiePlot3D！！
	pieplot.setNoDataMessage("No data available"); // 没有数据的时候显示的内容
	pieplot.setForegroundAlpha(0.7f);
	pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}占{2}",NumberFormat.getNumberInstance(),new DecimalFormat("0.00%")));
	return jfreechart;
	}
	
	public static void main (String[] args)  throws IOException{
		PieDataset pieDataset  = createDataset();
		
		JFreeChart jFreeChart = createChart(pieDataset);
		
		FileOutputStream fos_jpg = null;
		try {
			fos_jpg = new FileOutputStream("D:\\demo1.jpg");
			ChartUtilities.writeChartAsJPEG(fos_jpg,0.9f,jFreeChart,400,300,null);
			System.out.println("图片生成成功！");
		} finally {
			try {
				fos_jpg.close();
			} catch (Exception e) {}
		}

	}
}
