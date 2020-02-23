
package com.PCshang.view;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.jfree.chart.ChartFrame;
import org.jfree.data.category.DefaultCategoryDataset;

import com.PCshang.controller.ClearData;
import com.PCshang.controller.OpenSerialPort;
import com.PCshang.controller.SaveData;
import com.PCshang.controller.SendData;
import com.PCshang.controller.StopUpdate;
import com.PCshang.util.RealChartUtil;

import com.PCshang.util.RxThread;
import com.PCshang.util.TxThread;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import javax.swing.SpringLayout;
import javax.swing.Box;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.OutputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.DropMode;
import javax.swing.JCheckBox;
import java.awt.Canvas;
import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;

public class MainFrame extends JFrame {

	public JPanel contentPane;
	
	public JComboBox cbB_serialPortNum;
	public JComboBox cbB_baudRate;
	
	public OutputStream out;
	public RxThread rxthread;
	public TxThread txthread;   
	public Thread chartthread;

    
	public JCheckBox cb_HexReceive;
	public JCheckBox cb_HexSend;
	
	public JCheckBox cb_timerSend;
	public JTextField tf_time;
	public JTextArea ta_sendArea;
	
	public JButton btn_openSerialPort;
	public JButton btn_saveData;
	public JButton btn_stopUptate;
	public JButton btn_clearData;
	public JButton btn_sendData;
	
	
	//存放数据点的集合
	public DefaultCategoryDataset dataSet;
	private JPanel panel_taR;
	private JScrollPane scrollPane_1;
	public JTextArea ta_receiveArea;
	
	/**
	 * 绘图进程JChart
	 */
	 public RealChartUtil rtcp; 
	 public volatile  boolean startPlot;
	
