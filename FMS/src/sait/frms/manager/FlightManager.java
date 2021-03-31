package sait.frms.manager;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import sait.frms.exception.InvalidFlightCodeException;
import sait.frms.problemdomain.Flight;

public class FlightManager {
	static String WEEKDAY_ANY = "ANY";
	static String WEEKDAY_SUNDAY = "SUNDAY";
	static String WEEKDAY_MONDAY = "MONDAY";
	static String WEEKDAY_TUESDAY = "TUESDAY";
	static String WEEKDAY_WEDNESDAY = "WEDNESDAY";
	static String WEEKDAY_THURSDAY = "THURSDAY";
	static String WEEKDAY_FRIDAY = "FRIDAY";
	static String WEEKDAY_SATURDAY = "SATURDAY";

	public static final String FLIGHTS_TEXT = "res/flights.csv";
	public static final String AIRPORTS_TEXT = "res/airports.csv";

	private ArrayList<Flight> flights;
	private ArrayList<String> airports;

	public FlightManager() {
		flights = new ArrayList<Flight>();
		airports = new ArrayList<String>();
		populateFlights();
		populateAirports();
	}

	public ArrayList<Flight> getFlights() {
		return flights;
	}

	public ArrayList<String> getAirports() {
		return airports;
	}

	public String findAirportByCode(String code) {
		String airport = "";
		switch (code) {
		case "OA":
			airport = "Otto Airlines";
			break;
		case "CA":
			airport = "Conned Air";
			break;
		case "TB":
			airport = "Try a Bus Airways";
			break;
		case "VA":
			airport = "Vertical Airways";
		}
		return airport;
	}

	public Flight findFlightByCode(String code) {
		Flight flight = new Flight();
		for (Flight f : flights) {
			if (f.getCode().equals(code)) {
				flight = f;
			}
		}
		return flight;
	}

	public ArrayList<Flight> findFlights(String from, String to, String weekday) {
		ArrayList<Flight> newflights = new ArrayList<Flight>();
		for (Flight flight : flights) {
			if (flight.getFrom().equals(from) && flight.getTo().equals(to)) {
				if (weekday.equals(WEEKDAY_ANY)) {
					newflights.add(flight);
				}
				if (weekday.equals(flight.getWeekday().toUpperCase())) {
					newflights.add(flight);
				}
			}
		}
		return newflights;
	}

	private void populateFlights() {
		Scanner sc = null;
		try {
			sc = new Scanner(new File(FLIGHTS_TEXT));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (sc.hasNext()) {
			String entry = sc.nextLine();
			String[] fields = entry.split(",");

			String code = fields[0].trim();
			String airlineName = fields[0].substring(0, 2);
			String from = fields[1];
			String to = fields[2];
			String weekday = fields[3];
			String time = fields[4];
			int seats = Integer.parseInt(fields[5]);
			double costPerSeat = Double.parseDouble(fields[6]);
			Flight newFlight = null;
			try {
				newFlight = new Flight(code, from, to, weekday, time, seats, costPerSeat);
				this.flights.add(newFlight);
			} catch (InvalidFlightCodeException e) {
				System.out.println("Invalid Code Found: " + code);
			}

			// System.out.println("Loading " + newFlight + " into DB.");
		}
	}

	private void populateAirports() {
		try (Scanner sc = new Scanner(new File(AIRPORTS_TEXT))) {
			while (sc.hasNext()) {
				String entry = sc.nextLine();
				String[] fields = entry.split(",");

				String code = fields[0];

				airports.add(code);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
