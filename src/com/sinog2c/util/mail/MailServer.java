package com.sinog2c.util.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Session;

public class MailServer {
	
	  private Properties sessionProperties;
	  private Authenticator authenticator;

	  public Properties getSessionProperties() {
	    return sessionProperties;
	  }

	  public void setSessionProperties(Properties sessionProperties) {
	    this.sessionProperties = sessionProperties;
	  }

	  public Authenticator getAuthenticator() {
	    return authenticator;
	  }

	  public void setAuthenticator(Authenticator authenticator) {
	    this.authenticator = authenticator;
	  }

	  public Session getMailSession() {
	    return Session.getDefaultInstance(sessionProperties, authenticator);
	  }
	}
