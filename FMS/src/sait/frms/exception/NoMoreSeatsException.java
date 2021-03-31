package sait.frms.exception;
/** 
 * 
 * Class description:
 *
 */
public class NoMoreSeatsException extends Exception{
	public NoMoreSeatsException() {
		super("There is no more seats available");
	}

}
