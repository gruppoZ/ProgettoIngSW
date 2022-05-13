package gestioneCategorie;

import java.util.List;

import it.unibs.fp.mylib.BelleStringhe;

public class ViewCategoria {

	/**
	 * 
	 * @return la stampa della categoria con tutti i suoi attributi che la caratterizzano come tale
	 */
	public String showCategoriaDettagliata(Categoria categoria) {
		StringBuffer result = new StringBuffer();
		String nTab = BelleStringhe.ripetiChar('\t', categoria.getProfondita());
		String nTabCampi = BelleStringhe.ripetiChar('\t', categoria.getProfondita() + 1);
		
		result.append(nTab+"->Nome: "+categoria.getNome()+"  |  Descrizione: "+categoria.getDescrizione()+"\n");
		result.append(showCampi(categoria, nTab + nTabCampi));
		
		for (Categoria sotto_categoria : categoria.getSottoCategorie()) {
			result.append(nTab+"\t" + showCategoriaDettagliata(sotto_categoria)+"\n");
		}
		
		return result.toString();
	}
	
	/**
	 * 
	 * @param nTab = utilizzato per la visualizzazione, in base alla profondita', della categoria
	 * @return
	 */
	private String showCampi(Categoria categoria, String nTab) {
		List<CampoCategoria> campi = categoria.getCampiNativiEreditati();
		StringBuffer result = new StringBuffer();
		result.append(nTab + "CAMPI: \n");
		
		if(campi.size() == 0) {
			result.append(nTab + "\t" + "--- nessun Campo presente ---\n");
		} else {
			for (CampoCategoria campo_Categoria : campi) {			
				result.append(nTab + "\t" + showCampoCategoria(campo_Categoria) + "\n");
			}
		}

		result.append(nTab + "---- Fine Campi di " + categoria.getNome() + " ----\n\n");
		return result.toString();
	}
	
	private String showCampoCategoria(CampoCategoria campo) {
		StringBuffer sb = new StringBuffer();
		String msgObbligatorio = campo.isObbligatorio() ? "Obbligatorio" : "Facolatativo";
		sb.append("Campo: " + campo.getDescrizione() + " - " + msgObbligatorio);
		
		return sb.toString();
	}
}
