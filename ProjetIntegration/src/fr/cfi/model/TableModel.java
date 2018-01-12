package fr.cfi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {
	/**
	 * 
	 */
	private static IProcessRetriever processRet = null;
	private static final long serialVersionUID = -3375042552636018063L;
	private static Map<String, String[]> processList = null;
	private static List<String> pidList = null;
	
	
	//private static String[] columnName = new String[] { "Name", "Path", "CPU", "Description", "ID", "Processname" };
	private boolean started = false;

	public TableModel () {
		initProcessRetriever();
	}

	@Override
	public String getColumnName(int column) {
		return processRet.getColonnes()[column];
	}

	@Override
	public int getColumnCount() {
		String[] tabColonne = processRet.getColonnes();
		int nbColonne = tabColonne.length;
		return nbColonne;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		int ret = processList != null ? processList.size() : 0;
		return ret;
	}

	@Override
	public Object getValueAt(int row, int column) {
		Object value = "";
		if(pidList != null && pidList.size() > row && !pidList.isEmpty() ) {
			String keyPid = pidList.get(row);
			String[] values = processList.get(keyPid);
			if(values != null && values.length > column) {
				value = values[column];
			}
		
		}
		return value;
	}

//	public Object getTable() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public int[] getSelectedRows() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	private void initProcessRetriever() {
		if(processRet == null) {
			String property = System.getProperty("os.name");
			System.out.println("os.name="+ property);
			if(property.toLowerCase().contains("windows")) {
				processRet = new WindowsProcessRetriever();
			}
			else {
				processRet = new LinuxProcessRetriever();
			}
		}
	}

	public void updateValues() {
		initProcessRetriever() ;
		
		if (processList == null) {
			processList = new HashMap<>();
			//cpuList = new ArrayList<>();
			pidList = new ArrayList<>();
		}


		List<String> newPIDList = new ArrayList<>();
		List<String[]> sortedProcessList = processRet.sortProcess();
		/*for(String[] komtuve : sortedProcessList) {
			System.out.println(Arrays.toString(komtuve));
		}*/
		String pid = null;
		for (String[] values : sortedProcessList) {
			pid = values[processRet.getPidIndex()];
			//System.out.println("Values= PID = " + pid + " => "+ Arrays.toString(values));
			processList.put(pid, values);
			if(!pidList.contains(pid)) {
				pidList.add(pid);
			}
			newPIDList.add(pid);
		}
		//System.out.println(processList);
		//cpuList.add(CPU);
		//		if (ligne == null)
		//			break;

		//System.out.println("CPU = " + cpuList);

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
	}

	public void startMonitoring() {
		if (!started) {
			started = true;
			Thread thread = new Thread() 
			{
				public void run() {
					while (started) {
						try{
							initProcessRetriever() ;
							processRet.sortProcess();
							updateValues();
							Thread.sleep(1000);
						} catch (InterruptedException e) {
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
	
	public boolean isStarted() {
		return started;
	}
}
