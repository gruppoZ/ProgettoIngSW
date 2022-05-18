package gestioneScambioArticoli;

import java.time.LocalDate;
import java.util.List;

import gestioneOfferte.GestioneOfferta;
import gestioneOfferte.Offerta;
import gestioneOfferte.OffertaAccoppiata;
import gestioneOfferte.OffertaChiusa;
import gestioneOfferte.OffertaInScambio;
import gestioneOfferte.OffertaSelezionata;
import gestioneParametri.GestioneParametri;
import main.JsonIO;

public class GestioneBaratto {

	private static final String PATH_BARATTI = "src/gestioneScambioArticoli/baratti.json";
	private static final String PATH_BARATTI_TERMINATI = "src/gestioneScambioArticoli/barattiTerminati.json";

	private List<Baratto> listaBaratti;
	private Baratto baratto;
	
	/**
	 * Postcondizione: listaBaratti != null, baratto != null
	 */
	public GestioneBaratto() {
		this.listaBaratti = leggiBaratti();
		this.baratto = new Baratto();
	}
	
	public Baratto getBaratto() {
		return this.baratto;
	}
	
	/**
	 * Precondizione: offerta != null
	 * 
	 * @param offerta
	 * @return TRUE se "offerta" e' presente in almeno uno dei baratti nella lista
	 */
	public boolean isOffertaInBaratto(Offerta offerta) {
		for (Baratto baratto : listaBaratti) {
			if(baratto.getOffertaFruitorePromotore().equals(offerta) || baratto.getOffertaFruitoreRichiesta().equals(offerta)) return true;
		}
		return false;
	}
	
	/**
	 * Precondizione: offerta != null
	 * 
	 * @param offerta
	 * @return
	 */
	public Baratto getBarattoByOfferta(Offerta offerta) {
		for (Baratto baratto : listaBaratti) {
			if(baratto.getOffertaFruitorePromotore().equals(offerta) || baratto.getOffertaFruitoreRichiesta().equals(offerta)) return baratto;
		}
		return null;
	}
	
	/**
	 * Precondizione: offertaSelezionata != null
	 * @param offertaSelezionata
	 * @return
	 */
	protected Baratto getBarattoByOffertaSelezionata(Offerta offertaSelezionata) {
		for (Baratto baratto : listaBaratti) {
			if(baratto.getOffertaFruitoreRichiesta().equals(offertaSelezionata)) return baratto;
		}
		return null;
	}
	
	/**
	 * Precondizione: listaBarattiScaduti != null && listaOfferteScadute != null
	 * Postcondizione: 	listaBarattiScaduti'.size() = listaBarattiScaduti + n,
	 * 					listaOfferteScadute'.size() = listaBarattiScaduti + 2*n | n numero di baratti scaduti
	 * Inserisce nelle liste i baratti scaduti e le relative offerte 
	 * @param listaBarattiScaduti
	 * @param listaOfferteScadute
	 */
	public void caricaBarattiScadutiEOfferteScadute(List<Baratto> listaBarattiScaduti, List<Offerta> listaOfferteScadute) {
		for (Baratto baratto : listaBaratti) {
			if(baratto.getScadenza().isBefore(LocalDate.now())) {
				listaBarattiScaduti.add(baratto);
				listaOfferteScadute.add(baratto.getOffertaFruitorePromotore());
				listaOfferteScadute.add(baratto.getOffertaFruitoreRichiesta());
			}
		}
	}

	/**
	 * Precondizione: listaBarattiDaRimuovere != null
	 * Postcondizione:	listaBaratti'.size() = listaBaratti.size() - n | n = listaBarattiDaRimuovere.size()
	 * 
	 * Rimuove i baratti presenti in listaBarattiDaRimuovere dalla listaBaratti 
	 * dopodichè salva nel file baratti.json la nuova listaBaratti
	 * @param listaBarattiDaRimuovere
	 */
	public void rimuoviListaBaratti(List<Baratto> listaBarattiDaRimuovere) {
		for (Baratto baratto : listaBarattiDaRimuovere) {
			this.listaBaratti.remove(baratto);
		}
		
		salvaBaratti();
	}
	
