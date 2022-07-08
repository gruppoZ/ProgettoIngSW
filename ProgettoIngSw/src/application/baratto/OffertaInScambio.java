package application.baratto;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class OffertaInScambio extends StatoOfferta {
	String stato; //serve esplicitarlo per jackson
	
	@JsonCreator
	public OffertaInScambio() {
		stato = StatiOfferta.OFFERTA_IN_SCAMBIO.getNome();
	}
	
	@JsonIgnore	
	@Override
	public String getStato() {
		return this.stato;
	}
	
	@Override
	public void chiudiOfferta(Offerta offerta) throws IOException {
		StatoOfferta oldState = this;
		offerta.setStatoOfferta(new OffertaChiusa());	
		
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
	public void inScambio(Offerta offerta) {
		// no op
	}
}
