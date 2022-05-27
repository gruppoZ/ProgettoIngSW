package gestioneOfferte;

import com.fasterxml.jackson.annotation.JsonCreator;

public class OffertaRitirata implements StatoOfferta {
	String stato; //serve esplicitarlo per jackson

	@JsonCreator
	public OffertaRitirata() {
		stato = StatiOfferta.OFFERTA_RITIRATA.getNome();
	}
	
	@Override
	public String getStato() {
		return this.stato;
	}

	@Override
	public void changeState(Offerta offerta) {
	}
}
