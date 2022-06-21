package application.baratto;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonCreator;

public class OffertaAccoppiata extends StatoOfferta {
	String stato; //serve esplicitarlo per jackson
	
	@JsonCreator
	public OffertaAccoppiata(Offerta offerta) {
		super(offerta);
		stato = StatiOfferta.OFFERTA_ACCOPPIATA.getNome();
	}
	
	@Override
	public String getStato() {
		return stato;
	}
	
	@Override
	public void inScambio() throws IOException {
		StatoOfferta oldState = this;
		offerta.setStatoOfferta(new OffertaInScambio(offerta));
		
		gestisciCambiamentoStatoOfferta(offerta, oldState);
	}

	@Override
	public void apriOfferta() throws IOException {
		StatoOfferta oldState = this;
		offerta.setStatoOfferta(new OffertaAperta(offerta));
		
		gestisciCambiamentoStatoOfferta(offerta, oldState);
	}
	
	@Override
	public void accoppiaOfferta() {
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
}
