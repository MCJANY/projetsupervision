package fr.cfi.views;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import fr.cfi.model.IConnexion;

public class Popup {


	public  Hashtable<String, String> login(Component frame,final IConnexion connexion) {

		Hashtable<String, String> logininformation = null;
	    JPanel panel = new JPanel(new BorderLayout(5, 5));
	
	    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
	    label.add(new JLabel("User", SwingConstants.RIGHT));
	    label.add(new JLabel("Password", SwingConstants.RIGHT));
	    panel.add(label, BorderLayout.WEST);
	
	    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
	    JTextField username = new JTextField();
	    controls.add(username);
	    JPasswordField password = new JPasswordField();
	    
	    controls.add(password);
	    panel.add(controls, BorderLayout.CENTER);
	
	    int showConfirmDialog = JOptionPane.showConfirmDialog(
	            frame, panel, "login", JOptionPane.OK_CANCEL_OPTION);
	    
	    if(showConfirmDialog == JOptionPane.OK_OPTION) 
	    {
	    	 logininformation = new Hashtable<String, String>();
	    	 logininformation.put("user", username.getText());
	 	     logininformation.put("pass", new String(password.getPassword()));
	 	   
	 	     //connexion.connecBD("", username.getText(), new String(password.getPassword()));
	 	     connexion.login(username.getText(), new String(password.getPassword()));
	    }
	 	    
	    return logininformation;
	    
	}

}