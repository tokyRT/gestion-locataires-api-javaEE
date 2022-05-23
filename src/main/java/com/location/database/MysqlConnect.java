package com.location.database;

import java.sql.*;

public class MysqlConnect {
	private static String url = "jdbc:mysql://localhost:3306/locataires";
	private static String user = "root";
	private static String passwd = "";
	private static Connection connect;
	

	public static Connection getInstance() {
		if(connect == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connect = DriverManager.getConnection(url, user, passwd);
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return connect;
	}
}
