package com.location.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.location.models.Appartement;
import com.location.models.Locataire;
import com.location.models.Locataires;

/**
 * Servlet implementation class LocataireServle
 */
@WebServlet("/LocataireServlet")
public class LocataireServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Gson gson = new Gson();
    private Locataires locs = new Locataires();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LocataireServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsonOutput = "";
		String idParam = request.getParameter("id");
		String deleteParam = request.getParameter("delete");
		if(idParam != null) {
			Locataire loc = locs.find(Integer.valueOf(idParam));
			if(deleteParam == null) {
				jsonOutput = gson.toJson(loc);
			} else {
				loc.delete();
				jsonOutput = "success";
			}
		}
		else {
			ArrayList<Locataire> all = locs.all();
			jsonOutput = gson.toJson(all);
		}
		
		response.setContentType("application/json");
		response.getWriter().println(jsonOutput);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsonOutput = "";
		String id = request.getParameter("id"); //update
		String requestData = request.getReader().lines().collect(Collectors.joining());
		Locataire loc = gson.fromJson(requestData, Locataire.class);
		
		
		if(id != null) {
			//update
			loc.setId(Integer.valueOf(id));
			loc.update();
		} else {
			//create
			loc.save();
		}
		
		jsonOutput = gson.toJson(loc);
		
		response.setContentType("application/json");
		response.getWriter().println(jsonOutput);
		
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String jsonOutput = "";
		String id = request.getParameter("id");
		System.out.println("delete");
		if(id != null) {
			Locataire loc = locs.find(Integer.valueOf(id));
			loc.delete();
			jsonOutput = "success";
		} else {
			jsonOutput = "error";
		}
		response.setContentType("application/json");
		response.getWriter().println(jsonOutput);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String jsonOutput = "";
		String id = request.getParameter("id");
		if(id != null) {
			String requestData = request.getReader().lines().collect(Collectors.joining());
			Locataire loc = gson.fromJson(requestData, Locataire.class);
			loc.setId(Integer.valueOf(id));
			loc.update();
			jsonOutput = gson.toJson(loc);
		} else {
			jsonOutput = "error";
		}
		response.setContentType("application/json");
		response.getWriter().println(jsonOutput);
	}

}
