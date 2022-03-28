package gestioneUtenti;

import java.util.HashMap;

import gestioneCategorie.Gerarchia;
import gestioneParametri.Piazza;
import main.JsonIO;

public class GestioneFruitore {
	
	//TODO: creare un interface che JsonIO implementera' dei metodi tipo: salva/leggi
	//questo ci permette di staccare la logica della lettura/scrittura su json che per ora e' presente
	//nelle varie classi
	private static final String PATH_PIAZZA = "src/gestioneParametri/parametri.json";
	private static final String PATH_GERARCHIE = "src/gestioneCategorie/gerarchie.json";
				
	protected boolean isGerarchiePresenti() {
		HashMap<String, Gerarchia> gerarchie = JsonIO.leggiGerarchieDaJson(PATH_GERARCHIE);
		
		if(gerarchie.size() == 0)
			return false;
		else
			return true;
	}

	//TODO simile a quello di Configuratore...generalizzarlo?
	public void mostraGerarchie() {
		HashMap<String, Gerarchia> gerarchie = JsonIO.leggiGerarchieDaJson(PATH_GERARCHIE);
		//questo for può andare in un metodo di manager
		for (Gerarchia gerarchia : gerarchie.values()) {
			System.out.println(gerarchia.showGerarchia());
		}
	}
	
	public void mostraPiazza() {
		Piazza piazza = (Piazza) JsonIO.leggiOggettoDaJson(PATH_PIAZZA, Piazza.class);
		if(piazza.getCitta() == null) 
			System.out.println("Non esiste ancora una piazza disponibile");
		else
			System.out.println(piazza);
	}
}
