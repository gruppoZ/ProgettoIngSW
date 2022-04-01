package gestioneOfferte;

import java.util.HashMap;
import java.util.List;

import gestioneCategorie.CampoCategoria;
import gestioneCategorie.Categoria;
import gestioneCategorie.Gerarchia;
import it.unibs.fp.mylib.InputDati;

public class ViewArticolo {

	GestioneArticolo gestoreArticolo = new GestioneArticolo();

	
	
	public void aggiungiArticolo() {
		Categoria foglia = scegliFoglia(); //controllo return null
		HashMap<CampoCategoria, String> valoriCampi = inserisciValoriCampi(foglia);
		
		gestoreArticolo.creaArticolo(foglia, valoriCampi);
	}
	
	private HashMap<CampoCategoria, String> inserisciValoriCampi(Categoria foglia) {
		HashMap<CampoCategoria, String> valoriCampi = new HashMap<CampoCategoria, String>();
		
		for (CampoCategoria campo: foglia._getCampiNativiEreditati()) {
			System.out.println(campo.getDescrizione());
			if(campo.isObbligatorio()) {
				System.out.println("Se non si compila il campo la pubblicazione dell'articolo verra' annullata");
				if(InputDati.yesOrNo("Vuoi compilare il campo? ")) {
					String valoreCampo = InputDati.leggiStringaNonVuota("Inserire il valore del campo");
					valoriCampi.put(campo, valoreCampo);//gestoreArticoli.aggiungiValoreCampo()
				} else {
					//finire la creazione del articolo
					//possibile return null
					
					//oppure qua non si fa niente e il controllo relativo all'obbligatorieta' dei campi lo si fa fuori
				}
			} else {
				if(InputDati.yesOrNo("Vuoi compilare il campo? ")) {
					String valoreCampo = InputDati.leggiStringaNonVuota("Inserire il valore del campo");
					valoriCampi.put(campo, valoreCampo);
				}
			}
		}
		
		return valoriCampi;
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
	
	private Categoria scegliFoglia() {
		
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
				return gestoreArticolo.getCategoriaByName(gerarchia, nomeFogliaSelezionata);
				
			} else {
				System.out.println("Attenzione! Il nome della foglia inserito non e' presente");
			}
			
		} else {
			System.out.println("Attenzione! Il nome della root non fa riferimento a nessuna gerarchia");
		}
		return null;
	}
}
