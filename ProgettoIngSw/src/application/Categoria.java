package application;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Categoria {
	private String nome;
	private String descrizione;
	private boolean isRadice;
	private List<Categoria> sottoCategorie;
	private List<CampoCategoria> campiNativi;
	private List<CampoCategoria> campiEreditati;
	private int profondita;
	private boolean isValida;
	
	public Categoria() {
	}
	
	public Categoria(String nome, String descrizione, boolean isRadice, List<CampoCategoria> campiNativi, List<CampoCategoria> campiEreditati) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.isRadice = isRadice;
		this.sottoCategorie = new ArrayList<>();
		this.campiNativi = campiNativi; 
		this.campiEreditati = campiEreditati;
		this.isValida = true;
	}
	
	public boolean isValida() {
		return isValida;
	}

	public void setValida(boolean isValida) {
		this.isValida = isValida;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isRadice() {
		return isRadice;
	}

	public void setRadice(boolean isRadice) {
		this.isRadice = isRadice;
	}

	/**
	 * 
	 * @return Lista di sottocategorie, vuota nel caso non ce ne siano (e non NULL)
	 */
	public List<Categoria> getSottoCategorie() {
		return sottoCategorie;
	}

	public void setSottoCategorie(List<Categoria> sottoCategorie) {
		this.sottoCategorie = sottoCategorie;
	}

	/**
	 * 
	 * @return Lista di campi nativi, vuota nel caso non ce ne siano (e non NULL)
	 */
	public List<CampoCategoria> getCampiNativi() {
		return campiNativi;
	}
	
	public void setCampiNativi(List<CampoCategoria> campiNativi) {
		this.campiNativi = campiNativi;
	}

	/**
	 * 
	 * @return Lista di campi ereditati, vuota nel caso non ce ne siano (e non NULL)
	 */
	public List<CampoCategoria> getCampiEreditati() {
		return campiEreditati;
	}

	public void setCampiEreditati(List<CampoCategoria> campiEreditati) {
		this.campiEreditati = campiEreditati;
	}
	
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public int getProfondita() {
		return this.profondita;
	}

	public void setProfondita(int profondita) {
		this.profondita = profondita;
	}

	/**
	 *  Postcondizione: listaRitornata != null
	 * 
	 * @return lista completa contenente sia campi nativi che ereditati, vuota se non presenti (e non null)
	 */
	@JsonIgnore
	public List<CampoCategoria> getCampiNativiEreditati() {
		if(isRadice)
			return campiNativi;
		else {
			ArrayList<CampoCategoria> campi = new ArrayList<CampoCategoria>();
			
			if(campiNativi.size() != 0) {
				for (CampoCategoria campoNativo : campiNativi) {
					campi.add(campoNativo);
				}
			}
			
			if(campiEreditati.size() != 0) {
				for (CampoCategoria campoCategoria : campiEreditati) {
					campi.add(campoCategoria);
				}
			}
			
			return campi;
		}
	}
		
	@JsonIgnore
	public List<CampoCategoria> getCampiObbligatori() {
		List<CampoCategoria> campiObbligatori = new ArrayList<>();
		
		for (CampoCategoria campo : this.getCampiNativiEreditati()) {
			if(campo.isObbligatorio()) {
				campiObbligatori.add(campo);
			}
		}
		
		return campiObbligatori;
	}
	
	@JsonIgnore
	public List<CampoCategoria> getCampiFacoltativi() {
		List<CampoCategoria> campiFacoltativi = new ArrayList<>();
		
		for (CampoCategoria campo : this.getCampiNativiEreditati()) {
			if(!campo.isObbligatorio()) {
				campiFacoltativi.add(campo);
			}
		}
		
		return campiFacoltativi;
	}
	
	public int numeriDiSottocategorie() {
		return this.sottoCategorie.size();
	}
	
	/**
	 * Precondizione: sottoCategoria != null
	 * Postcondizione: sottoCategorie'.size() = sottoCategorie.size() + 1 
	 * 				   elemento aggiunto alla lista
	 * 
	 * @param sottoCategoria
	 */
	public void addSottoCategoria(Categoria sottoCategoria) {
		sottoCategorie.add(sottoCategoria);
	}
	
	/**
	 * Precondizione: sottoCategoria != null
	 * Postcondizione: sottoCategorie'.size() = sottoCategorie.size() - 1 
	 * 				   elemento rimosso dalla lista
	 * 
	 * @param sottoCategoria
	 */
	public void eliminaSottoCategoria(Categoria sottoCategoria) {
		this.sottoCategorie.remove(sottoCategoria);
	}
	
	public boolean sonoFoglia() {
		return sottoCategorie.size() == 0 ? true : false;
	}
	
	/**
	 * Una categoria non e' piu' valida quando viene eliminata, vengono quindi eliminate tutte le sottocategorie non valide
	 * 
	 */
	public void eliminaCategorieNonValide() {
		ArrayList<Categoria> categorieNonValide = new ArrayList<Categoria>();
		
		for (Categoria sotto_categoria : this.sottoCategorie) {
			if(!sotto_categoria.isValida()) {
				categorieNonValide.add(sotto_categoria);
			} else {
				sotto_categoria.eliminaCategorieNonValide();
			}
		}
		
		for (Categoria categoria : categorieNonValide) {
			if(this.getSottoCategorie().contains(categoria)) {
				eliminaSottoCategoria(categoria);
			}
		}
	}
	
	/**
	 * 
	 * @param campi
	 * @param descrizione
	 * @return TRUE se descrizione non e' gia' presente in campi FALSE altrimenti
	 */
	public static boolean checkUnicitaCampo(List<CampoCategoria> campi, String descrizione) {
		if(campi == null)
			return true;
		
		for (CampoCategoria campo : campi) {
			if(campo.getDescrizione().equalsIgnoreCase(descrizione)) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Precondizione: categoria != null
	 * 
	 * Metodo per sapere quando due categorie sono uguali
	 * @param categoria
	 * @return
	 */
	public boolean equals(Categoria categoria) {
		return this.nome.equals(categoria.getNome()); 
	}
}
