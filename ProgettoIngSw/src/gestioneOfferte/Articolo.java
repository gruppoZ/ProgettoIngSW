package gestioneOfferte;

import java.util.HashMap;

import gestioneCategorie.CampoCategoria;
import gestioneCategorie.Categoria;

public class Articolo {
	private Categoria foglia;
	private HashMap<CampoCategoria, String> valoreCampi;
	
	/**
	 * @param foglia
	 * @param valoreCampi
	 */
	public Articolo(Categoria foglia, HashMap<CampoCategoria, String> valoreCampi) {
		super();
		this.foglia = foglia;
		this.valoreCampi = valoreCampi;
	}

	public Categoria getFoglia() {
		return foglia;
	}

	public void setFoglia(Categoria foglia) {
		this.foglia = foglia;
	}

	public HashMap<CampoCategoria, String> getValoreCampi() {
		return valoreCampi;
	}

	public void setValoreCampi(HashMap<CampoCategoria, String> valoreCampi) {
		this.valoreCampi = valoreCampi;
	}
	
	
}
