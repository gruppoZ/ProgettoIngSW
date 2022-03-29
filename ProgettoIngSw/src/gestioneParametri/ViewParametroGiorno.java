package gestioneParametri;

import java.util.List;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class ViewParametroGiorno extends ViewParametri{
	
	private static final String MSG_GIORNI_PRESENTI = "Giorni per ora presenti: ";
	private static final String ASK_GIORNO_DESIDERATO = "Seleziona il giorno desiderato: ";
	private static final String MSG_GIORNO_NON_VALIDO = "Giorno non valido";
	private static final String ASK_INSERIRE_ALTRI_GIORNI = "Vuoi inserire altri giorni? ";
	private static final String MSG_GIORNO_GIA_PRESENTE = "Il giorno selezionato e' gia' presente";
	private static final String TIPOLOGIA_PARAMETRO = "Giorni";
	
	@Override
	public void menu() {
		MyMenu menuModificaGiorni = new MyMenu(TIPOLOGIA_PARAMETRO, TXT_VOCI_MODIFICA);
		int scelta = 0;
		boolean fine = false;
		do {
			showGiorniPresenti();

			scelta = menuModificaGiorni.scegli();
			switch(scelta) {
			case 0:
				fine = true;
				break;
			case 1:
				aggiungi();
				break;
			case 2:
				rimuovi();			
				break;
			default:
				System.out.println(TXT_ERRORE);
			}
		} while(!fine);
	}

	@Override
	public void aggiungi() {
		boolean presente = false;
		List<GiorniDellaSettimana> giorni = getGestoreParametri().getGiorni();
		
		showGiorni();
		
		do {
			GiorniDellaSettimana giorno = leggiGiorno();

			presente = !getGestoreParametri().checkAggiuntaGiorno(giorni, giorno);
			
			if(presente)
				System.out.println(MSG_GIORNO_GIA_PRESENTE);
			
		} while(presente || InputDati.yesOrNo(ASK_INSERIRE_ALTRI_GIORNI));	
	}

	@Override
	public void rimuovi() {
		List<GiorniDellaSettimana> giorni = getGestoreParametri().getGiorni();

		showGiorni();

		GiorniDellaSettimana giornoDaEliminare = leggiGiorno();
		
		if(!getGestoreParametri().checkRimozioneGiorno(giorni, giornoDaEliminare))
			System.out.println(MSG_GIORNO_NON_VALIDO);
	}
	
	private void showGiorniPresenti() {
		System.out.println(MSG_GIORNI_PRESENTI);
		System.out.println(getGestoreParametri().getGiorni());
	}
	
	private void showGiorni() {
		GiorniDellaSettimana.showGiorniSettimana();
	}
	
	private GiorniDellaSettimana leggiGiorno() {
		int id = InputDati.leggiIntero(ASK_GIORNO_DESIDERATO, 0, 6);
		return GiorniDellaSettimana.getById(id);
	}
}
