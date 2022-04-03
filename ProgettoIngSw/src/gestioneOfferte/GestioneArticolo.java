package gestioneOfferte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gestioneCategorie.CampoCategoria;
import gestioneCategorie.Categoria;
import gestioneCategorie.Gerarchia;
import gestioneCategorie.GestioneGerarchie;
import main.JsonIO;

public class GestioneArticolo {
	
	private static final String PATH_PUBBLICAZIONI = "src/gestioneOfferte/pubblicazioni.json";

	private ArrayList<Pubblicazione> listaPubblicazioni; 
	private Pubblicazione pubblicazione;
	private Articolo articolo;
	private GestioneGerarchie gestoreGerarchie;
	
	public GestioneArticolo() {
		listaPubblicazioni = new ArrayList<Pubblicazione>(); 
		articolo = new Articolo();
		gestoreGerarchie = new GestioneGerarchie();
		gestoreGerarchie.initGestioneArticolo(); //serve per popolare la hashmap all'interno di ogni singola gerarchia, potrebbe essere spostato in init
	}
	
	public void init() {
		listaPubblicazioni = leggiListaPubblicazioni(); 
		this.articolo = new Articolo();
		this.pubblicazione = new Pubblicazione();
	}
	
	private ArrayList<Pubblicazione> leggiListaPubblicazioni(){
		return (ArrayList<Pubblicazione>) JsonIO.leggiListaDaJson(PATH_PUBBLICAZIONI, Pubblicazione.class);
	}
	
	public void creaArticolo(Categoria foglia, HashMap<String, String> valoriCampi) {
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
	
	public List<CampoCategoria> getListaCampi(){
		return this.articolo.getFoglia()._getCampiNativiEreditati();
	}
	
	public void addFoglia(Categoria foglia) {
		this.articolo.setFoglia(foglia);
	}
	
	public void addValoreCampo(CampoCategoria campo, String valore) {
		this.articolo.addValoreCampo(campo.getDescrizione(), valore);
	}
	
	//gestione offerta/pubblicazione
	
	public void creaPubblicazione(String username) {
		this.pubblicazione = new Pubblicazione(articolo, username, new OffertaAperta());
	}
	
	public void addPubblicazione() {
		this.listaPubblicazioni.add(pubblicazione);
	}
	
	public void salvaPubblicazioni() {
		JsonIO.salvaOggettoSuJson(PATH_PUBBLICAZIONI, listaPubblicazioni);
	}
	
	
}
