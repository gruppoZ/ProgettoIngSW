package gestioneOfferte;

public class Offerta {
	
	private Articolo articolo;
	private String username; 
	private StatoOfferta tipoOfferta;

	public Offerta() {		
	}
	
	public Offerta(Articolo articolo, String username, StatoOfferta tipoOfferta) {
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
	
	public StatoOfferta getTipoOfferta() {
		return tipoOfferta;
	}

	public void setTipoOfferta(StatoOfferta tipoOfferta) {
		this.tipoOfferta = tipoOfferta;
	}
	
	@Override
	public String toString() {
		return "Offerta [articolo=" + articolo + ", username=" + username + ", tipoOfferta=" + tipoOfferta + "]";
	}
	
}
