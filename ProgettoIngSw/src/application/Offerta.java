package application;

public class Offerta {
	
	private String id;
	private Articolo articolo;
	private String username; 
	private StatoOfferta statoOfferta;
	

	public Offerta() {		
	}
	
	/**
	 * Precondizione: id >= 1, articolo != null, tipoOfferta != null
	 * Postcondizione: this.id >= 1, this.articolo != null, this.statoOfferta != null
	 * 
	 * @param id
	 * @param articolo
	 * @param username
	 * @param tipoOfferta
	 */
	public Offerta(String id, Articolo articolo, String username, StatoOfferta tipoOfferta) {
		this.id = id;
		this.articolo = articolo;
		this.username = username;
		this.statoOfferta = tipoOfferta;
	}
	
	public String getId() {
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
	
	/**
	 * Precondizione: offerta != null
	 * 
	 * @param offerta
	 * @return
	 */
	public boolean equals(Offerta offerta) {
		return this.id.equals(offerta.getId());
	}
}