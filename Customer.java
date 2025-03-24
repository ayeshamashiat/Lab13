import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Customer {

    // ************************************************************ Fields
    // ************************************************************
    private String name;
    private String userID;
    private String email;
    private String password;
    private String phone;
    private String address;
    private int age;
    private List<Flight> flightsRegisteredByUser;
    private List<Integer> numOfTicketsBookedByUser;
    public static final List<Customer> customerCollection = User.getCustomersCollection();

    // ************************************************************ Constructors
    // ************************************************************

    public Customer() {
        this.name = null;
        this.email = null;
        this.password = null;
        this.phone = null;
        this.address = null;
        this.age = 0;
    }

    public Customer(String name, String email, String password, String phone, String address, int age) {
        RandomGenerator random = new RandomGenerator();
        random.randomIDGen();
        this.name = name;
        this.userID = random.getRandomNumber();
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.age = age;
        this.flightsRegisteredByUser = new ArrayList<>();
        this.numOfTicketsBookedByUser = new ArrayList<>();
    }

    // ************************************************************ Methods
    // ************************************************************

    public void addNewCustomer() {
        Scanner read = new Scanner(System.in);
        String name = promptUserInput(read, "Enter your name :\t");
        String email = promptUserInput(read, "Enter your email address :\t");
        while (isUniqueData(email)) {
            System.out.println("ERROR!!! User with the same email already exists... Use new email or login using the previous credentials....");
            email = promptUserInput(read, "Enter your email address :\t");
        }
        String password = promptUserInput(read, "Enter your Password :\t");
        String phone = promptUserInput(read, "Enter your Phone number :\t");
        String address = promptUserInput(read, "Enter your address :\t");
        int age = Integer.parseInt(promptUserInput(read, "Enter your age :\t"));
        User.getCustomersCollection().add(new Customer(name, email, password, phone, address, age));
    }

    private String promptUserInput(Scanner read, String message) {
        System.out.print(message);
        return read.nextLine();
    }

    public void searchUser(String ID) {
        boolean isFound = false;
        Customer customerWithTheID = customerCollection.get(0);
        for (Customer c : customerCollection) {
            if (ID.equals(c.getUserID())) {
                System.out.printf("%-50sCustomer Found...!!!Here is the Full Record...!!!\n\n\n", " ");
                displayHeader();
                isFound = true;
                customerWithTheID = c;
                break;
            }
        }
        if (isFound) {
            System.out.println(customerWithTheID.toString(1));
            System.out.printf(
                    "%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+\n",
                    "");
        } else {
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", ID);
        }
    }

    private Customer findCustomerByID(String ID) {
        for (Customer c : User.getCustomersCollection()) {
            if (ID.equals(c.getUserID())) {
                return c;
            }
        }
        return null;
    }

    public boolean isUniqueData(String emailID) {
        return User.getCustomersCollection().stream().anyMatch(c -> emailID.equals(c.getEmail()));
    }

    public void editUserInfo(String ID) {
        Customer customer = findCustomerByID(ID);
        if (customer != null) {
            Scanner read = new Scanner(System.in);
            customer.setName(promptUserInput(read, "Enter the new name of the Passenger:\t"));
            customer.setEmail(promptUserInput(read, "Enter the new email address of Passenger " + customer.getName() + ":\t"));
            customer.setPhone(promptUserInput(read, "Enter the new Phone number of Passenger " + customer.getName() + ":\t"));
            customer.setAddress(promptUserInput(read, "Enter the new address of Passenger " + customer.getName() + ":\t"));
            customer.setAge(Integer.parseInt(promptUserInput(read, "Enter the new age of Passenger " + customer.getName() + ":\t")));
            displayCustomersData(false);
        } else {
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", ID);
        }
    }

    public void deleteUser(String ID) {
        Customer customer = findCustomerByID(ID);
        if (customer != null) {
            User.getCustomersCollection().remove(customer);
            System.out.printf("\n%-50sPrinting all Customer's Data after deleting Customer with the ID %s.....!!!!\n", "", ID);
            displayCustomersData(false);
        } else {
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", ID);
        }
    }

    public void displayCustomersData(boolean showHeader) {
        if (showHeader) {
            displayHeader();
        }
        int i = 0;
        for (Customer c : User.getCustomersCollection()) {
            i++;
            System.out.println(c.toString(i));
            System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+\n", "");
        }
    }

    void displayHeader() {
        System.out.println();
        System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+\n", "");
        System.out.printf("%10s| SerialNum  |   UserID   | Passenger Names                  | Age     | EmailID\t\t       | Home Address\t\t\t     | Phone Number\t       |%n", "");
        System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+\n", "");
        System.out.println();
    }

    String randomIDDisplay(String randomID) {
        return randomID.replaceAll("(.{3})", "$1 ");
    }

    void addNewFlightToCustomerList(Flight f) {
        this.flightsRegisteredByUser.add(f);
    }

    void addExistingFlightToCustomerList(int index, int numOfTickets) {
        int newNumOfTickets = numOfTicketsBookedByUser.get(index) + numOfTickets;
        this.numOfTicketsBookedByUser.set(index, newNumOfTickets);
    }

    @Override
    public String toString() {
        return String.format("Customer{name='%s', userID='%s', email='%s', phone='%s', address='%s', age=%d}", name, userID, email, phone, address, age);
    }

    public String toString(int index) {
        return String.format("%10s| %-10d | %-10s | %-32s | %-7d | %-29s | %-35s | %-23s |", "", index, userID, name, age, email, address, phone);
    }

    // ************************************************************ Setters &
    // Getters ************************************************************

    public List<Flight> getFlightsRegisteredByUser() {
        return flightsRegisteredByUser;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getNumOfTicketsBookedByUser() {
        return numOfTicketsBookedByUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }
}