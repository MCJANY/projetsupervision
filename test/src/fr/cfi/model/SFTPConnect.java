package fr.cfi.model;


public class SFTPConnect {
	
	
	jsch = new JSch();
	session = jsch.getSession(login, server, port);
	 
	// Java 6 version
	session.setPassword(password.getBytes(Charset.forName("ISO-8859-1")));
	             
	// Java 5 version
	// session.setPassword(password.getBytes("ISO-8859-1"));
	 
	Properties config = new java.util.Properties();
	config.put("StrictHostKeyChecking", "no");
	session.setConfig(config);
	 
	session.connect();
}
