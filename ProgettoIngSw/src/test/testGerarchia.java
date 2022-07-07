package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import application.CampoCategoria;
import application.Categoria;
import application.Configuratore;

class testGerarchia {

	Configuratore configuratore = new Configuratore();
	
	@Test
	void checkCreazioneRoot() {
		CampoCategoria campo = new CampoCategoria("Stato", false);
		List<CampoCategoria> listaCampi = new ArrayList<CampoCategoria>();
		listaCampi.add(campo);
		Categoria root = new Categoria("root", "sono root", true, listaCampi, null);
		
		assertThrows(NullPointerException.class, () -> configuratore.getGerarchiaInLavorazione());
		configuratore.creaRoot(root);
		assertTrue(configuratore.getGerarchiaInLavorazione() != null);
	}
	
	@Test
	void checkAggiuntaSottoCategoriaInHashMap() {
		CampoCategoria campo = new CampoCategoria("Stato", false);
		List<CampoCategoria> listaCampi = new ArrayList<CampoCategoria>();
		listaCampi.add(campo);
		Categoria root = new Categoria("root", "sono root", true, listaCampi, null);
		configuratore.creaRoot(root);
		Categoria sottoCategoria = new Categoria("foglia", "sono foglia", false, listaCampi, null);

		int sottoCategorieRootSize = configuratore.getGerarchiaInLavorazione().getElencoCategorie().size();
		int depthRoot = root.getProfondita();
		
		configuratore.creaSottoCategoria(root, sottoCategoria);
		assertTrue(configuratore.getGerarchiaInLavorazione().getElencoCategorie().size() == sottoCategorieRootSize + 1);
		assertTrue(sottoCategoria.getProfondita() == depthRoot + 1);
	}

}
