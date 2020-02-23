package com.PCshang.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

import com.PCshang.util.RxThread;
import com.PCshang.view.MainFrame;


public class StopUpdate implements ActionListener {
	MainFrame mainfrm;
	public boolean update_state;
	
	public StopUpdate(MainFrame mainfrm) {
		this.update_state = true;
		this.mainfrm = mainfrm;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(!update_state) {
			
			mainfrm.startPlot=true;
			mainfrm.btn_stopUptate.setText("暂停更新");
			mainfrm.btn_stopUptate.setBackground(Color.YELLOW);
		}else {
			mainfrm.startPlot=false;//进程还在，只是不再画图
			mainfrm.btn_stopUptate.setText("开始更新");
			
			mainfrm.btn_stopUptate.setBackground(Color.GREEN);
		}
		update_state = !update_state;
	}

	
}
