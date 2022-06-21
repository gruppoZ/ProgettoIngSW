package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import application.CampoCategoria;
import application.Categoria;
import application.baratto.Appuntamento;
import application.baratto.Articolo;
import application.baratto.Offerta;
import controller.GestioneBaratto;
import controller.GestioneOfferta;

class testGestioneScambioArticoli {

	@Test
	void compareAppointments() throws FileNotFoundException, IOException {
		GestioneBaratto gestoreBaratti = new GestioneBaratto();
		Appuntamento appuntamento1 = new Appuntamento("nord", LocalDate.now(),LocalTime.now(), "pippo");
		Appuntamento appuntamento2 = new Appuntamento("sud", LocalDate.now(),LocalTime.now(), "pippo");
	
		assertFalse(gestoreBaratti.checkUguaglianzaAppuntamenti(appuntamento1, appuntamento2));
	}
	
	@Test
	void switchOffertToClose() throws FileNotFoundException, IOException {
		CampoCategoria campo = new CampoCategoria("Stato", false);
		List<CampoCategoria> listaCampi = new ArrayList<CampoCategoria>();
		listaCampi.add(campo);
		Categoria foglia = new Categoria("foglia", "sono foglia", false, listaCampi, null);
		HashMap<String, String> valoreCampi = new HashMap<String, String>();
		valoreCampi.put(campo.getDescrizione(), "buono");
		
		Articolo articolo = new Articolo(foglia, valoreCampi);
		
		Offerta offertaA = new Offerta("1", articolo, "username1", true);
		Offerta offertaB = new Offerta("2", articolo, "username2", false);
		
		GestioneOfferta gestoreOfferte = new GestioneOfferta();
		gestoreOfferte.barattoCreato(offertaA, offertaB); //suppongo di aver creato un baratto
		
		assertTrue(gestoreOfferte.isOffertaAccoppiata(offertaA));
		assertTrue(gestoreOfferte.isOffertaSelezionata(offertaB));
		assertFalse(gestoreOfferte.isOffertaAperta(offertaA));
		assertFalse(gestoreOfferte.isOffertaAperta(offertaB));
	}
	
	@Test
	void dataScadenzaCalcolataCorrettamente() throws FileNotFoundException, IOException {
		GestioneBaratto gestoreBaratti = new GestioneBaratto();
		LocalDate dataScadenzaCalcolata = gestoreBaratti.calcolaDataScadenza(5);
		LocalDate dataScadenza = LocalDate.now().plusDays(5);
		assertTrue(dataScadenza.compareTo(dataScadenzaCalcolata) == 0);
	}
}
