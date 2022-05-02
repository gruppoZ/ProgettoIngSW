package gestioneOfferte;

public class Offerta {
	
	private Articolo articolo;
	private String username; 
	private StatoOfferta statoOfferta;

	public Offerta() {		
	}
	
	public Offerta(Articolo articolo, String username, StatoOfferta statoOfferta) {
		this.articolo = articolo;
		this.username = username;
		this.statoOfferta = statoOfferta;
	}
	
	public String getUsername() {
		return username;
	}
	public Articolo getArticolo() {
		return articolo;
	}
	
	public StatoOfferta getTipoOfferta() {
		return statoOfferta;
	}

	public void setTipoOfferta(StatoOfferta tipoOfferta) {
		this.statoOfferta = tipoOfferta;
	}
	
	@Override
	public String toString() {
		return "Offerta [articolo=" + articolo + ", username=" + username + ", tipoOfferta=" + statoOfferta + "]";
	}
	
}
