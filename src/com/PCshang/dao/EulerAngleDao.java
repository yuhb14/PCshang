package com.PCshang.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.PCshang.model.EulerAngle;
import com.PCshang.util.StringUtil;




public class EulerAngleDao {
/**
 * 增
 * @param con
 * @param eulerangle
 * @return
 * @throws Exception
 */
	public static int add(Connection con, EulerAngle eulerangle)throws Exception {
		String sql="insert into t_eulerangle values(null,?,?,?,?)";//第一个是主键，自增的填null就行，后面的是插入的数值
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, eulerangle.getTime_log());
		pstmt.setFloat(2, eulerangle.getRoll());
		pstmt.setFloat(3, eulerangle.getYaw());
		pstmt.setFloat(4, eulerangle.getPitch());
	
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
		String sql="delete from t_eulerangle where id=?";
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
	public int update(Connection con,EulerAngle eulerangle)throws Exception{
		String sql="update t_eulerangle set time_log=?,roll=?,yaw=?,pitch=? where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1,eulerangle.getTime_log());
		pstmt.setFloat(2,eulerangle.getRoll());
		pstmt.setFloat(3,eulerangle.getYaw());
		pstmt.setFloat(4,eulerangle.getPitch());
		pstmt.setInt(7,eulerangle.getId());
		return pstmt.executeUpdate();
	}
	
	/**
	 *查询
	 * @return
	 * @throws Exception
	 */
	public ResultSet list(Connection con,EulerAngle eulerangle)throws Exception{
		StringBuffer sb=new StringBuffer("select * from t_eulerangle angle,t_motorvelocity velocity where angle.time_log=velocity.time_log");//����ƴ��
		if(StringUtil.isNotEmpty(eulerangle.getTime_log())){	
			sb.append(" and eulerangle.roll like '%"+eulerangle.getTime_log()+"%'");
		}
		
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		return pstmt.executeQuery();	
		
	}

	public static int deleteAll(Connection con) throws Exception {
	
			String sql="delete from t_eulerangle";//不加where就是删除表中全部数据，但不删除表

			PreparedStatement pstmt=con.prepareStatement(sql);
			//pstmt.setString();
			return pstmt.executeUpdate();

	
		
	}
	
}
