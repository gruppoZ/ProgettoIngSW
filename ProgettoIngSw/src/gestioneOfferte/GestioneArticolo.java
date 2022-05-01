package gestioneOfferte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gestioneCategorie.CampoCategoria;
import gestioneCategorie.Categoria;

//TODO: serve un gestore a livello più alto che fa cose comuni sia per l'articolo che per le offerte e che abbia la lista delle offerte
public class GestioneArticolo {
	
	private Articolo articolo;

	public GestioneArticolo() {
		articolo = new Articolo();
	}

	public void creaArticolo(Categoria foglia, HashMap<String, String> valoriCampi) {
		this.articolo = new Articolo(foglia, valoriCampi);
	}
	
	public List<CampoCategoria> getListaCampi(){
		return this.articolo.getFoglia()._getCampiNativiEreditati();
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
	
	public void addFoglia(Categoria foglia) {
		this.articolo.setFoglia(foglia);
	}
	
	public void addValoreCampo(CampoCategoria campo, String valore) {
		this.articolo.addValoreCampo(campo.getDescrizione(), valore);
	}
}
