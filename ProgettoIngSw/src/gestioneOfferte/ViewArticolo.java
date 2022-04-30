package gestioneOfferte;

import java.util.ArrayList;
import java.util.List;

import gestioneCategorie.CampoCategoria;
import gestioneCategorie.Categoria;
import gestioneCategorie.Gerarchia;
import gestioneUtenti.GestioneFruitore;
import it.unibs.fp.mylib.InputDati;

public class ViewArticolo {
	
	private static final String MSG_ERROR_NOME_ROOT_INESISTENTE = "Attenzione! Il nome della root non fa riferimento a nessuna gerarchia";
	private static final String MSG_ERROR_CATEGORIA_FOGLIA_INESISTENTE = "Attenzione! Il nome della foglia inserito non e' presente";
	private static final String MSG_ERROR_COMPILAZIONE_CAMPO = "La compilazione del articolo non e' andata a buon fine";
	
	private static final String MSG_ASK_CATEGORIA_FOGLIA = "Inserire il nome della foglia desiderata: ";
	private static final String MSG_ASK_NOME_ROOT_CATEGORIA_SCELTA = "Inserisci il nome della root relativa"
			+ " alla gerarchia che si vuole scegliere: ";
	private static final String MSG_ASK_COMPILAZIONE_CAMPO = "Vuoi compilare il campo? ";
	private static final String MSG_ASK_VALORE_CAMPO = "Inserire il valore del campo: ";
	
	private static final String MSG_WARNING_CAMPO_COMPILAZIONE_OBBLIGATORIA = "Se non si compila il campo la pubblicazione dell'articolo verra' annullata";
	
	private static final String MSG_PUBBLICAZIONE_ACCETTATA = "La pubblicazione e' stata accettata";
	
	private GestioneFruitore gestoreFruitore;
	private GestioneArticolo gestoreArticolo;
	
	public ViewArticolo(GestioneFruitore gestoreFruitore) {
		this.gestoreFruitore = gestoreFruitore;
		gestoreArticolo = new GestioneArticolo();
	}
	
	
	public void aggiungiArticolo() {
//		gestoreArticolo.init();
		//forse bisogna reinizializzare articolo
		if(scegliFoglia()){
			if(inserisciValoriCampi()) {
				System.out.println(MSG_PUBBLICAZIONE_ACCETTATA);
				
				gestoreArticolo.manageAggiuntaPubblicazione(gestoreFruitore.getUsername());
			}
		}
	}
	
	private boolean compilaCampiObbligatori() {
		List<CampoCategoria> campiObbligatori = gestoreArticolo.getListaCampiObbligatori();
		
		for (CampoCategoria campo : campiObbligatori) {
			System.out.println(campo.getDescrizione());
			System.out.println(MSG_WARNING_CAMPO_COMPILAZIONE_OBBLIGATORIA);
			if(InputDati.yesOrNo(MSG_ASK_COMPILAZIONE_CAMPO)) {
				String valoreCampo = InputDati.leggiStringaNonVuota(MSG_ASK_VALORE_CAMPO);
				
				gestoreArticolo.addValoreCampo(campo, valoreCampo);
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	private void compilaCampiFacoltativi() {
		List<CampoCategoria> campiFacoltativi = gestoreArticolo.getListaCampiFacoltativi();
		
		for (CampoCategoria campo : campiFacoltativi) {
			System.out.println(campo.getDescrizione());
			if(InputDati.yesOrNo(MSG_ASK_COMPILAZIONE_CAMPO)) {
				String valoreCampo = InputDati.leggiStringaNonVuota(MSG_ASK_VALORE_CAMPO);
				
				gestoreArticolo.addValoreCampo(campo, valoreCampo);
			}
		}
	}
	
	
	private boolean inserisciValoriCampi() {		
		if(compilaCampiObbligatori()) {
			compilaCampiFacoltativi();
			//TODO: dire al gestore che la creazione e' terminata con successo
			return true;
		}  else {
			System.out.println(MSG_ERROR_COMPILAZIONE_CAMPO);
			return false;
		}
	}
	
//	public boolean compilaCampo(HashMap<CampoCategoria, String> valoriCampi, CampoCategoria campo) {
//		if(InputDati.yesOrNo("Vuoi compilare il campo? ")) {
//			String valoreCampo = InputDati.leggiStringaNonVuota("Inserire il valore del campo");
//			valoriCampi.put(campo, valoreCampo);
//			return true;
//		}
//		return false;
//	}
	
	//TODO metodo ridondante in ViewOfferte
	private void stampaGerarchie() {
		System.out.println(gestoreArticolo.getGestoreGerarchie().getToStringSintetico());
	}
	
	/*TODO: forse metodo che va spostato in viewGerarchia?
		al posto di return boolean è void e si ritornano throw
	*/
	private boolean scegliFoglia() {
		
		stampaGerarchie();
		
		String nomeRootSelezionata = InputDati.leggiStringaNonVuota(MSG_ASK_NOME_ROOT_CATEGORIA_SCELTA);
		
		//possibile evitare di fare i due passaggi e fare solo get, se ritorna null => non esiste (pero' dovrei fare un controllo nella view)
		//cosa che faccio gia' con checkEsistenzaCategoria quindi a sto punto
		if(gestoreArticolo.checkEsistenzaGerarchia(nomeRootSelezionata)) {
			Gerarchia gerarchia = gestoreArticolo.getGerarchiaByName(nomeRootSelezionata);
			//aggiunto un metodo in Gerarchia che permette di prendere la lista delle foglie
			//ATTENZIONE! nel metodo uso la hashmap che pero' non e' stata ripopolata quando si carica da file!
			List<Categoria> listaFoglie = gestoreArticolo.getListaFoglieByGerarchia(gerarchia); 
 
			for (Categoria categoria : listaFoglie) {
				System.out.println(categoria);
			}
			
			String nomeFogliaSelezionata = InputDati.leggiStringaNonVuota(MSG_ASK_CATEGORIA_FOGLIA);
			
			if(gestoreArticolo.checkEsistenzaCategoria(gerarchia, nomeFogliaSelezionata)) {
				Categoria foglia = gestoreArticolo.getCategoriaByName(gerarchia, nomeFogliaSelezionata);
				gestoreArticolo.addFoglia(foglia);
				return true;
			} else {
				System.out.println(MSG_ERROR_CATEGORIA_FOGLIA_INESISTENTE);
			}	
		} else {
			System.out.println(MSG_ERROR_NOME_ROOT_INESISTENTE);
		}
		return false;
	}
	
//	public void showOfferteAperteByCategoria() {
//		stampaGerarchie();
//		//da fare tutti i controlli
//		String nomeCategoria = InputDati.leggiStringaNonVuota("Inserire nome foglia");
//		ArrayList<Offerta> listaOfferte = gestoreArticolo.leggiListaOfferte();
//		
//		/*
//		for (Offerta offerta : listaOfferte) {
//			if(pubblicazione.getTipoOfferta().equals(new OffertaAperta())) { //equals da modificare
//				if(pubblicazione.getArticolo().getFoglia().getNome().equals(nomeCategoria))
//					System.out.println(pubblicazione);
//			}
//			
//		}
//		*/
//	}
	
}
