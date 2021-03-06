package fr.cfi.model;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Popup {

	public  Hashtable<String, String> login(Component frame) {
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
	 	     //Connect_BDD Connect_BDD = new Connect_BDD ();
	 	     //Connect_BDD.connectionBDD();
	    }
	 	    
	    return logininformation;
	    
	}

}