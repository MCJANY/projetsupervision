package fr.cfi.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinuxProcessRetriever implements IProcessRetriever {
	private static final String[] colonneName = {"PRI", "PID", "%CPU", "Command", "%MEM", "Virtual Size"}; 
	private static final int PROCESS_PID_INDEX = 1;

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

	
	public static void main(String[] args) {
		LinuxProcessRetriever linuxProcessRetriever = new LinuxProcessRetriever();
		linuxProcessRetriever.sortProcess();
	}

}
