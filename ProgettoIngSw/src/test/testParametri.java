package test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;

import gestioneParametri.GestioneParametri;
import gestioneParametri.Piazza;

class testParametri {

	@Test
	void canAddAndRemovePlacesFromPiazzaAlreadyCreated() {
		GestioneParametri gestroreParametri = new GestioneParametri();
		Piazza piazza = gestroreParametri.getPiazza();
		
		int numLuoghiPreModifiche = piazza.getLuoghi().size();
		
		gestroreParametri.aggiungiLuogo(piazza.getLuoghi(), "Test");
		
		assertEquals(numLuoghiPreModifiche, gestroreParametri.getPiazza().getLuoghi().size()-1);
		
		gestroreParametri.rimuoviLuogo(piazza.getLuoghi(), "Test");
		assertEquals(numLuoghiPreModifiche, gestroreParametri.getPiazza().getLuoghi().size());
	}
}
