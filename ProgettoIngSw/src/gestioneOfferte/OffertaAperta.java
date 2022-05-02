package gestioneOfferte;

import com.fasterxml.jackson.annotation.JsonCreator;

public class OffertaAperta implements StatoOfferta {
	String identificativo; //serve esplicitarlo per jackson
	
	@JsonCreator
	public OffertaAperta() {
		identificativo = StatiOfferta.OFFERTA_APERTA.getNome();
	}
	
	@Override
	public String getIdentificativo() {
		return identificativo;
	}

	@Override
	public void changeState(Offerta offerta) {
		offerta.setTipoOfferta(new OffertaRitirata());	
	}

	@Override
	public String toString() {
		return "OffertaAperta [identificativo=" + identificativo + "]";
	}
	
	
}

