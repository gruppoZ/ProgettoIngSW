package gestioneOfferte;

import com.fasterxml.jackson.annotation.JsonCreator;

public class OffertaAperta implements StatoOfferta {
	String stato; //serve esplicitarlo per jackson
	
	@JsonCreator
	public OffertaAperta() {
		stato = StatiOfferta.OFFERTA_APERTA.getNome();
	}
	
	@Override
	public String getStato() {
		return stato;
	}

	@Override
	public void changeState(Offerta offerta) {
		offerta.setStatoOfferta(new OffertaRitirata());	
	}

	@Override
	public String toString() {
		return "OffertaAperta [stato=" + stato + "]";
	}
}

