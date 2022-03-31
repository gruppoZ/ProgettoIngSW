package gestioneCategorie;

import java.util.*;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;
import main.JsonIO;

public class GestioneGerarchie {
	
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
		gerarchie = new HashMap<>();
	}
	
	public void leggiDaFileGerarchie() {
		this.gerarchie = JsonIO.leggiGerarchieDaJson(PATH_GERARCHIE);
	}
	
	public void salvaGerarchie() {
        JsonIO.salvaOggettoSuJson(PATH_GERARCHIE, this.gerarchie);
	}
	
	public boolean isGerarchiePresenti() {
		leggiDaFileGerarchie();
		
		if(gerarchie.size() == 0)
			return false;
		else
			return true;
	}
	
	public HashMap<String, Gerarchia> getGerarchie(){
		leggiDaFileGerarchie();
		return this.gerarchie;
	}
	
	/**
	 * Gestisce tutte le operazioni per la creazione di una gerarchia
	 * @return TRUE se la gerarchia e' stata creata correttamente FALSE se e' stata eliminata
	 */
	public boolean creaGerarchia() {
		Categoria currentRoot;
		Gerarchia currentGerarchia = new Gerarchia();
		boolean eliminaRoot = false;
		
		String nomeRoot = askNomeRoot();
		currentRoot = RepositoryGerarchia.creaRoot(currentGerarchia, nomeRoot);	
		currentGerarchia.setRoot(currentRoot);
		
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
				RepositoryGerarchia.creaSottoCategorie(currentGerarchia);
				break;
			case 2:
				RepositoryGerarchia.checkEliminaSottocategoria(currentGerarchia);
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
			gerarchie.put(currentGerarchia._getNomeFormattato(), currentGerarchia);
			return true;
		}
		
	}
	
	/**
	 * Chiede che nome utilizzare per il root.
	 * Verifica se il nome e' già in uso. Nel caso lo fosse viene richiesto di usare un altro nome
	 * @return nome utilizzabile
	 */
	private String askNomeRoot() {
		String nome;
		nome = InputDati.leggiStringaNonVuota("Nome Categoria Radice: ");
		
		while(gerarchiaPresente(nome)) {
			nome = InputDati.leggiStringaNonVuota("nome: " + nome + " già in uso per un'altra radice! Scegli un nome univoco per la radice: ");
		}
		
		return nome;
	}
	
	public boolean gerarchiaPresente(String nome) {
		return gerarchie.containsKey(this.formattaNome(nome));
	}
	
	private String formattaNome(String nome) {
		return nome.toUpperCase();
	}
	
	/**
	 * Permette di ricreare la hashmap ElencoCategoria di ogni Gerarchia salvata su JSON
	 */
	public void popolaGerarchie() {
		for (Gerarchia gerarchia : gerarchie.values()) {
			gerarchia.popolaElencoCategorie(gerarchia.getRoot());
		}
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		
		if(this.gerarchie.size() == 0) {
			result.append("\nNESSUNA GERARCHIA PRESENTE\n");
		} else {
			result.append("---------------------------\n");
			for (Gerarchia gerarchia : gerarchie.values()) {
				result.append(gerarchia.toString());
			}
		}
				
		
		return result.toString();		
	}
}
