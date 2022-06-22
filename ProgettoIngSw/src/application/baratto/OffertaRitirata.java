package application.baratto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class OffertaRitirata extends StatoOfferta {
	String stato; //serve esplicitarlo per jackson

	@JsonCreator
	public OffertaRitirata() {
		stato = StatiOfferta.OFFERTA_RITIRATA.getNome();
	}
	
	@JsonIgnore
	@Override
	public String getStato() {
		return this.stato;
	}

	@Override
	public void accoppiaOfferta(Offerta offerta) {
		// no op
	}

	@Override
	public void apriOfferta(Offerta offerta) {
		// no op
	}

	@Override
	public void ritiraOfferta(Offerta offerta) {
		// no op
	}

	@Override
	public void chiudiOfferta(Offerta offerta) {
		// no op
	}

	@Override
	public void inScambio(Offerta offerta) {
		// no op
	}
}
