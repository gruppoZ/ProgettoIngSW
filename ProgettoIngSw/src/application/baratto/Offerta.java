package application.baratto;

import java.io.IOException;

public class Offerta {
	
	private String id;
	private Articolo articolo;
	private String username; 
	private StatoOfferta statoOfferta;
	private boolean autore;
	
	public Offerta() {		
	}
	
	/**
	 * Precondizione: articolo != null
	 * Postcondizione: this.articolo != null
	 * 
	 * @param id
	 * @param articolo
	 * @param username
	 * @param autore
	 */
	public Offerta(String id, Articolo articolo, String username, boolean autore) {
		this.id = id;
		this.articolo = articolo;
		this.username = username;
		this.autore = autore;
		this.statoOfferta = new OffertaAperta();
	}
	
	/**
	 * Precondizione: articolo != null
	 * Postcondizione: this.articolo != null
	 * 
	 * @param id
	 * @param articolo
	 * @param username
	 */
	public Offerta(String id, Articolo articolo, String username) {
		this.id = id;
		this.articolo = articolo;
		this.username = username;
		this.statoOfferta = new OffertaAperta();
	}
	
	public void accoppiaOfferta() throws IOException {
		this.statoOfferta.accoppiaOfferta(this);
	}
	
	public void inScambioOfferta() throws IOException {
		this.statoOfferta.inScambio(this);
	}
	
	public void ritiraOfferta() throws IOException {
		this.statoOfferta.ritiraOfferta(this);
	}
	
	public void chiudiOfferta() throws IOException {
		this.statoOfferta.chiudiOfferta(this);
	}
	
	public void apriOfferta() throws IOException {
		this.statoOfferta.apriOfferta(this);
	}
	
	public boolean isAutore() {
		return autore;
	}

	public void setAutore(boolean autore) {
		this.autore = autore;
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