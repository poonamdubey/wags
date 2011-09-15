package microlabs.dst.shared;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Mike Dusenberry
 *
 */
public class EmailService {

	public void email()
	{
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        String msgBody = "...";

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("mdusen9@gmail.com", "DST Admin"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress("mdusen9@gmail.com", "User"));
            msg.setSubject("The user's project was correct.");
            msg.setText(msgBody);
            Transport.send(msg);

        } catch (Exception e) {
            // ...
        }
	}
}
