package sait.frms.gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sait.frms.exception.InvalidCitizenshipException;
import sait.frms.exception.InvalidNameException;
import sait.frms.manager.FlightManager;
import sait.frms.manager.ReservationManager;
import sait.frms.problemdomain.Reservation;

/**
 * Holds the components for the reservations tab.
 * 
 */
public class ReservationsTab extends TabBase {
	/**
	 * Instance of reservation manager.
	 */
	private ReservationManager reservationManager;
	private FlightManager flightManager;

	private JLabel codeLabel;
	private JTextField codeTextField;
	private JLabel flightLabel;
	private JTextField flightTextField;
	private JLabel airlineLabel;
	private JTextField airlineTextField;
	private JLabel costLabel;
	private JTextField costTextField;
	private JLabel nameLabel;
	private JTextField nameTextField;
	private JLabel citizenshipLabel;
	private JTextField citizenshipTextField;
	private JLabel statusLabel;
	private JComboBox<String> statusComboBox;

	private JList<Reservation> reservationsList;
	private DefaultListModel<Reservation> reservationsModel;

	private JButton findButton;
	private JButton updateButton;

	private JTextField searchAirlineTextField;
	private JTextField searchNameTextField;
	private JTextField searchCodeTextField;

	private JLabel searchCodeLabel;
	private JLabel searchAirlineLabel;
	private JLabel searchNameLabel;

	/**
	 * Creates the components for reservations tab.
	 */
	public ReservationsTab(ReservationManager reservationManager) {
		this.reservationManager = reservationManager;
		this.flightManager = new FlightManager();
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

	/**
	 * Creates the north panel.
	 * 
	 * @return JPanel that goes in north.
	 */
	private JPanel createNorthPanel() {
		JPanel panel = new JPanel();

		JLabel title = new JLabel("Reservations", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		panel.add(title);

		return panel;
	}

	private JPanel createCenterPanel() {
		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());

		reservationsModel = new DefaultListModel<>();
		reservationsList = new JList<>(reservationsModel);

		// User can only select one item at a time.
		reservationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Wrap JList in JScrollPane so it is scrollable.
		JScrollPane scrollPane = new JScrollPane(this.reservationsList);

		reservationsList.addListSelectionListener(new MyListSelectionListener());

		panel.add(scrollPane);

		return panel;
	}

	private JPanel createSouthPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		JPanel southWestPanel = new JPanel(new GridLayout(3, 1));

		JLabel southTitle = new JLabel("Search");
		southTitle.setFont(new Font("Serif", Font.PLAIN, 29));
		southTitle.setHorizontalAlignment(JLabel.CENTER);

		panel.add(southTitle, BorderLayout.NORTH);

		/**
		 * Panel for Bottom Left
		 */
		searchCodeLabel = new JLabel("Code:");
		searchCodeTextField = new JTextField();
		searchCodeLabel.setHorizontalAlignment(JLabel.RIGHT);

		searchAirlineLabel = new JLabel("AirLine:");
		searchAirlineTextField = new JTextField();
		searchAirlineLabel.setHorizontalAlignment(JLabel.RIGHT);

		searchNameLabel = new JLabel("Name:");
		searchNameTextField = new JTextField();
		searchNameLabel.setHorizontalAlignment(JLabel.RIGHT);
		/**
		 * Panel for Bottom Left
		 */
		southWestPanel.add(searchCodeLabel);
		southWestPanel.add(searchAirlineLabel);
		southWestPanel.add(searchNameLabel);
		panel.add(southWestPanel, BorderLayout.WEST);

		/**
		 * Panel for Bottom Center
		 */
		JPanel southCenterPanel = new JPanel(new GridLayout(3, 0));
		southCenterPanel.add(searchCodeTextField);
		southCenterPanel.add(searchAirlineTextField);
		southCenterPanel.add(searchNameTextField);
		panel.add(southCenterPanel, BorderLayout.CENTER);

		/**
		 * Panel for Bottom bottom
		 */
		findButton = new JButton("Find Reservations");
		findButton.addActionListener(new MyButtonActionListener());
		panel.add(findButton, BorderLayout.SOUTH);

		return panel;
	}

	private JPanel createEastPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		codeLabel = new JLabel("Code:");
		codeTextField = new JTextField(15);
		codeTextField.setEditable(false);
		codeLabel.setHorizontalAlignment(JLabel.RIGHT);

		flightLabel = new JLabel("Flight:");
		flightTextField = new JTextField(15);
		flightTextField.setEditable(false);
		flightLabel.setHorizontalAlignment(JLabel.RIGHT);

