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

public abstract class Offerta {
	
	private Articolo articolo;
	private String username; 
	private String tipoOfferta;
	
	//TODO: il problema di tenere tutto in offerta e' che quando salvo la lista di offerte come faccio a capire se era un offertaAperta o
	//offertaRitirata?
	//o si tiene una string tipoOfferta in offerta e quindi niente più sottoclassi?(possibile) => a pensarci molto caruccio

	
	public Offerta(Articolo articolo, String username, String tipoOfferta) {
		this.articolo = articolo;
		this.username = username;
		this.tipoOfferta = tipoOfferta;
	}
	
	public String getUsername() {
		return username;
	}
	public Articolo getArticolo() {
		return articolo;
	}
	
	public String getTipoOfferta() {
		return tipoOfferta;
	}
	
	public boolean equals(Offerta offerta) {
		return this.tipoOfferta.equals(offerta.tipoOfferta);
	}

	@Override
	public String toString() {
		return "Offerta [articolo=" + articolo + ", username=" + username + ", tipoOfferta=" + tipoOfferta + "]";
	}
	
}
