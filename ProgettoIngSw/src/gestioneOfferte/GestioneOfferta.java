package gestioneOfferte;

import java.time.LocalDate;
import java.util.*;


import gestioneCategorie.Categoria;
import gestioneScambioArticoli.GestioneBaratto;
import main.JsonIO;

public class GestioneOfferta {

	private static final String PATH_OFFERTE = "src/gestioneOfferte/offerte.json";
	private static final String PATH_STORICO_CAMBIO_STATI = "src/gestioneOfferte/storico_cambio_stati.json";
	
	private List<Offerta> listaOfferte = new ArrayList<Offerta>(); 
	
	HashMap<Integer, ArrayList<PassaggioTraStati>> storicoMovimentazioni;
	
	public GestioneOfferta() {
		this.storicoMovimentazioni = leggiStoricoCambioStati();
		listaOfferte = (ArrayList<Offerta>) leggiListaOfferte(); 
	}
	
	private boolean isOffertaAperta(Offerta offerta) {
		return offerta.getTipoOfferta().getStato().equalsIgnoreCase(StatiOfferta.OFFERTA_APERTA.getNome());
	}
	
	private boolean isOffertaChiusa(Offerta offerta) {
		return offerta.getTipoOfferta().getStato().equalsIgnoreCase(StatiOfferta.OFFERTA_CHIUSA.getNome());
	}
	
	private boolean isOffertaSelezionata(Offerta offerta) {
		return offerta.getTipoOfferta().getStato().equalsIgnoreCase(StatiOfferta.OFFERTA_SELEZIONATA.getNome());
	}
		
	private boolean isOffertaInScambio(Offerta offerta) {
		return offerta.getTipoOfferta().getStato().equalsIgnoreCase(StatiOfferta.OFFERTA_IN_SCAMBIO.getNome());
	}
	
	
	/**
	 * Permette di cambiare lo stato di un offerta, dopodichè salva il passaggio di stato nello storico
	 * @param offerta
	 */
	public void gestisciCambiamentoStatoOfferta(Offerta offerta, StatoOfferta stato) {
		int id = offerta.getId();
		String oldState, newState;
		oldState = offerta.getTipoOfferta().getStato();
		
		offerta.getTipoOfferta().changeState(offerta, stato);
		
		newState = offerta.getTipoOfferta().getStato();
		
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

	/*
	 * TODO: !! il GestioneOfferta viene creato troppo spesso e la lettura viene rifatta molte volte inutilmente
	 */
	protected List<Offerta> leggiListaOfferte() {
		List<Offerta> listaOfferte = (ArrayList<Offerta>) JsonIO.leggiListaDaJson(PATH_OFFERTE, Offerta.class);
		if(listaOfferte == null) //per evitare di salvare "null" nel file offerte.json 
			listaOfferte = new ArrayList<Offerta>();
		setListaOfferte(aggiornaListaOfferte(new GestioneBaratto(), listaOfferte));//gestioneBaratto da creare o da chiedere al metodo?
		//ora salva sempre, meglio salvare solo quando faccio un cambio
		salvaOfferte();		
		return listaOfferte;
	}
	
	private List<Offerta> aggiornaListaOfferte(GestioneBaratto gestoreBaratto, List<Offerta> listaOfferte) {
		gestoreBaratto.gestisciBarattiScaduti(this, listaOfferte);
		return listaOfferte;
	}

	
	/*
	 * Per ora aggiorna le offerte di TUTTI
	 * TODO: per aggiornare solo le MIE offerte ho bisogno del username (dovrei richiederlo nel costruttore)
	 */
//	private List<Offerta> aggiornaListaOfferte(GestioneBaratto gestoreBaratto, List<Offerta> listaOfferte) {
//		List<Offerta> listaOfferteAggiornata= new ArrayList<Offerta>();
//		for (Offerta offerta : listaOfferte) {
//			if(gestoreBaratto.isOffertaInBaratto(offerta)) {
//				Baratto baratto = gestoreBaratto.getBarattoByOfferta(offerta);
//				if(baratto.getScadenza().isBefore(LocalDate.now())) {
//					System.out.println("Il baratto dell'offerta con ID: " + offerta.getId() + " e' scaduto");
//					cambioOffertaScaduta(offerta);
//					//TODO: bisogna cancellare il baratto solo dopo che entrambe le offerte sono state rimosse:
//					//			non ciclare sulle offerte ma solo sui baratti => rimuovere entrambe le offerte => rimuovi baratto
////					gestoreBaratto.rimuoviBaratto(baratto);
//				}
//			}
//			listaOfferteAggiornata.add(offerta);
//		}
//		
////		gestoreBaratto.aggiornaListaBaratti();
//		
//		return listaOfferteAggiornata;
//	}
	
	public Offerta getOffertaById(int id, List<Offerta> listaOfferte) throws NullPointerException {
		for (Offerta offerta : listaOfferte) {
			if(offerta.getId() == id) return offerta;
		}
		throw new NullPointerException();
	}
	
	public Offerta getOffertaById(int id) throws NullPointerException {
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
	
	public List<Offerta> getOfferteChiuseByCategoria(Categoria foglia) {
		List<Offerta> result = new ArrayList<>();
		for (Offerta offerta : listaOfferte) {
			if(isOffertaChiusa(offerta)) {
				if(offerta.getArticolo().getFoglia().equals(foglia))
					result.add(offerta);		
			}
		}
		
		return result;
	}
	
	public List<Offerta> getOfferteInScambioByCategoria(Categoria foglia) {
		List<Offerta> result = new ArrayList<>();
		for (Offerta offerta : listaOfferte) {
			if(isOffertaInScambio(offerta)) {
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
