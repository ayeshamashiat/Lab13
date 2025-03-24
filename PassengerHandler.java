import java.util.Scanner;

public class PassengerHandler {

    public static void handlePassengerLogin(RolesAndPermissions r1, Customer c1, FlightReservation bookingAndReserving, Scanner read, Scanner read1) {
        System.out.print("\n\nEnter the Email to Login : \t");
        String userName = read1.nextLine();
        System.out.print("Enter the Password : \t");
        String password = read1.nextLine();
        String[] result = r1.isPassengerRegistered(userName, password).split("-");

        if (Integer.parseInt(result[0]) == 1) {
            handlePassengerPrivileges(userName, result[1], c1, bookingAndReserving, read, read1);
        } else {
            System.out.printf(
                    "\n%20sERROR!!! Unable to login Cannot find user with the entered credentials.... Try Creating New Credentials or get yourself register by pressing 4....\n",
                    "");
        }
    }

    private static void handlePassengerPrivileges(String userName, String userID, Customer c1, FlightReservation bookingAndReserving, Scanner read, Scanner read1) {
        int desiredChoice;
        System.out.printf(
                "\n\n%-20sLogged in Successfully as \"%s\"..... For further Proceedings, enter a value from below....",
                "", userName);
        do {
            System.out.printf("\n\n%-60s+++++++++ 3rd Layer Menu +++++++++%50sLogged in as \"%s\"\n", "", "", userName);
            System.out.printf("%-40s (a) Enter 1 to Book a flight....\n", "");
            System.out.printf("%-40s (b) Enter 2 to update your Data....\n", "");
            System.out.printf("%-40s (c) Enter 3 to delete your account....\n", "");
            System.out.printf("%-40s (d) Enter 4 to Display Flight Schedule....\n", "");
            System.out.printf("%-40s (e) Enter 5 to Cancel a Flight....\n", "");
            System.out.printf("%-40s (f) Enter 6 to Display all flights registered by \"%s\"....\n", "", userName);
            System.out.printf("%-40s (g) Enter 0 to Go back to the Main Menu/Logout....\n", "");
            System.out.print("Enter the desired Choice :   ");
            desiredChoice = read.nextInt();
            handlePassengerChoice(desiredChoice, userID, c1, bookingAndReserving, read, read1);
        } while (desiredChoice != 0);
    }

    private static void handlePassengerChoice(int desiredChoice, String userID, Customer c1, FlightReservation bookingAndReserving, Scanner read, Scanner read1) {
        Flight f1 = new Flight();
        if (desiredChoice == 1) {
            f1.displayFlightSchedule();
            System.out.print("\nEnter the desired flight number to book :\t ");
            String flightToBeBooked = read1.nextLine();
            System.out.print("Enter the Number of tickets for " + flightToBeBooked + " flight :   ");
            int numOfTickets = read.nextInt();
            while (numOfTickets > 10) {
                System.out.print("ERROR!! You can't book more than 10 tickets at a time for single flight....Enter number of tickets again : ");
                numOfTickets = read.nextInt();
            }
            bookingAndReserving.bookFlight(flightToBeBooked, numOfTickets, userID);
        } else if (desiredChoice == 2) {
            c1.editUserInfo(userID);
        } else if (desiredChoice == 3) {
            System.out.print("Are you sure to delete your account...It's an irreversible action...Enter Y/y to confirm...");
            char confirmationChar = read1.nextLine().charAt(0);
            if (confirmationChar == 'Y' || confirmationChar == 'y') {
                c1.deleteUser(userID);
                System.out.printf("User %s's account deleted Successfully...!!!", userID);
                desiredChoice = 0;
            } else {
                System.out.println("Action has been cancelled...");
            }
        } else if (desiredChoice == 4) {
            f1.displayFlightSchedule();
            f1.displayMeasurementInstructions();
        } else if (desiredChoice == 5) {
            bookingAndReserving.cancelFlight(userID);
        } else if (desiredChoice == 6) {
            bookingAndReserving.displayFlightsRegisteredByOneUser(userID);
        } else {
            if (desiredChoice != 0) {
                System.out.println("Invalid Choice...Looks like you're Robot...Entering values randomly...You've Have to login again...");
            }
            desiredChoice = 0;
        }
    }
}
