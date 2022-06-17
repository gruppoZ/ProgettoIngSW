package test;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import application.IntervalloOrario;
import application.Piazza;
import controller.GestioneParametri;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

class testParametri {

	private static final String INVALIDE_DATE = "300/10/2021";

	@Test
	void canAddAndRemovePlacesFromPiazzaAlreadyCreated() throws IOException, Exception {
		GestioneParametri gestroreParametri = new GestioneParametri();
		Piazza piazza = gestroreParametri.getPiazza();
		
		int numLuoghiPreModifiche = piazza.getLuoghi().size();
		
		gestroreParametri.aggiungiLuogo(piazza.getLuoghi(), "Test");
		assertEquals(numLuoghiPreModifiche, gestroreParametri.getLuoghi().size()-1);
		
		gestroreParametri.rimuoviLuogo(piazza.getLuoghi(), "Test");
		assertEquals(numLuoghiPreModifiche, gestroreParametri.getLuoghi().size());
	}
	
	@Test
	void canAddIntervvalloOrarioInListIntervalli() throws IOException {
		GestioneParametri gestoreParametri = new GestioneParametri();
		List<IntervalloOrario> intervalli = new ArrayList<>();
		
		IntervalloOrario intervalloNonValido = new IntervalloOrario(LocalTime.of(6, 30), LocalTime.of(8, 30));
		IntervalloOrario intervalloValido = new IntervalloOrario(LocalTime.of(4, 30), LocalTime.of(5, 30));
		
		intervalli.add(new IntervalloOrario(LocalTime.of(1, 00), LocalTime.of(2, 30)));
		intervalli.add(new IntervalloOrario(LocalTime.of(2, 30), LocalTime.of(4, 00)));
		intervalli.add(new IntervalloOrario(LocalTime.of(5, 30), LocalTime.of(7, 30)));
		
		assertFalse(gestoreParametri.checkValiditaIntervallo(intervalli, intervalloNonValido));
		assertTrue(gestoreParametri.checkValiditaIntervallo(intervalli, intervalloValido));
	}
	
	@Test
	void canNotConvertFromStringToLocalDate() throws IOException {
		GestioneParametri gestoreParametri = new GestioneParametri();
		
		assertThrows(DateTimeException.class, () -> {
			gestoreParametri.dateInput(INVALIDE_DATE);
		});
	}
}
