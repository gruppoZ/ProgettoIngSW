package gestioneOfferte;

public class Pubblicazione {
	private Articolo articolo;
	private String username; 
	private Offerta tipoOfferta; //ora come ora la classe offerta sembra inutile, forse meglio una string?
	
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

	@Override
	public String toString() {
		return "Pubblicazione [articolo=" + articolo + ", username=" + username + ", tipoOfferta=" + tipoOfferta + "]";
	}
	
	
	
}
