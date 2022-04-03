package gestioneOfferte;

import java.util.ArrayList;
import java.util.List;

import gestioneCategorie.CampoCategoria;
import gestioneCategorie.Categoria;
import gestioneCategorie.Gerarchia;
import gestioneUtenti.GestioneFruitore;
import it.unibs.fp.mylib.InputDati;

public class ViewArticolo {

	private GestioneFruitore gestoreFruitore;
	
	public ViewArticolo(GestioneFruitore gestoreFruitore) {
		this.gestoreFruitore = gestoreFruitore;
	}
	GestioneArticolo gestoreArticolo = new GestioneArticolo();
	
	public void aggiungiArticolo() {
		gestoreArticolo.init();
		//forse bisogna reinizializzare articolo
		if(scegliFoglia()){
			if(inserisciValoriCampi()) {
				System.out.println("La pubblicazione e' stata accettata");
				gestoreArticolo.creaPubblicazione(gestoreFruitore.getUsername());
				gestoreArticolo.addPubblicazione();
				gestoreArticolo.salvaPubblicazioni();
			}
		}
	}
	
	private boolean compilaCampiObbligatori(List<CampoCategoria> campi) {
		List<CampoCategoria> campiObbligatori = new ArrayList<>();
		
		for (CampoCategoria campo : campi) {
			if(campo.isObbligatorio()) {
				campiObbligatori.add(campo);
			}
		}
		
		for (CampoCategoria campo : campiObbligatori) {
			System.out.println(campo.getDescrizione());
			System.out.println("Se non si compila il campo la pubblicazione dell'articolo verra' annullata");
			if(InputDati.yesOrNo("Vuoi compilare il campo? ")) {
				String valoreCampo = InputDati.leggiStringaNonVuota("Inserire il valore del campo: ");
				gestoreArticolo.addValoreCampo(campo, valoreCampo);
			} else {
				return false;
			}
		}
		
		return true;
	}
	private void compilaCampiFacoltativi(List<CampoCategoria> campi) {
		List<CampoCategoria> campiFacoltativi = new ArrayList<>();
		
		for (CampoCategoria campo : campi) {
			if(!campo.isObbligatorio()) {
				campiFacoltativi.add(campo);
			}
		}
		
		for (CampoCategoria campo : campiFacoltativi) {
			System.out.println(campo.getDescrizione());
			if(InputDati.yesOrNo("Vuoi compilare il campo? ")) {
				String valoreCampo = InputDati.leggiStringaNonVuota("Inserire il valore del campo: ");
				gestoreArticolo.addValoreCampo(campo, valoreCampo);
			}
		}
	}
	
	
	private boolean inserisciValoriCampi() {
		List<CampoCategoria> campi = gestoreArticolo.getListaCampi();
		
		if(compilaCampiObbligatori(campi)) {
			compilaCampiFacoltativi(campi);
			//TODO: dire al gestore che la creazione e' terminata con successo
			return true;
		}  else {
			System.out.println("La compilazione del articolo non e' andata a buon fine");
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
	
	private void stampaGerarchie() {
		for (Gerarchia gerarchia : gestoreArticolo.getGerarchie().values()) {
			System.out.println(gerarchia.showGerarchia());
		}
	}
	
	private boolean scegliFoglia() {
		
		stampaGerarchie();
		
		String nomeRootSelezionata = InputDati.leggiStringaNonVuota("Inserisci il nome della root relativa"
				+ " alla gerarchia che si vuole scegliere: ");
		
		//possibile evitare di fare i due passaggi e fare solo get, se ritorna null => non esiste (pero' dovrei fare un controllo nella view)
		//cosa che faccio gia' con checkEsistenzaCategoria quindi a sto punto
		if(gestoreArticolo.checkEsistenzaGerarchia(nomeRootSelezionata)) {
			Gerarchia gerarchia = gestoreArticolo.getGerarchiaByName(nomeRootSelezionata);
			//aggiunto un metodo in Gerarchia che permette di prendere la lista delle foglie
			//ATTENZIONE! nel metodo uso la hashmap che pero' non e' stata ripopolata quando si carica da file!
			List<Categoria> listaFoglie = gestoreArticolo.getListaFoglie(gerarchia); 
 
			for (Categoria categoria : listaFoglie) {
				System.out.println(categoria);
			}
			String nomeFogliaSelezionata = InputDati.leggiStringaNonVuota("Inserire il nome della foglia desiderata: ");
			if(gestoreArticolo.checkEsistenzaCategoria(gerarchia, nomeFogliaSelezionata)) {
				Categoria foglia = gestoreArticolo.getCategoriaByName(gerarchia, nomeFogliaSelezionata);
				gestoreArticolo.addFoglia(foglia);
				return true;
			} else {
				System.out.println("Attenzione! Il nome della foglia inserito non e' presente");
			}	
		} else {
			System.out.println("Attenzione! Il nome della root non fa riferimento a nessuna gerarchia");
		}
		return false;
	}
}
