package gestioneOfferte;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OffertaAperta extends Offerta {
	
	@JsonCreator
	public OffertaAperta(@JsonProperty("articolo")Articolo articolo, @JsonProperty("username")String username) {
		super(articolo, username, "OffertaAperta");
	}
	
	public OffertaRitirata ritiraOfferta() {
		return new OffertaRitirata(this.getArticolo(), this.getUsername());
	}
}

