
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
import com.PCshang.controller.ClearDb;
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
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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

	public volatile  boolean readdataflag;
	public volatile  boolean stopDbUtil;
	public  JTable t_dbrealtime;
	public  JTable t_dbselecttime;
	private JPanel panel_other;
	private JLabel lblNewLabel_5;
	private JTextField t_voltage;


	

	
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
	    
		setBounds(333,72,1115,655);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel_receive = new JPanel();
		panel_receive.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u63A5\u6536\u533A", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0))));
		
		JPanel panel_send = new JPanel();
		panel_send.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u53D1\u9001\u533A", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0))));
		
		panel_taR = new JPanel();
		panel_taR.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u6570\u636E\u7F13\u5B58\u533A", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0))));
		panel_taR.setToolTipText("");
		
		scrollPane_1 = new JScrollPane();
		GroupLayout gl_panel_taR = new GroupLayout(panel_taR);
		gl_panel_taR.setHorizontalGroup(
			gl_panel_taR.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
		);
		gl_panel_taR.setVerticalGroup(
			gl_panel_taR.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_taR.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
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
			gl_panel_send.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel_send.createSequentialGroup()
					.addGap(19)
					.addComponent(cb_timerSend)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tf_time, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(25, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, gl_panel_send.createSequentialGroup()
					.addGap(25)
					.addComponent(ta_sendArea, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(28, Short.MAX_VALUE))
				.addGroup(gl_panel_send.createSequentialGroup()
					.addContainerGap(51, Short.MAX_VALUE)
					.addComponent(cb_HexSend)
					.addGap(45))
				.addGroup(Alignment.LEADING, gl_panel_send.createSequentialGroup()
					.addGap(67)
					.addComponent(btn_sendData)
					.addContainerGap(69, Short.MAX_VALUE))
		);
		gl_panel_send.setVerticalGroup(
			gl_panel_send.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_send.createSequentialGroup()
					.addContainerGap()
					.addComponent(ta_sendArea, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(cb_HexSend)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_send.createParallelGroup(Alignment.BASELINE)
						.addComponent(cb_timerSend)
						.addComponent(tf_time, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_sendData)
					.addGap(29))
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
		
		btn_saveData = new JButton("保存EXCEL");
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
		
		JButton btn_TXTData = new JButton("保存TXT");
		GroupLayout gl_panel_receive = new GroupLayout(panel_receive);
		gl_panel_receive.setHorizontalGroup(
			gl_panel_receive.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_receive.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addGap(18)
					.addComponent(cbB_serialPortNum, 0, 101, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_panel_receive.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_1)
					.addGap(18)
					.addComponent(cbB_baudRate, 0, 101, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_panel_receive.createSequentialGroup()
					.addGap(41)
					.addGroup(gl_panel_receive.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_receive.createSequentialGroup()
							.addComponent(cb_HexReceive)
							.addContainerGap(55, Short.MAX_VALUE))
						.addGroup(gl_panel_receive.createSequentialGroup()
							.addGroup(gl_panel_receive.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btn_TXTData, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btn_clearData, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btn_stopUptate, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btn_openSerialPort, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
								.addComponent(btn_saveData, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
							.addContainerGap())))
		);
		gl_panel_receive.setVerticalGroup(
			gl_panel_receive.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_receive.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_receive.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(cbB_serialPortNum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_receive.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbB_baudRate, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btn_openSerialPort, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(cb_HexReceive)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btn_stopUptate)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btn_clearData)
					.addGap(18)
					.addComponent(btn_saveData)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btn_TXTData)
					.addContainerGap(22, Short.MAX_VALUE))
		);
		panel_receive.setLayout(gl_panel_receive);
		
		
		
		panel_chart = new JPanel();
		panel_chart.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u66F2\u7EBF\u8868", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0))));
		
		JPanel panel_db = new JPanel();
		panel_db.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u6570\u636E\u5E93", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0))));
		
		panel_other = new JPanel();
		panel_other.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u5176\u4ED6", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0))));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_send, 0, 0, Short.MAX_VALUE)
						.addComponent(panel_receive, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_chart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_taR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_db, GroupLayout.PREFERRED_SIZE, 176, Short.MAX_VALUE)
						.addComponent(panel_other, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel_chart, GroupLayout.PREFERRED_SIZE, 499, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_taR, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel_receive, GroupLayout.PREFERRED_SIZE, 329, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_send, GroupLayout.PREFERRED_SIZE, 254, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel_db, GroupLayout.PREFERRED_SIZE, 399, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_other, GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)))
					.addContainerGap())
		);
		
		lblNewLabel_5 = new JLabel("电压(V)");
		
		t_voltage = new JTextField();
		t_voltage.setColumns(10);
		GroupLayout gl_panel_other = new GroupLayout(panel_other);
		gl_panel_other.setHorizontalGroup(
			gl_panel_other.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_other.createSequentialGroup()
					.addComponent(lblNewLabel_5)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(t_voltage, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_other.setVerticalGroup(
			gl_panel_other.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_other.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_other.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_5)
						.addComponent(t_voltage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(133, Short.MAX_VALUE))
		);
		panel_other.setLayout(gl_panel_other);
		
		JLabel lblNewLabel_3 = new JLabel("实时量");
		
		t_dbrealtime = new JTable();
		t_dbrealtime.setModel(new DefaultTableModel(
			new Object[][] {
				{"\u6570\u636E\u91CF", null},
				{"roll", null},
				{"yaw", null},
				{"pitch", null},
				{"motorA", null},
				{"motorB", null},
				{"motorC", null},
			},
			new String[] {
				"\u9009\u9879", "\u6570\u503C"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		JLabel lblNewLabel_4 = new JLabel("查询量");
		
		t_dbselecttime = new JTable();
		t_dbselecttime.setModel(new DefaultTableModel(
			new Object[][] {
				{"\u67E5\u8BE2time_log", null},
				{"roll", null},
				{"yaw", null},
				{"pitch", null},
				{"motorA", null},
				{"motorB", null},
				{"motorC", null},
			},
			new String[] {
				"\u9009\u9879", "\u6570\u503C"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		JButton btn_selectDb = new JButton("查询数据");
		btn_selectDb.addActionListener(
				
				new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		
		JButton btn_clearDb = new JButton("清空数据库");
		btn_clearDb.addActionListener(
				//清楚数据库
				new ClearDb(this)
				
				);
		
		GroupLayout gl_panel_db = new GroupLayout(panel_db);
		gl_panel_db.setHorizontalGroup(
			gl_panel_db.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_db.createSequentialGroup()
					.addGroup(gl_panel_db.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_3)
						.addComponent(t_dbrealtime, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_4)
						.addComponent(t_dbselecttime, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
						.addGroup(gl_panel_db.createSequentialGroup()
							.addGap(39)
							.addGroup(gl_panel_db.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btn_clearDb, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btn_selectDb, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_panel_db.setVerticalGroup(
			gl_panel_db.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_db.createSequentialGroup()
					.addComponent(lblNewLabel_3)
					.addGap(2)
					.addComponent(t_dbrealtime, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_4)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(t_dbselecttime, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_selectDb)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btn_clearDb)
					.addContainerGap(29, Short.MAX_VALUE))
		);
		panel_db.setLayout(gl_panel_db);
		
		//表格部件
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
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(rtcp, GroupLayout.PREFERRED_SIZE, 467, GroupLayout.PREFERRED_SIZE))
		);
		panel_chart.setLayout(gl_panel_chart);
		
		
		this.startPlot=false;		
		//(new Thread(rtcp) ).start();
		
		contentPane.setLayout(gl_contentPane);
	}
}