	/**
	 * Precondizione: baratto != null
	 * Postcondizione:	listaBaratti'.size() = listaBaratti.size() - 1
	 * @param baratto
	 */
	public void rimuoviBaratto(Baratto baratto) {
		listaBaratti.remove(baratto);
		salvaBaratti();
	}
	
	private List<Baratto> leggiBaratti(){
		return JsonIO.leggiListaDaJson(PATH_BARATTI, Baratto.class);
	}
	
	private void salvaBaratti() {
		JsonIO.salvaOggettoSuJson(PATH_BARATTI, listaBaratti);
	}
	
	private List<Baratto> leggiBarattiTerminati(){
		return JsonIO.leggiListaDaJson(PATH_BARATTI_TERMINATI, Baratto.class);
	}
	
	private void salvaBarattiTerminati(List<Baratto> listaBarattiTerminati) {
		JsonIO.salvaOggettoSuJson(PATH_BARATTI_TERMINATI, listaBarattiTerminati);
	}
	
	/**
	 * Precondizione: baratto != null
	 * @param baratto
	 */
	private void aggiungiBarattoTerminato(Baratto baratto) {
		List<Baratto> listaBarattiTerminati = leggiBarattiTerminati();
		listaBarattiTerminati.add(baratto);
		salvaBarattiTerminati(listaBarattiTerminati);
	}
	
	/**
	 * Preso in input la scadenza in giorni calcola a partire da oggi la data di scadenza 
	 * @param scadenzaInGiorni
	 * @return data di scadenza
	 */
	protected LocalDate calcolaDataScadenza(int scadenzaInGiorni) {
		LocalDate dataScadenza = LocalDate.now();
		return dataScadenza.plusDays(scadenzaInGiorni);
	}
	
	/**
	 * Precondizione: gestorePiazza != null, appuntamento != null
	 * 
	 * @param gestorePiazza
	 * @param appuntamento
	 * @return data di scadenza
	 */
	protected LocalDate getDataScadenza(GestioneParametri gestorePiazza, Appuntamento appuntamento) {
		LocalDate dataScadenza = calcolaDataScadenza(gestorePiazza.getScadenza());
		if(dataScadenza.isAfter(appuntamento.getData())) dataScadenza = appuntamento.getData();
		
		return dataScadenza;
	}
	
	/** 
	 * Precondizione: barattoDaAggiornare != null, offertaA != null, offertaB != null, dataScadenza != null, appuntamento != null
	 * 
	 * Aggiorna il baratto daAggiornare settando tutti i suoi parametri
	 * @param barattoDaAggiornare
	 * @param offertaA
	 * @param offertaB
	 * @param dataScadenza
	 * @param appuntamento
	 */
	protected void aggiornaBaratto(Baratto barattoDaAggiornare, Offerta offertaA, Offerta offertaB, LocalDate dataScadenza, Appuntamento appuntamento) {
		barattoDaAggiornare.setAppuntamento(appuntamento);
		barattoDaAggiornare.setOffertaFruitorePromotore(offertaA);
		barattoDaAggiornare.setOffertaFruitoreRichiesta(offertaB);
		barattoDaAggiornare.setScadenza(dataScadenza);
		
		salvaBaratti();
	}
	
	/** 
	 * Precondizione: barattoDaAggiornare != null, dataScadenza != null, appuntamento != null
	 * 
	 * Aggiorna il baratto daAggiornare settando soltanto il giorno della scadenza e l'appuntamento
	 * @param barattoDaAggiornare
	 * @param dataScadenza
	 * @param appuntamento
	 */
	protected void aggiornaBaratto(Baratto barattoDaAggiornare, LocalDate dataScadenza, Appuntamento appuntamento) {		
		barattoDaAggiornare.setAppuntamento(appuntamento);
		barattoDaAggiornare.setScadenza(dataScadenza);
		
		salvaBaratti();
	}
	
