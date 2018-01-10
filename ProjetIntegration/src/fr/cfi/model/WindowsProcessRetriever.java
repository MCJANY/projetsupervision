package fr.cfi.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WindowsProcessRetriever implements IProcessRetriever {
	

	public static final String[] COLONNES = new String[] {"Name", "Path", "Description", "PID", "ProcessName", "RAM (Ko)","StartTime", "TotalProcessorTime","CPU"};
	private static boolean started = false;

	
	
	//FICHIER MEMORY
	private static final int COLONNE_UNVISIBLE_PROCESSUS_MEMORY_USAGE=6;
	private static final int COLONNE_PATH=7;//
	//private static final int COLONNE_CPU=9;
	private static final int COLONNE_ProductVersion=11;
	private static final int COLONNE_Description=12;//
	private static final int COLONNE_ProductName=13;
	private static final int COLONNE_ID=22;//
	private static final int COLONNE_PROCESSNAME=47;//
	private static final int COLONNE_StartTime=52;//
	private static final int COLONNE_ProcessTime=55;//
	private static final int COLONNE_NAME = 0;
	private static final int COLONNE_PID = 6;
	private static final int COLONNE_CPU = 9;
	

	
	private static String[] processHeader = new String[10];
	
	public WindowsProcessRetriever() {
		// TODO Auto-generated constructor stub*
		// Paths - Refresh interval - 
		
	}
	
	public BufferedReader execGetProcessMemory() throws IOException{
		String command = "powershell.exe \"src/script/getProcessMemory.ps1\" ";
		Process powerShellProcess = Runtime.getRuntime().exec(command);
		System.out.println("Close file .ps1");
		BufferedReader reader = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));
		return reader;
	}
	
	public BufferedReader execGetProcessCpu() throws IOException{
		String command = "cmd /c src\\script\\getProcessCpu.bat";
		Process batchProcess = Runtime.getRuntime().exec(command);
		System.out.println("Close file .bat");
		BufferedReader reader = new BufferedReader(new InputStreamReader(batchProcess.getInputStream()));
		return reader;
	}
	
	public List<String[]> readProcessMemory(){
		List<String[]> processList = new ArrayList<>();
		String fichier = "./log/processMemory.csv";
		
		FileReader monFichier = null;
		BufferedReader tampon = null;
  
		try {
			//monFichier = new FileReader(fichier);
			//tampon = new BufferedReader(monFichier);
			tampon = execGetProcessMemory();
			int index = 0;

			String ligne = "toto";
			while (ligne != null) {
				ligne = tampon.readLine();
				if(ligne != null){
					ligne = ligne.replace('\"', '\0').replace('\n', '\0').replace('\r', '\0');
					String[] splittedLigne = ligne.split(";");
					index ++;

					/*if(index == 2){
						for(int idx=0; idx < splittedLigne.length ; idx++){
							if(!splittedLigne[idx].isEmpty()) {
								processHeader[idx] = splittedLigne[idx].trim();
							}
						}
						processHeader[splittedLigne.length] = "CPU";
					}*/
					
					// Supprime premiere et deuxi�me ligne
					if(index > 2 ){
						/*while(splittedLigne[COLONNE_NAME].charAt(0) == '\0'){
							splittedLigne[COLONNE_NAME] = splittedLigne[COLONNE_NAME].substring(1);
						}*/
						for(int idx=0; idx < splittedLigne.length ; idx++){
							splittedLigne[idx] = splittedLigne[idx].trim();
							if(splittedLigne[idx].isEmpty()) {
								splittedLigne[idx] = "-";
							}
						}

						String[] colValue = new String[]{splittedLigne[COLONNE_NAME], splittedLigne[COLONNE_PATH], (splittedLigne[COLONNE_ProductVersion].length()>2)?(splittedLigne[COLONNE_Description] +" - Version : " + splittedLigne[COLONNE_ProductVersion]):splittedLigne[COLONNE_Description], splittedLigne[COLONNE_ID], splittedLigne[COLONNE_PROCESSNAME], splittedLigne[COLONNE_UNVISIBLE_PROCESSUS_MEMORY_USAGE],splittedLigne[COLONNE_StartTime],splittedLigne[COLONNE_ProcessTime]};
						processList.add(colValue);
					}
				}
			}
		}catch (IOException exception){
			exception.printStackTrace();
		}finally{
			try{
				tampon.close();
				System.out.println("Close file mem.csv");
				monFichier.close();
			}catch(IOException exception1){
				exception1.printStackTrace();
			}
		}
		return processList;
	}
	
	public List<String[]> readProcessCpu(){
		List<String[]> processList = new ArrayList<>();
		List<String[]> cpuList = new ArrayList<>();
		String fichier = "./log/processCpu.csv";
		//String fichier = "C:\\Users\\alex\\Documents\\Local_new_Supervision\\log\\processCpu.csv";

		FileReader monFichier = null;
		BufferedReader tampon = null;

		try {
			//monFichier = new FileReader(fichier);
			//tampon = new BufferedReader(monFichier);
			tampon = execGetProcessCpu();
			int index = 0;

			String ligne = "1";
			while (ligne != null) {
				ligne = tampon.readLine();
				if(ligne != null){
					String[] splittedLigne = ligne.split("	");

					if(index == 0 ){
						for(int i=1; i<splittedLigne.length ; i++) {
							String[] tmpStr = {splittedLigne[i].substring(splittedLigne[i].indexOf('(') +1, splittedLigne[i].indexOf(')'))};
							/*while(tmpStr[COLONNE_NAME].charAt(tmpStr[COLONNE_NAME].length()-1) == ' '){
								tmpStr[COLONNE_NAME] = tmpStr[COLONNE_NAME].substring(0, tmpStr[COLONNE_NAME].length()-1);
							}*/
							tmpStr[COLONNE_NAME] = tmpStr[COLONNE_NAME].trim();
							processList.add(tmpStr);
							//System.out.println(tmpStr[0]);
						}
					}else if(index == 2) {
						for(int i=1; i<splittedLigne.length ; i++) {
							String[] tmpStr = {processList.get(i-1)[0], splittedLigne[i].substring(splittedLigne[i].indexOf('"') +1, splittedLigne[i].indexOf('"', splittedLigne[i].indexOf('"') +1))};
							if(tmpStr[1].isEmpty()) {
								tmpStr[1] = "-";
							}
							cpuList.add(tmpStr);
							//System.out.println("Process : " + tmpStr[0] + "	- CPU : " + tmpStr[1]);
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
				System.out.println("Close file cpu.csv");
				monFichier.close();
			}catch(IOException exception1){
				exception1.printStackTrace();
			}
		}
		return cpuList;
	}
	
	public List<String[]> sortProcess(){
		System.out.println("====== SORT PROCESS METHOD ======");
		List<String[]> memoryList = readProcessMemory();
		List<String[]> cpuList = readProcessCpu();
		
		List<String[]> sortedProcess = new ArrayList<>();
		String[] concat = null;

//		System.out.println("memoryList.size() = " + memoryList);
//		System.out.println("cpuList.size() = " + cpuList);
		String[] memoryValues = null;
		String[] cpuValues=null;
		String cpuValue = null;
		for(int idxMem = 0; idxMem < memoryList.size(); idxMem++){
			memoryValues = memoryList.get(idxMem);
			concat = new String[memoryValues.length+1];
			System.arraycopy(memoryValues, 0, concat, 0, memoryValues.length);
			cpuValue ="-";
			for(int idxCpu = 0; idxCpu < cpuList.size(); idxCpu++){
				cpuValues = cpuList.get(idxCpu);
				if(memoryValues[COLONNE_NAME].equals(cpuValues[COLONNE_NAME])){
					cpuValue = cpuValues[1];
					break;
				}
			}
			concat[concat.length-1] = cpuValue;
			sortedProcess.add(concat);
		}
		
		//System.out.println("sortedProcess size = " + sortedProcess.size());
//		for(String[] values : sortedProcess) {
//		System.out.println("values execGetProcess=" + Arrays.toString(values));
	
		//System.out.println("sortedProcess = " + Arrays.toString(sortedProcess.toArray()));
		return sortedProcess;
	}
	
	public String[] getProcessHeader() {
		return processHeader;
	}
	
	public List<String[]> execGetProcess() {
		List<String[]> result = null;
		
		try {
			result =  sortProcess();
//			for(String[] values : result) {
//				System.out.println("values execGetProcess=" + Arrays.toString(values));
//			}
			//execGetProcessMemory();
			//execGetProcessCpu();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void startMonitoring(String data) {
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
						 List<String[]> sortedProcess = sortProcess();
						 //System.out.println("====== SORTED PROCESS ======");
						 /*for(int i=0; i < sortedProcess.get(0).length; i++){
							 System.out.println(sortedProcess.get(0)[i]);
						 }*/
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

	@Override
	public String[] getColonnes() {
		
		return COLONNES;
	}


}
