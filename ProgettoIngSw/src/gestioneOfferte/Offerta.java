package gestioneOfferte;

public class Offerta {
	private String tipoOfferta;
	
	//TODO: il problema di tenere tutto in offerta e' che quando salvo la lista di offerte come faccio a capire se era un offertaAperta o
	//offertaRitirata?
	//o si tiene una string tipoOfferta in offerta e quindi niente più sottoclassi?(possibile) => a pensarci molto caruccio
	
	public Offerta() {
		
	}
	
	public Offerta(String tipoOfferta) {
		this.tipoOfferta = tipoOfferta;
	}
	public String getTipoOfferta() {
		return tipoOfferta;
	}
	
	public boolean equals(Offerta offerta) {
		return this.tipoOfferta.equals(offerta.tipoOfferta);
	}
	
}
