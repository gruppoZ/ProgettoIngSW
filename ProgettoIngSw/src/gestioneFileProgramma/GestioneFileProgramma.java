package gestioneFileProgramma;

import java.io.IOException;

import main.JsonIO;

public class GestioneFileProgramma {

	private static final String PATH_INDO_SISTEMA= "src/gestioneInfoDiSistema/system-info.json";
	private FileDiSistema infoSistema;
	
	public GestioneFileProgramma() throws IOException {
		infoSistema = (FileDiSistema) JsonIO.leggiOggettoDaJson(PATH_INDO_SISTEMA, FileDiSistema.class);
	}

	public FileDiSistema getInfoSistema() {
		return infoSistema;
	}

	public void setInfoSistema(FileDiSistema infoSistema) {
		this.infoSistema = infoSistema;
	}
}
