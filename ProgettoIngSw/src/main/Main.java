package main;

import java.io.IOException;

/**
 * versione 5
 * @author Giorgio Bonardi, Youssef El fadi, Raul Iosif Cocis
 * <br><p>
 * Progetto per il corso di "Ingegneria del Software" della prof.ssa Marina Zanella e del prof. Alessandro Saetti 
 * <br>2021/2022
 * </p>
 */
public class Main {

	public static void main(String[] args) {		
		SchermataPrincipale mainView = new SchermataPrincipale();
		
		try {
			mainView.init();
		} catch (IOException e) {
			System.out.println("\n" + e.getMessage());
		}
	}
}
