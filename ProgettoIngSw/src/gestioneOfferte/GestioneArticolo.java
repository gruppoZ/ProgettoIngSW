package gestioneOfferte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import gestioneCategorie.CampoCategoria;
import gestioneCategorie.Categoria;
import gestioneCategorie.Gerarchia;
import gestioneCategorie.GestioneGerarchie;
import main.JsonIO;


//TODO: serve un gestore a livello più alto che fa cose comuni sia per l'articolo che per le offerte
public class GestioneArticolo {
	
	private static final String PATH_OFFERTE = "src/gestioneOfferte/offerte.json";

	private ArrayList<Offerta> listaOfferte; 
	private Offerta offerta;
	private Articolo articolo;
	private GestioneGerarchie gestoreGerarchie;

	public GestioneArticolo() {
		listaOfferte = leggiListaOfferte(); 
		articolo = new Articolo();
		gestoreGerarchie = new GestioneGerarchie();
		gestoreGerarchie.initGestioneArticolo(); //serve per popolare la hashmap all'interno di ogni singola gerarchia, potrebbe essere spostato in init
	}
	
//	public void init() {
//		listaPubblicazioni = leggiListaPubblicazioni(); 
//		this.articolo = new Articolo();
//		this.offerta = new Offerta();
//	}
	
	ArrayList<Offerta> leggiListaOfferte() {
		ArrayList<Offerta> listaLetta = (ArrayList<Offerta>) JsonIO.leggiListaDaJson(PATH_OFFERTE, Offerta.class);
		ArrayList<Offerta> listaDaRitornare = new ArrayList<Offerta>();
		
		for(Offerta offerta : listaLetta) {
			switch (offerta.getTipoOfferta()) {
			case "OffertaAperta":
				listaDaRitornare.add(new OffertaAperta(offerta.getArticolo(), offerta.getUsername()));
				break;
			case "OffertaRitirata":
				listaDaRitornare.add(new OffertaRitirata(offerta.getArticolo(), offerta.getUsername()));
				break;
			default:
				break;
			}
		}
		
		return listaDaRitornare;
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
	
	public List<Categoria> getListaFoglieByGerarchia(Gerarchia gerarchia) {
		return gerarchia._getListaFoglie();
	}
	
	public List<CampoCategoria> getListaCampi(){
		return this.articolo.getFoglia()._getCampiNativiEreditati();
	}
	
	public GestioneGerarchie getGestoreGerarchie() {
		return gestoreGerarchie;
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
	
	//gestione offerta/pubblicazione
	//TODO: cambia nomi
	protected void manageAggiuntaPubblicazione(String username) {
		creaPubblicazione(username);
		addPubblicazione();
		salvaPubblicazioni();
	}
	
	private void creaPubblicazione(String username) {
		this.offerta = new OffertaAperta(articolo, username);
	}
	
	private void addPubblicazione() {
		this.listaOfferte.add(offerta);
	}
	
	private void salvaPubblicazioni() {
		JsonIO.salvaOfferteSuJson(PATH_OFFERTE, listaOfferte);
	}
	
	
}
