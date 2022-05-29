package gestioneParametri;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import main.JsonIO;

public class GestioneParametri {
	
	private Piazza piazza;
	
	public GestioneParametri() {
		this.piazza = leggiPiazza();
	}
	
	private static final String PATH_PIAZZA = "resources/parametri.json";

 	public void creaPiazza(String citta, List<String> listaLuoghi, List<GiorniDellaSettimana> giorni, List<IntervalloOrario> intervalliOrari,
 			int scadenza) {
 		
		piazza =  new Piazza(citta, listaLuoghi, giorni, intervalliOrari, scadenza);
		salvaPiazza();
	}
	
 	protected Piazza getPiazza() {
 		return this.piazza;
 	}
 	
 	protected void setCitta(String citta) {
 		this.piazza.setCitta(citta);
 	}
 	
 	protected List<String> getLuoghi(){
 		return piazza.getLuoghi();
 	}
 	
 	protected List<GiorniDellaSettimana> getGiorni(){
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
 	
 	protected int getScadenza() {
 		return piazza.getScadenza();
 	}

 	/**
 	 * 
 	 * @param listaLuoghi
 	 * @param luogo
 	 */
 	protected void aggiungiLuogo(List<String> listaLuoghi, String luogo) throws RuntimeException {
 		if(checkPresenzaLuogo(listaLuoghi, luogo)) {
 			throw new RuntimeException();
 		} else {
 			listaLuoghi.add(luogo);	
 			salvaPiazza();
 		}		
 	}
 	
 	/**
 	 *
 	 * @param listaLuoghi
 	 * @param luogoDaEliminare
 	 * @return TRUE se luogo è stato rimosso dalla lista, FALSE se non è stato rimosso
 	 */
 	protected void rimuoviLuogo(List<String> listaLuoghi, String luogoDaEliminare) throws RuntimeException {
 		if(checkPresenzaLuogo(piazza.getLuoghi(), luogoDaEliminare)) {
			piazza.rimuoviLuogo(luogoDaEliminare);
			salvaPiazza();
		} else {
			throw new RuntimeException();
		}
 	}
 	
 	/**
 	 * 
 	 * @return True se è soddisfatto il requisiti sulla quantità minima di luoghi nel caso venga svolto un'operazione di rimozione
 	 * 		   False altrimenti
 	 */
 	protected boolean checkVincoloLuoghiMinimi() {
 		return getLuoghi().size() > 1;
 	}
 	
 	/**
 	 * 
 	 * @param giorni
 	 * @param giorno
 	 */
 	protected void aggiungiGiorno(List<GiorniDellaSettimana> giorni, GiorniDellaSettimana giorno) throws RuntimeException {
 		if(checkPresenzaGiorno(giorni, giorno)) {
 			throw new RuntimeException();
 		} else {
 			giorni.add(giorno);
 			piazza.setGiorni(ordinaListaGiorni(giorni));
 			
 			salvaPiazza();
 		}		
 	}
 	
 	/**
 	 * 
 	 * @param giorni
 	 * @param giornoDaEliminare
 	 */
 	protected void rimuoviGiorno(List<GiorniDellaSettimana> giorni, GiorniDellaSettimana giornoDaEliminare) throws RuntimeException {
 		if(checkPresenzaGiorno(piazza.getGiorni() ,giornoDaEliminare)) {
			piazza.rimuoviGiorno(giornoDaEliminare);
			piazza.setGiorni(ordinaListaGiorni(giorni));
			
			salvaPiazza();
		} else {
 			throw new RuntimeException();
		}
 	}
 	
 	
 	private IntervalloOrario estraiIntervalloMin(List<IntervalloOrario> orari) {
 		IntervalloOrario intervalloMin = orari.get(0);
 		
 		for (IntervalloOrario intervallo : orari) {
			if(intervallo.minIsBefore(intervalloMin.getOrarioMin())) intervalloMin = intervallo;
		}
 		
 		return intervalloMin;
 	}
 	
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
 	 * 
 	 * @param orari
 	 * @param orarioDaAggiungere
 	 */
 	protected void aggiungiIntervalloOrario(List<IntervalloOrario> orari, IntervalloOrario orarioDaAggiungere) throws RuntimeException {
 		if(checkValiditaIntervallo(orari, orarioDaAggiungere)) {
 			orari.add(orarioDaAggiungere);
 			if(orari.size() > 1)
 				setIntervalli(ordinaListaIntervalliOrari(orari));
 			salvaPiazza();
 		} else {
 			throw new RuntimeException();
 		}		
 	}
 	
 	/**
 	 *
 	 * @param giorni
 	 * @param giornoDaEliminare
 	 */
 	protected void rimuoviIntervalloOrario(List<IntervalloOrario> orari, LocalTime orarioMinDaEliminare) throws RuntimeException {
 		for (IntervalloOrario intervallo : orari) {
			if(orarioMinDaEliminare.equals(intervallo.getOrarioMin())) {
				piazza.rimuoviIntervallo(intervallo);
				salvaPiazza();
			}
		}
 		
		throw new RuntimeException();
 	} 	
 	
 	/**
 	 * 
 	 * @return True se è soddisfatto il requisiti sulla quantità minima di Interavalli Orari nel caso venga svolto un'operazione di rimozione
 	 *		   False altrimenti         
 	 */
 	protected boolean checkVincolIntervalliMinimi() {
 		return getIntervalli().size() > 1;
 	}
 	
 	/**
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

	public void salvaPiazza() {
		JsonIO.salvaOggettoSuJson(PATH_PIAZZA, this.piazza);
	}
	
	public Piazza leggiPiazza() {
		return (Piazza) JsonIO.leggiOggettoDaJson(PATH_PIAZZA, Piazza.class);
	}

	public boolean checkPresenzaLuogo(List<String> listaLuoghi, String luogo) {
		return listaLuoghi.contains(luogo);
	}
		
	boolean checkPresenzaGiorno(List<GiorniDellaSettimana> giorni, GiorniDellaSettimana giorno) {
		return giorni.contains(giorno);
	}
	
	public List<GiorniDellaSettimana> ordinaListaGiorni(List<GiorniDellaSettimana> giorni) {
		return GiorniDellaSettimana.ordinaLista(giorni); //ordinare la lista dei giorni presenti in piazza
	}
	
	public void showScadenza() {
		System.out.println(piazza.getScadenza());
	}	
	
	protected void modificaScadenza(int scadenza) {
		piazza.setScadenza(scadenza);
		salvaPiazza();
	}
	
	public boolean isPiazzaCreata() {
		return this.piazza.getCitta() != null;
	}

	@Override
	public String toString() {
		return "[Piazza=" + piazza + "]";
	}

}
