package gestioneScambioArticoli;

import java.time.LocalDate;

import gestioneOfferte.Offerta;

//TODO: questi dati potrebbero essere salvati in Offera in Scambio
public class Baratto {
	Offerta offertaA;
	Offerta offertaB;
	LocalDate scadenza;
	Appuntamento appuntamento;
	
	public Baratto() {
		
	}
	
	public Baratto(Offerta offertaA, Offerta offertaB, LocalDate scadenza) {
		this.offertaA = offertaA;
		this.offertaB = offertaB;
		this.scadenza = scadenza;
		this.appuntamento = new Appuntamento();
	}

	public Appuntamento getAppuntamento() {
		return appuntamento;
	}

	public void setAppuntamento(Appuntamento appuntamento) {
		this.appuntamento = appuntamento;
	}

	public Offerta getOffertaA() {
		return offertaA;
	}

	public Offerta getOffertaB() {
		return offertaB;
	}

	public LocalDate getScadenza() {
		return scadenza;
	}

	@Override
	public String toString() {
		return "Scambio [offertaA=" + offertaA + ", offertaB=" + offertaB + ", scadenza=" + scadenza + "]";
	}
}
