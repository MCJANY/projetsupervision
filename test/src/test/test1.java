package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.PrintWriter;

public class test1 {

 public static void main(String[] args) throws IOException {
  //PrintWriter writer = new PrintWriter("C:\\Users\\natha\\Documents\\the-file-name.csv");
  //String command = "powershell.exe  your command";
  //Getting the version
  String command = "powershell.exe \"C:\\Users\\natha\\Documents\\test.ps1\" ";
  // Executing the command
  Process powerShellProcess = Runtime.getRuntime().exec(command);
  // Getting the results
  powerShellProcess.getOutputStream().close();
  String line;
  //System.out.println("Standard Output:");
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
    powerShellProcess.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   System.out.println(line);
  //Write into file
   //writer.println(line);

  }
  stdout.close();
  //System.out.println("Standard Error:");
  BufferedReader stderr = new BufferedReader(new InputStreamReader(
    powerShellProcess.getErrorStream()));
  while ((line = stderr.readLine()) != null) {
   //System.out.println(line);


  
  }
  //writer.close();
  stderr.close();
 // System.out.println("Done");

 }

}
