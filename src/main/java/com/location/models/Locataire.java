package com.location.models;

import java.sql.PreparedStatement;
import java.sql.Statement;

import com.location.database.MysqlConnect;

public class Locataire {
	private int id;
	private String nom;
	private String adresse;
	String table = "locataires";
	Locataires locs = new Locataires();
	
	public Locataire() {
		
	}
	public Locataire(String nom, String adresse) {
		this.nom = nom;
		this.adresse = adresse;
		id = -1;
	}
	
	public void save() {
		try {
			String query = "INSERT INTO "+table+" (nom, adresse) ";
				   query += "VALUES(?, ?)";
			PreparedStatement prepare = MysqlConnect.getInstance().prepareStatement(query);
			prepare.setString(1, nom);
			prepare.setString(2, adresse);	
			prepare.execute();
			this.id = locs.last().getId();
		} catch(Exception e) {e.printStackTrace();}
	}
	
	public void update() {
		try {
			Statement stmt = MysqlConnect.getInstance().createStatement();
			String query = "UPDATE "+table+" ";
				   query += "SET nom = ?, adresse = ? ";
				   query += "WHERE id = ?";
			PreparedStatement prepare = MysqlConnect.getInstance().prepareStatement(query);
			prepare.setString(1, nom);
			prepare.setString(2, adresse);
			prepare.setInt(3, id);
			prepare.execute();
		} catch(Exception e) {e.printStackTrace();}
	}
	public void delete() {
		try {
			String query = "DELETE FROM "+table+" ";
			       query += "WHERE id = ?";
			PreparedStatement prepare = MysqlConnect.getInstance().prepareStatement(query);
			prepare.setInt(1, id);
			prepare.execute();
		} catch(Exception e) {e.printStackTrace();}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
}
