package gestioneOfferte;

import com.fasterxml.jackson.annotation.JsonCreator;

import gestioneScambioArticoli.Appuntamento;

public class OffertaSelezionata implements StatoOfferta {
	String stato; //serve esplicitarlo per jackson
	
	@JsonCreator
	public OffertaSelezionata() {
		stato = StatiOfferta.OFFERTA_SELEZIONATA.getNome();
	}
	
	@Override
	public String getStato() {
		return this.stato;
	}

	@Override
	public void changeState(Offerta offerta) {
	}

	@Override
	public String toString() {
		return "OffertaSelezionata [stato=" + stato + "]";
	}
}
