package controller;

import java.io.IOException;
import java.util.*;

import application.CampoCategoria;
import application.Categoria;
import application.Configuratore;
import application.Gerarchia;

public class GerarchieFacade {	
	private Configuratore configuratore;
	
	public GerarchieFacade() throws IOException {
		configuratore = new Configuratore();
	}
	
	/**
	 * 
	 * @param campi
	 * @param descrizione
	 * @return TRUE se descrizione non e' gia' presente in campi FALSE altrimenti
	 */
	public boolean checkUnicitaCampo(List<CampoCategoria> campi, String descrizione) {
		return Categoria.checkUnicitaCampo(campi, descrizione);
	}	
	
	public Gerarchia getGerarchiaInLavorazione() {
		return configuratore.getGerarchiaInLavorazione();
	}
	
	public void eliminaCategoria(String nome) {
		configuratore.eliminaCategoria(nome);
	}
	
	public boolean checkNomeIsNomeRoot(String nomeDaEliminare) {
		return configuratore.checkNomeIsNomeRoot(nomeDaEliminare);
	}
	
	public boolean checkNomeCategoriaEsiste(String nomeCategoria) {
		return configuratore.checkNomeCategoriaEsiste(nomeCategoria);
	}
	
	public Categoria getCategoriaByName(String nome) {
		return configuratore.getCategoriaByName(nome);
	}
	
	public void creaSottoCategoria(Categoria categoriaPadre, Categoria categoriaFiglia) {
		configuratore.creaSottoCategoria(categoriaPadre, categoriaFiglia);
	}
	
	public void creaRoot(Categoria root) {
		configuratore.creaRoot(root);
	}
	
	public void addCampiDefaultRoot(List<CampoCategoria> campiNativi) {
		configuratore.addCampiDefaultRoot(campiNativi);
	}
}
