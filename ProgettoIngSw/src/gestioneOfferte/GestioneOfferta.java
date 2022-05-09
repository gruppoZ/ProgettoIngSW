package gestioneOfferte;

import java.util.*;


import gestioneCategorie.Categoria;
import main.JsonIO;

public class GestioneOfferta {

	private static final String PATH_OFFERTE = "src/gestioneOfferte/offerte.json";
	private static final String PATH_STORICO_CAMBIO_STATI = "src/gestioneOfferte/storico_cambio_stati.json";
	
	private List<Offerta> listaOfferte; 
	
	HashMap<Integer, ArrayList<PassaggioTraStati>> storicoMovimentazioni;
	
	public GestioneOfferta() {
		listaOfferte = (ArrayList<Offerta>) leggiListaOfferte(); 
		this.storicoMovimentazioni = leggiStoricoCambioStati();
	}
	
	protected boolean isOffertaAperta(Offerta offerta) {
		return offerta.getTipoOfferta().getStato().equalsIgnoreCase(StatiOfferta.OFFERTA_APERTA.getNome());
	}

	/**
	 * Permette di cambiare lo stato di un offerta, dopodichè salva il passaggio di stato nello storico
	 * @param offerta
	 */
	protected void gestisciCambiamentoStatoOfferta(Offerta offerta) {
		int id = offerta.getId();
		StatoOfferta oldState, newState;
		oldState = offerta.getTipoOfferta();
		
		offerta.getTipoOfferta().changeState(offerta);
		
		newState = offerta.getTipoOfferta();
		
		PassaggioTraStati cambio = new PassaggioTraStati(oldState, newState);
		
		if(storicoMovimentazioni.containsKey(id)) {
			storicoMovimentazioni.get(id).add(cambio);
		} else {
			ArrayList<PassaggioTraStati> passaggi = new ArrayList<PassaggioTraStati>();
			passaggi.add(cambio);
			storicoMovimentazioni.put(id, passaggi);
		}
		
		
		salvaStoricoCambioStati();
		salvaOfferte();
	}
	
	public List<Offerta> getListaOfferte() {
		return listaOfferte;
	}
	
	protected List<Offerta> leggiListaOfferte() {
		List<Offerta> listaLetta = (ArrayList<Offerta>) JsonIO.leggiListaDaJson(PATH_OFFERTE, Offerta.class);
		
		return listaLetta;
	}
	
	protected Offerta getOffertaById(int id, List<Offerta> listaOfferte) {
		for (Offerta offerta : listaOfferte) {
			if(offerta.getId() == id) return offerta;
		}
		return null; //TODO: meglio return null o throw null pointer?
	}
	
	protected List<Offerta> getOfferteByUtente(String username) {
		List<Offerta> result = new ArrayList<>();
		for (Offerta offerta : listaOfferte) {
			if(offerta.getUsername().equalsIgnoreCase(username))
				result.add(offerta);
		}
		
		return result;
	}
	
	public List<Offerta> getOfferteAperteByUtente(String username) {
		List<Offerta> result = new ArrayList<>();
		for (Offerta offerta : listaOfferte) {
			if(offerta.getUsername().equalsIgnoreCase(username) && isOffertaAperta(offerta))
				result.add(offerta);
		}
		
		return result;
	}
	
	public List<Offerta> getOfferteAperteByCategoria(Categoria foglia) {
		List<Offerta> result = new ArrayList<>();
		for (Offerta offerta : listaOfferte) {
			if(isOffertaAperta(offerta)) {
				if(offerta.getArticolo().getFoglia().equals(foglia))
					result.add(offerta);		
			}
		}
		
		return result;
	}
	
	public List<Offerta> getOfferteAperteByCategoriaNonDiPoprietaDiUsername(Categoria foglia, String username) {
		List<Offerta> result = new ArrayList<>();
		for (Offerta offerta : listaOfferte) {
			if(isOffertaAperta(offerta) && !offerta.getUsername().equals(username)) {
				if(offerta.getArticolo().getFoglia().equals(foglia))
					result.add(offerta);		
			}
		}
		
		return result;
	}
	
	protected HashMap<Integer, ArrayList<PassaggioTraStati>> leggiStoricoCambioStati() {
		return JsonIO.leggiStoricoCambioStatiOffertaDaJson(PATH_STORICO_CAMBIO_STATI);
	}
	
	protected void salvaOfferte() {
		JsonIO.salvaOggettoSuJson(PATH_OFFERTE, listaOfferte);
	}
	
	protected void salvaStoricoCambioStati() {
		JsonIO.salvaOggettoSuJson(PATH_STORICO_CAMBIO_STATI, storicoMovimentazioni);
	}
	
	protected int numeroOfferteAperte() {
		int n = 0;
		
		for (Offerta offerta : this.listaOfferte) {
			if(isOffertaAperta(offerta)) n++;
		}
		
		return n;
	}
	
	protected boolean ciSonoOfferteAperte() {
		return numeroOfferteAperte() > 0;
	}
	
	protected int getIdMax() {
		int idMax = 0;
		for (Offerta offerta : listaOfferte) {
			if(offerta.getId() > idMax) idMax = offerta.getId();
		}
		return idMax;
	}
	
	protected void manageAggiuntaOfferta(Articolo articolo, String username) {
		int id = getIdMax() + 1;
		Offerta offerta = creaOfferta(id, articolo, username);
		aggiungiOfferta(offerta);
		salvaOfferte();
	}
	
	private Offerta creaOfferta(int id, Articolo articolo, String username) {
		return new Offerta(id, articolo, username, new OffertaAperta());
	}
	
	protected void aggiungiOfferta(Offerta offerta) {
		this.listaOfferte.add(offerta);
	}
	
	protected void rimuoviOfferta(Offerta offerta) {
		this.listaOfferte.remove(offerta);
	}
}
