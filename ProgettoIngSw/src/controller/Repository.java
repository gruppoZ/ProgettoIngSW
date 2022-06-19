package controller;

import java.io.IOException;

public interface Repository {
	public void aggiorna() throws IOException;
	public void salva() throws IOException;
	public void leggiDaFile() throws IOException;
	public void importa(String path) throws IOException;
}
