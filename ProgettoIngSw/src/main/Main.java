package main;

import java.io.IOException;

public class Main {

	//versione 5
	public static void main(String[] args) {		
		SchermataPrincipale mainView = new SchermataPrincipale();
		
		try {
			mainView.init();
		} catch (IOException e) {
			System.out.println("\n" + e.getMessage());
		}
	}
}
