package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import application.CampoCategoria;
import application.Categoria;

class testCategoria {

	@Test
	void checkAddSottoCategoria() {
		CampoCategoria campo = new CampoCategoria("Stato", false);
		List<CampoCategoria> listaCampi = new ArrayList<CampoCategoria>();
		listaCampi.add(campo);
		Categoria root = new Categoria("root", "sono root", true, listaCampi, null);
		Categoria sottoCategoria = new Categoria("foglia", "sono foglia", false, listaCampi, null);

		int nFigliRootIniziale = root.numeriDiSottocategorie();
		root.addSottoCategoria(sottoCategoria);
		int nFigliRoot = root.numeriDiSottocategorie();
		assertTrue(nFigliRoot == nFigliRootIniziale + 1);
	}
	
	@Test
	void checkRemoveSottoCategoria() {
		CampoCategoria campo = new CampoCategoria("Stato", false);
		List<CampoCategoria> listaCampi = new ArrayList<CampoCategoria>();
		listaCampi.add(campo);
		Categoria root = new Categoria("root", "sono root", true, listaCampi, null);
		Categoria sottoCategoria = new Categoria("foglia", "sono foglia", false, listaCampi, null);
		
		root.addSottoCategoria(sottoCategoria);
		int nFigliRoot = root.numeriDiSottocategorie();
		root.eliminaSottoCategoria(sottoCategoria);
		assertTrue(root.numeriDiSottocategorie() == nFigliRoot - 1);
	}
	
	@Test
	void checkEliminazioneCategorieNonValide() {
		CampoCategoria campo = new CampoCategoria("Stato", false);
		List<CampoCategoria> listaCampi = new ArrayList<CampoCategoria>();
		listaCampi.add(campo);
		Categoria root = new Categoria("root", "sono root", true, listaCampi, null);
		Categoria sottoCategoria = new Categoria("sottoCat", "sotto categoia", false, listaCampi, null);
		Categoria sottoCategoria2 = new Categoria("foglia1", "sono foglia 1", false, listaCampi, null);
		Categoria sottoCategoria3 = new Categoria("foglia2", "sono foglia 2", false, listaCampi, null);
		
		sottoCategoria.addSottoCategoria(sottoCategoria2);
		sottoCategoria.addSottoCategoria(sottoCategoria3);
		root.addSottoCategoria(sottoCategoria);
		
		assertTrue(root.getSottoCategorie().size() == 1);
		assertTrue(sottoCategoria.getSottoCategorie().size() == 2);
		
		sottoCategoria.setValida(false);
		root.eliminaCategorieNonValide();
		assertTrue(root.getSottoCategorie().size() == 0);
	}
	
	@Test
	void checkUnicitaCampo() {
		CampoCategoria campo = new CampoCategoria("Stato", false);
		List<CampoCategoria> listaCampi = new ArrayList<CampoCategoria>();
		listaCampi.add(campo);
		assertFalse(Categoria.checkUnicitaCampo(listaCampi, "Stato"));
		assertTrue(Categoria.checkUnicitaCampo(listaCampi, "Prova"));
		
	}

}
