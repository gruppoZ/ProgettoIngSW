package gestioneScambioArticoli;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appuntamento {
	private String luogo;
	private LocalDate data;
	private LocalTime ora;
//	private boolean valido;
	private String username;
	
	public Appuntamento() {
		
	}
	
	/**
	 * @param luogo
	 * @param data
	 * @param ora
	 * @param valido
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
	
//	public boolean isValido() {
//		return valido;
//	}
//	
//	public void setValido(boolean valido) {
//		this.valido = valido;
//	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public boolean equals(Appuntamento appuntamento) {
		return this.luogo.equals(appuntamento.getLuogo()) && this.data.isEqual(appuntamento.getData()) && this.ora.compareTo(appuntamento.getOra()) == 0;
	}
}
