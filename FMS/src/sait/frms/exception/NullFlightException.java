package sait.frms.exception;

public class NullFlightException extends Exception{
	public NullFlightException() {
		super("That flight doesn't exist. Please try another.");
	}

}
