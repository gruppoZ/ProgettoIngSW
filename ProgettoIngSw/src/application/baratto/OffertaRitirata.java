package application.baratto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class OffertaRitirata extends StatoOfferta {
	String stato; //serve esplicitarlo per jackson

	@JsonCreator
	public OffertaRitirata(Offerta offerta) {
		super(offerta);
		stato = StatiOfferta.OFFERTA_RITIRATA.getNome();
	}
	
	@Override
	public String getStato() {
		return this.stato;
	}

	@Override
	public void accoppiaOfferta() {
		// no op
	}

	@Override
	public void apriOfferta() {
		// no op
	}

	@Override
	public void ritiraOfferta() {
		// no op
	}

	@Override
	public void chiudiOfferta() {
		// no op
	}

	@Override
	public void inScambio() {
		// no op
	}
}
