package view;

import java.time.format.DateTimeFormatter;

import application.Appuntamento;

public class ViewAppuntamento {
	
	private static final String FORMATO_DATA = "dd MMMM uuuu";
	private static final String MSG_APPUNTAMENTO_NON_DISPONIBILE = "\n*** Appuntamento ancora da fissare ***\n";
	private static final String DETTAGLI_APPUNTAMENTO = "->Appuntamento:\n\tLuogo: %s \t|\tData: %s \t|\t Ora: %s\n";
	
	/**
	 * Precondizione: appuntamento != null
	 * 
	 * @param appuntamento
	 */
	public void showAppuntamento(Appuntamento appuntamento) {
		try {
			System.out.printf(DETTAGLI_APPUNTAMENTO, appuntamento.getLuogo(), appuntamento.getData().format(DateTimeFormatter.ofPattern(FORMATO_DATA)), appuntamento.getOra());
		} catch (NullPointerException e) {
			System.out.println(MSG_APPUNTAMENTO_NON_DISPONIBILE);
		}
	}
}
