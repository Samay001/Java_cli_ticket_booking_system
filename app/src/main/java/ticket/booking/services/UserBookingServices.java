package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.User;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
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
            objectMapper.writeValue(file,userList);
            System.out.println("User data Successfully");
        }
        catch (IOException e){
            System.out.println("Error Storing user to database");
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

        System.out.println("No user found with username: " + user);
        return false;
    }

    public void signUp(User newUser) {
        try {
            if (fetchUserFromDb(newUser)) {
                System.out.println("User already exists!");
            }
            storeUserToDb(newUser);
            System.out.println("User successfully signup!");
        } catch (Exception e) {
            System.out.println("Error during sign-up: " + e.getMessage());
        }
    }


    public Boolean login(User user){
        try{
            if(fetchUserFromDb(user)){
                System.out.println("User Login successfully");
            }
            return true;
        }
        catch (Exception e){
            System.out.println("Error during login:" + e.getMessage());
            return false;
        }
    }

}
