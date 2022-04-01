package gestioneOfferte;

public class Pubblicazione {
	private Articolo articolo;
	private String username; //forse meglio lasciare lo username alla hashmap e non salvarlo qua
	private Offerta tipoOfferta;
	
	//costruttore vuoto per poter salvare un oggetto Pubblicazione nel file json
	public Pubblicazione() {
		
	}
	
	/**
	 * @param articolo
	 * @param username
	 * @param tipoOfferta
	 */
	public Pubblicazione(Articolo articolo, String username, Offerta tipoOfferta) {
		super();
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
	public Offerta getTipoOfferta() {
		return tipoOfferta;
	}
	
	
}
