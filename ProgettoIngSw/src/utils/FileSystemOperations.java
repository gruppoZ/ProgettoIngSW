package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import application.Credenziali;
import application.Gerarchia;
import application.baratto.PassaggioTraStati;

public interface FileSystemOperations {

	void salvaOggetto(String path, Object oggetto) throws IOException;
	<T> Object leggiOggetto(String path, Class<T> classe) throws IOException;
	
	Map<String, Gerarchia> leggiGerarchiehMap(String path) throws IOException, FileNotFoundException;
	Map<String, ArrayList<Credenziali>> leggiCredenzialiMap(String path) throws FileNotFoundException, IllegalArgumentException, IOException;
	Map<String, ArrayList<PassaggioTraStati>> leggiStoricoCambioStatiOfferta(String path) throws FileNotFoundException, IllegalArgumentException, IOException;
	
	<T> List<T> leggiLista(String path, Class<T> elementClass) throws IOException, FileNotFoundException;
}
