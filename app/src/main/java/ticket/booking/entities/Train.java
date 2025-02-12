package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

public class Train {

    private String trainNumber;
    private String trainName;
    private String source;
    private String destination;
    private String dateOfTravel;
    private final Integer totalSeats = 80;
    private Integer remainingSeats;


    public Train(){
        this.remainingSeats = totalSeats; // Initialize with total seats
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public Train(String trainNumber, String trainName, String source, String destination, String dateOfTravel){
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.dateOfTravel = dateOfTravel;
        this.remainingSeats = totalSeats; // Initialize with total seats
    }

    // Getters
    public String getTrainNumber(){
        return trainNumber;
    }
    public String getTrainName(){
        return trainName;
    }
    public String getSource(){
        return source;
    }
    public String getDestination(){
        return destination;
    }
    public String getDateOfTravel(){
        return dateOfTravel;
    }
    public Integer getRemainingSeats(){
        return remainingSeats;
    }

    // Setters
    public void setTrainNumber(String trainNumber){
        this.trainNumber = trainNumber;
    }
    public void setTrainName(String trainName){
        this.trainName = trainName;
    }
    public void setSource(String source){
        this.source = source;
    }
    public void setDestination(String destination){
        this.destination = destination;
    }
    public void setDateOfTravel(String dateOfTravel){
        this.dateOfTravel = dateOfTravel;
    }
    public void setRemainingSeats(Integer remainingSeats){
        this.remainingSeats = remainingSeats;
    }
}
