package gestioneCategorie;

import java.util.ArrayList;
import java.util.List;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class ViewGerarchia {
	private static final String ASK_CATEGORIA_DA_RAMIFICARE = "Quale categoria desideri ramificare?";
	private static final String ASK_NOME_ROOT = "Nome Categoria Radice: ";
	private static final String ASK_CONTINUARE_MODIFICA_GERARCHIA = "Continuare a modificare la Gerarchia? "
			+ "Se no, non sarà più possibile modificarla";
	private static final String ASK_INSERIRE_ALTRI_CAMPI_NATIVI = "Inserire altri Campi Nativi per questa categoria?";
	private static final String ASK_CAMPO_NATIVO_OBBLIGATORIETA = "\tCampo Nativo -> E' obbligatorio?(di default è facoltativo)";
	private static final String ASK_CAMPO_NATIVO_DESCRIZIONE = "\tCampo Nativo -> Descrizione: ";
	private static final String ASK_CAMPO_NATIVO = "Inserire Campo Nativo?";
	private static final String ASK_DESCRIZIONE_CATEGORIA = "Descrizione Categoria: ";
	private static final String MSG_CATEGORIA_INESISTENE_ASK_AGAIN = "Categoria non trovata. Scegliere una categoria esistente:";
	private static final String MSG_CAMPO_GIA_ESISTENTE_ASK_DESCRIZIONE_DIVERSA = "Campo gia' esistente. Scegli una descrizione diversa\n";
	
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
	
	private GestioneGerarchie gestoreGerarchie;
	
	public ViewGerarchia() {
		gestoreGerarchie = new GestioneGerarchie();
	}

	/**
	 * Chiede che nome utilizzare per il root.
	 * Verifica se il nome e' già in uso. Nel caso lo fosse viene richiesto di usare un altro nome
	 * @return nome utilizzabile
	 */
	private String askNomeRoot() {
		String nome;
		nome = InputDati.leggiStringaNonVuota(ASK_NOME_ROOT);
		
		while(gestoreGerarchie.checkGerarchiaPresente(nome)) {
			nome = InputDati.leggiStringaNonVuota("nome: " + nome + " già in uso per un'altra radice! Scegli un nome univoco per la radice: ");
		}
		
		return nome;
	}
	
	private String askNomeCategoria(String nome_padre) {
		String nome;
		nome = InputDati.leggiStringaNonVuota(nome_padre + "->Nome Categoria: ");
		
		while(gestoreGerarchie.checkNomeCategoriaEsiste(nome)) {
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
        	
        int numSottoCategorieMinime = gestoreGerarchie.getNumSottoCatDaInserire(categoriaDaRamificare);
        
        nSottocategorieDaInserire = InputDati.leggiInteroConMinimo("Quante sottoCategorie vuoi inserire? (minimo " + numSottoCategorieMinime +"): ", numSottoCategorieMinime);
            
        for(int i=0; i<nSottocategorieDaInserire; i++) {
            System.out.println("Sottocategoria#" + (nSottoCategorie+i+1));
            Categoria result = creaCategoria(categoriaDaRamificare);
            categoriaDaRamificare.addSottoCategoria(result);
        }
    }
	
	/**
	 * Si sceglie la categoria da ramificare in base al nome
	 * @param gerarchia
	 * @return 
	 */
	private Categoria scegliCategoriaDaRamificare() {
		String nomeCategoriaDaRamificare;
		nomeCategoriaDaRamificare = InputDati.leggiStringaNonVuota(ASK_CATEGORIA_DA_RAMIFICARE);
		
		
		while(!gestoreGerarchie.checkNomeCategoriaEsiste(nomeCategoriaDaRamificare)) {
			nomeCategoriaDaRamificare = InputDati.leggiStringaNonVuota(MSG_CATEGORIA_INESISTENE_ASK_AGAIN);
		}
		
		return gestoreGerarchie.getCategoriaByName(nomeCategoriaDaRamificare);
	}
	
	private void creaRoot() {					
		String nome, descrizione;
		boolean ask_campoNativo;
		boolean isRoot = true;
		Categoria categoria_result;
		
		List<CampoCategoria> campiNativi = new ArrayList<CampoCategoria>();
		List<CampoCategoria> campiEreditati = new ArrayList<CampoCategoria>();

		nome = askNomeRoot();
		
		gestoreGerarchie.addCampiDefaultRoot(campiNativi);
		
		descrizione = InputDati.leggiStringaNonVuota(ASK_DESCRIZIONE_CATEGORIA);
		ask_campoNativo = InputDati.yesOrNo(ASK_CAMPO_NATIVO);
		
		if (ask_campoNativo) {		
			for (CampoCategoria campo : (ArrayList<CampoCategoria>) askCampiNativi(campiEreditati)) {
				campiNativi.add(campo);
			}
		}
		
		categoria_result = new Categoria(nome, descrizione, isRoot, campiNativi, campiEreditati);
		
		gestoreGerarchie.creaRoot(categoria_result);
	}
	
	/**
	 * Gestisce tutta la creazione di una categoria
	 * @param gerarchia
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
		
		
		campiEreditati = categoriaPadre._getCampiNativiEreditati();
		
		ask_campoNativo = InputDati.yesOrNo(categoriaPadre.getNome() + "->"+nome+"->Inserire Campo Nativo?");
		
		if (ask_campoNativo)
			campiNativi = (ArrayList<CampoCategoria>) askCampiNativi(campiEreditati);
			
		categoriaResult = new Categoria(nome, descrizione, isRoot, campiNativi, campiEreditati);
		
		gestoreGerarchie.creaSottoCategoria(categoriaPadre, categoriaResult);
		
		return categoriaResult;
	}
	
	/**
	 * Chiede all'utente se inserire Campi Nativi per la categoria in gestione e ne gestisce l'inserimento
	 * @return lista di campi nativi creati
	 */
	private List<CampoCategoria> askCampiNativi(List<CampoCategoria> campiEreditati) {
		List<CampoCategoria> campi = new ArrayList<CampoCategoria>();
		String descrizione;
		boolean obbligatorio, aggiungereAltriCampi;
		
		do {
			descrizione = InputDati.leggiStringaNonVuota(ASK_CAMPO_NATIVO_DESCRIZIONE);
			while(!(gestoreGerarchie.checkUnicitaCampo(campiEreditati, descrizione) && gestoreGerarchie.checkUnicitaCampo(campi, descrizione))) {
				descrizione = InputDati.leggiStringaNonVuota(MSG_CAMPO_GIA_ESISTENTE_ASK_DESCRIZIONE_DIVERSA
						+ ASK_CAMPO_NATIVO_DESCRIZIONE);
			}
			
			
			obbligatorio = InputDati.yesOrNo(ASK_CAMPO_NATIVO_OBBLIGATORIETA);
			
			campi.add(new CampoCategoria(descrizione, obbligatorio));
			
			aggiungereAltriCampi = InputDati.yesOrNo(ASK_INSERIRE_ALTRI_CAMPI_NATIVI);
		}while(aggiungereAltriCampi);

		return campi;
	}
	
	public void menu() {
		MyMenu menuModificaGiorni = new MyMenu(TXT_TITOLO, TXT_VOCI);
		int scelta = 0;
		boolean fine = false;
		
		creaRoot();		
		
		do {
			scelta = menuModificaGiorni.scegli();
			switch(scelta) {
			case 0:
				fine = !InputDati.yesOrNo(ASK_CONTINUARE_MODIFICA_GERARCHIA);
				break;
			case 1:
				this.creaSottoCategorie();
				break;
			default:
				System.out.println(TXT_ERRORE);
			}
		} while(!fine);
		
		gestoreGerarchie.fineCreazioneGerarchia();
	}		
}
