package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import application.baratto.PassaggioTraStati;
import utils.FileSystemOperations;
import utils.JsonIO;

public class StatoTransizioniRepository implements Repository{

	private static final String PATH_STORICO_CAMBIO_STATI = "resources/storico_cambio_stati.json";
	
	private FileSystemOperations fs;	
	private Map<String, ArrayList<PassaggioTraStati>> storicoMovimentazioni;
	
	public StatoTransizioniRepository() {
		fs = new JsonIO();
	}
	
	public void setStoricoMovimentazione(Map<String, ArrayList<PassaggioTraStati>> storicoMovimentazioni) {
		this.storicoMovimentazioni = storicoMovimentazioni;
	}
	
	public  Map<String, ArrayList<PassaggioTraStati>> getStoricoMovimentazioni() throws IOException {
		if(this.storicoMovimentazioni == null)
			leggiDaFile();
		return this.storicoMovimentazioni;
	}
	
	@Override
	public void aggiorna() throws IOException {
		// no op
	}

	@Override
	public void salva() throws IOException {
		fs.salvaOggetto(PATH_STORICO_CAMBIO_STATI, storicoMovimentazioni);
	}

	@Override
	public void leggiDaFile() throws IOException {
		this.storicoMovimentazioni = fs.leggiStoricoCambioStatiOfferta(PATH_STORICO_CAMBIO_STATI);
	}

	@Override
	public void importa(String path) throws IOException {
		// no op
	}

	@Override
	public List<?> getItems() throws IOException {
		// no op
		return null;
	}
}
