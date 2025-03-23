/*
 * FlightReservation class allows the user to book, cancel and check the status of the registered flights.
 *
 *
 * */


import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FlightReservation implements DisplayClass {
    private BookingManager bookingManager;
    private CancellationManager cancellationManager;
    private DisplayManager displayManager;

    public FlightReservation() {
        Flight flight = new Flight();
        this.bookingManager = new BookingManager(flight);
        this.cancellationManager = new CancellationManager(flight);
        this.displayManager = new DisplayManager(flight);
    }

    public void bookFlight(String flightNo, int numOfTickets, String userID) {
        bookingManager.bookFlight(flightNo, numOfTickets, userID);
    }

    public void cancelFlight(String userID) {
        cancellationManager.cancelFlight(userID);
    }

    @Override
    public void displayFlightsRegisteredByOneUser(String userID) {
        displayManager.displayFlightsRegisteredByOneUser(userID);
    }

    @Override
    public void displayHeaderForUsers(Flight flight, List<Customer> c) {
        displayManager.displayHeaderForUsers(flight, c);
    }

    @Override
    public void displayRegisteredUsersForAllFlight() {
        displayManager.displayRegisteredUsersForAllFlight();
    }

    @Override
    public void displayRegisteredUsersForASpecificFlight(String flightNum) {
        displayManager.displayRegisteredUsersForASpecificFlight(flightNum);
    }
}