	/**
	 * Precondizione: gestoreOfferta != null, offertaA != null, offertaB != null
	 * 
	 * Cambia lo stato dell'offertaA in "OffertaAccoppiata" e quello dell'offertaB in "OffertaSelezionata"
	 * @param gestoreOfferta
	 * @param offertaA
	 * @param offertaB
	 */
	protected void switchToOfferteAccoppiate(GestioneOfferta gestoreOfferta, Offerta offertaA, Offerta offertaB) {
		gestoreOfferta.gestisciCambiamentoStatoOfferta(offertaA, new OffertaAccoppiata());
		gestoreOfferta.gestisciCambiamentoStatoOfferta(offertaB, new OffertaSelezionata());
	}
	
	/**
	 * Precondizione: gestoreOfferta != null, offertaA != null, offertaB != null
	 * 
	 * Cambia lo stato delle offerte in "OffertaInScambio"
	 * @param gestoreOfferta
	 * @param offertaA
	 * @param offertaB
	 */
	protected void switchToOfferteInScambio(GestioneOfferta gestoreOfferta, Offerta offertaA, Offerta offertaB) {
		gestoreOfferta.gestisciCambiamentoStatoOfferta(offertaA, new OffertaInScambio());
		gestoreOfferta.gestisciCambiamentoStatoOfferta(offertaB, new OffertaInScambio());
	}
	
	/**
	 * Precondizione: gestoreOfferta != null, offertaA != null, offertaB != null
	 * 
	 * Cambia lo stato delle offerte in "OffertaChiusa"
	 * @param gestoreOfferta
	 * @param offertaA
	 * @param offertaB
	 */
	protected void switchToOfferteChiuse(GestioneOfferta gestoreOfferta, Offerta offertaA, Offerta offertaB) {
		gestoreOfferta.gestisciCambiamentoStatoOfferta(offertaA, new OffertaChiusa());
		gestoreOfferta.gestisciCambiamentoStatoOfferta(offertaB, new OffertaChiusa());
	}
	
	/**
	 * Precondizione: baratto != null, appuntamento != null
	 * 
	 * @param baratto
	 * @param appuntamento
	 */
	protected void aggiornaAppuntamentoInBaratto(Baratto baratto, Appuntamento appuntamento) {
		baratto.setAppuntamento(appuntamento);
		salvaBaratti();
	}
	
	/**
	 * Precondizione: gestoreOfferte != null, offertaA != null, offertaB != null, baratto != null
	 * 
	 * @param gestoreOfferte
	 * @param offertaA
	 * @param offertaB
	 * @param baratto
	 */
	protected void gestisciChiusuraBaratto(GestioneOfferta gestoreOfferte, Offerta offertaA, Offerta offertaB, Baratto baratto) {
		switchToOfferteChiuse(gestoreOfferte, offertaA, offertaB);
		rimuoviBaratto(baratto);
		aggiungiBarattoTerminato(baratto);
	}

	/**
	 * Precondizione: offertaA != null, offertaB != nulll
	 * 
	 * Permette di creare un baratto, inserirlo nella listaBaratti ed aggiornare il file baratti.json
	 * @param offertaA
	 * @param offertaB
	 * @param scadenzaInGiorni
	 */
	protected void creaBaratto(Offerta offertaA, Offerta offertaB, int scadenzaInGiorni) {
		LocalDate dataScadenza = calcolaDataScadenza(scadenzaInGiorni);
		
		this.baratto = new Baratto(offertaA, offertaB, dataScadenza);
		
		listaBaratti.add(baratto);
		salvaBaratti();
	}
	
	/**
	 * Precondizione: appuntamento1 != null, appuntamento2 != null
	 * @param appuntamento1
	 * @param appuntamento2
	 * @return
	 */
	protected boolean checkUguaglianzaAppuntamenti(Appuntamento appuntamento1, Appuntamento appuntamento2) {
		return appuntamento1.equals(appuntamento2);
	}

}
