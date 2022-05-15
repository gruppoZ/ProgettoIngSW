package gestioneScambioArticoli;

import java.time.LocalDate;
import gestioneOfferte.Offerta;

//TODO: questi dati potrebbero essere salvati in Offera in Scambio
public class Baratto {
	Offerta offertaFruitorePromotore;
	Offerta offertaFruitoreRichiesta;
	LocalDate scadenza;
	Appuntamento appuntamento;
	
	public Baratto() {
		
	}
	
	public Baratto(Offerta offertaA, Offerta offertaB, LocalDate scadenza) {
		this.offertaFruitorePromotore = offertaA;
		this.offertaFruitoreRichiesta = offertaB;
		this.scadenza = scadenza;
		this.appuntamento = new Appuntamento();
	}
	
	public Baratto(Offerta offertaA, Offerta offertaB, LocalDate scadenza, Appuntamento appuntamento) {
		this.offertaFruitorePromotore = offertaA;
		this.offertaFruitoreRichiesta = offertaB;
		this.scadenza = scadenza;
		this.appuntamento = appuntamento;
	}

	public Appuntamento getAppuntamento() {
		return appuntamento;
	}

	public void setAppuntamento(Appuntamento appuntamento) {
		this.appuntamento = appuntamento;
	}

	public Offerta getOffertaFruitorePromotore() {
		return offertaFruitorePromotore;
	}

	public Offerta getOffertaFruitoreRichiesta() {
		return offertaFruitoreRichiesta;
	}

	public LocalDate getScadenza() {
		return scadenza;
	}

	@Override
	public String toString() {
		return "Scambio [offertaA=" + offertaFruitorePromotore + ", offertaB=" + offertaFruitoreRichiesta + ", scadenza=" + scadenza + "]";
	}
}
