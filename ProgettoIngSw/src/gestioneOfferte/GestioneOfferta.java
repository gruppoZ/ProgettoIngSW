package gestioneOfferte;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import gestioneCategorie.Categoria;
import gestioneScambioArticoli.Baratto;
import gestioneScambioArticoli.GestioneBaratto;
import main.JsonIO;

public class GestioneOfferta {
	private static final String PATH_OFFERTE = "resources/offerte.json";
	private static final String PATH_STORICO_CAMBIO_STATI = "resources/storico_cambio_stati.json";
	
	private List<Offerta> listaOfferte = new ArrayList<Offerta>(); 
	private Map<Integer, ArrayList<PassaggioTraStati>> storicoMovimentazioni;
	
	public GestioneOfferta() throws FileNotFoundException, IOException {
		this.storicoMovimentazioni = leggiStoricoCambioStati();
		listaOfferte = (ArrayList<Offerta>) leggiListaOfferte(); 
	}
	
	public boolean isOffertaAperta(Offerta offerta) {
		return offerta.getStatoOfferta().getStato().equalsIgnoreCase(StatiOfferta.OFFERTA_APERTA.getNome());
	}
	
	private boolean isOffertaChiusa(Offerta offerta) {
		return offerta.getStatoOfferta().getStato().equalsIgnoreCase(StatiOfferta.OFFERTA_CHIUSA.getNome());
	}
	
	private boolean isOffertaSelezionata(Offerta offerta) {
		return offerta.getStatoOfferta().getStato().equalsIgnoreCase(StatiOfferta.OFFERTA_SELEZIONATA.getNome());
	}
		
	public boolean isOffertaInScambio(Offerta offerta) {
		return offerta.getStatoOfferta().getStato().equalsIgnoreCase(StatiOfferta.OFFERTA_IN_SCAMBIO.getNome());
	}
	
	public List<Offerta> getListaOfferte() {
		return listaOfferte;
	}
	
	public void setListaOfferte(List<Offerta> listaOfferte) {
		this.listaOfferte = listaOfferte;
	}
	
