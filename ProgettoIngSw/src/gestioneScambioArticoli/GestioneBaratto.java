package gestioneScambioArticoli;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gestioneOfferte.GestioneOfferta;
import gestioneOfferte.Offerta;
import gestioneOfferte.OffertaAccoppiata;
import gestioneOfferte.OffertaAperta;
import gestioneOfferte.OffertaChiusa;
import gestioneOfferte.OffertaInScambio;
import gestioneOfferte.OffertaSelezionata;
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
			if(baratto.getOffertaA().equals(offerta) || baratto.getOffertaB().equals(offerta)) return true;
		}
		return false;
	}
	
	public Baratto getBarattoByOfferta(Offerta offerta) {
		for (Baratto baratto : listaBaratti) {
			if(baratto.getOffertaA().equals(offerta) || baratto.getOffertaB().equals(offerta)) return baratto;
		}
		return null;
	}
	
	protected Baratto getBarattoByOffertaSelezionata(Offerta offertaSelezionata) {
		for (Baratto baratto : listaBaratti) {
			if(baratto.getOffertaB().equals(offertaSelezionata)) return baratto;
		}
		return null;
	}
	
	/*
	 * Funziona ma c'è un ping pong tra i gestori, da risolvere
	 */
	public void gestisciBarattiScaduti(GestioneOfferta gestoreOfferte, List<Offerta> listaOfferte) {
		List<Baratto> listaBarattiScaduti = new ArrayList<Baratto>();
		List<Offerta> listaOfferteScadute = new ArrayList<Offerta>();
		
		for (Baratto baratto : listaBaratti) {
			if(baratto.getScadenza().isBefore(LocalDate.now())) {
				listaBarattiScaduti.add(baratto);
				listaOfferteScadute.add(baratto.getOffertaA());
				listaOfferteScadute.add(baratto.getOffertaB());
			}
		}
		
		rimuoviListaBaratti(listaBarattiScaduti);
		
		for (Offerta offertaScaduta : listaOfferteScadute) {
			Offerta offerta = gestoreOfferte.getOffertaById(offertaScaduta.getId(), listaOfferte);
			cambioOffertaScaduta(gestoreOfferte, offerta);
		}	
	}
	
	private void rimuoviListaBaratti(List<Baratto> listaBarattiDaRimuovere) {
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
	
	protected void creaCollegamento(GestioneOfferta gestoreOfferta, Offerta offertaA, Offerta offertaB) {
		gestoreOfferta.gestisciCambiamentoStatoOfferta(offertaA, new OffertaAccoppiata());
		gestoreOfferta.gestisciCambiamentoStatoOfferta(offertaB, new OffertaSelezionata());
	}
	
	protected void creaScambio(GestioneOfferta gestoreOfferta, Offerta offertaA, Offerta offertaB, Appuntamento appuntamento) {
		gestoreOfferta.gestisciCambiamentoStatoOfferta(offertaA, new OffertaInScambio());
		gestoreOfferta.gestisciCambiamentoStatoOfferta(offertaB, new OffertaInScambio(appuntamento));
	}
	
	protected void cambioOfferteChiuse(GestioneOfferta gestoreOfferta, Offerta offertaA, Offerta offertaB) {
		gestoreOfferta.gestisciCambiamentoStatoOfferta(offertaA, new OffertaChiusa());
		gestoreOfferta.gestisciCambiamentoStatoOfferta(offertaB, new OffertaChiusa());
	}
	
	protected void gestisciRifiutoAppuntamento(GestioneOfferta gestoreOfferte, OffertaInScambio tipoOfferta1, OffertaInScambio tipoOfferta2, Appuntamento appuntamento) {
		tipoOfferta2.getAppuntamento().setValido(false); //oppure si fa setAppuntamento(new Appuntamento())
		tipoOfferta1.setAppuntamento(appuntamento);
		gestoreOfferte.salvaOfferte(); //da spostare
	}
	
	private void cambioOffertaScaduta(GestioneOfferta gestoreOfferta, Offerta offerta) {
		gestoreOfferta.gestisciCambiamentoStatoOfferta(offerta, new OffertaAperta());
	}
	
	protected void creaBaratto(Offerta offertaA, Offerta offertaB, int scadenza) {
		LocalDate dataScadenza = LocalDate.now();
		dataScadenza = dataScadenza.plusDays(scadenza);
		
		this.baratto = new Baratto(offertaA, offertaB, dataScadenza);
		
		listaBaratti.add(baratto);
		salvaBaratti();
	}
	
	protected boolean checkUguaglianzaAppuntamenti(Appuntamento appuntamento1, Appuntamento appuntamento2) {
		return appuntamento1.equals(appuntamento2);
	}
}
