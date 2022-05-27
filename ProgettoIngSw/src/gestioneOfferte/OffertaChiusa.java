package gestioneOfferte;

import com.fasterxml.jackson.annotation.JsonCreator;

public class OffertaChiusa implements StatoOfferta {
	String stato; //serve esplicitarlo per jackson

	@JsonCreator
	public OffertaChiusa() {
		stato = StatiOfferta.OFFERTA_CHIUSA.getNome();
	}
	
	@Override
	public String getStato() {
		return this.stato;
	}

	@Override
	public void changeState(Offerta offerta) {
	}
}
