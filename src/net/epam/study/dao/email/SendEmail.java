package net.epam.study.dao.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public final class SendEmail {

    private String username;
    private String password;
    private Properties props;
    public static SendEmail sendEmail;

    public SendEmail(){
        EmailResourceManager emailResourceManager = EmailResourceManager.getInstance();

        props = new Properties();
        props.put(EmailParameter.AUTH, emailResourceManager.getValue(EmailParameter.AUTH));
        props.put(EmailParameter.HOST, emailResourceManager.getValue(EmailParameter.HOST));
        props.put(EmailParameter.PROTOCOL, emailResourceManager.getValue(EmailParameter.PROTOCOL));
        props.put(EmailParameter.USER, emailResourceManager.getValue(EmailParameter.USER));
        username = emailResourceManager.getValue(EmailParameter.USER);
        password = emailResourceManager.getValue(EmailParameter.PASSWORD);
    }
    public void send(String subject, String text, String toEmail) throws EmailException{

        Session mailSession = Session.getDefaultInstance(props);
        MimeMessage mimeMessage = new MimeMessage(mailSession);
        try {
            mimeMessage.setFrom(new InternetAddress(username));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(text);
        } catch (MessagingException e) {

            throw new EmailException("MimeMessage error!", e);
        }


        Transport transport;
        try {
            transport = mailSession.getTransport();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            throw new EmailException("GetTransport error!", e);
        }

        try {

            transport.connect(username, password);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new EmailException("Transport error!", e);
        }
    }
}
