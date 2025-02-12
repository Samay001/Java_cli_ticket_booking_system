package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
signup
login
storeUserToDb
fetchUserFromDb
*/
public class UserBookingServices {

    private static final String USER_DB_PATH = "app/src/main/java/ticket/booking/localDb/userDb.json";
    //serialization and deserialization
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void storeUserToDb(User newUser){
        try{
            File file = new File(USER_DB_PATH);
            List<User> userList;

            if(file.exists() && file.length() > 0){
                userList = objectMapper.readValue(file, new TypeReference<List<User>>() {});
            }
            else{
                userList = new ArrayList<>();
            }

            userList.add(newUser);
            objectMapper.writeValue(file, userList);
            System.out.println("User data stored successfully.");
        }
        catch (IOException e){
            System.out.println("Error storing user to database: " + e.getMessage());
        }
    }

    public Boolean fetchUserFromDb(User user){
        File file = new File(USER_DB_PATH);
        List<User> userList;

        if (file.exists() && file.length() > 0) {
            try {
                userList = objectMapper.readValue(file, new TypeReference<List<User>>() {});

                boolean userExists = userList.stream()
                        .anyMatch(u -> u.getUsername().equals(user.getUsername())
                                && u.getPassword().equals(user.getPassword()));

                if (userExists) {
                    System.out.println("User found: " + user.getUsername());
                    return true;
                }

            } catch (IOException e) {
                System.out.println("Error reading user database: " + e.getMessage());
            }
        }

        System.out.println("No user found with username: " + user.getUsername());
        return false;
    }

    // New Method: Check if Username Already Exists
    public Boolean isUsernameTaken(String username) {
        File file = new File(USER_DB_PATH);
        List<User> userList;

        if (file.exists() && file.length() > 0) {
            try {
                userList = objectMapper.readValue(file, new TypeReference<List<User>>() {});

                boolean usernameExists = userList.stream()
                        .anyMatch(u -> u.getUsername().equals(username));

                return usernameExists;
            } catch (IOException e) {
                System.out.println("Error reading user database: " + e.getMessage());
            }
        }
        return false;
    }

    public void signUp(User newUser) {
        try {
            if (isUsernameTaken(newUser.getUsername())) {
                System.out.println("Username already taken. Please choose another one.");
            }
            else{
                storeUserToDb(newUser);
                System.out.println("User successfully signed up!");
            }

        } catch (Exception e) {
            System.out.println("Error during sign-up: " + e.getMessage());
        }
    }

    public Boolean login(User user){
        try{
            if(fetchUserFromDb(user)){
                System.out.println("User logged in successfully.");
                return true;
            }
            else {
                System.out.println("Invalid username or password.");
                return false;
            }
        }
        catch (Exception e){
            System.out.println("Error during login:" + e.getMessage());
            return false;
        }
    }

}
