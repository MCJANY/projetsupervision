package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

 	
public class test1 {
	
private static List<String[]> processList = null;
private static String[] columnName = new String[]{"Name","Path","CPU","Description","ID","Processname"};
private static boolean started = false;
private static final int COLONNE_NAME=0;
private static final int COLONNE_PATH=8;
private static final int COLONNE_CPU=10;
private static final int COLONNE_Description=13;
private static final int COLONNE_ID=23;
private static final int COLONNE_PROCESSNAME=49;

	public static void powerShell() throws IOException{
		String command = "powershell.exe \"C:\\Users\\natha\\Documents\\test.ps1\" ";
		Process powerShellProcess = Runtime.getRuntime().exec(command);
		powerShellProcess.getOutputStream().close();
	}

public static void csvReader(String fichier){
	  processList = new ArrayList<>();
	  
	  FileReader monFichier = null;
	  BufferedReader tampon = null;
	  String name = "";
	  String si = "";
	  
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
//			  else if(index == 2 ) {
//				  colonneList = splittedLigne;
//				  name = splittedLigne[0];
//				  si = splittedLigne[1];
//				  //System.out.println(splittedLigne[0]);
//				  //System.out.println(splittedLigne[1]);
//			  System.out.println(Arrays.toString(colonneList));
//				  //String lol = Arrays.toString(colonneList);
//				  //System.out.println(colonneList[4]);
//				  //colonneList 
//				  //System.out.println(splittedLigne[0]);
//			  }
			  else if(index > 2 )
			  {
				 String[] colValue = new String[]{splittedLigne[COLONNE_NAME], splittedLigne[COLONNE_PATH], splittedLigne[COLONNE_CPU], splittedLigne[COLONNE_Description], splittedLigne[COLONNE_ID], splittedLigne[COLONNE_PROCESSNAME]};
				 processList.add(colValue);
				 System.out.println(Arrays.toString(colValue));

				  //System.out.println(splittedLigne[0]+";"+splittedLigne[1]+";"+splittedLigne[2]+";"+splittedLigne[3]+";"+splittedLigne[4]+";"+splittedLigne[5]);
				  //System.out.println(Arrays.toString(splittedLigne[0,1]));
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
	String csvFile = "C:\\Users\\natha\\Documents\\get-process_usage_cpu.csv";
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

