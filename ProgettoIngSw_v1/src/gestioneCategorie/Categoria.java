package gestioneCategorie;

import java.util.ArrayList;
import java.util.List;

import it.unibs.fp.mylib.BelleStringhe;

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
		sottoCategorie = new ArrayList<>();
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
	 * 
	 * @return lista completa contenente sia campi nativi che ereditati, vuota se non presenti (e non null)
	 */
	public List<CampoCategoria> _getCampiNativiEreditati() {
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
	
	
	public int numeriDiSottocategorie() {
		return this.sottoCategorie.size();
	}
	
	public void addSottoCategoria(Categoria sottoCategoria) {
		sottoCategorie.add(sottoCategoria);
	}
	
	private void eliminaSottoCategoria(Categoria categoria) {
		this.sottoCategorie.remove(categoria);
	}
	
	public boolean sonoFoglia() {
		return sottoCategorie.size() == 0 ? true : false;
	}
	
	/**
	 * Una categoria non e' piu' valida quando viene eliminata, vengono quindi eliminate tutte le sottocategorie non valide
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
	 * @param nTab = utilizzato per la visualizzazione, in base alla profondita', della categoria
	 * @return
	 */
	private String showCampi(String nTab) {
		List<CampoCategoria> campi = _getCampiNativiEreditati();
		StringBuffer result = new StringBuffer();
		result.append(nTab + "CAMPI: \n");
		
		if(campi.size() == 0) {
			result.append(nTab + "\t" + "--- nessun Campo presente ---\n");
		} else {
			for (CampoCategoria campo_Categoria : campi) {			
				result.append(nTab + "\t" + campo_Categoria.toString() + "\n");
			}
		}

		result.append(nTab + "---- Fine Campi di " + nome + " ----\n\n");
		return result.toString();
	}
	
	/**
	 * 
	 * @return la stampa della categoria con tutti i suoi attributi che la caratterizzano come tale
	 */
	public String showCategoriaDettagliata() {
		StringBuffer result = new StringBuffer();
		String nTab = BelleStringhe.ripetiChar('\t', this.profondita);
		
		result.append(nTab+"->Nome: "+nome+"  |  Descrizione: "+descrizione+"\n");
		result.append(showCampi(nTab));
		
		for (Categoria sotto_categoria : this.sottoCategorie) {
			result.append(nTab+"\t" + sotto_categoria.showCategoriaDettagliata()+"\n");
		}
		
		return result.toString();
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		String nTab = BelleStringhe.ripetiChar('\t',  getProfondita());
		result.append(nTab+"->"+nome+"\n");
		for (Categoria sotto_categoria : this.sottoCategorie) {
			result.append(nTab+"\t" + sotto_categoria.toString()+"\n");
		}
		return result.toString();
	}
}
