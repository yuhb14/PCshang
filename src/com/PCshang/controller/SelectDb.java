package com.PCshang.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.PCshang.view.MainFrame;

public class SelectDb implements ActionListener {
	MainFrame mainfrm;
	
	
	public SelectDb(MainFrame mainfrm) {
		super();
		this.mainfrm = mainfrm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		selectDb();
	}

	private void selectDb() {
		
		
	}
}