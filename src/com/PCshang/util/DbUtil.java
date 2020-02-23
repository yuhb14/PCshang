package com.PCshang.util;

import java.sql.Connection;
import java.sql.DriverManager;



public class DbUtil {
	private static String dbUrl="jdbc:mysql://localhost:3306/pc_shang";//数据库关系表地址？数据库应该怎么建立呢？ַ
	private static String dbUserName="root";//数据库用户名，以根目录形式进入数据库
	private static String dbPassword="123456";//数据库密码
	private static String jdbcName="com.mysql.jdbc.Driver";//jdbc驱动名字？
/**
 * 建立数据库连接函数，固定写法
 * @return
 * @throws Exception
 */
	public static Connection getCon()throws Exception{
		Class.forName(jdbcName);
		Connection con=DriverManager.getConnection(dbUrl,dbUserName, dbPassword);
		return con;
	}
	/**
	 * 关闭数据库
	 * @param con
	 * @throws Exception
	 */
	public static void closeCon(Connection con)throws Exception{
		if(con!=null){
			con.close();	
		}	
	}
	public static void main(String[] args) {
		   DbUtil dbUtil= new DbUtil();
		  try{		  
			  dbUtil.getCon();		  
			  System.out.println("数据库建立连接");
		  }catch(Exception e){
			  e.printStackTrace();
			  System.out.println("数据库建立失败");	  
		  } 	   
	}
}
