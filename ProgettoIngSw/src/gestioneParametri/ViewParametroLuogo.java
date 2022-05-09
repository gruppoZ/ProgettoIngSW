package gestioneParametri;

import java.util.List;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class ViewParametroLuogo extends ViewParametri{

	private static final String MSG_LUOGO_GIA_PRESNTE = "Il luogo e' gia' presente";
	private static final String MSG_ERRORE_RIMOZIONE_LUOGO = "Impossibile rimuovere il luogo. Nome non valido";
	private static final String MSG_ERRORE_RIMOZIONE_LUOGHI_INSUFFICIENTI = "Impossibile rimuovere il luogo. Ricorda che almeno un luogo deve rimanere fissato.";
	private static final String ASK_LUOGO_RIMOZIONE = "Inserisci il luogo da rimuovere: ";
	private static final String ASK_ALTRI_LUOGHI = "Vuoi inserire altri luoghi? ";
	private static final String ASK_LUOGO = "Inserisci un luogo: ";
	private static final String TIPOLOGIA_PARAMETRO = "Luoghi";	
	
	public ViewParametroLuogo(GestioneParametri gestoreParametri) {
		super(gestoreParametri);
	}
	
	@Override
	public void menu() {
		MyMenu menuModificaLuoghi = new MyMenu(TIPOLOGIA_PARAMETRO, TXT_VOCI_MODIFICA);
		int scelta = 0;
		boolean fine = false;
		do {
			showLuoghi();
						
			scelta = menuModificaLuoghi.scegli();
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
		List<String> listaLuoghi = getGestoreParametri().getLuoghi();
		do {
			String luogo = InputDati.leggiStringaNonVuota(ASK_LUOGO);
			
			try {
				getGestoreParametri().aggiungiLuogo(listaLuoghi, luogo);
			} catch(RuntimeException e) {
				System.out.println(MSG_LUOGO_GIA_PRESNTE);
			}		
		} while(InputDati.yesOrNo(ASK_ALTRI_LUOGHI));
	}

	@Override
	public void rimuovi() {
		List<String> listaLuoghi = getGestoreParametri().getLuoghi();
		
		showLuoghi();
		
		String luogoDaEliminare = InputDati.leggiStringaNonVuota(ASK_LUOGO_RIMOZIONE);
		
		if(!getGestoreParametri().checkVincoloLuoghiMinimi())
			System.out.println(MSG_ERRORE_RIMOZIONE_LUOGHI_INSUFFICIENTI);
		else {
			try {
				getGestoreParametri().rimuoviLuogo(listaLuoghi, luogoDaEliminare);
			} catch(RuntimeException e) {
				System.out.println(MSG_ERRORE_RIMOZIONE_LUOGO); 
			}
		}
	}
	
	private void showLuoghi() {
		System.out.println(getGestoreParametri().getLuoghi().toString());
	}
}
