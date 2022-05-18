package gestioneOfferte;

import java.util.List;

import gestioneCategorie.CampoCategoria;
import gestioneCategorie.Categoria;
import gestioneCategorie.ViewGerarchia;
import gestioneUtenti.GestioneFruitore;
import it.unibs.fp.mylib.InputDati;

public class ViewArticolo {
	
	private static final String MSG_CAMPI_NON_COMPILATI_ARTICOLO = "Nessun Campo compilato per questo articolo!";
	private static final String MSG_PUBBLICAZIONE_ACCETTATA = "\nLa pubblicazione e' stata accettata";
	
	private static final String MSG_ASK_COMPILAZIONE_CAMPO = "Vuoi compilare il campo? ";
	private static final String MSG_ASK_VALORE_CAMPO = "Inserire il valore del campo: ";
	
	private static final String MSG_WARNING_CAMPO_COMPILAZIONE_OBBLIGATORIA = "Se non si compila il campo la pubblicazione dell'articolo verra' annullata";
	private static final String MSG_ERROR_COMPILAZIONE_CAMPO = "\nLa compilazione dell'articolo non e' andata a buon fine!\n";
	
	private GestioneFruitore gestoreFruitore;
	private GestioneArticolo gestoreArticolo;
	private GestioneOfferta gestoreOfferte;
	
	public ViewArticolo() {
	}
	
	/**
	 * Precondizione: gestoreFruitore != null, gestoreOfferte != null
	 * Postcondizione: this.gestoreFruitore != null, this.gestoreOfferte != null, gestoreArticolo != null
	 * 
	 * @param gestoreFruitore
	 * @param gestoreOfferte
	 */
	public ViewArticolo(GestioneFruitore gestoreFruitore, GestioneOfferta gestoreOfferte) {
		this.gestoreFruitore = gestoreFruitore;
		this.gestoreOfferte = gestoreOfferte;
		gestoreArticolo = new GestioneArticolo();
	}
	
	public void aggiungiArticolo() {
		ViewGerarchia viewGerarchia = new ViewGerarchia();
		Categoria foglia = viewGerarchia.scegliFoglia();
		
		if(foglia != null){
			gestoreArticolo.addFoglia(foglia);
			
			try {
				inserisciValoriCampi();
				System.out.println(MSG_PUBBLICAZIONE_ACCETTATA);
				gestoreOfferte.manageAggiuntaOfferta(gestoreArticolo.getArticolo(), gestoreFruitore.getUsername());
			} catch(RuntimeException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	private boolean compilaCampiObbligatori() {
		List<CampoCategoria> campiObbligatori = gestoreArticolo.getListaCampiObbligatori();
		
		for (CampoCategoria campo : campiObbligatori) {
			System.out.println(campo.getDescrizione());
			System.out.println(MSG_WARNING_CAMPO_COMPILAZIONE_OBBLIGATORIA);
			
			if(InputDati.yesOrNo(MSG_ASK_COMPILAZIONE_CAMPO)) {
				String valoreCampo = InputDati.leggiStringaNonVuota(MSG_ASK_VALORE_CAMPO);
				
				gestoreArticolo.addValoreCampo(campo, valoreCampo);
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	private void compilaCampiFacoltativi() {
		List<CampoCategoria> campiFacoltativi = gestoreArticolo.getListaCampiFacoltativi();
		
		for (CampoCategoria campo : campiFacoltativi) {
			System.out.println(campo.getDescrizione());
			if(InputDati.yesOrNo(MSG_ASK_COMPILAZIONE_CAMPO)) {
				String valoreCampo = InputDati.leggiStringaNonVuota(MSG_ASK_VALORE_CAMPO);
				
				gestoreArticolo.addValoreCampo(campo, valoreCampo);
			}
		}
	}
	
	private void inserisciValoriCampi() throws RuntimeException {		
		if(compilaCampiObbligatori()) {
			compilaCampiFacoltativi();
		}  else {
			throw new RuntimeException(MSG_ERROR_COMPILAZIONE_CAMPO);
		}
	}
	
	/**
	 * Precondizione: articolo != null
	 * 
	 * @param articolo
	 */
	protected void showArticolo(Articolo articolo) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("Articolo appartenente alla categoria: " + articolo.getFoglia().getNome() + "\n");
		
		if(articolo.getValoreCampi().size() == 0)
			sb.append(MSG_CAMPI_NON_COMPILATI_ARTICOLO);
		else {
			sb.append("\tCampi:");
			articolo.getValoreCampi().forEach( (k,v) -> sb.append("\n\t\t" + k + ": " + v));
		}
		
		System.out.println(sb.toString());
	}
}
