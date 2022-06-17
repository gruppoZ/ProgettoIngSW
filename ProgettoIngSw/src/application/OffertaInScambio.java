package application;

import com.fasterxml.jackson.annotation.JsonCreator;

public class OffertaInScambio implements StatoOfferta {
	String stato; //serve esplicitarlo per jackson
	
	@JsonCreator
	public OffertaInScambio() {
		stato = StatiOfferta.OFFERTA_IN_SCAMBIO.getNome();
	}
	
	@Override
	public String getStato() {
		return this.stato;
	}
	
	@Override
	public void changeState(Offerta offerta) {
	}
}
