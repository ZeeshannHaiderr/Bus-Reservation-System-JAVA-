// Open Ended Lab Task (BUS RESERVATION SYSTEM)
// Made by : 1.Sameer Nadeem 2.Zeeshan Haider 3.Hajra
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
public class BusReservationSystemGUI extends JFrame {
    private JPanel panel;
    private JButton faresButton;
    private JButton availabilityButton;
    private JButton statusButton;
    private JButton reservationButton;
    private int[][] seats;
    private int[] availableBuses;
    private int totalSeatsBooked;
    private String username;
    public BusReservationSystemGUI(String username)
    {
        this.username = username;
        seats = new int[5][10]; // 5 destinations, 10 seats per bus
        availableBuses = new int[5]; // Number of available buses for each destination
        for (int i = 0; i < 5; i++)
        {
            availableBuses[i] = 5; // Initialize with 5 buses for each destination
        }
        totalSeatsBooked = 0;
        setTitle("Bus Reservation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        faresButton = createButton("Fares", "Display fare of every destination");
        panel.add(faresButton);
        availabilityButton = createButton("Availability", "Display availability of buses for any destination and seats");
        panel.add(availabilityButton);
        statusButton = createButton("No. of seats available", "Display the status of all buses to any destination");
        panel.add(statusButton);
        reservationButton = createButton("Reservation", "Reserve seats in any bus based on destination and availability");
        panel.add(reservationButton);
        add(panel);
    }
    private JButton createButton(String label, String tooltip)
    {
        JButton button = new JButton(label);
        button.setToolTipText(tooltip);
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (label.equals("Fares"))
                {
                    JOptionPane.showMessageDialog(null, "Fares: \n\nMabali (Destination 1): Rs 1000 \nDamn-e-Koh (Destination 2): Rs 1200 \nJoyLand (Destination 3): Rs 900 \nGIKI (Destination 4): Rs 2500 \nMCS (Destination 5): Rs 500");
                }
                else if (label.equals("Availability"))
                {
                    StringBuilder availability = new StringBuilder("Bus Availability:\n\n");
                    for (int i = 0; i < 5; i++)
                    {
                        availability.append("Destination ").append(i + 1).append(": ").append(availableBuses[i]).append(" buses available\n");
                    }
                    JOptionPane.showMessageDialog(null, availability.toString());
                }
                else if (label.equals("No. of seats available"))
                {
                    StringBuilder status = new StringBuilder("Bus Status:\n\n");
                    for (int i = 0; i < 5; i++)
                    {
                        status.append("Destination ").append(i + 1).append(": ");
                        if (availableBuses[i] == 0)
                        {
                            for (int j = 0; j < 10; j++)
                            {
                                status.append("[X]");
                            }
                        }
                        else
                        {
                            for (int j = 0; j < 10; j++)
                            {
                                status.append(seats[i][j] == 0 ? "[ ]" : "[X]");
                            }
                        }
                        status.append("\n");
                    }
                    JOptionPane.showMessageDialog(null, status.toString());
                }
                else if (label.equals("Reservation"))
                {
                    String input = JOptionPane.showInputDialog(null, "Enter destination (1-5):");
                    try
                    {
                        int destination = Integer.parseInt(input);
                        if (destination >= 1 && destination <= 5)
                        {
                            int busIndex = destination - 1;
                            if (availableBuses[busIndex] == 0)
                            {
                                JOptionPane.showMessageDialog(null, "No seats available for the selected destination.");
                                return;
                            }
                            int availableSeats = 0;
                            for (int j = 0; j < 10; j++)
                            {
                                if (seats[busIndex][j] == 0)
                                {
                                    availableSeats++;
                                }
                            }
                            String seatInput = JOptionPane.showInputDialog(null, "Enter number of seats to reserve (1-" + availableSeats + "):");
                            try
                            {
                                int numSeats = Integer.parseInt(seatInput);
                                if (numSeats >= 1 && numSeats <= availableSeats)
                                {
                                    int reserved = 0;
                                    for (int j = 0; j < 10; j++)
                                    {
                                        if (seats[busIndex][j] == 0 && reserved < numSeats)
                                        {
                                            seats[busIndex][j] = 1;
                                            reserved++;
                                        }
                                    }
                                    totalSeatsBooked += reserved;
                                    if (totalSeatsBooked == 10)
                                    {
                                        availableBuses[busIndex]--;
                                        resetSeats(busIndex);
                                        totalSeatsBooked = 0;
                                    }
                                    if (totalSeatsBooked == 50)
                                    {
                                        resetAllSeats();
                                        totalSeatsBooked = 0;
                                    }
                                    String code = generateRandomCode();
                                    JOptionPane.showMessageDialog(null, "Seats reserved successfully for "+username+"!\n\nYour code: " + code + "\nPlease show this code at the station to get your ticket.");
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null, "Invalid number of seats.");
                                }
                            }
                            catch (NumberFormatException ex)
                            {
                                JOptionPane.showMessageDialog(null, "Invalid input.");
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Invalid destination.");
                        }
                    }
                    catch (NumberFormatException ex)
                    {
                        JOptionPane.showMessageDialog(null, "Invalid input.");
                    }
                }
            }
        });
        return button;
    }
    private void resetSeats(int busIndex)
    {
        for (int j = 0; j < 10; j++)
        {
            seats[busIndex][j] = 0;
        }
    }
    private void resetAllSeats()
    {
        for (int i = 0; i < 5; i++)
        {
            resetSeats(i);
        }
    }
    private String generateRandomCode()
    {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Generate a 6-digit random code
        return String.valueOf(code);
    }
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                String username = JOptionPane.showInputDialog(null, "Welcome to OEL Bus Reservation System!\n\nEnter your name to begin your reservation:");
                if (username != null && !username.isEmpty())
                {
                    BusReservationSystemGUI frame = new BusReservationSystemGUI(username);
                    frame.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Invalid username. Exiting the application.");
                    System.exit(0);
                }
            }
        });
    }
}