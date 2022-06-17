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
		
		Credenziali credenziali = new Credenziali(username, password);
		GestioneAutenticazione gestoreAuth = new GestioneAutenticazione();
		
		assertFalse(gestoreAuth.login(new Fruitore(), credenziali));
		assertFalse(gestoreAuth.login(new Configuratore(), credenziali));
	}

	@Test
	void canObtainDefaultCredentialsFromFile() throws IOException {	
		GestioneAutenticazione gestoreAuth = new GestioneAutenticazione();
		Credenziali credenzialiDefault = gestoreAuth.getCredenzialiDefault();
		Credenziali credenziali = new Credenziali("root", "root");
		
		assertTrue(gestoreAuth.checkCredenzialiPrimoAccesso(credenzialiDefault));
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
