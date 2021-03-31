package sait.frms.gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

import sait.frms.exception.*;
import sait.frms.manager.FlightManager;
import sait.frms.manager.ReservationManager;
import sait.frms.problemdomain.Flight;

public class FlightsTab extends TabBase {
	private FlightManager flightManager;

	private ReservationManager reservationManager;

	private JList<Flight> flightsList;
	private MyListSelectionListener listSelectionListener;

	private DefaultListModel<Flight> flightsModel;

	private JLabel dayLabel;
	private JLabel fromLabel;
	private JLabel toLabel;

	private JComboBox<String> dayComboBox;
	private JComboBox<String> fromComboBox;
	private JComboBox<String> toComboBox;

	private JButton reserveButton;
	private JButton findButton;

	/**
	 * Creates the Labels for flights tab.
	 */
	private JLabel flightLabel;
	private JLabel airlineLabel;
	private JLabel timeLabel;
	private JLabel costLabel;
	private JLabel nameLabel;
	private JLabel citizenshipLabel;

	/**
	 * Creates the TextField for flights tab.
	 */
	private JTextField flightTextField;
	private JTextField airlineTextField;
	private JTextField dayTextField;
	private JTextField timeTextField;
	private JTextField costTextField;
	private JTextField nameTextField;
	private JTextField citizenshipTextField;

	public FlightsTab(FlightManager flightManager, ReservationManager reservationManager) {
		this.flightManager = flightManager;
		this.reservationManager = reservationManager;

		listSelectionListener = new MyListSelectionListener();

		panel.setLayout(new BorderLayout());

		JPanel northPanel = createNorthPanel();
		panel.add(northPanel, BorderLayout.NORTH);

		JPanel centerPanel = createCenterPanel();
		panel.add(centerPanel, BorderLayout.CENTER);

		JPanel eastPanel = createEastPanel();
		panel.add(eastPanel, BorderLayout.EAST);

		JPanel southPanel = createSouthPanel();
		panel.add(southPanel, BorderLayout.SOUTH);

	}

	private JPanel createNorthPanel() {
		JPanel panel = new JPanel();

		JLabel title = new JLabel("Flights", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		panel.add(title);

		return panel;
	}

	private JPanel createCenterPanel() {
		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());

		flightsModel = new DefaultListModel<>();
		flightsList = new JList<>(flightsModel);

		// User can only select one item at a time.
		flightsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Wrap JList in JScrollPane so it is scrollable.
		JScrollPane scrollPane = new JScrollPane(this.flightsList);

		flightsList.addListSelectionListener(listSelectionListener);

		panel.add(scrollPane, BorderLayout.NORTH);

		JLabel padding = new JLabel("");
		panel.add(padding, BorderLayout.CENTER);

		return panel;
	}

	private JPanel createSouthPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		JPanel southWestPanel = new JPanel(new GridLayout(3, 1));

		JLabel southTitle = new JLabel("Flight Finder");
		southTitle.setFont(new Font("Serif", Font.PLAIN, 29));
		southTitle.setHorizontalAlignment(JLabel.CENTER);

		panel.add(southTitle, BorderLayout.NORTH);

		/**
		 * Panel for Bottom Left
		 */
		fromLabel = new JLabel("From:");
		fromLabel.setHorizontalAlignment(JLabel.RIGHT);

		toLabel = new JLabel("To:");
		toLabel.setHorizontalAlignment(JLabel.RIGHT);

		dayLabel = new JLabel("Day:");
		dayLabel.setHorizontalAlignment(JLabel.RIGHT);

		/**
		 * Panel for Bottom Left
		 */
		southWestPanel.add(fromLabel);
		southWestPanel.add(toLabel);
		southWestPanel.add(dayLabel);
		panel.add(southWestPanel, BorderLayout.WEST);

		/**
		 * Panel for Bottom Center
		 */
		JPanel southCenterPanel = new JPanel(new GridLayout(3, 0));
		fromComboBox = new JComboBox(flightManager.getAirports().toArray());
		toComboBox = new JComboBox(flightManager.getAirports().toArray());
		dayComboBox = new JComboBox<String>();
		dayComboBox.addItem("ANY");
		dayComboBox.addItem("SUNDAY");
		dayComboBox.addItem("MONDAY");
		dayComboBox.addItem("TUESDAY");
		dayComboBox.addItem("WEDNESDAY");
		dayComboBox.addItem("THURSDAY");
		dayComboBox.addItem("FRIDAY");
		dayComboBox.addItem("SATURDAY");
		southCenterPanel.add(fromComboBox);
		southCenterPanel.add(toComboBox);
		southCenterPanel.add(dayComboBox);
		panel.add(southCenterPanel, BorderLayout.CENTER);

		/**
		 * Panel for Bottom bottom
		 */
		findButton = new JButton("Find Flights");
		findButton.addActionListener(new MyButtonActionListener());
		panel.add(findButton, BorderLayout.SOUTH);

