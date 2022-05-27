package gestioneParametri;

import java.io.IOException;
import java.util.List;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class ViewParametroLuogo extends ViewParametri{

	private static final String MSG_ERROR_RIMOZIONE_LUOGO = "Impossibile rimuovere il luogo. Nome non valido";
	private static final String MSG_ERROR_RIMOZIONE_LUOGHI_INSUFFICIENTI = "Impossibile rimuovere il luogo. Ricorda che almeno un luogo deve rimanere fissato.";
	private static final String MSG_ERROR_RIMOZIONE_LUOGO_FALLITA_INTERAZIONE_CON_FILE = "Impossibile rimuovere luogo. Fallita interazione con il file.";
	private static final String MSG_ERROR_AGGIUNGERE_LUOGO_FALLITA_INTERAZIONE_CON_FILE = "Impossibile aggiungere luogo. Fallita interazione con il file.";
	private static final String MSG_LUOGHI_PRESENTI = "Luoghi presenti: ";
	private static final String MSG_LUOGO_NON_PRESENTE = "il luogo scelto non è stato trovato. Scegli un luogo valido: ";
	private static final String MSG_LUOGO_GIA_PRESNTE = "Il luogo e' gia' presente";
	private static final String MSG_LUOGO_RIMOSSO = "\nLuogo rimosso!\n";	
	
	private static final String ASK_LUOGO_RIMOZIONE = "Inserisci il luogo da rimuovere: ";
	private static final String ASK_ALTRI_LUOGHI = "Vuoi inserire altri luoghi? ";
	private static final String ASK_LUOGO = "Inserisci un luogo: ";
	private static final String TIPOLOGIA_PARAMETRO = "Luoghi";
	
	
	public ViewParametroLuogo(GestioneParametri gestoreParametri) {
		super(gestoreParametri);
	}
	
	@Override
	public void menu() throws IOException {
		MyMenu menu = new MyMenu(TIPOLOGIA_PARAMETRO, TXT_VOCI_MODIFICA);
		int scelta = 0;
		boolean fine = false;
		do {
			showLuoghi();
						
			scelta = menu.scegli();
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
	public void aggiungi() throws IOException {
		List<String> listaLuoghi = getGestoreParametri().getLuoghi();
		do {
			String luogo = InputDati.leggiStringaNonVuota(ASK_LUOGO);
			
			try {
				getGestoreParametri().aggiungiLuogo(listaLuoghi, luogo);
			} catch(IOException e) {
				throw new IOException(MSG_ERROR_AGGIUNGERE_LUOGO_FALLITA_INTERAZIONE_CON_FILE);
			} catch(Exception e) {
				System.out.println(MSG_LUOGO_GIA_PRESNTE);
			}		
		} while(InputDati.yesOrNo(ASK_ALTRI_LUOGHI));
	}

	@Override
	public void rimuovi() throws IOException {
		List<String> listaLuoghi = getGestoreParametri().getLuoghi();

		if(!getGestoreParametri().checkVincoloLuoghiMinimi())
			System.out.println(MSG_ERROR_RIMOZIONE_LUOGHI_INSUFFICIENTI);
		else {
			
			String luogoDaEliminare = InputDati.leggiStringaNonVuota(ASK_LUOGO_RIMOZIONE);
			try {
				getGestoreParametri().rimuoviLuogo(listaLuoghi, luogoDaEliminare);
				System.out.println(MSG_LUOGO_RIMOSSO);
			} catch (IOException e) {
				throw new IOException(MSG_ERROR_RIMOZIONE_LUOGO_FALLITA_INTERAZIONE_CON_FILE);
			} catch (Exception e) {
				System.out.println(MSG_ERROR_RIMOZIONE_LUOGO); 
			}
		}
	}
	
	public String scegliLuogo() {
		showLuoghi();
		
		String luogo = InputDati.leggiStringaNonVuota(ASK_LUOGO);
	
		while(!getGestoreParametri().checkPresenzaLuogo(getGestoreParametri().getLuoghi(), luogo)) {
			luogo = InputDati.leggiStringaNonVuota(MSG_LUOGO_NON_PRESENTE);
		}
		
		return luogo;
	}
	
	protected void showLuoghi() {
		System.out.println(MSG_LUOGHI_PRESENTI);
		
		getGestoreParametri().getLuoghi().forEach(e -> {
			System.out.println("-> " + e);
		});
	}
}
