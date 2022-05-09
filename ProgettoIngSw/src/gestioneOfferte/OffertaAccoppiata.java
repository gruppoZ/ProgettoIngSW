package gestioneOfferte;

import com.fasterxml.jackson.annotation.JsonCreator;

public class OffertaAccoppiata implements StatoOfferta{
	String stato; //serve esplicitarlo per jackson
	
	@JsonCreator
	public OffertaAccoppiata() {
		stato = StatiOfferta.OFFERTA_ACCOPPIATA.getNome();
	}
	
	@Override
	public String getStato() {
		return stato;
	}

	@Override
	public void changeState(Offerta offerta) {
	}

	@Override
	public String toString() {
		return "OffertaAccoppiata [stato=" + stato + "]";
	}
}
