package gestioneUtenti;

public abstract class ViewUtente {
	protected static final String TXT_ERRORE = "ERRORE";
	protected static final String MSG_VISUALIZZA_GERARCHIE = "Visualizza tutte le gerarchie";
	
	public abstract void menu(String username);
}
