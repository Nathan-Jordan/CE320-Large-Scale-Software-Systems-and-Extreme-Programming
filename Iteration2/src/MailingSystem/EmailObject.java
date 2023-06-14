package MailingSystem;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailObject {

    Session session;

    public EmailObject() {
        String username = "choosymailbox@gmail.com";
        String password = "tKvZNRJj7pnmXawM";

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp-relay.sendinblue.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp-relay.sendinblue.com");

        session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void sendEmail(String subject, String email, String messageBody) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("choosymailbox@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(email));


            message.setSubject(subject);


            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(messageBody, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}