	/**
	 * 初始化，给曲线线程传入参数
	 */
	float[] f = {0.0f};
	float x_site=0.0f;

	
	
	
	private JPanel panel_chart;


	

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	}


	public MainFrame() {

        //获取桌面大小
       // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
		setTitle("PCshang");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 /*  
		int mainfrm_width = 700;
		int mainfrm_height = 624;
		int mainfrm_loacation_start_x = (int)((screenSize.getWidth()-mainfrm_width)/2);  //获取起始坐标
	    int mainfrm_loacation_start_y = (int)((screenSize.getHeight()-mainfrm_height)/2);
	 */
	    
		setBounds(333,72,1100,630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel_receive = new JPanel();
		
		JPanel panel_send = new JPanel();
		
		panel_taR = new JPanel();
		
		scrollPane_1 = new JScrollPane();
		GroupLayout gl_panel_taR = new GroupLayout(panel_taR);
		gl_panel_taR.setHorizontalGroup(
			gl_panel_taR.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_taR.createSequentialGroup()
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_taR.setVerticalGroup(
			gl_panel_taR.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_taR.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 341, GroupLayout.PREFERRED_SIZE))
		);
		
		ta_receiveArea = new JTextArea();
		scrollPane_1.setViewportView(ta_receiveArea);
		panel_taR.setLayout(gl_panel_taR);
		
		ta_sendArea = new JTextArea();
		
		btn_sendData = new JButton("发送");
		btn_sendData.addActionListener(
				new SendData(this)
				);
		
		cb_timerSend = new JCheckBox("定时发送");
		
		tf_time = new JTextField();
		tf_time.setText("1000");
		tf_time.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("ms");
		
		cb_HexSend = new JCheckBox("十六进制发送");
		GroupLayout gl_panel_send = new GroupLayout(panel_send);
		gl_panel_send.setHorizontalGroup(
			gl_panel_send.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_send.createSequentialGroup()
					.addGroup(gl_panel_send.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_send.createSequentialGroup()
							.addGap(22)
							.addComponent(ta_sendArea, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_send.createSequentialGroup()
							.addGap(49)
							.addComponent(cb_HexSend))
						.addGroup(gl_panel_send.createSequentialGroup()
							.addGap(14)
							.addComponent(cb_timerSend)
							.addGap(6)
							.addComponent(tf_time, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_send.createSequentialGroup()
							.addGap(58)
							.addComponent(btn_sendData)))
					.addContainerGap(16, Short.MAX_VALUE))
		);
		gl_panel_send.setVerticalGroup(
			gl_panel_send.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_send.createSequentialGroup()
					.addGap(10)
					.addComponent(ta_sendArea, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addGap(2)
					.addComponent(cb_HexSend)
					.addGap(1)
					.addGroup(gl_panel_send.createParallelGroup(Alignment.LEADING)
						.addComponent(cb_timerSend)
						.addGroup(gl_panel_send.createSequentialGroup()
							.addGap(1)
							.addGroup(gl_panel_send.createParallelGroup(Alignment.BASELINE)
								.addComponent(tf_time, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_2))))
					.addGap(7)
					.addComponent(btn_sendData))
		);
		panel_send.setLayout(gl_panel_send);
		
		JLabel lblNewLabel = new JLabel("串口号");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 18));
		
		cbB_serialPortNum = new JComboBox();
		cbB_serialPortNum.setMaximumRowCount(4);
		cbB_serialPortNum.setModel(new DefaultComboBoxModel(new String[] {"COM1", "COM2", "COM3", "COM4"}));
		cbB_serialPortNum.setSelectedIndex(0);
		cbB_serialPortNum.setFont(new Font("宋体", Font.PLAIN, 18));
		
		JLabel lblNewLabel_1 = new JLabel("波特率");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 18));
		
		cbB_baudRate = new JComboBox();
		cbB_serialPortNum.setMaximumRowCount(2);
		cbB_baudRate.setModel(new DefaultComboBoxModel(new String[] {"115200", "9600"}));
		cbB_baudRate.setSelectedIndex(0);
		cbB_baudRate.setMaximumRowCount(2);
		cbB_baudRate.setFont(new Font("宋体", Font.PLAIN, 18));
		
		btn_openSerialPort = new JButton("连接串口");
		btn_openSerialPort.setBackground(Color.GREEN);
		btn_openSerialPort.addActionListener(
				//打开串口按钮
				new OpenSerialPort(this)
				);
		
		
		cb_HexReceive = new JCheckBox("十六进制接收");
		
		btn_saveData = new JButton("保存数据");
		btn_saveData.addActionListener(		
				//保存数据
				new SaveData(this)
				);
		
		btn_stopUptate = new JButton("暂停更新");
		btn_stopUptate.setBackground(Color.YELLOW);
		btn_stopUptate.addActionListener(
				//停止更新
				new StopUpdate(this)
			);
		
		btn_clearData = new JButton("清空数据");
		btn_clearData.addActionListener(
				//清楚数据
				new ClearData(this)
				);
		GroupLayout gl_panel_receive = new GroupLayout(panel_receive);
		gl_panel_receive.setHorizontalGroup(
			gl_panel_receive.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_receive.createSequentialGroup()
					.addGroup(gl_panel_receive.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_receive.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_receive.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel)
								.addComponent(lblNewLabel_1))
							.addGap(18)
							.addGroup(gl_panel_receive.createParallelGroup(Alignment.LEADING)
								.addComponent(cbB_serialPortNum, 0, 91, Short.MAX_VALUE)
								.addComponent(cbB_baudRate, 0, 91, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_panel_receive.createSequentialGroup()
							.addGap(31)
							.addGroup(gl_panel_receive.createParallelGroup(Alignment.TRAILING)
								.addComponent(btn_openSerialPort, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_receive.createParallelGroup(Alignment.LEADING)
									.addComponent(btn_saveData)
									.addComponent(cb_HexReceive)
									.addComponent(btn_stopUptate)
									.addComponent(btn_clearData)))))
					.addGap(10))
		);
		gl_panel_receive.setVerticalGroup(
			gl_panel_receive.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_receive.createSequentialGroup()
					.addGap(40)
					.addGroup(gl_panel_receive.createParallelGroup(Alignment.LEADING)
						.addComponent(cbB_serialPortNum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_receive.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbB_baudRate, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(btn_openSerialPort, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(cb_HexReceive)
					.addGap(18)
					.addComponent(btn_saveData)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btn_stopUptate)
					.addGap(18)
					.addComponent(btn_clearData)
					.addContainerGap(42, Short.MAX_VALUE))
		);
		panel_receive.setLayout(gl_panel_receive);
		
		
		
		panel_chart = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(21)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_receive, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_send, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(panel_chart, GroupLayout.PREFERRED_SIZE, 635, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addComponent(panel_taR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(42))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(10, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel_taR, GroupLayout.PREFERRED_SIZE, 340, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel_receive, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(panel_send, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel_chart, GroupLayout.PREFERRED_SIZE, 499, GroupLayout.PREFERRED_SIZE)
							.addGap(30)))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		
		 rtcp = new RealChartUtil(this,"", "", "",f,x_site);
		 rtcp.setBackground(Color.ORANGE);
	
		
		
		GroupLayout gl_panel_chart = new GroupLayout(panel_chart);
		gl_panel_chart.setHorizontalGroup(
			gl_panel_chart.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_chart.createSequentialGroup()
					.addComponent(rtcp, GroupLayout.PREFERRED_SIZE, 633, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_chart.setVerticalGroup(
			gl_panel_chart.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_chart.createSequentialGroup()
					.addComponent(rtcp, GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_chart.setLayout(gl_panel_chart);
		
		this.startPlot=false;
	
		
		//(new Thread(rtcp) ).start();
		
		contentPane.setLayout(gl_contentPane);
	}
}
