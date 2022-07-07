package test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import application.parametriPiazza.IntervalloOrario;
import application.parametriPiazza.Piazza;
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
		
		gestroreParametri.aggiungiLuogo("Test");
		assertEquals(numLuoghiPreModifiche, gestroreParametri.getLuoghi().size()-1);
		
		gestroreParametri.rimuoviLuogo("Test");
		assertEquals(numLuoghiPreModifiche, gestroreParametri.getLuoghi().size());
	}
	
	@Test
	void canAddIntervalloOrarioInListIntervalli() {
		Piazza piazza = new Piazza();
		List<IntervalloOrario> intervalli = new ArrayList<>();
		
		IntervalloOrario intervalloNonValido = new IntervalloOrario(LocalTime.of(6, 30), LocalTime.of(8, 30));
		IntervalloOrario intervalloValido = new IntervalloOrario(LocalTime.of(4, 30), LocalTime.of(5, 30));
		
		intervalli.add(new IntervalloOrario(LocalTime.of(1, 00), LocalTime.of(2, 30)));
		intervalli.add(new IntervalloOrario(LocalTime.of(2, 30), LocalTime.of(4, 00)));
		intervalli.add(new IntervalloOrario(LocalTime.of(5, 30), LocalTime.of(7, 30)));
		
		piazza.setIntervalliOrari(intervalli);
		
		assertFalse(piazza.checkValiditaIntervallo(intervalloNonValido));
		assertTrue(piazza.checkValiditaIntervallo(intervalloValido));
	}
	
	//forse da eliminare
	@Test
	void checkIncorrectHour() {
		assertDoesNotThrow(() -> {new IntervalloOrario(LocalTime.of(00, 00), LocalTime.of(00, 30));});
		assertDoesNotThrow(() -> {new IntervalloOrario(LocalTime.of(23, 00), LocalTime.of(23, 30));});
		assertThrows(DateTimeException.class, () -> {new IntervalloOrario(LocalTime.of(-1, 00), LocalTime.of(1, 00));});
		assertThrows(DateTimeException.class, () -> {new IntervalloOrario(LocalTime.of(24, 00), LocalTime.of(1, 00));});
	}
	
	@Test
	void checkIncorrectMinute() {
		Piazza piazza = new Piazza();

		IntervalloOrario intervalloCorretto1 = new IntervalloOrario(LocalTime.of(00, 00), LocalTime.of(01, 00));
		IntervalloOrario intervalloCorretto2 = new IntervalloOrario(LocalTime.of(00, 30), LocalTime.of(01, 30));
		assertTrue(piazza.checkValiditaIntervallo(intervalloCorretto1));
		assertTrue(piazza.checkValiditaIntervallo(intervalloCorretto2));
		
		IntervalloOrario intervalloSbagliato1 = new IntervalloOrario(LocalTime.of(00, 00), LocalTime.of(00, 59));
		IntervalloOrario intervalloSbagliato2 = new IntervalloOrario(LocalTime.of(00, 59), LocalTime.of(00, 00));
		assertFalse(piazza.checkValiditaIntervallo(intervalloSbagliato1));
		assertFalse(piazza.checkValiditaIntervallo(intervalloSbagliato2));
		
		IntervalloOrario intervalloSbagliato3 = new IntervalloOrario(LocalTime.of(00, 01), LocalTime.of(00, 00));
		IntervalloOrario intervalloSbagliato4 = new IntervalloOrario(LocalTime.of(00, 00), LocalTime.of(00, 01));
		assertFalse(piazza.checkValiditaIntervallo(intervalloSbagliato3));
		assertFalse(piazza.checkValiditaIntervallo(intervalloSbagliato4));
		
		IntervalloOrario intervalloSbagliato5 = new IntervalloOrario(LocalTime.of(00, 29), LocalTime.of(01, 00));
		IntervalloOrario intervalloSbagliato6 = new IntervalloOrario(LocalTime.of(00, 00), LocalTime.of(00, 29));
		assertFalse(piazza.checkValiditaIntervallo(intervalloSbagliato5));
		assertFalse(piazza.checkValiditaIntervallo(intervalloSbagliato6));
		
		IntervalloOrario intervalloSbagliato7 = new IntervalloOrario(LocalTime.of(00, 31), LocalTime.of(01, 00));
		IntervalloOrario intervalloSbagliato8 = new IntervalloOrario(LocalTime.of(00, 00), LocalTime.of(00, 31));
		assertFalse(piazza.checkValiditaIntervallo(intervalloSbagliato7));
		assertFalse(piazza.checkValiditaIntervallo(intervalloSbagliato8));
	}
	
	@Test
	void checkSizeWhenAddIntervallo() throws Exception {
		Piazza piazza = new Piazza();
		
		IntervalloOrario intervallo1 = new IntervalloOrario(LocalTime.of(00, 00), LocalTime.of(00, 30));

		int initialListaSize = piazza.getIntervalliOrari().size();
		piazza.aggiungiIntervalloOrario(intervallo1);
		assertTrue(piazza.getIntervalliOrari().size() == initialListaSize + 1);
	}
	
	
	
	@Test
	void canNotConvertFromStringToLocalDate() throws IOException {
		GestioneParametri gestoreParametri = new GestioneParametri();
		
		assertThrows(DateTimeException.class, () -> {
			gestoreParametri.getDataFromText(INVALIDE_DATE);
		});
	}
}