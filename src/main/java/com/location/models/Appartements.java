package com.location.models;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

import com.location.database.MysqlConnect;

public class Appartements {
	String table = "appartements";
	//retrieve all appartements
	public ArrayList<Appartement> all() {
		ArrayList<Appartement> all = new ArrayList<Appartement>();
		
		try {
			Statement stmt = MysqlConnect.getInstance().createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM "+table+" ORDER BY id DESC");
			ResultSetMetaData  resMeta = res.getMetaData();
			
			while(res.next()) {
				Appartement ap = new Appartement(res.getString("designation"), res.getString("lieu"), res.getInt("loyer"));
				ap.setId(res.getInt("id"));
				ap.getCurrentLocataire();
				all.add(ap);
			}
			
		} catch(Exception e) {e.printStackTrace();}
		
		return all;
	}
	
//	retrieve one appartement by id
	public Appartement find(int id) {
		Appartement ap = null;
		try {
			Statement stmt = MysqlConnect.getInstance().createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM "+table+" WHERE id = "+id);
			res.next();
			ap = new Appartement(res.getString("designation"), res.getString("lieu"), res.getInt("loyer"));
			ap.setId(res.getInt("id"));
			ap.getCurrentLocataire();
			
		} catch(Exception e) {e.printStackTrace();}
		
		return ap;
	}
	
	public Appartement last() {
		Appartement ap = null;
		try {
			Statement stmt = MysqlConnect.getInstance().createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM "+table+" ORDER BY id DESC LIMIT 1");
			res.next();
			ap = new Appartement(res.getString("designation"), res.getString("lieu"), res.getInt("loyer"));
			ap.setId(res.getInt("id"));
			
		} catch(Exception e) {e.printStackTrace();}
		
		return ap;
	}
	
	public ArrayList<Appartement> getAvailable(String enterDate){
		//appartements deja occupe mais libre a la date d'entree du locataire
		ArrayList<Appartement> aps = new ArrayList<Appartement>();
		try {
			Statement stmt = MysqlConnect.getInstance().createStatement();
			String query = "SELECT *, DATE_ADD(date_entree, INTERVAL nbr_mois MONTH) AS date_fin, ap.id AS apid ";
			   	   query +="FROM louer l JOIN appartements ap ON l.appartement_id = ap.id ";
			       query +="WHERE DATE_ADD(date_entree, INTERVAL nbr_mois MONTH) < '"+enterDate+"' ";
			ResultSet res = stmt.executeQuery(query);
			while(res.next()) {
				Appartement ap = new Appartement(res.getString("designation"), res.getString("lieu"), res.getInt("loyer"));
				ap.setId(res.getInt("apid"));
				aps.add(ap);
			}
			
			
		} catch(Exception e) {e.printStackTrace();}
		
		return aps;
	}
	public ArrayList<Appartement> getAvailable(){
		//appartement libre jamais occupe
		ArrayList<Appartement> aps = new ArrayList<Appartement>();
		try {
			Statement stmt = MysqlConnect.getInstance().createStatement();
			String query = "SELECT * FROM appartements WHERE id NOT IN(SELECT appartement_id FROM louer) ORDER BY id DESC";
			System.out.println(query);
			ResultSet res = stmt.executeQuery(query);
			while(res.next()) {
				Appartement ap = new Appartement(res.getString("designation"), res.getString("lieu"), res.getInt("loyer"));
				ap.setId(res.getInt("id"));
				aps.add(ap);
			}
			
		} catch(Exception e) {e.printStackTrace();}
		
		return aps;
	}
	
}
