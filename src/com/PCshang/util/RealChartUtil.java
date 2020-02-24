package com.PCshang.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.PCshang.view.MainFrame;

public class RealChartUtil extends ChartPanel implements Runnable {

	MainFrame mainfrm;
	float x_site;

	private float[] f;
	public static TimeSeries[] ts;

	/*public static TimeSeries[] getTs() {
		return ts;
	}

	public static void setTs(TimeSeries[] ts) {
		RealChartUtil.ts = ts;
	}*/

	public static TimeSeriesCollection dataset;



	public RealChartUtil(MainFrame mainfrm, String chartContent, String title, String yaxisName, float[] f,
			float x_site) {
		super(createChart(chartContent, title, yaxisName));
		this.f = f;
		this.x_site = x_site;
		this.mainfrm = mainfrm;
	}

	public float[] getF() {
		return f;
	}

	public void setF(float[] f) {
		this.f = f;
	}

	public float getX_site() {
		return x_site;
	}

	public void setX_site(float x_site) {
		this.x_site = x_site;
	}

	
	
	
	public static TimeSeriesCollection getDataset() {
		return dataset;
	}

	public static void setDataset(TimeSeriesCollection dataset) {
		RealChartUtil.dataset = dataset;
	}

	@SuppressWarnings("deprecation")
	public static JFreeChart createChart(String chartContent, String title, String yaxisName) {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		ts = new TimeSeries[10];

		for (int j = 0; j < 10; j++) {
			ts[j] = new TimeSeries("" + j, Millisecond.class);
			dataset.addSeries(ts[j]);
		}

		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(chartContent, // 图标题
				title, // x 轴标题
				yaxisName, // y 轴标题
				dataset, // 数据源
				false, // 是否显示图例
				true, // 是否显示提示
				false); // 是否指定连接

		XYPlot plot = (XYPlot) jfreechart.getPlot();
		DateAxis xAxis = (DateAxis) plot.getDomainAxis(); // X 轴对象的获取操作：

		// NumberAxis yAxis = (NumberAxis) plot.getRangeAxis(); //：

		XYItemRenderer renderer = plot.getRenderer();// Renderer 对象的获取操作：

		ValueAxis valueaxis = plot.getDomainAxis();
		valueaxis.setAutoRange(true);// 自动设置数据轴数据范围

		ValueAxis valueayis = plot.getRangeAxis();
		valueayis.setAutoRange(true);

		/*
		 * plot.setNoDataMessage("无对应的数据，请重新查询。"); plot.setNoDataMessageFont(new
		 * Font("宋书", Font.PLAIN, 15));//字体的大小
		 * plot.setNoDataMessagePaint(Color.RED);//字体颜色
		 */
		return jfreechart;

	}

	@Override
	public void run() {

		while (true) {

			/*
			 * 关闭串口指令
			 */
			if (mainfrm.rxthread.stopRxThread == true) {				
				return;
			}
			/**
			 * 清除屏幕上数据
			 */
		/*	if (mainfrm.clearPlot) {
				for (int i = 0; i < f.length; i++) {
					ts[i].clear();				
				}
			}
			*/
			while (mainfrm.startPlot == true) {
				
				for (int i = 0; i < f.length; i++) {

					Millisecond se = new Millisecond();
					ts[i].add(se, (double) f[i]);
				//	System.out.println("chengg");

				}
				try {
					Thread.sleep(2);// 进程睡眠2ms，不然容易出现插入到数据库里相同的坐标，ts不允许有相同的x坐标
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		}

	}
}