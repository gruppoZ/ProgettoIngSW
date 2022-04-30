package gestioneOfferte;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gestioneCategorie.Categoria;
import gestioneCategorie.Gerarchia;
import gestioneUtenti.GestioneFruitore;
import it.unibs.fp.mylib.InputDati;

public class ViewOfferte {

	private GestioneArticolo gestoreArticolo; //gestore articolo fa troppe cose, da creare più gestori
	
	public ViewOfferte() {
		gestoreArticolo = new GestioneArticolo();
	}
	
	private int numeroOfferteAperte(ArrayList<Offerta> listaOfferte) {
		int n = 0;
		
		for (Offerta offerta : listaOfferte) {
			if(offerta.getTipoOfferta().equals("OffertaAperta")) {
				n++;
			}
		}
		
		return n;
	}
	
	public void ritiraOfferta(GestioneFruitore gestoreFruitore) {
		ArrayList<Offerta> listaOfferte = gestoreArticolo.getListaOfferte();//andrebbe richiamato gestore offerte
		
		if(numeroOfferteAperte(listaOfferte) > 0) {
			for (Offerta offerta : listaOfferte) {
				if(offerta.getTipoOfferta().equals("OffertaAperta") && offerta.getUsername().equals(gestoreFruitore.getUsername())) {
					System.out.println(offerta);
					boolean scelta = InputDati.yesOrNo("Vuoi ritirare questa offerta?");
					if(scelta) {						
						OffertaAperta offertaAperta = (OffertaAperta) offerta;
						OffertaRitirata offertaRitirata = offertaAperta.ritiraOfferta();
						
						gestoreArticolo.rimuoviOfferta(offerta); 
						gestoreArticolo.aggiungiOfferta(offertaRitirata);
						gestoreArticolo.salvaOfferte();
						
						System.out.println("L'offerta e' stata rimossa");
					}
						
				}	
			}
		} else {
			System.out.println("Non ci sono offerte da ritirare");
		}
		
		
		
	}
	
	private void stampaGerarchie() {
		System.out.println(gestoreArticolo.getGestoreGerarchie().getToStringSintetico());
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
			List<Categoria> listaFoglie = gestoreArticolo.getListaFoglieByGerarchia(gerarchia); 
 
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
			ArrayList<Offerta> listaOfferte = gestoreArticolo.leggiListaOfferte();//TODO: andrebbe richiamato gestore offerte e andrebbe usato il getListaOfferte
			
			for (Offerta offerta : listaOfferte) {
				if(offerta.getTipoOfferta().equals("OffertaAperta")) { //equals da modificare
					if(offerta.getArticolo().getFoglia().equals(foglia))
						System.out.println(offerta);
				}
				
			}
		}
	}
	
	/**
	 * Un fruitore può vedere tutte le sue offerte
	 * @param gestoreFruitore
	 */
	public void showOfferteByName(GestioneFruitore gestoreFruitore) {
		String username = gestoreFruitore.getUsername();
		
		ArrayList<Offerta> listaOfferte = gestoreArticolo.leggiListaOfferte();//andrebbe richiamato gestore offerte
		
		for (Offerta offerta : listaOfferte) {
			if(offerta.getUsername().equals(username)) 
				System.out.println(offerta);
			
		}
		
	}
	
}
