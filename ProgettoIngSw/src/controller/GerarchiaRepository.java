package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import application.CampoCategoria;
import application.Categoria;
import application.Gerarchia;

public interface GerarchiaRepository extends Repository{
	
	/**
	 * Precondizione: root != null
	 * Postcondizione: currentGerarchia != null
	 * @param root
	 */
	public void creaRoot(Categoria root);
	
	/**
	 * Precondizione: categotiaPadre != null, categoriaFiglia != null
	 * Postcondizione: categoriaPadre'.getSottoCategorie().size() = categoriaPadre.getSottoCategorie().size() + 1
	 * 					currentGerarchia'.getElencoCategorie().size() = currentGerarchia.getElencoCategorie().size() + 1
	 * @param categoriaPadre
	 * @param categoriaFiglia
	 */
	public void creaSottoCategoria(Categoria categoriaPadre, Categoria categoriaFiglia);
	public void addCampiDefaultRoot(List<CampoCategoria> campiNativi);
	public void fineCreazioneGerarchia() throws IOException;
	public boolean checkNomeCategoriaEsiste(String nomeCategoria);
	public Categoria getCategoriaByName(String nome);
	public void eliminaCategoria(String nome);
	public boolean checkNomeIsNomeRoot(String nomeDaEliminare);
	
	public Map<?, ?> getItems() throws IOException;
	public Gerarchia getCurrentItem(); //currentItem item che si sta creando
	public Object getItemByName(String nome) throws IOException;
	public boolean checkItemPresente(String nome) throws IOException;
}
