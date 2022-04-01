package gestioneCategorie;

import java.util.ArrayList;
import java.util.List;

import it.unibs.fp.mylib.InputDati;

public class RepositoryGerarchia {
			
	protected static void showGerarchia(Gerarchia gerarchia) {
		System.out.println("-----------------------------------");
		System.out.println(gerarchia.getRoot().showCategoriaDettagliata());
	}
}
