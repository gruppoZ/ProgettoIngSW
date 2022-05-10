package gestioneScambioArticoli;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appuntamento {
	String luogo;
	LocalDate data;
	LocalTime ora;
	
	public Appuntamento() {
		
	}
	/**
	 * @param luogo
	 * @param data
	 * @param ora
	 */
	public Appuntamento(String luogo, LocalDate data, LocalTime ora) {
		this.luogo = luogo;
		this.data = data;
		this.ora = ora;
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
}
