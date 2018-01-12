package fr.cfi.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class WindowsProcessRetriever implements IProcessRetriever {
	

	public static final String[] COLONNES = new String[] {"Name", "PID", "Memory Load (Ko)", "CPU Load (%)"};
	private static boolean started = false;
	private static Hashtable<String, String> cpuTimeProcess = null;

		
	private static final int NAME_COMMAND = 0;
	private static final int PID_COMMAND = 1;
	private static final int MEMORY_COMMAND = 4;
	private static final int CPU_TIME_COMMAND = 7;
	
	private static final int PROCESS_PID_INDEX = 1;
	

	private double totalCpuTime;
	private static String[] processHeader = new String[10];
	
	public WindowsProcessRetriever() {
		// TODO Auto-generated constructor stub*
		// Paths - Refresh interval - 
		
	}
					
	public String[] getProcessHeader() {
		return processHeader;
	}
	
	public BufferedReader getProcessInfo() throws IOException{
		String command = "cmd /c tasklist /V /FO CSV";
		Process batchProcess = Runtime.getRuntime().exec(command);
		BufferedReader reader = new BufferedReader(new InputStreamReader(batchProcess.getInputStream()));
		return reader;
	}
	
	private double convertTimeToDouble(String date1, String date2) {
		final int HEURE = 0;
		final int MINUTE = 1;
		final int SECONDE = 2;
		String[] tabDate1 = date1.split(":");
		String[] tabDate2 = date2.split(":");
		
		Double[] doubleDate1 = new Double[3];
		Double[] doubleDate2 = new Double[3];
		Double[] diffDate = new Double[3];
		for(int i=0; i<3; i++) {
			doubleDate1[i].parseDouble(tabDate1[i]);
			doubleDate2[i].parseDouble(tabDate2[i]);
			diffDate[i] = doubleDate1[i] - doubleDate2[i];
		}
		Double diffSec = (diffDate[HEURE] * 3600) +(diffDate[MINUTE] * 60) + diffDate[SECONDE];
		
		return diffSec;
	}
	
	
	private String calculCPU(String tempsCPU, String PID) {
		String resultStr = " - ";
		double result = 0.0;
		if(cpuTimeProcess != null && !cpuTimeProcess.isEmpty()) {
			for (Object str:cpuTimeProcess.keySet().toArray()) {
				if(PID.equals(str)) {
					result = convertTimeToDouble(cpuTimeProcess.get(PID), tempsCPU);
					cpuTimeProcess.replace(PID, tempsCPU);
					resultStr = String.valueOf(result);
					
				}else {
					cpuTimeProcess.put(PID, tempsCPU);
					// pas de modif du string de return
				}
			}
			
		}
		return resultStr;
	}
	
	public List<String[]> sortProcess(){
		List<String[]> tabProcess = new ArrayList<String[]>();
		cpuTimeProcess = null;
		BufferedReader reader;
		try {
			reader = getProcessInfo();

			String ligne;
			String[] split = null;
			while ((ligne=reader.readLine()) != null) {
				ligne = reader.readLine();
				if(ligne != null){
					ligne = ligne.replaceAll("\"", "").replace( (char) 255, ' ');
					split = ligne.split(",");
					
					
					
					if(!(split[1].equals("0")) ) {
						String pCPU = calculCPU(split[CPU_TIME_COMMAND], split[PID_COMMAND]);
						
						String[] process = {split[NAME_COMMAND], split[PID_COMMAND], split[MEMORY_COMMAND], pCPU};
						tabProcess.add(process);
						
					}else {
						if(cpuTimeProcess != null && !cpuTimeProcess.isEmpty()) {
							String[] tabKey = (String[]) cpuTimeProcess.keySet().toArray();
							for(String str : tabKey) {
								if(str.equals("0")) {
									totalCpuTime = convertTimeToDouble(cpuTimeProcess.get("0"), split[CPU_TIME_COMMAND]);
								}else {
									cpuTimeProcess.put(split[PID_COMMAND], split[CPU_TIME_COMMAND]);
								}
							}
						}
					}
				}
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("tabProcess.size() = " + tabProcess.size());
		
		return tabProcess;
	}
	
	@Override
	public String[] getColonnes() {
		
		return COLONNES;
	}
	
	@Override
	public int getPidIndex() {
		return PROCESS_PID_INDEX;
	}


	public static void main(String[] args) {
		WindowsProcessRetriever WindowsProcessRetriever = new WindowsProcessRetriever();
		List<String[]> process1 = new ArrayList<String[]>();
		
		for(int nbTest=0; nbTest<3; nbTest++) {
		
			process1 = WindowsProcessRetriever.sortProcess();
			for(String[] tabStr : process1) {
				for(String a : tabStr) {
					System.out.print(a+"|");
				}
			System.out.print("\n");
			}
		
		}
	}
}
