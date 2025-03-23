import java.util.Iterator;
import java.util.Scanner;

public class CancellationManager {
    private Flight flight;

    public CancellationManager(Flight flight) {
        this.flight = flight;
    }
    
        private void displayFlightsRegisteredByOneUser(String userID) {
            Customer customer = findCustomerByID(userID);
            if (customer != null) {
                for (Flight flight : customer.getFlightsRegisteredByUser()) {
                    System.out.println(flight);
                }
            } else {
                System.out.println("No flights found for user ID: " + userID);
            }
        }

    public void cancelFlight(String userID) {
        Scanner read = new Scanner(System.in);
        Customer customer = findCustomerByID(userID);
        if (customer != null) {
            if (!customer.getFlightsRegisteredByUser().isEmpty()) {
                System.out.printf("%50s %s Here is the list of all the Flights registered by you %s", " ", "++++++++++++++", "++++++++++++++");
                displayFlightsRegisteredByOneUser(userID);
                String flightNum = promptUserInput(read, "Enter the Flight Number of the Flight you want to cancel : ");
                int numOfTickets = Integer.parseInt(promptUserInput(read, "Enter the number of tickets to cancel : "));
                cancelCustomerFlight(customer, flightNum, numOfTickets, read);
            } else {
                System.out.println("No Flight Has been Registered by you.");
            }
        } else {
            System.out.println("ERROR!!! Couldn't find Customer with ID \"" + userID + "\".....");
        }
    }

    private Customer findCustomerByID(String userID) {
        for (Customer customer : Customer.customerCollection) {
            if (userID.equals(customer.getUserID())) {
                return customer;
            }
        }
        return null;
    }

    private String promptUserInput(Scanner read, String message) {
        System.out.print(message);
        return read.nextLine();
    }

    private void cancelCustomerFlight(Customer customer, String flightNum, int numOfTickets, Scanner read) {
        boolean isFound = false;
        int index = 0;
        Iterator<Flight> flightIterator = customer.getFlightsRegisteredByUser().iterator();
        while (flightIterator.hasNext()) {
            Flight flight = flightIterator.next();
            if (flightNum.equalsIgnoreCase(flight.getFlightNumber())) {
                isFound = true;
                handleFlightCancellation(customer, flight, numOfTickets, index, read, flightIterator);
                break;
            }
            index++;
        }
        if (!isFound) {
            System.out.println("ERROR!!! Couldn't find Flight with ID \"" + flightNum.toUpperCase() + "\".....");
        }
    }

    private void handleFlightCancellation(Customer customer, Flight flight, int numOfTickets, int index, Scanner read, Iterator<Flight> flightIterator) {
        int numOfTicketsForFlight = customer.getNumOfTicketsBookedByUser().get(index);
        numOfTickets = validateNumOfTickets(numOfTickets, numOfTicketsForFlight, read);
        updateFlightAndCustomerData(customer, flight, numOfTickets, numOfTicketsForFlight, index, flightIterator);
    }

    private int validateNumOfTickets(int numOfTickets, int numOfTicketsForFlight, Scanner read) {
        while (numOfTickets > numOfTicketsForFlight) {
            System.out.print("ERROR!!! Number of tickets cannot be greater than " + numOfTicketsForFlight + " for this flight. Please enter the number of tickets again : ");
            numOfTickets = read.nextInt();
        }
        return numOfTickets;
    }

    private void updateFlightAndCustomerData(Customer customer, Flight flight, int numOfTickets, int numOfTicketsForFlight, int index, Iterator<Flight> flightIterator) {
        int ticketsToBeReturned;
        if (numOfTicketsForFlight == numOfTickets) {
            ticketsToBeReturned = flight.getNoOfSeats() + numOfTicketsForFlight;
            customer.getNumOfTicketsBookedByUser().remove(index);
            flightIterator.remove();
        } else {
            ticketsToBeReturned = numOfTickets + flight.getNoOfSeats();
            customer.getNumOfTicketsBookedByUser().set(index, (numOfTicketsForFlight - numOfTickets));
        }
        flight.setNoOfSeatsInTheFlight(ticketsToBeReturned);
    }
}