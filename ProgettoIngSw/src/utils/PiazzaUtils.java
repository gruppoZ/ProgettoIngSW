package utils;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import application.parametriPiazza.GiorniDellaSettimana;
import application.parametriPiazza.IntervalloOrario;

public class PiazzaUtils {
	private static final String FORMATO_GIORNO_D_M_YYYY = "d/M/yyyy";
	
	/**
 	 * Precondizione: orari != null
 	 * @param orari
 	 * @return
 	 */
 	public static IntervalloOrario estraiIntervalloMin(List<IntervalloOrario> orari) {
 		IntervalloOrario intervalloMin = orari.get(0);
 		
 		for (IntervalloOrario intervallo : orari) {
			if(intervallo.minIsBefore(intervalloMin.getOrarioMin())) intervalloMin = intervallo;
		}
 		
 		return intervalloMin;
 	}
 	
 	/**
 	 * Precondizione: orari != null
 	 * @param orari
 	 * @return
 	 */
 	public static List<IntervalloOrario> ordinaListaIntervalliOrari(List<IntervalloOrario> orari) {
 		List<IntervalloOrario> ordinata = new ArrayList<IntervalloOrario>();
 		
 		do {
	 		IntervalloOrario intervalloMin = estraiIntervalloMin(orari);
	 		orari.remove(intervalloMin);
	 		ordinata.add(intervalloMin);
 		} while(orari.size() > 0);
 		
 		return ordinata;
 	}
 	 	
 	/**
	 * Precondizione: date != null
	 * 
	 * Prende il parametro LocalDate, e verifica che il relativo giorno della settimana sia fra quelli messi a 
	 * dispozione della Piazza 
	 * @param date
	 * @param giorni
	 * @return
	 */
	public static boolean checkValiditaGiornoSettimanaPiazzaFromLocalDate(LocalDate date, List<GiorniDellaSettimana> giorni) {
		String dateItalianoDayOfWeek = date.format(DateTimeFormatter.ofPattern("EEEE", Locale.ITALY));	

		for (GiorniDellaSettimana giornoSettimana : giorni) {
			if(giornoSettimana.getNome().equalsIgnoreCase(dateItalianoDayOfWeek))
				return true;
		}
	 
		return false;
	}
	
	/**
	 * Prende il parametro di tipo String nel formato d/M/yyyy e restituisce il relativo LocalDate
	 * @param userInput
	 * @return
	 */
	public static LocalDate dateInput(String userInput) throws DateTimeException {

	    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(FORMATO_GIORNO_D_M_YYYY);
	    try {
	    	LocalDate date = LocalDate.parse(userInput, dateFormat);
	    	return date;
	    } catch (Exception e) {
			throw new DateTimeException(e.getMessage());
		}
	}
	
	/**
	 * Precondizione: intervalliOrari != null, orario != null
	 * 
	 * @param intervalliOrari
	 * @param orario
	 * @return True se orario è compreso in uno degli intervalli in intervalliOrari
	 */
	public static boolean checkValiditaOrario(List<IntervalloOrario> intervalliOrari, LocalTime orario) {
		if(intervalliOrari.size() == 0) return false;
		
		for (IntervalloOrario intervalloOrario : intervalliOrari) {
			if(intervalloOrario.includeOrario(orario))
				return true;
		}
		
		return false;
	}
}
