package fr.cfi.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;

import fr.cfi.model.ChartModel;
import fr.cfi.model.Connect_BDD;
import fr.cfi.model.IChartModelListener;
import fr.cfi.model.IConnexion;
import fr.cfi.model.TableModelCsv;

public class Tableau extends JFrame {

	private static IConnexion connexion;

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	// private JTable tableau;
	private static JButton Identification = new JButton("Identification");
	private JButton Supprimer = new JButton("Supprimer");
	private JTable tableExemple = null;
	JTabbedPane onglet = new JTabbedPane();
	// private static String[] title = new String[]{"Name", "NPM", "Path",
	// "ProductVersion", "Description", "Product", "Id", "ProcessName", "StartTime",
	// "TotalProcessorTime"};

	private static JLabel InformationsLabel = new JLabel("InformationsLabel");

	public Tableau() {
		InformationsLabel.setText("Non connecté à la base de données ..");
		boolean connected = connexion.connecBD("", "admin", "admin");
		Identification.setEnabled(connected);
		// this.setLocationRelativeTo(null);
		this.setLocation(0, 0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Supervision");
		this.setSize(1000, 600);

		// TABLEAU PROCESS
		tableExemple = new JTable();
		TableModelCsv tableModel = new TableModelCsv();
		tableExemple.setModel(tableModel);
		tableModel.startMonitoring();
		onglet.addTab("PROCESS", new JScrollPane(tableExemple));

		// String title[] = { "Nom", "Performance", "Memoire", "Disque", "Reseau" };

		JPanel pan = new JPanel();
		Identification.setEnabled(false);

		// TABLEAU RAM
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

		// Gestion du Popup
		Identification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!connexion.isLogged()) {
					Popup popup = new Popup();
					/* Hashtable<String, String> result = */popup.login(tableExemple, connexion);
				}

				if (Identification.getText().equals("Se déconnecter")) {
					connexion.unlogUser();
				}

				// Identification.setEnabled(!connexion.isLogged());
				if (connexion.isLogged()) {
					Identification.setText("Se déconnecter");
				} else {
					// Popup popup = new Popup();
					/// *Hashtable<String, String> result = */popup.login(tableExemple,connexion);
					// System.out.println(result);
					Identification.setText("Identification");
				}
				Supprimer.setEnabled(connexion.isAdmin());
			}

		});

		Supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int[] selectedRows = tableExemple.getSelectedRows();
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

				// tableau.getValueAt(selectedRows , 1);

				// for()

				// IdentificationSize(75, 16);
				Identification.setEnabled(true);
				Supprimer.setEnabled(false);
			}
		});

		Supprimer.setEnabled(false);
		pan.add(Identification);
		pan.add(Supprimer);

//		this.getContentPane().add(new JScrollPane(tableExemple), BorderLayout.CENTER);
//		this.getContentPane().add(pan, BorderLayout.SOUTH);
		//this.getContentPane().add(new JScrollPane(tableExemple), BorderLayout.WEST);
		this.getContentPane().add(onglet, BorderLayout.CENTER);
		this.getContentPane().add(pan, BorderLayout.NORTH);

		pan.add(InformationsLabel);
	}

	public static void setConnectionEstablished() {
		Identification.setEnabled(!connexion.isLogged());
	}

	public static void setInformationsLabelText(String text) {
		InformationsLabel.setText(text);
		if (text.equals("Connecté à la base de données")) {
			Identification.setEnabled(true);
		}
	}

	/**
	 * Identification la taille d'une ligne et d'une colonne J'ai mis deux boucles
	 * afin que vous puissiez voir comment parcourir les colonnes et les lignes
	 */
	/*
	 * public void IdentificationSize(int width, int height){ //Nous crï¿½ons un
	 * objet TableColumn afin de travailler sur notre colonne TableColumn col;
	 * for(int i = 0; i < tableau.getColumnCount(); i++){ if(i == 1){ //On
	 * rï¿½cupï¿½re le modï¿½le de la colonne col =
	 * tableau.getColumnModel().getColumn(i); //On lui affecte la nouvelle valeur
	 * col.setPreferredWidth(width); } } for(int i = 0; i < tableau.getRowCount();
	 * i++){ //On affecte la taille de la ligne ï¿½ l'indice spï¿½cifiï¿½ ! if(i ==
	 * 1) tableau.setRowHeight(i, height); } }
	 */
	public static void main(String[] args) {
		connexion = new Connect_BDD();
		Tableau fen = new Tableau();

		fen.setVisible(true);
	}
}