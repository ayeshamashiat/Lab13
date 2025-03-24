import java.util.Scanner;

public class AdminHandler {
    private static String[][] adminUserNameAndPassword = new String[10][2];

    public static void handleAdminLogin(RolesAndPermissions r1, Customer c1, Scanner read1) {
        adminUserNameAndPassword[0][0] = "root";
        adminUserNameAndPassword[0][1] = "root";

        System.out.print("\nEnter the UserName to login to the Management System :     ");
        String username = read1.nextLine();
        System.out.print("Enter the Password to login to the Management System :    ");
        String password = read1.nextLine();
        System.out.println();

        if (r1.isPrivilegedUserOrNot(username, password) == -1) {
            System.out.printf(
                    "\n%20sERROR!!! Unable to login Cannot find user with the entered credentials.... Try Creating New Credentials or get yourself register by pressing 4....\n",
                    "");
        } else if (r1.isPrivilegedUserOrNot(username, password) == 0) {
            System.out.println(
                    "You've standard/default privileges to access the data... You can just view customers data... Can't perform any actions on them....");
            c1.displayCustomersData(true);
        } else {
            handleAdminPrivileges(username, c1, read1);
        }
    }

    private static void handleAdminPrivileges(String username, Customer c1, Scanner read1) {
        FlightReservation bookingAndReserving = new FlightReservation();
        Flight f1 = new Flight();
        int desiredOption;
        do {
            System.out.printf("\n\n%-60s+++++++++ 2nd Layer Menu +++++++++%50sLogged in as \"%s\"\n", "", "", username);
            System.out.printf("%-30s (a) Enter 1 to add new Passenger....\n", "");
            System.out.printf("%-30s (b) Enter 2 to search a Passenger....\n", "");
            System.out.printf("%-30s (c) Enter 3 to update the Data of the Passenger....\n", "");
            System.out.printf("%-30s (d) Enter 4 to delete a Passenger....\n", "");
            System.out.printf("%-30s (e) Enter 5 to Display all Passengers....\n", "");
            System.out.printf("%-30s (f) Enter 6 to Display all flights registered by a Passenger...\n", "");
            System.out.printf("%-30s (g) Enter 7 to Display all registered Passengers in a Flight....\n", "");
            System.out.printf("%-30s (h) Enter 8 to Delete a Flight....\n", "");
            System.out.printf("%-30s (i) Enter 0 to Go back to the Main Menu/Logout....\n", "");
            System.out.print("Enter the desired Choice :   ");
            desiredOption = read1.nextInt();
            handleAdminChoice(desiredOption, c1, bookingAndReserving, f1, read1);
        } while (desiredOption != 0);
    }

    private static void handleAdminChoice(int desiredOption, Customer c1, FlightReservation bookingAndReserving, Flight f1, Scanner read1) {
        switch (desiredOption) {
            case 1:
                c1.addNewCustomer();
                break;
            case 2:
                handleSearchCustomer(c1, read1);
                break;
            case 3:
                handleUpdateCustomer(c1, read1);
                break;
            case 4:
                handleDeleteCustomer(c1, read1);
                break;
            case 5:
                c1.displayCustomersData(false);
                break;
            case 6:
                handleDisplayFlightsByUser(c1, bookingAndReserving, read1);
                break;
            case 7:
                handleDisplayPassengersForFlights(bookingAndReserving, f1, read1);
                break;
            case 8:
                handleDeleteFlight(f1, read1);
                break;
            case 0:
                System.out.println("Thanks for Using BAV Airlines Ticketing System...!!!");
                break;
            default:
                System.out.println("Invalid Choice...Looks like you're Robot...Entering values randomly...You've Have to login again...");
                break;
        }
    }

    private static void handleSearchCustomer(Customer c1, Scanner read1) {
        c1.displayCustomersData(false);
        System.out.print("Enter the CustomerID to Search :\t");
        String customerID = read1.nextLine();
        System.out.println();
        c1.searchUser(customerID);
    }

    private static void handleUpdateCustomer(Customer c1, Scanner read1) {
        c1.displayCustomersData(false);
        System.out.print("Enter the CustomerID to Update its Data :\t");
        String customerID = read1.nextLine();
        if (User.getCustomersCollection().size() > 0) {
            c1.editUserInfo(customerID);
        } else {
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", customerID);
        }
    }

    private static void handleDeleteCustomer(Customer c1, Scanner read1) {
        c1.displayCustomersData(false);
        System.out.print("Enter the CustomerID to Delete its Data :\t");
        String customerID = read1.nextLine();
        if (User.getCustomersCollection().size() > 0) {
            c1.deleteUser(customerID);
        } else {
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", customerID);
        }
    }

    private static void handleDisplayFlightsByUser(Customer c1, FlightReservation bookingAndReserving, Scanner read1) {
        c1.displayCustomersData(false);
        System.out.print("\n\nEnter the ID of the user to display all flights registered by that user...");
        String id = read1.nextLine();
        bookingAndReserving.displayFlightsRegisteredByOneUser(id);
    }

    private static void handleDisplayPassengersForFlights(FlightReservation bookingAndReserving, Flight f1, Scanner read1) {
        System.out.print("Do you want to display Passengers of all flights or a specific flight.... 'Y/y' for displaying all flights and 'N/n' to look for a specific flight.... ");
        char choice = read1.nextLine().charAt(0);
        if ('y' == choice || 'Y' == choice) {
            bookingAndReserving.displayRegisteredUsersForAllFlight();
        } else if ('n' == choice || 'N' == choice) {
            f1.displayFlightSchedule();
            System.out.print("Enter the Flight Number to display the list of passengers registered in that flight... ");
            String flightNum = read1.nextLine();
            bookingAndReserving.displayRegisteredUsersForASpecificFlight(flightNum);
        } else {
            System.out.println("Invalid Choice...No Response...!");
        }
    }

    private static void handleDeleteFlight(Flight f1, Scanner read1) {
        f1.displayFlightSchedule();
        System.out.print("Enter the Flight Number to delete the flight : ");
        String flightNum = read1.nextLine();
        f1.deleteFlight(flightNum);
    }

    public static void handleAdminRegistration(RolesAndPermissions r1, Scanner read1, int countNumOfUsers) {
        System.out.print("\nEnter the UserName to Register :    ");
        String username = read1.nextLine();
        System.out.print("Enter the Password to Register :     ");
        String password = read1.nextLine();
        while (r1.isPrivilegedUserOrNot(username, password) != -1) {
            System.out.print("ERROR!!! Admin with same UserName already exist. Enter new UserName:   ");
            username = read1.nextLine();
            System.out.print("Enter the Password Again:   ");
            password = read1.nextLine();
        }

        adminUserNameAndPassword[countNumOfUsers][0] = username;
        adminUserNameAndPassword[countNumOfUsers][1] = password;
        countNumOfUsers++;
    }
}
