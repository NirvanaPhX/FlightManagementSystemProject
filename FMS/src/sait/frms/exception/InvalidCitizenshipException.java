package sait.frms.exception;

public class InvalidCitizenshipException extends Exception {

	public InvalidCitizenshipException() {
		super("Missing Traveler's Citizenship.");
	}
}
