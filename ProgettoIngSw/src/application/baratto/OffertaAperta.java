package application.baratto;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class OffertaAperta extends StatoOfferta {
	String stato; //serve esplicitarlo per jackson
	
	@JsonCreator
	public OffertaAperta() {
		stato = StatiOfferta.OFFERTA_APERTA.getNome();
	}
	
	@JsonIgnore
	@Override
	public String getStato() {
		return stato;
	}

	@Override
	public void ritiraOfferta(Offerta offerta) throws IOException {
		StatoOfferta oldState = this;
		offerta.setStatoOfferta(new OffertaRitirata());
		
		gestisciCambiamentoStatoOfferta(offerta, oldState);
	}


	@Override
	public void accoppiaOfferta(Offerta offerta) throws IOException {
		StatoOfferta oldState = this;
		
		if(offerta.isAutore())
			offerta.setStatoOfferta(new OffertaAccoppiata());
		else 
			offerta.setStatoOfferta(new OffertaSelezionata());
		
		gestisciCambiamentoStatoOfferta(offerta, oldState);
	}
	
	@Override
	public void chiudiOfferta(Offerta offerta) {
		// no op
	}

	@Override
	public void inScambio(Offerta offerta) {
		//no op
	}

	@Override
	public void apriOfferta(Offerta offerta) {
		// no op
	}
}

