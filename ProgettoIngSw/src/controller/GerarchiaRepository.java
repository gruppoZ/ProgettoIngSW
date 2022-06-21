package controller;

import java.io.IOException;
import java.util.Map;

public interface GerarchiaRepository extends Repository{
	
	public Map<?, ?> getGerarchie() throws IOException;
	public Object getGerarchiaByName(String nome) throws IOException;
	public boolean checkGerarchiaPresente(String nome) throws IOException;
	public void fineCreazioneGerarchia() throws IOException;
}
