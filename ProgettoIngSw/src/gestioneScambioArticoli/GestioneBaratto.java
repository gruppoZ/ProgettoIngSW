package gestioneScambioArticoli;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import gestioneOfferte.GestioneOfferta;
import gestioneOfferte.Offerta;
import gestioneOfferte.OffertaAccoppiata;
import gestioneOfferte.OffertaAperta;
import gestioneOfferte.OffertaChiusa;
import gestioneOfferte.OffertaInScambio;
import gestioneOfferte.OffertaSelezionata;
import gestioneParametri.GestioneParametri;
import main.JsonIO;

public class GestioneBaratto {

	private static final String PATH_BARATTI = "src/gestioneScambioArticoli/baratti.json";

	private List<Baratto> listaBaratti;
	private Baratto baratto;
	
	public GestioneBaratto() {
		this.listaBaratti = leggiBaratti();
		this.baratto = new Baratto();
	}
	
	public Baratto getBaratto() {
		return this.baratto;
	}
	
	public boolean isOffertaInBaratto(Offerta offerta) {
		for (Baratto baratto : listaBaratti) {
			if(baratto.getOffertaFruitorePromotore().equals(offerta) || baratto.getOffertaFruitoreRichiesta().equals(offerta)) return true;
		}
		return false;
	}
	
	public Baratto getBarattoByOfferta(Offerta offerta) {
		for (Baratto baratto : listaBaratti) {
			if(baratto.getOffertaFruitorePromotore().equals(offerta) || baratto.getOffertaFruitoreRichiesta().equals(offerta)) return baratto;
		}
		return null;
	}
	
	protected Baratto getBarattoByOffertaSelezionata(Offerta offertaSelezionata) {
		for (Baratto baratto : listaBaratti) {
			if(baratto.getOffertaFruitoreRichiesta().equals(offertaSelezionata)) return baratto;
		}
		return null;
	}
	
	//TODO: NOME da cambiare
	public void riempiListeBarattiScaduti(List<Baratto> listaBarattiScaduti, List<Offerta> listaOfferteScadute){
		for (Baratto baratto : listaBaratti) {
			if(baratto.getScadenza().isBefore(LocalDate.now())) {
				listaBarattiScaduti.add(baratto);
				listaOfferteScadute.add(baratto.getOffertaFruitorePromotore());
				listaOfferteScadute.add(baratto.getOffertaFruitoreRichiesta());
			}
		}
	}
	
	/*
	 * TODO
	 * Funziona ma c'� un ping pong tra i gestori, da risolvere
	 */
//	public void gestisciBarattiScaduti(GestioneOfferta gestoreOfferte, List<Offerta> listaOfferte) {
//		List<Baratto> listaBarattiScaduti = new ArrayList<Baratto>();
//		List<Offerta> listaOfferteScadute = new ArrayList<Offerta>();
//		
//		for (Baratto baratto : listaBaratti) {
//			if(baratto.getScadenza().isBefore(LocalDate.now())) {
//				listaBarattiScaduti.add(baratto);
//				listaOfferteScadute.add(baratto.getOffertaFruitorePromotore());
//				listaOfferteScadute.add(baratto.getOffertaFruitoreRichiesta());
//			}
//		}
//		
//		rimuoviListaBaratti(listaBarattiScaduti);
//		
//		for (Offerta offertaScaduta : listaOfferteScadute) {
//			Offerta offerta = gestoreOfferte.getOffertaById(offertaScaduta.getId(), listaOfferte);
//			cambioOffertaScaduta(gestoreOfferte, offerta);
//		}	
//	}
	
	public void rimuoviListaBaratti(List<Baratto> listaBarattiDaRimuovere) {
		for (Baratto baratto : listaBarattiDaRimuovere) {
			this.listaBaratti.remove(baratto);
		}
		
		salvaBaratti();
	}
	
	private List<Baratto> leggiBaratti(){
		return JsonIO.leggiListaDaJson(PATH_BARATTI, Baratto.class);
	}
	
	private void salvaBaratti() {
		JsonIO.salvaOggettoSuJson(PATH_BARATTI, listaBaratti);
	}
	
	public void rimuoviBaratto(Baratto baratto) {
		listaBaratti.remove(baratto);
		salvaBaratti();
	}
	
