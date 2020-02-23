package com.PCshang.util;


import java.sql.Connection;

import com.PCshang.dao.EulerAngleDao;
import com.PCshang.dao.MotorVelocityDao;
import com.PCshang.model.EulerAngle;
import com.PCshang.model.MotorVelocity;

public class InsertDbUtil {

	public static void insertDbEulerUtil(float[] f) {
		
		if (f.length >= 3) {
			EulerAngle euler = new EulerAngle(f[0], f[1], f[2]);
			Connection con = null;
			try {
				con = DbUtil.getCon();
				int n = EulerAngleDao.add(con, euler);
				if (n == 1) {
					// JOptionPane.showMessageDialog(null, "��ӳɹ�");

				} else {
					// JOptionPane.showMessageDialog(null, "���ʧ��");
				}
			} catch (Exception e) {
				e.printStackTrace();
				// JOptionPane.showMessageDialog(null, "���ʧ��");
			} finally {
				try {
					DbUtil.closeCon(con);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	public static void insertDbVelocityUtil(float[] f) {
		if (f.length >= 6) {
			MotorVelocity velocity = new MotorVelocity(f[3], f[4], f[5]);
			Connection con = null;
			try {
				con = DbUtil.getCon();
				int n = MotorVelocityDao.add(con, velocity);
				if (n == 1) {
					// JOptionPane.showMessageDialog(null, "��ӳɹ�");

				} else {
					// JOptionPane.showMessageDialog(null, "���ʧ��");
				}
			} catch (Exception e) {
				e.printStackTrace();
				// JOptionPane.showMessageDialog(null, "���ʧ��");
			} finally {
				try {
					DbUtil.closeCon(con);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
}
