package fr.cfi.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

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
import fr.cfi.model.EmailSender;
import fr.cfi.model.IChartModelListener;
import fr.cfi.model.IConnexion;
import fr.cfi.model.TableModel;
import java.awt.Component;
import javax.swing.Box;

public class MainWindow extends JFrame {

	private static IConnexion connexion;

	FileInputStream propFile;
	
	
	private static final long serialVersionUID = 1L;
	// private JTable tableau;
	private static JButton Identification = new JButton("Login");
	private JButton Supprimer = new JButton("Kill");
	private JTable tableExemple = null;
	JTabbedPane onglet = new JTabbedPane();

	private static JLabel InformationsLabel = new JLabel("InformationsLabel");
	private final JButton btnPause = new JButton("Pause");

	public MainWindow() {
		
		// Chargement fichier config
		try {
			String fichier = "./log/Configuration.txt";
			FileInputStream propFile = new FileInputStream( fichier);
		
			Properties p =new Properties(System.getProperties());
			p.load(propFile);
			System.setProperties(p);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		InformationsLabel.setText("database disconnected");
		boolean connected = connexion.connecBD("", "admin", "admin");
		Identification.setEnabled(connected);
		// this.setLocationRelativeTo(null);
		this.setLocation(0, 0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Supervision");
		this.setSize(1000, 600);

		// TABLEAU PROCESS
		tableExemple = new JTable();
		TableModel tableModel = new TableModel();
		tableExemple.setModel(tableModel);
		tableModel.startMonitoring();
		JScrollPane scrollPane = new JScrollPane(tableExemple);
		onglet.setBounds(0, 33, 984, 528);
		onglet.addTab("PROCESS", scrollPane);
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		scrollPane.setRowHeaderView(btnPause);

		JPanel pan = new JPanel();
		pan.setBounds(0, 0, 984, 33);
		Identification.setEnabled(false);

		// TABLEAU RAM
		Chart ex = new Chart();
		ChartModel chartModel = new ChartModel(new IChartModelListener() {
			@Override
			public void dataChanged(XYDataset dataset) {
				JFreeChart chart = ex.getChart();
				XYPlot plot = chart.getXYPlot();
				plot.setDataset(dataset);
			}
		});
		chartModel.startMonitoring();
		getContentPane().setLayout(null);
		
		onglet.addTab("CPU USAGE",ex);
		//onglet.setTitleAt(1,"RAM");
		onglet.addTab("USERS",new JLabel("users"));
		//onglet.setTitleAt(2,"USERS");
		onglet.addTab("SERVICES",new JLabel("services"));
		//onglet.setTitleAt(2,"SERVICES");
		//onglet.add(tableExemple, 0);
		getContentPane().add(onglet);

		// Gestion du Popup
		Identification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!connexion.isLogged()) {
					Popup popup = new Popup();
					/* Hashtable<String, String> result = */popup.login(tableExemple, connexion);
				}

				if (Identification.getText().equals("Logout")) {
					connexion.unlogUser();
				}

				// Identification.setEnabled(!connexion.isLogged());
				if (connexion.isLogged()) {
					Identification.setText("Logout");
				} else {
					// Popup popup = new Popup();
					/// *Hashtable<String, String> result = */popup.login(tableExemple,connexion);
					// System.out.println(result);
					Identification.setText("Login");
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
		this.getContentPane().add(pan);

		pan.add(InformationsLabel);
	}

	public static void setConnectionEstablished() {
		Identification.setEnabled(!connexion.isLogged());
	}

	public static void setInformationsLabelText(String text) {
		InformationsLabel.setText(text);
		if (text.equals("Connect� � la base de donn�es")) {
			Identification.setEnabled(true);
		}
	}

	/**
	 * Identification la taille d'une ligne et d'une colonne J'ai mis deux boucles
	 * afin que vous puissiez voir comment parcourir les colonnes et les lignes
	 */
	/*
	 * public void IdentificationSize(int width, int height){ //Nous cr�ons un
	 * objet TableColumn afin de travailler sur notre colonne TableColumn col;
	 * for(int i = 0; i < tableau.getColumnCount(); i++){ if(i == 1){ //On
	 * r�cup�re le mod�le de la colonne col =
	 * tableau.getColumnModel().getColumn(i); //On lui affecte la nouvelle valeur
	 * col.setPreferredWidth(width); } } for(int i = 0; i < tableau.getRowCount();
	 * i++){ //On affecte la taille de la ligne � l'indice sp�cifi� ! if(i ==
	 * 1) tableau.setRowHeight(i, height); } }
	 */
	public static void main(String[] args) {
		connexion = new Connect_BDD();
		MainWindow fen = new MainWindow();

		fen.setVisible(true);
	}
}