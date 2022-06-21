package view;

import java.io.IOException;

import application.Gerarchia;
import controller.GestioneConfiguratore;
import it.unibs.fp.mylib.MyMenu;
import view.viewParametriPiazza.ViewParametri;
import view.viewParametriPiazza.ViewParametroPiazza;

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
	
	private GestioneConfiguratore gestoreConfiguratore;
	private ViewOfferta viewOfferte;
	private ViewGerarchia viewGerarchia;
	
	@Override
	public void menu(String username) throws IOException {
		try {
			gestoreConfiguratore = new GestioneConfiguratore();
			viewOfferte = new ViewOfferta();
			viewGerarchia = new ViewGerarchia();
					
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
					creaGerarchia();			
					break;
				case 2:
					showGerarchie();
					break;
				case 3:
					viewPiazza();
					break;
				case 4:
					showOfferteAperteByCategoria();
					break;
				case 5:
					showOfferteInScambioEChiusebyCategoria();
					break;
				case 6:
					importaDati();
					break;
				default:
					System.out.println(TXT_ERROR);	
				}
			} while(!fine);
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		}	
	}
	
	private void creaGerarchia() throws IOException {
		viewGerarchia.update();
		viewGerarchia.menu();
		
		gestoreConfiguratore.aggiornaGerarchie();	
	}
	
	private void viewPiazza() throws IOException {
		ViewParametri viewPiazza = new ViewParametroPiazza();
		viewPiazza.menu();
	}
	
	private void showGerarchie() throws IOException {
		viewGerarchia.update();
		
		if(!gestoreConfiguratore.isGerarchieCreate())
			System.out.println(MSG_ASSENZA_GERARCHIE);
		else {				
			for (Gerarchia gerarchia : gestoreConfiguratore.getGerarchie().values()) {
				viewGerarchia.showGerarchia(gerarchia);
			}
		}
	}
	
	private void showOfferteInScambioEChiusebyCategoria() throws IOException {
		viewGerarchia.update();
		viewOfferte = new ViewOfferta();
		
		viewOfferte.showOfferteInScambioByCategoria();
		viewOfferte.showOfferteChiuseByCategoria();
	}
	
	private void showOfferteAperteByCategoria() throws IOException {
		viewGerarchia.update();
		viewOfferte = new ViewOfferta();
		
		viewOfferte.showOfferteAperteByCategoria();
	}
	
	private void importaDati() throws IOException {
		ViewFileProgramma viewInfo = new ViewFileProgramma();
		
		viewInfo.menu();
		
		gestoreConfiguratore.aggiornaGerarchie();
	}
}
