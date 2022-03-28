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

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		String msgObbligatorio = obbligatorio ? "Obbligatorio" : "Facolatativo";
		result.append("Campo: " + descrizione + " - " + msgObbligatorio);
		
		return result.toString();
	}
}
