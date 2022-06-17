package application;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appuntamento {
	private String luogo;
	private LocalDate data;
	private LocalTime ora;
	private String username;
	
	public Appuntamento() {
	}
	
	/**
	 * Precondizione: luogo != null, ora != null
	 * Postcondizione: this.luogo != null, this.ora != null
	 * @param luogo
	 * @param data
	 * @param ora
	 * @param username
	 */
	public Appuntamento(String luogo, LocalDate data, LocalTime ora, String username) {
		this.luogo = luogo;
		this.data = data;
		this.ora = ora;
		this.username = username;
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
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Precondizione: appuntamento != null
	 * 
	 * @param appuntamento
	 * @return TRUE quando luogo data e ora sono uguali
	 */
	public boolean equals(Appuntamento appuntamento) {
		return this.luogo.equals(appuntamento.getLuogo()) && this.data.isEqual(appuntamento.getData()) && this.ora.compareTo(appuntamento.getOra()) == 0;
	}
}
