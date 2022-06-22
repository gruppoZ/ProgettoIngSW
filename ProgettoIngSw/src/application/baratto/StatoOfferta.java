package application.baratto;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import controller.StatoTransizioniRepository;

@JsonTypeInfo(use = Id.NAME,
include = JsonTypeInfo.As.PROPERTY,
property = "type")
@JsonSubTypes({
@Type(value = OffertaAperta.class),
@Type(value = OffertaRitirata.class),
@Type(value = OffertaAccoppiata.class),
@Type(value = OffertaSelezionata.class),
@Type(value = OffertaInScambio.class),
@Type(value = OffertaChiusa.class)
})
public abstract class StatoOfferta {
	
	private StatoTransizioniRepository repoTransizioniStato;
	
	public StatoOfferta() {
		repoTransizioniStato = new StatoTransizioniRepository();
	}
	
	public abstract String getStato();
	abstract void accoppiaOfferta(Offerta offerta) throws IOException;
	abstract void apriOfferta(Offerta offerta) throws IOException;
	abstract void ritiraOfferta(Offerta offerta) throws IOException;
	abstract void chiudiOfferta(Offerta offerta) throws IOException;
	abstract void inScambio(Offerta offerta) throws IOException;
	
	/**
	 * Precondizione: offerta != null, stato != null
	 * 
	 * Permette di cambiare lo stato di un offerta, dopodichè salva il passaggio di stato nello storico
	 * @param offerta
	 * @throws IOException 
	 */
	public void gestisciCambiamentoStatoOfferta(Offerta offerta, StatoOfferta oldState) throws IOException {
		String id = offerta.getId();
		String newStateName = offerta.getStatoOfferta().getStato();
		String oldStateName = oldState.getStato();

		if(!oldStateName.equalsIgnoreCase(newStateName)) {
			PassaggioTraStati cambio = new PassaggioTraStati(oldStateName, newStateName);
			
			if(repoTransizioniStato.getStoricoMovimentazioni().containsKey(id)) {
				repoTransizioniStato.getStoricoMovimentazioni().get(id).add(cambio);
			} else {
				ArrayList<PassaggioTraStati> passaggi = new ArrayList<PassaggioTraStati>();
				passaggi.add(cambio);
				repoTransizioniStato.getStoricoMovimentazioni().put(id, passaggi);
			}
			
			repoTransizioniStato.salva();
		}
	}
	
	/**
	 * Precondizione: offerta != null, newState != null
	 * Postcondizione: offerta.getStatoOfferta() != null
	 * 
	 * @param offerta
	 * @param newState
	 */
	void changeState(Offerta offerta, StatoOfferta newState) {
		offerta.setStatoOfferta(newState);	
	}
}