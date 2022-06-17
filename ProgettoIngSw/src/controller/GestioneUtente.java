package controller;

import java.io.IOException;
import java.util.HashMap;

import application.Gerarchia;

public abstract class GestioneUtente {
	private GestioneGerarchie gestoreGerarchie;
	
	/**
	 * Postcondizione: gestoreGerarchie != null
	 * @throws IOException 
	 */
	public GestioneUtente() throws IOException {
		gestoreGerarchie = new GestioneGerarchie();
	}
	
	protected GestioneGerarchie getGestoreGerarchie() {
		return this.gestoreGerarchie;
	}
	
	public HashMap<String, Gerarchia> getGerarchie() throws IOException {
		return gestoreGerarchie.getGerarchie();
	}

	public boolean isGerarchieCreate() throws IOException {
		return gestoreGerarchie.isGerarchiePresenti();
	}	
	
	public void aggiornaGerarchie() throws IOException {
		gestoreGerarchie.leggiDaFileGerarchie();
	}
}