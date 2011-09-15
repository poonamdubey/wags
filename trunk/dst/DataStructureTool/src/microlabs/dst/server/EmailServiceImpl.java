package microlabs.dst.server;

import java.util.Properties;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import microlabs.dst.shared.EmailService;
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
@SuppressWarnings("serial")
public class EmailServiceImpl extends RemoteServiceServlet implements EmailService {

	public void email(String problemName)
	{
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        
        String email = UserServiceFactory.getUserService().getCurrentUser().getEmail();
		//email = email.replace("@", "XATSIGNX");

        String msgBody = "...";

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("mdusen9@gmail.com", "DST Admin"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress("mdusen9@gmail.com", "User"));
            msg.setSubject("User " + email + " completed activity: " + problemName);
            msg.setText(msgBody);
            Transport.send(msg);

        } catch (Exception e) {
            // ...
        }
	}
}
