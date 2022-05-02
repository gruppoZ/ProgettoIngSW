package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import gestioneCategorie.CampoCategoria;
import gestioneCategorie.Categoria;
import gestioneCategorie.GestioneGerarchie;

class testGestioneGerarchia {

	GestioneGerarchie gestoreGerarchia;
	
	@Test
	void test() {
		gestoreGerarchia = new GestioneGerarchie();
		
		List<CampoCategoria> campiNativiDefault = new ArrayList<>();
		gestoreGerarchia.addCampiDefaultRoot(campiNativiDefault);
		
		Categoria root = new Categoria("root_6", "e' la radice6", true, campiNativiDefault, new ArrayList<>());
		
		gestoreGerarchia.creaRoot(root);
		Categoria categoriaDaRamificare = root;
		
		for(int i=0; i<3; i++) {
            System.out.println("Sottocategoria#" + (i+1));
            Categoria result = creaCategoria(categoriaDaRamificare, "f#", i);
            categoriaDaRamificare.addSottoCategoria(result);
            
        }
		
		categoriaDaRamificare = gestoreGerarchia.getCategoriaByName("f#0");
		
		for(int i=0; i<2; i++) {
            System.out.println("Sottocategoria#" + (i+1));
            Categoria result = creaCategoria(categoriaDaRamificare, "n#", i);
            categoriaDaRamificare.addSottoCategoria(result);
            
        }
		
		System.out.println(gestoreGerarchia.showGerarchia());
		
		gestoreGerarchia.eliminaCategoria("f#0");
		System.out.println("\n eliminato f#0 \n");
		System.out.println(gestoreGerarchia.showGerarchia());
	}
	
	Categoria creaCategoria(Categoria padre, String id,int i) {
		Categoria result = new Categoria(id + i, "desc_f#" + i, false, new ArrayList<>(), padre.getCampiNativiEreditati());
		gestoreGerarchia.creaSottoCategoria(padre, result);
		return result;
	}

}
