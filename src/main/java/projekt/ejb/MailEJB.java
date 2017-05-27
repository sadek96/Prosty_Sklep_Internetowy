/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt.ejb;

import java.util.Date;
import java.util.Properties;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author student
 */
@Stateless
public class MailEJB {

    //logger
    
    @Asynchronous
    public void sendMail(String mailTo, String subject, String body) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "poczta.interia.pl");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.ssl.enable", "true");
        Session session = Session.getDefaultInstance(props,new Authenticator(){
            @Override
            public PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("testmail1234","danieel1");
            }
        });
        MimeMessage m = new MimeMessage(session);
        try {
            InternetAddress[] address = {new InternetAddress(mailTo)};
            m.setFrom(new InternetAddress("testmail1234@interia.pl"));
            m.setRecipients(Message.RecipientType.TO, address);
            m.setSubject(subject);
            m.setSentDate(new Date());
            m.setText(body);
            Transport.send(m);
        } catch (MessagingException mex) {
            //log
        }
    }
}
