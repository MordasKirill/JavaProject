package net.epam.study.dao.email;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public final class SendEmail {

    private static final Logger LOG = Logger.getLogger(SendEmail.class);
    public static SendEmail sendEmail;
    private final String username;
    private final String password;
    private final Properties props;

    public SendEmail() {
        EmailResourceManager emailResourceManager = EmailResourceManager.getInstance();

        props = new Properties();
        props.put(EmailParameter.AUTH, emailResourceManager.getValue(EmailParameter.AUTH));
        props.put(EmailParameter.HOST, emailResourceManager.getValue(EmailParameter.HOST));
        props.put(EmailParameter.USER, emailResourceManager.getValue(EmailParameter.USER));
        props.put(EmailParameter.PORT, emailResourceManager.getValue(EmailParameter.PORT));
        props.put(EmailParameter.TRUST, emailResourceManager.getValue(EmailParameter.TRUST));
        props.put(EmailParameter.ENABLE, emailResourceManager.getValue(EmailParameter.ENABLE));
        username = emailResourceManager.getValue(EmailParameter.USER);
        password = emailResourceManager.getValue(EmailParameter.PASSWORD);
    }

    public void send(String status, String toEmail) throws EmailException {

        EmailResourceManager emailResourceManager = EmailResourceManager.getInstance();

        if (toEmail == null) {
            toEmail = emailResourceManager.getValue(EmailParameter.USER);
        }

        String success;

        if (status.equals("accepted")) {

            success = "We'll complete it during an hour, thank you!";

        } else {
            success = "Please, check entered data.";
        }

        Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            String htmlBody = "<strong>Your order is " + status.toUpperCase() + ". " + success + "</strong>";

            Message message = new MimeMessage(mailSession);

            message.setFrom(new InternetAddress(emailResourceManager.getValue(EmailParameter.USER)));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            message.setSubject("ORDER FBOnline");

            MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();

            mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
            mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
            mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
            mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
            mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");

            CommandMap.setDefaultCommandMap(mc);

            message.setContent(htmlBody, "text/html");
            Transport.send(message);

            LOG.info("Email sent.");


        } catch (MessagingException e) {

            LOG.log(Level.ERROR, "SendEmail error.", e);
            throw new EmailException("Send email exception", e);
        }
    }
}
