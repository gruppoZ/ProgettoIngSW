package application.baratto;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonCreator;

public class OffertaAperta extends StatoOfferta {
	String stato; //serve esplicitarlo per jackson
	
	@JsonCreator
	public OffertaAperta(Offerta offerta) {
		super(offerta);
		stato = StatiOfferta.OFFERTA_APERTA.getNome();
	}
	
	@Override
	public String getStato() {
		return stato;
	}

	@Override
	public void ritiraOfferta() throws IOException {
		StatoOfferta oldState = this;
		offerta.setStatoOfferta(new OffertaRitirata(offerta));
		
		gestisciCambiamentoStatoOfferta(offerta, oldState);
	}


	@Override
	public void accoppiaOfferta() throws IOException {
		StatoOfferta oldState = this;
		
		if(offerta.isAutore())
			offerta.setStatoOfferta(new OffertaAccoppiata(offerta));
		else 
			offerta.setStatoOfferta(new OffertaSelezionata(offerta));
		
		gestisciCambiamentoStatoOfferta(offerta, oldState);
	}
	
	@Override
	public void chiudiOfferta() {
		// no op
	}

	@Override
	public void inScambio() {
		//no op
	}

	@Override
	public void apriOfferta() {
		// no op
	}
}

