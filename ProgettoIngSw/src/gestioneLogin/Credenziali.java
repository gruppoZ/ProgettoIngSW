package gestioneLogin;

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

	public boolean checkCredenzialiUguali(Credenziali credenziali) {
		return this.checkUsername(credenziali) && this.checkPassword(credenziali);
	}
	
	public boolean checkUsername(Credenziali credenziali) {
		return this.username.equals(credenziali.getUsername());
	}
	
	public boolean checkPassword(Credenziali credenziali) {
		return this.password.equals(credenziali.getPassword());
	}
	
	@Override
	public String toString() {
		return "Credenziali [username=" + username + ", password=" + password + "]";
	}	
}
