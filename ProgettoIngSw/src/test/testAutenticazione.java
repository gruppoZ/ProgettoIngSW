package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import application.Configuratore;
import application.Credenziali;
import application.Fruitore;
import controller.GestioneAutenticazione;

class testAutenticazione {

	@Test
	void canNotLoginNotRegisteredUser() throws FileNotFoundException, IllegalArgumentException, IOException {
		String username = "Pippo";
		String password = "Pluto";
		
		GestioneAutenticazione gestoreAuth = new GestioneAutenticazione();
		
		assertFalse(gestoreAuth.login(new Fruitore(), username, password));
		assertFalse(gestoreAuth.login(new Configuratore(), username, password));
	}

	@Test
	void canObtainDefaultCredentialsFromFile() throws IOException {	
		GestioneAutenticazione gestoreAuth = new GestioneAutenticazione();
		Credenziali credenzialiDefault = gestoreAuth.getCredenzialiDefault();
		String username = "root";
		String password = "root";
		Credenziali credenziali = new Credenziali(username, password);
		
		assertTrue(gestoreAuth.checkCredenzialiPrimoAccesso(username, password));
		assertTrue(credenziali.checkCredenzialiUguali(credenzialiDefault));
	}
	
	@Test
	void canCompareCredentials() {
		Credenziali credenzialiPippo = new Credenziali("Pippo", "Pippo");
		Credenziali credenzialiPluto = new Credenziali("Pluto", "Pluto");
		
		assertFalse(credenzialiPippo.checkCredenzialiUguali(credenzialiPluto));
		assertTrue(credenzialiPippo.checkCredenzialiUguali(credenzialiPippo));
	}
}
