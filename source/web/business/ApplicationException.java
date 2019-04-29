package edu.gatech.cs6400.team81.business;

public class ApplicationException extends Exception {
	private static final long serialVersionUID = -7661581777864046920L;

	public ApplicationException() {
		super();
	}

	public ApplicationException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ApplicationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ApplicationException(String arg0) {
		super(arg0);
	}

	public ApplicationException(Throwable arg0) {
		super(arg0);
	}
}
