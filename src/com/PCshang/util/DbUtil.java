package com.PCshang.util;

import java.sql.Connection;
import java.sql.DriverManager;
 
public class DbUtil {
	private static String dbUrl = "jdbc:mysql://localhost:3306/pcshang";// 连接到pcshang这个数据库，连接方式是jdbc:mysql://主机名:端口号/数据库名
	private static String dbUserName = "root";// 数据库用户名，以根目录形式进入数据库
	private static String dbPassword = "123456";// 数据库密码
	private static String jdbcName = "com.mysql.jdbc.Driver";// jdbc驱动名字，MySQL的必备格式

	/**
	 * 建立数据库连接函数，获取数据库连接 
	 * @return
	 * @throws Exception
	 */
	public static Connection getCon() throws Exception {
		Class.forName(jdbcName); // 动态加载类，通过名字建立连接到数据库
		Connection con = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
		return con;
	}

	/**
	 * 关闭数据库连接，防止空指针异常，一定要关闭，占很大内存
	 * @param con
	 * @throws Exception
	 */
	public static void closeCon(Connection con) throws Exception {
		if (con != null) {
			con.close();
		}
	}

	
	public static void main(String[] args) {
		DbUtil dbUtil = new DbUtil();
		try {
			dbUtil.getCon();
			System.out.println("数据库连接成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("数据库连接失败");
		}
	}
	
}
