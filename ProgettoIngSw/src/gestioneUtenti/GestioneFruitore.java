package gestioneUtenti;

import gestioneParametri.GestioneParametri;
import gestioneParametri.Piazza;

public class GestioneFruitore extends GestioneUtente{
	
	private GestioneParametri gestoreParametri;
	
	public GestioneFruitore() {
		super();
		gestoreParametri = new GestioneParametri();
	}
	
	public boolean isPiazzaCreata() {
		return gestoreParametri.isPiazzaCreata();
	}
	
	public Piazza getPiazza() {
		return gestoreParametri.leggiPiazza();
	}
	
}
