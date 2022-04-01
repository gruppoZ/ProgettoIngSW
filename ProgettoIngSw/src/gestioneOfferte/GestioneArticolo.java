package gestioneOfferte;

import java.util.HashMap;
import java.util.List;

import gestioneCategorie.CampoCategoria;
import gestioneCategorie.Categoria;
import gestioneCategorie.Gerarchia;
import gestioneCategorie.GestioneGerarchie;
import main.JsonIO;

public class GestioneArticolo {
	private Articolo articolo;
	
	//TODO: al posto di gerarchie si usa GestioneGerarchie e si risolve la lettura e la formattazione
	HashMap<String, Gerarchia> gerarchie;
	GestioneGerarchie gestoreGerarchie;
	
	public GestioneArticolo() {
		gestoreGerarchie = new GestioneGerarchie();
	}
	
	public void creaArticolo(Categoria foglia, HashMap<CampoCategoria, String> valoriCampi) {
		this.articolo = new Articolo(foglia, valoriCampi);
	}
	
	//sarebbe meglio far si che gestionegerarchia legga il file json nel suo costruttore
	public void caricaGerarchie() {
		gestoreGerarchie.leggiDaFileGerarchie();
	}
	
	public HashMap<String, Gerarchia> getGerarchie() {
		caricaGerarchie();
		return gestoreGerarchie.getGerarchie();
	}

	
	public boolean checkEsistenzaCategoria(String nome) {		
		return gestoreGerarchie.checkGerarchiaPresente(nome);
	}
	
	public Gerarchia getGerarchiaByName(String nomeRoot) {
		return gerarchie.get(nomeRoot);
	}
	
	public Categoria getCategoriaByName(Gerarchia gerarchia, String nome) {
		return gerarchia._getCategoriaByName(nome);
	}
	
	public List<Categoria> getListaFoglie(Gerarchia gerarchia) {
		return gerarchia._getListaFoglie();
	}
	
	
}
