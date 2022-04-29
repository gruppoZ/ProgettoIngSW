package gestioneCategorie;

import java.util.*;
import main.JsonIO;

public class GestioneGerarchie {
	
	private static final int NUM_SOTTOCATEGORIE_AGGIUNGERE_CON_MINIMO_RISPETTATO = 1;
	private static final int NUM_MIN_SOTTOCATEGORIE = 2;
	
	private static final String PATH_GERARCHIE = "src/gestioneCategorie/gerarchie.json";
		
	//NOTA: i nomi usati come KEY vengono formattati attraverso il metodo "formattaNome"
	private HashMap<String, Gerarchia> gerarchie; 
	
	public GestioneGerarchie() {
	}
	
	public void leggiDaFileGerarchie() {
		this.gerarchie = JsonIO.leggiGerarchieDaJson(PATH_GERARCHIE);
	}
	
	public void salvaGerarchie() {
        JsonIO.salvaOggettoSuJson(PATH_GERARCHIE, this.getGerarchie());
	}
	
	public boolean isGerarchiePresenti() {
		if(this.getGerarchie().size() == 0)
			return false;
		else
			return true;
	}
	
	public HashMap<String, Gerarchia> getGerarchie() {
		if(gerarchie == null)
			leggiDaFileGerarchie();
		return this.gerarchie;
	}
	
	public void initGestioneArticolo() {
		this.popolaGerarchie();
	}

	
	//-----------------------------------------------------------------
	private Gerarchia currentGerarchia;
	
	public void creaRoot(Categoria root) {
		int depth = 0;
		
		root.setProfondita(depth);
		currentGerarchia = new Gerarchia(root);
		
		currentGerarchia.addCategoriaInElenco(root.getNome(), root);	
	}
	
	public void creaSottoCategoria(Categoria categoriaPadre, Categoria categoriaFiglia) {
		int depth = categoriaPadre.getProfondita() + 1;
		
		categoriaFiglia.setProfondita(depth);
		this.currentGerarchia.addCategoriaInElenco(categoriaFiglia.getNome(),categoriaFiglia);
	}
	
	public void addCampiDefaultRoot(List<CampoCategoria> campiNativi) {
		campiNativi.add(new CampoCategoria("Stato di conservazione", true));
		campiNativi.add(new CampoCategoria("Descrizione libera", false));
	}
	
	public void fineCreazioneGerarchia() {
		this.getGerarchie().put(this.currentGerarchia._getNomeFormattato(), currentGerarchia);
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
	
	protected boolean checkNomeCategoriaEsiste(String nomeCategoria) {
		return this.currentGerarchia.checkNomeCategoriaEsiste(nomeCategoria);
	}
	
	public Categoria getCategoriaByName(String nome) {
		return this.currentGerarchia._getCategoriaByName(nome);
	}
	
	protected boolean checkNumMinimoSottoCategorie(int nSottoCategorie) {
		return nSottoCategorie >= NUM_MIN_SOTTOCATEGORIE;
	}
	
	protected int getNumSottoCatDaInserire(Categoria categoriaDaRamificare) {
		if(checkNumMinimoSottoCategorie(categoriaDaRamificare.getSottoCategorie().size()) )
			return NUM_SOTTOCATEGORIE_AGGIUNGERE_CON_MINIMO_RISPETTATO;
		else
			return NUM_MIN_SOTTOCATEGORIE;
	}
	
	protected int getNumMinSottoCategorie() {
		return NUM_MIN_SOTTOCATEGORIE;
	}
	
	protected boolean checkCategoriaDaEliminare(String nomeCategoriaDaEliminare) {
		Categoria categoriaDaEliminare = this.currentGerarchia._getCategoriaByName(nomeCategoriaDaEliminare);
		return this.currentGerarchia.cercaPadre(categoriaDaEliminare).numeriDiSottocategorie() == NUM_MIN_SOTTOCATEGORIE;
	}
	
	public void eliminaCategoria(String nome) {
		this.currentGerarchia.eliminaCategoria(nome);
	}
	
	protected boolean checkNomeIsNomeRoot(String nomeDaEliminare) {
		return this.currentGerarchia.getRoot().getNome().equalsIgnoreCase(nomeDaEliminare);
	}
	
	public boolean checkGerarchiaPresente(String nome) {
		return getGerarchie().containsKey(this.formattaNome(nome));
	}
	
	public Gerarchia getGerarchiaByName(String nomeRoot) {
		return getGerarchie().get(this.formattaNome(nomeRoot));
	} 
	
	private String formattaNome(String nome) {
		return nome.toUpperCase();
	}
	
	/**
	 * Permette di ricreare la hashmap ElencoCategoria di ogni Gerarchia salvata su JSON
	 */
	public void popolaGerarchie() {
		for (Gerarchia gerarchia : getGerarchie().values()) {
			gerarchia.popolaElencoCategorie(gerarchia.getRoot());
		}
	}
	
	public String showGerarchia() {
		return this.currentGerarchia.toString();
	}
	
	public String getToStringSintetico() {
		StringBuffer result = new StringBuffer();
		
		if(this.gerarchie.size() == 0) {
			result.append("\nNESSUNA GERARCHIA PRESENTE\n");
		} else {
			result.append("---------------------------\n");
			for (Gerarchia gerarchia : gerarchie.values()) {
				result.append(gerarchia.showGerarchiaSintetica());
			}
		}
				
		return result.toString();
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		
		if(this.getGerarchie().size() == 0) {
			result.append("\nNESSUNA GERARCHIA PRESENTE\n");
		} else {
			result.append("---------------------------\n");
			for (Gerarchia gerarchia : getGerarchie().values()) {
				result.append(gerarchia.toString());
			}
		}
				
		return result.toString();		
	}
}
