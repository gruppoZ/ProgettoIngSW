package gestioneParametri;

import java.util.ArrayList;
import java.util.List;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class ViewParametroGiorno extends ViewParametri{
	
	private static final String MSG_GIORNO_GIA_PRESENTE = "Il giorno selezionato e' gia' presente";
	private static final String MSG_GIORNO_NON_VALIDO = "Giorno non valido";
	private static final String MSG_GIORNI_PRESENTI = "Giorni presenti: ";
	private static final String ASK_GIORNO_DESIDERATO = "Seleziona il giorno desiderato tramite id: ";
	private static final String ASK_INSERIRE_ALTRI_GIORNI = "Vuoi inserire altri giorni? ";
	private static final String TIPOLOGIA_PARAMETRO = "Giorni";
	
	/**
	 * Precondizione: gestoreParametri != null
	 * 
	 * @param gestoreParametri
	 */
	public ViewParametroGiorno(GestioneParametri gestoreParametri) {
		super(gestoreParametri);
	}
	
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
		
		showTuttiGiorniDellaSettimana();
		
		do {
			GiorniDellaSettimana giorno = leggiGiorno();
			
			try {
				getGestoreParametri().aggiungiGiorno(giorni, giorno);
				presente = false;
			} catch (RuntimeException e) {
				System.out.println(MSG_GIORNO_GIA_PRESENTE);
				presente = true;
			}
			
		} while(presente || InputDati.yesOrNo(ASK_INSERIRE_ALTRI_GIORNI));	
	}

	@Override
	public void rimuovi() {
		List<GiorniDellaSettimana> giorni = getGestoreParametri().getGiorni();

		showTuttiGiorniDellaSettimana();

		GiorniDellaSettimana giornoDaEliminare = leggiGiorno();
		
		try {
			getGestoreParametri().rimuoviGiorno(giorni, giornoDaEliminare);
		} catch (RuntimeException e) {
			System.out.println(MSG_GIORNO_NON_VALIDO);
		}
	}
	
	public GiorniDellaSettimana scegliGiorno() {
		showGiorniPresenti();
		
		GiorniDellaSettimana giorno = leggiGiorno();
		
		while(!getGestoreParametri().checkPresenzaGiorno(getGestoreParametri().getGiorni(), giorno)) {
			System.out.println(MSG_GIORNO_NON_VALIDO);
			giorno = leggiGiorno();
		}
		
		return giorno;
	}
	
	public void showGiorniPresenti() {
		System.out.println(MSG_GIORNI_PRESENTI);
		
		showGiorni(getGestoreParametri().getGiorni());
	}
	
	private void showTuttiGiorniDellaSettimana() {
		List<GiorniDellaSettimana> giorni = new ArrayList<>();
		
		for (GiorniDellaSettimana giorniDellaSettimana : GiorniDellaSettimana.values()) {
			giorni.add(giorniDellaSettimana);
		}
		
		showGiorni(giorni);
	}
	
	/**
	 * Precondizione: giorni != null
	 * 
	 * @param giorni
	 */
	public void showGiorni(List<GiorniDellaSettimana> giorni) {
		StringBuilder sb = new StringBuilder();
		
		for (GiorniDellaSettimana giorniDellaSettimana : giorni) {
			sb.append(giorniDellaSettimana.getOrdine() + " " + giorniDellaSettimana.getNome() + "\t");
		}
		
		System.out.println(sb.toString());
	}
	
	private GiorniDellaSettimana leggiGiorno() {
		int id = InputDati.leggiIntero(ASK_GIORNO_DESIDERATO, 0, 6);
		return GiorniDellaSettimana.getById(id);
	}
}
