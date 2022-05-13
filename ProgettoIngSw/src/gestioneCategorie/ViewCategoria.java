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
		
		result.append(nTab+"->Nome: "+categoria.getNome()+"  |  Descrizione: "+categoria.getDescrizione()+"\n");
		result.append(showCampi(categoria, nTab));
		
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
				result.append(nTab + "\t" + campo_Categoria.toString() + "\n");
			}
		}

		result.append(nTab + "---- Fine Campi di " + categoria.getNome() + " ----\n\n");
		return result.toString();
	}
}
