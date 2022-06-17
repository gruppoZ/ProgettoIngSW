package application;

public enum StatiOfferta {
	OFFERTA_APERTA("OffertaAperta"),
	OFFERTA_RITIRATA("OffertaRitirata"),
	OFFERTA_ACCOPPIATA("OffertaAccoppiata"),
	OFFERTA_SELEZIONATA("OffertaSelezionata"),
	OFFERTA_CHIUSA("OffertaChiusa"),
	OFFERTA_IN_SCAMBIO("OffertaInScambio");
	
	private String nome;
	
	StatiOfferta(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;
	}
}
