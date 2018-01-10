package fr.cfi.model;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationProperties {

	public static void main(String[] args) {
		 // set up new properties object
        // from file "myProperties.txt"
	
		System.out.println("os.name=" + System.getProperty("os.name") );
		System.out.println("os.version=" + System.getProperty("os.version") );
        FileInputStream propFile;
		try {
			String fichier = "./log/Configuration.txt";
			propFile = new FileInputStream( fichier);
		
			Properties p =new Properties(System.getProperties());
			p.load(propFile);

        // set the system properties
        System.setProperties(p);
        // display new properties
        String property = System.getProperty("host");
        System.out.println("host="+property);
        //System.getProperties().list(System.out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
