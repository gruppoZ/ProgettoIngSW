package gestioneUtenti;

import gestioneCategorie.GestioneGerarchie;
import gestioneParametri.ViewParametri;
import gestioneParametri.ViewParametroPiazza;

public class GestioneConfiguratore{	
	private GestioneGerarchie managerGerarchie;
		
	public void init() {
		managerGerarchie = new GestioneGerarchie();
		
		managerGerarchie.leggiDaFileGerarchie();
	}
	
	public void creaGerarchia() {
		boolean isGerarchiaCreata = managerGerarchie.creaGerarchia();
        
        if(isGerarchiaCreata)
        	managerGerarchie.salvaGerarchie();
	}
	
	public void mostraGerarchie() {
		System.out.println(managerGerarchie.toString());
	}
	
	public void gestisciPiazza() {
		ViewParametri viewPiazza = new ViewParametroPiazza();
		viewPiazza.menu();
	}
}