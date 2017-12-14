package fr.cfi.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;



public class TableModelCsv extends DefaultTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3375042552636018063L;
	private static Map<String,String[]> processList = null;
	private static List<String> PIDList = null;
	private static String[] columnName = new String[]{"Name","Path","CPU","Description","ID","Processname"};
	private boolean started = false;
	
	private static final int COLONNE_NAME=0;
	private static final int COLONNE_PATH=8;
	private static final int COLONNE_CPU=10;
	private static final int COLONNE_Description=13;
	private static final int COLONNE_ID=23;
	private static final int COLONNE_PROCESSNAME=49;
	

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
	public int getRowCount() {
		// TODO Auto-generated method stub
		return processList != null ? processList.size() : 0;
	}

	@Override
	public Object getValueAt(int row, int column) {
		String value = "";
		String pid = PIDList.get(row);
		if (PIDList != null && PIDList.size() > row){
			value= processList != null ? processList.get(pid)[column] : "";
		}
		return value;
	}

////////////////////////////////////////////////////////////////////////////////////////
	public static void powerShell() throws IOException{
		String command = "powershell.exe \"C:\\Users\\natha\\Documents\\test.ps1\" ";
		Process powerShellProcess = Runtime.getRuntime().exec(command);
		powerShellProcess.getOutputStream().close();
	}	
	
	private void readData() {
		if(processList == null){
			processList= new HashMap<>();
			PIDList = new ArrayList<>(); 
		}
		 List<String> newPIDList =  new ArrayList<>();
		//processList.clear();
		
		String csvFile = "C:\\Users\\natha\\Documents\\get-process_usage_cpu.csv";
		
		  FileReader monFichier = null;
		  BufferedReader tampon = null;
		  
		  try {
		  monFichier = new FileReader(csvFile);
		  tampon = new BufferedReader(monFichier);
		  int index = 0;
		  
		  while (true) {
			  String ligne = tampon.readLine();
			  if (ligne != null)
			  {
				  String[] splittedLigne = ligne.split(",");
				  index ++;
				  if(index == 1){} //Supprime premiere ligne
				  else if(index == 2){}// on ignore
				  else if(index > 2 )
				  {
					 String PID = splittedLigne[COLONNE_ID];
					  String[] colValue = new String[]{splittedLigne[COLONNE_NAME], splittedLigne[COLONNE_PATH], splittedLigne[COLONNE_CPU], splittedLigne[COLONNE_Description], PID, splittedLigne[COLONNE_PROCESSNAME]};
					 PIDList.add(PID);
					 processList.put(PID,colValue);
					 
					 newPIDList.add(PID);
					 
				  }

			  }

			  if (ligne == null)

				  break;

		  	} 
		  
		  //System.out.println("newPIDList = "+newPIDList);
		
		  if( false &&  newPIDList.size() !=PIDList.size() && !newPIDList.isEmpty()){
			  List<String> removePID = new ArrayList<>();
		  System.out.println("newPIDList = "+newPIDList);
		  System.out.println("PIDList = "+PIDList);
		  
		  for(String oldPID : PIDList){
			  
			  if(!newPIDList.contains(oldPID))
			  {
				 //System.out.println(oldPID);
				  removePID.add(oldPID);
				  
			  }
			  
		  }
		  
		  for(String pidToRemove : removePID){
			  
			  //PIDList.remove(pidToRemove);
				  processList.remove(pidToRemove);
				  
			  
			  
		  }
		  }
			  
		  this.fireTableDataChanged();
		  } 
		  catch (IOException exception) 
		  {
			  exception.printStackTrace();
		  } 
		  finally 
		  {
			  try {
				  tampon.close();
				  monFichier.close();
			  }	 
			  catch(IOException exception1) 
			  {
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
							Thread.sleep(1000);
							readData();
	
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch (IOException e) {
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
}
