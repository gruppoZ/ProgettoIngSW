package application;

import java.util.List;

public class Configuratore extends Utente {

	private Gerarchia currentGerarchia;
	
	public Configuratore() {
	}
	
	/**
	 * @param credenziali
	 */
	public Configuratore(Credenziali credenziali) {
		super(credenziali);
	}
	
	/**
	 * Precondizione: root != null
	 * Postcondizione: currentGerarchia != null
	 * @param root
	 */
	public void creaRoot(Categoria root) {
		root.setProfondita(0);
		currentGerarchia = new Gerarchia(root);
		
		currentGerarchia.addCategoriaInElenco(root.getNome(), root);	
	}
	
	public Gerarchia getGerarchiaInLavorazione() throws NullPointerException{
		if (currentGerarchia != null)
			return currentGerarchia;
		throw new NullPointerException();
	}	
	
	/**
	 * Precondizione: categotiaPadre != null, categoriaFiglia != null
	 * Postcondizione: categoriaPadre'.getSottoCategorie().size() = categoriaPadre.getSottoCategorie().size() + 1
	 * 					currentGerarchia'.getElencoCategorie().size() = currentGerarchia.getElencoCategorie().size() + 1
	 * @param categoriaPadre
	 * @param categoriaFiglia
	 */
	public void creaSottoCategoria(Categoria categoriaPadre, Categoria categoriaFiglia) {
		int depth = categoriaPadre.getProfondita() + 1;

		categoriaFiglia.setProfondita(depth);
		this.getGerarchiaInLavorazione().addCategoriaInElenco(categoriaFiglia.getNome(),categoriaFiglia);
	}
	
	public void addCampiDefaultRoot(List<CampoCategoria> campiNativi) {
		campiNativi.add(new CampoCategoria("Stato di conservazione", true));
		campiNativi.add(new CampoCategoria("Descrizione libera", false));
	}
	
	public boolean checkNomeCategoriaEsiste(String nomeCategoria) {
		return this.getGerarchiaInLavorazione().checkNomeCategoriaEsiste(nomeCategoria);
	}
	
	public Categoria getCategoriaByName(String nome) {
		return this.getGerarchiaInLavorazione().getCategoriaByName(nome);
	}
		
	public void eliminaCategoria(String nome) {
		this.getGerarchiaInLavorazione().eliminaCategoria(nome);
	}
	
	public boolean checkNomeIsNomeRoot(String nomeDaEliminare) {
		return this.getGerarchiaInLavorazione().getRoot().getNome().equalsIgnoreCase(nomeDaEliminare);
	}
}
