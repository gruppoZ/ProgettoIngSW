package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import application.Categoria;
import application.baratto.Articolo;
import application.baratto.Baratto;
import application.baratto.Offerta;
import application.baratto.StatiOfferta;

public class GestioneOfferta {
	
	private List<Offerta> listaOfferte; 
	
	private OffertaRepository repo;
	
	public GestioneOfferta() throws FileNotFoundException, IOException {
		repo = new OffertaRepository();
		
		listaOfferte = (ArrayList<Offerta>) leggiListaOfferte(); 
	}
	
	public boolean isOffertaAperta(Offerta offerta) {
		return offerta.getStatoOfferta().getStato().equalsIgnoreCase(StatiOfferta.OFFERTA_APERTA.getNome());
	}
	
	public boolean isOffertaChiusa(Offerta offerta) {
		return offerta.getStatoOfferta().getStato().equalsIgnoreCase(StatiOfferta.OFFERTA_CHIUSA.getNome());
	}
	
	public boolean isOffertaSelezionata(Offerta offerta) {
		return offerta.getStatoOfferta().getStato().equalsIgnoreCase(StatiOfferta.OFFERTA_SELEZIONATA.getNome());
	}
	
	public boolean isOffertaAccoppiata(Offerta offerta) {
		return offerta.getStatoOfferta().getStato().equalsIgnoreCase(StatiOfferta.OFFERTA_ACCOPPIATA.getNome());
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
	 * Precondizione: offertaA != null, offertaB != null
	 * 
	 * Cambia lo stato dell'offertaA in "OffertaAccoppiata" e quello dell'offertaB in "OffertaSelezionata"
	 * @param offertaA
	 * @param offertaB
	 * @throws IOException 
	 */
	public void barattoCreato(Offerta offertaA, Offerta offertaB) throws IOException {	
		offertaA.setAutore(true);
		
		offertaA.accoppiaOfferta();
		offertaB.accoppiaOfferta();
		
		salvaOfferte();
	}
	
	/**
	 * Precondizione: offertaA != null, offertaB != null
	 * 
	 * Cambia lo stato delle offerte in "OffertaInScambio"
	 * @param offertaA
	 * @param offertaB
	 * @throws IOException 
	 */
	public void switchToOfferteInScambio(Offerta offertaA, Offerta offertaB) throws IOException {
		offertaA.inScambioOfferta();
		offertaB.inScambioOfferta();
		
		salvaOfferte();
	}
	
	/**
	 * Precondizione: offertaA != null, offertaB != null
	 * 
	 * Cambia lo stato delle offerte in "OffertaChiusa"
	 * @param offertaA
	 * @param offertaB
	 * @throws IOException 
	 */
	public void switchToOfferteChiuse(Offerta offertaA, Offerta offertaB) throws IOException {
		offertaA.chiudiOfferta();
		offertaB.chiudiOfferta();
		
		salvaOfferte();
	}	
	
	public void ritiraOfferta(Offerta offerta) throws IOException {
		offerta.ritiraOfferta();
		
		salvaOfferte();
	}
	
	/**
	 * Precondizione: offerta != null
	 * 
	 * @param offerta
	 * @throws IOException 
	 */
	private void cambioOffertaScaduta(Offerta offerta) throws IOException {
		offerta.apriOfferta();
		
		salvaOfferte();
	}
	
	protected List<Offerta> leggiListaOfferte() throws FileNotFoundException, IOException {
		List<Offerta> listaOfferte = (ArrayList<Offerta>) repo.getItems();
		
		if(listaOfferte == null) 
			listaOfferte = new ArrayList<Offerta>();
		
		setListaOfferte(aggiornaListaOfferte(listaOfferte));
		
		salvaOfferte();		
		return listaOfferte;
	}
	
	/**
	 * Precondizione: listaOfferte != null
	 * 
	 * @param listaOfferte
	 * @return
	 * @throws IOException 
	 */
	private List<Offerta> aggiornaListaOfferte(List<Offerta> listaOfferte) throws IOException {
		gestisciBarattiScaduti(listaOfferte);
		return listaOfferte;
	}
	
	
	/**
	 * Precondizione: listaOfferte != null
	 * 
	 * @param listaOfferte
	 * @throws IOException 
	 */
	private void gestisciBarattiScaduti(List<Offerta> listaOfferte) throws IOException {
		GestioneBaratto gestoreBaratto = new GestioneBaratto();
		
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
	 * Precondizione: id >= 1, listaOfferte != null
	 * 
	 * @param id
	 * @param listaOfferte
	 * @return
	 * @throws NullPointerException
	 */
	public Offerta getOffertaById(String id, List<Offerta> listaOfferte) throws NullPointerException {
		for (Offerta offerta : listaOfferte) {
			if(offerta.getId().equals(id)) return offerta;
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
	public Offerta getOffertaById(String id) throws NullPointerException {
		for (Offerta offerta : listaOfferte) {
			if(offerta.getId().equals(id)) return offerta;
		}
		throw new NullPointerException();
	}
	
	public List<Offerta> getOfferteByUtente(String username) {
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
	
	
	public void salvaOfferte() throws IOException {
		repo.setOfferte(listaOfferte);
		repo.salva();
	}
		
	protected int numeroOfferteAperte() {
		int n = 0;
		
		for (Offerta offerta : this.listaOfferte) {
			if(isOffertaAperta(offerta)) n++;
		}
		
		return n;
	}
	
	/**
	 * Precondizione: articolo != null
	 * 
	 * @param articolo
	 * @param username
	 * @throws IOException 
	 */
	public void manageAggiuntaOfferta(Articolo articolo, String username) throws IOException {
		String id = UUID.randomUUID().toString();	
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
	private Offerta creaOfferta(String id, Articolo articolo, String username) {
		return new Offerta(id, articolo, username);
	}
	
	/**
	 * Precondizione: offerta != null
	 * Postcondizione: listaOfferte'.size() = listaOfferte.size() + 1 
	 * @param offerta
	 * @throws IOException 
	 */
	protected void aggiungiOfferta(Offerta offerta) throws IOException {
		this.listaOfferte.add(offerta);
		this.salvaOfferte();
	}
	
	/**
	 * Precondizione: offerta != null
	 * Postcondizione: listaOfferte'.size() = listaOfferte.size() - 1 
	 * @param offerta
	 * @throws IOException 
	 */
	protected void rimuoviOfferta(Offerta offerta) throws IOException {
		this.listaOfferte.remove(offerta);
		this.salvaOfferte();
	}
}