		airlineLabel = new JLabel("Airline:");
		airlineTextField = new JTextField(15);
		airlineTextField.setEditable(false);
		airlineLabel.setHorizontalAlignment(JLabel.RIGHT);

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

		String[] statuslist = { "Active", "Inactive" };
		statusLabel = new JLabel("Status:");
		statusComboBox = new JComboBox<String>(statuslist);
		statusComboBox.addActionListener(new MyButtonActionListener());
		statusLabel.setHorizontalAlignment(JLabel.RIGHT);

		JPanel eastNorthPanel = new JPanel(new GridLayout(2, 0));
		JLabel eastTitle = new JLabel("Reserve");
		eastTitle.setFont(new Font("Serif", Font.PLAIN, 29));
		eastTitle.setHorizontalAlignment(JLabel.CENTER);
		JLabel padding = new JLabel();
		eastNorthPanel.add(eastTitle);
		eastNorthPanel.add(padding);

		panel.add(eastNorthPanel, BorderLayout.NORTH);

		/**
		 * Panel for the Right Left
		 */
		JPanel eastWestPanel = new JPanel(new GridLayout(7, 1));
		eastWestPanel.add(codeLabel);
		eastWestPanel.add(flightLabel);
		eastWestPanel.add(airlineLabel);
		eastWestPanel.add(costLabel);
		eastWestPanel.add(nameLabel);
		eastWestPanel.add(citizenshipLabel);
		eastWestPanel.add(statusLabel);
		panel.add(eastWestPanel, BorderLayout.WEST);

		/**
		 * Panel for the East Center
		 */
		JPanel eastCenterPanel = new JPanel(new GridLayout(7, 0));
		eastCenterPanel.add(codeTextField);
		eastCenterPanel.add(flightTextField);
		eastCenterPanel.add(airlineTextField);
		eastCenterPanel.add(costTextField);
		eastCenterPanel.add(nameTextField);
		eastCenterPanel.add(citizenshipTextField);
		eastCenterPanel.add(statusComboBox);
		panel.add(eastCenterPanel, BorderLayout.CENTER);

		JPanel eastSouthPanel = new JPanel(new GridLayout(2, 0));
		updateButton = new JButton("Update");
		eastSouthPanel.add(padding);
		eastSouthPanel.add(updateButton);
		updateButton.addActionListener(new MyButtonActionListener());
		panel.add(eastSouthPanel, BorderLayout.SOUTH);

		return panel;
	}

	private class MyListSelectionListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getSource() == reservationsList) {
				Reservation selectedReservation = reservationsList.getSelectedValue();
				if (selectedReservation != null) {
				codeTextField.setText(selectedReservation.getCode());
				flightTextField.setText(selectedReservation.getFlightCode());
				airlineTextField.setText(flightManager.findAirportByCode(selectedReservation.getAirline()));
				costTextField.setText(String.format("$%.2f", selectedReservation.getCost()));
				nameTextField.setText(selectedReservation.getName());
				citizenshipTextField.setText(selectedReservation.getCitizenship());
				}
			}
		}
	}

	private class MyButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == findButton) {
				reservationsModel.removeAllElements();
				ArrayList<Reservation> reservationsFound = reservationManager.findReservations(
						searchCodeTextField.getText(), searchAirlineTextField.getText(), searchNameTextField.getText());
				for (Reservation r : reservationsFound) {
					reservationsModel.addElement(r);
				}
			}
			if (e.getSource() == updateButton) {
				Reservation reservationToBeChanged = reservationsList.getSelectedValue();
				if (reservationToBeChanged != null) {
					try {
						reservationToBeChanged.setName(nameTextField.getText());
						reservationToBeChanged.setCitizenship(citizenshipTextField.getText());
						boolean active = statusComboBox.getSelectedItem().equals("Active");
						reservationToBeChanged.setActive(active);
						JOptionPane.showMessageDialog(updateButton, "Reservation Updated!");
						// If selected reservation changed to inactive, increase the flight available seat by 1
						if (!active) {
							reservationsModel.removeElement(reservationToBeChanged);
							reservationManager.removeElementsFromList(reservationToBeChanged);
							flightManager.findFlightByCode(flightTextField.getText())
							.setSeats(flightManager.findFlightByCode(flightTextField.getText()).getSeats()+1);
						}
					} catch (InvalidNameException e1) {
						JOptionPane.showMessageDialog(nameTextField, e1.getMessage());
					} catch (InvalidCitizenshipException e1) {
						JOptionPane.showMessageDialog(citizenshipTextField, e1.getMessage());
					}
				}
			}

		}

	}
}
