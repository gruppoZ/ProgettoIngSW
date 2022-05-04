package gestioneOfferte;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.NAME,
include = JsonTypeInfo.As.PROPERTY,
property = "type")
@JsonSubTypes({
@Type(value = OffertaAperta.class),
@Type(value = OffertaRitirata.class),
})
public interface StatoOfferta {
	
	String getStato();
	void changeState(Offerta offerta);
	
	default void changeState(Offerta offerta, StatoOfferta newState) {
		offerta.setTipoOfferta(newState);	
	}
	
	
}