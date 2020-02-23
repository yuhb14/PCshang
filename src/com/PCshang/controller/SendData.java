package com.PCshang.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.PCshang.util.TxThread;

import com.PCshang.view.MainFrame;

public class SendData implements ActionListener{

	MainFrame mainfrm;
		
	public SendData(MainFrame mainfrm) {
		this.mainfrm = mainfrm;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(mainfrm.btn_sendData.getText().equals("停止")){
			mainfrm.txthread.stop = false;
			mainfrm.btn_sendData.setText("发送");
	        return;
		}
		mainfrm.txthread = new TxThread(mainfrm.out,mainfrm);
		mainfrm.txthread.start();	   
	}


	
}
