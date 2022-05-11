package gestioneOfferte;

import java.time.LocalDate;
import java.util.*;


import gestioneCategorie.Categoria;
import gestioneScambioArticoli.Baratto;
import gestioneScambioArticoli.GestioneBaratto;
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
	
	private boolean isOffertaSelezionata(Offerta offerta) {
		return offerta.getTipoOfferta().getStato().equalsIgnoreCase(StatiOfferta.OFFERTA_SELEZIONATA.getNome());
	}
	
	/**
	 * Permette di cambiare lo stato di un offerta, dopodich� salva il passaggio di stato nello storico
	 * @param offerta
	 */
	public void gestisciCambiamentoStatoOfferta(Offerta offerta, StatoOfferta stato) {
		int id = offerta.getId();
		StatoOfferta oldState, newState;
		oldState = offerta.getTipoOfferta();
		
		offerta.getTipoOfferta().changeState(offerta, stato);
		
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
	
	public void setListaOfferte(List<Offerta> listaOfferte) {
		this.listaOfferte = listaOfferte;
	}

	protected List<Offerta> leggiListaOfferte() {
		List<Offerta> listaOfferte = (ArrayList<Offerta>) JsonIO.leggiListaDaJson(PATH_OFFERTE, Offerta.class);
		setListaOfferte(aggiornaListaOfferte(new GestioneBaratto(), listaOfferte));
		return listaOfferte;
	}
	
	private List<Offerta> aggiornaListaOfferte(GestioneBaratto gestoreBaratto, List<Offerta> listaOfferte){
		List<Offerta> listaOfferteAggiornata= new ArrayList<Offerta>();
		
		for (Offerta offerta : listaOfferte) {
			if(gestoreBaratto.isOffertaInBaratto(offerta)) {
				Baratto baratto = gestoreBaratto.getBarattoByOfferta(offerta);
				if(baratto.getScadenza().isAfter(LocalDate.now())) listaOfferteAggiornata.add(offerta);
				//TODO: else "faccio scadere" l'offerta (la metto in aperta)
			} else {
				listaOfferteAggiornata.add(offerta);
			}
		}
		return listaOfferteAggiornata;
		
	}
	
	protected Offerta getOffertaById(int id, List<Offerta> listaOfferte) throws NullPointerException {
		for (Offerta offerta : listaOfferte) {
			if(offerta.getId() == id) return offerta;
		}
		throw new NullPointerException();
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
	
	public List<Offerta> getOfferteSelezionateByUtente(String username) {
		List<Offerta> result = new ArrayList<>();
		for (Offerta offerta : listaOfferte) {
			if(offerta.getUsername().equalsIgnoreCase(username) && isOffertaSelezionata(offerta))
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