	protected LocalDate calcolaDataScadenza(int scadenzaInGiorni) {
		LocalDate dataScadenza = LocalDate.now();
		return dataScadenza.plusDays(scadenzaInGiorni);
	}
	
	protected LocalDate getDataScadenza(GestioneParametri gestorePiazza, Appuntamento appuntamento) {
		LocalDate dataScadenza = calcolaDataScadenza(gestorePiazza.getScadenza());
		if(dataScadenza.isAfter(appuntamento.getData())) dataScadenza = appuntamento.getData();
		
		return dataScadenza;
	}
	
	/** 
	 * Aggiorna il baratto da aggiornare settando tutti i suoi parametri
	 * @param daAggiornare
	 * @param offertaA
	 * @param offertaB
	 * @param dataScadenza
	 * @param appuntamento
	 */
	
	protected void aggiornaBaratto(Baratto daAggiornare, Offerta offertaA, Offerta offertaB, LocalDate dataScadenza, Appuntamento appuntamento) {
		
		daAggiornare.setAppuntamento(appuntamento);
		daAggiornare.setOffertaFruitorePromotore(offertaA);
		daAggiornare.setOffertaFruitoreRichiesta(offertaB);
		daAggiornare.setScadenza(dataScadenza);
		
		salvaBaratti();
	}
	
	/** 
	 * Aggiorna il baratto da aggiornare settando soltanto il giorno della scadenza e l'appuntamento
	 * @param daAggiornare
	 * @param dataScadenza
	 * @param appuntamento
	 */
	protected void aggiornaBaratto(Baratto daAggiornare, LocalDate dataScadenza, Appuntamento appuntamento) {		
		daAggiornare.setAppuntamento(appuntamento);
		daAggiornare.setScadenza(dataScadenza);
		
		salvaBaratti();
	}
	
	protected void switchToOfferteAccoppiate(GestioneOfferta gestoreOfferta, Offerta offertaA, Offerta offertaB) {
		gestoreOfferta.gestisciCambiamentoStatoOfferta(offertaA, new OffertaAccoppiata());
		gestoreOfferta.gestisciCambiamentoStatoOfferta(offertaB, new OffertaSelezionata());
	}
	
	protected void switchToOfferteInScambio(GestioneOfferta gestoreOfferta, Offerta offertaA, Offerta offertaB) {
		gestoreOfferta.gestisciCambiamentoStatoOfferta(offertaA, new OffertaInScambio());
		gestoreOfferta.gestisciCambiamentoStatoOfferta(offertaB, new OffertaInScambio());
	}
	
	protected void switchToOfferteChiuse(GestioneOfferta gestoreOfferta, Offerta offertaA, Offerta offertaB) {
		gestoreOfferta.gestisciCambiamentoStatoOfferta(offertaA, new OffertaChiusa());
		gestoreOfferta.gestisciCambiamentoStatoOfferta(offertaB, new OffertaChiusa());
	}
	
	protected void gestisciRifiutoAppuntamento(Baratto baratto, Appuntamento appuntamento) {
		baratto.setAppuntamento(appuntamento);
		salvaBaratti();
	}
	
//	private void cambioOffertaScaduta(GestioneOfferta gestoreOfferta, Offerta offerta) {
//		gestoreOfferta.gestisciCambiamentoStatoOfferta(offerta, new OffertaAperta());
//	}
	
//	protected void creaBaratto(Offerta offertaA, Offerta offertaB, int scadenza, Appuntamento appuntamento) {
//		LocalDate dataScadenza = LocalDate.now();
//		dataScadenza = dataScadenza.plusDays(scadenza);
//		
//		this.baratto = new Baratto(offertaA, offertaB, dataScadenza, appuntamento);/
//		
//		listaBaratti.add(baratto);
//		salvaBaratti();
//	}
	protected void creaBaratto(Offerta offertaA, Offerta offertaB, int scadenzaInGiorni) {
		LocalDate dataScadenza = calcolaDataScadenza(scadenzaInGiorni);
		
		this.baratto = new Baratto(offertaA, offertaB, dataScadenza);
		
		listaBaratti.add(baratto);
		salvaBaratti();
	}
	
	protected boolean checkUguaglianzaAppuntamenti(Appuntamento appuntamento1, Appuntamento appuntamento2) {
		return appuntamento1.equals(appuntamento2);
	}

}
