package gestioneParametri;

import java.time.LocalTime;
import java.util.List;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class ViewParametroIntervalloOrario extends ViewParametri{

	private static final String GIVE_INTERVALLI_PRESENTI = "\nIntervalli presenti: ";
	private static final String MSG_INTERVALLO_AGGIUNTO_CORRETTAMENTE = "\nIntervallo aggiunto correttamente!\n";
	private static final String MSG_TITOLO_ORARIO_FINALE = "\n------Inserimento Orario Finale------";
	private static final String MSG_TITOLO_ORARIO_INIZIALE = "\n------Inserimento Orario Iniziale------";
	private static final String MSG_ERRORE_RIMOZIONE_INTERVALLO_NON_VALIDO = "Impossibile rimuovere l'intervallo. Intervallo non valido";
	private static final String MSG_ERRORE_RIMOZIONE_INTERVALLI_INSUFFICIENTI = "Impossibile rimuovere l'Intervallo Orario. Ricorda che almeno un intervallo orario deve rimanere fissato.";
	private static final String MSG_INTERVALLO_RIMOSSO = "\nIntervallo rimosso!\n";
	private static final String MSG_INTERVALLI_SOVRAPPOSTI = "\nImpossibile inserire l'intervallo. L'intervallo inserito si sovrappone con gli intervalli gia' presenti\n";
	
	private static final String ASK_ORA_INIZIALE_INTERVALLO_RIMUOVERE = "Inserire l'orario iniziale dell'intervallo da rimuovere";
	private static final String ASK_OPERAZIONE_DESIDERATA = "\n\nDigita il numero dell'opzione desiderata > ";
	private static final String ASK_ORARIO_FINALE = "Inserire l'orario finale dell'intervallo";
	private static final String ASK_ORARIO_INIZIALE = "Inserire l'orario iniziale dell'intervallo";
	private static final String ASK_INTERVALLO = "Inserire l'intervallo: ";
	private static final String ASK_ALTRI_INTERVALLI = "Vuoi inserire altri intervalli orari? ";
	private static final String ASK_ORA = "Scegli l'ora: ";
	
	private static final String GIVE_ALTERNATIVE_ORARIO = "Puoi scegliere: %s:00 oppure %s:30 \n";
	private static final String GIVE_ORARIO_INIZIALE = "Orario iniziale: %s\n";
	private static final String GIVE_ORARIO_FINALE= "Orario finale: %s\n";
	private static final String GIVE_ORA_INIZIALE_INTERVALLO_RIMUOVERE = "Orario iniziale dell'intervallo da eliminare: %s\n";
	
	private static final String TIPOLOGIA_PARAMETRO = "Intervalli";
	
	@Override
	public void menu() {
		MyMenu menuModificaIntervalli = new MyMenu(TIPOLOGIA_PARAMETRO, TXT_VOCI_MODIFICA);
		int scelta = 0;
		boolean fine = false;
		do {
			scelta = menuModificaIntervalli.scegli();
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
		List<IntervalloOrario> listaIntervalli = getGestoreParametri().getIntervalli();
		
		showIntervalli();
		
		do {
			IntervalloOrario daAggiungere = creaIntervalloOrario();
			
			if(!getGestoreParametri().checkAggiuntaIntervalloOrario(listaIntervalli, daAggiungere))
				System.out.println(MSG_INTERVALLI_SOVRAPPOSTI);
			
			System.out.println(MSG_INTERVALLO_AGGIUNTO_CORRETTAMENTE);
		} while(InputDati.yesOrNo(ASK_ALTRI_INTERVALLI));
		
		showIntervalli();
	}

	@Override
	public void rimuovi() {
		List<IntervalloOrario> listaIntervalli = getGestoreParametri().getIntervalli();

		showIntervalli();
		
		System.out.println(ASK_ORA_INIZIALE_INTERVALLO_RIMUOVERE);
		LocalTime orarioMinDaEliminare = creaOrarioMin(LocalTime.MIN, LocalTime.of(23, 0));
		System.out.printf(GIVE_ORA_INIZIALE_INTERVALLO_RIMUOVERE,orarioMinDaEliminare.toString());
					
		if(!getGestoreParametri().checkVincolIntervalliMinimi())
			System.out.println(MSG_ERRORE_RIMOZIONE_INTERVALLI_INSUFFICIENTI);
		else {
			if(getGestoreParametri().checkRimozioneIntervalloOrario(listaIntervalli, orarioMinDaEliminare)) {
				System.out.println(MSG_INTERVALLO_RIMOSSO);
				showIntervalli();
			}	
			else
				System.out.println(MSG_ERRORE_RIMOZIONE_INTERVALLO_NON_VALIDO);
		}
	}

	private void showIntervalli() {
		System.out.print(GIVE_INTERVALLI_PRESENTI);
		System.out.println(getGestoreParametri().getIntervalli().toString());
	}
	
	private IntervalloOrario creaIntervalloOrario() {
		//crea intervallo orario
		System.out.println(ASK_INTERVALLO);
		
		//orarioMin
		System.out.println(MSG_TITOLO_ORARIO_INIZIALE);
		System.out.println(ASK_ORARIO_INIZIALE);
		LocalTime orarioMin = creaOrarioMin(LocalTime.MIN, LocalTime.of(23, 0));
		System.out.printf(GIVE_ORARIO_INIZIALE, orarioMin.toString());
		
		//orarioMax
		System.out.println(MSG_TITOLO_ORARIO_FINALE);
		System.out.println(ASK_ORARIO_FINALE);
		LocalTime orarioMax;
		
		
		if(orarioMin.getHour() == 23)
			orarioMax = LocalTime.of(23, 30);
		else
			orarioMax = creaOrarioMax(orarioMin, LocalTime.MAX);
		
		System.out.printf(GIVE_ORARIO_FINALE, orarioMax.toString());
		
		return new IntervalloOrario(orarioMin, orarioMax);
	}
	
	private LocalTime creaOrarioMin(LocalTime min, LocalTime max) {
		int ora = InputDati.leggiIntero(ASK_ORA, min.getHour(), max.getHour());
		int minuti;
		
		if(ora == 23)
			return LocalTime.of(ora, 0);
		
		minuti = scegliMinuti(ora);
		
		return LocalTime.of(ora, minuti);
	}
	
	private LocalTime creaOrarioMax(LocalTime min, LocalTime max) {
		int ora;
		int minuti;
		//check
		//get -> ora = InputDati.leggiIntero(ASK_ORA, get..., max.getHour());
		if(min.getMinute() == 30) {
			LocalTime newMin = LocalTime.of(min.getHour() + 1, 0);
			ora = InputDati.leggiIntero(ASK_ORA, newMin.getHour(), max.getHour());
		} else {
			ora = InputDati.leggiIntero(ASK_ORA, min.getHour(), max.getHour());
		}
		
		if(min.getHour() == ora)
			minuti = 30;
		else
			minuti = scegliMinuti(ora);
		
		return LocalTime.of(ora, minuti);
	}
	
	private int scegliMinuti(int ora) {
		int minuti = 0;
		System.out.printf(GIVE_ALTERNATIVE_ORARIO, ora, ora);
		int scelta = InputDati.leggiIntero("(0) " + ora + ":00\n(1) " + ora + ":30" + ASK_OPERAZIONE_DESIDERATA, 0, 1);
		
		switch (scelta) {
		case 0:
			minuti = 0;
			break;
		case 1:
			minuti = 30;
			break;
		default:
			break;
		}
		
		return minuti;
	}
}
