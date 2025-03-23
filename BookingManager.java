import java.util.List;

public class BookingManager {
    private Flight flight;
    private int flightIndexInFlightList;

    public BookingManager(Flight flight) {
        this.flight = flight;
    }

    public void bookFlight(String flightNo, int numOfTickets, String userID) {
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

    private void addNumberOfTicketsToAlreadyBookedFlight(Customer customer, int numOfTickets) {
        int newNumOfTickets = customer.numOfTicketsBookedByUser.get(flightIndexInFlightList) + numOfTickets;
        customer.numOfTicketsBookedByUser.set(flightIndexInFlightList, newNumOfTickets);
    }

    private void addNumberOfTicketsForNewFlight(Customer customer, int numOfTickets) {
        customer.numOfTicketsBookedByUser.add(numOfTickets);
    }

    private boolean isFlightAlreadyAddedToCustomerList(List<Flight> flightList, Flight flight) {
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

    private int flightIndex(List<Flight> flightList, Flight flight) {
        int i = -1;
        for (Flight flight1 : flightList) {
            if (flight1.equals(flight)) {
                i = flightList.indexOf(flight1);
            }
        }
        return i;
    }
}