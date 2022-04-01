package gestioneCategorie;

import java.util.*;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;
import main.JsonIO;

public class GestioneGerarchie {
	
	private static final int NUM_SOTTOCATEGORIE_AGGIUNGERE_CON_MINIMO_RISPETTATO = 1;
	private static final int NUM_MIN_SOTTOCATEGORIE = 2;
	
	private static final String PATH_GERARCHIE = "src/gestioneCategorie/gerarchie.json";

	private static final String TXT_TITOLO = "Menu creazione gerarchia";
	private static final String TXT_ERRORE = "ERRORE";
	private static final String MSG_AGGIUNGI_SOTTOCATEGORIA = "Aggiungi sottocategoria";
	private static final String MSG_ELIMINA_SOTTOCATEGORIA = "Elimina sottocategoria";
	private static final String MSG_ELIMINA_GERARCHIA = "Elimina gerarchia";
	private static final String MSG_VISUALIZZA_GERARCHIA = "Visualizza gerarchia";
	private static final String [] TXT_VOCI = {
			MSG_AGGIUNGI_SOTTOCATEGORIA,
			MSG_ELIMINA_SOTTOCATEGORIA,
			MSG_ELIMINA_GERARCHIA,
			MSG_VISUALIZZA_GERARCHIA,
	};
		
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
	
	public void initGestioneArticolo() {
		this.popolaGerarchie();
	}
	
	public HashMap<String, Gerarchia> getGerarchie(){
		if(gerarchie == null)
			leggiDaFileGerarchie();
		return this.gerarchie;
	}
	//-----------------------------------------------------------------
	private Categoria currentRoot;
	private Gerarchia currentGerarchia;
	
	public void creaRoot(Categoria root) {
		currentRoot = root;
		int depth = 0;
		
		currentRoot.setProfondita(depth);
		currentGerarchia = new Gerarchia(currentRoot);
		
		currentGerarchia.addCategoriaInElenco(currentRoot.getNome(), currentRoot);	
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
	
	protected Categoria getCategoriaByName(String nome) {
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
	
	protected void eliminaCategoria(String nome) {
		this.currentGerarchia.eliminaCategoria(nome);
	}
	
		
	//TODO: try catch -> nel caso currentRoot è null
	/**
	 * Gestisce tutte le operazioni per la creazione di una gerarchia
	 * @return TRUE se la gerarchia e' stata creata correttamente FALSE se e' stata eliminata
	 */
	public boolean creaGerarchia() {
		boolean eliminaRoot = false;
		
		MyMenu menuGerarchia = new MyMenu(TXT_TITOLO, TXT_VOCI);
		int scelta = 0;
		boolean fine = false;
		do {
			scelta = menuGerarchia.scegli();
			switch(scelta) {
			case 0:
				fine = !InputDati.yesOrNo("Continuare a modificare la Gerarchia? "
						+ "Se no, non sarà più possibile modificarla");
				break;
			case 1:
				break;
			case 2:
				//RepositoryGerarchia.checkEliminaSottocategoria(currentGerarchia);
				break;
			case 3:
				boolean confermaCancellazione = InputDati.yesOrNo("Sei sicuro di eliminare la gerarchia?");
				if(confermaCancellazione) {
					System.out.println("\nGerarchia eliminata con successo");
					eliminaRoot = true;
					fine = true;
				} else {
					System.out.println("\nGerarchia non eliminata");
				}
				
				break;
			case 4:
				RepositoryGerarchia.showGerarchia(currentGerarchia);
				break;
			default:
				System.out.println(TXT_ERRORE);
				
			}
		} while(!fine);
		
		if(eliminaRoot) {
			currentRoot = null;
			currentGerarchia = null;
			return false;
		} else {
			getGerarchie().put(currentGerarchia._getNomeFormattato(), currentGerarchia);
			return true;
		}
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
