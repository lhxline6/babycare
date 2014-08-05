package com.example.babycare.library;

import java.util.Arrays;

import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.annotation.SuppressLint;
import android.graphics.Color;

@SuppressLint("NewApi")
public class ChartDrawing2 {

	private String xTitle, yTitle, chartTitle;
	private String xLabel[];
	private XYMultipleSeriesDataset dataset;
	private XYMultipleSeriesDataset dataset_scatter;
	private XYMultipleSeriesRenderer multiRenderer_scatter;
	private XYMultipleSeriesRenderer multiRenderer;

	public XYMultipleSeriesRenderer getMultiRenderer() {
		return multiRenderer;
	}
	
	public XYMultipleSeriesRenderer getMultiRenderer_scatter() {
		return multiRenderer_scatter;
	}

	public XYMultipleSeriesDataset getDataset() {
		return dataset;
	}
	
	public XYMultipleSeriesDataset getDataset_scatter() {
		return dataset_scatter;
	}
	

	public ChartDrawing2(String xTitle, String yTitle, String chartTitle,String xLabel[]) {
		this.xTitle = xTitle;
		this.yTitle = yTitle;
		this.xLabel = Arrays.copyOf(xLabel, xLabel.length);
		this.chartTitle = chartTitle;
		// Creating a XYMultipleSeriesRenderer to customize the whole chart
		this.multiRenderer = new XYMultipleSeriesRenderer();
		this.multiRenderer_scatter = new XYMultipleSeriesRenderer();
		// Creating a dataset to hold each series
		this.dataset = new XYMultipleSeriesDataset();
		this.dataset_scatter = new XYMultipleSeriesDataset();
	}
	
	
	/**
	 * ��XYSeries�����ơ�������ӵ���ݼ� XYMultipleSeriesDataset������ȥ
	 * */
	public void set_XYSeries(double value[], String lineName) {
		// ����һ��XYSeries�����ΪlineName�ϵ����
		XYSeries oneSeries = new XYSeries(lineName);
		// Adding data to Series
		for (int i = 0; i < value.length; i++) {
			oneSeries.add(i + 1, value[i]);
		}
		// Adding Series to the dataset
		this.dataset.addSeries(oneSeries);
	}
	
	public void set_XYSeries_scatter(double value[], String lineName) {
		// ����һ��XYSeries�����ΪlineName�ϵ����
		XYSeries oneSeries = new XYSeries(lineName);
		// Adding data to Series
		for (int i = 0; i < value.length; i++) {
			oneSeries.add(i + 1, value[i]);
		}
		// Adding Series to the dataset
		this.dataset_scatter.addSeries(oneSeries);
	}
   
	
	public XYSeriesRenderer set_XYSeriesRender_Style_Minus(int style) {
		XYSeriesRenderer seriesRenderer = new XYSeriesRenderer();
		//������������ɫ
		//seriesRenderer.setZoomLimits(ChartUtil.TEMPERATURE_LIMITES);  
		seriesRenderer.setColor(Color.CYAN);
		seriesRenderer.setFillPoints(true);
		//���������Ŀ��
		seriesRenderer.setLineWidth(2);
		PointStyle point = null;
		if(style == 1)
			seriesRenderer.setPointStyle(point.CIRCLE);
		if(style == 2)
			seriesRenderer.setPointStyle(point.SQUARE);
		if(style == 3)
			seriesRenderer.setPointStyle(point.TRIANGLE);
		seriesRenderer.setDisplayChartValues(true);
		return seriesRenderer;
	}

	public XYSeriesRenderer set_XYSeriesRender_Style_Middle() {
		XYSeriesRenderer seriesRenderer = new XYSeriesRenderer();
		//������������ɫ
		
		seriesRenderer.setColor(Color.YELLOW);
		seriesRenderer.setFillPoints(true);
		//���������Ŀ��
		seriesRenderer.setLineWidth(2);
		seriesRenderer.setDisplayChartValues(true);
		return seriesRenderer;
	}
	
	public XYSeriesRenderer set_XYSeriesRender_Style_User() {
		XYSeriesRenderer seriesRenderer = new XYSeriesRenderer();
		//������������ɫ
		
		seriesRenderer.setColor(Color.BLACK);
		//seriesRenderer.setFillPoints(true);
		//���������Ŀ��
		PointStyle point = null;
		seriesRenderer.setPointStyle(point.DIAMOND);
		seriesRenderer.setLineWidth(4);
		seriesRenderer.setDisplayChartValues(true);
		return seriesRenderer;
	}
	
