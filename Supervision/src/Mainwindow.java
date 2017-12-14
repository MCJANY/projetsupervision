import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.CardLayout;
import javax.swing.JLayeredPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class Mainwindow {

	private JFrame frame;
	private JTable table;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mainwindow window = new Mainwindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Mainwindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 703, 534);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		
	
		
		
		
		Component verticalGlue = Box.createVerticalGlue();
		verticalGlue.setBounds(0, 0, 0, 0);
		frame.getContentPane().add(verticalGlue);
		
		JButton btnNewButton = new JButton("Connection");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		
		});
		btnNewButton.setBounds(27, 446, 106, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Supp.");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(149, 446, 106, 23);
		frame.getContentPane().add(btnNewButton_1, BorderLayout.SOUTH);
		
		
		
		
		
		Object[][] data = {
			      {"Cysboy", "28 ans", "1.80 m"},
			      {"BZHHydde", "28 ans", "1.80 m"},
			      {"IamBow", "24 ans", "1.90 m"},
			      {"FunMan", "32 ans", "1.85 m"}
			    };
	    String  title[] = {"Pseudo", "Age", "Taille"};
	    table_1 = new JTable(data, title);
	    
		JScrollPane jScrollPane = new JScrollPane(table_1);
	    //frame.getContentPane().add(jScrollPane,BorderLayout.CENTER);
	  //  table_1.setcolu
	   // table_1.setBounds(241, 77, 185, 156);
	    //frame.getContentPane().add(table_1);
	
		//table_1.setModel(new DefaultTableModel(data, data.length));
	}
	
	
}








