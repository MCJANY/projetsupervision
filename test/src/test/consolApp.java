package test;

import java.util.*;

public class consolApp {
    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final String SMTP_AUTH_USER = "mcjany78@gmail.com";
    private static final String SMTP_AUTH_PWD  = "azerty78";

	public static void main(String[] args) throws Exception{
		//emailSender myEmailBox = new emailSender(SMTP_HOST_NAME, SMTP_AUTH_USER, SMTP_AUTH_PWD);
		//myEmailBox.sendEmail(SMTP_AUTH_USER, "Warning : Memory overload", "Be carefull, the memory of the computer with the following IP adress reached over than 80%");
		
	       Timer timer = new Timer();


	       // Schedule to run after every 3 second(3000 millisecond)
	       timer.scheduleAtFixedRate( new PeriodicalTask(), 1000, 2000);
	   }
}

class PeriodicalTask extends TimerTask {


    int count = 1;

    // run is a abstract method that defines task performed at scheduled time.
    public void run() {
        System.out.println(count+" : Mahendra Singh");
        count++;
    }
}
