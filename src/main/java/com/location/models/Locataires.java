package com.location.models;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.location.database.MysqlConnect;

public class Locataires {
	String table = "locataires";
	
	public ArrayList<Locataire> all(){
		ArrayList<Locataire> all = new ArrayList<Locataire>();
		
		try {
			Statement stmt = MysqlConnect.getInstance().createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM "+table+" ORDER BY id DESC");
			while(res.next()) {
				Locataire loc = new Locataire(res.getString("nom"), res.getString("adresse"));
				loc.setId(res.getInt("id"));
				all.add(loc);
			}
		} catch(Exception e) {e.printStackTrace();}
		
		return all;
	}
	
	public Locataire find(int id) {
		Locataire loc = null;
		try {
			Statement stmt = MysqlConnect.getInstance().createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM "+table+" WHERE id = "+id);
			res.next();
			loc = new Locataire(res.getString("nom"), res.getString("adresse"));
			loc.setId(res.getInt("id"));
		} catch(Exception e) {e.printStackTrace();}
		
		return loc;
	}
	
	public Locataire last() {
		Locataire loc = null;
		try {
			Statement stmt = MysqlConnect.getInstance().createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM "+table+" ORDER BY id DESC LIMIT 1");
			res.next();
			loc = new Locataire(res.getString("nom"), res.getString("adresse"));
			loc.setId(res.getInt("id"));
		} catch(Exception e) {e.printStackTrace();}
		
		return loc;
	}
}
