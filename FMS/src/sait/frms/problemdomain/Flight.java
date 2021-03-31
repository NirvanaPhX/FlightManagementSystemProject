package sait.frms.problemdomain;
 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sait.frms.exception.InvalidFlightCodeException;

public class Flight {
	private String code;
	private String airline;
	private String from;
	private String to;
	private String weekday;
	private String time;
	private int seats;
	private double costPerSeat;

	public Flight() {
	}

	public Flight(String code, String from, String to, String weekday, String time, int seats, double costPerSeat)
			throws InvalidFlightCodeException {
		this.parseCode(code);
		this.code = code;
		this.from = from;
		this.to = to;
		this.weekday = weekday;
		this.time = time;
		this.seats = seats;
		this.costPerSeat = costPerSeat;
	}

	public String getCode() {
		return code;
	}

	public String getAirline() {
		return this.code.substring(0, 2);
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getWeekday() {
		return weekday;
	}

	public String getTime() {
		return time;
	}

	public int getSeats() {
		return seats;
	}

	public double getCostPerSeat() {
		return costPerSeat;
	}

	public boolean isDomestic() {
		return true;
	}

	public void parseCode(String code) throws InvalidFlightCodeException {
		Pattern pattern = Pattern.compile("[a-zA-Z]{2}-\\d{4}");
		Matcher m = pattern.matcher(code.trim());
		if (!m.matches()) {
			throw new InvalidFlightCodeException();
		}
	}

	@Override
	public boolean equals(Object object) {
		return this.getCode().equals(((Flight) object).getCode());
	}

	@Override
	public String toString() {
		return code + ", From: " + from + ", To: " + to + ", Day: " + weekday + ", Cost: "
				+ costPerSeat;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

}
