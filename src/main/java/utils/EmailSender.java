package utils;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {

	private final String username;
    private final String password;
	private Properties props;

    public EmailSender() {
    	
    	this.username = Config.get("mail.username");
        this.password = Config.get("mail.password");
        
        if (this.username == null || this.password == null) {
         
        	throw new RuntimeException("Faltan credenciales de mail en config.properties");
        
        }
        
    	props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    
    }

    public void sendEmail(String toEmail, String subject, String bodyHtml) {
        
        Session session = Session.getInstance(props, new Authenticator() {
    
        	@Override
            protected PasswordAuthentication getPasswordAuthentication() {
            
        		return new PasswordAuthentication(username, password);
            
        	}
        
        });

        try {
        
        	Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            
            message.setContent(bodyHtml, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("✅ Email enviado con éxito a: " + toEmail);

        } catch (MessagingException e) {
            
        	System.err.println("❌ Error enviando email: " + e.getMessage());
            e.printStackTrace();
        
        }
    
    }

}