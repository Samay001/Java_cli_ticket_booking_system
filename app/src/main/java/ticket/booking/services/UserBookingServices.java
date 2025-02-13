package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserBookingServices {

    private static final String USER_DB_PATH = "app/src/main/java/ticket/booking/localDb/userDb.json";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private User currentActiveUser;

    // -------------------  Store User to DB  -------------------
    public void storeUserToDb(User newUser) {
        try {
            File file = new File(USER_DB_PATH);
            List<User> userList;

            if (file.exists() && file.length() > 0) {
                userList = objectMapper.readValue(file, new TypeReference<List<User>>() {});
            } else {
                userList = new ArrayList<>();
            }

            userList.add(newUser);
            objectMapper.writeValue(file, userList);
            System.out.println("User data stored successfully.");
        } catch (IOException e) {
            System.out.println("Error storing user to database: " + e.getMessage());
        }
    }

    // -------------------  Fetch User from DB  -------------------
    public Boolean fetchUserFromDb(User user) {
        File file = new File(USER_DB_PATH);
        List<User> userList;

        if (file.exists() && file.length() > 0) {
            try {
                userList = objectMapper.readValue(file, new TypeReference<List<User>>() {});

                Optional<User> foundUser = userList.stream()
                        .filter(u -> u.getUsername().equals(user.getUsername())
                                && u.getPassword().equals(user.getPassword()))
                        .findFirst();

                if (foundUser.isPresent()) {
                    currentActiveUser = foundUser.get();
                    System.out.println("User found and logged in: " + currentActiveUser.getUsername());
                    return true;
                }

            } catch (IOException e) {
                System.out.println("Error reading user database: " + e.getMessage());
            }
        }

        System.out.println("No user found with username: " + user.getUsername());
        return false;
    }

    // -------------------  Sign Up  -------------------
    public void signUp(User newUser) {
        if (isUsernameTaken(newUser.getUsername())) {
            System.out.println("Username already taken. Please choose another one.");
        } else {
            newUser.setUserId(UUID.randomUUID().toString());
            storeUserToDb(newUser);
            System.out.println("User successfully signed up!");
        }
    }

    // -------------------  Login  -------------------
    public Boolean login(User user) {
        if (fetchUserFromDb(user)) {
            System.out.println("User logged in successfully.");
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    // -------------------  Check if Username Already Exists  -------------------
    public Boolean isUsernameTaken(String username) {
        File file = new File(USER_DB_PATH);
        List<User> userList;

        if (file.exists() && file.length() > 0) {
            try {
                userList = objectMapper.readValue(file, new TypeReference<List<User>>() {});
                return userList.stream().anyMatch(u -> u.getUsername().equals(username));
            } catch (IOException e) {
                System.out.println("Error reading user database: " + e.getMessage());
            }
        }
        return false;
    }

    // -------------------  Fetch Bookings  -------------------
    public void fetchBookings() {
        if (currentActiveUser == null) {
            System.out.println("No user is currently logged in.");
            return;
        }

        List<Ticket> bookings = currentActiveUser.getTicketsBooked();
        if (bookings != null && !bookings.isEmpty()) {
            bookings.forEach(System.out::println);
        } else {
            System.out.println("No bookings found for the current user.");
        }
    }



    // -------------------  Book Seat  -------------------
//    public void bookSeat(Train selectedTrain) {
//        if (currentActiveUser == null) {
//            System.out.println("Please login to book a seat.");
//            return;
//        }
//
//        Ticket newTicket = new Ticket(UUID.randomUUID().toString(), selectedTrain);
//        currentActiveUser.getTicketsBooked().add(newTicket);
//        storeUserToDb(currentActiveUser);
//
//        System.out.println("Ticket booked successfully: " + newTicket);
//    }

    // -------------------  Cancel My Booking  -------------------
//    public void cancelMyBooking(String ticketId) {
//        if (currentActiveUser == null) {
//            System.out.println("Please login to cancel a booking.");
//            return;
//        }
//
//        List<Ticket> bookings = currentActiveUser.getTicketsBooked();
//        if (bookings != null && !bookings.isEmpty()) {
//            bookings.removeIf(ticket -> ticket.getTicketId().equals(ticketId));
//            storeUserToDb(currentActiveUser);
//            System.out.println("Booking cancelled successfully.");
//        } else {
//            System.out.println("No bookings found to cancel.");
//        }
//    }
}
