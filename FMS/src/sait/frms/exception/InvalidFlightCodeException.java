package sait.frms.exception;

public class InvalidFlightCodeException extends Exception{
	public InvalidFlightCodeException() {
		super("That is an invalid flight code.");
	}

}
