package gestioneScambioArticoli;

import java.sql.Date;
import java.time.LocalDate;

import gestioneOfferte.Offerta;

public class Scambio {
	Offerta offertaA;
	Offerta offertaB;
	LocalDate scadenza;
	
	public Scambio(Offerta offertaA, Offerta offertaB, LocalDate scadenza) {
		this.offertaA = offertaA;
		this.offertaB = offertaB;
		this.scadenza = scadenza;
	}

	@Override
	public String toString() {
		return "Scambio [offertaA=" + offertaA + ", offertaB=" + offertaB + ", scadenza=" + scadenza + "]";
	}
	
	
}
