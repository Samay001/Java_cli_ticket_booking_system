package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TrainServices {

    private List<Train> trainList;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String TRAIN_DB_PATH = "app/src/main/java/ticket/booking/localDb/trainDb.json";

    // Search Trains by Source and Destination
    public List<String> getTrains(String src, String des) {
        List<String> matchingTrains = new ArrayList<>();

        try {
            File file = new File(TRAIN_DB_PATH);

            // Check if the file exists and is not empty
            if (file.exists() && file.length() > 0) {
                // Deserialize JSON into List of Train objects
                trainList = objectMapper.readValue(file, new TypeReference<List<Train>>() {});

                // Filter trains based on source and destination
                matchingTrains = trainList.stream()
                        .filter(train -> train.getSource().equalsIgnoreCase(src)
                                && train.getDestination().equalsIgnoreCase(des))
                        .map(Train::getTrainName)  // Get Train Names
                        .collect(Collectors.toList());
            } else {
                System.out.println("Train database is empty or not found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading train database: " + e.getMessage());
        }

        // Return list of matching train names
        return matchingTrains;
    }

    public Integer availableSeats(String trainName) {
        try {
            // Load the train list from the JSON file
            trainList = Arrays.asList(objectMapper.readValue(new File(TRAIN_DB_PATH), Train[].class));

            // Search for the train by name and get the remaining seats
            return trainList.stream()
                    .filter(train -> train.getTrainName().equalsIgnoreCase(trainName))
                    .map(Train::getRemainingSeats)
                    .findFirst()
                    .orElse(null); // Returns null if no matching train is found
        } catch (IOException e) {
            System.out.println("Error reading train database: " + e.getMessage());
            return null;
        }
    }

    public void updateSeatCount(int countOfSeatsBooked, String trainName) {
        try {
            // Load the train list from the JSON file
            trainList = Arrays.asList(objectMapper.readValue(new File(TRAIN_DB_PATH), Train[].class));

            // Find the booked train and update its seat count
            trainList.forEach(train -> {
                if (train.getTrainName().equalsIgnoreCase(trainName)) {
                    int remainingSeats = train.getRemainingSeats();
                    if (remainingSeats >= countOfSeatsBooked) {
                        train.setRemainingSeats(remainingSeats - countOfSeatsBooked);
                        System.out.println("Seat count updated successfully.");
                        System.out.println("Remaining Seats" + train.getRemainingSeats());
                    } else {
                        System.out.println("Not enough seats available.");
                    }
                }
            });

            // Save the updated list back to the JSON file
            objectMapper.writeValue(new File(TRAIN_DB_PATH), trainList);

        } catch (IOException e) {
            System.out.println("Error updating seat count: " + e.getMessage());
        }
    }




}
