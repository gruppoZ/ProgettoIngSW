package gestioneCategorie;

import java.util.ArrayList;
import java.util.List;

import it.unibs.fp.mylib.InputDati;

public class RepositoryGerarchia {
	
	private static final int NUM_MIN_SOTTOCATEGORIE = 2;
	
	/**
	 * Si sceglie la categoria da ramificare in base al nome
	 * @param gerarchia
	 * @return 
	 */
	private static Categoria scegliCategoriaDaRamificare(Gerarchia gerarchia) {
		String nomeCategoriaDaRamificare;
		nomeCategoriaDaRamificare = InputDati.leggiStringaNonVuota("Quale categoria desideri ramificare?");
		
		
		while(!gerarchia.checkNomeCategoriaEsiste(nomeCategoriaDaRamificare)) {
			nomeCategoriaDaRamificare = InputDati.leggiStringaNonVuota("Categoria non trovata. Scegliere una categoria esistente:");
		}
		
		return gerarchia._getCategoriaByName(nomeCategoriaDaRamificare);
	}
	
	/**
	 * Chiede all'utente se inserire Campi Nativi per la categoria in gestione e ne gestisce l'inserimento
	 * @return lista di campi nativi creati
	 */
	private static List<CampoCategoria> askCampiNativi(List<CampoCategoria> campiEreditati) {
		List<CampoCategoria> campi = new ArrayList<CampoCategoria>();
		String descrizione;
		boolean obbligatorio, aggiungereAltriCampi;
		
		do {
			descrizione = InputDati.leggiStringaNonVuota("\tCampo Nativo -> Descrizione: ");
			while(!(checkUnicitaCampo(campiEreditati, descrizione) && checkUnicitaCampo(campi, descrizione))) {
				descrizione = InputDati.leggiStringaNonVuota("Campo gia' esistente. Scegli una descrizione diversa\n"
						+ "\tCampo Nativo -> Descrizione: ");
			}
			
			
			obbligatorio = InputDati.yesOrNo("\tCampo Nativo -> E' obbligatorio?(di default è facoltativo)");
			
			campi.add(new CampoCategoria(descrizione, obbligatorio));
			
			aggiungereAltriCampi = InputDati.yesOrNo("Inserire altri Campi Nativi per questa categoria?");
		}while(aggiungereAltriCampi);

		return campi;
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
	
	/**
	 * Richiede il nome della categoria da eliminare e ne gestisce l'eliminazione
	 * @param gerarchia
	 */
	protected static void checkEliminaSottocategoria(Gerarchia gerarchia) {
		String nomeDaEliminare = InputDati.leggiStringaNonVuota("Inserisci nome da eliminare");
		Categoria categoriaDaEliminare;

		if(gerarchia.checkNomeCategoriaEsiste(nomeDaEliminare)) {
			if(nomeDaEliminare.equalsIgnoreCase(gerarchia.getRoot().getNome())) {
				System.out.println("Stai provando ad eliminare la radice della gerarchia!");
			} else {	
				categoriaDaEliminare = gerarchia._getCategoriaByName(nomeDaEliminare);
				if(gerarchia.cercaPadre(categoriaDaEliminare).numeriDiSottocategorie() == NUM_MIN_SOTTOCATEGORIE) {
					System.out.println("\nStai cercando di eliminare una sottocategoria di una categoria che ne ha soltanto " + NUM_MIN_SOTTOCATEGORIE);
					System.out.println("Procedi quindi a creare prima almeno un' altra sottocategoria");
				} else {
					gerarchia.eliminaCategoria(nomeDaEliminare);
					System.out.println("La categoria: "+nomeDaEliminare+" e' stata eliminata correttamente");
				}
			}
			
		} else {
			System.out.println("La categoria cercata non esiste");
		}
	}
	
	
	/**
	 * Gestisce tutta la creazione della radice di una gerarchia
	 * @param gerarchia
	 * @param _nome
	 * @return la radice della gerarchia
	 */
	protected static Categoria creaRoot(Gerarchia gerarchia, String _nome)  {
		
		/*@requires gerarchia != null @*/
		
		String nome, descrizione;
		int depth;
		boolean ask_campoNativo;
		boolean isRoot = true;
		Categoria categoria_result;
		
		List<CampoCategoria> campiNativi = new ArrayList<CampoCategoria>();
		List<CampoCategoria> campiEreditati = new ArrayList<CampoCategoria>();

		addCampiDefaultRoot(campiNativi);
		
		nome = _nome;
		descrizione = InputDati.leggiStringaNonVuota("Descrizione Categoria: ");
		ask_campoNativo = InputDati.yesOrNo("Inserire Campo Nativo?");
		depth = 0;
		
		if (ask_campoNativo) {		
			for (CampoCategoria campo : (ArrayList<CampoCategoria>) askCampiNativi(campiEreditati)) {
				campiNativi.add(campo);
			}
		}
		categoria_result = new Categoria(nome, descrizione, isRoot, campiNativi, campiEreditati);
			
		categoria_result.setProfondita(depth);
		gerarchia.addCategoriaInElenco(nome, categoria_result);
		
		return categoria_result;
	}

	/**
	 * Gestisce tutta la creazione di una categoria
	 * @param gerarchia
	 * @param categoriaPadre
	 * @return la categoria creata
	 */
	protected static Categoria creaCategoria(Gerarchia gerarchia, Categoria categoriaPadre) {
		String nome, descrizione;
		boolean isRoot = false;
		int depth;
		boolean ask_campoNativo;
		
		List<CampoCategoria> campiNativi = new ArrayList<CampoCategoria>();
		List<CampoCategoria> campiEreditati = new ArrayList<CampoCategoria>();
		
		Categoria categoriaResult;

		nome = askNomeCategoria(gerarchia, categoriaPadre.getNome());
		descrizione = InputDati.leggiStringaNonVuota(categoriaPadre.getNome() + "->"+nome+"->Descrizione Categoria: ");
		
		depth = categoriaPadre.getProfondita() + 1;
		campiEreditati = categoriaPadre._getCampiNativiEreditati();
		
		ask_campoNativo = InputDati.yesOrNo(categoriaPadre.getNome() + "->"+nome+"->Inserire Campo Nativo?");
		
		if (ask_campoNativo)
			campiNativi = (ArrayList<CampoCategoria>) askCampiNativi(campiEreditati);
			
		categoriaResult = new Categoria(nome, descrizione, isRoot, campiNativi, campiEreditati);
		
		categoriaResult.setProfondita(depth);
		gerarchia.addCategoriaInElenco(nome ,categoriaResult);
		
		return categoriaResult;
	}
	
	public static void addCampiDefaultRoot(List<CampoCategoria> campiNativi) {
		campiNativi.add(new CampoCategoria("Stato di conservazione", true));
		campiNativi.add(new CampoCategoria("Descrizione libera", false));
	}
	
	private static String askNomeCategoria(Gerarchia gerarchia, String nome_padre) {
		String nome;
		nome = InputDati.leggiStringaNonVuota(nome_padre + "->Nome Categoria: ");
		
		while(gerarchia.checkNomeCategoriaEsiste(nome)) {
			nome = InputDati.leggiStringaNonVuota("nome: " + nome + " già in uso per un'altra categoria! Scegli un nome univoco per la categoria: ");
		}
		
		return nome;
	}
	
	
	/**
	 * Permette e gestisce l'inserimento di sottocategorie di una data categoria richiesta
	 */
	protected static void creaSottoCategorie(Gerarchia gerarchia) {
        int nSottocategorieDaInserire, nSottoCategorie;
        Categoria categoriaDaRamificare;

        categoriaDaRamificare = scegliCategoriaDaRamificare(gerarchia);
        nSottoCategorie = categoriaDaRamificare.numeriDiSottocategorie();
        		
        if(nSottoCategorie >= NUM_MIN_SOTTOCATEGORIE)
            nSottocategorieDaInserire = InputDati.leggiInteroConMinimo("Quante sottoCategorie vuoi inserire?", 1);
        else
            nSottocategorieDaInserire = InputDati.leggiInteroConMinimo("Quante sottoCategorie vuoi inserire? (minimo 2): ", 2);

        for(int i=0; i<nSottocategorieDaInserire; i++) {
            System.out.println("Sottocategoria#" + (nSottoCategorie+i+1));
            Categoria result = creaCategoria(gerarchia, categoriaDaRamificare);
            categoriaDaRamificare.addSottoCategoria(result);
        }
    }
	
	protected static void showGerarchia(Gerarchia gerarchia) {
		System.out.println("-----------------------------------");
		System.out.println(gerarchia.getRoot().showCategoriaDettagliata());
	}
}
