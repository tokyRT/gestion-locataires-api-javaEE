package com.location.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.location.database.MysqlConnect;

public class Louer {
	private int id;
	private int locId;
	private int apId;
	private String dateEntree;
	private int nbrMois;
	
	public Louer(int locId, int apId, String dateEntree, int nbrMois) {
		this.locId = locId;
		this.apId = apId;
		this.dateEntree = dateEntree;
		this.nbrMois = nbrMois;
	}
	
	public void save() {
		try {
			String query = "INSERT INTO louer (locataire_id, appartement_id, date_entree, nbr_mois) ";
				   query+= "VALUES(?, ?, ?, ?)";
			
			PreparedStatement prepare = MysqlConnect.getInstance().prepareStatement(query);
			prepare.setInt(1, locId);
			prepare.setInt(2, apId);
			prepare.setString(3, dateEntree);
			prepare.setInt(4, nbrMois);
			prepare.execute();
			
			this.id = last().getId();
			
		} catch(Exception e) {e.printStackTrace();}
	}

	public Louer last() {
		Louer l = null;
		try {
			Statement stmt = MysqlConnect.getInstance().createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM louer ORDER BY id DESC LIMIT 1");
			res.next();
			l = new Louer(res.getInt("locataire_id"), res.getInt("appartement_id"), res.getString("date_entree"), res.getInt("nbr_mois"));
			l.setId(res.getInt("id"));
		} catch(Exception e) {e.printStackTrace();}
		
		return l;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLocId() {
		return locId;
	}

	public void setLocId(int locId) {
		this.locId = locId;
	}

	public int getApId() {
		return apId;
	}

	public void setApId(int apId) {
		this.apId = apId;
	}

	public String getDateEntree() {
		return dateEntree;
	}

	public void setDateEntree(String dateEntree) {
		this.dateEntree = dateEntree;
	}

	public int getNbrMois() {
		return nbrMois;
	}

	public void setNbrMois(int nbrMois) {
		this.nbrMois = nbrMois;
	}
	
	
}
