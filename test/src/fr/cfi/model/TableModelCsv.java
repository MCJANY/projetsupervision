package fr.cfi.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.table.DefaultTableModel;

public class TableModelCsv extends DefaultTableModel implements MyInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3375042552636018063L;
	private static Map<String, String[]> processList = null;

	private static List<String> cpuList = null;

	private static String[] columnName = new String[] { "Name", "Path", "CPU", "Description", "ID", "Processname" };
	private boolean started = false;

	private static final int COLONNE_NAME = 0;
	private static final int COLONNE_PATH = 7;
	private static final int COLONNE_CPU = 9;
	private static final int COLONNE_Description = 12;
	private static final int COLONNE_ID = 22;
	private static final int COLONNE_PROCESSNAME = 47;

	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnName.length;
	}

	@Override
	public boolean isCellEditable(int row, int column) {

		return false;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return processList != null ? processList.size() : 0;
	}



	public Object getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public int[] getSelectedRows() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		String value = "";
		Set<String> keySet = processList.keySet();
		if (keySet != null && keySet.size() > row) {
			Object[] array = keySet.toArray();
			String pid = array[row].toString();
			value = processList != null && processList.containsKey(pid) ? processList.get(pid)[column] : "";
		}
		return value;
	}
	////////////////////////////////////////////////////////////////////////////////////////
	public void powerShell() throws IOException {
		String command = "powershell \" C:\\Users\\natha\\Documents\\test2.ps1\" ";
		Process powerShellProcess = Runtime.getRuntime().exec(command);
		powerShellProcess.getOutputStream().close();

	}

	public void readData() {

		if (processList == null) {
			processList = new HashMap<>();
			cpuList = new ArrayList<>();
		}

		List<String> newPIDList = new ArrayList<>();
		

		String csvFile = "C:\\Users\\natha\\Documents\\get-process_usage_cpu.csv";
		FileReader monFichier = null;
		BufferedReader tampon = null;
		try {
			monFichier = new FileReader(csvFile);
			tampon = new BufferedReader(monFichier);
			int index = 0;
			while (true) {
				String ligne = tampon.readLine();
				if (ligne != null) {
					System.out.println(ligne);
					ligne = ligne.replaceAll("\"", "");
					String[] splittedLigne = ligne.split(";");
					index++;
					if (index == 2) {
					} // on ignore
					else if (index > 2) {
						String PID = splittedLigne[COLONNE_ID];
						String CPU = splittedLigne[COLONNE_CPU];
						String[] colValue = new String[] { splittedLigne[COLONNE_NAME], splittedLigne[COLONNE_PATH],
								splittedLigne[COLONNE_CPU], splittedLigne[COLONNE_Description], PID,
								splittedLigne[COLONNE_PROCESSNAME] };
						processList.put(PID, colValue);
						newPIDList.add(PID);
						cpuList.add(CPU);
					}
				}
				if (ligne == null)
					break;
			}

			// System.out.println("CPU = " + cpuList);

			if (newPIDList.size() != processList.size() && !processList.isEmpty()) {
				List<String> removePID = new ArrayList<>();
				Set<String> PIDList = processList.keySet();
				for (String oldPID : PIDList) {
					if (!newPIDList.contains(oldPID)) {
						removePID.add(oldPID);
					}
				}

				for (String pidToRemove : removePID) {
					processList.remove(pidToRemove);
				}
			}

			this.fireTableDataChanged();
		} catch (IOException exception) {
			exception.printStackTrace();
		} finally {
			try {
				monFichier.close();
				tampon.close();
			} catch (IOException exception1) {
				exception1.printStackTrace();
			}
		}
	}

	public void startMonitoring() {
		if (!started) {
			started = true;
			Thread thread = new Thread() {
				public void run() {
					while (started) {

						try {
							powerShell();
							Thread.sleep(200);
							readData();
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
			};
			thread.start();
		}
	}

	public void stopMonitoring() {
		if (started) {
			started = false;
		}
	}

	/*
	 * public static void main(String[] args) { powerShell();
	 * 
	 * }
	 */

}
