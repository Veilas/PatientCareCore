package src;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailServer {
	
	private final String from = new String("MehoMehmedovich@gmail.com");
	private final String to = new String("MehoMehmedovich@gmail.com");
	private final String pass = new String("stevicazli");
	private final String host = new String("smtp.gmail.com");
	private Properties props = null;
	private Session session = null;
	private MimeMessage msg = null;
	
	public MailServer() {
	    props = new Properties();
		props.setProperty("mail.smtp.host", host);
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		session = Session.getDefaultInstance(props, 
				new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(from, pass);
					}
		});
		msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public void sendRequestMail(String bedNumber, String requestTime) {
		try {
			msg.setSubject("You have a request!");
			msg.setText("Njega je zahtijevana od strane pacijenta smjestenog na krevetu " + 
						bedNumber + ", a vrijeme zahtijevanja njege je " + requestTime);
			Transport.send(msg);
			System.out.println("Message sent...");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public void sendConformationMail(String bedNumber, String serviceTime) {
		try {
			msg.setSubject("You have a conformation!");
			msg.setText("Njega je pruzena pacijentu smjestenom na krevetu " + 
						bedNumber + ", a vrijeme pruzanja njege je " + serviceTime.toString());
			Transport.send(msg);
			System.out.println("Message sent...");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}	
}
