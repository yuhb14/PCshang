package com.PCshang.util;


import java.sql.Connection;

import javax.swing.JOptionPane;

import com.PCshang.dao.EulerAngleDao;
import com.PCshang.dao.MotorVelocityDao;
import com.PCshang.model.EulerAngle;
import com.PCshang.model.MotorVelocity;
import com.PCshang.view.MainFrame;

public class InsertDbUtil {
	
	private static DbUtil dbUtil =new DbUtil();
	private static EulerAngleDao eulerangledao = new EulerAngleDao();
	private static MotorVelocityDao motoreulerdao = new MotorVelocityDao();

	public static MainFrame mainfrm;
	
	
	
	public InsertDbUtil(MainFrame mainfrm) {
		super();
		this.mainfrm = mainfrm;
	}



	public static void insertDbPCshangUtil(float[] f) {
		
		Connection con = null;
		
		mainfrm.stopDbUtil=false;
	
		
		if (f.length >= 3) {
			EulerAngle euler = new EulerAngle(f[0], f[1], f[2]);
			
			try {
				con = DbUtil.getCon();
				int n = eulerangledao .add(con, euler);  //会得到一个返回值，返回值为1就是添加成功
				System.out.println("连接上数据库");
				if(f.length >= 6 ) {
					MotorVelocity velocity = new MotorVelocity(f[3], f[4], f[5]);
					int m = motoreulerdao.add(con, velocity);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					dbUtil.closeCon(con);
					mainfrm.stopDbUtil = true;
					System.out.println("关闭数据库");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	
		

	
}
