package gestioneParametri;

import java.time.LocalTime;
import java.util.List;
import main.JsonIO;

//TODO : ordinare gli intervalli (ordine crescente)

public class GestioneParametri {
	
	private Piazza piazza;
	
	public GestioneParametri() {
		this.piazza = leggiPiazza();
	}
	
	private static final String PATH_PIAZZA = "src/gestioneParametri/parametri.json";

 	public void creaPiazza(String citta, List<String> listaLuoghi, List<GiorniDellaSettimana> giorni, List<IntervalloOrario> intervalliOrari,
 			int scadenza) {
 		
		piazza =  new Piazza(citta, listaLuoghi, giorni, intervalliOrari, scadenza);
		salvaPiazza();
	}
	
 	protected Piazza getPiazza() {
 		return this.piazza;
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
 	
 	protected int getNumeroIntervalliOrari() {
 		return piazza.getIntervalliOrari().size();
 	}

 	/**
 	 * 
 	 * @param listaLuoghi
 	 * @param luogo
 	 * @return TRUE se luogo � stato aggiunto alla lista, FALSE se non � stato aggiunto a causa di duplicati
 	 */
 	protected boolean checkAggiuntaLuogo(List<String> listaLuoghi, String luogo) {
 		if(checkPresenzaLuogo(listaLuoghi, luogo)) {
 			return false;
 		} else {
 			listaLuoghi.add(luogo);	
 			salvaPiazza();
 			return true;
 		}		
 	}
 	
 	/**
 	 *
 	 * @param listaLuoghi
 	 * @param luogoDaEliminare
 	 * @return TRUE se luogo � stato rimosso dalla lista, FALSE se non � stato rimosso
 	 */
 	protected boolean checkRimozioneLuogo(List<String> listaLuoghi, String luogoDaEliminare) {
 		if(checkPresenzaLuogo(piazza.getLuoghi(), luogoDaEliminare)) {
			piazza.rimuoviLuogo(luogoDaEliminare);
			salvaPiazza();
			return true;
		} else {
			return false;
		}
 	}
 	
 	/**
 	 * 
 	 * @return True se � soddisfatto il requisiti sulla quantit� minima di luoghi nel caso venga svolto un'operazione di rimozione
 	 * 		   False altrimenti
 	 */
 	protected boolean checkVincoloLuoghiMinimi() {
 		return getLuoghi().size() > 1;
 	}
 	
 	/**
 	 * 
 	 * @param giorni
 	 * @param giorno
 	 * @return TRUE se giorno � stato aggiunto alla lista, FALSE se non � stato aggiunto a causa di duplicati
 	 */
 	protected boolean checkAggiuntaGiorno(List<GiorniDellaSettimana> giorni, GiorniDellaSettimana giorno) {
 		if(checkPresenzaGiorno(giorni, giorno)) {
 			return false;
 		} else {
 			giorni.add(giorno);
 			salvaPiazza();
 			return true;
 		}		
 	}
 	
 	/**
 	 * 
 	 * @param giorni
 	 * @param giornoDaEliminare
 	 * @return TRUE se giorno � stato rimosso dalla lista, FALSE se non � stato rimosso
 	 */
 	protected boolean checkRimozioneGiorno(List<GiorniDellaSettimana> giorni, GiorniDellaSettimana giornoDaEliminare) {
 		if(checkPresenzaGiorno(piazza.getGiorni() ,giornoDaEliminare)) {
			piazza.rimuoviGiorno(giornoDaEliminare);
			salvaPiazza();
			return true;
		} else {
			return false;
		}
 	}
 	
 	/**
 	 * 
 	 * @param orari
 	 * @param orarioDaAggiungere
 	 * @return TRUE se intervalloOrario � stato aggiunto alla lista, FALSE se non � stato aggiunto a causa di incongruenze con altri intervalli
 	 */
 	protected boolean checkAggiuntaIntervalloOrario(List<IntervalloOrario> orari, IntervalloOrario orarioDaAggiungere) {
 		if(checkValiditaIntervallo(orari, orarioDaAggiungere)) {
 			orari.add(orarioDaAggiungere);
 			salvaPiazza();
 			return true;
 		} else {
 			return false;
 		}		
 	}
 	
 	/**
 	 *
 	 * @param giorni
 	 * @param giornoDaEliminare
 	 * @return TRUE se intervalloOrario � stato rimosso dalla lista, FALSE se non � stato rimosso
 	 */
 	protected boolean checkRimozioneIntervalloOrario(List<IntervalloOrario> orari, LocalTime orarioMinDaEliminare) {
 		boolean trovato = false;
 		
 		for (IntervalloOrario intervallo : orari) {
			if(orarioMinDaEliminare.equals(intervallo.getOrarioMin())) {
				trovato = true;
				piazza.rimuoviIntervallo(intervallo);
				salvaPiazza();
			}
		}
 		return trovato;
 	} 	
 	
 	/**
 	 * 
 	 * @return True se � soddisfatto il requisiti sulla quantit� minima di Interavalli Orari nel caso venga svolto un'operazione di rimozione
 	 *		   False altrimenti         
 	 */
 	protected boolean checkVincolIntervalliMinimi() {
 		return getIntervalli().size() > 1;
 	}
 	
 	/**
 	 * 
 	 * @param intervalliOrari
 	 * @param intervallo
 	 * @return TRUE se intervallo � valido e pu� essere inserito in intervalliOrari, FALSE altrimenti
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
	}
	public boolean isPiazzaCreata() {
		return this.piazza.getCitta() != null;
	}

	@Override
	public String toString() {
		return "[Piazza=" + piazza + "]";
	}

}
