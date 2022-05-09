package gestioneScambioArticoli;

import java.time.LocalDate;
import java.util.List;

import gestioneOfferte.Offerta;
import gestioneOfferte.OffertaAccoppiata;
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
	
	private List<Baratto> leggiBaratti(){
		return JsonIO.leggiListaDaJson(PATH_BARATTI, Baratto.class);
	}
	
	private void salvaBaratti() {
		JsonIO.salvaOggettoSuJson(PATH_BARATTI, listaBaratti);
	}
	
	protected void creaCollegamento(Offerta offertaA, Offerta offertaB) {
		offertaA.getTipoOfferta().changeState(offertaA, new OffertaAccoppiata());
		offertaB.getTipoOfferta().changeState(offertaB, new OffertaSelezionata());
	}
	
	protected void creaBaratto(Offerta offertaA, Offerta offertaB, int scadenza) {
		LocalDate dataScadenza = LocalDate.now();
		dataScadenza = dataScadenza.plusDays(scadenza);
		
		this.baratto = new Baratto(offertaA, offertaB, dataScadenza);
		
		listaBaratti.add(baratto);
		salvaBaratti();
	}
	
	
}
