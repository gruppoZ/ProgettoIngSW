package gestioneParametri;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import gestioneFileProgramma.GestioneFileProgramma;
import main.JsonIO;

public class GestioneParametri {
	private static final String FORMATO_GIORNO_D_M_YYYY = "d/M/yyyy";
	private static final int NUM_MINIMO_GIORNI = 1;
	private static final int NUM_MASSIMO_GIORNI = 7;
	private static final int NUM_MINIMO_INTERVALLI = 1;
	private static final int NUM_MINIMO_LUOGHI = 1;
	
	private Piazza piazza;
	private String pathParametri;	
	
	/**
	 * Postcondizione: pathParametri.length() > 0, this.piazza != null
	 * @throws IOException 
	 */
	public GestioneParametri() throws IOException {
		GestioneFileProgramma info = new GestioneFileProgramma();
		pathParametri = info.getInfoSistema().getUrlParametri(); 

		this.piazza = leggiPiazza(pathParametri);
	}
	
	/**
	 * Precondizione: citta.length > 0, listaLuoghi.size() > 0, giorni.size() > 0, intervalliOrari.size() > 0, piazza != null
	 * 
	 * @param citta
	 * @param listaLuoghi
	 * @param giorni
	 * @param intervalliOrari
	 * @param scadenza
	 * @throws IOException 
	 */
 	public void creaPiazza(String citta, List<String> listaLuoghi, List<GiorniDellaSettimana> giorni, List<IntervalloOrario> intervalliOrari,
 			int scadenza) throws IOException {
 		
		piazza =  new Piazza(citta, listaLuoghi, giorni, intervalliOrari, scadenza);
		salvaPiazza();
	}
	
 	public Piazza getPiazza() throws IOException {
 		if(piazza != null)
 			return this.piazza;
 		else
 			return leggiPiazza(pathParametri);
 	}
 	
 	protected void setCitta(String citta) {
 		this.piazza.setCitta(citta);
 	}
 	
 	protected List<String> getLuoghi(){
 		return piazza.getLuoghi();
 	}
 	
 	public List<GiorniDellaSettimana> getGiorni(){
 		return piazza.getGiorni();
 	}
 	
 	protected List<IntervalloOrario> getIntervalli(){
 		return piazza.getIntervalliOrari();
 	}
 	
 	protected void setIntervalli(List<IntervalloOrario> intervalli) {
 		this.piazza.setIntervalliOrari(intervalli);
 	}
 	
 	protected int getNumeroIntervalliOrari() {
 		return piazza.getIntervalliOrari().size();
 	}
 	
 	public int getScadenza() {
 		return piazza.getScadenza();
 	}
 	
 	public void importaParametri(String path) throws IOException {
 		this.piazza = leggiPiazza(path);
 		salvaPiazza();
 	}

 	/**
 	 * Precondizione: listaLuoghi != null
 	 * Postcondizione: listaLuoghi'.size() = listaLuoghi.size() + 1 se checkPresenzaLuogo == false
 	 * 
 	 * Aggiunge luogo alla lista se non e' gia' presente inoltre salva su Json
 	 * Se luogo e' gia' presente throw new RuntimeException()
 	 * @param listaLuoghi
 	 * @param luogo
 	 * @throws IOException se la scrittura su file fallisce
 	 * @throws Exception nel caso in cui il luogo sia già presente in listaLuoghi
 	 */
 	protected void aggiungiLuogo(List<String> listaLuoghi, String luogo) throws Exception, IOException {
 		if(checkPresenzaLuogo(listaLuoghi, luogo)) {
 			throw new Exception();
 		} else {
 			listaLuoghi.add(luogo);	
 			salvaPiazza();
 		}		
 	}
 	
 	/**
 	 * Precondizione: listaLuoghi != null
 	 * Postcondizione: listaLuoghi'.size() = listaLuoghi.size() - 1 se checkPresenzaLuogo == true
 	 * @param listaLuoghi
 	 * @param luogoDaEliminare
 	 * @throws IOException se la scrittura su file fallisce
 	 * @throws Exception se luogoDaEliminare non fa parte dei listaLuoghi
 	 */
 	protected void rimuoviLuogo(List<String> listaLuoghi, String luogoDaEliminare) throws IOException, Exception {
 		if(checkPresenzaLuogo(listaLuoghi, luogoDaEliminare)) {
			piazza.rimuoviLuogo(luogoDaEliminare);
			salvaPiazza();
		} else {
			throw new Exception();
		}
 	}
 	
 	/**
 	 * 
 	 * @return True se è soddisfatto il requisiti sulla quantità minima di luoghi nel caso venga svolto un'operazione di rimozione
 	 * 		   False altrimenti
 	 */
 	protected boolean checkVincoloLuoghiMinimi() {
 		return getLuoghi().size() > NUM_MINIMO_LUOGHI;
 	}
 	
