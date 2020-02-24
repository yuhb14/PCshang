package com.PCshang.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JOptionPane;

import org.jfree.data.category.DefaultCategoryDataset;

import com.PCshang.util.ByteUtil;
import com.PCshang.util.RxThread;
import com.PCshang.util.SerialPortUtil;
import com.PCshang.util.ShowUtil;
import com.PCshang.view.MainFrame;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

public class OpenSerialPort implements ActionListener {

	public boolean serialPort_state;
	MainFrame mainfrm;
	
	CommPort commPort;
	public SerialPort serialPort;

	public OpenSerialPort(MainFrame mainfrm) {
		this.serialPort_state = true;
		this.mainfrm = mainfrm;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (serialPort_state && mainfrm.btn_openSerialPort.getText().equals("连接串口")) {
			try {
				
				openSerialPort();
			} catch (Exception e) {
			}
		} else {
			closeSerialPort();
		}
		serialPort_state = !serialPort_state;
	}

	private void openSerialPort( ) throws Exception {
		// 获取串口名称
		String commName = getCOMNumber();
		int baudrate = Integer.parseInt(getBaudRate());
		try {
			// 通过端口名得到其端口标识符对象的引用
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(commName);
			if (portIdentifier.isCurrentlyOwned())// 调用端口标识符对象的判断端口是否被占用方法
			{
				ShowUtil.infoShow(commName + "被占用");
			} else// 如果端口未被占用的话
			{
				// 打开端口，并给端口名字和一个timeout（打开操作的超时时间）
				commPort = portIdentifier.open(this.getClass().getName(), 2000);
				if (commPort instanceof SerialPort)// 判断这个端口是不是串口
				{
					// 串口的使用方法：
					// 首先找到这个串口，给它配置相应的波特率，数据位，停止位和检验即可
					serialPort = (SerialPort) commPort;// 类型转换为串口类
					serialPort.setSerialPortParams(baudrate, // 设置波特率
							8, // 设置八位数据，一共可以是5678
							1, // 停止位可以是1，1.5,2
							0);// 校验位0  
					
					serialPort.toString();
				    OutputStream out = serialPort.getOutputStream();//
		
				    mainfrm.out=out;
				    
				    mainfrm.startPlot =true;
				    
				    mainfrm.rxthread = new RxThread(serialPort, mainfrm);
				    
			//	    mainfrm.stopPlot=false;
				    
					//(new Thread(mainfrm.rtcp) ).start();//这两个进程开始的顺序很重要
				   mainfrm.chartthread=new Thread(mainfrm.rtcp);
				   mainfrm.chartthread.start();
				    mainfrm.rxthread.start();	//这两个进程开始的顺序很重要
				    
					// 添加串口监听
					SerialPortUtil.addListener(serialPort, new SerialPortUtil.DataAvailableListener() {						
						@Override
						public void dataAvailable() {							
							// 连接的时候开的是读线程，写线程是在点击发送之后再开 //单开接收线程								
						//	mainfrm.rxthread.run();	
						}						
					}
					);
					
				} else {
					ShowUtil.infoShow(commName + "不是串口");
				}
			}
			if (serialPort != null) {
				mainfrm.btn_openSerialPort.setText("断开串口");
				mainfrm.btn_openSerialPort.setBackground(Color.RED);
			}
			
		} catch (PortInUseException e) {
			JOptionPane.showMessageDialog(null, "连接失败");
			return;
		}
	}
	
	private void closeSerialPort() {
		
		mainfrm.startPlot = false;
		
		mainfrm.rxthread.stopRxThread = true;
		
		// 这个很重要，当点完连接再点关闭，再连接会卡住，保留这句话不然线程会卡住
		// mainfrm.rxthread.interrupt();
		while (mainfrm.rxthread.getState() != Thread.State.TERMINATED);
		while(mainfrm.stopDbUtil ==false);
		serialPort.close();
		
		mainfrm.btn_openSerialPort.setBackground(Color.GREEN);		
		mainfrm.btn_openSerialPort.setText("连接串口");
		
		mainfrm.btn_stopUptate.setText("暂停更新");
		mainfrm.btn_stopUptate.setBackground(Color.YELLOW);
	}

	
	
	public String getCOMNumber() {
		return (String) mainfrm.cbB_serialPortNum.getSelectedItem();
	}

	public String getBaudRate() {
		return (String) mainfrm.cbB_baudRate.getSelectedItem();
	}
	/*
	 * 这里一直抛异常，类型转换错误 
	 * public Integer getBaudRate(){ return (Integer)
	 * mainfrm.cbB_baudRate.getSelectedItem(); }
	 */

}
