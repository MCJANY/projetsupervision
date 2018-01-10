package fr.cfi.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinuxProcessRetriever implements IProcessRetriever {

	@Override
	public List<String[]> execGetProcess() {
		List<String[]> process = new ArrayList<String[]>();
		String command = "ps -eo pid,psr,pcpu,comm,%mem,args,vsize ";
		Process linuxShellProcess;
		try {
			linuxShellProcess = Runtime.getRuntime().exec(command);
			linuxShellProcess.getOutputStream().close();
			System.out.println("Close file .ps1");
			BufferedReader reader = new BufferedReader(new InputStreamReader(linuxShellProcess.getInputStream()));
			System.out.println(reader.readLine());
			
			String ligne;
			String[] split = null;
			while ((ligne=reader.readLine()) != null) {
				ligne = reader.readLine();
				if(ligne != null){
					ligne = ligne.trim().replaceAll(" {2,}", " ");
					split = ligne.split(" ");
					process.add(split);
					System.out.println(Arrays.toString(split));
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
		
		return null;
	}
	
	public static void main(String[] args) {
		LinuxProcessRetriever linuxProcessRetriever = new LinuxProcessRetriever();
		linuxProcessRetriever.execGetProcess();
	}

}
