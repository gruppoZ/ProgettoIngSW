package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import gestioneCategorie.Gerarchia;
import gestioneLogin.Credenziali;

/**
 * La libreria JACKSON prende i dati da salvare di un oggetto attraverso i metodi get
 * per questo motivo ad alcuni metodi get e' stato messo un "_" che precede get
 * @author 
 *
 */
public class JsonIO {
	
	/**
	 * Salva una List di tipo T in un file di formato JSON
	 * @param <K>
	 * @param <V>
	 * @param path
	 * @param hashMap
	 */
	public static <K, V> void salvaHashMapSuJson(String path, HashMap<K, V> hashMap) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		try { 
			
			writer.writeValue(Paths.get(path).toFile(), hashMap);
			
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Salva una List di tipo T in un file di formato JSON
	 * @param <T>
	 * @param path
	 * @param lista
	 */
	public static <T> void salvaListSuJson(String path, List<T> lista) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		try { 
			writer.writeValue(Paths.get(path).toFile(), lista);
			
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	/**
	 * Legge da un file le gerarchie scritte in formato JSON e le salva in una HashMap
	 * @param path
	 * @return
	 */
	public static HashMap<String, Gerarchia> leggiGerarchieDaJson(String path) {
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Gerarchia> result = null;
		try { 
			result = mapper.readValue(new File(path), new TypeReference<HashMap<String, Gerarchia>>() {});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Legge da un file le credenziali scritte in formato JSON e le salva in un ArrayList
	 * @param path
	 * @return ArrayList di credenziali
	 */
	public static ArrayList<Credenziali> leggiCredenzialiDaJson (String path) {
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<Credenziali> listaCredenziali = null;
		try {
			//Viene usato ArrayList perche' List da errori nel casting/utilizzo di alcuni metodi
			listaCredenziali = new ArrayList<>(Arrays.asList(mapper.readValue(new File(path), Credenziali[].class)));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listaCredenziali;
	}
}
