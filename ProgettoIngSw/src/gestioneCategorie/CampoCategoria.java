package gestioneCategorie;


public class CampoCategoria {

	private String descrizione;
	private boolean obbligatorio;
	
	public CampoCategoria() {
	}
	
	public CampoCategoria(String descrizione, boolean obbligatorio) {
		super();
		this.descrizione = descrizione;
		this.obbligatorio = obbligatorio;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public boolean isObbligatorio() {
		return obbligatorio;
	}

	public void setObbligatorio(boolean obbligatorio) {
		this.obbligatorio = obbligatorio;
	}
}
