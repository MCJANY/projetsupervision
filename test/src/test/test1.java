package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

 	
public class test1 {
	
	private static String[] columnName = new String[]{"Name", "Path", "Description", "ID", "Processname", "RAM (Ko)", "Start Time", "Process Time"};
	private static boolean started = false;
	private static final int COLONNE_NAME=0;//
	private static final int COLONNE_UNVISIBLE_PROCESSUS_MEMORY_USAGE=6;
	private static final int COLONNE_PATH=7;//
	//private static final int COLONNE_CPU=9;
	private static final int COLONNE_ProductVersion=11;
	private static final int COLONNE_Description=12;//
	private static final int COLONNE_ProductName=13;
	private static final int COLONNE_ID=22;//
	private static final int COLONNE_PROCESSNAME=47;//
	private static final int COLONNE_StartTime=55;//
	private static final int COLONNE_ProcessTime=56;//
	
	
	//private static final int COLONNE_VISIBLE_PROCESSUS_MEMORY_USAGE=5;
	//private static final int COLONNE_VIRTUAL_MEMORY_USAGE=3;
	
	public static void powerShell() throws IOException{
		//String command = "powershell.exe \"C:\\Users\\natha\\Documents\\test.ps1\" ";
		System.out.println("Powershell begin");
		String command = "powershell.exe \"src/script/getProcessMemory.ps1\" ";
		Process powerShellProcess = Runtime.getRuntime().exec(command);
		powerShellProcess.getOutputStream().close();
	}
	
	public static void execBat() throws IOException{
		//String command = "powershell.exe \"C:\\Users\\natha\\Documents\\test.ps1\" ";
		System.out.println("execBat begin");
		String command = "cmd /c src\\script\\getProcessCpu.bat";
		Process batchProcess = Runtime.getRuntime().exec(command);
		batchProcess.getOutputStream().close();
	}
	
	public static void csvProcessMemoryReader(String fichier){
		List<String[]> processList = new ArrayList<>();
		  
		  FileReader monFichier = null;
		  BufferedReader tampon = null;
		  
		  try {
			  monFichier = new FileReader(fichier);
			  tampon = new BufferedReader(monFichier);
			  int index = 0;
			  
			  String ligne = "1";
			  while (ligne != null) {
				  ligne = tampon.readLine();
				  ligne = ligne.replace('\"', '\0').replace('\n', '\0').replace('\r', '\0');
				  String[] splittedLigne = ligne.split(";");
				  index ++;
				  
				  // Supprime premiere et deuxième ligne
				  if(index > 2 ){
					  String[] colValue = new String[]{splittedLigne[COLONNE_NAME], splittedLigne[COLONNE_PATH], (splittedLigne[COLONNE_ProductVersion].length()>2)?(splittedLigne[COLONNE_Description] +" - Version : " + splittedLigne[COLONNE_ProductVersion]):splittedLigne[COLONNE_Description], splittedLigne[COLONNE_ID], splittedLigne[COLONNE_PROCESSNAME], splittedLigne[COLONNE_UNVISIBLE_PROCESSUS_MEMORY_USAGE],splittedLigne[COLONNE_StartTime],splittedLigne[COLONNE_ProcessTime]};
					 
					  /*for(int i=0; i < colValue.length; i++){
						  System.out.println(colValue[i]);
					  }*/
					  processList.add(colValue);
				  }
			  }
		  }catch (IOException exception){
			  exception.printStackTrace();
		  }finally{
			  try{
				  tampon.close();
				  monFichier.close();
			  }catch(IOException exception1){
				  exception1.printStackTrace();
			  }
		  }
	}
	
	public static void csvProcessCpuReader(String fichier){
		List<String[]> processList = new ArrayList<>();
		List<String[]> cpuList = new ArrayList<>();		
		
		FileReader monFichier = null;
		BufferedReader tampon = null;
	  
		try {
			  monFichier = new FileReader(fichier);
			  tampon = new BufferedReader(monFichier);
			  int index = 0;
			  
			  String ligne = "1";
			  while (ligne != null) {
				  ligne = tampon.readLine();
				  if(ligne != null){
					  String[] splittedLigne = ligne.split("	");
					  
					  if(index == 0 ){
						  for(int i=1; i<splittedLigne.length ; i++) {
							  /*System.out.println(splittedLigne[i]);
							  System.out.println(splittedLigne[i].indexOf('('));
							  System.out.println(splittedLigne[i].indexOf(')'));
							  System.out.println(splittedLigne[i].substring(splittedLigne[i].indexOf('(') +1, splittedLigne[i].indexOf(')')));*/
							  
							  String[] tmpStr = {splittedLigne[i].substring(splittedLigne[i].indexOf('(') +1, splittedLigne[i].indexOf(')'))};
							  processList.add(tmpStr);
							  System.out.println(tmpStr[0]);
						  }
					  }else if(index == 2) {
						  for(int i=1; i<splittedLigne.length ; i++) {
							  /*System.out.println(splittedLigne[i]);
							  System.out.println(splittedLigne[i].indexOf('"'));
							  System.out.println(splittedLigne[i].indexOf('"'));
							  System.out.println(splittedLigne[i].substring(splittedLigne[i].indexOf('"') +1, splittedLigne[i].indexOf('"', splittedLigne[i].indexOf('"') +1)));
							  */
							  String[] tmpStr = {splittedLigne[i].substring(splittedLigne[i].indexOf('"') +1, splittedLigne[i].indexOf('"', splittedLigne[i].indexOf('"') +1))};
							  if(tmpStr[0].isEmpty()) {
								  tmpStr[0] = "-";
							  }
							  cpuList.add(tmpStr);
							  System.out.println(tmpStr[0]);
						  }
					  }
				  }
				  index ++;
			  }
		  }catch (IOException exception){
			  exception.printStackTrace();
		  }finally{
			  try{
				  tampon.close();
				  monFichier.close();
			  }catch(IOException exception1){
				  exception1.printStackTrace();
			  }
		  }
	}
	
	public static void startMonitoring() {
		//String csvFile = "C:\\Users\\natha\\Documents\\get-process_usage_cpu.csv";
		String csvMemoryFile = "./log/processMemory.csv";
		String csvCpuFile = "./log/processCpu.csv";
		if (!started) {
			started = true;
			Thread thread = new Thread() {
				public void run() {
					while (started) {
						try {
							Thread.sleep(5000); //500
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 
						 try {
							//powerShell();
							//csvProcessMemoryReader(csvMemoryFile);
							execBat();
							csvProcessCpuReader(csvCpuFile);
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
	
	/*public static void main(String[] args) throws IOException {
		System.out.println("args=" + Arrays.toString(args));
		System.out.println("FileConfig="+System.getProperty("FileConfig"));
		System.out.println("toto="+System.getProperty("toto"));
		startMonitoring();
	}
 */
}

