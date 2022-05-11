package gestioneScambioArticoli;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appuntamento {
	String luogo;
	LocalDate data;
	LocalTime ora;
	boolean rifiutato;
	
	public Appuntamento() {
		
	}
	/**
	 * @param luogo
	 * @param data
	 * @param ora
	 * @param rifiutato
	 */
	public Appuntamento(String luogo, LocalDate data, LocalTime ora) {
		this.luogo = luogo;
		this.data = data;
		this.ora = ora;
		this.rifiutato = false;
	}

	public String getLuogo() {
		return luogo;
	}

	public LocalDate getData() {
		return data;
	}

	public LocalTime getOra() {
		return ora;
	}
	
	public boolean isRifiutato() {
		return rifiutato;
	}
	public void setRifiutato(boolean rifiutato) {
		this.rifiutato = rifiutato;
	}	
	
}
