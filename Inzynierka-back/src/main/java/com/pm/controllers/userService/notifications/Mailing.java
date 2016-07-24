package com.pm.controllers.userService.notifications;
import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

public class Mailing{

    private InternetAddress myAddress;
    private Session session;
    private SMTPTransport transportLayer;

    public Mailing() throws UnsupportedEncodingException, MessagingException {
        System.out.println("Konfiguruje klase do wysylania e-maili");
        configure();
    }

    public void configure() throws UnsupportedEncodingException, MessagingException {
        Properties prop = new Properties();
        InputStream input = null;

        // load a properties file
        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
            this.myAddress = new InternetAddress(prop.getProperty("mail"), "PatternMoveAGH");

            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            Properties props = System.getProperties();
            props.setProperty("mail.smtps.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.setProperty("mail.smtps.auth", "true");
            props.put("mail.smtps.quitwait", "false");

            this.session = Session.getInstance(props, null);
            this.transportLayer = (SMTPTransport) this.session.getTransport("smtps");
            this.transportLayer.connect("smtp.gmail.com", prop.getProperty("mail"), prop.getProperty("mailpassword"));

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void finalize() {
        try {
            super.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        try {
            this.transportLayer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean sendMail(String messageContent, String reciversEmail, String subject) {

        final MimeMessage message = new MimeMessage(this.session);
        try {
            message.setFrom(this.myAddress);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(reciversEmail, false));
            message.setSubject(subject);
            message.setText(messageContent, "utf-8");
            message.setSentDate(new Date());

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                this.transportLayer.sendMessage(message, message.getAllRecipients());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }
}
