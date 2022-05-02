package gestioneOfferte;

import java.util.ArrayList;
import gestioneCategorie.Categoria;
import gestioneCategorie.ViewGerarchia;
import gestioneUtenti.GestioneFruitore;
import it.unibs.fp.mylib.InputDati;

public class ViewOfferte {

	private static final String MSG_OFFERTE_BY_UTENTE_INESISTENTI = "\nNon sono presenti offerte a tuo nome:";
	private static final String MSG_OFFERTE_ATTIVE_BY_CATEGORIA_INESISTENTI = "\nNon sono presenti offerte ATTIVE per la categoria selezionata.";
	private static final String MSG_OFFERTE_RITIRABILI_INESISTENTI = "Non ci sono offerte da ritirare";
	private static final String MSG_OFFERTA_RITIRATA = "L'offerta e' stata rimossa";
	private static final String MSG_ASK_RITIRARE_OFFERTA = "Vuoi ritirare questa offerta?";
	//TODO: se non ci sono offerte => view "non sono presenti offerte"
	private GestioneOfferta gestoreOfferta;
	
	public ViewOfferte() {
		gestoreOfferta = new GestioneOfferta();
	}
		
	/**
	 * Mostra, se disponibli altrimenti viene detto che non ci sono, uno ad uno le offerte attive che fanno a capo l'username/utente loggato
	 * Per ogni offerta viene chiesto se la si vuole ritirare
	 * @param gestoreFruitore
	 */
	public void ritiraOfferta(GestioneFruitore gestoreFruitore) {
		ArrayList<Offerta> listaOfferteAttiveByUtente = (ArrayList<Offerta>) gestoreOfferta.getOfferteAttiveByUtente(gestoreFruitore.getUsername());
		
		if(listaOfferteAttiveByUtente.size() > 0) {
			for (Offerta offerta : listaOfferteAttiveByUtente) {
				showOfferta(offerta);
				
				boolean scelta = InputDati.yesOrNo(MSG_ASK_RITIRARE_OFFERTA);
				if(scelta) {					
					gestoreOfferta.gestisciCambiamentoStatoOfferta(offerta);
					
					System.out.println(MSG_OFFERTA_RITIRATA);
				}
			}
		} else {
			System.out.println(MSG_OFFERTE_RITIRABILI_INESISTENTI);
		}	
	}
	
	/**
	 * Mostra se disponibili le offerte attive dopo aver scelto una Categoria Foglia.
	 * Se non presenti lo viene detto a video
	 */
	public void showOfferteAperteByCategoria() {
		ViewGerarchia viewGerarchia = new ViewGerarchia();
		Categoria foglia = viewGerarchia.scegliFoglia();
		 
		if(foglia != null) {
			
			ArrayList<Offerta> listaOfferteAttiveByCategoria = (ArrayList<Offerta>) gestoreOfferta.getOfferteAperteByCategoria(foglia);
			
			if(listaOfferteAttiveByCategoria.size() > 0) {
				for (Offerta offerta : listaOfferteAttiveByCategoria) {
					showOfferta(offerta);
				}
			} else
				System.out.println(MSG_OFFERTE_ATTIVE_BY_CATEGORIA_INESISTENTI);
		}
	}
	
	/**
	 * Un fruitore può vedere tutte le sue offerte
	 * @param gestoreFruitore
	 */
	public void showOfferteByName(GestioneFruitore gestoreFruitore) {
		String username = gestoreFruitore.getUsername();
		
		ArrayList<Offerta> listaOfferteByUtente = (ArrayList<Offerta>) gestoreOfferta.getOfferteByUtente(username);
				
		if(listaOfferteByUtente.size() > 0) {
			for (Offerta offerta : listaOfferteByUtente) {
				showOfferta(offerta);
			}
		} else
			System.out.println(MSG_OFFERTE_BY_UTENTE_INESISTENTI + username);
	}
	
	protected void showOfferta(Offerta offerta) {
		StringBuffer sb = new StringBuffer();
		ViewArticolo viewArticolo = new ViewArticolo();
		
		sb.append("Offerta di " + offerta.getUsername() +".\n"
				+ "->Stato Offerta: " + offerta.getTipoOfferta().getIdentificativo() + "\n"
				+ "->" );
		
		System.out.print(sb.toString());
		viewArticolo.showArticolo(offerta.getArticolo());
	}
}
