package com.PCshang.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JOptionPane;

import com.PCshang.dao.EulerAngleDao;
import com.PCshang.dao.MotorVelocityDao;
import com.PCshang.util.DbUtil;
import com.PCshang.view.MainFrame;

public class ClearDb implements ActionListener{

	MainFrame mainfrm;
	
	private static DbUtil dbUtil =new DbUtil();
	private static EulerAngleDao eulerangledao = new EulerAngleDao();
	private static MotorVelocityDao motoreulerdao = new MotorVelocityDao();

	
	public ClearDb(MainFrame mainfrm) {
		this.mainfrm = mainfrm;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		clearDb();
		
	}

	private void clearDb() {
		Connection con=null;
		try{
			con=dbUtil.getCon();
			int deleteNum1=EulerAngleDao.deleteAll(con);
			int deleteNum2=MotorVelocityDao.deleteAll(con);
			System.out.println(deleteNum1);//返回的删除的数目
			System.out.println(deleteNum1);
	
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("数据库连接失败");
			
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
