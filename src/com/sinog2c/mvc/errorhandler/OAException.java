package com.sinog2c.mvc.errorhandler;

public class OAException  extends RuntimeException {

	  private static final long serialVersionUID = 1L;

	  public OAException() {
	    super();
	  }
	  public OAException(String msg, Throwable cause) {
	    super(msg);
	    super.initCause(cause);
	  }
	  public OAException(String msg) {
	    super(msg);
	  }
	  public OAException(Throwable cause) {
	    super();
	    super.initCause(cause);
	  }
	}