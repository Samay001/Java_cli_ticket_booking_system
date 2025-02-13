package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;

import javax.jws.soap.SOAPBinding;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserBookingServices {

    private static final String USER_DB_PATH = "app/src/main/java/ticket/booking/localDb/userDb.json";
    private static final String TRAIN_DB_PATH = "app/src/main/java/ticket/booking/localDb/trainDb.json";

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

    // -------------------  Updating tickets booked  -------------------
    public void updateTicketsBooked(String username, String trainName,Integer bookedSeats) {
        try {
            // Load the user list from the JSON file
            File userFile = new File(USER_DB_PATH);
            List<User> userList = objectMapper.readValue(userFile, new TypeReference<List<User>>() {});

            // Load the train list from the JSON file
            File trainFile = new File(TRAIN_DB_PATH);
            List<Train> trainList = objectMapper.readValue(trainFile, new TypeReference<List<Train>>() {});

            // Find the user by username
            User userToUpdate = userList.stream()
                    .filter(user -> user.getUsername().equalsIgnoreCase(username))
                    .findFirst()
                    .orElse(null);

            if (userToUpdate == null) {
                System.out.println("User not found.");
                return;
            }

            // Find the train by name
            Train selectedTrain = trainList.stream()
                    .filter(train -> train.getTrainName().equalsIgnoreCase(trainName))
                    .findFirst()
                    .orElse(null);

            if (selectedTrain == null) {
                System.out.println("Train not found.");
                return;
            }

            Ticket newTicket = new Ticket();
            newTicket.setTrainName(selectedTrain.getTrainName());
            newTicket.setTrainNumber(selectedTrain.getTrainNumber());
            newTicket.setUserId(userToUpdate.getUsername());
            newTicket.setSource(selectedTrain.getSource());
            newTicket.setDestination(selectedTrain.getDestination());
            newTicket.setBookedSeats(bookedSeats);

            // Add the new ticket to the user's ticket list
            List<Ticket> tickets = userToUpdate.getTicketsBooked();
            if (tickets == null) {
                tickets = new ArrayList<>();
            }
            tickets.add(newTicket);
            userToUpdate.setTicketsBooked(tickets);

            // Update the user list in the JSON file
            objectMapper.writeValue(userFile, userList);
            System.out.println("Ticket booking updated successfully.");

        } catch (IOException e) {
            System.out.println("Error updating tickets booked: " + e.getMessage());
        }
    }
}
