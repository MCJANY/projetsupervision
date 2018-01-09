package fr.cfi.model;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	
	////// Attributes //////
	private Session m_mailSession;
	
	////// Methods //////
	// Constructor
	EmailSender(String arg_hostName, String arg_user, String arg_password){
        Properties props = new Properties();
        props.put("mail.smtp.host", arg_hostName);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        
		m_mailSession = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(arg_user, arg_password);
					}
				});
	}
	
	// Send
	public void sendEmail(String arg_dest, String arg_subject, String arg_text) {
		try {
	        MimeMessage message = new MimeMessage(m_mailSession);
	        message.setSubject(arg_subject);
	        message.setText(arg_text);
	        message.setFrom(new InternetAddress("mcjany78@gmail.com"));
	        message.setRecipients(Message.RecipientType.TO,
	            InternetAddress.parse(arg_dest));

	        Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	

}
