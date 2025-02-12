package ticket.booking;

import ticket.booking.entities.User;
import ticket.booking.services.UserBookingServices;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class App {

    public static void main(String[] args) {
        System.out.println("Welcome to the IRCTC Ticket Booking System!");

        Scanner input = new Scanner(System.in);
        UserBookingServices userBookingServices = new UserBookingServices();
        boolean isRunning = true;
        boolean isAuthenticated = false;
        String loggedInUser = null;

        while (isRunning) {
            // Display Menu Options
            System.out.println("\nChoose an option:");
            System.out.println("1: Sign Up");
            System.out.println("2: Login");
            System.out.println("3: Fetch Bookings");
            System.out.println("4: Search Trains");
            System.out.println("5: Book Seats");
            System.out.println("6: Cancel My Booking");
            System.out.println("7: Exit the app");
            System.out.print("Your choice: ");

            int choice = input.nextInt();
            input.nextLine();  // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.println("\n=== Sign Up ===");
                    System.out.print("Enter Username: ");
                    String usernameToSignup = input.nextLine().trim();
                    System.out.print("Enter Password: ");
                    String passwordToSignUp = input.nextLine().trim();

                    // Validate Input
                    if (usernameToSignup.isEmpty() || passwordToSignUp.isEmpty()) {
                        System.out.println("Username and Password cannot be empty.");
                        break;
                    }

                    User newUser = new User(usernameToSignup, passwordToSignUp, UUID.randomUUID().toString(), new ArrayList<>());
                    userBookingServices.signUp(newUser);
                    break;

                case 2:
                    System.out.println("\n=== Login ===");
                    System.out.print("Enter Username: ");
                    String usernameToLogin = input.nextLine().trim();
                    System.out.print("Enter Password: ");
                    String passwordToLogin = input.nextLine().trim();

                    User userToLogin = new User(usernameToLogin, passwordToLogin, null, null);
                    if (userBookingServices.login(userToLogin)) {
                        isAuthenticated = true;
                        loggedInUser = usernameToLogin;
                        System.out.println("Welcome, " + loggedInUser + "!");
                    } else {
                        System.out.println("Login failed. Please check your credentials.");
                    }
                    break;

                case 3:
                    if (isAuthenticated) {
                        System.out.println("\n=== Fetch Bookings ===");
                        System.out.println("Fetching bookings for " + loggedInUser + "...");
                        // Call the method to fetch bookings for loggedInUser (to be implemented)
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;

                case 4:
                    if (isAuthenticated) {
                        System.out.println("\n=== Search Trains ===");
                        System.out.print("Enter Source: ");
                        String source = input.nextLine().trim();
                        System.out.print("Enter Destination: ");
                        String destination = input.nextLine().trim();
                        System.out.println("Searching trains from " + source + " to " + destination + "...");
                        // Call the method to search trains (to be implemented)
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;

                case 5:
                    if (isAuthenticated) {
                        System.out.println("\n=== Book Seats ===");
                        System.out.println("Booking seats for " + loggedInUser + "...");
                        // Call the method to book seats (to be implemented)
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;

                case 6:
                    if (isAuthenticated) {
                        System.out.println("\n=== Cancel My Booking ===");
                        System.out.println("Cancelling booking for " + loggedInUser + "...");
                        // Call the method to cancel booking (to be implemented)
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;

                case 7:
                    System.out.println("Exiting the application. Goodbye!");
                    isRunning = false;
                    break;

                default:
                    System.out.println("Invalid option. Please choose a valid option from the menu.");
                    break;
            }
        }

        input.close();
    }
}
