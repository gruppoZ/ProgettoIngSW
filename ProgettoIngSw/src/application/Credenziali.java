package application;

public class Credenziali {

	private String username;
	private String password;
	
	public Credenziali() {
	}
	
	public Credenziali(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Precondizioni: credenziali != null
	 * @param credenziali
	 * @return
	 */
	public boolean checkCredenzialiUguali(Credenziali credenziali) {
		return this.checkUsername(credenziali) && this.checkPassword(credenziali);
	}
	
	/**
	 * Precondizioni: credenziali != null
	 * @param credenziali
	 * @return
	 */
	public boolean checkUsername(Credenziali credenziali) {
		return this.username.equals(credenziali.getUsername());
	}
	
	/**
	 * Precondizioni: credenziali != null
	 * @param credenziali
	 * @return
	 */
	public boolean checkPassword(Credenziali credenziali) {
		return this.password.equals(credenziali.getPassword());
	}
}