 	/**
 	 * 
 	 * @return True se è soddisfatto il requisiti sulla quantità minima di giorni nel caso venga svolto un'operazione di rimozione
 	 * 		   False altrimenti
 	 */
 	protected boolean checkVincoloGiorniMinimi() {
 		return getGiorni().size() > NUM_MINIMO_GIORNI;
 	}
 	
 	protected boolean checkListaGiorniPiena() {
 		return getGiorni().size() == NUM_MASSIMO_GIORNI;
 	}
 	
 	/**
 	 * Precondizione: giorni != null, giorno != null
 	 * Postcondizione: giorni'.size() = giorni.size() + 1 se checkPresenzaGiorno == false
 	 * 
 	 * @param giorni
 	 * @param giorno
 	 * @throws IOException se la scrittura su file fallisce
 	 * @throws Exception se giorno è già presente in giorni
 	 */
 	protected void aggiungiGiorno(List<GiorniDellaSettimana> giorni, GiorniDellaSettimana giorno) throws IOException, Exception {
 		if(checkPresenzaGiorno(giorni, giorno)) {
 			throw new Exception();
 		} else {
 			giorni.add(giorno);
 			piazza.setGiorni(ordinaListaGiorni(giorni));
 			
 			salvaPiazza();
 		}		
 	}
 	
 	/**
 	 * Precondizione: giorni != null, giornoDaEliminare != null
 	 * Postcondizione: giorni'.size() = giorni.size() - 1 se checkPresenzaGiorno == true 
 	 * 
 	 * @param giorni
 	 * @param giornoDaEliminare
 	 * @throws IOException se la scrittura su file fallisce
 	 * @throws Exception se giorno non è presente in giorni
 	 */
 	protected void rimuoviGiorno(List<GiorniDellaSettimana> giorni, GiorniDellaSettimana giornoDaEliminare) throws IOException, Exception {
 		if(checkPresenzaGiorno(giorni, giornoDaEliminare)) {
			piazza.rimuoviGiorno(giornoDaEliminare);
			piazza.setGiorni(ordinaListaGiorni(giorni));
			
			salvaPiazza();
		} else {
 			throw new Exception();
		}
 	}
 	
