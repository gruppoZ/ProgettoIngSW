package gestioneUtenti;

import gestioneParametri.GestioneParametri;
import gestioneParametri.Piazza;

public class GestioneFruitore extends GestioneUtente{
	
	private GestioneParametri gestoreParametri;
	private String username;
	
	public GestioneFruitore(String username) {
		super();
		gestoreParametri = new GestioneParametri();
		this.username = username;
	}
	
	public boolean isPiazzaCreata() {
		return gestoreParametri.isPiazzaCreata();
	}
	
	public Piazza getPiazza() {
		return gestoreParametri.leggiPiazza();
	}
	
	public String getUsername() {
		return this.username;
	}
	
}
