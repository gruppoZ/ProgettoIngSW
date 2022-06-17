package view;

import java.io.IOException;

import application.Categoria;
import application.Gerarchia;
import controller.GestioneConfiguratore;
import it.unibs.fp.mylib.MyMenu;

public class ViewConfiguratore extends ViewUtente{
	
	private static final String TXT_TITOLO = "Benvenuto Configuratore";
	private static final String MSG_CREA_GERARCHIA = "Crea gerarchia";
	private static final String MSG_IMPOSTA_PARAMETRI = "Gestisci Piazza";
	private static final String MSG_OFFERTE_APERTE = "Visualizzare tutte le attuali Offerte aperte relative ad una categoria";
	private static final String MSG_OFFERTE_IN_SCAMBIO_E_CHIUSE = "Visualizzare tutte le attuali Offerte In Scambio e Chiuse relative ad una categoria";
	private static final String MSG_IMPORTA_DATI = "Importa Dati";
	
	private static final String [] TXT_VOCI = {
			MSG_CREA_GERARCHIA,
			MSG_VISUALIZZA_GERARCHIE,
			MSG_IMPOSTA_PARAMETRI,
			MSG_OFFERTE_APERTE,
			MSG_OFFERTE_IN_SCAMBIO_E_CHIUSE,
			MSG_IMPORTA_DATI
	};
	
	@Override
	public void menu(String username) throws IOException {
		try {
			GestioneConfiguratore gestoreConfiguratore = new GestioneConfiguratore();
			ViewOfferte viewOfferte = new ViewOfferte();
			ViewGerarchia viewGerarchia = new ViewGerarchia();
			Categoria foglia;
			
			MyMenu menu = new MyMenu(TXT_TITOLO, TXT_VOCI);
			int scelta = 0;
			boolean fine = false;
			do {
				scelta = menu.scegli();
				switch(scelta) {
				case 0:
					fine = true;
					break;
				case 1:
					viewGerarchia.update();
					viewGerarchia.menu();
					
					gestoreConfiguratore.aggiornaGerarchie();				
					break;
				case 2:
					viewGerarchia.update();
					
					if(!gestoreConfiguratore.isGerarchieCreate())
						System.out.println(MSG_ASSENZA_GERARCHIE);
					else {				
						for (Gerarchia gerarchia : gestoreConfiguratore.getGerarchie().values()) {
							viewGerarchia.showGerarchia(gerarchia);
						}
					}
					break;
				case 3:
					ViewParametri viewPiazza = new ViewParametroPiazza();
					viewPiazza.menu();
					break;
				case 4:
					viewGerarchia.update();
					viewOfferte = new ViewOfferte();
					
					foglia = viewGerarchia.scegliFoglia();
					
					viewOfferte.showOfferteAperteByCategoria(foglia);
					break;
				case 5:
					viewGerarchia.update();
					viewOfferte = new ViewOfferte();
					
					foglia = viewGerarchia.scegliFoglia();
					
					viewOfferte.showOfferteInScambioByCategoria(foglia);
					viewOfferte.showOfferteChiuseByCategoria(foglia);
					break;
				case 6:
					ViewFileProgramma viewInfo = new ViewFileProgramma();
					
					viewInfo.menu();
					
					gestoreConfiguratore.aggiornaGerarchie();
					break;
				default:
					System.out.println(TXT_ERROR);	
				}
			} while(!fine);
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		}	
	}	
}
