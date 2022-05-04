package gestioneOfferte;

public enum StatiOfferta {
	OFFERTA_APERTA("OffertaAperta"),
	OFFERTA_RITIRATA("OffertaRitirata");
	
	private String nome;
	
	StatiOfferta(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;
	}
}
