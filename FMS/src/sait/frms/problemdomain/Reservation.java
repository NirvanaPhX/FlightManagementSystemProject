package sait.frms.problemdomain;

import sait.frms.exception.InvalidCitizenshipException;
import sait.frms.exception.InvalidNameException;

public class Reservation {
	private String code; 
	private String flightCode; 
	private String airline; 
	private String name; 
	private String citizenship; 
	private double cost; 
	private boolean active; 
	
	static int RECORD_SIZE = 5 + 1 + 7 + 1 + 17 + 1 + 100 + 1 + 100 + 1 + 8 + 1 + 1 + 1;
	
	public Reservation(String code, String flightCode, String airline, String name, String citizenship, double cost) 
			throws InvalidNameException,
				   InvalidCitizenshipException {
		if (name==null||name.equals("")) throw new InvalidNameException();
		if (citizenship==null||citizenship.equals("")) throw new InvalidCitizenshipException();

		this.code = code;
		this.flightCode = flightCode;
		this.airline = airline;
		this.name = name;
		this.citizenship = citizenship;
		this.cost = cost;
		this.active = true;
	}
	
	public Reservation(String reservationCode, Flight flight, String name, String citizenship) 
			throws InvalidNameException,
				   InvalidCitizenshipException {
		this(reservationCode, flight.getCode(), flight.getAirline(), name, citizenship, flight.getCostPerSeat());
		this.active = true;
	}

	public String getCode() {
		return code;
	}

	public String getFlightCode() {
		return flightCode;
	}

	public String getAirline() {
		return airline;
	}

	public String getName() {
		return name;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public double getCost() {
		return cost;
	}

	public boolean isActive() {
		return active;
	}

	public void setName(String name) throws InvalidNameException {
		if (name==null || name.equals(""))
			throw new InvalidNameException();
		this.name = name;
	}

	public void setCitizenship(String citizenship) throws InvalidCitizenshipException {
		if (citizenship == null || citizenship.equals(""))
			throw new InvalidCitizenshipException();
		this.citizenship = citizenship;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
//		return code + "," + flightCode + "," + airline + "," + name + "," + citizenship + "," + cost;
		return code;
	}	
}
