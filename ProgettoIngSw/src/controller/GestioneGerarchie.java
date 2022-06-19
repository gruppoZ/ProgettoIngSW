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
	//NOTA: i nomi usati come KEY vengono formattati attraverso il metodo "formattaNome"
	private HashMap<String, Gerarchia> gerarchie; 
	private String pathGerarchie;
	private FileSystemOperations fs;
	
	public GestioneGerarchie() throws IOException {
		fs = new JsonIO();
		aggiorna();
	}
	
	public void aggiorna() throws IOException {
		GestioneFileProgramma info = new GestioneFileProgramma();
		pathGerarchie = info.getInfoSistema().getUrlGerarchie(); 
		
		popolaGerarchie();
	}
	
	public Map<String, Gerarchia> getItems() throws IOException {
		if(gerarchie == null)
			leggiDaFile();
		return this.gerarchie;
	}
	
	public void leggiDaFile() throws IOException {
		this.gerarchie = (HashMap<String, Gerarchia>) fs.leggiGerarchiehMap(pathGerarchie);
	}
	
	public void salva() throws IOException {
        fs.salvaOggetto(pathGerarchie, this.getItems());
	}
	
	public void importa(String path) throws IOException {
		HashMap<String, Gerarchia> gerarchieImportate = (HashMap<String, Gerarchia>) fs.leggiGerarchiehMap(path);
		
		this.gerarchie = gerarchieImportate;
		salva();
	}
	
	/**
	 * Permette di ricreare la hashmap ElencoCategoria di ogni Gerarchia salvata su JSON
	 * @throws IOException 
	 */
	public void popolaGerarchie() throws IOException {
		for (Gerarchia gerarchia : getItems().values()) {
			gerarchia.popolaElencoCategorie(gerarchia.getRoot());
		}
	}
	
	public Gerarchia getItemByName(String nomeRoot) throws IOException {
		return this.getItems().get(this.formattaNome(nomeRoot));
	} 
	
	public boolean checkItemPresente(String nome) throws IOException {
		return this.getItems().containsKey(this.formattaNome(nome));
	}
	
	/**
	 * 
	 * @param campi
	 * @param descrizione
	 * @return TRUE se descrizione non e' gia' presente in campi FALSE altrimenti
	 */
	public static boolean checkUnicitaCampo(List<CampoCategoria> campi, String descrizione) {
		if(campi == null)
			return true;
		
		for (CampoCategoria campo : campi) {
			if(campo.getDescrizione().equalsIgnoreCase(descrizione)) {
				return false;
			}
		}
		
		return true;
	}
	
	private String formattaNome(String nome) {
		return nome.toUpperCase();
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
	
	public Gerarchia getCurrentItem() throws NullPointerException{
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
		this.getCurrentItem().addCategoriaInElenco(categoriaFiglia.getNome(),categoriaFiglia);
	}
	
	public void addCampiDefaultRoot(List<CampoCategoria> campiNativi) {
		campiNativi.add(new CampoCategoria("Stato di conservazione", true));
		campiNativi.add(new CampoCategoria("Descrizione libera", false));
	}
	
	public void fineCreazioneGerarchia() throws IOException {
		this.getItems().put(this.getCurrentItem().getNomeFormattato(), this.getCurrentItem());
		this.salva();
	}
	
	public boolean checkNomeCategoriaEsiste(String nomeCategoria) {
		return this.getCurrentItem().checkNomeCategoriaEsiste(nomeCategoria);
	}
	
	public Categoria getCategoriaByName(String nome) {
		return this.getCurrentItem().getCategoriaByName(nome);
	}
		
	public void eliminaCategoria(String nome) {
		this.getCurrentItem().eliminaCategoria(nome);
	}
	
	public boolean checkNomeIsNomeRoot(String nomeDaEliminare) {
		return this.getCurrentItem().getRoot().getNome().equalsIgnoreCase(nomeDaEliminare);
	}
}
