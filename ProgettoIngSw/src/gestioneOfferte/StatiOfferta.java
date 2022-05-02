package gestioneOfferte;

public enum StatiOfferta {
	OFFERTA_APERTA("Offerta Aperta"),
	OFFERTA_RITIRATA("Offerta Ritirata");
	
	private String nome;
	
	StatiOfferta(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;
	}
}
