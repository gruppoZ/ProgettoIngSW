package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import application.baratto.Baratto;
import utils.FileSystemOperations;
import utils.JsonIO;

public class BarattoRepository implements Repository{
	private static final String PATH_BARATTI = "resources/baratti.json";
	private static final String PATH_BARATTI_TERMINATI = "resources/barattiTerminati.json";
	
	private FileSystemOperations fs;	
	private List<Baratto> baratti;
	private List<Baratto> barattiTerminati;
	
	public BarattoRepository() {
		fs = new JsonIO();
	}
	
	@Override
	public void aggiorna() throws IOException {
		// no op
	}

	@Override
	public void salva() throws IOException {
		fs.salvaOggetto(PATH_BARATTI, getItems());
	}

	@Override
	public void leggiDaFile() throws IOException {
		baratti = fs.leggiLista(PATH_BARATTI, Baratto.class);		
	}

	@Override
	public void importa(String path) throws IOException {
		// no op
	}
	
	@Override
	public List<Baratto> getItems() throws IOException {
		if(this.baratti == null)
			leggiDaFile();
		
		return this.baratti;
	}
	
	public void setBaratti(List<Baratto> baratti) {
		this.baratti = baratti;
	}
	
	public void setBarattiTerminati(List<Baratto> barattiTerminati) {
		this.barattiTerminati = barattiTerminati;
	}
	
	public List<Baratto> getBarattiTerminati() throws FileNotFoundException, IOException {
		if(this.barattiTerminati == null)
			leggiBarattiTerminati();
		
		return this.barattiTerminati;
	}
	
	public void salvaBarattiTerminati() throws IOException {
		fs.salvaOggetto(PATH_BARATTI_TERMINATI, getBarattiTerminati());
	}
	
	public void leggiBarattiTerminati() throws FileNotFoundException, IOException {
		barattiTerminati = fs.leggiLista(PATH_BARATTI_TERMINATI, Baratto.class);
	}
}
