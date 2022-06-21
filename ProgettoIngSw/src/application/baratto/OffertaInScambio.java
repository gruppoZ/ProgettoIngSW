package application.baratto;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonCreator;

public class OffertaInScambio extends StatoOfferta {
	String stato; //serve esplicitarlo per jackson
	
	@JsonCreator
	public OffertaInScambio(Offerta offerta) {
		super(offerta);
		stato = StatiOfferta.OFFERTA_IN_SCAMBIO.getNome();
	}
	
	@Override
	public String getStato() {
		return this.stato;
	}
	
	@Override
	public void chiudiOfferta() throws IOException {
		StatoOfferta oldState = this;
		offerta.setStatoOfferta(new OffertaChiusa(offerta));	
		
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
	public void inScambio() {
		// no op
	}
}
