package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import application.parametriPiazza.GiorniDellaSettimana;
import application.parametriPiazza.IntervalloOrario;
import application.parametriPiazza.Piazza;
import utils.PiazzaUtils;

public class PiazzaFacade {
	
	Piazza piazza;
	
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
	}
	
 	public Piazza getPiazza() throws NullPointerException{
 		if(piazza == null)
 			throw new NullPointerException();
 		
 		return this.piazza;
 	}
 	
 	public void setPiazza(Piazza piazza) {
 		this.piazza = piazza;
 	}
 	
 	public int getScadenzaPiazza() {
 		return this.piazza.getScadenza();
 	}
 	
 	public void setCitta(String citta) {
 		this.piazza.setCitta(citta);
 	}
 	
 	public List<String> getLuoghi(){
 		return piazza.getLuoghi();
 	}
 	
 	public List<GiorniDellaSettimana> getGiorni(){
 		return piazza.getGiorni();
 	}
 	
 	public List<IntervalloOrario> getIntervalli(){
 		return piazza.getIntervalliOrari();
 	}
 	
 	protected void setIntervalli(List<IntervalloOrario> intervalli) {
 		this.piazza.setIntervalliOrari(intervalli);
 	}
 	
 	protected int getNumeroIntervalliOrari() {
 		return this.piazza.getNumeroIntervalliOrari();
 	}
 	
 	public void aggiungiLuogo(String luogo) throws Exception {
 		this.piazza.aggiungiLuogo(luogo);
 	}
 	
 	public void rimuoviLuogo(String luogoDaEliminare) throws Exception {
 		this.piazza.rimuoviLuogo(luogoDaEliminare);
 	}
 	
 	public void aggiungiGiorno(GiorniDellaSettimana giorno) throws IOException, Exception {
 		piazza.aggiungiGiorno(giorno);
 		piazza.setGiorni(ordinaListaGiorni(piazza.getGiorni()));	
 	}
 	
 	public void rimuoviGiorno(GiorniDellaSettimana giornoDaEliminare) throws Exception {
 		this.piazza.rimuoviGiorno(giornoDaEliminare);
 		//non serve riordinare i giorni per la rimozione
 	}
 	
 	public void aggiungiIntervalloOrario(IntervalloOrario orarioDaAggiungere) throws Exception {
 		piazza.aggiungiIntervalloOrario(orarioDaAggiungere);
 		piazza.setIntervalliOrari(ordinaListaIntervalliOrari(piazza.getIntervalliOrari()));
 	}
 	
 	public void rimuoviIntervalloOrario(LocalTime orarioMinDaEliminare) throws Exception {
 		piazza.rimuoviIntervalloOrario(orarioMinDaEliminare);
 	} 	
 	
 	public void modificaScadenza(int scadenza) throws IOException {
		piazza.setScadenza(scadenza);
	}
 	
 	public boolean checkPresenzaGiorno(GiorniDellaSettimana giorno) {
		return this.piazza.checkPresenzaGiorno(giorno);
	}
	
	public boolean checkPresenzaLuogo(String luogo) {
		return this.piazza.checkPresenzaLuogo(luogo);
	}
 	
	/**
 	 * Precondizione: orari != null
 	 * @param orari
 	 * @return
 	 */
 	public IntervalloOrario estraiIntervalloMin(List<IntervalloOrario> orari) { 		
 		return PiazzaUtils.estraiIntervalloMin(orari);
 	}
 	
 	/**
 	 * Precondizione: orari != null
 	 * @param orari
 	 * @return
 	 */
 	public List<IntervalloOrario> ordinaListaIntervalliOrari(List<IntervalloOrario> orari) {
 		return PiazzaUtils.ordinaListaIntervalliOrari(orari);
 	}
	
	/**
	 * Precondizione: intervalliOrari != null, orario != null
	 * 
	 * @param intervalliOrari
	 * @param orario
	 * @return True se orario è compreso in uno degli intervalli in intervalliOrari
	 */
	public boolean checkValiditaOrario(List<IntervalloOrario> intervalliOrari, LocalTime orario) {
		return PiazzaUtils.checkValiditaOrario(intervalliOrari, orario);
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
	 * Precondizione: date != null
	 * 
	 * Prende il parametro LocalDate, e verifica che il relativo giorno della settimana sia fra quelli messi a 
	 * dispozione della Piazza 
	 * @param date
	 * @param giorni
	 * @return
	 */
	public boolean checkValiditaGiornoSettimanaPiazzaFromLocalDate(LocalDate date, List<GiorniDellaSettimana> giorni) {
		return PiazzaUtils.checkValiditaGiornoSettimanaPiazzaFromLocalDate(date, giorni);
	}
	
	public LocalDate getDataFromText(String data) {
		return PiazzaUtils.dateInput(data);
	}
}
