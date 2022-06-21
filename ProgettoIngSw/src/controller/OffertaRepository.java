package controller;

import java.io.IOException;
import java.util.List;

import application.baratto.Offerta;
import utils.FileSystemOperations;
import utils.JsonIO;

public class OffertaRepository implements Repository{

	private static final String PATH_OFFERTE = "resources/offerte.json";
	
	private List<Offerta> offerte;
	private FileSystemOperations fs;
	
	public OffertaRepository() {
		fs = new JsonIO();
	}
	
	@Override
	public void aggiorna() throws IOException {	
	}

	@Override
	public void salva() throws IOException {
		fs.salvaOggetto(PATH_OFFERTE, getItems());
	}

	@Override
	public void leggiDaFile() throws IOException {
		offerte = fs.leggiLista(PATH_OFFERTE, Offerta.class);
	}

	@Override
	public void importa(String path) throws IOException {
		// no op
	}

	@Override
	public List<?> getItems() throws IOException {
		if(this.offerte == null)
			leggiDaFile();
		
		return this.offerte;
	}
	
	public void setOfferte(List<Offerta> offerte) {
		this.offerte = offerte;
	}

}
