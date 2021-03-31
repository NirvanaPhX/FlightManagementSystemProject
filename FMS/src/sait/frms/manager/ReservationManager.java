package sait.frms.manager;

import java.io.*;
import java.util.ArrayList;

import sait.frms.exception.*;
import sait.frms.problemdomain.Flight;
import sait.frms.problemdomain.Reservation;

public class ReservationManager {
	private ArrayList<Reservation> reservations;
	static String RESERVATIONS_BINARY = "res/reservations.bin";
	private FlightManager flightManager = new FlightManager();

	public ReservationManager() {
		populateFromBinary();
	}

	public Reservation makeReservation(Flight flight, String name, String citizenship) throws InvalidNameException,
			InvalidCitizenshipException, NoMoreSeatsException, NullFlightException, InvalidFlightCodeException {
		if (name == null || name.equals(""))
			throw new InvalidNameException();
		if (citizenship == null || citizenship.equals(""))
			throw new InvalidCitizenshipException();
		if (flight == null)
			throw new InvalidFlightCodeException();
		if (flight.getSeats() < 1)
			throw new NoMoreSeatsException();

		String reservationCode = generateReservationCode(flight);

		Reservation reservation = new Reservation(reservationCode, flight, name, citizenship);

		flight.setSeats(flight.getSeats() - 1);
		reservations.add(reservation);
		return new Reservation(reservationCode, flight, name, citizenship);
	}

	public ArrayList<Reservation> findReservations(String reservationCode, String airline, String name) {
		ArrayList<Reservation> reservationlist = new ArrayList<>();
		if ((!reservationCode.equals("")) && reservationCode != null) {
			reservationlist.add(findReservationByCode(reservationCode));
		}
		if ((!name.equals("")) && name != null) {
			for (Reservation r : reservations) {
				if (r.getName().contains(name)) {
					reservationlist.add(r);
				}
			}
		}
		if ((!airline.equals("")) && airline != null) {
			for (Reservation r : reservations) {
				if (r.getAirline().equals(airline)) {
					reservationlist.add(r);
				}
			}
		}
		return reservationlist;
	}

	public Reservation findReservationByCode(String reservationCode) {
		populateFromBinary();
		Reservation reservation = null;
		System.out.println(reservations);
		for (Reservation r : reservations) {
			if (r.getCode().equals(reservationCode)) {
				reservation = r;
			}
		}
		return reservation;
	}

	public void persist() {
		try (RandomAccessFile raf = new RandomAccessFile(RESERVATIONS_BINARY, "rw")) {
			for (Reservation reservation : reservations) {
				if (reservation.isActive()) {
					raf.writeUTF(String.format("%-5s", reservation.getCode()));
					raf.writeUTF(String.format("%-7s", reservation.getFlightCode()));
					raf.writeUTF(String.format("%-17s", reservation.getAirline()));
					raf.writeUTF(String.format("%-100s", reservation.getName()));
					raf.writeUTF(String.format("%-100s", reservation.getCitizenship()));
					raf.writeDouble(reservation.getCost());
					raf.writeBoolean(reservation.isActive());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getAvailableSeats(Flight flight) {
		return flight.getSeats();
	}

	public String generateReservationCode(Flight flight) {
		String codeLetter1 = (!flight.getFrom().substring(0, 1).equals("Y")) ? "I"
				: flight.getTo().substring(0, 1).equals("Y") ? "D" : "I";
		int number = (int) (Math.random() * 9000 + 1000);
		String random4Digits = String.format("%04d", number);
		return codeLetter1 + random4Digits;
	}

	public void populateFromBinary() {
		reservations = new ArrayList<>();
		try (RandomAccessFile raf = new RandomAccessFile(RESERVATIONS_BINARY, "r")) {
			for (long position = 0; position < raf.length(); position += 248) {
				raf.seek(position);
				String reservationCode = raf.readUTF().trim();
				String flightCode = raf.readUTF().trim();
				String airline = flightManager.findAirportByCode(raf.readUTF().trim());
				String name = raf.readUTF().trim();
				String citizenship = raf.readUTF().trim();
				double cost = raf.readDouble();
				reservations.add(new Reservation(reservationCode, flightCode, airline, name, citizenship, cost));
			}
		} catch (EOFException e) {
			System.out.println("Reach end of File");
		} catch (FileNotFoundException e) {
			System.out.println("File doesn't exist yet.");
		} catch (IOException e) {
			System.out.println("No such file please check your file path and name.");
		} catch (InvalidNameException e) {
			e.getMessage();
		} catch (InvalidCitizenshipException e) {
			e.getMessage();
		}
	}

	public ArrayList<String> getCodeListFromBinary() {
		ArrayList<String> codeList = new ArrayList<>();
		try (RandomAccessFile raf = new RandomAccessFile(RESERVATIONS_BINARY, "r")) {
			for (long position = 0; position < raf.length(); position += 248) {
				raf.seek(position);
				String reservationCode = raf.readUTF().trim();
				codeList.add(reservationCode);
			}
		} catch (EOFException e) {
			System.out.println("Reach end of File");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("No such file please check your file path and name.");
		}
		return codeList;

	}
	
	public void removeElementsFromList(Reservation r) {
		reservations.remove(r);
	}
}