 	/**
 	 * Precondizione: orari != null
 	 * @param orari
 	 * @return
 	 */
 	private IntervalloOrario estraiIntervalloMin(List<IntervalloOrario> orari) {
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
 	private List<IntervalloOrario> ordinaListaIntervalliOrari(List<IntervalloOrario> orari) {
 		List<IntervalloOrario> ordinata = new ArrayList<IntervalloOrario>();
 		
 		do {
	 		IntervalloOrario intervalloMin = estraiIntervalloMin(orari);
	 		orari.remove(intervalloMin);
	 		ordinata.add(intervalloMin);
 		} while(orari.size() > 0);
 		
 		return ordinata;
 	}
 	
 	/**
 	 * Precondizione: orari != null, orarioDaAggiungere != null
 	 * Postcondizione: orari'.size() = orari.size() + 1 se checkValiditaIntervallo == false
 	 * 
 	 * @param orari
 	 * @param orarioDaAggiungere
 	 * @throws IOException se la scrittura su file fallisce
 	 * @throws Exception se orarioDaAggiungere non è valido per essere inserito tra gli orari già presenti
 	 */
 	protected void aggiungiIntervalloOrario(List<IntervalloOrario> orari, IntervalloOrario orarioDaAggiungere) throws IOException, Exception {
 		if(checkValiditaIntervallo(orari, orarioDaAggiungere)) {
 			orari.add(orarioDaAggiungere);
 			if(orari.size() > 1)
 				setIntervalli(ordinaListaIntervalliOrari(orari));
 			salvaPiazza();
 		} else {
 			throw new Exception();
 		}		
 	}
 	
 	/**
 	 * Precondizione: orari != null, orarioDaAggiungere != null
 	 * Postcondizione: orari'.size() = orari.size() - 1 se intervalloTrovato != null
 	 * 
 	 * @param orari
 	 * @param orarioMinDaEliminare
 	 * @throws IOException se la scrittura su file fallisce
 	 * @throws Exception se l'intervallo da eliminare con orarioMinDaEliminare non è presente fra gli orari
 	 */
 	protected void rimuoviIntervalloOrario(List<IntervalloOrario> orari, LocalTime orarioMinDaEliminare) throws IOException, Exception {
 		IntervalloOrario intervalloTrovato = null;
 		
 		for (IntervalloOrario intervallo : orari) {
			if(orarioMinDaEliminare.equals(intervallo.getOrarioMin())) {
				intervalloTrovato = intervallo;
				salvaPiazza();
			}
		}
 		if(intervalloTrovato != null)
 			piazza.rimuoviIntervallo(intervalloTrovato);
 		else 
 			throw new Exception();
 	} 	
 	
 	/**
 	 * 
 	 * @return True se è soddisfatto il requisiti sulla quantità minima di Interavalli Orari nel caso venga svolto un'operazione di rimozione
 	 *		   False altrimenti         
 	 */
 	protected boolean checkVincolIntervalliMinimi() {
 		return getIntervalli().size() > NUM_MINIMO_INTERVALLI;
 	}
 	
 	/**
 	 * Precondizione: intervalliOrari != null, intervallo != null
 	 * 
 	 * @param intervalliOrari
 	 * @param intervallo
 	 * @return TRUE se intervallo è valido e può essere inserito in intervalliOrari, FALSE altrimenti
 	 */
	private boolean checkValiditaIntervallo(List<IntervalloOrario> intervalliOrari, IntervalloOrario intervallo) {
		if(intervalliOrari.size() == 0) return true;
		
		for (IntervalloOrario intervalloOrario : intervalliOrari) {
			if(intervalloOrario.parzialmenteInclude(intervallo) || 
					intervallo.parzialmenteInclude(intervalloOrario))
				return false;
		}
		
		return true;
	}
	
	/**
	 * Precondizione: intervalliOrari != null, orario != null
	 * 
	 * @param intervalliOrari
	 * @param orario
	 * @return
	 */
	protected boolean checkValiditaOrario(List<IntervalloOrario> intervalliOrari, LocalTime orario) {
		if(intervalliOrari.size() == 0) return false;
		
		for (IntervalloOrario intervalloOrario : intervalliOrari) {
			if(intervalloOrario.includeOrario(orario))
				return true;
		}
		
		return false;
	}

	/**
	 * Prende il parametro di tipo String nel formato d/M/yyyy e restituisce il relativo LocalDate
	 * @param userInput
	 * @return
	 */
	public LocalDate dateInput(String userInput) throws DateTimeException {

	    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(FORMATO_GIORNO_D_M_YYYY);
	    try {
	    	LocalDate date = LocalDate.parse(userInput, dateFormat);
	    	return date;
	    } catch (Exception e) {
			throw new DateTimeException(e.getMessage());
		}
	}
	
	/**
	 * Precondizione: date != null
	 * 
	 * Prende il parametro LocalDate, e verifica che il relativo giorno della settimana sia fra quelli messi a 
	 * dispozione della Piazza 
	 * @param date
	 * @return
	 */
	public boolean checkValiditaGiornoSettimanaPiazzaFromLocalDate(LocalDate date) {
		String dateItalianoDayOfWeek = date.format(DateTimeFormatter.ofPattern("EEEE", Locale.ITALY));	
	 
		for (GiorniDellaSettimana giornoSettimana : getGiorni()) {
			if(giornoSettimana.getNome().equalsIgnoreCase(dateItalianoDayOfWeek))
				return true;
		}
	 
		return false;
	}
	
	public void salvaPiazza() throws IOException {
		JsonIO.salvaOggettoSuJson(pathParametri, this.piazza);
	}
	
	public Piazza leggiPiazza(String path) throws IOException {
		return (Piazza) JsonIO.leggiOggettoDaJson(path, Piazza.class);
	}

	/**
	 * Precondizione: listaLuoghi != null
	 * 
	 * @param listaLuoghi
	 * @param luogoDaIndividuare
	 * @return
	 */
	public boolean checkPresenzaLuogo(List<String> listaLuoghi, String luogoDaIndividuare) {
		for (String luogo : listaLuoghi) {
			if(luogo.equalsIgnoreCase(luogoDaIndividuare))
				return true;
		}
		return false;
	}
	
	/**
	 * Precondizione: giorni != null
	 * 
	 * @param giorni
	 * @param giorno
	 * @return
	 */
	public boolean checkPresenzaGiorno(List<GiorniDellaSettimana> giorni, GiorniDellaSettimana giorno) {
		return giorni.contains(giorno);
	}
	
	/**
	 * Precondizione: giorni != null
	 * Postcondizione: giorni'.size() = giorni.size()
	 * 
	 * @param giorni
	 * @return
	 */
	public List<GiorniDellaSettimana> ordinaListaGiorni(List<GiorniDellaSettimana> giorni) {
		return GiorniDellaSettimana.ordinaLista(giorni); //ordinare la lista dei giorni presenti in piazza
	}

	/**
	 * Precondizione: scadenza > 0
	 * 
	 * @param scadenza
	 * @throws IOException 
	 */
	protected void modificaScadenza(int scadenza) throws IOException {
		piazza.setScadenza(scadenza);
		salvaPiazza();
	}
	
	public boolean isPiazzaCreata() {
		return this.piazza.getCitta() != null;
	}
}
