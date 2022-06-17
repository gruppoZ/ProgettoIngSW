package application;

import java.util.ArrayList;
import java.util.List;

public enum GiorniDellaSettimana {
	LUNEDI(0, "Lunedì"),
	MARTEDI(1, "Martedì"),
	MERCOLEDI(2, "Mercoledì"),
	GIOVEDI(3, "Giovedì"),
	VENERDI(4, "Venerdì"),
	SABATO(5, "Sabato"),
	DOMENICA(6, "Domenica");
	
	private int ordine;
	private String nome;
	
	GiorniDellaSettimana(int ordine, String nome) {
		this.ordine = ordine;
		this.nome = nome;
	}

	public int getOrdine() {
		return ordine;
	}
	
	public String getNome() {
		return nome;
	}
	
	/**
	 * Precondizione: daOrdinare != null
	 * Postcondizione: ordinata.size() = daOrdinare.size()
	 * @param daOrdinare
	 * @return
	 */
	public static List<GiorniDellaSettimana> ordinaLista(List<GiorniDellaSettimana> daOrdinare) {
		List<GiorniDellaSettimana> ordinata = new ArrayList<>();
		
		for (GiorniDellaSettimana giorno : values()) {
			if(daOrdinare.contains(giorno))
				ordinata.add(giorno);
		}
		
		return ordinata;
	}
	
	/**
	 * Precondizione: id > = 0 AND id < = 6
	 * 
	 * @param id
	 * @return
	 * @throws NullPointerException
	 */
	public static GiorniDellaSettimana getById(int id) throws NullPointerException {
		for (GiorniDellaSettimana giorno : values()) {
			if(giorno.ordine == id) return giorno;
		}
		return null;
	}
}
