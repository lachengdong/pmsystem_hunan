package com.sinog2c.util.mail;

import java.util.Collection;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import com.sinog2c.mvc.errorhandler.OAException;



public class WebMailSession {

	private MailServer mailServer;

	public void send(Collection<Message> emails) throws Exception {
		// Emails need to have the sessions populated.
		for (Message email : emails) {
			try {
				Address[] to = email.getRecipients(RecipientType.TO);
				Address[] cc = email.getRecipients(RecipientType.CC);
				Address[] bcc = email.getRecipients(RecipientType.BCC);

				// Set the email with the new filtered addresses.
				email.setRecipients(RecipientType.TO, to);
				email.setRecipients(RecipientType.CC, cc);
				email.setRecipients(RecipientType.BCC, bcc);

				// if sender is not present, use local address
				Session mailSession = mailServer.getMailSession();
				if (email.getFrom() == null) {
					email.setFrom(InternetAddress.getLocalAddress(mailSession));
				}

				// If there is someone to send it to, then send it.
				// Address[] recipients = email.getAllRecipients();
				//
				// // mailServer.getMailSession().GETT
				// if (recipients.length > 0) {
				// Transport transport = mailSession
				// .getTransport(recipients[0]);
				// try {
				// transport.connect();
				// transport.sendMessage(email, recipients);
				// } finally {
				// transport.close();
				// }
				// }
				Transport transport = mailSession.getTransport("smtp");
				try {
					transport.connect();
					transport.sendMessage(email, email.getAllRecipients());
				} catch (Exception ex) {
					throw ex;
				} finally {
					transport.close();
				}
			} catch (MessagingException e) {
				throw new OAException("could not send email: " + email, e);
			}
		}
	}

	public void setMailServers(MailServer mailServer) {
		this.mailServer = mailServer;
	}

}