	/**
	 * Precondizione: offerta != null, stato != null
	 * 
	 * Permette di cambiare lo stato di un offerta, dopodichè salva il passaggio di stato nello storico
	 * @param offerta
	 * @throws IOException 
	 */
	public void gestisciCambiamentoStatoOfferta(Offerta offerta, StatoOfferta stato) throws IOException {
		int id = offerta.getId();
		String oldState, newState;
		oldState = offerta.getStatoOfferta().getStato();
		
		offerta.getStatoOfferta().changeState(offerta, stato);
		
		newState = offerta.getStatoOfferta().getStato();
		
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

	protected List<Offerta> leggiListaOfferte() throws FileNotFoundException, IOException {
		List<Offerta> listaOfferte = (ArrayList<Offerta>) JsonIO.leggiListaDaJson(PATH_OFFERTE, Offerta.class);
		if(listaOfferte == null) 
			listaOfferte = new ArrayList<Offerta>();
		setListaOfferte(aggiornaListaOfferte(new GestioneBaratto(), listaOfferte));
		
		salvaOfferte();		
		return listaOfferte;
	}
	
	/**
	 * Precondizione: gestoreBaratto != null, listaOfferte != null
	 * 
	 * @param gestoreBaratto
	 * @param listaOfferte
	 * @return
	 * @throws IOException 
	 */
	private List<Offerta> aggiornaListaOfferte(GestioneBaratto gestoreBaratto, List<Offerta> listaOfferte) throws IOException {
		gestisciBarattiScaduti(gestoreBaratto, listaOfferte);
		return listaOfferte;
	}
	
	
	/**
	 * Precondizione: gestoreBaratto != null, listaOfferte != null
	 * 
	 * @param gestoreBaratto
	 * @param listaOfferte
	 * @throws IOException 
	 */
	private void gestisciBarattiScaduti(GestioneBaratto gestoreBaratto, List<Offerta> listaOfferte) throws IOException {
		List<Baratto> listaBarattiScaduti = new ArrayList<Baratto>();
		List<Offerta> listaOfferteScadute = new ArrayList<Offerta>();
		gestoreBaratto.caricaBarattiScadutiEOfferteScadute(listaBarattiScaduti, listaOfferteScadute);
		
		gestoreBaratto.rimuoviListaBaratti(listaBarattiScaduti);
		
		for (Offerta offertaScaduta : listaOfferteScadute) {
			Offerta offerta = getOffertaById(offertaScaduta.getId(), listaOfferte);
			cambioOffertaScaduta(offerta);
		}	
		
	}
	
	/**
	 * Precondizione: offerta != null
	 * 
	 * @param offerta
	 * @throws IOException 
	 */
	private void cambioOffertaScaduta(Offerta offerta) throws IOException {
		gestisciCambiamentoStatoOfferta(offerta, new OffertaAperta());
	}
	
	
	/**
	 * Precondizione: id >= 1, listaOfferte != null
	 * 
	 * @param id
	 * @param listaOfferte
	 * @return
	 * @throws NullPointerException
	 */
	public Offerta getOffertaById(int id, List<Offerta> listaOfferte) throws NullPointerException {
		for (Offerta offerta : listaOfferte) {
			if(offerta.getId() == id) return offerta;
		}
		throw new NullPointerException();
	}
	
	/**
	 * Precondizione: id >= 1
	 * 
	 * @param id
	 * @return
	 * @throws NullPointerException
	 */
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
	
	public List<Offerta> getOfferteInScambioByUtente(String username) {
		List<Offerta> result = new ArrayList<>();
		for (Offerta offerta : listaOfferte) {
			if(offerta.getUsername().equalsIgnoreCase(username) && isOffertaInScambio(offerta))
				result.add(offerta);
		}
		
		return result;
	}
	
	/**
	 * Precondizione: foglia != null
	 * 
	 * @param foglia
	 * @return
	 */
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
	
	/**
	 * Precondizione: foglia != null
	 * 
	 * @param foglia
	 * @return
	 */
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
	

	/**
	 * Precondizione: foglia != null
	 * 
	 * @param foglia
	 * @return
	 */
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
	
	/**
	 * Precondizione: foglia != null
	 * 
	 * @param foglia
	 * @param username
	 * @return
	 */
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
	
	protected HashMap<Integer, ArrayList<PassaggioTraStati>> leggiStoricoCambioStati() throws FileNotFoundException, IOException {
		return JsonIO.leggiStoricoCambioStatiOffertaDaJson(PATH_STORICO_CAMBIO_STATI);
	}
	
	public void salvaOfferte() throws IOException {
		JsonIO.salvaOggettoSuJson(PATH_OFFERTE, listaOfferte);
	}
	
	protected void salvaStoricoCambioStati() throws IOException {
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
	
	/**
	 * Precondizione: articolo != null
	 * 
	 * @param articolo
	 * @param username
	 * @throws IOException 
	 */
	protected void manageAggiuntaOfferta(Articolo articolo, String username) throws IOException {
		int id = getIdMax() + 1;
		Offerta offerta = creaOfferta(id, articolo, username);
		aggiungiOfferta(offerta);
		salvaOfferte();
	}
	
	/**
	 * Precondizione: id >= 1, Articolo != null
	 * 
	 * @param id
	 * @param articolo
	 * @param username
	 * @return
	 */
	private Offerta creaOfferta(int id, Articolo articolo, String username) {
		return new Offerta(id, articolo, username, new OffertaAperta());
	}
	
	/**
	 * Precondizione: offerta != null
	 * Postcondizione: listaOfferte'.size() = listaOfferte.size() + 1 
	 * @param offerta
	 */
	protected void aggiungiOfferta(Offerta offerta) {
		this.listaOfferte.add(offerta);
	}
	
	/**
	 * Precondizione: offerta != null
	 * Postcondizione: listaOfferte'.size() = listaOfferte.size() - 1 
	 * @param offerta
	 */
	protected void rimuoviOfferta(Offerta offerta) {
		this.listaOfferte.remove(offerta);
	}
}
