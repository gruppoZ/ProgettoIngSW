package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.CampoCategoria;
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
		return this.getItems().get(Gerarchia.formattaNome(nomeRoot));
	} 
	
	public boolean checkItemPresente(String nome) throws IOException {
		return this.getItems().containsKey(Gerarchia.formattaNome(nome));
	}
		
	public void fineCreazioneGerarchia(Gerarchia gerarchiaDaSalvare) throws IOException {
		this.getItems().put(gerarchiaDaSalvare.getNomeFormattato(), gerarchiaDaSalvare);
		this.salva();
	}
}
