package gestioneOfferte;

import java.util.HashMap;
import java.util.List;

import gestioneCategorie.CampoCategoria;
import gestioneCategorie.Categoria;
import gestioneCategorie.Gerarchia;
import gestioneCategorie.GestioneGerarchie;
import main.JsonIO;

public class GestioneArticolo {
	private static final String PATH_GERARCHIE = "src/gestioneCategorie/gerarchie.json";

	
	private Articolo articolo;
	
	//TODO: al posto di gerarchie si usa GestioneGerarchie e si risolve la lettura e la formattazione
	HashMap<String, Gerarchia> gerarchie;
	GestioneGerarchie gestoreGerarchie = new GestioneGerarchie(); // da creare e fare init blabla
	
	public void creaArticolo(Categoria foglia, HashMap<CampoCategoria, String> valoriCampi) {
		this.articolo = new Articolo(foglia, valoriCampi);
	}
	
	public void mostraGerarchie() {
		 gerarchie = JsonIO.leggiGerarchieDaJson(PATH_GERARCHIE);
		 
		 
		 
		 
		for (Gerarchia gerarchia : gerarchie.values()) {
			System.out.println(gerarchia.showGerarchia());
		}
	}
	
	public boolean checkEsistenzaCategoria(String nome) {		
		return gerarchie.containsKey(nome);
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
