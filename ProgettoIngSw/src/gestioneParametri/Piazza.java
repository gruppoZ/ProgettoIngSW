package gestioneParametri;

import java.util.ArrayList;
import java.util.List;

public class Piazza {
	private String citta;
	private List<String> luoghi;
	private List<GiorniDellaSettimana> giorni;
	private List<IntervalloOrario> intervalliOrari;
	private int scadenza;
	
	//LISTA intervalli -> oggetto intervalli (orario_min, ora_max) -> classe orario (ora[00-23], minuti [00 o 30]
	
	//TODO: forse scadenza ma solo le liste
	public Piazza() {
		this.luoghi = new ArrayList<>();
		this.giorni = new ArrayList<>();
		this.intervalliOrari = new ArrayList<>();
		this.scadenza = 1;
	}
	
	/**
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

	public void rimuoviLuogo(String luogoDaRimuovere) {
		this.luoghi.remove(luogoDaRimuovere);
	}
		
	public void rimuoviGiorno(GiorniDellaSettimana giornoDaRimuovere) {
		this.giorni.remove(giornoDaRimuovere);
	}
		
	public void rimuoviIntervallo(IntervalloOrario intervalloDaRimuovere) {
		this.intervalliOrari.remove(intervalloDaRimuovere);
	}
}
