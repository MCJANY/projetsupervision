package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileReader;


public class test1 {
	
public static void powerShell() throws IOException{
	String command = "powershell.exe \"C:\\Users\\natha\\Documents\\test.ps1\" ";
	Process powerShellProcess = Runtime.getRuntime().exec(command);
	powerShellProcess.getOutputStream().close();
}

public static void csvReader(){
	  String csvFile = "C:\\Users\\natha\\Documents\\get-process_usage_cpu.csv";
	  FileReader monFichier = null;
	  BufferedReader tampon = null;

	  try {
	  monFichier = new FileReader(csvFile);
	  tampon = new BufferedReader(monFichier);

	  while (true) {
		  String ligne = tampon.readLine();
		  if (ligne == null)
			  break;
		  System.out.println(ligne);
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

 public static void main(String[] args) throws IOException {
	
	 powerShell();
	 csvReader();
 }
}
