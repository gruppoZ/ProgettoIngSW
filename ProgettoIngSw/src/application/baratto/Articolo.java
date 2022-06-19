package application.baratto;

import java.util.HashMap;

import application.Categoria;

public class Articolo {
	private Categoria foglia;
	private HashMap<String, String> valoreCampi;
	
	public Articolo() {
		foglia = new Categoria();
		valoreCampi = new HashMap<String, String>();
	}
	
	/**
	 * Precondizione: foglia != null, valoreCampi != null
	 * Postcondizione: this.foglia != null, this.valoreCampi != null
	 * 
	 * @param foglia
	 * @param valoreCampi
	 */
	public Articolo(Categoria foglia, HashMap<String, String> valoreCampi) {
		this.foglia = foglia;
		this.valoreCampi = valoreCampi;
	}

	public Categoria getFoglia() {
		return foglia;
	}

	public void setFoglia(Categoria foglia) {
		this.foglia = foglia;
	}

	public HashMap<String, String> getValoreCampi() {
		return valoreCampi;
	}

	public void setValoreCampi(HashMap<String, String> valoreCampi) {
		this.valoreCampi = valoreCampi;
	}
	
	/**
	 * Postcondizione: this.valoreCampi'.size() = this.valoreCampi.size() + 1
	 * @param descrizioneCampo
	 * @param valore
	 */
	public void addValoreCampo(String descrizioneCampo, String valore) {
		this.valoreCampi.put(descrizioneCampo, valore);
	}
}
