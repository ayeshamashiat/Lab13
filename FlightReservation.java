/*
 * FlightReservation class allows the user to book, cancel and check the status of the registered flights.
 *
 *
 * */


import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FlightReservation implements DisplayClass {

    //        ************************************************************ Fields ************************************************************
    Flight flight = new Flight();
    int flightIndexInFlightList;

    //        ************************************************************ Behaviours/Methods ************************************************************


    /**
     * Book the numOfTickets for said flight for the specified user. Update the available seats in main system by
     * Subtracting the numOfTickets from the main system. If a new customer registers for the flight, then it adds
     * the customer to that flight, else if the user is already added to that flight, then it just updates the
     * numOfSeats of that flight.
     *
     * @param flightNo     FlightID of the flight to be booked
     * @param numOfTickets number of tickets to be booked
     * @param userID       userID of the user which is booking the flight
     */
    void bookFlight(String flightNo, int numOfTickets, String userID) {
        boolean isFound = false;
        for (Flight f1 : flight.getFlightList()) {
            if (!flightNo.equalsIgnoreCase(f1.getFlightNumber())) {
                continue;
            }
            for (Customer customer : Customer.customerCollection) {
                if (!userID.equals(customer.getUserID())) {
                    continue;
                }
                isFound = true;
                processBooking(f1, customer, numOfTickets);
                break;
            }
            if (isFound) {
                break;
            }
        }
        displayBookingResult(isFound, flightNo, numOfTickets);
    }

    private void processBooking(Flight flight, Customer customer, int numOfTickets) {
        flight.setNoOfSeatsInTheFlight(flight.getNoOfSeats() - numOfTickets);
        if (!flight.isCustomerAlreadyAdded(flight.getListOfRegisteredCustomersInAFlight(), customer)) {
            flight.addNewCustomerToFlight(customer);
        }
        if (isFlightAlreadyAddedToCustomerList(customer.flightsRegisteredByUser, flight)) {
            addNumberOfTicketsToAlreadyBookedFlight(customer, numOfTickets);
            if (flightIndex(flight.getFlightList(), flight) != -1) {
                customer.addExistingFlightToCustomerList(flightIndex(flight.getFlightList(), flight), numOfTickets);
            }
        } else {
            customer.addNewFlightToCustomerList(flight);
            addNumberOfTicketsForNewFlight(customer, numOfTickets);
        }
    }

    private void displayBookingResult(boolean isFound, String flightNo, int numOfTickets) {
        if (!isFound) {
            System.out.println("Invalid Flight Number...! No flight with the ID \"" + flightNo + "\" was found...");
        } else {
            System.out.printf("\n %50s You've booked %d tickets for Flight \"%5s\"...", "", numOfTickets, flightNo.toUpperCase());
        }
    }

    /**
     * Cancels the flight for a particular user and return/add the numOfTickets back to
     * the main flight scheduler.
     *
     * @param userID    ID of the user for whom the flight is to be cancelled
     */
    void cancelFlight(String userID) {
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

    void addNumberOfTicketsToAlreadyBookedFlight(Customer customer, int numOfTickets) {
        int newNumOfTickets = customer.numOfTicketsBookedByUser.get(flightIndexInFlightList) + numOfTickets;
        customer.numOfTicketsBookedByUser.set(flightIndexInFlightList, newNumOfTickets);
    }

    void addNumberOfTicketsForNewFlight(Customer customer, int numOfTickets) {
        customer.numOfTicketsBookedByUser.add(numOfTickets);
    }

    boolean isFlightAlreadyAddedToCustomerList(List<Flight> flightList, Flight flight) {
        boolean addedOrNot = false;
        for (Flight flight1 : flightList) {
            if (flight1.getFlightNumber().equalsIgnoreCase(flight.getFlightNumber())) {
                this.flightIndexInFlightList = flightList.indexOf(flight1);
                addedOrNot = true;
                break;
            }
        }
        return addedOrNot;
    }

    String flightStatus(Flight flight) {
        boolean isFlightAvailable = false;
        for (Flight list : flight.getFlightList()) {
            if (list.getFlightNumber().equalsIgnoreCase(flight.getFlightNumber())) {
                isFlightAvailable = true;
                break;
            }
        }
        if (isFlightAvailable) {
            return "As Per Schedule";
        } else {
            return "   Cancelled   ";
        }
    }

    /*toString() Method for displaying number of flights registered by single user...*/
    public String toString(int serialNum, Flight flights, Customer customer) {
        return String.format("| %-5d| %-41s | %-9s | \t%-9d | %-21s | %-22s | %-10s  |   %-6sHrs |  %-4s  | %-10s |", serialNum, flights.getFlightSchedule(), flights.getFlightNumber(), customer.numOfTicketsBookedByUser.get(serialNum - 1), flights.getFromWhichCity(), flights.getToWhichCity(), flights.fetchArrivalTime(), flights.getFlightTime(), flights.getGate(), flightStatus(flights));
    }

    @Override
    public void displayFlightsRegisteredByOneUser(String userID) {
        System.out.println();
        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+-----------------+\n");
        System.out.printf("| Num  | FLIGHT SCHEDULE\t\t\t   | FLIGHT NO |  Booked Tickets  | \tFROM ====>>       | \t====>> TO\t   | \t    ARRIVAL TIME       | FLIGHT TIME |  GATE  |  FLIGHT STATUS  |%n");
        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+-----------------+\n");
        for (Customer customer : Customer.customerCollection) {
            List<Flight> f = customer.getFlightsRegisteredByUser();
            int size = customer.getFlightsRegisteredByUser().size();
            if (userID.equals(customer.getUserID())) {
                for (int i = 0; i < size; i++) {
                    System.out.println(toString((i + 1), f.get(i), customer));
                    System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+-----------------+\n");
                }
            }
        }
    }

    /*overloaded toString() method for displaying all users in a flight....*/

    public String toString(int serialNum, Customer customer, int index) {
        return String.format("%10s| %-10d | %-10s | %-32s | %-7s | %-27s | %-35s | %-23s |       %-7s  |", "", (serialNum + 1), customer.randomIDDisplay(customer.getUserID()), customer.getName(),
                customer.getAge(), customer.getEmail(), customer.getAddress(), customer.getPhone(), customer.numOfTicketsBookedByUser.get(index));
    }

    @Override
    public void displayHeaderForUsers(Flight flight, List<Customer> c) {
        System.out.printf("\n%65s Displaying Registered Customers for Flight No. \"%-6s\" %s \n\n", "+++++++++++++", flight.getFlightNumber(), "+++++++++++++");
        System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+----------------+\n", "");
        System.out.printf("%10s| SerialNum  |   UserID   | Passenger Names                  | Age     | EmailID\t\t       | Home Address\t\t\t     | Phone Number\t       | Booked Tickets |%n", "");
        System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+----------------+\n", "");
        int size = flight.getListOfRegisteredCustomersInAFlight().size();
        for (int i = 0; i < size; i++) {
            System.out.println(toString(i, c.get(i), flightIndex(c.get(i).flightsRegisteredByUser, flight)));
            System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+----------------+\n", "");
        }
    }

    @Override
    public void displayRegisteredUsersForAllFlight() {
        System.out.println();
        for (Flight flight : flight.getFlightList()) {
            List<Customer> c = flight.getListOfRegisteredCustomersInAFlight();
            int size = flight.getListOfRegisteredCustomersInAFlight().size();
            if (size != 0) {
                displayHeaderForUsers(flight, c);
            }
        }
    }

    int flightIndex(List<Flight> flightList, Flight flight) {
        int i = -1;
        for (Flight flight1 : flightList) {
            if (flight1.equals(flight)) {
                i = flightList.indexOf(flight1);
            }
        }
        return i;
    }

    @Override
    public void displayRegisteredUsersForASpecificFlight(String flightNum) {
        System.out.println();
        for (Flight flight : flight.getFlightList()) {
            List<Customer> c = flight.getListOfRegisteredCustomersInAFlight();
            if (flight.getFlightNumber().equalsIgnoreCase(flightNum)) {
                displayHeaderForUsers(flight, c);
            }
        }
    }


}
