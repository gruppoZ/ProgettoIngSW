package controller;

import java.io.IOException;
import java.util.*;

import application.CampoCategoria;
import application.Categoria;
import application.Gerarchia;

public class GerarchieFacade {
	
	private static final int NUM_SOTTOCATEGORIE_AGGIUNGERE_CON_MINIMO_RISPETTATO = 1;
	private static final int NUM_MIN_SOTTOCATEGORIE = 2;
	private GerarchiaRepository repo;
	
	public GerarchieFacade() throws IOException {
		repo = new GestioneGerarchie();
		updateGerarchie();	
	}
	
	public void updateGerarchie() throws IOException {
		repo.aggiorna();
	}
	
	public void leggiDaFileGerarchie() throws IOException {
		repo.leggiDaFile();
	}
	
	public void salvaGerarchie() throws IOException {
       repo.salva();
	}
	
	public boolean isGerarchiePresenti() throws IOException {
		if(this.getGerarchie().size() == 0)
			return false;
		else
			return true;
	}
	
	public HashMap<String, Gerarchia> getGerarchie() throws IOException {
		return (HashMap<String, Gerarchia>) repo.getItems();
	}
	
	public void importaGerarchie(String path) throws IOException {
		repo.importa(path);
	}
	
	public boolean checkGerarchiaPresente(String nome) throws IOException {
		return repo.checkItemPresente(nome);
	}
	
	public Gerarchia getGerarchiaByName(String nomeRoot) throws IOException {
		return (Gerarchia) repo.getItemByName(nomeRoot);
	} 
	
	/**
	 * Permette di ricreare la hashmap ElencoCategoria di ogni Gerarchia salvata su JSON
	 * @throws IOException 
	 */
	public void popolaGerarchie() throws IOException {
		for (Gerarchia gerarchia : getGerarchie().values()) {
			gerarchia.popolaElencoCategorie(gerarchia.getRoot());
		}
	}
	
	protected boolean checkNumMinimoSottoCategorie(int nSottoCategorie) {
		return nSottoCategorie >= NUM_MIN_SOTTOCATEGORIE;
	}
	
	/**
	 * Precondizione: categoriaDaRamificare != null
	 * 
	 * @param categoriaDaRamificare
	 * @return
	 */
	public int getNumSottoCatDaInserire(Categoria categoriaDaRamificare) {
		if(checkNumMinimoSottoCategorie(categoriaDaRamificare.getSottoCategorie().size()) )
			return NUM_SOTTOCATEGORIE_AGGIUNGERE_CON_MINIMO_RISPETTATO;
		else
			return NUM_MIN_SOTTOCATEGORIE;
	}
	
	public int getNumMinSottoCategorie() {
		return NUM_MIN_SOTTOCATEGORIE;
	}
	
	/**
	 * 
	 * @param campi
	 * @param descrizione
	 * @return TRUE se descrizione non e' gia' presente in campi FALSE altrimenti
	 */
	public boolean checkUnicitaCampo(List<CampoCategoria> campi, String descrizione) {
		return GestioneGerarchie.checkUnicitaCampo(campi, descrizione);
	}	
	
	public Gerarchia getGerarchiaInLavorazione() {
		return repo.getCurrentItem();
	}
	
	public void eliminaCategoria(String nome) {
		repo.eliminaCategoria(nome);
	}
	
	public boolean checkNomeIsNomeRoot(String nomeDaEliminare) {
		return repo.checkNomeIsNomeRoot(nomeDaEliminare);
	}
	
	public boolean checkNomeCategoriaEsiste(String nomeCategoria) {
		return repo.checkNomeCategoriaEsiste(nomeCategoria);
	}
	
	public void fineCreazioneGerarchia() throws IOException {
		repo.fineCreazioneGerarchia();
	}
	
	public Categoria getCategoriaByName(String nome) {
		return repo.getCategoriaByName(nome);
	}
	
	public void creaSottoCategoria(Categoria categoriaPadre, Categoria categoriaFiglia) {
		repo.creaSottoCategoria(categoriaPadre, categoriaFiglia);
	}
	
	public void creaRoot(Categoria root) {
		repo.creaRoot(root);
	}
	
	public void addCampiDefaultRoot(List<CampoCategoria> campiNativi) {
		repo.addCampiDefaultRoot(campiNativi);
	}
	
	public boolean checkCategoriaDaEliminare(String nomeCategoriaDaEliminare) {
		Categoria categoriaDaEliminare = ((Gerarchia) repo.getCurrentItem()).getCategoriaByName(nomeCategoriaDaEliminare);
		return ((Gerarchia) repo.getCurrentItem()).cercaPadre(categoriaDaEliminare).numeriDiSottocategorie() == NUM_MIN_SOTTOCATEGORIE;
	}
}
