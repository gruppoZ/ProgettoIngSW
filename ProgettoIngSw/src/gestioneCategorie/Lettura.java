//package gestioneCategorie;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Test;
//
//class Lettura {
//
//	@Test
//	void testHasMap() {
//		ManagerGerarchie manager = new ManagerGerarchie();
//		manager.leggiDaFileGerarchie();
//		
//		Gerarchia gerarchia = manager.getGerarchie().get("ROOT");
//		
//		gerarchia.checkNomeCategoriaEsiste("fnroot");
//	}
//	@Test
//	void testSoloRoot() {
//		ManagerGerarchie manager = new ManagerGerarchie();
//		manager.leggiDaFileGerarchie();
//		
//		Gerarchia gerarchia = manager.getGerarchie().get("ROOT");
//		
//		gerarchia.checkNomeCategoria1(gerarchia.getRoot(), "fnroot");
//	}
//
//}
