package gestioneInfoDiSistema;

import main.JsonIO;

public class GestioneInfoSistema {

	private static final String PATH_INDO_SISTEMA= "src/gestioneInfoDiSistema/system-info.json";
	private InfoDiSistema infoSistema;
	
	public GestioneInfoSistema() {
		infoSistema = (InfoDiSistema) JsonIO.leggiOggettoDaJson(PATH_INDO_SISTEMA, InfoDiSistema.class);
	}

	public InfoDiSistema getInfoSistema() {
		return infoSistema;
	}

	public void setInfoSistema(InfoDiSistema infoSistema) {
		this.infoSistema = infoSistema;
	}
}
