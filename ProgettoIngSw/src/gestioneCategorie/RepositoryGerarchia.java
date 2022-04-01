package gestioneCategorie;

import java.util.ArrayList;
import java.util.List;

import it.unibs.fp.mylib.InputDati;

public class RepositoryGerarchia {
	
	/**
	 * Richiede il nome della categoria da eliminare e ne gestisce l'eliminazione
	 * @param gerarchia
	 */
//	protected static void checkEliminaSottocategoria(Gerarchia gerarchia) {
//		String nomeDaEliminare = InputDati.leggiStringaNonVuota("Inserisci nome da eliminare");
//		Categoria categoriaDaEliminare;
//
//		if(gerarchia.checkNomeCategoriaEsiste(nomeDaEliminare)) {
//			if(nomeDaEliminare.equalsIgnoreCase(gerarchia.getRoot().getNome())) {
//				System.out.println("Stai provando ad eliminare la radice della gerarchia!");
//			} else {	
//				categoriaDaEliminare = gerarchia._getCategoriaByName(nomeDaEliminare);
//				if(gerarchia.cercaPadre(categoriaDaEliminare).numeriDiSottocategorie() == NUM_MIN_SOTTOCATEGORIE) {
//					System.out.println("\nStai cercando di eliminare una sottocategoria di una categoria che ne ha soltanto " + NUM_MIN_SOTTOCATEGORIE);
//					System.out.println("Procedi quindi a creare prima almeno un' altra sottocategoria");
//				} else {
//					gerarchia.eliminaCategoria(nomeDaEliminare);
//					System.out.println("La categoria: "+nomeDaEliminare+" e' stata eliminata correttamente");
//				}
//			}
//			
//		} else {
//			System.out.println("La categoria cercata non esiste");
//		}
//	}	
		
	protected static void showGerarchia(Gerarchia gerarchia) {
		System.out.println("-----------------------------------");
		System.out.println(gerarchia.getRoot().showCategoriaDettagliata());
	}
}
