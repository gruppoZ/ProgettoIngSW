package controller;

import java.io.IOException;
import java.util.*;

import application.CampoCategoria;
import application.Categoria;
import application.Gerarchia;
import utils.JsonIO;

public class GestioneGerarchie {
	
	private static final int NUM_SOTTOCATEGORIE_AGGIUNGERE_CON_MINIMO_RISPETTATO = 1;
	private static final int NUM_MIN_SOTTOCATEGORIE = 2;

	String pathGerarchie;
	
	//NOTA: i nomi usati come KEY vengono formattati attraverso il metodo "formattaNome"
	private HashMap<String, Gerarchia> gerarchie; 
	
	public GestioneGerarchie() throws IOException {
		updateGerarchie();	
	}
	
	public void updateGerarchie() throws IOException {
		GestioneFileProgramma info = new GestioneFileProgramma();
		pathGerarchie = info.getInfoSistema().getUrlGerarchie(); 
		
		popolaGerarchie();
	}
	
	public void leggiDaFileGerarchie() throws IOException {
		this.gerarchie = JsonIO.leggiGerarchieDaJson(pathGerarchie);
	}
	
	public void salvaGerarchie() throws IOException {
        JsonIO.salvaOggettoSuJson(pathGerarchie, this.getGerarchie());
	}
	
	public boolean isGerarchiePresenti() throws IOException {
		if(this.getGerarchie().size() == 0)
			return false;
		else
			return true;
	}
	
	public HashMap<String, Gerarchia> getGerarchie() throws IOException {
		if(gerarchie == null)
			leggiDaFileGerarchie();
		return this.gerarchie;
	}
	
	public void importaGerarchie(String path) throws IOException {
		HashMap<String, Gerarchia> gerarchieImportate = JsonIO.leggiGerarchieDaJson(path);
		
		this.gerarchie = gerarchieImportate;
		salvaGerarchie();
	}
	
	//-----------------------------------------------------------------
	private Gerarchia currentGerarchia;
	
	/**
	 * Precondizione: root != null
	 * Postcondizione: currentGerarchia != null
	 * @param root
	 */
	public void creaRoot(Categoria root) {
		root.setProfondita(0);
		currentGerarchia = new Gerarchia(root);
		
		currentGerarchia.addCategoriaInElenco(root.getNome(), root);	
	}
	
	public Gerarchia getGerarchiaInLavorazione() throws NullPointerException{
		if (currentGerarchia != null)
			return currentGerarchia;
		throw new NullPointerException();
	}	
	
	/**
	 * Precondizione: categotiaPadre != null, categoriaFiglia != null
	 * Postcondizione: categoriaPadre'.getSottoCategorie().size() = categoriaPadre.getSottoCategorie().size() + 1
	 * 					currentGerarchia'.getElencoCategorie().size() = currentGerarchia.getElencoCategorie().size() + 1
	 * @param categoriaPadre
	 * @param categoriaFiglia
	 */
	public void creaSottoCategoria(Categoria categoriaPadre, Categoria categoriaFiglia) {
		int depth = categoriaPadre.getProfondita() + 1;

		categoriaFiglia.setProfondita(depth);
		this.currentGerarchia.addCategoriaInElenco(categoriaFiglia.getNome(),categoriaFiglia);
	}
	
	public void addCampiDefaultRoot(List<CampoCategoria> campiNativi) {
		campiNativi.add(new CampoCategoria("Stato di conservazione", true));
		campiNativi.add(new CampoCategoria("Descrizione libera", false));
	}
	
	public void fineCreazioneGerarchia() throws IOException {
		this.getGerarchie().put(this.currentGerarchia.getNomeFormattato(), currentGerarchia);
		salvaGerarchie();
	}
	
	/**
	 * 
	 * @param campi
	 * @param descrizione
	 * @return TRUE se descrizione non e' gia' presente in campi FALSE altrimenti
	 */
	public boolean checkUnicitaCampo(List<CampoCategoria> campi, String descrizione) {
		if(campi == null)
			return true;
		
		for (CampoCategoria campo : campi) {
			if(campo.getDescrizione().equalsIgnoreCase(descrizione)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean checkNomeCategoriaEsiste(String nomeCategoria) {
		return this.getGerarchiaInLavorazione().checkNomeCategoriaEsiste(nomeCategoria);
	}
	
	public Categoria getCategoriaByName(String nome) {
		return this.getGerarchiaInLavorazione().getCategoriaByName(nome);
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
	
	public boolean checkCategoriaDaEliminare(String nomeCategoriaDaEliminare) {
		Categoria categoriaDaEliminare = this.currentGerarchia.getCategoriaByName(nomeCategoriaDaEliminare);
		return this.currentGerarchia.cercaPadre(categoriaDaEliminare).numeriDiSottocategorie() == NUM_MIN_SOTTOCATEGORIE;
	}
	
	public void eliminaCategoria(String nome) {
		this.currentGerarchia.eliminaCategoria(nome);
	}
	
	public boolean checkNomeIsNomeRoot(String nomeDaEliminare) {
		return this.currentGerarchia.getRoot().getNome().equalsIgnoreCase(nomeDaEliminare);
	}
	
	public boolean checkGerarchiaPresente(String nome) throws IOException {
		return getGerarchie().containsKey(this.formattaNome(nome));
	}
	
	public Gerarchia getGerarchiaByName(String nomeRoot) throws IOException {
		return getGerarchie().get(this.formattaNome(nomeRoot));
	} 
	
	private String formattaNome(String nome) {
		return nome.toUpperCase();
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
}
