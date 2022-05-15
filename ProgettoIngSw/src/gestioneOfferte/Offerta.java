package gestioneOfferte;

public class Offerta {
	
	private int id;
	private Articolo articolo;
	private String username; 
	private StatoOfferta statoOfferta;
	

	public Offerta() {		
	}
	
	public Offerta(int id, Articolo articolo, String username, StatoOfferta tipoOfferta) {
		this.id = id;
		this.articolo = articolo;
		this.username = username;
		this.statoOfferta = tipoOfferta;
	}
	
	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}
	public Articolo getArticolo() {
		return articolo;
	}
	
	public StatoOfferta getStatoOfferta() {
		return statoOfferta;
	}

	public void setStatoOfferta(StatoOfferta tipoOfferta) {
		this.statoOfferta = tipoOfferta;
	}
	
	public boolean equals(Offerta offerta) {
		return this.id == offerta.id;
	}

	@Override
	public String toString() {
		return "Offerta [id=" + id + ", articolo=" + articolo + ", username=" + username + ", tipoOfferta="
				+ statoOfferta + "]";
	}
}
