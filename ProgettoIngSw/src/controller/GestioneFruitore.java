package controller;

import java.io.IOException;

import application.Piazza;

public class GestioneFruitore extends GestioneUtente{
	
	private GestioneParametri gestoreParametri;
	private String username;
	
	/**
	 * Postcondizione: gestoreParametri != null
	 * @param username
	 * @throws IOException 
	 */
	public GestioneFruitore(String username) throws IOException {
		super();
		gestoreParametri = new GestioneParametri();
		this.username = username;
	}
	
	public boolean isPiazzaCreata() {
		return gestoreParametri.isPiazzaCreata();
	}
	
	public Piazza getPiazza() throws IOException {
		return gestoreParametri.getPiazza();
	}
	
	public String getUsername() {
		return this.username;
	}
}
