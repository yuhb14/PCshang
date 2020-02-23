package com.PCshang.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;

import com.PCshang.util.RealChartUtil;
import com.PCshang.view.MainFrame;

public class ClearData implements ActionListener{

	MainFrame mainfrm;
	float[] m;
	
	public ClearData(MainFrame mainfrm) {
		this.mainfrm = mainfrm;
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		//clearData();
	}

	/**
	 * 清除数据：这个还没解决，改天再说
	 * 1.将屏幕上的数据清空
	 * 2.但进程不能停
	 * 3.屏幕上数据清空后还能继续接受新的数据
	 */
	private void clearData() {
		
			mainfrm.btn_clearData.setBackground(Color.RED);
			mainfrm.startPlot = false;		
			
	
		
			
			for (int i = 0; i <100; i++) {
				i++;
			}	
			int len = mainfrm.rtcp.getF().length;
			m = new float[len];
			for (int i = 0; i <len; i++) {
				
				mainfrm.rtcp.ts[i].clear();
			
				
				//m[i] = 0.0f;
			}	
		for (int i = 0; i <len; i++) {
				
				
				mainfrm.rtcp.ts[i]= new TimeSeries("" + i, Millisecond.class);		
				mainfrm.rtcp.getDataset().addSeries(mainfrm.rtcp.ts[i]);
				
				//m[i] = 0.0f;
			}	
			
			
		//	mainfrm.rtcp.setF(m);
		/*	try {
				//mainfrm.chartthread.sleep(1);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	/*		try {
				
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}*/
			
			mainfrm.startPlot = true;
			
		
		
			
			mainfrm.btn_clearData.setBackground(Color.WHITE);
	
	}

}