	public XYSeriesRenderer set_XYSeriesRender_Style_Positive(int style) {
		XYSeriesRenderer seriesRenderer = new XYSeriesRenderer();
		//������������ɫ
		//seriesRenderer.setPointStyle(style);
		seriesRenderer.setColor(Color.GREEN);
		
		seriesRenderer.setFillPoints(true);
		//���������Ŀ��
		seriesRenderer.setLineWidth(2);
		PointStyle point = null;
		if(style == 1)
			seriesRenderer.setPointStyle(point.CIRCLE);
		if(style == 2)
			seriesRenderer.setPointStyle(point.SQUARE);
		if(style == 3)
			seriesRenderer.setPointStyle(point.TRIANGLE);
		seriesRenderer.setDisplayChartValues(true);
		return seriesRenderer;
	}
	

	
	public void set_XYMultipleSeriesRenderer_Style(XYSeriesRenderer renderer) {
		// ���� X �᲻��ʾ����(���������ֶ���ӵ����ֱ�ǩ)
		this.multiRenderer.setXLabels(0);
		//����Y��Ľ����
		this.multiRenderer.setYLabels(10);
		//����X��Ĵ������
		this.multiRenderer.setAxisTitleTextSize(16);// 设置坐标轴标题文本大小(30); // 设置轴标签文本大小
		this.multiRenderer.setXTitle(xTitle);
		//����Y��Ĵ������
		this.multiRenderer.setYTitle(yTitle);
		this.multiRenderer.setShowGrid(true);//设置是否在图表中显示网格
		this.multiRenderer.setAxesColor(Color.BLACK);
		this.multiRenderer.setXLabelsColor(Color.BLACK);
		this.multiRenderer.setYLabelsColor(0, Color.BLACK);
		this.multiRenderer.setLabelsColor(Color.BLACK);
		//������״ͼ������֮��ļ��
		this.multiRenderer.setZoomButtonsVisible(true);
		//this.multiRenderer.setPanLimits(new double[] {0, 50, 40, 120 });
		//this.multiRenderer.setZoomLimits(new double[] { 0.5, 20, 1, 150 });//设置缩放的范围
		//this.multiRenderer.setRange(new double[]{5d, 20d, 0d, 150d}); //设置chart的视图范围
		this.multiRenderer.setBarSpacing(0.5);
		this.multiRenderer.setApplyBackgroundColor(true);
		this.multiRenderer.setBackgroundColor(0xef5b9c);
		//this.multiRenderer.setMargins(new int[] { 20, 20, 50, 10 });//设置图表的外边框(上/左/下/右)
		this.multiRenderer.setMarginsColor(0xef5b9c);
		
		//this.multiRenderer.setBackgroundColor(Color.BLACK);
		this.multiRenderer.setZoomButtonsVisible(true);
		for (int i = 0; i < xLabel.length; i++) {
			//���X���ǩ
			this.multiRenderer.addXTextLabel(i + 1, this.xLabel[i]);
		}
		
		this.multiRenderer.addSeriesRenderer(renderer);

	}
	
	public void set_XYMultipleSeriesRenderer_scatter_Style(XYSeriesRenderer renderer) {
		// ���� X �᲻��ʾ����(���������ֶ���ӵ����ֱ�ǩ)
		this.multiRenderer_scatter.setXLabels(0);
		//����Y��Ľ����
		this.multiRenderer_scatter.setYLabels(10);
		//����X��Ĵ������
		this.multiRenderer_scatter.setAxisTitleTextSize(16);// 设置坐标轴标题文本大小(30); // 设置轴标签文本大小
		this.multiRenderer_scatter.setXTitle(xTitle);
		//����Y��Ĵ������
		this.multiRenderer_scatter.setYTitle(yTitle);
		this.multiRenderer_scatter.setShowGrid(true);//设置是否在图表中显示网格
		this.multiRenderer_scatter.setAxesColor(Color.BLACK);
		this.multiRenderer_scatter.setXLabelsColor(Color.BLACK);
		this.multiRenderer_scatter.setYLabelsColor(0, Color.BLACK);
		this.multiRenderer_scatter.setLabelsColor(Color.BLACK);
		//������״ͼ������֮��ļ��
		//this.multiRenderer_scatter.setZoomButtonsVisible(true);
		this.multiRenderer_scatter.setPanLimits(new double[] {0, 50, 40, 120 });
		this.multiRenderer.setZoomLimits(new double[] { 0.5, 20, 1, 150 });//设置缩放的范围
		this.multiRenderer.setRange(new double[]{5d, 20d, 0d, 150d}); //设置chart的视图范围
		this.multiRenderer_scatter.setBarSpacing(0.5);
		this.multiRenderer_scatter.setApplyBackgroundColor(true);
		this.multiRenderer_scatter.setBackgroundColor(0xef5b9c);
		//this.multiRenderer.setMargins(new int[] { 20, 20, 50, 10 });//设置图表的外边框(上/左/下/右)
		this.multiRenderer_scatter.setMarginsColor(0xef5b9c);
		
		//this.multiRenderer.setBackgroundColor(Color.BLACK);
		this.multiRenderer_scatter.setZoomButtonsVisible(true);
		for (int i = 0; i < xLabel.length; i++) {
			//���X���ǩ
			this.multiRenderer_scatter.addXTextLabel(i + 1, this.xLabel[i]);
		}
		
		this.multiRenderer_scatter.addSeriesRenderer(renderer);

	}

}


