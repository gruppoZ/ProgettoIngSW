package gestioneOfferte;

import com.fasterxml.jackson.annotation.JsonCreator;

public class OffertaRitirata implements StatoOfferta {
	String identificativo; //serve esplicitarlo per jackson

	@JsonCreator
	public OffertaRitirata() {
		identificativo = StatiOfferta.OFFERTA_RITIRATA.getNome();
	}
	
	@Override
	public String getIdentificativo() {
		return this.identificativo;
	}

	@Override
	public void changeState(Offerta offerta) {		
	}
}
