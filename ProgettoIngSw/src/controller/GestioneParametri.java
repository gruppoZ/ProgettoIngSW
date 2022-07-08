package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import application.parametriPiazza.GiorniDellaSettimana;
import application.parametriPiazza.IntervalloOrario;
import application.parametriPiazza.Piazza;
import utils.FileSystemOperations;
import utils.JsonIO;

public class GestioneParametri {
	
	private static final int NUM_MINIMO_GIORNI = 1;
	private static final int NUM_MASSIMO_GIORNI = 7;
	private static final int NUM_MINIMO_INTERVALLI = 1;
	private static final int NUM_MINIMO_LUOGHI = 1;
	public static final int MINUTI_MIN = 0;
	public static final int MINUTI_MAX = 30;
	
	private String pathParametri;	
	private FileSystemOperations fs;
	private PiazzaFacade piazzaFacade;
	
	/**
	 * Postcondizione: pathParametri.length() > 0, this.piazza != null
	 * @throws IOException 
	 */
	public GestioneParametri() throws IOException {
		fs = new JsonIO();
		GestioneFileProgramma info = new GestioneFileProgramma();
		pathParametri = info.getInfoSistema().getUrlParametri(); 
		
		Piazza piazza = leggiPiazza(pathParametri);
		this.piazzaFacade = new PiazzaFacade();
		this.piazzaFacade.setPiazza(piazza);
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
 			int scadenza) throws IOException, NullPointerException {
 		
		piazzaFacade.creaPiazza(citta, listaLuoghi, giorni, intervalliOrari, scadenza);
		salvaPiazza();
	}
	
 	public Piazza getPiazza() throws IOException {
 		try {
 			return this.piazzaFacade.getPiazza();
 		} catch (NullPointerException e) {
 			this.piazzaFacade.setPiazza(leggiPiazza(pathParametri));
 			
 			return this.piazzaFacade.getPiazza();
 		}
 	}
 	
 	public void setCitta(String citta) {
 		this.piazzaFacade.setCitta(citta);
 	}
 	
 	public List<String> getLuoghi(){
 		return piazzaFacade.getLuoghi();
 	}
 	
 	public List<GiorniDellaSettimana> getGiorni(){
 		return piazzaFacade.getGiorni();
 	}
 	
 	public List<IntervalloOrario> getIntervalli(){
 		return piazzaFacade.getIntervalli();
 	}
 	
 	protected void setIntervalli(List<IntervalloOrario> intervalli) {
 		this.piazzaFacade.setIntervalli(intervalli);
 	}
 	
 	protected int getNumeroIntervalliOrari() {
 		return piazzaFacade.getNumeroIntervalliOrari();
 	}
 	
 	public int getScadenza() {
 		return piazzaFacade.getScadenzaPiazza();
 	}
 	
 	public void salvaPiazza() throws IOException, NullPointerException {
		fs.salvaOggetto(pathParametri, piazzaFacade.getPiazza());
	}
	
	public Piazza leggiPiazza(String path) throws IOException {
		return (Piazza) fs.leggiOggetto(path, Piazza.class);
	}
 	
 	public void importaParametri(String path) throws IOException, NullPointerException {
 		piazzaFacade.setPiazza(leggiPiazza(path));
 		salvaPiazza();	
 	}

 	public void aggiungiLuogo(String luogo) throws Exception, IOException {
 		piazzaFacade.aggiungiLuogo(luogo);
 		salvaPiazza();	
 	}

 	public void rimuoviLuogo(String luogoDaEliminare) throws IOException, Exception {
 		piazzaFacade.rimuoviLuogo(luogoDaEliminare);
 		salvaPiazza();
 	}
 	 	
 	/**
 	 * Precondizione: giorno != null
 	 * 
 	 * @param giorno
 	 * @throws IOException se la scrittura su file fallisce
 	 * @throws Exception se giorno è già presente tra i giorni della piazza
 	 */
 	public void aggiungiGiorno(GiorniDellaSettimana giorno) throws IOException, Exception {
 		piazzaFacade.aggiungiGiorno(giorno);
 		salvaPiazza();		
 	}
 	
 	/**
 	 * Precondizione: giornoDaEliminare != null
 	 * 
 	 * @param giornoDaEliminare
 	 * @throws IOException se la scrittura su file fallisce
 	 * @throws Exception se giorno non è presente tra i giorni della piazza
 	 */
 	public void rimuoviGiorno(GiorniDellaSettimana giornoDaEliminare) throws IOException, Exception {
 		piazzaFacade.rimuoviGiorno(giornoDaEliminare);
 		salvaPiazza();
 	}

