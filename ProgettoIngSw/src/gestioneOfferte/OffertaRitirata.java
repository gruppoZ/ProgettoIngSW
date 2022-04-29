package gestioneOfferte;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OffertaRitirata extends Offerta {
	
	@JsonCreator
	public OffertaRitirata(@JsonProperty("articolo")Articolo articolo, @JsonProperty("username")String username) {
		super(articolo, username, "OffertaRitirata");
	}
}
