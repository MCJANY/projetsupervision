package fr.cfi.model;

public interface IConnexion {

	public boolean connecBD(String host, String user, String password);
	
	public boolean login(String user, String password);
	
	public void unlogUser();
	
	/**
	 * check if admin see @IConnexion
	 */
	public boolean isAdmin();
	
	public boolean isLogged();
	

}