 	/**
 	 * Precondizione: orarioDaAggiungere != null
 	 * 
 	 * @param orarioDaAggiungere
 	 * @throws IOException se la scrittura su file fallisce
 	 * @throws Exception se orarioDaAggiungere non è valido per essere inserito tra gli orari già presenti
 	 */
 	public void aggiungiIntervalloOrario(IntervalloOrario orarioDaAggiungere) throws IOException, Exception {
 		piazzaFacade.aggiungiIntervalloOrario(orarioDaAggiungere); 		
 		salvaPiazza();	
 	}
 	
 	/**
 	 * Precondizione: orarioDaAggiungere != null
 	 * 
 	 * @param orarioMinDaEliminare
 	 * @throws IOException se la scrittura su file fallisce
 	 * @throws Exception se l'intervallo da eliminare con orarioMinDaEliminare non è presente fra gli orari
 	 */
 	public void rimuoviIntervalloOrario(LocalTime orarioMinDaEliminare) throws IOException, Exception {
 		piazzaFacade.rimuoviIntervalloOrario(orarioMinDaEliminare);
 		salvaPiazza();
 	} 	
 	
	/**
	 * Precondizione: intervalliOrari != null, orario != null
	 * 
	 * @param intervalliOrari
	 * @param orario
	 * @return True se orario è compreso in uno degli intervalli in intervalliOrari
	 */
	public boolean checkValiditaOrario(List<IntervalloOrario> intervalliOrari, LocalTime orario) {
		return piazzaFacade.checkValiditaOrario(intervalliOrari, orario);
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
		return piazzaFacade.checkValiditaGiornoSettimanaPiazzaFromLocalDate(date, getGiorni());
	}
	
	public boolean checkPresenzaGiorno(GiorniDellaSettimana giorno) {
		return piazzaFacade.checkPresenzaGiorno(giorno);
	}
	
	public boolean checkPresenzaLuogo(String luogo) {
		return piazzaFacade.checkPresenzaLuogo(luogo);
	}
	
	/**
	 * Precondizione: giorni != null
	 * Postcondizione: giorni'.size() = giorni.size()
	 * 
	 * @param giorni
	 * @return
	 */
	public List<GiorniDellaSettimana> ordinaListaGiorni(List<GiorniDellaSettimana> giorni) {
		return piazzaFacade.ordinaListaGiorni(giorni); //ordinare la lista dei giorni presenti in piazza
	}
	
	/**
	 * Precondizione: scadenza > 0
	 * 
	 * @param scadenza
	 * @throws IOException 
	 */
	public void modificaScadenza(int scadenza) throws IOException {
		piazzaFacade.modificaScadenza(scadenza);
		salvaPiazza();
	}
	
	public boolean isPiazzaCreata() throws IOException {
		return getPiazza().getCitta() != null;
	}
	
	public LocalDate getDataFromText(String data) {
		return piazzaFacade.getDataFromText(data);
	}
	
	/**
 	 * 
 	 * @return True se è soddisfatto il requisiti sulla quantità minima di luoghi nel caso venga svolto un'operazione di rimozione
 	 * 		   False altrimenti
 	 */
 	public boolean checkVincoloLuoghiMinimi() {
 		return getLuoghi().size() > NUM_MINIMO_LUOGHI;
 	}
 	
 	/**
 	 * 
 	 * @return True se è soddisfatto il requisiti sulla quantità minima di giorni nel caso venga svolto un'operazione di rimozione
 	 * 		   False altrimenti
 	 */
 	public boolean checkVincoloGiorniMinimi() {
 		return getGiorni().size() > NUM_MINIMO_GIORNI;
 	}
 	
 	public boolean checkListaGiorniPiena() {
 		return getGiorni().size() == NUM_MASSIMO_GIORNI;
 	}
 	
 	/**
 	 * 
 	 * @return True se è soddisfatto il requisiti sulla quantità minima di Interavalli Orari nel caso venga svolto un'operazione di rimozione
 	 *		   False altrimenti         
 	 */
 	public boolean checkVincolIntervalliMinimi() {
 		return getIntervalli().size() > NUM_MINIMO_INTERVALLI;
 	}
}
