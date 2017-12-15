import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.*;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Connect_BDD extends JFrame 
{

	//CTRL + SHIFT + O pour générer les imports

	public void connectionBDD(){
		Connection conn = null;
		Statement st = null;
	      //conn = DriverManager.getConnection(url, user, passwd);
	  //javax.swing.JOptionPane.showMessageDialog(null,"Oooouuuiiiiiii !!!");
	
	  //System.out.println("Connexion effective !");         
	
	  try{
		  Class.forName("com.mysql.jdbc.Driver");
		  System.out.println("Driver O.K.");
		  //désactiver le pare-feu du destinataire de l'adresse IP
		  String url = "jdbc:mysql://192.168.200.25:3306/projet";
		  String user = "phpmyadmin";
		  String passwd = "admin";
		  
		  //Class.forName("org.postgresql.Driver");
		  conn = DriverManager.getConnection(url, user, passwd);
		  //st = conn.createStatement();
		  //String sql = "SELECT * FROM log";
				    			  
		  /*st.executeUpdate(sql);
	  } catch (SQLException e){
		  e.printStackTrace();
	  }catch (ClassNotFoundException e){
		  e.printStackTrace();
	  } catch (Exception e){
			e.printStackTrace();
	  }finally{
		  try{
			  conn.close();
			  st.close();
		  }catch (SQLException e){
			  e.printStackTrace();
		  }*/
		  System.out.println("Connexion effective !");         
	         
	    } catch (Exception e){
	      e.printStackTrace();
	    } 
  }
}



