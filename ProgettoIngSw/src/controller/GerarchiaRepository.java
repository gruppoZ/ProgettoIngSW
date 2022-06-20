package controller;

import java.io.IOException;
import java.util.Map;

import application.Gerarchia;

public interface GerarchiaRepository extends Repository{
	
	public Map<?, ?> getItems() throws IOException;
	public Object getItemByName(String nome) throws IOException;
	public boolean checkItemPresente(String nome) throws IOException;
	public void fineCreazioneGerarchia(Gerarchia gerarchiaDaSalvare) throws IOException;
}
