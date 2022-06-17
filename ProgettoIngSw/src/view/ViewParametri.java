package view;

import java.io.IOException;

import controller.GestioneParametri;

public abstract class ViewParametri {
	private static final String MSG_ERROR_INIT_PARAMETRI = "*** Errore inizializzazione Parametri ***";
	
	//costanti per menu
	protected static final String TXT_TITOLO = "Gestione Piazza";
	protected static final String TXT_TITOLO_SOTTOMENU = "-----------";
	protected static final String TXT_ERRORE = "ERRORE";
	protected static final String MSG_MODIFICA_LUOGHI = "Modifica la lista dei luoghi";
	protected static final String MSG_MODIFICA_GIORNI = "Modifica la lista dei giorni";
	protected static final String MSG_MODIFICA_INTERVALLI = "Modifica la lista degli intervalli";
	protected static final String MSG_MODIFICA_SCADENZA = "Modifica la scadenza";
	
	protected static final String [] TXT_VOCI = {
			MSG_MODIFICA_LUOGHI,
			MSG_MODIFICA_GIORNI,
			MSG_MODIFICA_INTERVALLI,
			MSG_MODIFICA_SCADENZA
	};
	
	protected static final String MSG_AGGIUNGI = "Aggiungi";
	protected static final String MSG_RIMUOVI= "Rimuovi";
	
	protected static final String [] TXT_VOCI_MODIFICA = {
			MSG_AGGIUNGI,
			MSG_RIMUOVI,
	};
	
	private GestioneParametri gestoreParametri;
	
	public ViewParametri() throws IOException {
		try {
			gestoreParametri = new GestioneParametri();
		} catch (IOException e) {
			throw new IOException(MSG_ERROR_INIT_PARAMETRI);
		}
	}
	
	/**
	 * Precondizione: gestoreParametri != null
	 * 
	 * @param gestoreParametri
	 */
	public ViewParametri(GestioneParametri gestoreParametri) {
		this.gestoreParametri = gestoreParametri;
	}
	
	public GestioneParametri getGestoreParametri() {
		return this.gestoreParametri;
	}
	
	public abstract void menu() throws IOException;
	public abstract void aggiungi() throws IOException;
	public abstract void rimuovi() throws IOException;
}
