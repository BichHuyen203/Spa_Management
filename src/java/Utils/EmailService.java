/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.util.Properties;
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
 * @author nhatm
 */
public class EmailService {

    //Email: focuslearn927@gmail.com
    private static final String from = "focuslearn927@gmail.com";
    //Pass: sedwroyckpunaubh
    private static final String password = "sedwroyckpunaubh";

    //Send email from ... to ..., if type is signup then send sign up mail, type is forgotpass then send password reset mail
    public static void sendEmail(String to, String subject, String content) {
        String emailContent = "";
        //Properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        //Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }

        };
        //Session
        Session session = Session.getInstance(props, auth);

        //Send email
        MimeMessage msg = new MimeMessage(session);

        try {
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");

            //Sender
            msg.setFrom(from);

            //Receiver
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            msg.setSubject("Request to reset password ");

            //Content
            emailContent = content;

            msg.setText(emailContent, "UTF-8");
            //Send Email
            Transport.send(msg);
        } catch (Exception e) {
            System.out.println("sendEmail(): " + e.getMessage());
        }
    }
}
