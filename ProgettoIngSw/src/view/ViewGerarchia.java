package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.CampoCategoria;
import application.Categoria;
import application.Gerarchia;
import controller.GestioneGerarchie;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class ViewGerarchia {
	
	private static final String MSG_ERROR_SALVATAGGIO_GERARCHIA_APPENA_CREATA = "*** ERRORE salvataggio gerarchia appena creata ***";
	private static final String MSG_ERROR_INIT_GERARCHIE = "*** ERRORE Inizializzazione Gerarchie ***";
	private static final String MSG_ERROR_NOME_ROOT_INESISTENTE = "Attenzione! Il nome della root non fa riferimento a nessuna gerarchia";
	private static final String MSG_ERROR_CATEGORIA_FOGLIA_INESISTENTE = "Attenzione! Il nome della foglia inserito non e' presente";
	
	private static final String MSG_GERARCHIA_RIMOSSA_SUCCESSO = "\nGerarchia eliminata con successo";
	private static final String MSG_GERARCHIA_CREATA_CORRETTAMENTE = "*** Gerarchia creata correttamente ***";
	
	private static final String ASK_CONFERNA_RIMOZIONE_GERARCHIA = "Sei sicuro di eliminare la gerarchia?";

	private static final String GIVE_NOME_CATEGORIA_ELIMINATA_CORRETTAMENTE = "La categoria: %s e' stata eliminata correttamente.\n";
	
	private static final String ASK_CATEGORIA_FOGLIA = "Inserire il nome della foglia desiderata: ";
	private static final String ASK_NOME_ROOT_CATEGORIA_SCELTA = "Inserisci il nome della root relativa alla gerarchia che si vuole scegliere: ";
	private static final String ASK_NOME_CATEGORIA_DA_ELIMINARE = "Inserisci nome categoria da eliminare";
	private static final String ASK_CATEGORIA_DA_RAMIFICARE = "Quale categoria desideri ramificare?";
	private static final String ASK_NOME_ROOT = "Nome Categoria Radice: ";
	private static final String ASK_CONTINUARE_MODIFICA_GERARCHIA = "Continuare a modificare la Gerarchia? "
			+ "Se no, non sarà più possibile modificarla";
	private static final String ASK_INSERIRE_ALTRI_CAMPI_NATIVI = "Inserire altri Campi Nativi per questa categoria?";
	private static final String ASK_CAMPO_NATIVO_OBBLIGATORIETA = "\tCampo Nativo -> E' obbligatorio?(di default è facoltativo)";
	private static final String ASK_CAMPO_NATIVO_DESCRIZIONE = "\tCampo Nativo -> Descrizione: ";
	private static final String ASK_CAMPO_NATIVO = "Inserire Campo Nativo?";
	private static final String ASK_DESCRIZIONE_CATEGORIA = "Descrizione Categoria: ";
	
	private static final String MSG_ATTENZIONE_TENTATIVO_RIMOZIONE_ROOT = "Stai provando ad eliminare la radice della gerarchia!";
	private static final String MSG_CATEGORIA_INESISTENTE = "La categoria cercata non esiste";
	private static final String MSG_CATEGORIA_INESISTENE_ASK_AGAIN = "Categoria non trovata. Scegliere una categoria esistente:";
	private static final String MSG_CAMPO_GIA_ESISTENTE_ASK_DESCRIZIONE_DIVERSA = "Campo gia' esistente. Scegli una descrizione diversa\n";
	private static final String MSG_ATTENZIONE_RIMOZIONE_CATEGORIA_DI_CATPADRE_CON_MIN_NUM_SOTCAT = "\nStai cercando di eliminare una sottocategoria di una categoria che ne ha soltanto %s."
			+ "																						\nProcedi quindi a creare prima almeno un' altra sottocategoria.\n";
	//costanti per menu
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
	
	private GestioneGerarchie gestoreGerarchia;
	
	public ViewGerarchia() throws IOException {
		try {
			gestoreGerarchia = new GestioneGerarchie();
		} catch (IOException e) {
			throw new IOException(MSG_ERROR_INIT_GERARCHIE);
		}
	}
	
	public void update() throws IOException {
		try {
			this.gestoreGerarchia.updateGerarchie();
		} catch (IOException e) {
			throw new IOException(MSG_ERROR_INIT_GERARCHIE);
		}
	}

	/**
	 * Chiede che nome utilizzare per il root.
	 * Verifica se il nome e' già in uso. Nel caso lo fosse viene richiesto di usare un altro nome
	 * @return nome utilizzabile
	 * @throws IOException 
	 */
	private String askNomeRoot() throws IOException {
		String nome = InputDati.leggiStringaNonVuota(ASK_NOME_ROOT);

		try {
			while(gestoreGerarchia.checkGerarchiaPresente(nome)) {
				nome = InputDati.leggiStringaNonVuota("nome: " + nome + " già in uso per un'altra radice! Scegli un nome univoco per la radice: ");
			}
		} catch (IOException e) {
			throw new IOException(MSG_ERROR_INIT_GERARCHIE);
		}
		
		return nome;
	}
	
	private String askNomeCategoria(String nome_padre) {
		String nome;
		nome = InputDati.leggiStringaNonVuota(nome_padre + "->Nome Categoria: ");
		
		while(gestoreGerarchia.checkNomeCategoriaEsiste(nome)) {
			nome = InputDati.leggiStringaNonVuota("nome: " + nome + " già in uso per un'altra categoria! Scegli un nome univoco per la categoria: ");
		}
		
		return nome;
	}
	
	/**
	 * Permette e gestisce l'inserimento di sottocategorie di una data categoria richiesta
	 */
	private void creaSottoCategorie() {
        int nSottocategorieDaInserire, nSottoCategorie;
        Categoria categoriaDaRamificare;

        categoriaDaRamificare = scegliCategoriaDaRamificare();
        nSottoCategorie = categoriaDaRamificare.numeriDiSottocategorie();
        	
        int numSottoCategorieMinime = gestoreGerarchia.getNumSottoCatDaInserire(categoriaDaRamificare);
        
        nSottocategorieDaInserire = InputDati.leggiInteroConMinimo("Quante sottoCategorie vuoi inserire? (minimo " + numSottoCategorieMinime +"): ", numSottoCategorieMinime);
            
        for(int i=0; i<nSottocategorieDaInserire; i++) {
            System.out.println("Sottocategoria#" + (nSottoCategorie+i+1));
            Categoria result = creaCategoria(categoriaDaRamificare);
            categoriaDaRamificare.addSottoCategoria(result);
        }
    }
	
	/**
	 * Si sceglie la categoria da ramificare in base al nome
	 * @return 
	 */
	private Categoria scegliCategoriaDaRamificare() {
		String nomeCategoriaDaRamificare = InputDati.leggiStringaNonVuota(ASK_CATEGORIA_DA_RAMIFICARE);
		
		
		while(!gestoreGerarchia.checkNomeCategoriaEsiste(nomeCategoriaDaRamificare)) {
			nomeCategoriaDaRamificare = InputDati.leggiStringaNonVuota(MSG_CATEGORIA_INESISTENE_ASK_AGAIN);
		}
		
		return gestoreGerarchia.getCategoriaByName(nomeCategoriaDaRamificare);
	}
	
	private void creaRoot() throws IOException {					
		String nome, descrizione;
		boolean ask_campoNativo;
		boolean isRoot = true;
		Categoria categoria_result;
		
		List<CampoCategoria> campiNativi = new ArrayList<CampoCategoria>();
		List<CampoCategoria> campiEreditati = new ArrayList<CampoCategoria>();

		nome = askNomeRoot();
		
		gestoreGerarchia.addCampiDefaultRoot(campiNativi);
		
		descrizione = InputDati.leggiStringaNonVuota(ASK_DESCRIZIONE_CATEGORIA);
		ask_campoNativo = InputDati.yesOrNo(ASK_CAMPO_NATIVO);
		
		if (ask_campoNativo) {		
			for (CampoCategoria campo : (ArrayList<CampoCategoria>) askCampiNativi(campiEreditati)) {
				campiNativi.add(campo);
			}
		}
		
		categoria_result = new Categoria(nome, descrizione, isRoot, campiNativi, campiEreditati);
		
		gestoreGerarchia.creaRoot(categoria_result);
	}
	
	/**
	 * Gestisce tutta la creazione di una categoria
	 * @param categoriaPadre
	 * @return la categoria creata
	 */
	protected Categoria creaCategoria(Categoria categoriaPadre) {
		String nome, descrizione;
		boolean isRoot = false;
		boolean ask_campoNativo;
		
		List<CampoCategoria> campiNativi = new ArrayList<CampoCategoria>();
		List<CampoCategoria> campiEreditati = new ArrayList<CampoCategoria>();
		
		Categoria categoriaResult;

		nome = askNomeCategoria(categoriaPadre.getNome());
		descrizione = InputDati.leggiStringaNonVuota(categoriaPadre.getNome() + "->"+nome+"->Descrizione Categoria: ");
		
		
		campiEreditati = categoriaPadre.getCampiNativiEreditati();
		
		ask_campoNativo = InputDati.yesOrNo(categoriaPadre.getNome() + "->"+nome+"->Inserire Campo Nativo?");
		
		if (ask_campoNativo)
			campiNativi = (ArrayList<CampoCategoria>) askCampiNativi(campiEreditati);
			
		categoriaResult = new Categoria(nome, descrizione, isRoot, campiNativi, campiEreditati);
		
		gestoreGerarchia.creaSottoCategoria(categoriaPadre, categoriaResult);
		
		return categoriaResult;
	}
	
	/**
	 * Chiede all'utente se inserire Campi Nativi per la categoria in gestione e ne gestisce l'inserimento
	 * 
	 * @param campiEreditati
	 * @return lista di campi nativi creati
	 */
	private List<CampoCategoria> askCampiNativi(List<CampoCategoria> campiEreditati) {
		List<CampoCategoria> campi = new ArrayList<CampoCategoria>();
		String descrizione;
		boolean obbligatorio, aggiungereAltriCampi;
		
		do {
			descrizione = InputDati.leggiStringaNonVuota(ASK_CAMPO_NATIVO_DESCRIZIONE);
			while(!(gestoreGerarchia.checkUnicitaCampo(campiEreditati, descrizione) && gestoreGerarchia.checkUnicitaCampo(campi, descrizione))) {
				descrizione = InputDati.leggiStringaNonVuota(MSG_CAMPO_GIA_ESISTENTE_ASK_DESCRIZIONE_DIVERSA
						+ ASK_CAMPO_NATIVO_DESCRIZIONE);
			}
			
			obbligatorio = InputDati.yesOrNo(ASK_CAMPO_NATIVO_OBBLIGATORIETA);
			
			campi.add(new CampoCategoria(descrizione, obbligatorio));
			
			aggiungereAltriCampi = InputDati.yesOrNo(ASK_INSERIRE_ALTRI_CAMPI_NATIVI);
		}while(aggiungereAltriCampi);

		return campi;
	}
	
	private void eliminaSottoCategoria() {
		String nomeDaEliminare = InputDati.leggiStringaNonVuota(ASK_NOME_CATEGORIA_DA_ELIMINARE);
		
		if(gestoreGerarchia.checkNomeCategoriaEsiste(nomeDaEliminare)) {
			if(gestoreGerarchia.checkNomeIsNomeRoot(nomeDaEliminare)) {
				System.out.println(MSG_ATTENZIONE_TENTATIVO_RIMOZIONE_ROOT);
			} else {
				if(gestoreGerarchia.checkCategoriaDaEliminare(nomeDaEliminare)) {
					System.out.printf(MSG_ATTENZIONE_RIMOZIONE_CATEGORIA_DI_CATPADRE_CON_MIN_NUM_SOTCAT, gestoreGerarchia.getNumMinSottoCategorie());
				} else {
					gestoreGerarchia.eliminaCategoria(nomeDaEliminare);
					System.out.printf(GIVE_NOME_CATEGORIA_ELIMINATA_CORRETTAMENTE, nomeDaEliminare);
				}
			}
		} else {
			System.out.println(MSG_CATEGORIA_INESISTENTE);
		}				 
	}
	
	private boolean askEliminaGerarchia() {
		boolean confermaCancellazione = InputDati.yesOrNo(ASK_CONFERNA_RIMOZIONE_GERARCHIA);
		if(confermaCancellazione) {
			System.out.println(MSG_GERARCHIA_RIMOSSA_SUCCESSO);
			return true;
		} else {
			return false;
		}
	}
	
	public Categoria scegliFoglia() throws IOException {
		ViewCategoria viewCategoria = new ViewCategoria();
		try {
			gestoreGerarchia.getGerarchie().forEach((k, v) -> showGerarchiaSintetica(v));
			
			String nomeRootSelezionata = InputDati.leggiStringaNonVuota(ASK_NOME_ROOT_CATEGORIA_SCELTA);

			if(gestoreGerarchia.checkGerarchiaPresente(nomeRootSelezionata)) {
				Gerarchia gerarchia = gestoreGerarchia.getGerarchiaByName(nomeRootSelezionata);
				
				List<Categoria> listaFoglie = gerarchia.getListaFoglie(); 
	 
				for (Categoria categoria : listaFoglie) {
					System.out.println(viewCategoria.showCategoriaFoglia(categoria));
				}
				
				String nomeFogliaSelezionata = InputDati.leggiStringaNonVuota(ASK_CATEGORIA_FOGLIA);
				
				if(gerarchia.checkNomeCategoriaEsiste(nomeFogliaSelezionata)) {
					Categoria foglia = gerarchia.getCategoriaByName(nomeFogliaSelezionata);
					
					return foglia;
				} else {
					System.out.println(MSG_ERROR_CATEGORIA_FOGLIA_INESISTENTE);
				}	
			} else {
				System.out.println(MSG_ERROR_NOME_ROOT_INESISTENTE);
			}
		} catch (IOException e) {
			throw new IOException(MSG_ERROR_INIT_GERARCHIE);
		}
		return null;
	}	
	
	public void menu() throws IOException {
		MyMenu menu = new MyMenu(TXT_TITOLO, TXT_VOCI);
		int scelta = 0;
		boolean fine = false;
		boolean gerarchiaEliminata = false;
		creaRoot();		
		
		do {
			scelta = menu.scegli();
			switch(scelta) {
			case 0:
				fine = !InputDati.yesOrNo(ASK_CONTINUARE_MODIFICA_GERARCHIA);
				break;
			case 1:
				this.creaSottoCategorie();
				break;
			case 2:
				this.eliminaSottoCategoria();
				break;
			case 3:
				if(this.askEliminaGerarchia()) {
					fine = true;
					gerarchiaEliminata = true;
				}
				break;
			case 4:
				this.showGerarchiaInLavorazione();
				break;
			default:
				System.out.println(TXT_ERRORE);
			}
		} while(!fine);
		
		if(!gerarchiaEliminata) {
			try {
				gestoreGerarchia.fineCreazioneGerarchia();
				System.out.println(MSG_GERARCHIA_CREATA_CORRETTAMENTE);
			} catch (IOException e) {
				System.out.println(MSG_ERROR_SALVATAGGIO_GERARCHIA_APPENA_CREATA);
			}
		}
	}
	
	private void showGerarchiaInLavorazione() {
		try {
			Gerarchia gerarchiaInLavorazione = gestoreGerarchia.getGerarchiaInLavorazione();
			showGerarchia(gerarchiaInLavorazione);
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			//Per come è strutturato il programma, l'utente potrà accedere a questo menù soltanto dopo
			//aver inizializzato la gerarchia creando la categoria radice => gerarchiaInLavorazione != null.
		}
	}
	
	public void showGerarchia(Gerarchia gerarchia) {
		ViewCategoria viewCategoria = new ViewCategoria();
		StringBuilder sb = new StringBuilder();
		
		sb.append("******************************\n");
		sb.append("Gerarchia con Radice:  " + gerarchia.getRoot().getNome() + "\n");
		System.out.println(sb.toString());	
		
		System.out.println(viewCategoria.showCategoriaDettagliata(gerarchia.getRoot()));
	}
	
	public void showGerarchiaSintetica(Gerarchia gerarchia) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("******************************\n");
		sb.append("Nome: " + gerarchia.getRoot().getNome() + " | Descrizione: " + gerarchia.getRoot().getDescrizione() + "\n");
		
		System.out.println(sb.toString());	
	}
}