		return panel;
	}

	private JPanel createEastPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		flightLabel = new JLabel("Flight:");
		flightTextField = new JTextField(15);
		flightTextField.setEditable(false);
		flightLabel.setHorizontalAlignment(JLabel.RIGHT);

		airlineLabel = new JLabel("Airline:");
		airlineTextField = new JTextField(15);
		airlineTextField.setEditable(false);
		airlineLabel.setHorizontalAlignment(JLabel.RIGHT);

		dayLabel = new JLabel("Day:");
		dayTextField = new JTextField(15);
		dayTextField.setEditable(false);
		dayLabel.setHorizontalAlignment(JLabel.RIGHT);

		timeLabel = new JLabel("Time:");
		timeTextField = new JTextField(15);
		timeTextField.setEditable(false);
		timeLabel.setHorizontalAlignment(JLabel.RIGHT);

		costLabel = new JLabel("Cost:");
		costTextField = new JTextField(15);
		costTextField.setEditable(false);
		costLabel.setHorizontalAlignment(JLabel.RIGHT);

		nameLabel = new JLabel("Name:");
		nameTextField = new JTextField(15);
		nameLabel.setHorizontalAlignment(JLabel.RIGHT);

		citizenshipLabel = new JLabel("Citizenship:");
		citizenshipTextField = new JTextField(15);
		citizenshipLabel.setHorizontalAlignment(JLabel.RIGHT);

		JPanel eastNouthPanel = new JPanel(new GridLayout(2, 0));
		JLabel eastTitle = new JLabel("Reserve");
		eastTitle.setFont(new Font("Serif", Font.PLAIN, 29));
		eastTitle.setHorizontalAlignment(JLabel.CENTER);
		JLabel padding = new JLabel();

		eastNouthPanel.add(eastTitle);
		eastNouthPanel.add(padding);

		panel.add(eastNouthPanel, BorderLayout.NORTH);

		/**
		 * Panel for the Right Left
		 */
		JPanel eastWestPanel = new JPanel(new GridLayout(7, 1));
		eastWestPanel.add(flightLabel);
		eastWestPanel.add(airlineLabel);
		eastWestPanel.add(dayLabel);
		eastWestPanel.add(timeLabel);
		eastWestPanel.add(costLabel);
		eastWestPanel.add(nameLabel);
		eastWestPanel.add(citizenshipLabel);
		panel.add(eastWestPanel, BorderLayout.WEST);

		/**
		 * Panel for the East Center
		 */
		JPanel eastCenterPanel = new JPanel(new GridLayout(7, 0));
		eastCenterPanel.add(flightTextField);
		eastCenterPanel.add(airlineTextField);
		eastCenterPanel.add(dayTextField);
		eastCenterPanel.add(timeTextField);
		eastCenterPanel.add(costTextField);
		eastCenterPanel.add(nameTextField);
		eastCenterPanel.add(citizenshipTextField);
		panel.add(eastCenterPanel, BorderLayout.CENTER);

		JPanel eastSouthPanel = new JPanel(new GridLayout(2, 0));
		reserveButton = new JButton("Reserve");
		eastSouthPanel.add(padding);
		eastSouthPanel.add(reserveButton);
		reserveButton.addActionListener(new MyButtonActionListener());
		panel.add(eastSouthPanel, BorderLayout.SOUTH);

		return panel;
	}

	private class MyListSelectionListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			Flight selectedFlight = flightsList.getSelectedValue();
			if (selectedFlight != null) {
			flightTextField.setText(selectedFlight.getCode());
			airlineTextField.setText(flightManager.findAirportByCode(selectedFlight.getAirline()));
			dayTextField.setText(selectedFlight.getWeekday());
			timeTextField.setText(selectedFlight.getTime());
			costTextField.setText(String.format("$%.2f", selectedFlight.getCostPerSeat()));
			}
		}
	}

	private class MyButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == findButton) {
				String from = fromComboBox.getSelectedItem().toString();
				String to = toComboBox.getSelectedItem().toString();
				String weekday = dayComboBox.getSelectedItem().toString();

				flightsModel.removeAllElements();
				ArrayList<Flight> flights = new ArrayList<>();
				flights = flightManager.findFlights(from, to, weekday);
				if (flights != null) {
					for (Flight f : flights) {
						flightsModel.addElement(f);
					}
				}
			}
			if (e.getSource()== reserveButton) {
				Flight selectedFlight = flightsList.getSelectedValue();
				try {
					String code = reservationManager.makeReservation(selectedFlight, nameTextField.getText(), citizenshipTextField.getText()).getCode();
					JOptionPane.showMessageDialog(reserveButton, "Reservation created. Your code is " + code);
				} catch (InvalidNameException e1) {
					JOptionPane.showMessageDialog(nameTextField, e1.getMessage());
				} catch (InvalidCitizenshipException e1) {
					JOptionPane.showMessageDialog(citizenshipTextField, e1.getMessage());
				} catch (NoMoreSeatsException e1) {
					JOptionPane.showMessageDialog(flightLabel, e1.getMessage());
				} catch (NullFlightException e1) {
					JOptionPane.showMessageDialog(flightLabel, e1.getMessage());
				} catch (InvalidFlightCodeException e1) {
					JOptionPane.showMessageDialog(flightLabel, e1.getMessage());
				}
				reservationManager.persist();
			}
		}
	}
}