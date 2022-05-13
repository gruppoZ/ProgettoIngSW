package gestioneUtenti;

import java.util.HashMap;

import gestioneCategorie.Gerarchia;
import gestioneCategorie.GestioneGerarchie;

public abstract class GestioneUtente {
	private GestioneGerarchie gestoreGerarchie;
	
	public GestioneUtente() {
		gestoreGerarchie = new GestioneGerarchie();
	}
	
	protected GestioneGerarchie getGestoreGerarchie() {
		return this.gestoreGerarchie;
	}
	
	public HashMap<String, Gerarchia> getGerarchie() {
		return gestoreGerarchie.getGerarchie();
	}

	public boolean isGerarchieCreate() {
		return gestoreGerarchie.isGerarchiePresenti();
	}	
	
	protected void aggiornaGerarchie() {
		gestoreGerarchie.leggiDaFileGerarchie();
	}
}
