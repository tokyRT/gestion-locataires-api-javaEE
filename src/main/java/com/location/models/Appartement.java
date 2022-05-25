package com.location.models;
import com.location.database.*;
import java.sql.*;
import java.util.ArrayList;

public class Appartement extends Model{
	private String designation, lieu;
	private int id, loyer;
	private Locataire locataire = null;
	String table = "appartements";
	Appartements aps = new Appartements();
	
	public Appartement() {
		
	}
	public Appartement(String designation, String lieu, int loyer) {
		this.lieu = lieu;
		this.loyer = loyer;
		this.designation = designation;
		id = -1;
	}

	//save an appartement to the database
	public void save() {
		try {
			Statement stmt = MysqlConnect.getInstance().createStatement();
			String query = "INSERT INTO "+table+" (designation, lieu, loyer) ";
				   query += "VALUES(?, ?, ?)";
			PreparedStatement prepare = MysqlConnect.getInstance().prepareStatement(query);
			prepare.setString(1, designation);
			prepare.setString(2, lieu);
			prepare.setInt(3, loyer);
			prepare.execute();
			this.id = aps.last().getId();
			this.locataire = this.getCurrentLocataire();
		} catch(Exception e) {e.printStackTrace();}
	}
	
	public void update() {
		try {
			Statement stmt = MysqlConnect.getInstance().createStatement();
			String query = "UPDATE "+table+" ";
				   query += "SET designation = ?, lieu = ?, loyer = ? ";
				   query += "WHERE id = ?";
			PreparedStatement prepare = MysqlConnect.getInstance().prepareStatement(query);
			prepare.setString(1, designation);
			prepare.setString(2, lieu);
			prepare.setInt(3, loyer);
			prepare.setInt(4, id);
			System.out.println(prepare.toString());
			prepare.execute();
		} catch(Exception e) {e.printStackTrace();}
	}
	
	public void delete() {
		try {
			Statement stmt = MysqlConnect.getInstance().createStatement();
			String query = "DELETE FROM "+table+" ";
				   query += "WHERE id = ?";
			PreparedStatement prepare = MysqlConnect.getInstance().prepareStatement(query);
			prepare.setInt(1, id);
			prepare.execute();
		} catch(Exception e) {e.printStackTrace();}
	}
	
	
	
	public ArrayList<Locataire> getLocataires(){
		ArrayList<Locataire> locs = new ArrayList<Locataire>();
		try {
			Statement stmt = MysqlConnect.getInstance().createStatement();
			String query = "SELECT loc.id, loc.nom, loc.adresse ";
				   query +="FROM locataires AS loc JOIN louer AS l ON loc.id = l.locataire_id ";
				   query +="WHERE l.appartement_id = "+id;
			ResultSet res = stmt.executeQuery(query);
			System.out.println(res.toString());
			while(res.next()) {
				Locataire loc = new Locataire(res.getString("nom"), res.getString("adresse"));
				loc.setId(res.getInt("id"));
				locs.add(loc);
			}
		} catch(Exception e) {e.printStackTrace();}
		return locs;
	}
	
	
	
	public ArrayList<Locataire> getLocataires(int year){
		ArrayList<Locataire> locs = new ArrayList<Locataire>();
		try {
			Statement stmt = MysqlConnect.getInstance().createStatement();
			String query = "SELECT *, DATE_ADD(date_entree, INTERVAL nbr_mois MONTH) AS date_fin ";
				   query +="FROM louer JOIN locataires ON locataires.id = locataire_id ";
				   query +="WHERE appartement_id = "+id+" ";
				   query +="AND (YEAR(date_entree) = "+year+" ";
				   query +="OR YEAR(DATE_ADD(date_entree, INTERVAL nbr_mois MONTH)) = "+year+")";
			ResultSet res = stmt.executeQuery(query);
			while(res.next()) {
				Locataire loc = new Locataire(res.getString("nom"), res.getString("adresse"));
				loc.setId(res.getInt("id"));
				locs.add(loc);
			}
		} catch(Exception e) {e.printStackTrace();}
		return locs;
	}
	public ArrayList<Locataire> getLocataires(String from, String to){
		ArrayList<Locataire> locs = new ArrayList<Locataire>();
		try {
			Statement stmt = MysqlConnect.getInstance().createStatement();
			String query = "SELECT *, DATE_ADD(date_entree, INTERVAL nbr_mois MONTH) AS date_fin ";
				   query +="FROM louer JOIN locataires ON locataires.id = locataire_id ";
				   query +="WHERE appartement_id = "+id+" ";
				   query +="AND ((date_entree BETWEEN '"+from+"' AND '"+to+"' ) ";
				   query +="OR (DATE_ADD(date_entree, INTERVAL nbr_mois MONTH) BETWEEN  '"+from+"' AND '"+to+"' ))";
			ResultSet res = stmt.executeQuery(query);
			while(res.next()) {
				Locataire loc = new Locataire(res.getString("nom"), res.getString("adresse"));
				loc.setId(res.getInt("id"));
				locs.add(loc);
			}
		} catch(Exception e) {e.printStackTrace();}
		return locs;
	}
	
	public Locataire getCurrentLocataire() {
		try {
			Statement stmt = MysqlConnect.getInstance().createStatement();
			String query = "SELECT * FROM locataires loc JOIN louer lou ON loc.id = lou.locataire_id ";
				   query+= "WHERE appartement_id = "+this.id+" ";
				   query+= "AND DATE_ADD(date_entree, INTERVAL nbr_mois MONTH) > NOW()";
			ResultSet res = stmt.executeQuery(query);
			if(res.next()) {
				this.locataire = new Locataire(res.getString("nom"), res.getString("adresse"));
				this.locataire.setId(res.getInt("id"));
			}
			
		} catch(Exception e) {e.printStackTrace();}
		
		return this.locataire;
	}
	
	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getLieu() {
		return lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	public int getLoyer() {
		return loyer;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public void setLoyer(int loyer) {
		this.loyer = loyer;
	}
	
//	public void save() {
//		
//	}
}
