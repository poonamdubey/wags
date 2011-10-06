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

	public String email(String problemName)
	{
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        
        String email = UserServiceFactory.getUserService().getCurrentUser().getEmail();
		//email = email.replace("@", "XATSIGNX");
        
        // Testing
        System.out.println("The email address is:" + email);

        //String msgBody = "...";

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("AppDataStructures@gmail.com", "DST Admin"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress("pmeznar@gmail.com", "User"));
            msg.setSubject("User " + email + " completed activity: " + problemName);
            msg.setText("User " + email + " completed activity: " + problemName + 
            			" of the Data Structure Tool logical labs.");
            Transport.send(msg);

        } catch (Exception e) {
            System.out.println("There was an email error: ");
            System.out.println(e.getMessage());
            return "Failure";
        }
        
        return "Success";
	}
}
