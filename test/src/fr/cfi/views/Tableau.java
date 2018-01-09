package fr.cfi.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.jdesktop.swingx.JXTable;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;

import fr.cfi.model.ChartModel;
import fr.cfi.model.IChartModelListener;
import fr.cfi.model.Popup;
import fr.cfi.model.TableModelCsv;

//CTRL + SHIFT + O pour g�n�rer les imports

public class Tableau extends JFrame {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	// private JTable tableau;
	
	
	private JButton Identification = new JButton("Identification");
	private JButton Supprimer = new JButton("Supprimer");
	private JTable tableExemple = null;
	//private static String[] title = new String[]{"Name","Path","CPU","Description","ID","Processname"};
	JTabbedPane onglet = new JTabbedPane(); 
	
	public Tableau() {
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Supervision");
		this.setSize(800, 800);

		

		
	    
		//TABLEAU PROCESS
	    tableExemple = new JXTable();
		TableModelCsv tableModel = new TableModelCsv();
		tableExemple.setModel(tableModel);
		tableModel.startMonitoring();		
		onglet.addTab("PROCESS",new JScrollPane(tableExemple));
		
		//TABLEAU RAM
		ChartExemple ex = new ChartExemple();
		ChartModel chartModel = new ChartModel(new IChartModelListener() {
			@Override
			public void dataChanged(XYDataset dataset) {
				JFreeChart chart = ex.getChart();
				XYPlot plot = chart.getXYPlot();
				plot.setDataset(dataset);
			}
		});
		chartModel.startMonitoring();
		
		
		onglet.addTab("RAM",ex);
		//onglet.setTitleAt(1,"RAM");
		onglet.addTab("USERS",new JLabel("users"));
		//onglet.setTitleAt(2,"USERS");
		onglet.addTab("SERVICES",new JLabel("services"));
		//onglet.setTitleAt(2,"SERVICES");
		//onglet.add(tableExemple, 0);
		add(onglet);
		
		
		
		//String title[] = { "Nom", "Performance", "Memoire", "Disque", "Reseau" };

		JPanel pan = new JPanel();

		// Gestion du Popup
		Identification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Popup popup = new Popup();
				Hashtable<String, String> result = popup.login(tableExemple);
				String toto1 = result.get("user");
				String toto2 = result.get("pass");
				String titi1 = "admin";
				String titi2 = "admin";
				String titi3 = "user";
				String titi4 = "user";

				// permet de valider l'ecriture de l'id et du password avec
				// l'affichage du popup
				/*
				 * if (toto1.equals(titi1) && toto2.equals(titi2)) {
				 * javax.swing.JOptionPane.showMessageDialog(
				 * null,"Oooouuuiiiiiii !!!"); }
				 * 
				 * if (toto1.equals(titi3) && toto2.equals(titi4)) {
				 * javax.swing.JOptionPane.showMessageDialog(
				 * null,"Nnnnnaaaaaaannnnn !!!"); }
				 * 
				 * if (toto1 != titi1 || toto1 != titi3 && toto2 != titi2 ||
				 * toto2 != titi4) {
				 * javax.swing.JOptionPane.showMessageDialog(null,"Error"); }
				 */

				Identification.setEnabled(false);
				Supprimer.setEnabled(true);
			}

		});

		Supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int[] selectedRows =  tableExemple.getSelectedRows();
				// int[] selectedColum = tableau.getSelectedColumns();
				System.out.println("addActionListener=" + Arrays.toString(selectedRows));

				if (selectedRows != null && selectedRows.length > 0) {
					for (int arow : selectedRows) { 
						// selectedRows[0];
						Object value = tableExemple.getValueAt(selectedRows[0], 4);
						System.out.println("tableau[" + arow + "]=" + value);
						String command = "powershell.exe Get-Process -ID " + value + " | Stop-Process ";
						System.out.println(command);
						Process powerShellProcess = null;
						try {
							powerShellProcess = Runtime.getRuntime().exec(command);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							powerShellProcess.getOutputStream().close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
				Identification.setEnabled(true);
				Supprimer.setEnabled(false);
			}
		});

		Supprimer.setEnabled(false);
		pan.add(Identification);
		pan.add(Supprimer);

		//this.getContentPane().add(new JScrollPane(tableExemple), BorderLayout.WEST);
		this.getContentPane().add(onglet, BorderLayout.CENTER);
		this.getContentPane().add(pan, BorderLayout.NORTH);
		//this.getContentPane().add(name, comp)
		
	}
	

	/**
	 * Identification la taille d'une ligne et d'une colonne J'ai mis deux
	 * boucles afin que vous puissiez voir comment parcourir les colonnes et les
	 * lignes
	 */
	/*
	 * public void IdentificationSize(int width, int height){ //Nous cr�ons un
	 * objet TableColumn afin de travailler sur notre colonne TableColumn col;
	 * for(int i = 0; i < tableau.getColumnCount(); i++){ if(i == 1){ //On
	 * r�cup�re le mod�le de la colonne col =
	 * tableau.getColumnModel().getColumn(i); //On lui affecte la nouvelle
	 * valeur col.setPreferredWidth(width); } } for(int i = 0; i <
	 * tableau.getRowCount(); i++){ //On affecte la taille de la ligne �
	 * l'indice sp�cifi� ! if(i == 1) tableau.setRowHeight(i, height); } }
	 */
	public static void main(String[] args) {
		
		
		Tableau fen = new Tableau();
		fen.setVisible(true);
	}
}