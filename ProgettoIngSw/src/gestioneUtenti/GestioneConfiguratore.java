package gestioneUtenti;

public class GestioneConfiguratore extends GestioneUtente{	
		
	public GestioneConfiguratore() {
		super();
	}
	
	public void creaGerarchia() {
		boolean isGerarchiaCreata = getGestoreGerarchie().creaGerarchia();
        
        if(isGerarchiaCreata)
        	getGestoreGerarchie().salvaGerarchie();
	}
}