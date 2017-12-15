package test;

import java.util.*;
import java.io.IOException;

public class consolApp {
    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final String SMTP_AUTH_USER = "mcjany78@gmail.com";
    private static final String SMTP_AUTH_PWD  = "azerty78";

	public static void main(String[] args) throws Exception{
		//emailSender myEmailBox = new emailSender(SMTP_HOST_NAME, SMTP_AUTH_USER, SMTP_AUTH_PWD);
		//myEmailBox.sendEmail(SMTP_AUTH_USER, "Warning : Memory overload", "Be carefull, the memory of the computer with the following IP adress reached over than 80%");
		
		System.out.println("Create process");
		processRetriever process = new processRetriever();
		
		/*try {
			System.out.println("Exec Process Cpu");
			process.execGetProcessCpu();
		 } catch (IOException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }*/
		
		/*System.out.println("Read Process Cpu");
		process.readProcessCpu();
		
		System.out.println("Exec Process Mem");
		process.execGetProcessMemory();
		System.out.println("Read Process Mem");
		process.readProcessMemory();
		*/
		//System.out.println("Start Monitoring");
		//process.startMonitoring("cpu");
		System.out.println("Stop Monitoring");
		process.startMonitoring("mem");
		
		//process.stopMonitoring();
		

	   }
}
