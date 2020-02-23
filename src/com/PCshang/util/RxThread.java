package com.PCshang.util;

import java.io.IOException;
import java.io.InputStream;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;


import com.PCshang.view.MainFrame;

import gnu.io.SerialPort;

public class RxThread extends Thread {

	MainFrame mainfrm;

	SerialPort serialport;

	
	InputStream in;
	

	// 这里用了一个锁，可能就是防止多线程导致卡住的原因，看一下这个什么作用
	public volatile boolean stopRxThread;
	
	public RxThread(SerialPort serialport, MainFrame mainfrm) {

		this.mainfrm = mainfrm;
		this.serialport = serialport;
		this.stopRxThread = false;
		
	}

	@Override
	public void run() {
		
		
		Decoder();

	}

	/**
	 * 发送的数据帧头是'$'，中间是数据，帧尾是数组长度，一个数据的 AA 00 00 00 00 35 下位机发送的char数组
	 * 
	 * @throws InterruptedException
	 */
	
	public void Decoder() {

		byte[] bytes = null;
		int len = 1;
		byte[] buffer = new byte[len];
		int spdatalength = 0;		
		int i=0;
		float j =0.0f;
		
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		 JFreeChart chart = null;
		//ChartFrame chartFrame=new ChartFrame(null,chart);
		// chartFrame.setVisible(true);
	
		 ChartFrame cartFrame = null;
		 
		 float f=0.0f;
		 /*
			JFrame frame = new JFrame("Test Chart");
			RealTimeChart rtcp = new RealTimeChart("Random Data", "随机数", "数值",f);
			frame.getContentPane().add(rtcp, new BorderLayout().CENTER);
			frame.pack();
			frame.setVisible(true);
			(new Thread(rtcp) ).start();
			frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent windowevent) {
					System.exit(0);
				}

			});
			*/
			
		try {

			while ((len) > -1) {

				len = this.serialport.getInputStream().read(buffer);
				if (stopRxThread) {// 退出接收循环
					return;
				}
				// 这个一段的数据发送完全，开始处理数据

				if (len == 0 && (bytes == null)) {
					continue;
				} else if (len == 0 && (bytes != null)) {
					// 操作存数据和绘图
					if (ByteUtil.byteToHex(bytes[bytes.length - 1]).equals("AB") &&ByteUtil.byteToHex(bytes[0]).equals("AA")
							&&(spdatalength == 6  || spdatalength == 10 || spdatalength == 14  || spdatalength == 18 || 
							spdatalength == 22 || spdatalength == 26 || spdatalength == 30  || spdatalength == 34  ||
							spdatalength == 38  || spdatalength == 42)) {
						 
						// f = new float[(spdatalength-2)/4];
											
						 float[] f1 = ByteUtil.byteToFloat(bytes, spdatalength);
						 
						 //绘制曲线在主界面
						 mainfrm.rtcp.setF(f1);
						 
						 //插入数据库
						InsertDbUtil.insertDbEulerUtil(f1);
						InsertDbUtil.insertDbVelocityUtil(f1);
	
		
					//	mainfrm.ta_receiveArea.append(ByteUtil.byteArrayToHexString(bytes));
						for(int ss=0;ss<((spdatalength-2)/4);ss++) {
							mainfrm.ta_receiveArea.append(Float.toString(f1[ss]));
						}
						bytes = null;
					}
					
					continue;
				} else {					
					i++;
					if (ByteUtil.byteToHex(buffer[0]).equals("AB")) {
						spdatalength = i;
						i=0;					
					}
					bytes = ByteUtil.concat(bytes, buffer);
				}
			}

		} catch (IOException e) {
			ShowUtil.infoShow("接收串口数据异常");
		}

	}



}
