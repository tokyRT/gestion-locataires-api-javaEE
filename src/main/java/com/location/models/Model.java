package com.location.models;

import java.sql.*;
import com.location.database.MysqlConnect;

public class Model {
	
//	public void all() {
//		try {
//			PreparedStatement prepare = MysqlConnect.getInstance().prepareStatement("select * from appartements where id = ?");
//			Statement stmt = MysqlConnect.getInstance().createStatement();
//			ResultSet rs = stmt.executeQuery("select * from appartements");
//			ResultSetMetaData rsMeta = rs.getMetaData();
//			
//			for (int i = 1; i <= rsMeta.getColumnCount(); i++) {
//				System.out.print(rsMeta.getColumnName(i) + "\t");
//			}
//			System.out.println("");
//			
//			while(rs.next()) {
//				System.out.println(rs.getInt("id")+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getInt(4));
//			}
//			
//			rs.absolute(1);
//			
//			System.out.println(rs.getInt(1)+" | "+rs.getString(2)+" | "+rs.getString(3)+" | "+rs.getInt(4));
//			
//		} catch(Exception e) {e.printStackTrace();}
//	}
}
