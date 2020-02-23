package com.PCshang.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import com.PCshang.model.MotorVelocity;
import com.PCshang.util.StringUtil;

public class MotorVelocityDao {
    
	/*
	**
	 * 增
	 * @param con
	 * @param eulerangle
	 * @return
	 * @throws Exception
	 */
		public static int add(Connection con, MotorVelocity motorvelocity)throws Exception {
			String sql="insert into t_motorvelocity values(null,?,?,?,?)";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, motorvelocity.getTime_log());
			pstmt.setFloat(2, motorvelocity.getV_motor_a());
			pstmt.setFloat(3, motorvelocity.getV_motor_b());
			pstmt.setFloat(4, motorvelocity.getV_motor_c());
		
			return pstmt.executeUpdate();	
		}
		
		/**
		 * 删
		 * @param con
		 * @param id
		 * @return
		 * @throws Exception
		 */
		public int delete(Connection con,String id)throws Exception {
			String sql="delete from t_motorvelocity where id=?";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			return pstmt.executeUpdate();
		}
		
		/**
		 * 改
		 * @param con
		 * @param id
		 * @return
		 * @throws Exception
		 */
		public int update(Connection con,MotorVelocity motorvelocity)throws Exception{
			String sql="update t_motorvelocity set time_log=?,v_motor_a=?,v_motor_b=?,v_motor_c=? where id=?";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1,motorvelocity.getTime_log());
			pstmt.setFloat(2,motorvelocity.getV_motor_a());
			pstmt.setFloat(3,motorvelocity.getV_motor_b());
			pstmt.setFloat(4,motorvelocity.getV_motor_c());
			pstmt.setInt(7,motorvelocity.getId());
			return pstmt.executeUpdate();
		}
		
		/**
		 *插
		 * @return
		 * @throws Exception
		 */
		public ResultSet list(Connection con,MotorVelocity motorvelocity)throws Exception{
			StringBuffer sb=new StringBuffer("select * from t_eulerangle angle,t_motorvelocity velocity where angle.time_log=velocity.time_log");//����ƴ��
			if(StringUtil.isNotEmpty(motorvelocity.getTime_log())){	
				sb.append(" and eulerangle.roll like '%"+motorvelocity.getTime_log()+"%'");
			}
			
			PreparedStatement pstmt=con.prepareStatement(sb.toString());
			return pstmt.executeQuery();	
		}
}
