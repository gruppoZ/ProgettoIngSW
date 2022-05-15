package gestioneScambioArticoli;

import java.time.format.DateTimeFormatter;

public class ViewAppuntamento {
	
	private static final String FORMATO_DATA = "dd MMMM uuuu";
	private static final String DETTAGLI_APPUNTAMENTO = "Appuntamento:\nLuogo: %s \t|\tData: %s \t|\t Ora: %s\n";
	
	public void showAppuntamento(Appuntamento appuntamento) {
		System.out.printf(DETTAGLI_APPUNTAMENTO, appuntamento.getLuogo(), appuntamento.getData().format(DateTimeFormatter.ofPattern(FORMATO_DATA)), appuntamento.getOra());
	}
}
