package com.location.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.*;
import com.location.models.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.stream.Collectors;

/**
 * Servlet implementation class AppartementServlet
 */
//@WebServlet("/AppartementServlet")
public class AppartementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Appartements aps = new Appartements();
	Gson gson = new Gson();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppartementServlet() {
        super();
    }

	/**
	 * 	/appartement retrieve all appartements
	 * 	/appartement?id=2 retrieve an appartement by id
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsonOutput = "";
		String idParam = request.getParameter("id");
		String locParam = request.getParameter("locataire");
		String deleteParam = request.getParameter("delete");
		if(idParam != null) {
			Appartement ap = aps.find(Integer.valueOf(idParam));
			
			if(locParam == null) {
				if(deleteParam == null) {
					jsonOutput = gson.toJson(ap);
				} else {
					ap.delete();
					jsonOutput = "success";
				}
				
				
			} else {
				locParam = locParam.toUpperCase();
				System.out.println(locParam);
				switch (locParam) {
				case "ALL": 
					ArrayList<Locataire> locs = ap.getLocataires();
					jsonOutput = gson.toJson(locs);
				break;
				
				case "YEAR":
					int year = Integer.valueOf(request.getParameter("year"));
					System.out.println(year);
					ArrayList<Locataire> locsYear = ap.getLocataires(year);
					jsonOutput = gson.toJson(locsYear);
				break;
				
				case "RANGE":
					String from = request.getParameter("from");
					String to = request.getParameter("to");
					ArrayList<Locataire> locsRange = ap.getLocataires(from, to);
					jsonOutput = gson.toJson(locsRange);
					
				break;
				
				
				default:
				}
			}
				
			
		} 
		else {
			String filter = request.getParameter("filter");
			if(filter != null && filter.equals("AVAILABLE")) {
				String enterDate = request.getParameter("enterDate");
				ArrayList<Appartement> available = aps.getAvailable(enterDate);
				jsonOutput = gson.toJson(available);
				System.out.println(jsonOutput);
			} else {
				ArrayList<Appartement> all = aps.all();
				jsonOutput = gson.toJson(all);
			}
			
		}
		
		response.setContentType("application/json");
		response.getWriter().println(jsonOutput);
		
	}

	/**
	 * /appartement store an appartement
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsonOutput = "ok";
		String id = request.getParameter("id"); //update
		String requestData = request.getReader().lines().collect(Collectors.joining());
		System.out.println(requestData);
		
		Appartement ap = gson.fromJson(requestData, Appartement.class);
		
		if(id != null) {
			//update
			ap.setId(Integer.valueOf(id));
			ap.update();
		} else {
			//create
			ap.save(); 
		}
		
		jsonOutput = gson.toJson(ap);
		
		
		response.setContentType("application/json");
		response.getWriter().println(jsonOutput);
	}

	/**
	 * /appartement?id=2 delete an appartement by id
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String jsonOutput = "";
		String id = request.getParameter("id");
		System.out.println("delete");
		if(id != null) {
			Appartement ap = aps.find(Integer.valueOf(id));
			ap.delete();
			jsonOutput = "success";
		} else {
			jsonOutput = "error";
		}
		response.setContentType("application/json");
		response.getWriter().println(jsonOutput);
	}
	
	/**
	 * /appartement?id=2 update an appartement by id
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String jsonOutput = "";
		String id = request.getParameter("id");
		if(id != null) {
			String requestData = request.getReader().lines().collect(Collectors.joining());
			Appartement ap = gson.fromJson(requestData, Appartement.class);
			ap.setId(Integer.valueOf(id));
			ap.update();
			jsonOutput = gson.toJson(ap);
		} else {
			jsonOutput = "error";
		}
		response.setContentType("application/json");
		response.getWriter().println(jsonOutput);
	}
	
}
