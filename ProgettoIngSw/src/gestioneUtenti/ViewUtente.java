package gestioneUtenti;

import java.io.IOException;

public abstract class ViewUtente {
	protected static final String TXT_ERROR = "ERRORE";
	protected static final String MSG_VISUALIZZA_GERARCHIE = "Visualizza tutte le gerarchie";
	protected static final String MSG_ASSENZA_GERARCHIE = "NESSUNA GERARCHIA PRESENTE";
	
	public abstract void menu(String username) throws IOException;
}
