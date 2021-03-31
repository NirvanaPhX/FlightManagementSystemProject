package sait.frms.exception;
/** 
 * 
 * Class description:
 *
 * @author Johnathon Bowden
 */
public class InvalidNameException extends Exception {
	public InvalidNameException() {
		super("Missing Traveler's Name.");
	}

}
