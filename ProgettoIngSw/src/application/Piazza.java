package application;

import java.util.ArrayList;
import java.util.List;

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

	/**
	 * Precondizione: luogoDaRimuovere != null
	 * Postcondizione: luoghi'.size() = luoghi.size() - 1
	 * 
	 * @param luogoDaRimuovere
	 */
	public void rimuoviLuogo(String luogoDaRimuovere) {
		this.luoghi.remove(luogoDaRimuovere);
	}
		
	/**
	 * Precondizione: giornoDaRimuovere != null
	 * Postcondizione: giorni'.size() = giorni.size() - 1
	 * 
	 * @param giornoDaRimuovere
	 */
	public void rimuoviGiorno(GiorniDellaSettimana giornoDaRimuovere) {
		this.giorni.remove(giornoDaRimuovere);
	}
		
	/**
	 * Precondizione: intervalloDaRimuovere != null
	 * Postcondizione: intervalliOrari'.size() = intervalliOrari.size() - 1
	 * 
	 * @param intervalloDaRimuovere
	 */
	public void rimuoviIntervallo(IntervalloOrario intervalloDaRimuovere) {
		this.intervalliOrari.remove(intervalloDaRimuovere);
	}
}
