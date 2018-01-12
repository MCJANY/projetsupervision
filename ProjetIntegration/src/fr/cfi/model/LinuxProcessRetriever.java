package fr.cfi.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinuxProcessRetriever implements IProcessRetriever {
	private static final String[] colonneName = {"PRI", "PID", "%CPU", "Command", "%MEM", "Virtual Size"}; 
	private static final int PROCESS_PID_INDEX = 1;
	private static final int PROCESS_CPU_INDEX = 2;
	private static final int PROCESS_NAME_INDEX = 3;
	
	public EmailSender email = new EmailSender();
	private static Map<String, String[]> processAlerted = new HashMap<>();

	@Override
	public List<String[]> sortProcess() {
		List<String[]> process = new ArrayList<String[]>();
		String command = "ps -eo pri,pid,pcpu,comm,%mem,vsize ";
		Process linuxShellProcess;
		try {
			linuxShellProcess = Runtime.getRuntime().exec(command);
			linuxShellProcess.getOutputStream().close();
			//System.out.println("Close file .ps1");
			BufferedReader reader = new BufferedReader(new InputStreamReader(linuxShellProcess.getInputStream()));
			//System.out.println(reader.readLine());
			
			String ligne;
			String[] split = null;
			while ((ligne=reader.readLine()) != null) {
				ligne = reader.readLine();
				if(ligne != null){
					ligne = ligne.trim().replaceAll(" {2,}", " ");
					split = ligne.split(" ");
					surveyCPU(split);
					process.add(split);
					//System.out.println(Arrays.toString(split));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return process;
	}
	
	@Override
	public String[] getColonnes() {
		return colonneName;
	}
	
	@Override
	public int getPidIndex() {
		return PROCESS_PID_INDEX;
	}

	private void surveyCPU(String[] valuesProcess) {
		Double maxCPU = Double.parseDouble(System.getProperty("maxcpu"));
		Double processCPU = Double.parseDouble(valuesProcess[PROCESS_CPU_INDEX]);
		
		if(processCPU > maxCPU) {
			System.out.println("tableau pas vide");
			if((processAlerted.get(valuesProcess[PROCESS_PID_INDEX])) == null) {
				String textEmail = "Le processus " + valuesProcess[PROCESS_NAME_INDEX] + " a depasse le seuil de " + maxCPU + " %.";
				System.out.println(textEmail);
				processAlerted.put(valuesProcess[PROCESS_PID_INDEX], valuesProcess);
				if(System.getProperty("enableMailNotification").equals("true")) {
					email.sendEmail("Alerte charge CPU", textEmail);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		LinuxProcessRetriever linuxProcessRetriever = new LinuxProcessRetriever();
		linuxProcessRetriever.sortProcess();
	}

}
