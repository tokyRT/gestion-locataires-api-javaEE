package com.location.models;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;

import com.location.database.MysqlConnect;

public class Statistic {
	private int appartements;
	private int locataires;
	private int caTotal = 0;
	private ArrayList<Ca> caParClient = new ArrayList<Ca>();
//	private 
	
	public Statistic() {
		try {
			Statement stmt = MysqlConnect.getInstance().createStatement();
			String query = "SELECT COUNT(id) AS appartements FROM appartements";
			ResultSet res = stmt.executeQuery(query);
			res.next();
			appartements = res.getInt("appartements");
			
			query = "SELECT COUNT(id) AS locataires FROM locataires";
			res = stmt.executeQuery(query);
			res.next();
			locataires = res.getInt("locataires");

			query = "SELECT locataire_id, SUM(nbr_mois * loyer) AS ca FROM louer GROUP BY locataire_id";
			res = stmt.executeQuery(query);
			while(res.next()) {
				Ca ca = new Ca(res.getInt("locataire_id"), res.getInt("ca"));
				caTotal += ca.getCa();
				caParClient.add(ca);
			}
			
		} catch(Exception e) {e.printStackTrace();}
	}

}
