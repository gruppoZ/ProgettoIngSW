package gestioneOfferte;

import com.fasterxml.jackson.annotation.JsonCreator;

import gestioneScambioArticoli.Appuntamento;

public class OffertaInScambio implements StatoOfferta {
	String stato; //serve esplicitarlo per jackson
	Appuntamento appuntamento;

	@JsonCreator
	public OffertaInScambio() {
		stato = StatiOfferta.OFFERTA_IN_SCAMBIO.getNome();
		this.appuntamento = new Appuntamento();
	}
	
	public OffertaInScambio(Appuntamento appuntamento) {
		stato = StatiOfferta.OFFERTA_IN_SCAMBIO.getNome();
		this.appuntamento = appuntamento;
	}
	
	@Override
	public String getStato() {
		return this.stato;
	}

	public Appuntamento getAppuntamento() {
		return appuntamento;
	}
	@Override
	public void changeState(Offerta offerta) {
	}

	@Override
	public String toString() {
		return "OffertaInScambio [stato=" + stato + "]";
	}
}
