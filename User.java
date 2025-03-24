/*
 * This class is intended to be the main class for this Project. All necessary methods are getting calls from this class.
 *
 *
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {

    // ************************************************************ Fields
    // ************************************************************
    private static List<Customer> customersCollection = new ArrayList<>();

    // ************************************************************ Behaviours/Methods
    // ************************************************************

    public static void main(String[] args) {
        initializeSystem();
        Scanner read = new Scanner(System.in);
        displayWelcomeMessage();
        displayMainMenu();
        int desiredOption = read.nextInt();
        validateDesiredOption(desiredOption, read);

        do {
            Scanner read1 = new Scanner(System.in);
            handleDesiredOption(desiredOption, read, read1);
            displayMainMenu();
            desiredOption = read1.nextInt();
            validateDesiredOption(desiredOption, read1);
        } while (desiredOption != 0);
    }

    private static void initializeSystem() {
        int countNumOfUsers = 1;
        RolesAndPermissions r1 = new RolesAndPermissions();
        Flight f1 = new Flight();
        FlightReservation bookingAndReserving = new FlightReservation();
        Customer c1 = new Customer();
        f1.flightScheduler();
    }

    private static void displayWelcomeMessage() {
        System.out.println(
                "\n\t\t\t\t\t+++++++++++++ Welcome to BAV AirLines +++++++++++++\n\nTo Further Proceed, Please enter a value.");
        System.out.println(
                "\n***** Default Username && Password is root-root ***** Using Default Credentials will restrict you to just view the list of Passengers....\n");
    }

    private static void validateDesiredOption(int desiredOption, Scanner read) {
        while (desiredOption < 0 || desiredOption > 8) {
            System.out.print("ERROR!! Please enter value between 0 - 4. Enter the value again :\t");
            desiredOption = read.nextInt();
        }
    }

    private static void handleDesiredOption(int desiredOption, Scanner read, Scanner read1) {
        RolesAndPermissions r1 = new RolesAndPermissions();
        Flight f1 = new Flight();
        FlightReservation bookingAndReserving = new FlightReservation();
        Customer c1 = new Customer();
        int countNumOfUsers = 1;

        if (desiredOption == 1) {
            AdminHandler.handleAdminLogin(r1, c1, read1);
        } else if (desiredOption == 2) {
            AdminHandler.handleAdminRegistration(r1, read1, countNumOfUsers);
        } else if (desiredOption == 3) {
            PassengerHandler.handlePassengerLogin(r1, c1, bookingAndReserving, read, read1);
        } else if (desiredOption == 4) {
            c1.addNewCustomer();
        } else if (desiredOption == 5) {
            manualInstructions();
        }
    }

    static void displayMainMenu() {
        System.out.println("\n\n\t\t(a) Press 0 to Exit.");
        System.out.println("\t\t(b) Press 1 to Login as admin.");
        System.out.println("\t\t(c) Press 2 to Register as admin.");
        System.out.println("\t\t(d) Press 3 to Login as Passenger.");
        System.out.println("\t\t(e) Press 4 to Register as Passenger.");
        System.out.println("\t\t(f) Press 5 to Display the User Manual.");
        System.out.print("\t\tEnter the desired option:    ");
    }

    static void manualInstructions() {
        Scanner read = new Scanner(System.in);
        System.out.printf("%n%n%50s %s Welcome to BAV Airlines User Manual %s", "", "+++++++++++++++++",
                "+++++++++++++++++");
        System.out.println("\n\n\t\t(a) Press 1 to display Admin Manual.");
        System.out.println("\t\t(b) Press 2 to display User Manual.");
        System.out.print("\nEnter the desired option :    ");
        int choice = read.nextInt();
        while (choice < 1 || choice > 2) {
            System.out.print("ERROR!!! Invalid entry...Please enter a value either 1 or 2....Enter again....");
            choice = read.nextInt();
        }
        if (choice == 1) {
            displayAdminManual();
        } else {
            displayUserManual();
        }
    }

    private static void displayAdminManual() {
        System.out.println(
                "\n\n(1) Admin have the access to all users data...Admin can delete, update, add and can perform search for any customer...\n");
        System.out.println(
                "(2) In order to access the admin module, you've to get yourself register by pressing 2, when the main menu gets displayed...\n");
        System.out.println(
                "(3) Provide the required details i.e., name, email, id...Once you've registered yourself, press 1 to login as an admin... \n");
        System.out.println(
                "(4) Once you've logged in, 2nd layer menu will be displayed on the screen...From here on, you can select from variety of options...\n");
        System.out.println(
                "(5) Pressing \"1\" will add a new Passenger, provide the program with required details to add the passenger...\n");
        System.out.println(
                "(6) Pressing \"2\" will search for any passenger, given the admin(you) provides the ID from the table printing above....  \n");
        System.out.println(
                "(7) Pressing \"3\" will let you update any passengers data given the user ID provided to program...\n");
        System.out.println("(8) Pressing \"4\" will let you delete any passenger given its ID provided...\n");
        System.out.println("(9) Pressing \"5\" will let you display all registered passenger...\n");
        System.out.println(
                "(10) Pressing \"6\" will let you display all registered passengers...After selecting, program will ask, if you want to display passengers for all flights(Y/y) or a specific flight(N/n)\n");
        System.out.println(
                "(11) Pressing \"7\" will let you delete any flight given its flight number provided...\n");
        System.out.println(
                "(11) Pressing \"0\" will make you logged out of the program...You can login again any time you want during the program execution....\n");
    }

    private static void displayUserManual() {
        System.out.println(
                "\n\n(1) Local user has the access to its data only...He/She won't be able to change/update other users data...\n");
        System.out.println(
                "(2) In order to access local users benefits, you've to get yourself register by pressing 4, when the main menu gets displayed...\n");
        System.out.println(
                "(3) Provide the details asked by the program to add you to the users list...Once you've registered yourself, press \"3\" to login as a passenger...\n");
        System.out.println(
                "(4) Once you've logged in, 3rd layer menu will be displayed...From here on, you embarked on the journey to fly with us...\n");
        System.out.println(
                "(5) Pressing \"1\" will display available/scheduled list of flights...To get yourself booked for a flight, enter the flight number and number of tickets for the flight...Max num of tickets at a time is 10 ...\n");
        System.out.println(
                "(7) Pressing \"2\" will let you update your own data...You won't be able to update other's data... \n");
        System.out.println("(8) Pressing \"3\" will delete your account... \n");
        System.out
                .println("(9) Pressing \"4\" will display randomly designed flight schedule for this runtime...\n");
        System.out.println("(10) Pressing \"5\" will let you cancel any flight registered by you...\n");
        System.out.println("(11) Pressing \"6\" will display all flights registered by you...\n");
        System.out.println(
                "(12) Pressing \"0\" will make you logout of the program...You can login back at anytime with your credentials...for this particular run-time... \n");
    }

    // ************************************************************ Setters &
    // Getters ************************************************************

    public static List<Customer> getCustomersCollection() {
        return customersCollection;
    }
}