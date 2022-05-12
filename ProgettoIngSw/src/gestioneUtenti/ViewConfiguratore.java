package gestioneUtenti;

import gestioneCategorie.Categoria;
import gestioneCategorie.ViewGerarchia;
import gestioneOfferte.ViewOfferte;
import gestioneParametri.ViewParametri;
import gestioneParametri.ViewParametroPiazza;
import it.unibs.fp.mylib.MyMenu;

public class ViewConfiguratore extends ViewUtente{
	
	private static final String TXT_TITOLO = "Benvenuto Configuratore";
	private static final String MSG_CREA_GERARCHIA = "Crea gerarchia";
	private static final String MSG_IMPOSTA_PARAMETRI = "Gestisci Piazza";
	private static final String MSG_OFFERTE_APERTE = "Visualizzare tutte le attuali Offerte aperte relative ad una categoria";
	private static final String MSG_OFFERTE_IN_SCAMBIO_E_CHIUSE = "Visualizzare tutte le attuali Offerte In Scambio e Chiuse relative ad una categoria";
	
	private static final String [] TXT_VOCI = {
			MSG_CREA_GERARCHIA,
			MSG_VISUALIZZA_GERARCHIE,
			MSG_IMPOSTA_PARAMETRI,
			MSG_OFFERTE_APERTE,
			MSG_OFFERTE_IN_SCAMBIO_E_CHIUSE
	};
	
	@Override
	public void menu(String username) {
		GestioneConfiguratore gestoreConfiguratore = new GestioneConfiguratore();		
		
		ViewOfferte viewOfferte = new ViewOfferte();
		ViewGerarchia viewGerarchia = new ViewGerarchia();
		Categoria foglia;
		
		MyMenu menuConfiguratore = new MyMenu(TXT_TITOLO, TXT_VOCI);
		int scelta = 0;
		boolean fine = false;
		do {
			scelta = menuConfiguratore.scegli();
			switch(scelta) {
			case 0:
				fine = true;
				break;
			case 1:
				ViewGerarchia view = new ViewGerarchia();
				view.menu();
				break;
			case 2:
				System.out.println(gestoreConfiguratore.getGerarchie());
				break;
			case 3:
				ViewParametri viewPiazza = new ViewParametroPiazza();
				viewPiazza.menu();
				break;
			case 4:
				foglia = viewGerarchia.scegliFoglia();
				
				viewOfferte.showOfferteAperteByCategoria(foglia);
				break;
			case 5:
				foglia = viewGerarchia.scegliFoglia();
				
				viewOfferte.showOfferteInScambioByCategoria(foglia);
				viewOfferte.showOfferteChiuseByCategoria(foglia);
				break;
			default:
				System.out.println(TXT_ERRORE);	
			}
		} while(!fine);
	}
	
}
