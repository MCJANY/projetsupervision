package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

 	
public class test1 {
	
private static List<String[]> processList = null;
private static String[] columnName = new String[]{"Name","Path","Description","ID","Processname","CPU(s)", "Visible Processus Memory Usage(Ko)","Unvisible Processus Memory usage(Ko)", "Virtual Memory Usage(Mo)"};
private static boolean started = false;
private static final int COLONNE_NAME=0;
private static final int COLONNE_PATH=8;
private static final int COLONNE_CPU=10;
private static final int COLONNE_Description=13;
private static final int COLONNE_ID=23;
private static final int COLONNE_PROCESSNAME=49;


private static final int COLONNE_VISIBLE_PROCESSUS_MEMORY_USAGE=5;
private static final int COLONNE_UNVISIBLE_PROCESSUS_MEMORY_USAGE=6;
private static final int COLONNE_VIRTUAL_MEMORY_USAGE=3;

	public static void powerShell() throws IOException{
		//String command = "powershell.exe \"C:\\Users\\natha\\Documents\\test.ps1\" ";
		System.out.println("Powershell begin");
		String command = "powershell.exe \"src/test.ps1\" ";
		Process powerShellProcess = Runtime.getRuntime().exec(command);
		powerShellProcess.getOutputStream().close();
	}

public static void csvReader(String fichier){
	  processList = new ArrayList<>();
	  
	  FileReader monFichier = null;
	  BufferedReader tampon = null;
	  
	  try {
	  monFichier = new FileReader(fichier);
	  tampon = new BufferedReader(monFichier);
	  int index = 0;
	  
	  while (true) {
		  String ligne = tampon.readLine();
		  if (ligne != null)
		  {
			  String[] splittedLigne = ligne.split(",");
			  index ++;
			  if(index == 1){}//Supprime premiere ligne
			  else if(index == 2){}// on ignore
			  else if(index > 2 )
			  {
				 String[] colValue = new String[]{splittedLigne[COLONNE_NAME], splittedLigne[COLONNE_PATH], splittedLigne[COLONNE_Description], splittedLigne[COLONNE_ID], splittedLigne[COLONNE_PROCESSNAME], splittedLigne[COLONNE_CPU],splittedLigne[COLONNE_VISIBLE_PROCESSUS_MEMORY_USAGE],splittedLigne[COLONNE_UNVISIBLE_PROCESSUS_MEMORY_USAGE],splittedLigne[COLONNE_VIRTUAL_MEMORY_USAGE]};
				 processList.add(colValue);
				 System.out.println(Arrays.toString(colValue));
			  }

		  }

		  if (ligne == null)

			  break;

	  	} 
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

public static void startMonitoring() {
	//String csvFile = "C:\\Users\\natha\\Documents\\get-process_usage_cpu.csv";
	String csvFile = "./src/get-process_usage_cpu.csv";
	if (!started) {
		started = true;
		Thread thread = new Thread() {
			public void run() {
				while (started) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
					 try {
						powerShell();
						 csvReader(csvFile);
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

public static void main(String[] args) throws IOException {
	 startMonitoring();
}
 
}

