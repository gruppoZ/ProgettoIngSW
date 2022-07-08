package application.baratto;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class OffertaAccoppiata extends StatoOfferta {
	String stato; //serve esplicitarlo per jackson
	
	@JsonCreator
	public OffertaAccoppiata() {
		stato = StatiOfferta.OFFERTA_ACCOPPIATA.getNome();
	}
	
	@JsonIgnore
	@Override
	public String getStato() {
		return stato;
	}
	
	@Override
	public void inScambio(Offerta offerta) throws IOException {
		StatoOfferta oldState = this;
		offerta.setStatoOfferta(new OffertaInScambio());
		
		gestisciCambiamentoStatoOfferta(offerta, oldState);
	}

	@Override
	public void apriOfferta(Offerta offerta) throws IOException {
		StatoOfferta oldState = this;
		offerta.setStatoOfferta(new OffertaAperta());
		
		gestisciCambiamentoStatoOfferta(offerta, oldState);
		offerta.setAutore(false);
	}
	
	@Override
	public void accoppiaOfferta(Offerta offerta) {
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
}
