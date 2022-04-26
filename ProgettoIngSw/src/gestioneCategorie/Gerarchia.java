package gestioneCategorie;

import java.util.HashMap;

public class Gerarchia {
	
	private Categoria root;
	private HashMap<String, Categoria> elencoCategorie;
	
	public Gerarchia() {
		elencoCategorie = new HashMap<>();
	}
	
	public Gerarchia(Categoria root) {
		this.root = root;
		elencoCategorie = new HashMap<>();
	}

	public HashMap<String, Categoria> _getElencoCategorie() {
		return elencoCategorie;
	}

	public void setElencoCategorie(HashMap<String, Categoria> elencoCategorie) {
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
	 * 
	 * @param categoria
	 * @return padre della categoria passata come parametro, potrebbe essere NULL
	 */
	protected Categoria cercaPadre(Categoria categoria) {
		Categoria padre = null;
		
		for (Categoria c : this.elencoCategorie.values()) {
			if(c.getSottoCategorie().contains(categoria)) {
				padre = c;
			}
		}
		
		return padre;
	}
	
	protected boolean checkNomeCategoriaEsiste(String nomeCategoria) {
		return elencoCategorie.containsKey(formattaNome(nomeCategoria));
	}
	
	protected Categoria _getCategoriaByName(String nome) {
		return elencoCategorie.get(formattaNome(nome));
	}
	
	protected String _getNomeGerarchia() {
		return root.getNome();
	}
	
	protected String _getNomeFormattato() {
		return formattaNome(this._getNomeGerarchia());
	}
	
	/**
	 * Ricorsivamente vengono invalidate anche tutte le sottocategorie della categoria da eliminare
	 * Viene aggiornata la lista delle categorie
	 * 
	 * @param nomeDaEliminare della categoria
	 */
	protected void eliminaCategoria(String nomeDaEliminare) {
		for (Categoria c : this._getCategoriaByName(nomeDaEliminare).getSottoCategorie()) {
			eliminaCategoria(c.getNome());
		}
		this._getCategoriaByName(nomeDaEliminare).setValida(false);
		this.elencoCategorie.remove(formattaNome(nomeDaEliminare));
		
		this.getRoot().eliminaCategorieNonValide();
	}


	private String formattaNome(String nome) {
		return nome.toUpperCase();
	}
	
	public String showGerarchiaSintetica() {
		return "Nome: " + root.getNome() + " Descrizione: " + root.getDescrizione() + "\n";
	}
	
	@Override
	public String toString() {
		return "Gerarchia con Radice:  " + root.getNome() + "\n"
				+ root.showCategoriaDettagliata();
	}
}
