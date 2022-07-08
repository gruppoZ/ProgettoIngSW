package application.parametriPiazza;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import utils.PiazzaUtils;

public class Piazza {
	private String citta;
	private List<String> luoghi;
	private List<GiorniDellaSettimana> giorni;
	private List<IntervalloOrario> intervalliOrari;
	private int scadenza;
		
	public Piazza() {
		this.luoghi = new ArrayList<>();
		this.giorni = new ArrayList<>();
		this.intervalliOrari = new ArrayList<>();
		this.scadenza = 1;
	}
	
	/**
	 * Precondizione: citta.length > 0, luoghi.size() > 0, giorni.size() > 0, intervalliOrari.size() > 0, scadenza > 0
	 * 
	 * @param citta
	 * @param luoghi
	 * @param giorni
	 * @param intervalliOrari
	 */
	public Piazza(String citta, List<String> luoghi, List<GiorniDellaSettimana> giorni,
			List<IntervalloOrario> intervalliOrari, int scadenza) {
		this.citta = citta;
		this.luoghi = luoghi;
		this.giorni = giorni;
		this.intervalliOrari = intervalliOrari;
		this.scadenza = scadenza;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}
	public List<String> getLuoghi() {
		return luoghi;
	}

	public void setLuoghi(List<String> luoghi) {
		this.luoghi = luoghi;
	}

	public List<GiorniDellaSettimana> getGiorni() {
		return giorni;
	}

	public void setGiorni(List<GiorniDellaSettimana> giorni) {
		this.giorni = giorni;
	}

	public List<IntervalloOrario> getIntervalliOrari() {
		return intervalliOrari;
	}

	public void setIntervalliOrari(List<IntervalloOrario> intervalliOrari) {
		this.intervalliOrari = intervalliOrari;
	}
	
	public int getScadenza() {
		return scadenza;
	}
	
	public void setScadenza(int scadenza) {
		this.scadenza = scadenza;
	}

	@JsonIgnore
	public int getNumeroIntervalliOrari() {
 		return this.getIntervalliOrari().size();
 	}
	
	/**
	 * Precondizione: luogoDaRimuovere != null
	 * Postcondizione: luoghi'.size() = luoghi.size() - 1
	 * 
	 * @param luogoDaRimuovere
	 * @throws Exception 
	 */
	public void rimuoviLuogo(String luogoDaRimuovere) throws Exception {
		if(checkPresenzaLuogo(luogoDaRimuovere)) {
			this.luoghi.remove(luogoDaRimuovere);
		} else {
			throw new Exception();
		}
	}
	
	/**
 	 * Precondizione: luoghi != null
 	 * Postcondizione: luoghi'.size() = luoghi.size() + 1 se checkPresenzaLuogo == false
 	 * 
 	 * Aggiunge luogo alla lista dei luoghi della piazza se non e' gia' presente
 	 * @param luogoDaAggiungere
 	 * @throws Exception nel caso in cui il luogo sia già presente in listaLuoghi della piazza
 	 */
 	public void aggiungiLuogo(String luogoDaAggiungere) throws Exception {
 		if(checkPresenzaLuogo(luogoDaAggiungere)) {
 			throw new Exception();
 		} else {
 			this.luoghi.add(luogoDaAggiungere);	
 		}		
 	}	
		
 	/**
 	 * Precondizione: giorni != null, giornoDaEliminare != null
 	 * Postcondizione: giorni'.size() = giorni.size() - 1 se checkPresenzaGiorno == true 
 	 * 
 	 * @param giornoDaEliminare
 	 * @throws Exception se giorno non è presente in giorni
 	 */
 	public void rimuoviGiorno(GiorniDellaSettimana giornoDaEliminare) throws Exception {
 		if(checkPresenzaGiorno( giornoDaEliminare)) {
			this.giorni.remove(giornoDaEliminare);
		} else {
 			throw new Exception();
		}
 	}
	
	/**
 	 * Precondizione: giorni != null, giorno != null
 	 * Postcondizione: giorni'.size() = giorni.size() + 1 se checkPresenzaGiorno == false
 	 * 
 	 * @param giorno
 	 * @throws Exception se giorno è già presente in giorni
 	 */
 	public void aggiungiGiorno(GiorniDellaSettimana giorno) throws Exception {
 		if(checkPresenzaGiorno(giorno)) {
 			throw new Exception();
 		} else {
 			giorni.add(giorno);
 		}		
 	}
		
 	/**
 	 * Precondizione: intervalliOrari != null, orarioDaAggiungere != null
 	 * Postcondizione: intervalliOrari'.size() = intervalliOrari.size() + 1 se checkValiditaIntervallo == false
 	 * 
 	 * @param orarioDaAggiungere
 	 * @throws Exception se orarioDaAggiungere non è valido per essere inserito tra gli orari già presenti
 	 */
 	public void aggiungiIntervalloOrario(IntervalloOrario orarioDaAggiungere) throws IOException, Exception {
 		if(checkValiditaIntervallo(orarioDaAggiungere)) {
 			this.intervalliOrari.add(orarioDaAggiungere);
 		} else {
 			throw new Exception();
 		}		
 	}
 	
 	/**
 	 * Precondizione: orari != null, orarioDaAggiungere != null
 	 * Postcondizione: orari'.size() = orari.size() - 1 se intervalloTrovato != null
 	 * 
 	 * @param orarioMinDaEliminare
 	 * @throws Exception se l'intervallo da eliminare con orarioMinDaEliminare non è presente fra gli orari
 	 */
 	public void rimuoviIntervalloOrario(LocalTime orarioMinDaEliminare) throws IOException, Exception {
 		IntervalloOrario intervalloTrovato = null;
 		
 		for (IntervalloOrario intervallo : this.intervalliOrari) {
			if(orarioMinDaEliminare.equals(intervallo.getOrarioMin())) {
				intervalloTrovato = intervallo;
			}
		}
 		if(intervalloTrovato != null)
 			this.intervalliOrari.remove(intervalloTrovato);
 		else 
 			throw new Exception();
 	} 
	
	/**
	 * Precondizione: luogoDaIndividuare != null
	 * 
	 * @param luogoDaRimuovere
	 * @return
	 */
	public boolean checkPresenzaLuogo(String luogoDaRimuovere) {
		for (String luogo : this.luoghi) {
			if(luogo.equalsIgnoreCase(luogoDaRimuovere))
				return true;
		}
		return false;
	}
	
	/**
	 * Precondizione: giorni != null
	 * 
	 * @param giorno
	 * @return
	 */
	public boolean checkPresenzaGiorno(GiorniDellaSettimana giorno) {
		return this.giorni.contains(giorno);
	}
	
	/**
 	 * Precondizione: intervalliOrari != null, intervallo != null
 	 * 
 	 * @param intervallo
 	 * @return TRUE se intervallo è valido e può essere inserito in intervalliOrari, FALSE altrimenti
 	 */
	public boolean checkValiditaIntervallo(IntervalloOrario intervallo) {
		if(PiazzaUtils.checkValiditaMinuti(intervallo)) return false;
		
		if(intervalliOrari.size() == 0) return true;
		
		for (IntervalloOrario intervalloOrario : intervalliOrari) {
			if(intervalloOrario.checkValiditaSovrapposizioneTraIntervalli(intervallo))
				return false;
		}
		
		return true;		
	}
}
