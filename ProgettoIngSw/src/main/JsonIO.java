package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import gestioneCategorie.Gerarchia;
import gestioneLogin.Credenziali;
import gestioneOfferte.PassaggioTraStati;
import gestioneParametri.Piazza;

/**
 * La libreria JACKSON prende i dati da salvare di un oggetto attraverso i metodi get
 * Es: getNome() => JACKSON sa che deve salvare sul file un attributo "nome"
 * per questo motivo alcuni metodi sono preceduti da @JsonIgnore in modo tale che JACKSON ignori il metodo nonostante
 * inizi con get
 * @author 
 *
 */
public class JsonIO {
	private static ObjectMapper mapper = JsonMapper.builder()
	        .findAndAddModules()
	        .build();
	private static ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
	private static TypeFactory typeFactory = mapper.getTypeFactory();
	
	/**
	 * 
	 * @param path
	 * @param oggetto
	 */
	public static void salvaOggettoSuJson(String path, Object oggetto) {
		try { 
			writer.writeValue(Paths.get(path).toFile(), oggetto);
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
		HashMap<String, Gerarchia> result = null;
		try { 
			result = mapper.readValue(new File(path), new TypeReference<HashMap<String, Gerarchia>>() {});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static HashMap<String, ArrayList<Credenziali>> leggiCredenzialiHashMapDaJson(String path) {
		return JsonIO.leggiHashMapDaJson(path, typeFactory.constructFromCanonical(String.class.getName()),
				typeFactory.constructCollectionType(ArrayList.class, Credenziali.class));
	}
	
	public static HashMap<Integer, ArrayList<PassaggioTraStati>> leggiStoricoCambioStatiOffertaDaJson(String path) {
		return JsonIO.leggiHashMapDaJson(path, typeFactory.constructFromCanonical(Integer.class.getName()),
				typeFactory.constructCollectionType(ArrayList.class, PassaggioTraStati.class));
	}
	
	/**
	 * lettura di una hashmap generica attraverso i javatype
	 * @param <K>
	 * @param <V>
	 * @param path
	 * @param elementClassKey
	 * @param elementClassValue
	 * @return
	 */
	private static <K, V> HashMap<K, V> leggiHashMapDaJson(String path, JavaType elementClassKey, JavaType elementClassValue) {
		HashMap<K, V> mappa = null;
		
		MapType listType;
		listType  = typeFactory.constructMapType(HashMap.class, elementClassKey, elementClassValue);	
		try {
			mappa = mapper.readValue(new File(path), listType);		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mappa;
	}
		
	public static <T> List<T> leggiListaDaJson(String path, Class<T> elementClass) {
		List<T> lista = null;
		CollectionType listType ;
		listType  = typeFactory.constructCollectionType(ArrayList.class, elementClass);	
		try {
			lista = mapper.readValue(new File(path), listType);		
		} catch (Exception e) {
            e.printStackTrace();
        }
		
		return lista;
	}
	
	public static Piazza leggiPiazzaDaJson(String path) {
		Piazza piazza = null;
		try {
			piazza = mapper.readValue(new File(path), Piazza.class);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return piazza;
	}
	
	public static <T> Object leggiOggettoDaJson(String path, Class<T> classe) {
		try {
			return mapper.readValue(new File(path), classe);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
