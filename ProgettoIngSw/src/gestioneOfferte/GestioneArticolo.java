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
	
	GestioneGerarchie gestoreGerarchie;
	
	public GestioneArticolo() {
		gestoreGerarchie = new GestioneGerarchie();
		gestoreGerarchie.initGestioneArticolo(); //serve per popolare la hashmap all'interno di ogni singola gerarchia
	}
	
	public void creaArticolo(Categoria foglia, HashMap<CampoCategoria, String> valoriCampi) {
		this.articolo = new Articolo(foglia, valoriCampi);
	}
	
	public HashMap<String, Gerarchia> getGerarchie() {
		return gestoreGerarchie.getGerarchie();
	}
	
	public boolean checkEsistenzaGerarchia(String nome) {		
		return gestoreGerarchie.checkGerarchiaPresente(nome);
	}
	
	public Gerarchia getGerarchiaByName(String nome) {
		return gestoreGerarchie.getGerarchiaByName(nome);
	}
	
	public boolean checkEsistenzaCategoria(Gerarchia gerarchia, String nome) {		
		return gerarchia.checkNomeCategoriaEsiste(nome);
	}
	
	public Categoria getCategoriaByName(Gerarchia gerarchia, String nome) {
		return gerarchia._getCategoriaByName(nome);
	}
	
	public List<Categoria> getListaFoglie(Gerarchia gerarchia) {
		return gerarchia._getListaFoglie();
	}
	
	
}
