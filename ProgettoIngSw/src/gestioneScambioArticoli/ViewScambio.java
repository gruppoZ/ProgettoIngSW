package gestioneScambioArticoli;

import gestioneOfferte.GestioneOfferta;
import gestioneUtenti.GestioneFruitore;
import it.unibs.fp.mylib.MyMenu;

public class ViewScambio {
	//costanti per menu
	protected static final String TXT_ERRORE = "ERRORE";
	private static final String TXT_TITOLO = "Scambio Articoli";
	
	private static final String MSG_SCEGLI_OFFERTE_APERTE = "Scegli Offerta Aperta";
	
	private static final String [] TXT_VOCI = {
			MSG_SCEGLI_OFFERTE_APERTE
	};
	
	
	private GestioneOfferta gestoreOfferta;
	private GestioneFruitore gestoreFruitore;
	
	public ViewScambio() {
	}
	
	public ViewScambio(GestioneOfferta gestoreOfferta, GestioneFruitore gestoreFruitore) {
		this.gestoreOfferta = gestoreOfferta;
		this.gestoreFruitore = gestoreFruitore;
	}
	
	public void menu() {	
		MyMenu viewScambio = new MyMenu(TXT_TITOLO, TXT_VOCI);
		int scelta = 0;
		boolean fine = false;
		do {
			scelta = viewScambio.scegli();
			switch(scelta) {
			case 0:
				fine = true;
				break;
			case 1:
				
				break;
			default:
				System.out.println(TXT_ERRORE);
			}
		} while(!fine);
	}
}
