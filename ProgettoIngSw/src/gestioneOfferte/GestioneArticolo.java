package gestioneOfferte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gestioneCategorie.CampoCategoria;
import gestioneCategorie.Categoria;

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
		List<CampoCategoria> campiObbligatori = new ArrayList<>();
		
		for (CampoCategoria campo : getListaCampi()) {
			if(campo.isObbligatorio()) {
				campiObbligatori.add(campo);
			}
		}
		
		return campiObbligatori;
	}
	
	public List<CampoCategoria> getListaCampiFacoltativi(){
		List<CampoCategoria> campiFacoltativi = new ArrayList<>();
		
		for (CampoCategoria campo : getListaCampi()) {
			if(!campo.isObbligatorio()) {
				campiFacoltativi.add(campo);
			}
		}
		
		return campiFacoltativi;
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
