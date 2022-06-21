package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Gerarchia {
	
	private Categoria root;
	private Map<String, Categoria> elencoCategorie;
	
	public Gerarchia() {
		elencoCategorie = new HashMap<>();
	}
	
	public Gerarchia(Categoria root) {
		this.root = root;
		elencoCategorie = new HashMap<>();
	}

	@JsonIgnore
	public Map<String, Categoria> getElencoCategorie() {
		return elencoCategorie;
	}

	public void setElencoCategorie(Map<String, Categoria> elencoCategorie) {
		this.elencoCategorie = elencoCategorie;
	}

	public void addCategoriaInElenco(String nome, Categoria categoria) {
		this.elencoCategorie.put(formattaNome(nome), categoria);
	}
	
	public Categoria getRoot() {
		return root;
	}

	public void setRoot(Categoria root) {
		this.root = root;
	}
	
	/**
	 * Precondizione: categoria != null
	 * Postcondizione: padre != null se categoria è sottoCategoria di qualcuno
	 * 
	 * @param categoria
	 * @return padre della categoria passata come parametro, potrebbe essere NULL
	 */
	public Categoria cercaPadre(Categoria categoria) {
		Categoria padre = null;
		
		for (Categoria c : this.elencoCategorie.values()) {
			if(c.getSottoCategorie().contains(categoria)) {
				padre = c;
			}
		}
		
		return padre;
	}
	
	/**
	 * 
	 * @param nomeCategoria
	 * @return True se nomeCategoria è effettivamente il nome di una categoria esistente
	 */
	public boolean checkNomeCategoriaEsiste(String nomeCategoria) {
		return elencoCategorie.containsKey(formattaNome(nomeCategoria));
	}
	
	@JsonIgnore
	public Categoria getCategoriaByName(String nome) {
		return elencoCategorie.get(formattaNome(nome));
	}
	
	@JsonIgnore
	protected String getNomeGerarchia() {
		return root.getNome();
	}
	
	@JsonIgnore
	public String getNomeFormattato() {
		return formattaNome(this.getNomeGerarchia());
	}
	
	/**
	 * Postcondizione: se nomeDaEliminare è il nome di una categoria => elencoCategorie'.size() = elencoCategorie.size() - 1
	 * 
	 * Ricorsivamente vengono invalidate anche tutte le sottocategorie della categoria da eliminare
	 * Viene aggiornata la lista delle categorie
	 * 
	 * @param nomeDaEliminare della categoria
	 */
	public void eliminaCategoria(String nomeDaEliminare) {
		for (Categoria c : this.getCategoriaByName(nomeDaEliminare).getSottoCategorie()) {
			eliminaCategoria(c.getNome());
		}
		this.getCategoriaByName(nomeDaEliminare).setValida(false);
		this.elencoCategorie.remove(formattaNome(nomeDaEliminare));
		
		this.getRoot().eliminaCategorieNonValide();
	}

	/**
	 * Postcondizione: Se Gerarchia contiene Categorie foglie => listaFoglie'.size() > 0
	 * @return
	 */
	@JsonIgnore
	public List<Categoria> getListaFoglie() {
		List<Categoria> listaFoglie = new ArrayList<Categoria>();
		
		for (Categoria categoria : elencoCategorie.values()) {
			if(categoria.getSottoCategorie() == null || categoria.getSottoCategorie().size() == 0)
				listaFoglie.add(categoria);
		}
		return listaFoglie;
	}
	
	/**
	 * Precondizione: categoriaPassata != null
	 * Postcondizione: elencoCategorie'.size() = elencoCategorie.size() + 1  
	 * 
	 * In questo modo si aggiorna l'elenco di Categorie con tutte le categorie effettivamente presenti 
	 * nella Gerarchia
	 * @param categoriaPassata
	 */
	public void popolaElencoCategorie(Categoria categoriaPassata){
		addCategoriaInElenco(categoriaPassata.getNome(), categoriaPassata);
		for (Categoria categoria : categoriaPassata.getSottoCategorie()) {
			popolaElencoCategorie(categoria);
		}
		
	}

	public static String formattaNome(String nome) {
		return nome.toUpperCase();
	}
}
