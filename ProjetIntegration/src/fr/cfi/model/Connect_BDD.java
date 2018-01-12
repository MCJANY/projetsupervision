package fr.cfi.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import fr.cfi.views.MainWindow;

public class Connect_BDD  implements IConnexion
{

	private static boolean connected = false;
	private static boolean logged = false;
	private static boolean admin = false;
	
	private static Connection conn = null;
	//CTRL + SHIFT + O pour g�n�rer les imports

	public boolean connectionBDD(String host, String user, String passwd ){
		connected = false;

		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver O.K.");
			//d�sactiver le pare-feu du destinataire de l'adresse IP

			//String hostTmp = host != null && !host.isEmpty() ? host : "192.168.30.41";
			String hostTmp = host != null && !host.isEmpty() ? host : "192.168.30.56";
			String url = "jdbc:mysql://"+hostTmp+":3306/projet";
			conn = DriverManager.getConnection(url, user, passwd);

			System.out.println("Connection succeed !");     
			connected = true;
			MainWindow.setConnectionEstablished();

		} catch (Exception e){
			e.printStackTrace();
		} 
	  return connected;
  }
	
	@Override
	public boolean login(String user, String password ){
		logged = false;
//		String hostTmp = host != null &&!host.isEmpty() ? host : "192.168.30.41";
//		String url = "jdbc:mysql://"+hostTmp+":3306/projet";
//		conn = DriverManager.getConnection(url, user, passwd);
		Statement st = null;
		try {
			if(conn != null && !conn.isClosed()) {
				st = conn.createStatement();
				String sql = "SELECT * FROM log WHERE User = '" + user + "' ";
				ResultSet resQuery = st.executeQuery(sql);
				if(resQuery.next()){
					if(resQuery.getString("Mdp").equals(password)) {
						logged = true;
						admin = resQuery.getString("Status").equals("admin");
						MainWindow.setInformationsLabelText((admin ? "Admin " : "User ") + "identified");
					}else {
						MainWindow.setInformationsLabelText("Error : Wrong password !");
					}
				}else{
					MainWindow.setInformationsLabelText("Error : Wrong username !");
				}
			}
			
		}catch (Exception e) {
			MainWindow.setInformationsLabelText("Sql Err login");
		}
		return logged;
	}

	@Override
	public boolean connecBD(String host, String user, String password) {
		Thread thread = new Thread("Connection Thread") {
			public void run() {
				connectionBDD( host,  user,  password );
				MainWindow.setInformationsLabelText("Connected to database");
			};
		};
		
		thread.start();
		return false;
	
	}
	
	@Override
	public void unlogUser(){
		admin = false;
		logged = false;
		MainWindow.setInformationsLabelText("User disconnected");
	}


		
	/**
	 * check if admin see @IConnexion
	 */
	@Override 
	public boolean isAdmin() {	
		return admin;
	}

	@Override
	public boolean isLogged() {
		return logged;
	}
}



