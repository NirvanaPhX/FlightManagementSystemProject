package sait.frms.exception;

public class NoMoreSeatsException extends Exception{
	public NoMoreSeatsException() {
		super("There is no more seats available");
	}

}
