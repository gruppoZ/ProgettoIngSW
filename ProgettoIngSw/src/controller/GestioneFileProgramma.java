package controller;

import java.io.IOException;

import utils.FileDiSistema;
import utils.FileSystemOperations;
import utils.JsonIO;

public class GestioneFileProgramma {
	private static final String PATH_INFO_SISTEMA= "resources/system-info.json";
	private FileDiSistema infoSistema;
	private FileSystemOperations fs;
	
	public GestioneFileProgramma() throws IOException {
		fs = new JsonIO();
		infoSistema = (FileDiSistema) fs.leggiOggetto(PATH_INFO_SISTEMA, FileDiSistema.class);
	}

	public FileDiSistema getInfoSistema() {
		return infoSistema;
	}

	public void setInfoSistema(FileDiSistema infoSistema) {
		this.infoSistema = infoSistema;
	}
}
