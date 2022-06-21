package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.CampoCategoria;
import application.Categoria;
import application.Gerarchia;
import utils.FileSystemOperations;
import utils.JsonIO;

public class GestioneGerarchie implements GerarchiaRepository {
	private static final int NUM_SOTTOCATEGORIE_AGGIUNGERE_CON_MINIMO_RISPETTATO = 1;
	private static final int NUM_MIN_SOTTOCATEGORIE = 2;
	
	//NOTA: i nomi usati come KEY vengono formattati attraverso il metodo "formattaNome"
	private HashMap<String, Gerarchia> gerarchie; 
	private String pathGerarchie;
	private FileSystemOperations fs;
	private GerarchieFacade gerarchiaFacade;	
	
	public GestioneGerarchie() throws IOException {
		fs = new JsonIO();
		
		aggiorna();
	}
	
	public boolean isGerarchiePresenti() throws IOException {
		if(this.getGerarchie().size() == 0)
			return false;
		else
			return true;
	}
		
	@Override
	public void aggiorna() throws IOException {
		GestioneFileProgramma info = new GestioneFileProgramma();
		pathGerarchie = info.getInfoSistema().getUrlGerarchie(); 
		
		popolaGerarchie();
	}
	
	@Override
	public Map<String, Gerarchia> getGerarchie() throws IOException {
		if(gerarchie == null)
			leggiDaFile();
		return this.gerarchie;
	}
	
	@Override
	public void leggiDaFile() throws IOException {
		this.gerarchie = (HashMap<String, Gerarchia>) fs.leggiGerarchiehMap(pathGerarchie);
	}
	
	@Override
	public void salva() throws IOException {
        fs.salvaOggetto(pathGerarchie, this.getGerarchie());
	}
	
	@Override
	public void importa(String path) throws IOException {
		HashMap<String, Gerarchia> gerarchieImportate = (HashMap<String, Gerarchia>) fs.leggiGerarchiehMap(path);
		
		this.gerarchie = gerarchieImportate;
		salva();
	}
	
	@Override
	public Gerarchia getGerarchiaByName(String nomeRoot) throws IOException {
		return this.getGerarchie().get(Gerarchia.formattaNome(nomeRoot));
	} 
	
	@Override
	public boolean checkGerarchiaPresente(String nome) throws IOException {
		return this.getGerarchie().containsKey(Gerarchia.formattaNome(nome));
	}
	
	@Override
	public void fineCreazioneGerarchia() throws IOException {
		this.getGerarchie().put(getGerarchiaInLavorazione().getNomeFormattato(), getGerarchiaInLavorazione());
		this.salva();
	}
	
	@Override
	public List<?> getItems() throws IOException {
		// no op
		return null;
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
		return Categoria.checkUnicitaCampo(campi, descrizione);
	}	
	
	public Gerarchia getGerarchiaInLavorazione() {
		return gerarchiaFacade.getGerarchiaInLavorazione();
	}
	
	public void eliminaCategoria(String nome) {
		gerarchiaFacade.eliminaCategoria(nome);
	}
	
	public boolean checkNomeIsNomeRoot(String nomeDaEliminare) {
		return gerarchiaFacade.checkNomeIsNomeRoot(nomeDaEliminare);
	}
	
	public boolean checkNomeCategoriaEsiste(String nomeCategoria) {
		return gerarchiaFacade.checkNomeCategoriaEsiste(nomeCategoria);
	}
		
	public Categoria getCategoriaByName(String nome) {
		return gerarchiaFacade.getCategoriaByName(nome);
	}
	
	public void creaSottoCategoria(Categoria categoriaPadre, Categoria categoriaFiglia) {
		gerarchiaFacade.creaSottoCategoria(categoriaPadre, categoriaFiglia);
	}
	
	public void creaRoot(Categoria root) {
		gerarchiaFacade.creaRoot(root);
	}
	
	public void addCampiDefaultRoot(List<CampoCategoria> campiNativi) {
		gerarchiaFacade.addCampiDefaultRoot(campiNativi);
	}
	
	public boolean checkCategoriaDaEliminare(String nomeCategoriaDaEliminare) {
		Gerarchia gerarchiaInLavorazione = gerarchiaFacade.getGerarchiaInLavorazione();
		Categoria categoriaDaEliminare = ((Gerarchia) gerarchiaInLavorazione).getCategoriaByName(nomeCategoriaDaEliminare);
		
		return ((Gerarchia) gerarchiaInLavorazione).cercaPadre(categoriaDaEliminare).numeriDiSottocategorie() == NUM_MIN_SOTTOCATEGORIE;
	}
}
