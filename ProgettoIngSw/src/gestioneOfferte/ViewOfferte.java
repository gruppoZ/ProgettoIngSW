package gestioneOfferte;

import java.util.ArrayList;
import java.util.List;

import gestioneCategorie.Categoria;
import gestioneCategorie.Gerarchia;
import it.unibs.fp.mylib.InputDati;

public class ViewOfferte {

	private GestioneArticolo gestoreArticolo; //gestore articolo fa troppe cose, da creare più gestori
	
	public ViewOfferte() {
		gestoreArticolo = new GestioneArticolo();
	}
	
	private void stampaGerarchie() {
		for (Gerarchia gerarchia : gestoreArticolo.getGerarchie().values()) {
			System.out.println(gerarchia.showGerarchia());
		}
	}
	
	//metodo presente già in viewArticolo
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
				Categoria foglia = gestoreArticolo.getCategoriaByName(gerarchia, nomeFogliaSelezionata);
				return foglia;
			} else {
				System.out.println("Attenzione! Il nome della foglia inserito non e' presente");
			}	
		} else {
			System.out.println("Attenzione! Il nome della root non fa riferimento a nessuna gerarchia");
		}
		return null;
	}

	
	public void showOfferteAperteByCategoria() {
		Categoria foglia = scegliFoglia();
		
		if(foglia != null) {
			ArrayList<Pubblicazione> listaPubblicazioni = gestoreArticolo.leggiListaPubblicazioni(); //andrebbe richiamato gestore offerte
			
			for (Pubblicazione pubblicazione : listaPubblicazioni) {
				if(pubblicazione.getTipoOfferta().equals(new OffertaAperta())) { //equals da modificare
					if(pubblicazione.getArticolo().getFoglia().equals(foglia))
						System.out.println(pubblicazione);
				}
				
			}
		}
	}
	
}
