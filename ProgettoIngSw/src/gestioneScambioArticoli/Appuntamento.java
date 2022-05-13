package gestioneScambioArticoli;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appuntamento {
	private String luogo;
	private LocalDate data;
	private LocalTime ora;
	private boolean valido;
	
	public Appuntamento() {
		
	}
	/**
	 * @param luogo
	 * @param data
	 * @param ora
	 * @param valido
	 */
	public Appuntamento(String luogo, LocalDate data, LocalTime ora) {
		this.luogo = luogo;
		this.data = data;
		this.ora = ora;
		this.valido = true;
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
	
	public boolean isValido() {
		return valido;
	}
	public void setValido(boolean valido) {
		this.valido = valido;
	}	
	
}
