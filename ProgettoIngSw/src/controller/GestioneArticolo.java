package controller;

import java.util.HashMap;
import java.util.List;

import application.CampoCategoria;
import application.Categoria;
import application.baratto.Articolo;

public class GestioneArticolo {
	
	private Articolo articolo;

	/**
	 * Postcondizione: articolo != null
	 */
	public GestioneArticolo() {
		articolo = new Articolo();
	}

	/**
	 * Precondizione: foglia != null, valoriCampi != null
	 * Postcondizione: articolo != null
	 * @param foglia
	 * @param valoriCampi
	 */
	public void creaArticolo(Categoria foglia, HashMap<String, String> valoriCampi) {
		this.articolo = new Articolo(foglia, valoriCampi);
	}
	
	public List<CampoCategoria> getListaCampi(){
		return this.articolo.getFoglia().getCampiNativiEreditati();
	}
	
	public Articolo getArticolo() {
		return articolo;
	}

	public void setArticolo(Articolo articolo) {
		this.articolo = articolo;
	}	
	
	public List<CampoCategoria> getListaCampiObbligatori(){
		return this.articolo.getFoglia().getCampiObbligatori();
	}
	
	public List<CampoCategoria> getListaCampiFacoltativi(){
		return this.articolo.getFoglia().getCampiFacoltativi();
	}
	
	/**
	 * Precondizione: foglia != null
	 * Postcondizione: this.articolo.getFoglia() != null
	 * @param foglia
	 */
	public void addFoglia(Categoria foglia) {
		this.articolo.setFoglia(foglia);
	}
	
	/**
	 * Precondizione: campo != null
	 * Postcondizione: this.articolo.getValoreCampi() != null
	 * @param campo
	 * @param valore
	 */
	public void addValoreCampo(CampoCategoria campo, String valore) {
		this.articolo.addValoreCampo(campo.getDescrizione(), valore);
	}
}
