package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Ticket {

    private String trainNumber;
    private String trainName;
    private String source;
    private String destination;
    private String dateOfTravel;
    private String userId;
    private Integer bookedSeats;

    @Override
    public String toString() {
        return "Ticket{" +
                "trainNumber='" + trainNumber + '\'' +
                ", trainName='" + trainName + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", dateOfTravel='" + dateOfTravel + '\'' +
                ", remainingSeat=" + bookedSeats +
                '}';
    }


    public Ticket(){}
    @JsonIgnoreProperties(ignoreUnknown = true)
    public Ticket(String trainNumber, String trainName, String userId, String source, String destination, String dateOfTravel, Integer bookedSeats){
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.userId = userId;
        this.source = source;
        this.destination = destination;
        this.dateOfTravel = dateOfTravel;
        this.bookedSeats = bookedSeats;
    }

    // Getters and Setters
    public String getTrainNumber(){
        return trainNumber;
    }
    public void setTrainNumber(String trainNumber){
        this.trainNumber = trainNumber;
    }

    public String getTrainName(){
        return trainName;
    }
    public void setTrainName(String trainName){
        this.trainName = trainName;
    }

    public String getSource(){
        return source;
    }
    public void setSource(String source){
        this.source = source;
    }

    public String getDestination(){
        return destination;
    }
    public void setDestination(String destination){
        this.destination = destination;
    }

    public String getDateOfTravel(){
        return dateOfTravel;
    }
    public void setDateOfTravel(String dateOfTravel){
        this.dateOfTravel = dateOfTravel;
    }

    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }

    public Integer getBookedSeats(){
        return bookedSeats;
    }
    public void setBookedSeats(Integer bookedSeats){
        this.bookedSeats = bookedSeats;
    }
}
